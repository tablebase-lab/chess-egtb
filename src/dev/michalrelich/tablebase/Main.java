package dev.michalrelich.tablebase;

import dev.michalrelich.tablebase.ui.Board;
import dev.michalrelich.tablebase.ui.Piece;

public class Main {

    static void main() {
        Board  board = new Board();
        System.out.println(board);

        board.addToBoard(new Piece(Piece.PieceType.BISHOP, Piece.PieceColor.WHITE), 7, 5);
        System.out.println(board);
    }
}
