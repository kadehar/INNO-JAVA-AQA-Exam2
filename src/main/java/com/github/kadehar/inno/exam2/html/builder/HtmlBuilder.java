package com.github.kadehar.inno.exam2.html.builder;

import com.github.kadehar.inno.exam2.html.elements.structural.Body;
import com.github.kadehar.inno.exam2.html.elements.structural.Head;
import com.github.kadehar.inno.exam2.html.elements.structural.Html;

import java.util.function.Supplier;

public class HtmlBuilder {
    public static Html html(Supplier<Html> supplier) {
        return supplier.get();
    }

    public static Head head(Supplier<Head> supplier) {
        return supplier.get();
    }

    public static Body body(Supplier<Body> supplier) {
        return supplier.get();
    }
}
