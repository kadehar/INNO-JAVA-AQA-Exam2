package com.github.kadehar.inno.exam2.resolvers;

import com.github.kadehar.inno.exam2.annotaions.Exists;
import com.github.kadehar.inno.exam2.annotaions.NotExists;
import com.github.kadehar.inno.exam2.data.PlayerId;
import com.github.kadehar.inno.exam2.model.Player;
import com.github.kadehar.inno.exam2.service.PlayerService;
import com.github.kadehar.inno.exam2.service.PlayerServiceImpl;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerIdResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == PlayerId.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext,
                                   ExtensionContext extensionContext) throws ParameterResolutionException {
        NotExists notExists = AnnotationSupport.findAnnotation(
                parameterContext.getParameter(),
                NotExists.class
        ).orElse(null);
        Exists exists = AnnotationSupport.findAnnotation(
                parameterContext.getParameter(),
                Exists.class
        ).orElse(null);

        if (notExists != null) {
            return new PlayerId(10);
        }

        if (exists != null) {
            PlayerService playerService = new PlayerServiceImpl();
            List<Integer> ids = playerService.getPlayers().stream().map(Player::getId).toList();
            if (ids.size() == 1) {
                return new PlayerId(ids.getFirst());
            } else {
                int playerId = ids.get(ThreadLocalRandom.current().nextInt(0, ids.size() - 1));
                return new PlayerId(playerId);
            }
        }

        return new PlayerId(0);
    }
}
