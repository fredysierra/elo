package io.fysus.elo.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import io.fysus.elo.domain.Match;
import io.fysus.elo.domain.Player;
import io.fysus.elo.source.MatchRepository;
import io.fysus.elo.source.PlayerRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DetailPlayerServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private PlayerRepository playerRepository;


    @BeforeEach
    void setUp() {
        setupMatches();
        setupPlayers();
    }

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
    void testGetDetailReport() {
        var detailPlayerService = new DetailPlayerService(matchRepository, playerRepository);
        var detailReport = detailPlayerService.getDetailPlayerReport("1");

        assertAll(
            () -> assertEquals("1", detailReport.getId()),
            () -> assertEquals("Player1", detailReport.getName()),
            () -> assertEquals(2, detailReport.getNumberWins()),
            () -> assertEquals(0, detailReport.getNumberLosses()),
            () -> assertEquals(1028, detailReport.getRanking()),
            () -> assertThat(detailReport.getNumberGamesWonByRival().keySet(),
                contains(new Player("2", "Player2"), new Player("3", "Player3"))),
            () -> assertEquals(1, detailReport.getNumberGamesWonByRival().get(new Player("2", "Player2")))
        );
    }
}