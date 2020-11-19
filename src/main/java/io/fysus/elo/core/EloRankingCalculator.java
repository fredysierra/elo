package io.fysus.elo.core;

/**
 * Class which calculates new Elo rankings.
 */
public class EloRankingCalculator {

    private double getProbability(int rating1, int rating2) {
        return 1.0f * 1.0f / (1 + 1.0f * (Math.pow(10, 1.0f * (rating1 - rating2) / 400)));
    }

    /**
     * Based on two rankings generate two new other rankings based on Elo algorithm.
     * @param rating1 First and winner ranking.
     * @param rating2 Second ranking.
     * @param k K-Factor of Elo algorithm. (https://en.wikipedia.org/wiki/Elo_rating_system#Most_accurate_K-factor)
     * @return New Rankings.
     */
    public NewRankings getNewEloRanking(int rating1, int rating2, int k) {

        var probability2 = getProbability(rating1, rating2);

        var probability1 = getProbability(rating2, rating1);

        final double newRankingPlayer1;
        final double newRankingPlayer2;

        newRankingPlayer1 = rating1 + k * (1 - probability1);
        newRankingPlayer2 = rating2 + k * (0 - probability2);

        return new NewRankings(roundRanking(newRankingPlayer1), roundRanking(newRankingPlayer2));

    }

    private int roundRanking(double ranking) {
        return (int) (Math.round(ranking * 1000000.0) / 1000000.0);
    }

    /**
     * Enclose the response of two new Rankings.
     */
    public static final class NewRankings {

        private final int rankingPlayer1;

        private final int rankingPlayer2;

        private NewRankings(int rankingPlayer1, int rankingPlayer2) {
            this.rankingPlayer1 = rankingPlayer1;
            this.rankingPlayer2 = rankingPlayer2;
        }

        public int getRankingPlayer1() {
            return rankingPlayer1;
        }

        public int getRankingPlayer2() {
            return rankingPlayer2;
        }
    }
}
