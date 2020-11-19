package io.fysus.elo.source;

import io.fysus.elo.domain.Player;
import io.fysus.elo.error.LoadingPlayersException;
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
 * Obtain players from a file. Players file Path is required in the constructor.
 */
@Service
@Slf4j
public class PlayerFileRepository implements PlayerRepository {


    private final String filePlayerPath;

    public PlayerFileRepository(@Value("${players.filename}") String filePlayerPath) {
        this.filePlayerPath = filePlayerPath;
    }

    /**
     * Obtain the list of player included in the file.
     * Each line in the file should contain the player Id and the Player name separated by an space.
     * @return List of players
     */
    @Override
    public List<Player> getPlayerList() {
        log.debug("Obtain list of players from {}", filePlayerPath);
        try (Stream<String> lines = Files.lines(Paths.get(filePlayerPath))) {
            return lines.map(this::readPlayer).collect(Collectors.toList());
        } catch (IOException e) {
            throw new LoadingPlayersException("Problem reading players list file " + filePlayerPath, e);
        } catch (Exception e) {
            throw new LoadingPlayersException("Players file format incorrect", e);
        }
    }

    private Player readPlayer(String line) {

        String[] playerParts = line.replaceAll("\\p{javaSpaceChar}{2,}", " ").split(" ");
        return new Player(playerParts[0].trim(), playerParts[1].trim());
    }


}
