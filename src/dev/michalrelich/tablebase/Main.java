package dev.michalrelich.tablebase;

import dev.michalrelich.tablebase.backend.Constants;
import dev.michalrelich.tablebase.backend.move.Move;
import dev.michalrelich.tablebase.frontend.Board;
import dev.michalrelich.tablebase.frontend.Piece;
import dev.michalrelich.tablebase.gaussfunction.GaussFunction;

import java.util.Random;

import static dev.michalrelich.tablebase.frontend.Piece.PieceColor.BLACK;
import static dev.michalrelich.tablebase.frontend.Piece.PieceColor.WHITE;
import static dev.michalrelich.tablebase.frontend.Piece.PieceType.KING;
import static dev.michalrelich.tablebase.frontend.Piece.PieceType.PAWN;

public class Main {

    private static final Random random = new Random();

    // todo: limit conversions from int[] to long OR (ideally) use bit-packing

    static void main() {
        Board b = new Board(WHITE);

        b.addToBoard(new Piece(KING, WHITE), 1);
        b.addToBoard(new Piece(KING, BLACK), 3);
        b.addToBoard(new Piece(PAWN, WHITE), 12);
        b.addToBoard(new Piece(PAWN, WHITE), 48);
        b.addToBoard(new Piece(PAWN, BLACK), 14);

        long gauss = GaussFunction.gaussFunction(b, true);
        gauss = Move.move(gauss, 548, 56, 1);

        Board b2 = GaussFunction.inverse(gauss);
        b2.launchApp();
//
//        if (gaussMove != -1) {
//            Board bMove = GaussFunction.inverse(gaussMove);
//            bMove.launchApp();
//        }

        b.launchApp();
    }

    public static void addRandomPieces(Board board) {
        for (int i = 0; i < Constants.MAX_NON_KING_PIECES; i++) {
            Piece piece = new Piece(
                    Piece.PieceType.values()[random.nextInt(1, Piece.PieceType.values().length)],
                    Piece.PieceColor.values()[random.nextInt(2)]);
            int random1 = random.nextInt(Constants.BOARD_LENGTH) + 1;
            int random2 = random.nextInt(Constants.BOARD_LENGTH) + 1;
            System.out.println(piece + " row: " + random1 + ", col: " + random2);

            board.addToBoard(piece, random1, random2);
        }
    }

    public static void addRandomKings(Board board) {
        for (int i = 0; i <= 1; i++) {
            Piece.PieceColor color = i == 0 ? WHITE : Piece.PieceColor.BLACK;
            int row = random.nextInt(8) + 1;
            int col = random.nextInt(8) + 1;
            Piece piece = new Piece(KING, color);

            System.out.println(piece + ", row: " + row + ", col: " + col);
            board.addToBoard(piece, row, col);
        }
    }
}
