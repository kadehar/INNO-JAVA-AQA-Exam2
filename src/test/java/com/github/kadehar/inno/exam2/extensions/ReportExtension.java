package com.github.kadehar.inno.exam2.extensions;

import com.github.kadehar.inno.exam2.html.elements.base.Heading;
import com.github.kadehar.inno.exam2.html.elements.base.Table;
import com.github.kadehar.inno.exam2.html.elements.structural.Body;
import com.github.kadehar.inno.exam2.html.elements.structural.Head;
import com.github.kadehar.inno.exam2.html.elements.structural.Html;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReportExtension implements TestWatcher, BeforeAllCallback, AfterAllCallback {

    private final List<Table.Row> rows = new ArrayList<>();

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        Files.deleteIfExists(Path.of("./report.html"));
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        Table.Row row = createRows(context, TestStatus.Passed);
        rows.add(row);
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        Table.Row row = createRows(context, TestStatus.Disabled);
        rows.add(row);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        Table.Row row = createRows(context, TestStatus.Aborted);
        rows.add(row);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        Table.Row row = createRows(context, TestStatus.Failed);
        rows.add(row);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        String htmlString = new Html()
                .head(
                        new Head()
                                .title("Test report")
                                .externalStyle("/src/test/resources/reportStyle.css")
                                .style("body { padding: 4px; }")
                )
                .body(
                        new Body()
                                .heading(Heading.Level.H1, "Test results")
                                .heading(Heading.Level.H2, "Test methods")
                                .table(ReportData.createTestMethodsTable(rows))
                                .heading(Heading.Level.H2, "Test count")
                                .table(ReportData.createTestCountTable(rows))
                                .paragraph("<strong>Total: </strong>" + rows.size())
                )
                .build();
        Files.writeString(Path.of("./report.html"), htmlString, StandardOpenOption.CREATE);
    }

    private Table.Row createRows(ExtensionContext context, TestStatus status) {
        return new Table.Row().cell(ReportData.testName(context))
                .cell(status.toString());
    }
}
