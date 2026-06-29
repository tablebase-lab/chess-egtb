package dev.michalrelich.tablebase.ui;

public class Board {

    private int[][] board;

    public Board() {
        board = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = 0;
            }
        }
    }

    public Board(int[][] board) {
        this.board = board;
    }

    public int[][] getBoard() {
        return board;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                sb.append(board[i][j]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
