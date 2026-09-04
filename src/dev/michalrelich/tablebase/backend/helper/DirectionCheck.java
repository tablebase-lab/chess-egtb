package dev.michalrelich.tablebase.backend.helper;

import dev.michalrelich.tablebase.frontend.Board;

// checks whether a move for the piece is technically possible
// asserts both the parameters are 0-63, and that the parameters aren't the same (check is performed in Move)

public class DirectionCheck {

    public static boolean king(int king, int position) {
        int length = Board.BOARD_LENGTH;

        int colDiff = Math.abs(king % length - position % length);
        int rowDiff = Math.abs(king / length - position / length);
        return colDiff <= 1 && rowDiff <= 1;
    }

    public static boolean knight(int knight, int position) {
        int length = Board.BOARD_LENGTH;
        boolean canMove = false;

        // since the condition is knight +- length * 2 +- 1 == pos, we just put pos to the left and 1 to right
        if (Math.abs(knight + length * 2 - position) == 1 || Math.abs(knight - length * 2 - position) == 1) canMove = true;
        if (Math.abs(knight + length - position) == 2 || Math.abs(knight - length - position) == 2) canMove = true;

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

    public static boolean pawn(int pawn, int position, boolean whiteTurn) {
        int length = Board.BOARD_LENGTH;
        int colDiff = Math.abs(pawn % length - position % length); // so they don't jump to opposite columns

        boolean verticalMove = pawn + length == position ||
                (pawn + length * 2 == position && ((pawn / length == 1 && whiteTurn) ||
                        (pawn / length == length - 1 && !whiteTurn)));

        boolean diagonalMove = pawn + length + 1 == position || pawn + length - 1 == position;

        return colDiff <= 1 && (verticalMove || diagonalMove);
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
