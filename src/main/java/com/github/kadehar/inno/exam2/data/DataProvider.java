package com.github.kadehar.inno.exam2.data;

import com.github.kadehar.inno.exam2.model.Player;

import java.io.IOException;
import java.util.Collection;

public interface DataProvider {

    void save(Collection<Player> players) throws IOException;

    Collection<Player> load() throws IOException;
}
