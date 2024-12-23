package com.github.kadehar.inno.exam2.utils;

import com.github.kadehar.inno.exam2.html.elements.base.Table;

import java.util.ArrayList;
import java.util.List;

public enum ReportData {
    INSTANCE;

    private final List<Table.Row> rows = new ArrayList<>();

    public void add(Table.Row row) {
        rows.add(row);
    }

    public List<Table.Row> getRows() {
        return rows;
    }
}
