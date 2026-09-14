package dev.michalrelich.tablebase.backend.helper;

import dev.michalrelich.tablebase.backend.move.Move;
import dev.michalrelich.tablebase.frontend.Board;

public class Check {
    // returns a 0 if no one is in check, a 1 if white, a 2 if black, a 3 if both

    // too long of a method, needs shortening
    public static int isInCheck(long gauss) {

        int[] pieces = GaussHelper.getPiecesArr(gauss);
        int length = Board.BOARD_LENGTH;

        int whiteKing = pieces[1] > 63 ? pieces[1] % 10 : pieces[1];
        int blackKing = pieces[2] > 63 ? pieces[2] % 10 : pieces[1];

        boolean whiteCheck = false;
        boolean blackCheck = false;

        boolean isBlack = false;
        for (int i = 3; i < pieces.length; i++) {
            int piece = pieces[i] % 100;

            if (piece == 9) {
                isBlack = true;
                continue;
            }

            boolean whiteCheckPre = false;
            boolean blackCheckPre = false;

            if (isBlack) whiteCheckPre = Move.canMove(pieces, pieces[i], whiteKing) &&
                    Move.checkInBetweenPieces(pieces, length,pieces[i], whiteKing);
            else blackCheckPre = Move.canMove(pieces, pieces[i], blackKing) &&
                    Move.checkInBetweenPieces(pieces, length,pieces[i], blackKing);

            if (whiteCheckPre) whiteCheck = true;
            if (blackCheckPre) blackCheck = true;
        }

        if (whiteCheck && blackCheck) {
            return 3;
        }

        else if (whiteCheck) return 1;
        else if (blackCheck) return 2;
        else return 0;
    }
}
