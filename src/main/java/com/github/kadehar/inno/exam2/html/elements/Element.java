package com.github.kadehar.inno.exam2.html.elements;

import java.util.ArrayList;
import java.util.List;

public abstract class Element {
    protected String tagName;
    protected List<Element> children = new ArrayList<>();
    protected String content;

    protected Element(String tagName) {
        this.tagName = tagName;
    }

    protected Element add(Element element) {
        children.add(element);
        return this;
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("<").append(tagName).append(">");
        if (content != null) {
            builder.append(content);
        }
        for (Element child : children) {
            builder.append("\n").append(child.build());
        }
        builder.append("</").append(tagName).append(">");
        return builder.toString();
    }
}
