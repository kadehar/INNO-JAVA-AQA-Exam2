package com.github.kadehar.inno.exam2.html.elements.base;

import com.github.kadehar.inno.exam2.html.elements.Element;

public class Heading extends Element {

    public Heading(Level level, String content) {
        super(level.tag);
        this.content = content;
    }

    public enum Level {
        H1("h1"),
        H2("h2"),
        H3("h3"),
        H4("h4"),
        H5("h5"),
        H6("h6");

        private final String tag;

        Level(String tag) {
            this.tag = tag;
        }
    }
}
