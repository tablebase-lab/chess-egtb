package dev.michalrelich.tablebase.frontend;

import dev.michalrelich.tablebase.frontend.swing.App;

import java.util.Map;
import java.util.TreeMap;

public class Board {

    private Map<Piece, Integer> board = new TreeMap<>();
    public static final int BOARD_LENGTH = 8;

    public boolean addToBoard(Piece piece, int row, int col) {
        if (row > 8 || col > 8 || row < 0 || col < 0) return false;

        board.put(piece, col - 1 + BOARD_LENGTH * (row - 1));
        return true;
    }

    public Map<Piece, Integer> getBoard() {
        return board;
    }

    public void launchApp() {
        App.loadBoard(this);
        App.launch();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");


        for (var entry : board.entrySet()) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                if (j == 0) sb.append("| ");
                if (entry.getKey() == null) {
                    sb.append("  | ");
                    continue;
                }
                sb.append(entry.getKey()).append(" | ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
