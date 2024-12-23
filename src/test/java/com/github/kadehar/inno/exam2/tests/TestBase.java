package com.github.kadehar.inno.exam2.tests;

import com.github.kadehar.inno.exam2.extensions.NoJsonFileExtension;
import com.github.kadehar.inno.exam2.extensions.ReportTestWatcher;
import com.github.kadehar.inno.exam2.extensions.WithPlayersAndPointsExtension;
import com.github.kadehar.inno.exam2.extensions.WithPlayersExtension;
import com.github.kadehar.inno.exam2.resolvers.PlayerIdResolver;
import com.github.kadehar.inno.exam2.resolvers.PlayerResolver;
import com.github.kadehar.inno.exam2.resolvers.PointsResolver;
import com.github.kadehar.inno.exam2.resolvers.UsernameResolver;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({
        ReportTestWatcher.class,
        NoJsonFileExtension.class,
        WithPlayersExtension.class,
        WithPlayersAndPointsExtension.class,
        UsernameResolver.class,
        PlayerIdResolver.class,
        PointsResolver.class,
        PlayerResolver.class
})
public class TestBase {
}
