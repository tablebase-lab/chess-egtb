package dev.michalrelich.tablebase.backend.helper;

import dev.michalrelich.tablebase.frontend.Board;

// checks if two positions are on the same diagonal / horizontal / vertical line
// counts on the fact that the piece position passed are VALID!

public class DirectionCheck {
    
    public static boolean knight(int knight, int pieceTwo) {
        int length = Board.BOARD_LENGTH;

        if (knight + length * 2 + 1 == pieceTwo || knight + length * 2 - 1 == pieceTwo) return true;
        if (knight - length * 2 + 1 == pieceTwo || knight - length * 2 - 1 == pieceTwo) return true;

        if (knight + length + 2 == pieceTwo || knight + length - 2 == pieceTwo) return true;
        if (knight - length + 2 == pieceTwo || knight - length - 2 == pieceTwo) return true;

        return false;
    }

    public static boolean queen(int queen, int pieceTwo) {
        return horizontalVerticalCheck(queen, pieceTwo) || diagonalCheck(queen, pieceTwo);
    }

    public static boolean rook(int rook, int pieceTwo) {
        return DirectionCheck.horizontalVerticalCheck(rook, pieceTwo);
    }

    public static boolean bishop(int bishop, int pieceTwo) {
        return DirectionCheck.diagonalCheck(bishop, pieceTwo);
    }

    public static boolean pawn(int pawn, int pieceTwo) {
        return pawn + Board.BOARD_LENGTH + 1 == pieceTwo || pawn + Board.BOARD_LENGTH - 1 == pieceTwo;
    }

    public static boolean horizontalVerticalCheck(int pieceOne, int pieceTwo) {
        int length = Board.BOARD_LENGTH;
        return pieceOne / length == pieceTwo / length || pieceOne % length == pieceTwo % length;
    }

    public static boolean diagonalCheck(int pieceOne, int pieceTwo) {
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
                System.out.println("ChecpieceTwo ascendingUp: " + plusDif);
                if (plusDif == pieceTwo) return true;

                if (!(plusDif % length != length - 1 && plusDif / length < length - 1)) ascendingDiagUp = false;


            }

            if (ascendingDiagDown) {
                minDif = pieceOne - difference;
                System.out.println("ChecpieceTwo ascendingDown: " + minDif);
                if (minDif == pieceTwo) return true;

                if (!(minDif % length != 0 && minDif / length != 0)) ascendingDiagDown = false;

            }

            difference = (length - 1) * i;

            if (descendingDiagUp) {
                plusDif = pieceOne + difference;
                System.out.println("ChecpieceTwo descendingUp: " + plusDif);
                if (plusDif == pieceTwo) return true;

                if (!(plusDif % length != 0 && plusDif / length < length - 1)) descendingDiagUp = false;

            }

            if (descendingDiagDown) {
                minDif = pieceOne - difference;
                System.out.println("ChecpieceTwo descendingDown: " + minDif);
                if (minDif == pieceTwo) return true;

                if (!(minDif % length != length - 1 && minDif / length != 0)) descendingDiagDown = false;

            }

            i++;
        }
        return false;
    }

}
