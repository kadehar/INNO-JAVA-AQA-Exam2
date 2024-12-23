package com.github.kadehar.inno.exam2.tests.positive;

import com.github.kadehar.inno.exam2.annotaions.*;
import com.github.kadehar.inno.exam2.data.Nickname;
import com.github.kadehar.inno.exam2.data.PlayerId;
import com.github.kadehar.inno.exam2.data.Points;
import com.github.kadehar.inno.exam2.model.Player;
import com.github.kadehar.inno.exam2.service.PlayerService;
import com.github.kadehar.inno.exam2.service.PlayerServiceImpl;
import com.github.kadehar.inno.exam2.tests.TestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerServiceImplPositiveTests extends TestBase {

    @Test
    @DisplayName("Добавить игрока")
    @NoJsonFile
    void addNewPlayer(Nickname nickname) {
        PlayerService playerService = new PlayerServiceImpl();
        String username = nickname.get();
        Player expectedPlayer = new Player(1, username, 0, true);
        assertThat(expectedPlayer).isNotIn(playerService.getPlayers());
        int playerId = playerService.createPlayer(username);
        Player actualPlayer = playerService.getPlayerById(playerId);
        assertThat(actualPlayer).isEqualTo(expectedPlayer);
    }

    @Test
    @DisplayName("(Добавить игрока) - удалить игрока - проверить отсутствие в списке")
    @WithPlayers(count = 2)
    void removePlayer() {
        PlayerService playerService = new PlayerServiceImpl();
        Player firstPlayer = playerService.getPlayers().stream().findFirst().get();
        playerService.deletePlayer(firstPlayer.getId());
        assertThat(firstPlayer).isNotIn(playerService.getPlayers());
        assertThat(playerService.getPlayers()).hasSize(1);
    }

    @Test
    @DisplayName("(Нет json-файла) добавить игрока")
    @NoJsonFile
    void addNewPlayerWithNoJSONFile(Nickname nickname) {
        PlayerService playerService = new PlayerServiceImpl();
        String username = nickname.get();
        Player player = new Player(1, username, 0, true);
        assertThat(playerService.getPlayers()).isEmpty();
        playerService.createPlayer(username);
        assertThat(player).isIn(playerService.getPlayers());
        assertThat(playerService.getPlayers()).hasSize(1);
    }

    @Test
    @DisplayName("(Есть json-файл) добавить игрока")
    @WithPlayers(count = 2)
    void addNewPlayerWithJSONFile(Nickname nickname) {
        PlayerService playerService = new PlayerServiceImpl();
        String username = nickname.get();
        Player player = new Player(3, username, 0, true);
        assertThat(playerService.getPlayers()).hasSize(2);
        playerService.createPlayer(username);
        assertThat(player).isIn(playerService.getPlayers());
        assertThat(playerService.getPlayers()).hasSize(3);
    }

    @Test
    @DisplayName("Начислить баллы существующему игроку")
    void addPointsToPlayer(@New Player player, @Positive Points points) {
        PlayerService playerService = new PlayerServiceImpl();
        int actualPoints = playerService.addPoints(player.getId(), points.get());
        player = playerService.getPlayerById(player.getId());
        assertThat(actualPoints).isEqualTo(player.getPoints());
    }

    @Test
    @DisplayName("Добавить очков поверх существующих")
    @WithPlayersAndPoints(players = 1)
    void addPointsToPlayerOverExistingPoints(@Exists PlayerId playerId, @Positive Points points) {
        PlayerService playerService = new PlayerServiceImpl();
        int actualPoints = playerService.addPoints(playerId.get(), points.get());
        Player player = playerService.getPlayerById(playerId.get());
        assertThat(actualPoints).isEqualTo(player.getPoints());
    }

    @Test
    @DisplayName("(Добавить игрока) - получить игрока по id")
    @NoJsonFile
    void getPlayerById(Nickname nickname) {
        PlayerService playerService = new PlayerServiceImpl();
        String username = nickname.get();
        int playerId = playerService.createPlayer(username);
        Player player = playerService.getPlayerById(playerId);
        Player expectedPlayer = new Player(1, username, 0, true);
        assertThat(player).isEqualTo(expectedPlayer);
    }

    @Test
    @DisplayName("Проверить корректность сохранения в файл")
    @NoJsonFile
    void validateSaveToFile(Nickname nickname) throws IOException {
        PlayerService playerService = new PlayerServiceImpl();
        String username = nickname.get();
        playerService.createPlayer(username);
        String expectedFileContent = String.format(
                "[{\"id\":1,\"nick\":\"%s\",\"points\":0,\"online\":true}]",
                username
        );
        String actualFileContent = Files.readString(Path.of("./data.json"));
        assertThat(actualFileContent).isEqualTo(expectedFileContent);
    }

    @Test
    @DisplayName("Проверить корректность загрузки json-файла")
    @NoJsonFile
    void validateJSONFileUpload(Nickname nickname) {
        PlayerService playerService = new PlayerServiceImpl();
        String firstPlayerNick = nickname.get();
        String secondPlayerNick = nickname.get();
        int firstPlayerId = playerService.createPlayer(firstPlayerNick);
        int secondPlayerId = playerService.createPlayer(secondPlayerNick);
        assertThat(playerService.getPlayers()).hasSize(2);
        Player firstPlayer = playerService.getPlayerById(firstPlayerId);
        Player secondPlayer = playerService.getPlayerById(secondPlayerId);
        assertThat(firstPlayer.getNick()).isEqualTo(firstPlayerNick);
        assertThat(firstPlayer.getId()).isEqualTo(firstPlayerId);
        assertThat(secondPlayer.getNick()).isEqualTo(secondPlayerNick);
        assertThat(secondPlayer.getId()).isEqualTo(secondPlayerId);
        assertThat(firstPlayer).isIn(playerService.getPlayers());
        assertThat(secondPlayer).isIn(playerService.getPlayers());
    }

    @Test
    @DisplayName("Проверить, что id всегда уникальный")
    @WithPlayers(count = 5)
    void validateThatIdIsUnique(@Exists PlayerId playerId, Nickname nickname) {
        PlayerService playerService = new PlayerServiceImpl();
        assertThat(playerService.getPlayers()).hasSize(5);
        playerService.deletePlayer(playerId.get());
        int newPlayerId = playerService.createPlayer(nickname.get());
        assertThat(newPlayerId).isEqualTo(6);
    }

    @Test
    @DisplayName("(Нет json-файла) запросить список игроков")
    @NoJsonFile
    void getPlayersCountWithoutJSONFile() {
        PlayerService playerService = new PlayerServiceImpl();
        assertThat(playerService.getPlayers()).isEmpty();
    }

    @Test
    @DisplayName("Проверить создание игрока с 15 символами")
    @NoJsonFile
    void createPlayerWithFifteenSymbolsUsernameLength(Nickname nickname) {
        PlayerService playerService = new PlayerServiceImpl();
        int playerId = playerService.createPlayer(nickname.fifteenLength());
        Player player = playerService.getPlayerById(playerId);
        assertThat(player.getNick()).isEqualTo(nickname.fifteenLength());
    }
}
