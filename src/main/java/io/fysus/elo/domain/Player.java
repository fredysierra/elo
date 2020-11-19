package io.fysus.elo.domain;

import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Exclude;
import lombok.Getter;
import lombok.NonNull;

@Getter
@EqualsAndHashCode
public class Player {

    private String id;

    @Exclude
    private String name;

    public Player(@NonNull String id, @NonNull String name) {
        if (id.isEmpty()) {
            throw new IllegalArgumentException("Player id is empty");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Player name is empty");
        }
        this.id = id;
        this.name = name;
    }
}
