package com.github.kadehar.inno.exam2.html.elements.structural;

import com.github.kadehar.inno.exam2.html.elements.Element;
import com.github.kadehar.inno.exam2.html.elements.structural.style.ExternalStyle;
import com.github.kadehar.inno.exam2.html.elements.structural.style.Style;

public class Head extends Element {

    public Head() {
        super("head");
    }

    public Head title(String title) {
        add(new Title(title));
        return this;
    }

    public Head externalStyle(String cssFile) {
        add(new ExternalStyle(cssFile));
        return this;
    }

    public Head style(String styleContent) {
        add(new Style(styleContent));
        return this;
    }
}
