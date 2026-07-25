package dev.michalrelich.tablebase;

import dev.michalrelich.tablebase.frontend.Board;
import dev.michalrelich.tablebase.frontend.Piece;
import dev.michalrelich.tablebase.frontend.swing.App;

public class Main {

    static void main() {
        Board  board = new Board();
        System.out.println(board);

        board.addToBoard(new Piece(Piece.PieceType.BISHOP, Piece.PieceColor.WHITE), 7, 5);
        System.out.println(board);

        for (int i = 0; i <= 63; i++) {
            App.loadPiece(new Piece(Piece.PieceType.PAWN, Piece.PieceColor.BLACK), i);
        }

        App.launch();
    }
}
