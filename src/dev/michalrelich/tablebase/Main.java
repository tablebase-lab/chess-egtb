package dev.michalrelich.tablebase;

import dev.michalrelich.tablebase.ui.Board;
import dev.michalrelich.tablebase.ui.Piece;

public class Main {

    static void main() {
        Board  board = new Board();
        System.out.println(board);

        board.addToBoard(Piece.BISHOP, 7, 5, false);
        System.out.println(board);
    }
}
