package dev.michalrelich.tablebase.frontend;

import dev.michalrelich.tablebase.exceptions.InvalidBoardException;
import dev.michalrelich.tablebase.frontend.swing.App;

import java.util.*;

public class Board {

    private final Map<Piece, NavigableSet<Integer>> board = new TreeMap<>();
    public static final int BOARD_LENGTH = 8;

    public boolean addToBoard(Piece piece, int row, int col) {
        if (row > 8 || col > 8 || row < 0 || col < 0) return false;
        if (piece == null) return false;

        for (var v : board.values()) {
            v.removeIf(position -> position == col - 1 + (row - 1) * BOARD_LENGTH);
        }

        board.computeIfAbsent(piece, _ -> new TreeSet<>())
                .add(col - 1 + (row - 1) * BOARD_LENGTH);

        return true;
    }

    public NavigableMap<Piece, NavigableSet<Integer>> getBoard() { // or SortedMap, Map?
        NavigableMap<Piece, NavigableSet<Integer>> deepCopy = new TreeMap<>();
        for (var entry : board.entrySet()) {
            deepCopy.put(new Piece(entry.getKey().getType(), entry.getKey().getColor()),
                    new TreeSet<>(entry.getValue()));
        }
        return deepCopy;
    }

    public void checkPieceConditions() { // only checks piece count, can still have kings next to each other, ...
        int kingCount = 0;
        int pieceCount = 0;

        for (var entry : board.entrySet()) {
            if (entry.getKey().getType() == Piece.PieceType.KING) {
                if (entry.getValue().size() != 1) throw new InvalidBoardException("Invalid number of kings");
                kingCount++;
                continue;
            }

            pieceCount += entry.getValue().size();
        }

        if (kingCount != 2) throw new InvalidBoardException("Invalid number of kings: " + kingCount + ".");
        if (pieceCount > 3) throw new InvalidBoardException("Piece count " + pieceCount + " larger than 3.");
    }

    public void launchApp() {
        App.loadBoard(this);
        App.launch();
    }
}
