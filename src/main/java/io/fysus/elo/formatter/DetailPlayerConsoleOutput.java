package io.fysus.elo.formatter;

import static java.lang.System.out;

import io.fysus.elo.domain.DetailPlayerInformation;
import org.springframework.stereotype.Component;

@Component
public class DetailPlayerConsoleOutput implements OutputFormatter<DetailPlayerInformation> {

    @Override
    public void format(DetailPlayerInformation player) {

        out.println("Detail player information");

        out.format("%-10s%-20s%-20s%-10s%-10s%n", "ID", "NAME", "RANKING", "WINS", "LOSSES");

        out.format("%-10s%-20s%-20d%-10d%-10d%n",
            player.getId(),
            player.getName(),
            player.getRanking(),
            player.getNumberWins(),
            player.getNumberLosses());

        out.println("Winning against:");

        out.format("%-10s%-10s%-10s%n", "ID", "NAME", "NUMBER WINS");

        player.getNumberGamesWonByRival()
            .forEach((opponent, numberWins) -> out.format("%-10s%-10s%-10d%n",
                opponent.getId(),
                opponent.getName(),
                numberWins));

        out.println("Loosing against:");

        out.format("%-10s%-10s%-10s%n", "ID", "NAME", "NUMBER LOSSES");

        player.getNumberGamesLostByRival().forEach((opponent, numberLosses) -> out.format("%-10s%-10s%-10d%n",
            opponent.getId(),
            opponent.getName(),
            numberLosses));


    }
}
