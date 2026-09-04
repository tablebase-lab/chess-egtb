package dev.michalrelich.tablebase.backend.helper;

import dev.michalrelich.tablebase.frontend.Board;

// checks whether a move for the piece is technically possible (like on an otherwise empty board)
// asserts both the parameters are 0-63

public class DirectionCheck {

    // we assert position is 0 - 63 and king is 0-63 aswell
    public static boolean king(int king, int position) {
        int length = Board.BOARD_LENGTH;

        int colDiff = Math.abs(king % length - position % length);
        int rowDiff = Math.abs(king / length - position / length);
        return colDiff <= 1 && rowDiff <= 1;
        // so they are either 0 or 1, the case they are both 0 (king == position) is handled in Move.move

    }

    public static boolean knight(int knight, int position) {
        int length = Board.BOARD_LENGTH;

        boolean canMove = false;
        if (knight + length * 2 + 1 == position || knight + length * 2 - 1 == position) canMove = true;
        if (knight - length * 2 + 1 == position || knight - length * 2 - 1 == position) canMove = true;

        if (knight + length + 2 == position || knight + length - 2 == position) canMove = true;
        if (knight - length + 2 == position || knight - length - 2 == position) canMove = true;

        int colDiff = Math.abs(knight % length - position % length); // so they don't jump to opposite columns

        return colDiff <= 2 && canMove;
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

    // not done
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

    // not done
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
