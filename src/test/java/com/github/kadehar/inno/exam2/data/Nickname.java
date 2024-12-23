package com.github.kadehar.inno.exam2.data;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;

import java.util.Locale;

public class Nickname {
    private final Name username = new Faker(Locale.US).name();

    public String get() {
        return username.username();
    }

    public String empty() {
        return "";
    }

    public String sixteenLength() {
        return "boroboroboroboro";
    }

    public String fifteenLength() {
        return "boroboroborobor";
    }
}
