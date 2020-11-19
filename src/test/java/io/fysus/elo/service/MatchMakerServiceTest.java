package io.fysus.elo.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import io.fysus.elo.domain.Match;
import io.fysus.elo.domain.Player;
import io.fysus.elo.error.EloException;
import io.fysus.elo.source.MatchRepository;
import io.fysus.elo.source.PlayerRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MatchMakerServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private PlayerRepository playerRepository;


    private void setupPlayers() {
        List<Player> players = List.of(
            new Player("1", "Player1"),
            new Player("2", "Player2"),
            new Player("3", "Player3")
        );
        when(playerRepository.getPlayerList()).thenReturn(players);
    }

    private void setupMatches() {
        List<Match> matches = List.of(
            new Match("1", "2"),
            new Match("2", "3"),
            new Match("1", "3")
        );
        when(matchRepository.getMatchesList()).thenReturn(matches);
    }

    @Test
    void testGetMatches() {

        setupMatches();
        setupPlayers();

        var matchMaker = new MatchMakerService(matchRepository, playerRepository);

        var nextMatches = matchMaker.getMatches();

        assertAll(() -> assertMatch("3", "2", nextMatches.get(0)),
            () -> assertMatch("1", "3", nextMatches.get(1)));

    }


    @Test
    void testGetMatchesErrorMoreThanOnePlayerNeeded() {

        when(playerRepository.getPlayerList()).thenReturn(Collections.singletonList(new Player("1", "Player1")));

        var matchMaker = new MatchMakerService(matchRepository, playerRepository);

        Exception exception = assertThrows(EloException.class, matchMaker::getMatches);

        assertEquals("More than one player is required to generate matches", exception.getMessage());


    }


    void assertMatch(String player1, String player2, Match match) {
        assertAll(() -> assertEquals(player1, match.getWinnerPlayerId()),
            () -> assertEquals(player2, match.getLoserPlayerId()));
    }
}