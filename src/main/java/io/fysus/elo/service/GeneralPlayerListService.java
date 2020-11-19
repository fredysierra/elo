package io.fysus.elo.service;

import io.fysus.elo.core.MatchAnalyzer;
import io.fysus.elo.domain.GeneralPlayerInformation;
import io.fysus.elo.domain.Player;
import io.fysus.elo.source.MatchRepository;
import io.fysus.elo.source.PlayerRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GeneralPlayerListService {

    private MatchRepository matchRepository;
    private PlayerRepository playerRepository;


    public List<GeneralPlayerInformation> getReport(SortBy sortBy, boolean reversed) {

        var players = playerRepository.getPlayerList();
        var matches = matchRepository.getMatchesList();

        var matchAnalyzer = new MatchAnalyzer(matches, players);

        var rankingByPlayer = matchAnalyzer.getRankingByPlayer();

        var winsByPlayer = matchAnalyzer.getWinsByPlayer();

        var lossesByPlayer = matchAnalyzer.getLossesByPlayer();

        var report = new ArrayList<GeneralPlayerInformation>();

        for (Player player : players) {
            report.add(
                GeneralPlayerInformation.builder()
                    .name(player.getName())
                    .id(player.getId())
                    .ranking(rankingByPlayer.getOrDefault(player.getId(), 0))
                    .numberWins(winsByPlayer.getOrDefault(player.getId(), 0L))
                    .numberLosses(lossesByPlayer.getOrDefault(player.getId(), 0L)).build()
            );
        }

        var sortComparator = (reversed) ? sortBy.comparator.reversed() : sortBy.comparator;

        report.sort(sortComparator);

        return report;
    }

    public enum SortBy {
        ID((player1, player2) -> player1.getId().compareTo(player2.getId())),
        NAME((player1, player2) -> player1.getName().compareTo(player2.getName())),
        RANKING((player1, player2) -> player1.getRanking() - player2.getRanking()),
        WINS((player1, player2) -> (int) (player1.getNumberWins() - player2.getNumberWins())),
        LOSSES((player1, player2) -> (int) (player1.getNumberLosses() - player2.getNumberLosses()));

        private final Comparator<GeneralPlayerInformation> comparator;

        SortBy(Comparator<GeneralPlayerInformation> comparator) {
            this.comparator = comparator;
        }
    }
}
