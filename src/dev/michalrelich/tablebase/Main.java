package dev.michalrelich.tablebase;

import dev.michalrelich.tablebase.frontend.Board;
import dev.michalrelich.tablebase.frontend.Piece;

public class Main {

    static void main() {
        Board board = new Board();
        board.launchApp();

        board.addToBoard(new Piece(Piece.PieceType.BISHOP, Piece.PieceColor.WHITE), 7, 5);
        board.addToBoard(new Piece(Piece.PieceType.BISHOP, Piece.PieceColor.WHITE), 6, 5);
        board.launchApp();

        for (int i = 0; i <= 10; i++) {

        }

    }
}
