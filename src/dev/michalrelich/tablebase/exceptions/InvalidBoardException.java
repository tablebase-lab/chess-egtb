package dev.michalrelich.tablebase.exceptions;

public class InvalidBoardException extends RuntimeException {
    public InvalidBoardException(String message) {
        super(message);
    }
}
