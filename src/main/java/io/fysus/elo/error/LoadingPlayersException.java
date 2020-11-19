package io.fysus.elo.error;

public class LoadingPlayersException extends RuntimeException {

    public LoadingPlayersException(String message, Throwable cause) {
        super(message, cause);
    }
}
