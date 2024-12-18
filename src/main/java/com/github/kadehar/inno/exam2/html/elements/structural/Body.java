package com.github.kadehar.inno.exam2.html.elements.structural;

import com.github.kadehar.inno.exam2.html.elements.Element;
import com.github.kadehar.inno.exam2.html.elements.base.Heading;
import com.github.kadehar.inno.exam2.html.elements.base.Paragraph;
import com.github.kadehar.inno.exam2.html.elements.base.Table;

public class Body extends Element {

    public Body() {
        super("body");
    }

    public Body heading(Heading.Level level, String content) {
        add(new Heading(level, content));
        return this;
    }

    public Body paragraph(String content) {
        add(new Paragraph(content));
        return this;
    }

    public Body table(Table table) {
        add(table);
        return this;
    }
}
