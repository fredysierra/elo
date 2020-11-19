package io.fysus.elo.source;

import io.fysus.elo.domain.Player;
import java.util.List;

public interface PlayerRepository {

    List<Player> getPlayerList();

}
