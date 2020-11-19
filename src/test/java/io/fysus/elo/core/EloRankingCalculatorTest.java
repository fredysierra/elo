package io.fysus.elo.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.fysus.elo.core.EloRankingCalculator.NewRankings;
import org.junit.jupiter.api.Test;

class EloRankingCalculatorTest {

    @Test
    void testRankingForGame() {
        EloRankingCalculator elo = new EloRankingCalculator();

        NewRankings newRatings = elo.getNewEloRanking(1000, 1000, 30);

        assertEquals(1015, newRatings.getRankingPlayer1());
        assertEquals(985, newRatings.getRankingPlayer2());
    }
}