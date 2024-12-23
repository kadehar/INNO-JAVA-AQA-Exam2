package com.github.kadehar.inno.exam2.extensions;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.github.kadehar.inno.exam2.annotaions.WithPlayersAndPoints;
import com.github.kadehar.inno.exam2.service.PlayerService;
import com.github.kadehar.inno.exam2.service.PlayerServiceImpl;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class WithPlayersAndPointsExtension implements BeforeEachCallback, AfterEachCallback {

    private final Name fakerName = new Faker(Locale.US).name();

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        WithPlayersAndPoints annotation = AnnotationSupport.findAnnotation(
                context.getRequiredTestMethod(),
                WithPlayersAndPoints.class
        ).orElse(AnnotationSupport.findAnnotation(
                context.getRequiredTestClass(),
                WithPlayersAndPoints.class
        ).orElse(null));

        if (annotation != null) {
            Files.deleteIfExists(Path.of(annotation.file()));
            PlayerService playerService = new PlayerServiceImpl();
            for (int i = 0; i < annotation.players(); i++) {
                int playerId = playerService.createPlayer(fakerName.username());
                playerService.addPoints(playerId, annotation.points());
            }
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        WithPlayersAndPoints annotation = AnnotationSupport.findAnnotation(
                context.getRequiredTestMethod(),
                WithPlayersAndPoints.class
        ).orElse(AnnotationSupport.findAnnotation(
                context.getRequiredTestClass(),
                WithPlayersAndPoints.class
        ).orElse(null));

        if (annotation != null) {
            Files.deleteIfExists(Path.of(annotation.file()));
        }
    }
}
