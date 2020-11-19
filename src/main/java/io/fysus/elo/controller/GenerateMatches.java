package io.fysus.elo.controller;

import io.fysus.elo.domain.Match;
import io.fysus.elo.error.EloException;
import io.fysus.elo.formatter.OutputFormatter;
import io.fysus.elo.service.MatchMakerService;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Controller implementation for GENERATE_MATCH Elo application option.
 */
@Component
@AllArgsConstructor
@Slf4j
public class GenerateMatches implements Controller {

    private MatchMakerService matchMakerService;
    private OutputFormatter<List<Match>> formatter;

    /**
     * Generate matches handler method. Rely on {@link MatchMakerService} to generate the matches
     * and on a {@link OutputFormatter} to format and output the result.
     * @param args For this particular implementation should be empty an array.
     */
    @Override
    public void execute(String[] args) {
        log.debug("Generating matches with {} parameters", Arrays.toString(args));
        if (args.length != 0) {
            throw new EloException("These options are not valid "+ Arrays.toString(args));
        }

        var matches = matchMakerService.getMatches();

        formatter.format(matches);
    }
}
