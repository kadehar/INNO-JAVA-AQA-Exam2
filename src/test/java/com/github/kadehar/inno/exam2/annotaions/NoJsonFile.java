package com.github.kadehar.inno.exam2.annotaions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Позволяет удалить json файл по указанному пути. <br>
 * По умолчанию ищет файл <code>data.json</code>.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface NoJsonFile {
    String value() default "./data.json";
}