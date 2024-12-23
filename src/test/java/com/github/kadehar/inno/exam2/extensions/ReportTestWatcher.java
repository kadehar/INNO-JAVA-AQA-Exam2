package com.github.kadehar.inno.exam2.extensions;

import com.github.kadehar.inno.exam2.html.elements.base.Table;
import com.github.kadehar.inno.exam2.utils.ReportData;
import com.github.kadehar.inno.exam2.utils.ReportUtils;
import com.github.kadehar.inno.exam2.utils.TestStatus;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

public class ReportTestWatcher implements TestWatcher {

    @Override
    public void testSuccessful(ExtensionContext context) {
        addRow(context, TestStatus.Passed);
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        addRow(context, TestStatus.Disabled);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        addRow(context, TestStatus.Aborted);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        addRow(context, TestStatus.Failed);
    }

    private void addRow(ExtensionContext context, TestStatus status) {
        Table.Row row = createRows(context, status);
        ReportData.INSTANCE.add(row);
    }

    private Table.Row createRows(ExtensionContext context, TestStatus status) {
        return new Table.Row().cell(ReportUtils.testName(context))
                .cell(status.toString());
    }
}
