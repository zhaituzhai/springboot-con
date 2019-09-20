package com.zhaojm.redis.annotation;

import java.lang.annotation.*;

/**
 * 列属性信息
 *
 *      支持Java对象数据类型：Boolean、String、Short、Integer、Long、Float、Double、Date
 *      支持Excel的Cell类型为：String
 *
 * @author tuyl 2018-10-08
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelField {

    /**
     * 列名称
     *
     * @return
     */
    String name();

    int index();

    /**
     * 列宽 (大于0时生效; 如果不指定列宽，将会自适应调整宽度；)
     *
     * @return
     */
    int width() default 0;

    /**
     * 字典类型
     * @return
     */
    String dict() default "";

    /**
     * 字段类型(
     */

    String comb() default "";

    String split() default "/";


    /**
     * 水平对齐方式
     *
     * @return
     */

    /**
     * 时间格式化，日期类型时生效
     *
     * @return
     */
    //String dateformat() default "yyyy-MM-dd HH:mm:ss";

    /** .## */
    //String numberformat() default  "";


    String format() default "";

    String remark() default "";

}
