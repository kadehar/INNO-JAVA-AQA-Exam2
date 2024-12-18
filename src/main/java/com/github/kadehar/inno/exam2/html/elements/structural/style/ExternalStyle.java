package com.github.kadehar.inno.exam2.html.elements.structural.style;

import com.github.kadehar.inno.exam2.html.elements.Element;

public class ExternalStyle extends Element {
    public ExternalStyle(String cssFile) {
        super();
        this.cssFileLink = "<link rel=\"stylesheet\" href=\"" + cssFile + "\">";
    }
}
