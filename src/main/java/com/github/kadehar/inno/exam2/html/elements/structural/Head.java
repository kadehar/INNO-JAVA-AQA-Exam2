package com.github.kadehar.inno.exam2.html.elements.structural;

import com.github.kadehar.inno.exam2.html.elements.Element;

public class Head extends Element {

    public Head() {
        super("head");
    }

    public Head title(String title) {
        add(new Title(title));
        return this;
    }
}
