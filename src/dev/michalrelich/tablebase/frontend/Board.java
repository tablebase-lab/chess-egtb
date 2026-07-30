package dev.michalrelich.tablebase.frontend;

import dev.michalrelich.tablebase.frontend.swing.App;

import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class Board {

    private Map<Piece, HashSet<Integer>> board = new TreeMap<>();
    public static final int BOARD_LENGTH = 8;

    public boolean addToBoard(Piece piece, int row, int col) {
        if (row > 8 || col > 8 || row < 0 || col < 0) return false;

        for (var v : board.values()) {
            v.removeIf(position -> position == col - 1 + (row - 1) * BOARD_LENGTH);
        }

        board.computeIfAbsent(piece, _ -> new HashSet<>())
                .add(col - 1 + (row - 1) * BOARD_LENGTH);

        return true;
    }

    public Map<Piece, HashSet<Integer>> getBoard() {
        return board;
    }

    public void launchApp() {
        App.loadBoard(this);
        App.launch();
    }

    private void removePiece(int row, int col) {
        int position = col - 1 + (row - 1) * 8;

        for (var e : board.entrySet()) {
            e.getValue().removeIf(p -> p == position);
        }
    }
}
