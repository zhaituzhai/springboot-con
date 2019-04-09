package com.zhaojm.redis.utils;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.parameter.GenerateParam;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.zhaojm.redis.annotation.ExcelField;
import com.zhaojm.redis.annotation.ExcelSheet;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExcelWriterCon extends ExcelWriter {

    private static Logger logger = LoggerFactory.getLogger(ExcelWriterCon.class);

    private Sheet sheet;

    /**
     * 数据字典值
     */
    Map<String, Map<String, String>> dictMap;
    /**
     * 排序之后的属性列表
     */
    private List<Field> fieldlist;

    private Table table;

    public ExcelWriterCon(OutputStream outputStream, ExcelTypeEnum typeEnum) {
        super(outputStream, typeEnum);
    }

    public ExcelWriterCon(GenerateParam generateParam) {
        super(generateParam);
    }

    public ExcelWriterCon(OutputStream outputStream, ExcelTypeEnum typeEnum, boolean needHead) {
        super(outputStream, typeEnum, needHead);
    }

    public ExcelWriterCon(InputStream templateInputStream, OutputStream outputStream, ExcelTypeEnum typeEnum, boolean needHead) {
        super(templateInputStream, outputStream, typeEnum, needHead);
    }


    public ExcelWriterCon(OutputStream out, Map<String, Map<String, String>> dictMap, Class<?> clazz) {
        super(out, ExcelTypeEnum.XLSX);
        out = new ByteArrayOutputStream();
        this.sheet = setSheet(clazz);
        this.fieldlist = getFieldList(clazz);
        this.dictMap = dictMap;
        this.table = this.setHead();
    }

    //写入数据
    public void appendData(List<?> sheetDataList){
        List<List<String>> listData = new ArrayList<>();
        sheetDataList.forEach(x -> {
            listData.add(dataChange(x));
        });
        this.write0(listData, sheet, table);
    }
    /***
     *
     * @param 数据转换
     * @return
     */
    private List<String>  dataChange(Object obj){
        List<String> rowData = new ArrayList<>();
        fieldlist.forEach(x -> {
            x.setAccessible(true);
            ExcelField excelField = x.getAnnotation(ExcelField.class);
            try {
                Object fieldValue = x.get(obj);
                String fieldValueString = FieldReflectionUtil.formatValue(x, fieldValue,excelField.format());
                if (!StringUtils.isEmpty(excelField.dict()) && !StringUtils.isEmpty(fieldValueString)) {
                    if (dictMap.get(excelField.dict()) != null) {
                        String dictValue = dictMap.get(excelField.dict()).get(fieldValueString);
                        if (!StringUtils.isEmpty(dictValue)) {
                            fieldValueString = dictValue;
                        }
                    }
                }
                rowData.add(fieldValueString);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        });
        return rowData;
    }
    /***
     * 设置头信息
     */
    private Table setHead() {
        Table table = new Table(1);
        List<List<String>> titles = new ArrayList<List<String>>();
        fieldlist.forEach(x -> {
            titles.add(Arrays.asList(x.getAnnotation(ExcelField.class).name()));
        });
        table.setHead(titles);
        return table;
    }

    /**
     * 设置表
     * @param clazz
     * @return
     */
    private static Sheet setSheet(Class<?> clazz){
        Sheet sheet = new Sheet(1,0);
        boolean clzHasAnno = clazz.isAnnotationPresent(ExcelSheet.class);
        if(clzHasAnno){
            ExcelSheet sheetAnno = clazz.getAnnotation(ExcelSheet.class);
            String sheetName = sheetAnno.name().trim();
            if(StringUtils.isNotEmpty(sheetName)){
                sheet.setSheetName(sheetName);
            }else{
                sheet.setSheetName("sheet1");
            }
        }
        return sheet;
    }

    /***
     * 所有属性
     *
     * @param clazz
     * @return
     */
    private static List<Field> getFieldList(Class<?> clazz) {
        List<Field> fieldlist = Arrays.asList(clazz.getDeclaredFields());
        // 筛选
        fieldlist = fieldlist.stream().filter(x -> x.isAnnotationPresent(ExcelField.class))
                .collect(Collectors.toList());
        fieldlist = fieldlist.stream().sorted((x1, x2) -> {
            return Integer.compare(x1.getAnnotation(ExcelField.class).index(),
                    x2.getAnnotation(ExcelField.class).index());
        }).collect(Collectors.toList());
        return fieldlist;
    }
}
