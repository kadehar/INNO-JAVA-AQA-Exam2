package com.github.kadehar.inno.exam2.service;

import com.github.kadehar.inno.exam2.model.Player;

import java.util.Collection;

public interface PlayerService {
    // получить игрока по id
    Player getPlayerById(int id);

    // получить список игроков
    Collection<Player> getPlayers();

    // создать игрока (возвращает id нового игрока)
    int createPlayer(String nickname);

    // удалить игрока по id'шнику, вернет удаленного игрока
    Player deletePlayer(int id);

    // Добавить очков игроку. Возвращает обновленный счет
    int addPoints(int playerId, int points);
}
