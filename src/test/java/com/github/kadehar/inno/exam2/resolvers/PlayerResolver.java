package com.github.kadehar.inno.exam2.resolvers;

import com.github.javafaker.Faker;
import com.github.kadehar.inno.exam2.annotaions.New;
import com.github.kadehar.inno.exam2.model.Player;
import com.github.kadehar.inno.exam2.service.PlayerService;
import com.github.kadehar.inno.exam2.service.PlayerServiceImpl;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.Locale;

public class PlayerResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == Player.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext,
                                   ExtensionContext extensionContext) throws ParameterResolutionException {
        New newPlayer = AnnotationSupport.findAnnotation(
                parameterContext.getParameter(),
                New.class
        ).orElse(null);
        if (newPlayer != null) {
            PlayerService playerService = new PlayerServiceImpl();
            int playerId = playerService.createPlayer(new Faker(Locale.US).name().username());
            return playerService.getPlayerById(playerId);
        }

        return new Player(0, "", 0, false);
    }
}
