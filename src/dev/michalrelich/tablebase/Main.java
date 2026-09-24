package dev.michalrelich.tablebase;

import dev.michalrelich.tablebase.backend.Constants;
import dev.michalrelich.tablebase.backend.helper.Check;
import dev.michalrelich.tablebase.frontend.Board;
import dev.michalrelich.tablebase.frontend.Piece;
import dev.michalrelich.tablebase.gaussfunction.GaussFunction;

import java.util.Random;

import static dev.michalrelich.tablebase.frontend.Piece.PieceColor.*;
import static dev.michalrelich.tablebase.frontend.Piece.PieceType.*;

// todo: check Check
public class Main {

    private static final Random random = new Random();

    static void main() {

        Board b5 = new Board(WHITE);
        b5.addToBoard(new Piece(KING, WHITE), 32);  // A4
        b5.addToBoard(new Piece(PAWN, BLACK), 39);  // H5
        b5.addToBoard(new Piece(KING, BLACK), 60);  // E8
        long gauss5 = GaussFunction.gaussFunction(b5, true);
        System.out.println(Check.isInCheck(gauss5));
        b5.launchApp();

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
