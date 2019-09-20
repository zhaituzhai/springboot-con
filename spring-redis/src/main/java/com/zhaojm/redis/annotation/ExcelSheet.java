package com.zhaojm.redis.annotation;

import java.lang.annotation.*;

/**
 * 表信息
 *
 * @author tuyl 2018-10-08
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelSheet {

    /**
     * 表名称
     *
     * @return
     */
    String name() default "";

    boolean needRemark() default false;


    /**
     * excel模板文件
     * @return
     */
    String template() default "";

    /**
     * 表头/首行的颜色
     *
     * @return
     */
    //HSSFColor.HSSFColorPredefined headColor() default HSSFColor.HSSFColorPredefined.LIGHT_GREEN;

    int start()default 2;

}

