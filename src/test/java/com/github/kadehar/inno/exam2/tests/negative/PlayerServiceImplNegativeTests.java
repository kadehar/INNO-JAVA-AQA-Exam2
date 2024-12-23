package com.github.kadehar.inno.exam2.tests.negative;

import com.github.kadehar.inno.exam2.annotaions.*;
import com.github.kadehar.inno.exam2.data.Nickname;
import com.github.kadehar.inno.exam2.data.PlayerId;
import com.github.kadehar.inno.exam2.data.Points;
import com.github.kadehar.inno.exam2.model.Player;
import com.github.kadehar.inno.exam2.service.PlayerService;
import com.github.kadehar.inno.exam2.service.PlayerServiceImpl;
import com.github.kadehar.inno.exam2.tests.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerServiceImplNegativeTests extends TestBase {

    @Test
    @DisplayName("Удалить игрока, которого нет")
    @WithPlayers(count = 8)
    void removeNoneExistingPlayerById(@NotExists PlayerId playerId) {
        PlayerService playerService = new PlayerServiceImpl();
        NoSuchElementException exception = Assertions.assertThrows(
                NoSuchElementException.class,
                () -> playerService.deletePlayer(playerId.get())
        );
        String expectedErrorMessage = "No such user: " + playerId.get();
        assertThat(exception.getMessage()).isEqualTo(expectedErrorMessage);
    }

    @Test
    @DisplayName("Создать дубликат (имя уже занято)")
    @NoJsonFile
    void createUserWithExistingNickname(Nickname nickname) {
        PlayerService playerService = new PlayerServiceImpl();
        String username = nickname.get();
        playerService.createPlayer(username);
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> playerService.createPlayer(username)
        );
        String expectedErrorMessage = "Nickname is already in use: " + username;
        assertThat(exception.getMessage()).isEqualTo(expectedErrorMessage);
    }

    @Test
    @DisplayName("Получить игрока по id, которого нет")
    @WithPlayers(count = 8)
    void getNoneExistingPlayerById(@NotExists PlayerId playerId) {
        PlayerService playerService = new PlayerServiceImpl();
        NoSuchElementException exception = Assertions.assertThrows(
                NoSuchElementException.class,
                () -> playerService.getPlayerById(playerId.get())
        );
        String expectedErrorMessage = "No such user: " + playerId.get();
        assertThat(exception.getMessage()).isEqualTo(expectedErrorMessage);
    }

    @Test
    @DisplayName("Сохранить игрока с пустым ником")
    @NoJsonFile
    /* Метод создания игрока должен возвращать ошибку, если nickname пустой. */
    void createPlayerWithEmptyNickname(Nickname nickname) {
        PlayerService playerService = new PlayerServiceImpl();
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> playerService.createPlayer(nickname.empty())
        );
        String expectedErrorMessage = "Nickname can not be empty!";
        assertThat(exception.getMessage()).isEqualTo(expectedErrorMessage);
    }

    @Test
    @DisplayName("Начислить отрицательное число очков")
    @WithPlayers(count = 1)
    /* Метод начисления очков должен возвращать ошибку, если количество очков меньше 0. */
    void addNegativeAmountOfPointsToPlayer(@Negative Points points) {
        PlayerService playerService = new PlayerServiceImpl();
        Player player = playerService.getPlayers().stream().findFirst().get();
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> playerService.addPoints(player.getId(), points.get())
        );
        String expectedErrorMessage = "Can not add points less than zero!";
        assertThat(exception.getMessage()).isEqualTo(expectedErrorMessage);
    }

    @Test
    @DisplayName("Накинуть очков игроку, которого нет")
    @WithPlayers(count = 3)
    void addPointsToNonExistingPlayer(@NotExists PlayerId playerId, @Positive Points points) {
        PlayerService playerService = new PlayerServiceImpl();
        NoSuchElementException exception = Assertions.assertThrows(
                NoSuchElementException.class,
                () -> playerService.addPoints(playerId.get(), points.get())
        );
        String expectedErrorMessage = "No such user: " + playerId.get();
        assertThat(exception.getMessage()).isEqualTo(expectedErrorMessage);
    }

    @Test
    @DisplayName("Проверить создание игрока с 16 символами")
    @NoJsonFile
        /* Метод создания игрока должен возвращать ошибку, если длина nickname больше 15. */
    void createPlayerWithSixteenSymbolsUsernameLength(Nickname nickname) {
        PlayerService playerService = new PlayerServiceImpl();
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> playerService.createPlayer(nickname.sixteenLength())
        );
        String expectedErrorMessage = "Nickname can not be larger than 15 symbols!";
        assertThat(exception.getMessage()).isEqualTo(expectedErrorMessage);
    }
}
