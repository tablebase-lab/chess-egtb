package dev.michalrelich.tablebase;

import dev.michalrelich.tablebase.backend.helper.LongHelper;
import dev.michalrelich.tablebase.frontend.Board;
import dev.michalrelich.tablebase.frontend.Piece;

import java.util.Random;

public class Main {

    private static final Random random = new Random();

    static void main() {

//        Board board = new Board();
//
//        addRandomKings(board);
//        addRandomPieces(board);
//
////        board.addToBoard(new Piece(Piece.PieceType.KING, Piece.PieceColor.WHITE), 62);
////        board.addToBoard(new Piece(Piece.PieceType.KING, Piece.PieceColor.BLACK), 1);
////        board.addToBoard(new Piece(Piece.PieceType.ROOK, Piece.PieceColor.WHITE), 63);
////        board.addToBoard(new Piece(Piece.PieceType.BISHOP, Piece.PieceColor.WHITE), 0);
////        board.addToBoard(new Piece(Piece.PieceType.QUEEN, Piece.PieceColor.WHITE), 61);
//
//        long gauss = GaussFunction.gaussFunction(board, true);
//        board.launchApp();
//
//        Board inverse = GaussFunction.inverse(gauss);
//        inverse.launchApp();

        System.out.println(LongHelper.getLongByIndex(123456789987654321L, 0, 1));
//            System.out.println(10 + " length: " + LongHelper.getLongLength(0));
    }

    public static void addRandomPieces(Board board) {
        for (int i = 0; i <= 2; i++) {
            Piece piece = new Piece(
                    Piece.PieceType.values()[random.nextInt(1, Piece.PieceType.values().length)],
                    Piece.PieceColor.values()[random.nextInt(2)]);
            int random1 = random.nextInt(Board.BOARD_LENGTH) + 1;
            int random2 = random.nextInt(Board.BOARD_LENGTH) + 1;
            System.out.println(piece + " row: " + random1 + ", col: " + random2);

            board.addToBoard(piece, random1, random2);
        }
    }

    public static void addRandomKings(Board board) {
        for (int i = 0; i <= 1; i++) {
            Piece.PieceColor color = i == 0 ? Piece.PieceColor.WHITE : Piece.PieceColor.BLACK;
            int row = random.nextInt(8) + 1;
            int col = random.nextInt(8) + 1;
            Piece piece = new Piece(Piece.PieceType.KING, color);

            System.out.println(piece + ", row: " + row + ", col: " + col);
            board.addToBoard(piece, row, col);
        }
    }
}
