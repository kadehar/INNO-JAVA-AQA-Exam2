package com.github.kadehar.inno.exam2.tests;

import com.github.kadehar.inno.exam2.extensions.ReportExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@ExtendWith(ReportExtension.class)
public class MyTests {

    @Test
    @Disabled
    void disabledTest() {}

    @ParameterizedTest
    @MethodSource("provideArgs")
    void successfulTest(int actual, int expected) {
        Assertions.assertEquals(expected, actual);
    }

    private static Stream<Arguments> provideArgs() {
        return Stream.of(
                Arguments.of(2 + 2, 4),
                Arguments.of(3 + 3, 6),
                Arguments.of(1 + 5, 6)
        );
    }
}
