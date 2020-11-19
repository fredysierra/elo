package io.fysus.elo.source;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.fysus.elo.domain.Match;
import io.fysus.elo.error.LoadingMatchesException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MatchFileRepositoryTest {

    @Test
    void testGetMatchList() {
        MatchFileRepository matchFileRepository = new MatchFileRepository("src/test/resources/matches.txt");

        List<Match> matches = matchFileRepository.getMatchesList();

        assertFalse(matches.isEmpty());

        assertAll(() -> assertEquals("37", matches.get(0).getWinnerPlayerId()),
            () -> assertEquals("38", matches.get(0).getLoserPlayerId()));
    }

    @Test
    void testGetMatchListErrorAccessingFile() {
        MatchFileRepository matchFileRepository = new MatchFileRepository("noExistingFile.txt");
        var exception = Assertions
            .assertThrows(LoadingMatchesException.class, matchFileRepository::getMatchesList);
        assertEquals("Problem reading matches list file noExistingFile.txt", exception.getMessage());
    }

    @Test
    void testGetMatchListErrorIncorrectFileFormat() {
        var matchFileRepository = new MatchFileRepository("src/test/resources/justbadformat.txt");
        var exception = assertThrows(LoadingMatchesException.class, matchFileRepository::getMatchesList);

        assertEquals("Matches file format incorrect", exception.getMessage());
    }
}