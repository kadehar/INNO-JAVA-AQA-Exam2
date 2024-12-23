package com.github.kadehar.inno.exam2.annotaions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Позволяет указывать какое количество игроков создать и сколько очков им добавить. <br>
 * По умолчанию создаётся 10 игроков с 50 очками у каждого.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface WithPlayersAndPoints {
    int players() default 10;
    int points() default 50;
    String file() default "./data.json";
}
