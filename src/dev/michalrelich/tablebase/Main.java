package dev.michalrelich.tablebase;

import dev.michalrelich.tablebase.backend.move.Move;
import dev.michalrelich.tablebase.frontend.Board;
import dev.michalrelich.tablebase.frontend.Piece;
import dev.michalrelich.tablebase.gaussfunction.GaussFunction;

import java.util.Random;

import static dev.michalrelich.tablebase.frontend.Piece.PieceColor.BLACK;
import static dev.michalrelich.tablebase.frontend.Piece.PieceColor.WHITE;
import static dev.michalrelich.tablebase.frontend.Piece.PieceType.*;

public class Main {

    private static final Random random = new Random();

    static void main() throws InterruptedException {
        Board board = new Board(Piece.PieceColor.WHITE);

//        addRandomKings(board);
//        addRandomPieces(board);

        int pieceOne = 56;
        int pieceTwo = 63;
        board.addToBoard(new Piece(KING, WHITE), 20);
        board.addToBoard(new Piece(KING, BLACK), 8);
        board.addToBoard(new Piece(PAWN, WHITE), 3, 3);
        board.addToBoard(new Piece(ROOK, WHITE), pieceOne);
        board.addToBoard(new Piece(QUEEN, WHITE), pieceTwo);
        board.launchApp();

        long gauss = GaussFunction.gaussFunction(board, true);
//        long gaussTwo = Move.move(gauss, 200 + pieceOne, 33);
//        if (gaussTwo == -1) throw new RuntimeException("Oops");
//        Board boardTwo = GaussFunction.inverse(gaussTwo);
//        boardTwo.launchApp();

        for (int i = 0; i <= 63; i++) {
            long gaussTwo = Move.move(gauss, 200 + pieceOne, i);
            if (gaussTwo == -1) {
                System.out.println("Cannot move " + pieceOne + " to " + i);
                Thread.sleep(1000);
                continue;
            }
            System.out.println("Moved from " + pieceOne + " to " + i);
            Board board2 = GaussFunction.inverse(gaussTwo);
            board2.launchApp();
            Thread.sleep(1000);
        }
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
