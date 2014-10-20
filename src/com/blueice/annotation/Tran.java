package com.blueice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标明要使用事务管理的注解类。
 *
 */
@Retention(RetentionPolicy.RUNTIME) //作用于运行时。
@Target(ElementType.METHOD)  //作用目标为方法。
public @interface Tran {

}
