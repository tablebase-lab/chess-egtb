package dev.michalrelich.tablebase.backend.helper;

import dev.michalrelich.tablebase.frontend.Board;

// checks if two positions are on the same diagonal / horizontal / vertical line
// counts on the fact that the piece position passed are VALID!

public class DirectionCheck {

    public static boolean king(int king, int position) {
        int length = Board.BOARD_LENGTH;
        return king == position + 1 || king == position - 1 || king == position - length || king == position + length
                || king == position - length - 1 || king == position - length + 1 || king == position + length - 1
                || king == position + length + 1;
    }

    public static boolean knight(int knight, int position) {
        int length = Board.BOARD_LENGTH;

        if (knight + length * 2 + 1 == position || knight + length * 2 - 1 == position) return true;
        if (knight - length * 2 + 1 == position || knight - length * 2 - 1 == position) return true;

        if (knight + length + 2 == position || knight + length - 2 == position) return true;
        if (knight - length + 2 == position || knight - length - 2 == position) return true;

        return false;
    }

    public static boolean queen(int queen, int position) {
        return horizontalVerticalCheck(queen, position) || diagonalCheck(queen, position);
    }

    public static boolean rook(int rook, int position) {
        return DirectionCheck.horizontalVerticalCheck(rook, position);
    }

    public static boolean bishop(int bishop, int position) {
        return DirectionCheck.diagonalCheck(bishop, position);
    }

    public static boolean pawn(int pawn, int pieceTwo, boolean whiteTurn) {
        int length = Board.BOARD_LENGTH;
        return pawn + length + 1 == pieceTwo || pawn + length - 1 == pieceTwo || pawn + length == pieceTwo ||
                (pawn + length * 2 == pieceTwo && ((pawn / length == 1 && whiteTurn) ||
                        (pawn / length == length - 1 && !whiteTurn)));
    }

    public static boolean horizontalVerticalCheck(int pieceOne, int pieceTwo) {
        int length = Board.BOARD_LENGTH;
        return pieceOne / length == pieceTwo / length || pieceOne % length == pieceTwo % length;
    }

    public static boolean diagonalCheck(int pieceOne, int position) {
        int length = Board.BOARD_LENGTH;

        int i = 1;
        boolean ascendingDiagUp = pieceOne % length != length - 1 && pieceOne / length < length - 1;
        boolean ascendingDiagDown = pieceOne % length != 0 && pieceOne / length != 0;
        boolean descendingDiagUp = pieceOne % length != 0 && pieceOne / length < length - 1;
        boolean descendingDiagDown = pieceOne % length != length - 1 && pieceOne / length != 0;

        int plusDif;
        int minDif;
        while (ascendingDiagUp || ascendingDiagDown || descendingDiagUp || descendingDiagDown) {
            System.out.println("iteration: " + i);

            int difference = (length + 1) * i;

            if (ascendingDiagUp) {
                plusDif = pieceOne + difference;
                System.out.println("Check pieceTwo ascendingUp: " + plusDif);
                if (plusDif == position) return true;

                if (!(plusDif % length != length - 1 && plusDif / length < length - 1)) ascendingDiagUp = false;


            }

            if (ascendingDiagDown) {
                minDif = pieceOne - difference;
                System.out.println("Check pieceTwo ascendingDown: " + minDif);
                if (minDif == position) return true;

                if (!(minDif % length != 0 && minDif / length != 0)) ascendingDiagDown = false;

            }

            difference = (length - 1) * i;

            if (descendingDiagUp) {
                plusDif = pieceOne + difference;
                System.out.println("Check pieceTwo descendingUp: " + plusDif);
                if (plusDif == position) return true;

                if (!(plusDif % length != 0 && plusDif / length < length - 1)) descendingDiagUp = false;

            }

            if (descendingDiagDown) {
                minDif = pieceOne - difference;
                System.out.println("Check pieceTwo descendingDown: " + minDif);
                if (minDif == position) return true;

                if (!(minDif % length != length - 1 && minDif / length != 0)) descendingDiagDown = false;

            }

            i++;
        }
        return false;
    }

}
