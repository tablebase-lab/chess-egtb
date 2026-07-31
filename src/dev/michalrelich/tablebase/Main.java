package dev.michalrelich.tablebase;

import dev.michalrelich.tablebase.frontend.Board;
import dev.michalrelich.tablebase.frontend.Piece;
import dev.michalrelich.tablebase.gaussfunction.GaussFunction;

import java.util.Random;

public class Main {

    static void main() {

        Random random = new Random();
        Board board = new Board();

        board.addToBoard(new Piece(Piece.PieceType.KING, Piece.PieceColor.BLACK),
                random.nextInt(8) + 1, random.nextInt(8) + 1);
        board.addToBoard(new Piece(Piece.PieceType.KING, Piece.PieceColor.WHITE),
                random.nextInt(8) + 1, random.nextInt(8) + 1);

        for (int i = 0; i <= 2; i++) {
            Piece piece = new Piece(Piece.PieceType.values()[random.nextInt(Piece.PieceType.values().length)],
                    Piece.PieceColor.values()[random.nextInt(2)]);
            int random1 = random.nextInt(8) + 1;
            int random2 = random.nextInt(8) + 1;
            System.out.println(piece + " row: " + random1 + ", col: " + random2);

            board.addToBoard(piece, random1, random2);
        }

        GaussFunction.gaussFunction(board, true);
        board.launchApp();
    }
}
