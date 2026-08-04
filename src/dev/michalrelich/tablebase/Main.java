package dev.michalrelich.tablebase;

import dev.michalrelich.tablebase.backend.helper.GaussHelper;
import dev.michalrelich.tablebase.frontend.Board;
import dev.michalrelich.tablebase.frontend.Piece;
import dev.michalrelich.tablebase.gaussfunction.GaussFunction;

import java.util.Arrays;
import java.util.Random;

public class Main {

    private static final Random random = new Random();

    static void main() {
        Board board = new Board(Piece.PieceColor.BLACK);

//        addRandomKings(board);
//        addRandomPieces(board);

        board.addToBoard(new Piece(Piece.PieceType.KING, Piece.PieceColor.WHITE), 62);
        board.addToBoard(new Piece(Piece.PieceType.KING, Piece.PieceColor.BLACK), 60);
        board.addToBoard(new Piece(Piece.PieceType.PAWN, Piece.PieceColor.WHITE), 5, 6);
        board.addToBoard(new Piece(Piece.PieceType.PAWN, Piece.PieceColor.BLACK), 5,7);
        board.addToBoard(new Piece(Piece.PieceType.QUEEN, Piece.PieceColor.WHITE), 2);

        long gauss = GaussFunction.gaussFunction(board, true);
        System.out.println(Arrays.toString(GaussHelper.getPiecesArr(gauss)));
        board.launchApp();

//        Board inverse = GaussFunction.inverse(gauss);
//        inverse.launchApp();

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
