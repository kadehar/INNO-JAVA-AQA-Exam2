package com.github.kadehar.inno.exam2.annotaions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Позволяет указывать какое количество игроков создать. <br>
 * По умолчанию создаётся 10 игроков.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface WithPlayers {
    int count() default 10;
    String file() default "./data.json";
}
