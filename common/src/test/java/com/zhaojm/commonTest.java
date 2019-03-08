package com.zhaojm;

import org.apache.commons.lang3.StringUtils;

public class commonTest {

    public static void main(String[] args) {
        String path = "com.zhaojm.redis.controller.UserAccountController";
        System.out.println(classPathToUrl(path));
    }

    private static String classPathToUrl(String path) {
        String typeValue = path.replaceAll("^com\\.zhaojm\\.[a-zA-z]+\\.|[c|C]ontroller[\\.]*", "");
        //3.类名称首字母转小写
        String[] typeValues = typeValue.split("\\.");
        String lastStr = typeValues[typeValues.length-1];
        typeValues[typeValues.length-1] = lastStr.substring(0, 1).toLowerCase() + lastStr.substring(1);
        return StringUtils.join(typeValues, "/");
    }
}
