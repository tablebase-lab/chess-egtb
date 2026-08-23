package dev.michalrelich.tablebase.backend.helper;

import dev.michalrelich.tablebase.frontend.Board;

// checks if two positions are on the same diagonal / horizontal / vertical line
public class DirectionCheck {

    public static boolean horizontalVerticalCheck(int pieceOne, int pieceTwo) {
        int length = Board.BOARD_LENGTH;
        return pieceOne / length == pieceTwo / length || pieceOne % length == pieceTwo % length;
    }

    public static boolean diagonalCheck(int pieceOne, int pieceTwo) {
        int length = Board.BOARD_LENGTH;

        int i = 1;
        boolean right = true;
        boolean left = true;
        // checks the diagonal from bottom left to top right
        while (right || left) {
            if (right) {
                if (pieceOne + (length + 1) * i == pieceTwo || pieceOne + (length - 1) * i == pieceTwo) return true;
            }

            if (left) {
                if (pieceOne - (length + 1) * i == pieceTwo || pieceOne + (length - 1) * i == pieceTwo) return true;
            }

            if (i % 8 == 0) {
                left = false;
            }

            if (i % 8 == 7) {
                right = false;
            }

            i++;
        }

        return false;
    }



}
