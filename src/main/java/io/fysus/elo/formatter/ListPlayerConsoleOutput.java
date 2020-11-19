package io.fysus.elo.formatter;

import static java.lang.System.out;

import io.fysus.elo.domain.GeneralPlayerInformation;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ListPlayerConsoleOutput implements OutputFormatter<List<GeneralPlayerInformation>> {

    @Override
    public void format(List<GeneralPlayerInformation> players) {
        out.println("List players");

        out.format("%-10s%-20s%-20s%-10s%-10s%n", "ID", "NAME", "RANKING", "WINS", "LOSSES");

        players.forEach(player -> out.format("%-10s%-20s%-20d%-10d%-10d%n",
            player.getId(),
            player.getName(),
            player.getRanking(),
            player.getNumberWins(),
            player.getNumberLosses()));
    }
}
