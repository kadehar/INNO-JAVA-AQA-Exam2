package com.github.kadehar.inno.exam2.extensions;

import com.github.kadehar.inno.exam2.html.builder.HtmlBuilder;
import com.github.kadehar.inno.exam2.html.elements.base.Heading;
import com.github.kadehar.inno.exam2.html.elements.base.Table;
import com.github.kadehar.inno.exam2.html.elements.structural.Body;
import com.github.kadehar.inno.exam2.html.elements.structural.Head;
import com.github.kadehar.inno.exam2.html.elements.structural.Html;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReportExtension implements TestWatcher, BeforeAllCallback, AfterAllCallback {

    private final List<Table.Row> rows = new ArrayList<>();

    @Override
    public void testSuccessful(ExtensionContext context) {
        Table.Row row = createRows(context, "PASSED");
        rows.add(row);
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        Table.Row row = createRows(context, "DISABLED");
        rows.add(row);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        Table.Row row = createRows(context, "ABORTED");
        rows.add(row);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        Table.Row row = createRows(context, "FAILED");
        rows.add(row);
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        Files.deleteIfExists(Path.of("./report.html"));
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        String htmlString = HtmlBuilder
                .html(() -> new Html()
                        .head(HtmlBuilder.head(() -> new Head().title("Test report")))
                        .body(HtmlBuilder.body(() -> new Body()
                                        .heading(Heading.Level.H1, "Test results")
                                        .table(createTable())
                                )
                        )
                )
                .build();
        Files.writeString(Path.of("./report.html"), htmlString, StandardOpenOption.CREATE);
    }

    private Table.Row createRows(ExtensionContext context, String status) {
        return new Table.Row().cell(context
                        .getTestMethod()
                        .map(Method::getName)
                        .orElse(context.getDisplayName())
                )
                .cell(status);
    }

    private Table createTable() {
        Table table = new Table();
        table.row(new Table.Row().header("Method").header("Status"));
        for (Table.Row row : rows) {
            table.row(row);
        }
        return table;
    }
}
