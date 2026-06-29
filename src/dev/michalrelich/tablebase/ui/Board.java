package dev.michalrelich.tablebase.ui;

public class Board {

    private char[][] board;

    public Board() {
        board = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = '\u0000';
            }
        }
    }

    public char[][] getBoard() {
        return board;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (char[] chars : board) {
            for (int j = 0; j < chars.length; j++) {
                if (j == 0) sb.append("| ");
                sb.append(chars[j]).append(" | ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
