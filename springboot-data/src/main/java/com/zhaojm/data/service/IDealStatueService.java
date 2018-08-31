package com.zhaojm.data.service;

import java.io.FileInputStream;

public interface IDealStatueService {

    int toParse(FileInputStream xmlByte,int i);
    
    String dataToMap();

}
