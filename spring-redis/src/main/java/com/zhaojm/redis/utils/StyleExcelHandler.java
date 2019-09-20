package com.zhaojm.redis.utils;

import com.alibaba.excel.event.WriteHandler;
import org.apache.poi.ss.usermodel.*;

public class StyleExcelHandler implements WriteHandler {

    @Override
    public void sheet(int i, Sheet sheet) {
    }

    @Override
    public void row(int i, Row row) {
    }

    @Override
    public void cell(int i, Cell cell) {
        // 从第二行开始设置格式，第一行是表头
        Workbook workbook = cell.getSheet().getWorkbook();
        if (cell.getRowIndex() == 1) {
            CellStyle cellStyle1 = workbook.createCellStyle();
            cellStyle1.setWrapText(true);
            cell.setCellStyle(cellStyle1);
        }else if (cell.getRowIndex() == 2) {
            CellStyle cellStyle2 = workbook.createCellStyle();
            cellStyle2.getFontIndex();
            Font font = workbook.createFont();
            font.setBold(true);
            cellStyle2.setFont(font);
            cell.setCellStyle(cellStyle2);
        }
    }

    /**
     * 实际中如果直接获取原单元格的样式进行修改, 最后发现是改了整行的样式, 因此这里是新建一个样* 式
     */
    private CellStyle createStyle(Workbook workbook) {
        // https://blog.csdn.net/fly_captain/article/details/81357477
        CellStyle cellStyle = workbook.createCellStyle();
//        // 下边框
//        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
//        // 左边框
//        cellStyle.setBorderLeft(BorderStyle.MEDIUM);
//        // 上边框
//        cellStyle.setBorderTop(BorderStyle.MEDIUM);
//        // 右边框
//        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        // 水平对齐方式
//        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 垂直对齐方式
//        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

//        cellStyle.setWrapText(true);
        return cellStyle;
    }
}
