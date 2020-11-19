package io.fysus.elo.controller;

import io.fysus.elo.domain.GeneralPlayerInformation;
import io.fysus.elo.error.EloException;
import io.fysus.elo.formatter.OutputFormatter;
import io.fysus.elo.service.GeneralPlayerListService;
import io.fysus.elo.service.GeneralPlayerListService.SortBy;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Controller implementation for LIST_PLAYERS Elo application option.
 */
@Component
@AllArgsConstructor
@Slf4j
public class ListPlayersController implements Controller {

    private GeneralPlayerListService service;
    private OutputFormatter<List<GeneralPlayerInformation>> formatter;

    /**
     * Generate a list of player handler. Rely on {@link GeneralPlayerListService} to obtain the list and
     * a {@link OutputFormatter} to format and output the information. By default the List is sorted by Player Ranking.
     * @param args two option parameter are accepted. A sort field {@link SortBy}
     * and a sort direction indicator {@link SortDirection}.
     */
    @Override
    public void execute(String[] args) {
        log.debug("List players general statistics with {} parameters", Arrays.toString(args));
        SortBy sortBy;
        boolean descendant = false;
        if (args.length < 1) {
            sortBy = SortBy.RANKING;
        } else {
            sortBy = extractSortByParam(args);
            SortDirection sortDirection = extractSortDirectionByParam(args);
            descendant = (sortDirection == SortDirection.DES);
        }

        var listPlayers = service.getReport(sortBy, descendant);

        formatter.format(listPlayers);

    }

    private SortDirection extractSortDirectionByParam(String[] args) {
        if (args.length >= 2) {
            try {
                return SortDirection.valueOf(args[1]);
            } catch (IllegalArgumentException e) {
                throw new EloException("No valid option, valid options are " + Arrays.toString(SortDirection.values()));
            }
        } else {
            return SortDirection.ASC;
        }
    }

    private SortBy extractSortByParam(String[] args) {
        SortBy sortBy;
        try {
            sortBy = SortBy.valueOf(args[0]);
            return sortBy;
        } catch (IllegalArgumentException e) {
            throw new EloException("No valid option, valid options are " + Arrays.toString(SortBy.values()));
        }
    }

    private enum SortDirection {
        ASC, DES
    }
}
