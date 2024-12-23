package com.github.kadehar.inno.exam2.extensions;

import com.github.kadehar.inno.exam2.annotaions.NoJsonFile;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

import java.nio.file.Files;
import java.nio.file.Path;

public class NoJsonFileExtension implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        NoJsonFile annotation = AnnotationSupport.findAnnotation(
                context.getRequiredTestMethod(),
                NoJsonFile.class
        ).orElse(AnnotationSupport.findAnnotation(
                context.getRequiredTestClass(),
                NoJsonFile.class
        ).orElse(null));

        if (annotation != null) {
            Files.deleteIfExists(Path.of(annotation.value()));
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        NoJsonFile annotation = AnnotationSupport.findAnnotation(
                context.getRequiredTestMethod(),
                NoJsonFile.class
        ).orElse(AnnotationSupport.findAnnotation(
                context.getRequiredTestClass(),
                NoJsonFile.class
        ).orElse(null));

        if (annotation != null) {
            Files.deleteIfExists(Path.of(annotation.value()));
        }
    }
}
