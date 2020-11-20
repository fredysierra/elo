package io.fysus.elo.core;

import io.fysus.elo.domain.Match;
import io.fysus.elo.domain.Player;
import io.fysus.elo.error.PlayerNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Core main app class. Update player rankings based on a list of matches.
 * Additionally provides information of number or wins and losses per player and whom the player played.
 * Initial Ranking for all the players is INITIAL_RANK=1000. K-Factor used to all the ranking calculation is 30.
 */
public class MatchAnalyzer {

    private static final int INITIAL_RANK = 1000;
    private static final int K_FACTOR = 30;

    private final List<Match> matches;

    private final List<Player> players;

    private final Map<String, Integer> rankingByPlayer;

    public MatchAnalyzer(List<Match> match, List<Player> players) {
        this.matches = match;
        this.players = players;
        this.rankingByPlayer = getInitialRankingByPlayer(players);
        updateRanking();
    }

    private Map<String, Integer> getInitialRankingByPlayer(List<Player> players) {
        Map<String, Integer> initialValues = new HashMap<>();
        for (Player player : players) {
            initialValues.put(player.getId(), INITIAL_RANK);
        }
        return initialValues;
    }


    private void updateRanking() {
        var eloRankingCalculator = new EloRankingCalculator();

        for (Match match : matches) {

            var rankPlayer1 = rankingByPlayer.get(match.getWinnerPlayerId());
            var rankPlayer2 = rankingByPlayer.get(match.getLoserPlayerId());

            var newRankings = eloRankingCalculator.getNewEloRanking(rankPlayer1, rankPlayer2, K_FACTOR);

            rankingByPlayer.replace(match.getWinnerPlayerId(), newRankings.getRankingPlayer1());
            rankingByPlayer.replace(match.getLoserPlayerId(), newRankings.getRankingPlayer2());
        }
    }

    /**
     * Given a player id obtain a {@link Map} with Player Id of the rivals and number of times
     * the player won against them.
     * @param playerId Player id.
     */
    public Map<String, Long> getPlayerWinsPerRival(String playerId) {
        return matches.stream()
            .filter(match -> match.getWinnerPlayerId().equals(playerId))
            .map(Match::getLoserPlayerId)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    /**
     * Given a player id obtain a {@link Map} with Player Id of the rivals and number of times
     * the player lost against them.
     * @param playerId Player Id.
     */
    public Map<String, Long> getPlayerLossesPerRival(String playerId) {
        return matches.stream()
            .filter(match -> match.getLoserPlayerId().equals(playerId))
            .map(Match::getWinnerPlayerId)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    /**
     * Obtain {@link Player} based on Player Id.
     * @param playerId Player Id.
     * @return Player for the given Player Id.
     */
    public Player getPlayerById(String playerId) {
        return players.stream().filter(player -> player.getId().equals(playerId)).findFirst()
            .orElseThrow(() -> new PlayerNotFoundException("Player id " + playerId + " not found"));
    }

    /**
     * Obtain ranking of each Player Id
     */
    public Map<String, Integer> getRankingByPlayer() {
        return rankingByPlayer;
    }

    /**
     * Obtain number of wins per Player ID.
     */
    public Map<String, Long> getWinsByPlayer() {
        return matches.stream().collect(Collectors.groupingBy(Match::getWinnerPlayerId, Collectors.counting()));
    }

    /**
     * Obtain number of losses per Player ID.
     */
    public Map<String, Long> getLossesByPlayer() {
        return matches.stream().collect(Collectors.groupingBy(Match::getLoserPlayerId, Collectors.counting()));
    }

}
