package io.fysus.elo.formatter;

import static java.lang.System.out;

import io.fysus.elo.domain.Match;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class NextMatchConsoleOutput implements OutputFormatter<List<Match>> {

    @Override
    public void format(List<Match> matches) {
        out.println("Suggested next matches.");

        matches.forEach(match -> out.println(match.getWinnerPlayerId() + " " + match.getLoserPlayerId()));
    }
}
