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

/**
 * <pre>Description: excel导出工具测试
 *  格式，顺序等都是通过 dto上增加ExcelField 和ExcelSheet 注解实现。注解配置参考
 *
 *  支持两种方式：
 *  1、注解   (表头和数据顺序等都是通过注解配置)
 *  2、注解 + excel模板   (适用于复杂表头复杂样式，可以先用excle做好模板上传到服务器,通过注解定义顺序等，然后填充数据)
 *
 * ----------------------------------------------------------------------------------------------
 *  @ExcelSheet(name = "商户列表22",template = "",start = 3)
    class ShopDTO {

    @ExcelField(name = "是否VIP商户", index = 2)
    private boolean vip;

    @ExcelField(name = "商户名称", index = 1, comb = "shopId", split = "*", align = HorizontalAlignment.CENTER)
    private String shopName;

    @ExcelField(name = "分店数量", index = 3, dict = "function_type" ,comb = "addTime", split = "/")
    private short branchNum;

    @ExcelField(name = "商户ID", index = 8)
    private int shopId;

    @ExcelField(name = "浏览人数", index = 4)
    private long visitNum;

    @ExcelField(name = "当月营业额", index = 6)
    private float turnover;

    @ExcelField(name = "历史营业额", index = 5, format = ".##")
    private double totalTurnover;

    @ExcelField(name = "开店时间", index = 7, format = "yyyy-MM-dd HH:mm")
    private Date addTime;

 *---------------------------------------------------------------------------------------------
 *
 *  incdex : 字段顺序 下标是从1开始
 *  fomat  ：格式化暂时主支持 日期和数值格式化，其它有需求再说)
 *  dict :   对应字典sys_dictionary的 enum_type_code,会用enum_name 替换字典值.example   env_type: 51--> 电子发票
 *  comb  和 split :  是用来这种场景  比如输出 ”销方名称/信用代码“  comb 是合并字段 split 是用什么来分割
 * --------------------------------------------------------------------------------------------
 *
 *
 * usage case: 数据量少不分页，一次查询导出
 *
 *      @Autowired
 *      ExcelExportHelp excelExportHelp;
 *
 *
 *      WorkBook workbook = excelExportHelp.init(dto.class);
 *      workbook.append(List<dto> dtolist);
 *      workbook.close();
 *
 *
 * usage case: 数据量大，分页查询
 *      WorkBook workbook = excelExportHelp.init(dto.class);  // init workbook
 *
 *      int pageSize = 1000;
 *      PageHelper.startPage(1, pageSize);
 *      List<ShopDto> shopPageList = shopMapper.pageQueryShop(requestparam);
        PageInfo<ShopDto> pageInfo = new PageInfo<>(shopPageList);

        workbook.append(shopPageList);

        //4、数据大于 1000
        int pages = pageInfo.getPages();
        int nowPageNum = 2;
        while(nowPageNum <= pages){
            PageHelper.startPage(nowPageNum++, pageSize);
            List<ShopDto> shopPageList = shopMapper.pageQueryShop(requestparam);
            workbook.append(shopPageList);
            nowPageNum ++
        }
 *      workbook.close();


 * </pre>
 * <pre>Company: www.szhtxx.com</pre>
 * <pre>date: 2018/10/11 11:02</pre>
 *
 * @author tuyl
 */
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

