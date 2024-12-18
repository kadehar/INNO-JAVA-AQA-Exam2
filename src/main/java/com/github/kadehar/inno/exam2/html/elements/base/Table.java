package com.github.kadehar.inno.exam2.html.elements.base;

import com.github.kadehar.inno.exam2.html.elements.Element;

public class Table extends Element {

    public Table() {
        super("table");
    }

    public Table row(Row row) {
        add(row);
        return this;
    }

    public static class Row extends Element {

        public Row() {
            super("tr");
        }

        public Row header(String content) {
            add(new Header(content));
            return this;
        }

        public Row cell(String content) {
            add(new Cell(content));
            return this;
        }
    }

    public static class Header extends Element {

        public Header(String content) {
            super("th");
            this.content = content;
        }
    }

    public static class Cell extends Element {

        public Cell(String content) {
            super("td");
            this.content = content;
        }
    }
}