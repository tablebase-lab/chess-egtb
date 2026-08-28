package dev.michalrelich.tablebase;

import dev.michalrelich.tablebase.backend.move.Move;
import dev.michalrelich.tablebase.exceptions.InvalidBoardException;
import dev.michalrelich.tablebase.frontend.Board;
import dev.michalrelich.tablebase.frontend.Piece;
import dev.michalrelich.tablebase.gaussfunction.GaussFunction;

import java.util.Random;

import static dev.michalrelich.tablebase.frontend.Piece.PieceColor.*;
import static dev.michalrelich.tablebase.frontend.Piece.PieceType.*;

public class Main {

    private static final Random random = new Random();

    static void main() {
        Board board = new Board(Piece.PieceColor.WHITE);

//        addRandomKings(board);
//        addRandomPieces(board);

        int pieceOne = 12;
        int pieceTwo = 63;
        board.addToBoard(new Piece(KING, WHITE), 3);
        board.addToBoard(new Piece(KING, BLACK), 5);
        board.addToBoard(new Piece(PAWN, WHITE), 2, 1);
        board.addToBoard(new Piece(ROOK, WHITE), pieceOne);
        board.addToBoard(new Piece(QUEEN, WHITE), pieceTwo);
        board.launchApp();

        long gauss = GaussFunction.gaussFunction(board, true);
        long gaussTwo = Move.move(gauss, 200 + pieceOne, 15);
        if (gaussTwo == -1) throw new InvalidBoardException("Oops");
        Board boardTwo = GaussFunction.inverse(gaussTwo);
        boardTwo.launchApp();
    }

    public static void addRandomPieces(Board board) {
        for (int i = 0; i < Board.MAX_PIECE_COUNT; i++) {
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
            Piece.PieceColor color = i == 0 ? WHITE : Piece.PieceColor.BLACK;
            int row = random.nextInt(8) + 1;
            int col = random.nextInt(8) + 1;
            Piece piece = new Piece(KING, color);

            System.out.println(piece + ", row: " + row + ", col: " + col);
            board.addToBoard(piece, row, col);
        }
    }
}
