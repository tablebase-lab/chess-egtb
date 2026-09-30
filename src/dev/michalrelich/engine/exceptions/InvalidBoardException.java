package dev.michalrelich.engine.exceptions;

public class InvalidBoardException extends RuntimeException {
    public InvalidBoardException(String message) {
        super(message);
    }
}
