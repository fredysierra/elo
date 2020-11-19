package io.fysus.elo.domain;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class DetailPlayerInformation {

    private String id;
    private String name;
    private int ranking;
    private long numberWins;
    private long numberLosses;

    private Map<Player, Long> numberGamesWonByRival;
    private Map<Player, Long> numberGamesLostByRival;

}
