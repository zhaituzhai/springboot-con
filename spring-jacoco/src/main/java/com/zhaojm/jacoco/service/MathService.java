package com.zhaojm.jacoco.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MathService {
    public static Logger logger = LoggerFactory.getLogger(MathService.class);
    
    public void logTest(String mathString){
        logger.error(mathString);
    }

}
