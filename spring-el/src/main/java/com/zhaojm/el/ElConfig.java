package com.zhaojm.el;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan("com.zhaojm.el")
@PropertySource("classpath:com/zhaojm/el/test.properties")
public class ElConfig {
    
    @Value("I LOVE YOU XUAN")
    private String normal;
    
    @Value("#{systemProperties['os.name']}")
    private String osName;
    
    @Value("#{ T(java.lang.Math).random() * 100.0 }")
    private double randomNumber;
    
    @Value("#{demoService.another}")
    private String fromAnother;
    
    @Value("classpath:com/zhaojm/el/test.txt")
    private Resource testFile;
    
    @Value("http://baidu.com")
    private Resource testUrl;
    
    @Value("${book.name}")
    private String bookName;
    
    @Autowired
    private Environment environment;
    
    public static PropertySourcesPlaceholderConfigurer propertyConfigure() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public void outputResource() {
        try {
            System.out.println(normal);
            System.out.println(osName);
            System.out.println(randomNumber);
            System.out.println(fromAnother);
            System.out.println(IOUtils.toString(testFile.getInputStream()));
            System.out.println(IOUtils.toString(testUrl.getInputStream()));
            System.out.println(bookName);
            System.out.println(environment.getProperty("book.author"));
            
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
