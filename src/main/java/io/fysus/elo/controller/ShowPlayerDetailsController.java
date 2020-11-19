package io.fysus.elo.controller;

import io.fysus.elo.domain.DetailPlayerInformation;
import io.fysus.elo.error.EloException;
import io.fysus.elo.formatter.OutputFormatter;
import io.fysus.elo.service.DetailPlayerService;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Controller implementation for SHOW_DETAILS Elo application option.
 */
@Component
@AllArgsConstructor
@Slf4j
public class ShowPlayerDetailsController implements Controller {

    private DetailPlayerService detailPlayerService;
    private OutputFormatter<DetailPlayerInformation> formatter;

    /**
     * Handler method to obtain a detail information of the player.
     * Rely on {@link DetailPlayerService} to obtain player information and on {@link OutputFormatter}
     * to output the detail player information
     * @param args it should contain a parameter specifying Player Id.
     */
    @Override
    public void execute(String[] args) {
        log.debug("Show player detail statistics with args {}", Arrays.toString(args));
        if (args.length < 1) {
            throw new EloException("PlayerId is required for this option");
        }
        if (args.length > 1) {
            throw new EloException("Extra parameters no required "+Arrays.toString(args)+". Just playerId is required");
        }
        var detailPlayer = detailPlayerService.getDetailPlayerReport(args[0]);

        formatter.format(detailPlayer);
    }
}
