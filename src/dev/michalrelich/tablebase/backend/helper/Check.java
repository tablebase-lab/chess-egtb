package dev.michalrelich.tablebase.backend.helper;

import dev.michalrelich.tablebase.backend.Constants;
import dev.michalrelich.tablebase.backend.move.Move;

public class Check {
    // returns a 0 if no one is in check, a 1 if white, a 2 if black, a -1 if the check is impossible (both or wrong side)
    // so figures out everything you need to know check-wise about a position
    // DONE

    public static int isInCheck(long gauss) {

        int[] pieces = GaussHelper.getPiecesArr(gauss);
        int length = Constants.BOARD_LENGTH;

        int whiteKing = pieces[1];
        int blackKing = pieces[2];

        boolean whiteCheck = false;
        boolean blackCheck = false;

        int delimiter = pieces[3];
        for (int i = 4; i < pieces.length; i++) {
            int iPiece = pieces[i];

            boolean isPieceWhite = i - 4 < delimiter;

            boolean whiteCheckPre = false;
            boolean blackCheckPre = false;
            if (iPiece / 100 != 5) {
                if (isPieceWhite) blackCheckPre = Move.canMove(pieces, iPiece, blackKing) &&
                        Move.checkInBetweenPieces(pieces, length, iPiece, blackKing);
                else whiteCheckPre = Move.canMove(pieces, iPiece, whiteKing) &&
                        Move.checkInBetweenPieces(pieces, length, iPiece, whiteKing);
            } else {
                if (isPieceWhite) blackCheckPre = pawnCheck(iPiece, pieces[2], length, true);
                else whiteCheckPre = pawnCheck(iPiece, pieces[1], length, false);
            }

            if (whiteCheckPre) whiteCheck = true;
            if (blackCheckPre) blackCheck = true;
        }

        if (whiteCheck && blackCheck) {
            return -1;
        }

        if ((whiteCheck && pieces[0] > 2) || (blackCheck && pieces[0] <= 2)) return -1;

        else if (whiteCheck) return 1;
        else if (blackCheck) return 2;
        else return 0;
    }

    // counts that the king is the opposite color
    public static boolean pawnCheck(int fullPawnInt, int king, int length, boolean isPawnWhite) {
        int pawn = fullPawnInt % 100;

        if (isPawnWhite) {
            return Math.abs(king - pawn - length) == 1; // king == pawn + length + 1 || king == pawn + length - 1;
        } else {
            return Math.abs(king - pawn + length) == 1; // king == pawn - length + 1 || king == pawn - length - 1;
        }
    }
}
