package dev.michalrelich.tablebase.backend.move;

import dev.michalrelich.tablebase.frontend.Board;

public class Move {

    // handles captures as well. enpassant logic is NOT here but in the move generator (if i pass enpassant movepos and it's in correct position the method will move it)
    public static void move(int gauss, int fullPieceInt, int movePos) {

    }

    public static boolean queenMove(int queenPos, int movePos) {
        return false;
    }

    private static boolean isDiagonal(int piecePos, int movePos) {
        return false;
    }

    private static boolean isHorOrVer(int piecePos, int movePos) {
        return false;
    }

    private static boolean horizontalVerticalCheck(int king, int piece) {
        int length = Board.BOARD_LENGTH;
        return king / length == piece / length || king % length == piece % length;
    }

    private static boolean diagonalCheck(int king, int piece) {
        return king - piece % Board.BOARD_LENGTH + 1 == 0 || king - piece % Board.BOARD_LENGTH - 1 == 0;
    }
}
