package com.ql.view;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ql
 *         创建时间:2017/11/28
 *         描述:
 */

@Documented
@Inherited
//该注解可以作用于方法,类与接口
@Target({ElementType.TYPE})
//JVM会读取注解,所以利用反射可以获得注解
@Retention(RetentionPolicy.RUNTIME)
public @interface Layout {
    int value() default -1;

    String title() default "";
}
