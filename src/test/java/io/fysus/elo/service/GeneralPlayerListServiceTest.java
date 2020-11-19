package io.fysus.elo.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import io.fysus.elo.domain.Match;
import io.fysus.elo.domain.Player;
import io.fysus.elo.service.GeneralPlayerListService.SortBy;
import io.fysus.elo.source.MatchRepository;
import io.fysus.elo.source.PlayerRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GeneralPlayerListServiceTest {

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
    void testGetReportSortById() {
        GeneralPlayerListService service = new GeneralPlayerListService(matchRepository, playerRepository);
        var report = service.getReport(SortBy.ID, false);

        assertAll(() -> assertEquals("1", report.get(0).getId()),
            () -> assertEquals("Player1", report.get(0).getName()),
            () -> assertEquals(1028, report.get(0).getRanking()),
            () -> assertEquals(2, report.get(0).getNumberWins()),
            () -> assertEquals(0, report.get(0).getNumberLosses()));

        assertAll("Report should be sorted by id",
            () -> assertEquals("1", report.get(0).getId()),
            () -> assertEquals("2", report.get(1).getId()),
            () -> assertEquals("3", report.get(2).getId()));

    }

    @Test
    void testGetReportSortByName() {
        GeneralPlayerListService service = new GeneralPlayerListService(matchRepository, playerRepository);
        var report = service.getReport(SortBy.NAME, false);

        assertAll(() -> assertEquals("1", report.get(0).getId()),
            () -> assertEquals("Player1", report.get(0).getName()),
            () -> assertEquals(1028, report.get(0).getRanking()),
            () -> assertEquals(2, report.get(0).getNumberWins()),
            () -> assertEquals(0, report.get(0).getNumberLosses()));

        assertAll("Report should be sorted by Name",
            () -> assertEquals("Player1", report.get(0).getName()),
            () -> assertEquals("Player2", report.get(1).getName()),
            () -> assertEquals("Player3", report.get(2).getName()));
    }

    @Test
    void testGetReportSortByRanking() {
        GeneralPlayerListService service = new GeneralPlayerListService(matchRepository, playerRepository);
        var report = service.getReport(SortBy.RANKING, false);

        assertAll(() -> assertEquals("3", report.get(0).getId()),
            () -> assertEquals("Player3", report.get(0).getName()),
            () -> assertEquals(970, report.get(0).getRanking()),
            () -> assertEquals(0, report.get(0).getNumberWins()),
            () -> assertEquals(2, report.get(0).getNumberLosses()));

        assertAll("Report should be sorted by Ranking",
            () -> assertEquals(970, report.get(0).getRanking()),
            () -> assertEquals(1000, report.get(1).getRanking()),
            () -> assertEquals(1028, report.get(2).getRanking()));
    }

    @Test
    void testGetReportSortByRankingReverseOrder() {
        GeneralPlayerListService service = new GeneralPlayerListService(matchRepository, playerRepository);
        var report = service.getReport(SortBy.RANKING, true);

        assertAll(() -> assertEquals("1", report.get(0).getId()),
            () -> assertEquals("Player1", report.get(0).getName()),
            () -> assertEquals(1028, report.get(0).getRanking()),
            () -> assertEquals(2, report.get(0).getNumberWins()),
            () -> assertEquals(0, report.get(0).getNumberLosses()));

        assertAll("Report should be sorted by Ranking",
            () -> assertEquals(1028, report.get(0).getRanking()),
            () -> assertEquals(1000, report.get(1).getRanking()),
            () -> assertEquals(970, report.get(2).getRanking()));
    }


    @Test
    void testGetReportSortByWins() {
        GeneralPlayerListService service = new GeneralPlayerListService(matchRepository, playerRepository);
        var report = service.getReport(SortBy.WINS, false);

        assertAll(() -> assertEquals("3", report.get(0).getId()),
            () -> assertEquals("Player3", report.get(0).getName()),
            () -> assertEquals(970, report.get(0).getRanking()),
            () -> assertEquals(0, report.get(0).getNumberWins()),
            () -> assertEquals(2, report.get(0).getNumberLosses()));

        assertAll("Report should be sorted by Wins",
            () -> assertEquals(0, report.get(0).getNumberWins()),
            () -> assertEquals(1, report.get(1).getNumberWins()),
            () -> assertEquals(2, report.get(2).getNumberWins()));
    }

    @Test
    void testGetReportSortByLosses() {
        GeneralPlayerListService service = new GeneralPlayerListService(matchRepository, playerRepository);
        var report = service.getReport(SortBy.LOSSES, false);

        assertAll(() -> assertEquals("1", report.get(0).getId()),
            () -> assertEquals("Player1", report.get(0).getName()),
            () -> assertEquals(1028, report.get(0).getRanking()),
            () -> assertEquals(2, report.get(0).getNumberWins()),
            () -> assertEquals(0, report.get(0).getNumberLosses()));

        assertAll("Report should be sorted by Losses",
            () -> assertEquals(0, report.get(0).getNumberLosses()),
            () -> assertEquals(1, report.get(1).getNumberLosses()),
            () -> assertEquals(2, report.get(2).getNumberLosses()));
    }


}