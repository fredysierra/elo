package io.fysus.elo.core;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.fysus.elo.domain.Match;
import io.fysus.elo.domain.Player;
import io.fysus.elo.error.PlayerNotFoundException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchAnalyzerTest {

    private List<Player> players;

    private List<Match> matches;

    @BeforeEach
    void setUp() {
        var player1 = new Player("1", "Player1");
        var player2 = new Player("2", "Player2");
        var player3 = new Player("3", "Player3");
        matches = List.of(
            new Match("1", "2"),
            new Match("2", "3"),
            new Match("1", "3")
        );

        players = List.of(player1, player2, player3);

    }

    @Test
    void testRankPlayers() {

        MatchAnalyzer matchAnalyzer = new MatchAnalyzer(matches, players);

        Map<String, Integer> newRankingByPlayer = matchAnalyzer.getRankingByPlayer();

        assertEquals(1028, newRankingByPlayer.get("1"));
        assertEquals(1000, newRankingByPlayer.get("2"));
        assertEquals(970, newRankingByPlayer.get("3"));

    }


    @Test
    void testCountWinsGames() {

        MatchAnalyzer matchAnalyzer = new MatchAnalyzer(matches, players);

        Map<String, Long> winsByPlayer = matchAnalyzer.getWinsByPlayer();

        assertEquals(2, winsByPlayer.getOrDefault("1", 0L));
        assertEquals(1, winsByPlayer.getOrDefault("2", 0L));
        assertEquals(0, winsByPlayer.getOrDefault("3", 0L));

    }

    @Test
    void testCountLossesGames() {

        MatchAnalyzer matchAnalyzer = new MatchAnalyzer(matches, players);

        Map<String, Long> lossesByPlayer = matchAnalyzer.getLossesByPlayer();

        assertEquals(0, lossesByPlayer.getOrDefault("1", 0L));
        assertEquals(1, lossesByPlayer.getOrDefault("2", 0L));
        assertEquals(2, lossesByPlayer.getOrDefault("3", 0L));
    }

    @Test
    void testWinsPerRival() {

        MatchAnalyzer matchAnalyzer = new MatchAnalyzer(matches, players);

        var winsPerRival = matchAnalyzer.getPlayerWinsPerRival("1");

        assertEquals(1, winsPerRival.get("2"));
        assertEquals(1, winsPerRival.get("3"));
    }

    @Test
    void testLossesPerRival() {
        MatchAnalyzer matchAnalyzer = new MatchAnalyzer(matches, players);

        var lossesPerRival = matchAnalyzer.getPlayerLossesPerRival("3");

        assertEquals(1, lossesPerRival.get("2"));
        assertEquals(1, lossesPerRival.get("1"));
    }

    @Test
    void testGetPlayerById() {
        MatchAnalyzer matchAnalyzer = new MatchAnalyzer(matches, players);

        var player = matchAnalyzer.getPlayerById("1");

        assertAll(() -> assertEquals("1", player.getId()),
            () -> assertEquals("Player1", player.getName()));
    }

    @Test
    void testGetPlayerByIncorrectId() {
        MatchAnalyzer matchAnalyzer = new MatchAnalyzer(matches, players);

        Exception exception = assertThrows(PlayerNotFoundException.class, () -> matchAnalyzer.getPlayerById("13"));

        assertEquals("Player id 13 not found", exception.getMessage());

    }
}