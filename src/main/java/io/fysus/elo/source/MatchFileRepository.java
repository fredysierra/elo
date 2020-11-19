package io.fysus.elo.source;

import io.fysus.elo.domain.Match;
import io.fysus.elo.error.LoadingMatchesException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Obtain matches from a file. Matches file Path is required in the constructor.
 */
@Service
@Slf4j
public class MatchFileRepository implements MatchRepository {

    private final String fileMatchPath;

    public MatchFileRepository(@Value("${matches.filename}") String fileMatchPath) {
        this.fileMatchPath = fileMatchPath;
    }

    /**
     * Obtain the list of all the matches included in the matches file.
     * Each line in the file should contain two playerId separated by an space.
     * First playerId is considered the winner of the match.
     * @return List of matches.
     */
    @Override
    public List<Match> getMatchesList() {
        log.debug("Obtain list of matches from {}", fileMatchPath);
        try (Stream<String> lines = Files.lines(Paths.get(fileMatchPath))) {
            return lines.map(this::readMatch).collect(Collectors.toList());
        } catch (IOException e) {
            throw new LoadingMatchesException("Problem reading matches list file " + fileMatchPath, e);
        } catch (Exception e) {
            throw new LoadingMatchesException("Matches file format incorrect", e);
        }
    }

    private Match readMatch(String line) {
        String[] matchParts = line.split(" ");
        return new Match(matchParts[0].trim(), matchParts[1].trim());
    }

}
