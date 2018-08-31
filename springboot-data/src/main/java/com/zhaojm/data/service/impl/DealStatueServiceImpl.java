package com.zhaojm.data.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zhaojm.data.bean.DealStatueTableDTO;
import com.zhaojm.data.mapper.IDealStatueTableMapper;
import com.zhaojm.data.service.IDealStatueService;

@Service
public class DealStatueServiceImpl implements IDealStatueService {

    @Autowired
    IDealStatueTableMapper dealStatueTableMapper;
    
    @Override
    public String dataToMap() {
        Map<String, Map> totalMap = new HashMap<>();
        Map<String, List<Map<String, Boolean>>> PERMISSION_MAP_1 = new HashMap<String, List<Map<String, Boolean>>>();
        Map<String, List<Map<String, Boolean>>> PERMISSION_MAP_2 = new HashMap<String, List<Map<String, Boolean>>>();
        Map<String, Boolean> OPERATION_MAP_1 = null;
//        Map<String, Boolean> OPERATION_MAP_2 = new HashMap<String, Boolean>();
        
        List<DealStatueTableDTO> dealStauteMap1 = dealStatueTableMapper.selectOpreat();
        //PERMISSION_MAP = new HashMap<String, Map<String, Boolean>>();
        for (DealStatueTableDTO test1 : dealStauteMap1) {
            if(test1.getType() == 0){
                PERMISSION_MAP_1.put(test1.getDealStatues(), new ArrayList<>());
            }
            if(test1.getType() == 1){
                PERMISSION_MAP_2.put(test1.getDealStatues(), new ArrayList<>());
            }
            
        }
        List<DealStatueTableDTO> dealStauteList = dealStatueTableMapper.selectAll();
        
        for (DealStatueTableDTO dealStaute : dealStauteList) {
            OPERATION_MAP_1 = new HashMap<String, Boolean>();
            if(dealStaute.getType() == 0){
                OPERATION_MAP_1.put(dealStaute.getDealName(), dealStaute.getDeal().equalsIgnoreCase("TRUE") ? true :false);
                PERMISSION_MAP_1.get(dealStaute.getDealStatues()).add(OPERATION_MAP_1);
            }
            if(dealStaute.getType() == 1){
                OPERATION_MAP_1.put(dealStaute.getDealName(), dealStaute.getDeal().equalsIgnoreCase("TRUE") ? true :false);
                PERMISSION_MAP_2.get(dealStaute.getDealStatues()).add(OPERATION_MAP_1);
            }
        }
        totalMap.put("positive_bill", PERMISSION_MAP_1);
        totalMap.put("negative_bill", PERMISSION_MAP_2);
        
        return JSON.toJSONString(totalMap);
    }
    
    @Override
    public int toParse(FileInputStream xmlByte, int type) {
        int total = 0;
        List<DealStatueTableDTO> importList = doParse(xmlByte, type);
        for (DealStatueTableDTO medicine : importList) {
            total += dealStatueTableMapper.insertSelective(medicine);
        }
        return total;
    }

    private List<DealStatueTableDTO> doParse(FileInputStream xmlByte, int type) {
        Workbook workbook = getWorkBook(xmlByte);
        List<DealStatueTableDTO> list = new ArrayList<DealStatueTableDTO>();
        for (int i = 0; i < 1; i++) {
            Sheet sheet0 = workbook.getSheetAt(i);
            list.addAll(parseSheet(sheet0, 2, type));
        }
        return list;
    }

    private List<DealStatueTableDTO> parseSheet(Sheet sheet, int starRow,int type) {
        List<DealStatueTableDTO> list = new ArrayList<DealStatueTableDTO>();
        DealStatueTableDTO dealStatue = null;
        if (null == sheet)
            return list;
        // 循环解析
        int rowNum = sheet.getLastRowNum();
        for (int i = starRow; i <= rowNum; i++) {
            Row hss = sheet.getRow(i);
            if (null != hss && hss.getPhysicalNumberOfCells() > 0) { // 去除空行判断
                // https://www.tapd.cn/20490491/bugtrace/bugs/view?bug_id=1120490491001005787

                // DETAIL EDIT MERGE SPLIT REDUCTION SUBMIT ABOLITION DELETE
                if(type == 0){
                    for (int j = 3; j < 11; j++) {
                        dealStatue = new DealStatueTableDTO();
                        dealStatue.setType(type);
                        dealStatue.setDealStatues("0_"+String.valueOf(hss.getCell(2)));
                        if (j == 3) {
                            dealStatue.setDealName("DETAIL");
                            dealStatue.setDeal(String.valueOf(hss.getCell(j)));
                        }
                        if (j == 4) {
                            dealStatue.setDealName("EDIT");
                            dealStatue.setDeal(String.valueOf(hss.getCell(j)));
                        }
                        if (j == 5) {
                            dealStatue.setDealName("MERGE");
                            dealStatue.setDeal(String.valueOf(hss.getCell(j)));
                        }
                        if (j == 6) {
                            dealStatue.setDealName("SPLIT");
                            dealStatue.setDeal(String.valueOf(hss.getCell(j)));
                        }
                        if (j == 7) {
                            dealStatue.setDealName("REDUCTION");
                            dealStatue.setDeal(String.valueOf(hss.getCell(j)));
                        }
                        if (j == 8) {
                            dealStatue.setDealName("SUBMIT");
                            dealStatue.setDeal(String.valueOf(hss.getCell(j)));
                        }
                        if (j == 9) {
                            dealStatue.setDealName("ABOLITION");
                            dealStatue.setDeal(String.valueOf(hss.getCell(j)));
                        }
                        if (j == 10) {
                            dealStatue.setDealName("DELETE");
                            dealStatue.setDeal(String.valueOf(hss.getCell(j)));
                        }
                        list.add(dealStatue);
                    }
                }
                if(type == 1){
                    for (int j = 3; j < 8; j++) {
                        dealStatue = new DealStatueTableDTO();
                        dealStatue.setType(type);
                        dealStatue.setDealStatues("1_"+String.valueOf(hss.getCell(2)));
                     // DETAIL EDIT MERGE SPLIT REDUCTION SUBMIT ABOLITION DELETE
                        if (j == 3) {
                            dealStatue.setDealName("DETAIL");
                            dealStatue.setDeal(String.valueOf(hss.getCell(j)));
                        }
                        if (j == 4) {
                            dealStatue.setDealName("EDIT_POINT");
                            dealStatue.setDeal(String.valueOf(hss.getCell(j)));
                        }
                        if (j == 5) {
                            dealStatue.setDealName("REDUCTION");
                            dealStatue.setDeal(String.valueOf(hss.getCell(j)));
                        }
                        if (j == 6) {
                            dealStatue.setDealName("SUBMIT");
                            dealStatue.setDeal(String.valueOf(hss.getCell(j)));
                        }
                        if (j == 7) {
                            dealStatue.setDealName("DELETE");
                            dealStatue.setDeal(String.valueOf(hss.getCell(j)));
                        }
                        list.add(dealStatue);
                    }
                }
            } else {
                break;
            }
        }
        return list;
    }

    public Workbook getWorkBook(InputStream inputStream) {

        Workbook workbook = null;
        try {
            if (!inputStream.markSupported()) {
                inputStream = new PushbackInputStream(inputStream, 8);
            }
            if (POIFSFileSystem.hasPOIFSHeader(inputStream)) {// 2003
                workbook = new HSSFWorkbook(inputStream);
            } else if (POIXMLDocument.hasOOXMLHeader(inputStream)) {// 2007
                workbook = new XSSFWorkbook(inputStream);
            }
            return workbook;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
