package com.github.kadehar.inno.exam2.html.elements;

import java.util.ArrayList;
import java.util.List;

public abstract class Element {
    protected String tagName;
    protected List<Element> children = new ArrayList<>();
    protected String content;
    protected String cssFileLink;
    protected String metaTag;

    protected Element(String tagName) {
        this.tagName = tagName;
    }

    protected Element() {}

    protected Element add(Element element) {
        children.add(element);
        return this;
    }

    public List<Element> children() {
        return children;
    }

    public String content() {
        return content;
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        if (tagName != null) {
            builder.append("<").append(tagName).append(">");
        }
        if (cssFileLink != null) {
            builder.append(cssFileLink);
        }
        if (metaTag != null) {
            builder.append(metaTag);
        }
        if (content != null) {
            builder.append(content);
        }
        for (Element child : children) {
            builder.append("\n").append(child.build());
        }
        if (tagName != null) {
            builder.append("</").append(tagName).append(">");
        }
        return builder.toString();
    }
}
