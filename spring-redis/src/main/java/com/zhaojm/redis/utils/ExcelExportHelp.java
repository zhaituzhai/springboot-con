package com.zhaojm.redis.utils;

import com.zhaojm.redis.annotation.ExcelField;
import com.zhaojm.redis.annotation.ExcelSheet;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ExcelExportHelp {
    private ThreadLocal <Workbook> workbookThreadLocal = new ThreadLocal();
    private ThreadLocal<Map<String,MyField>> myFieldThreadLocal = new ThreadLocal();
    public static final Integer DEFAULT_ROWS = 500;
    private int start = 0;
    private static Logger log = LoggerFactory.getLogger(ExcelExportHelp.class);
    // 数据字典
    Map<String, Map<String, String>> dictMap = new ConcurrentHashMap();

    @Autowired
//    private DictionaryHelper dictionaryHelper;
    public ExcelExportHelp(){
        //getWorkBook();
    }
    private Workbook getWorkBook() {
        // ②如果workbookThreadLocal没有本线程对应的Workbook创建一个新的Workbook，
        // 并将其保存到线程本地变量中。
        if (workbookThreadLocal.get() == null) {
            Workbook workbook = new SXSSFWorkbook(DEFAULT_ROWS);
            workbookThreadLocal.set(workbook);
            return workbook;
        } else {
            return workbookThreadLocal.get();
            // ③直接返回线程本地变量
        }
    }

    private Map<String,MyField> parase(Class<?> sheetClass) {
        int headColorIndex = -1;
        ExcelSheet excelSheet = sheetClass.getAnnotation(ExcelSheet.class);
        String sheetName = "";
        Sheet sheet = null;
        Workbook workbook = getWorkBook();
        Map<String, MyField> myFieldMap = new HashMap();
        // sheet field
        if (sheetClass.getDeclaredFields() != null && sheetClass.getDeclaredFields().length > 0) {
            for (Field field : sheetClass.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if (field.isAnnotationPresent(ExcelField.class)) { // 如果有excel
                    // 注解设置到对象中
                    ExcelField excelField = field.getAnnotation(ExcelField.class);
                    field.setAccessible(true);// 设置实体类私有属性可访问
                    MyField myField = new MyField();
                    myField.setExcelField(excelField);
                    myField.setField(field);
                    myField.setName(field.getName());
                    myFieldMap.put(field.getName(), myField);
                }
            }
        }

        if (CollectionUtils.isEmpty(myFieldMap)) {
            throw new RuntimeException(">>>>>>>>>>> excel export error, data field can not be empty.");
        }

        int fieldCnt = 0;
        for (String key : myFieldMap.keySet()) {
            ExcelField excelField = myFieldMap.get(key).getExcelField();
            MyField myField = myFieldMap.get(key);
            if (excelField != null) {
                if (!StringUtils.isEmpty(excelField.comb())) {
                    MyField tempField = myFieldMap.get(excelField.comb());
                    if (tempField == null) {
                        continue;
                    }
                    Field combField = tempField.getField();
                    myField.setCombField(combField);
                    //ExcelField combExcelField = tempField.getExcelField();
                    //tempField.setCombFieldVisible(combExcelField.combVisible());
                    fieldCnt += 1;
                }
            }
        }

        if (excelSheet != null) {
            if (excelSheet.name() != null && excelSheet.name().trim().length() > 0) {
                sheetName = excelSheet.name().trim();
            }else{
                sheetName = "sheet1";
            }
            start = excelSheet.start();
            if (start <= 0) {
                start = 1;
            }
            String template = excelSheet.template();
            if (!StringUtils.isEmpty(template)) {
                //  Resource resource = new ClassPathResource(template);
                //Resource resource = this.getClass().getClassLoader().getResource(template);
                // byte[] input = resource.getInputStream();
                try {
                    Workbook tworkbook = WorkbookFactory.create(this.getClass().getClassLoader().getResourceAsStream(template));
                    SXSSFWorkbook zworkbook = new SXSSFWorkbook((XSSFWorkbook) tworkbook, 1000);
                    workbookThreadLocal.set(zworkbook);
                } catch (IOException e) {
                	log.error("系统业务异常",e);
                } catch (InvalidFormatException e) {
                	log.error("系统业务异常",e);
                }
                sheet = getWorkBook().getSheetAt(0);
            } else {
                // 没有模板,1、 create sheet  2、表头
                sheet = workbook.createSheet(sheetName);
                // sheet header row
                CellStyle[] fieldDataStyleArr = new CellStyle[fieldCnt];
                int[] fieldWidthArr = new int[fieldCnt];
                Row headRow = sheet.createRow(0);
                for (String key : myFieldMap.keySet()) {

                    // field
                    Field field = myFieldMap.get(key).getField();
                    ExcelField excelField = myFieldMap.get(key).getExcelField();
                    Field combField = myFieldMap.get(key).getCombField();

                    String fieldName = field.getName();
                    int fieldWidth = 0;
                    HorizontalAlignment align = null;
                    if (excelField != null) {
//                        if (combFieldSet.contains(key)) {
//                            continue;
//                        }
                        if (excelField.index() == 0){
                            continue;
                        }
                        if (excelField.name() != null && excelField.name().trim().length() > 0) {
                            fieldName = excelField.name().trim();
                        }
                        if (combField != null) {
                            ExcelField combExcelField = myFieldMap.get(combField.getName()).getExcelField();
                            if (combExcelField != null && !org.springframework.util.StringUtils.isEmpty(combExcelField.name())) {
                                fieldName = fieldName + excelField.split() + combExcelField.name();
                            }
                        }
                        fieldWidth = excelField.width();
                        //align = excelField.align();
                        // field width
                        //fieldWidthArr[i] = fieldWidth;

                        // head-style、field-data-style
//                        CellStyle fieldDataStyle = workbook.createCellStyle();
//                        if (align != null) {
//                            fieldDataStyle.setAlignment(align);
//                        }
                        // fieldDataStyleArr[i] = fieldDataStyle;

                        CellStyle headStyle = workbook.createCellStyle();
                        //headStyle.cloneStyleFrom(fieldDataStyle);
                        if (headColorIndex > -1) {
                            headStyle.setFillForegroundColor((short) headColorIndex);
                            headStyle.setFillBackgroundColor((short) headColorIndex);
                            headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                        }
                        // head-field data
                        Cell cellX = headRow.createCell(excelField.index() - 1, Cell.CELL_TYPE_STRING);
                        cellX.setCellStyle(headStyle);
                        cellX.setCellValue(String.valueOf(fieldName));
                    }
                }
            }
        }else{
            throw new RuntimeException(">>>>>>>>>>> excel export error, @ExcelSheet is required");
        }
        return myFieldMap;
    }

    public Workbook init(Class<?> sheetClass){
        Map<String,MyField> myFieldMap = myFieldThreadLocal.get();
        if (myFieldMap == null){
            myFieldMap = parase(sheetClass);
            myFieldThreadLocal.set(myFieldMap);
        }
//        dictMap = dictionaryHelper.getDictMap();
        return getWorkBook();
    }

    public void append(List<?> sheetDataList){
        Workbook workbook = getWorkBook();
        Map<String,MyField> myFieldMap = myFieldThreadLocal.get();
        Sheet sheet = workbook.getSheetAt(0);
        // 填充数据
        // sheet data rows
        for (int dataIndex = 0; dataIndex < sheetDataList.size(); dataIndex++) {
            Object rowData = sheetDataList.get(dataIndex);
            Row rowX = sheet.createRow(start - 1);
            start++;
            for (String key : myFieldMap.keySet()) {
                Field field = myFieldMap.get(key).getField();
                Field combField = myFieldMap.get(key).getCombField();
                ExcelField excelField = myFieldMap.get(key).getExcelField();
//                if (combFieldSet.contains(key)) {
//                    continue;

                if (excelField.index() == 0){
                    continue;
                }
                try {
                    field.setAccessible(true);
                    Object fieldValue = field.get(rowData);
                    String fieldValueString = FieldReflectionUtil.formatValue(field, fieldValue,excelField.format());
                    if (!StringUtils.isEmpty(excelField.dict()) && !StringUtils.isEmpty(fieldValueString)) {
                        if (dictMap.get(excelField.dict()) != null) {
                            String dictValue = dictMap.get(excelField.dict()).get(fieldValueString);
                            if (!StringUtils.isEmpty(dictValue)) {
                                fieldValueString = dictValue;
                            }
                        }
                    }
                    if (combField != null) {
                        //System.out.println(myFieldMap);
                        ExcelField combExcelField = myFieldMap.get(combField.getName()).getExcelField();
                        String combFieldValueString = FieldReflectionUtil.formatValue(combField, combField.get(rowData),combExcelField.format());
                        if (!StringUtils.isEmpty(combExcelField.dict()) && !StringUtils.isEmpty(combFieldValueString)) {
                            if (dictMap.get(combExcelField.dict()) != null) {
                                String dictValue = dictMap.get(combExcelField.dict()).get(combFieldValueString);
                                if (!StringUtils.isEmpty(dictValue)) {
                                    combFieldValueString = dictValue;
                                }
                            }
                        }

                        if (!StringUtils.isEmpty(fieldValueString)) {
                            fieldValueString = fieldValueString + excelField.split() + combFieldValueString;
                        }
                    }
                    Cell cellX = rowX.createCell(excelField.index() - 1, Cell.CELL_TYPE_STRING);
                    cellX.setCellValue(fieldValueString);
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
            }
        }
    }
    // return workbook;

    public void close(){
        workbookThreadLocal.remove();
        myFieldThreadLocal.remove();
    }

    class MyField{
        private String name;
        private ExcelField excelField;
        private Field field;
        private Field combField;
        private boolean combFieldVisible;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ExcelField getExcelField() {
            return excelField;
        }

        public void setExcelField(ExcelField excelField) {
            this.excelField = excelField;
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }

        public Field getCombField() {
            return combField;
        }

        public void setCombField(Field combField) {
            this.combField = combField;
        }

        public boolean isCombFieldVisible() {
            return combFieldVisible;
        }

        public void setCombFieldVisible(boolean combFieldVisible) {
            this.combFieldVisible = combFieldVisible;
        }

    }
}

