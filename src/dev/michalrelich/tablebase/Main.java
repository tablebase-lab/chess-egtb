package dev.michalrelich.tablebase;

import dev.michalrelich.tablebase.frontend.Board;
import dev.michalrelich.tablebase.frontend.Piece;

public class Main {

    static void main() {
        Board board = new Board();
        System.out.println(board);

        board.addToBoard(new Piece(Piece.PieceType.BISHOP, Piece.PieceColor.WHITE), 7, 5);
        System.out.println(board);

        board.launchApp();

        for (int i = 0; i <= 7; i++) {
            System.out.println(board.getBoard()[6][4]);
        }

//        for (int i = 0; i <= 63; i++) {
//            App.loadPiece(new Piece(Piece.PieceType.PAWN, Piece.PieceColor.BLACK), i);
//        }
//
//        App.launch();
    }
}
