package io.fysus.elo.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GeneralPlayerInformation {

    private String id;
    private String name;
    private int ranking;
    private long numberWins;
    private long numberLosses;
}
