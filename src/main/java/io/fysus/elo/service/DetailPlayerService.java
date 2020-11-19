package io.fysus.elo.service;

import io.fysus.elo.core.MatchAnalyzer;
import io.fysus.elo.domain.DetailPlayerInformation;
import io.fysus.elo.source.MatchRepository;
import io.fysus.elo.source.PlayerRepository;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DetailPlayerService {

    private static final long ZERO = 0L;

    private MatchRepository matchRepository;
    private PlayerRepository playerRepository;

    public DetailPlayerInformation getDetailPlayerReport(String playerId) {

        var players = playerRepository.getPlayerList();
        var matches = matchRepository.getMatchesList();

        var matchAnalyzer = new MatchAnalyzer(matches, players);

        var player = matchAnalyzer.getPlayerById(playerId);
        var ranking = matchAnalyzer.getRankingByPlayer().get(playerId);
        var wins = matchAnalyzer.getWinsByPlayer().getOrDefault(playerId, ZERO);
        var losses = matchAnalyzer.getLossesByPlayer().getOrDefault(playerId, ZERO);
        var numberGamesWonByRival = matchAnalyzer.
            getPlayerWinsPerRival(playerId);
        var numberGamesLostByRival = matchAnalyzer.
            getPlayerLossesPerRival(playerId);

        return DetailPlayerInformation
            .builder()
            .id(playerId)
            .name(player.getName())
            .ranking(ranking)
            .numberWins(wins)
            .numberLosses(losses)
            .numberGamesWonByRival(numberGamesWonByRival.keySet().stream()
                .collect(Collectors.toMap(matchAnalyzer::getPlayerById, numberGamesWonByRival::get)))
            .numberGamesLostByRival(numberGamesLostByRival.keySet().stream()
                .collect(Collectors.toMap(matchAnalyzer::getPlayerById, numberGamesLostByRival::get)))
            .build();

    }


}
