package com.github.kadehar.inno.exam2.html.elements.structural;

import com.github.kadehar.inno.exam2.html.elements.Element;

public class Html extends Element {

    public Html() {
        super("html");
    }

    public Html head(Head head) {
        add(head);
        return this;
    }

    public Html body(Body body) {
        add(body);
        return this;
    }
}
