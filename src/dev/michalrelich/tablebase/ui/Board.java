package dev.michalrelich.tablebase.ui;

public class Board {

    private char[][] board;

    public Board() {
        board = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public boolean addToBoard(Piece piece, int row, int col, boolean white) {
        if (row > 8 || col > 8 || row < 0 || col < 0) return false;

        board[row - 1][col - 1] = piece.asChar(white);
        return true;
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
