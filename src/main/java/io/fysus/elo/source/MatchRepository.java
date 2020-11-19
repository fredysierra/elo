package io.fysus.elo.source;

import io.fysus.elo.domain.Match;
import java.util.List;

public interface MatchRepository {

    List<Match> getMatchesList();
}
