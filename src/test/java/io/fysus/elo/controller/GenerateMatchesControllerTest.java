package io.fysus.elo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import io.fysus.elo.domain.Match;
import io.fysus.elo.error.EloException;
import io.fysus.elo.formatter.OutputFormatter;
import io.fysus.elo.service.MatchMakerService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class GenerateMatchesControllerTest {

    @InjectMocks
    private GenerateMatchesController generateMatches;

    @Mock
    private OutputFormatter<List<Match>> formatter;

    @Mock
    private MatchMakerService matchMakerService;


    @Test
    void testExecuteWithExtraParameters() {
        Exception exception = assertThrows(EloException.class, () ->generateMatches.execute(new String[]{"crazyValue1"}));
        assertEquals("These options are not valid [crazyValue1]", exception.getMessage());
    }

    @Test
    void testExecute() {
        generateMatches.execute(new String[]{});
        verify(matchMakerService).getMatches();
        verify(formatter).format(any());
    }

}