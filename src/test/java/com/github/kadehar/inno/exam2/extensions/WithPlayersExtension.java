package com.github.kadehar.inno.exam2.extensions;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.github.kadehar.inno.exam2.annotaions.WithPlayers;
import com.github.kadehar.inno.exam2.service.PlayerService;
import com.github.kadehar.inno.exam2.service.PlayerServiceImpl;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class WithPlayersExtension implements BeforeEachCallback, AfterEachCallback {

    private final Name fakerName = new Faker(Locale.US).name();

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        WithPlayers annotation = AnnotationSupport.findAnnotation(
                context.getRequiredTestMethod(),
                WithPlayers.class
        ).orElse(AnnotationSupport.findAnnotation(
                context.getRequiredTestClass(),
                WithPlayers.class
        ).orElse(null));

        if (annotation != null) {
            Files.deleteIfExists(Path.of(annotation.file()));
            PlayerService playerService = new PlayerServiceImpl();
            for (int i = 0; i < annotation.count(); i++) {
                playerService.createPlayer(fakerName.username());
            }
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        WithPlayers annotation = AnnotationSupport.findAnnotation(
                context.getRequiredTestMethod(),
                WithPlayers.class
        ).orElse(AnnotationSupport.findAnnotation(
                context.getRequiredTestClass(),
                WithPlayers.class
        ).orElse(null));

        if (annotation != null) {
            Files.deleteIfExists(Path.of(annotation.file()));
        }
    }
}
