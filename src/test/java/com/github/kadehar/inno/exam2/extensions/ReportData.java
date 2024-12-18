package com.github.kadehar.inno.exam2.extensions;

import com.github.kadehar.inno.exam2.html.elements.base.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.platform.commons.support.AnnotationSupport;

import java.lang.reflect.Method;
import java.util.List;

public class ReportData {
    public static Table createTestMethodsTable(List<Table.Row> rows) {
        Table table = new Table();
        table.row(new Table.Row().header("Method").header("Status"));
        for (Table.Row row : rows) {
            table.row(row);
        }
        return table;
    }

    public static Table createTestCountTable(List<Table.Row> rows) {
        Table table = new Table();
        table.row(new Table.Row().header("Status").header("Count"));
        table.row(new Table.Row()
                .cell(TestStatus.Passed.toString())
                .cell(countTestsByStatus(rows, TestStatus.Passed)));
        table.row(new Table.Row()
                .cell(TestStatus.Disabled.toString())
                .cell(countTestsByStatus(rows, TestStatus.Disabled)));
        table.row(new Table.Row()
                .cell(TestStatus.Aborted.toString())
                .cell(countTestsByStatus(rows, TestStatus.Aborted)));
        table.row(new Table.Row()
                .cell(TestStatus.Failed.toString())
                .cell(countTestsByStatus(rows, TestStatus.Failed)));
        return table;
    }

    public static String testName(ExtensionContext context) {
        DisplayName displayName = AnnotationSupport.findAnnotation(
                context.getRequiredTestMethod(),
                DisplayName.class
        ).orElse(AnnotationSupport.findAnnotation(
                context.getRequiredTestClass(),
                DisplayName.class
        ).orElse(null));

        ParameterizedTest parameterizedTest = AnnotationSupport.findAnnotation(
                context.getRequiredTestMethod(),
                ParameterizedTest.class
        ).orElse(null);

        if (displayName != null) {
            return displayName.value();
        }

        if (parameterizedTest != null && !parameterizedTest.name().equals("{default_display_name}")) {
            return parameterizedTest.name();
        }

        return context
                .getTestMethod()
                .map(Method::getName)
                .orElse("unknown");
    }

    private static String countTestsByStatus(List<Table.Row> rows, TestStatus status) {
        long count = 0L;
        for (Table.Row row : rows) {
            count += row.children()
                    .stream()
                    .filter(child -> child.content().equals(status.toString()))
                    .count();
        }
        return String.valueOf(count);
    }
}
