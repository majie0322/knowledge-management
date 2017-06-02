package com.management.annotation;

import com.management.constant.DBEnum;
import com.management.constant.EnvEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义（数据库，使用环境，使用表）
 */
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MapperInfo {

    DBEnum dbType() default DBEnum.NEO4J;

    EnvEnum env() default EnvEnum.TEST;

    String refTable() default "";

}
