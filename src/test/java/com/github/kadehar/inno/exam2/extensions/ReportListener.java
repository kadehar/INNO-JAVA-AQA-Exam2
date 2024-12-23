package com.github.kadehar.inno.exam2.extensions;

import com.github.kadehar.inno.exam2.html.elements.base.Heading;
import com.github.kadehar.inno.exam2.html.elements.base.Table;
import com.github.kadehar.inno.exam2.html.elements.structural.Body;
import com.github.kadehar.inno.exam2.html.elements.structural.Head;
import com.github.kadehar.inno.exam2.html.elements.structural.Html;
import com.github.kadehar.inno.exam2.utils.ReportData;
import com.github.kadehar.inno.exam2.utils.ReportUtils;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ReportListener implements TestExecutionListener {

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        try {
            Files.deleteIfExists(Path.of("./report.html"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        List<Table.Row> rows = ReportData.INSTANCE.getRows();
        String htmlString = new Html()
                .head(
                        new Head()
                                .title("Test report")
                                .externalStyle("/src/test/resources/reportStyle.css")
                                .style("body { padding: 4px; }")
                                .meta()
                )
                .body(
                        new Body()
                                .heading(Heading.Level.H1, "Test results")
                                .heading(Heading.Level.H2, "Test count")
                                .table(ReportUtils.createTestCountTable(rows))
                                .paragraph("<strong>Total: </strong>" + rows.size())
                                .heading(Heading.Level.H2, "Test methods")
                                .table(ReportUtils.createTestMethodsTable(rows))
                )
                .build();
        try {
            Files.writeString(
                    Path.of("./report.html"),
                    htmlString,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.SYNC
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
