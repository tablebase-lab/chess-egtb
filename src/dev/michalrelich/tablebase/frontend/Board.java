package dev.michalrelich.tablebase.frontend;

public class Board {

    private Piece[][] board = new Piece[8][8];

    public boolean addToBoard(Piece piece, int row, int col) {
        if (row > 8 || col > 8 || row < 0 || col < 0) return false;

        board[row - 1][col - 1] = piece;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (Piece[] pieces : board) {
            for (int j = 0; j < pieces.length; j++) {
                if (j == 0) sb.append("| ");
                if (pieces[j] == null) {
                    sb.append("  | ");
                    continue;
                }
                sb.append(pieces[j]).append(" | ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
