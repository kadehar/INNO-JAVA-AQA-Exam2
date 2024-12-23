package com.github.kadehar.inno.exam2.resolvers;

import com.github.kadehar.inno.exam2.annotaions.Negative;
import com.github.kadehar.inno.exam2.annotaions.Positive;
import com.github.kadehar.inno.exam2.data.Points;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.concurrent.ThreadLocalRandom;

public class PointsResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == Points.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext,
                                   ExtensionContext extensionContext) throws ParameterResolutionException {
        Negative negative = AnnotationSupport.findAnnotation(
                parameterContext.getParameter(),
                Negative.class
        ).orElse(null);
        Positive positive = AnnotationSupport.findAnnotation(
                parameterContext.getParameter(),
                Positive.class
        ).orElse(null);

        if (negative != null) {
            int points = ThreadLocalRandom.current().nextInt(-100, -50);
            return new Points(points);
        }

        if (positive != null) {
            int points = ThreadLocalRandom.current().nextInt(100, 200);
            return new Points(points);
        }

        return new Points(0);
    }
}
