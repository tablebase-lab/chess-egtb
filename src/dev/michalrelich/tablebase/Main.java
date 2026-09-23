package dev.michalrelich.tablebase;

import dev.michalrelich.tablebase.backend.Constants;
import dev.michalrelich.tablebase.frontend.Board;
import dev.michalrelich.tablebase.frontend.Piece;
import dev.michalrelich.tablebase.gaussfunction.GaussFunction;

import java.util.Random;

import static dev.michalrelich.tablebase.frontend.Piece.PieceColor.BLACK;
import static dev.michalrelich.tablebase.frontend.Piece.PieceColor.WHITE;
import static dev.michalrelich.tablebase.frontend.Piece.PieceType.*;

public class Main {

    // todo: finish Check!
    private static final Random random = new Random();

    static void main() throws InterruptedException {

//        for (int i = 0; i <= 63; i++) {
//                System.out.println("DirectionCheck for knight on " + 28 + " to " + i + ": " +
//                        DirectionCheck.knight(28, i));
//        }

        Board board = new Board(Piece.PieceColor.WHITE);

//        addRandomKings(board);
//        addRandomPieces(board);

        int pieceOne = 10;
        int pieceTwo = 34;
        int pieceThree = 37;

        board.addToBoard(new Piece(KING, WHITE), 24);
        board.addToBoard(new Piece(KING, BLACK), 42);
        board.addToBoard(new Piece(QUEEN, BLACK), pieceThree);
        board.addToBoard(new Piece(PAWN, WHITE), pieceOne);
        board.addToBoard(new Piece(PAWN, WHITE), pieceTwo);

        board.launchApp();

        long gauss = GaussFunction.gaussFunction(board, true);
        Board board2 = GaussFunction.inverse(gauss);
        board2.launchApp();
        System.out.println(GaussFunction.gaussFunction(board2, true));


//        long wrongGauss = 1_24_41_1_538_138L;
//        System.out.println(PositionCheck.checkPosition(wrongGauss));

//        long gaussTwo = Move.move(gauss, 200 + pieceOne, 33);
//        if (gaussTwo == -1) throw new RuntimeException("Oops");
//        Board boardTwo = GaussFunction.inverse(gaussTwo);
//        boardTwo.launchApp();
//
//        // HORSE LOGIC BROKEN?
//        for (int i = 0; i <= 63; i++) {
//            long gaussTwo = Move.move(gauss, 200 + pieceOne, i);
//            if (gaussTwo == -1) {
//                System.out.println("Cannot move " + pieceOne + " to " + i);
//                Thread.sleep(1000);
//                continue;
//            }
//            System.out.println("Moved from " + pieceOne + " to " + i);
//            Board board2 = GaussFunction.inverse(gaussTwo);
//            board2.launchApp();
//            Thread.sleep(1000);
//        }
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
