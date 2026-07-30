package dev.michalrelich.tablebase;

import dev.michalrelich.tablebase.frontend.Board;
import dev.michalrelich.tablebase.frontend.Piece;
import dev.michalrelich.tablebase.gaussfunction.GaussFunction;

public class Main {

    static void main() {

        Board board = new Board();

        board.addToBoard(new Piece(Piece.PieceType.KING, Piece.PieceColor.BLACK), 1, 5);
        board.addToBoard(new Piece(Piece.PieceType.KING, Piece.PieceColor.WHITE), 1, 4);

        board.addToBoard(new Piece(Piece.PieceType.PAWN, Piece.PieceColor.BLACK), 4, 2);
        board.addToBoard(new Piece(Piece.PieceType.KNIGHT, Piece.PieceColor.WHITE), 3, 3);
        board.addToBoard(new Piece(Piece.PieceType.ROOK, Piece.PieceColor.BLACK), 2, 3);

        GaussFunction.gaussFunction(board, true);
        board.launchApp();

//        Random random = new Random();
//        for (int i = 0; i <= 1000; i++) {
//            Piece piece = new Piece(Piece.PieceType.values()[random.nextInt(Piece.PieceType.values().length)],
//                    Piece.PieceColor.values()[random.nextInt(2)]);
//            int random1 = random.nextInt(8) + 1;
//            int random2 = random.nextInt(8) + 1;
//            System.out.println(piece + " row: " + random1 + ", col: " + random2);
//
//            board.addToBoard(piece, random1, random2);
//        }
//
//        board.launchApp();
//
//        GaussFunction.toLong(board);

//        Piece piece1 = new Piece(Piece.PieceType.BISHOP, Piece.PieceColor.WHITE);
//        Piece piece2 = new Piece(Piece.PieceType.KNIGHT, Piece.PieceColor.WHITE);
//
//        System.out.println(piece1.compareTo(piece2));
    }
}
