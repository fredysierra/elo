package io.fysus.elo.source;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.fysus.elo.domain.Player;
import io.fysus.elo.error.LoadingPlayersException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PlayerFileRepositoryTest {

    @Test
    void testGetPlayerList() {
        PlayerFileRepository playerFileRepository = new PlayerFileRepository("src/test/resources/players.txt");

        List<Player> players = playerFileRepository.getPlayerList();

        assertFalse(players.isEmpty());

        assertAll(() -> assertEquals("0", players.get(0).getId()),
            () -> assertEquals("Wesley", players.get(0).getName()));
    }

    @Test
    void testGetPlayerListErrorAccessingFile() {
        PlayerFileRepository playerFileRepository = new PlayerFileRepository("noExistingFile.txt");
        var exception = Assertions
            .assertThrows(LoadingPlayersException.class, playerFileRepository::getPlayerList);
        assertEquals("Problem reading players list file noExistingFile.txt", exception.getMessage());
    }

    @Test
    void testGetPlayerListErrorIncorrectFileFormat() {
        var playerFileRepository = new PlayerFileRepository("src/test/resources/justbadformat.txt");
        var exception = assertThrows(LoadingPlayersException.class, playerFileRepository::getPlayerList);

        assertEquals("Players file format incorrect", exception.getMessage());
    }
}