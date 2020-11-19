package io.fysus.elo.domain;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Match {

    private String winnerPlayerId;
    private String loserPlayerId;

    public Match(@NonNull String winnerPlayerId, @NonNull String loserPlayerId) {
        if (winnerPlayerId.isEmpty()) {
            throw new IllegalArgumentException("winner playerId is empty");
        }
        if (loserPlayerId.isEmpty()) {
            throw new IllegalArgumentException("loser playerId is empty");
        }
        this.winnerPlayerId = winnerPlayerId;
        this.loserPlayerId = loserPlayerId;
    }
}
