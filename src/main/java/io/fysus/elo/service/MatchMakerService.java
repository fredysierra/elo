package io.fysus.elo.service;

import io.fysus.elo.core.MatchAnalyzer;
import io.fysus.elo.domain.Match;
import io.fysus.elo.error.EloException;
import io.fysus.elo.source.MatchRepository;
import io.fysus.elo.source.PlayerRepository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MatchMakerService {

    private MatchRepository matchRepository;
    private PlayerRepository playerRepository;

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public List<Match> getMatches() {

        var players = playerRepository.getPlayerList();

        if (players.size() <= 1) {
            throw new EloException("More than one player is required to generate matches");
        }

        var matches = matchRepository.getMatchesList();

        var matchAnalyzer = new MatchAnalyzer(matches, players);

        var rankingByPlayer = matchAnalyzer.getRankingByPlayer();

        return generateMatchesBasedOnRanking(rankingByPlayer);
    }

    private List<Match> generateMatchesBasedOnRanking(Map<String, Integer> rankingByPlayer) {
        var sortedByValueRanking = sortByValue(rankingByPlayer);

        List<Match> newMatches = new ArrayList<>();

        Iterator<String> it = sortedByValueRanking.keySet().iterator();

        while (it.hasNext()) {
            String player1 = it.next();
            if (it.hasNext()) {
                String player2 = it.next();
                newMatches.add(new Match(player1, player2));
            } else {
                Iterator<String> it2 = sortedByValueRanking.keySet().iterator();
                String player2 = it2.next();
                newMatches.add(new Match(player1, player2));
                break;
            }
        }
        return newMatches;
    }
}
