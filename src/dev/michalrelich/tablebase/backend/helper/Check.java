package dev.michalrelich.tablebase.backend.helper;

public class Check {
    // returns a 0 if no one is in check, a 1 if white, a 2 if black, a 3 if both

    // too long of a method, needs shortening
    public static int isInCheck(int[] pieces) {

        int whiteKing = pieces[1];
        int blackKing = pieces[2];

        boolean whiteCheck = false;
        boolean blackCheck = false;

        boolean isBlack = false;
        for (int i = 3; i < pieces.length; i++) {
            int piece = pieces[i];

            if (piece == 9) {
                isBlack = true;
                continue;
            }

            boolean whiteCheckPre = false;
            boolean blackCheckPre = false;

            switch (piece / 100) {
                case 1 -> {
                    if (isBlack) {
                        whiteCheckPre = DirectionCheck.queen(piece, whiteKing);
                    } else {
                        blackCheckPre = DirectionCheck.queen(piece, blackKing);
                    }
                }
                case 2 -> {
                    if (isBlack) {
                        whiteCheckPre = DirectionCheck.rook(piece, whiteKing);
                    } else {
                        blackCheckPre = DirectionCheck.rook(piece, blackKing);
                    }
                }
                case 3 -> {
                    if (isBlack) {
                        whiteCheckPre = DirectionCheck.bishop(piece, whiteKing);
                    } else {
                        blackCheckPre = DirectionCheck.bishop(piece, blackKing);
                    }
                }
                case 4 -> {
                    if (isBlack) {
                        whiteCheckPre = DirectionCheck.knight(piece, whiteKing);
                    } else {
                        blackCheckPre = DirectionCheck.knight(piece, blackKing);
                    }
                }
                case 5 -> {
                    if (isBlack) {
                        whiteCheckPre = DirectionCheck.pawn(piece, whiteKing);
                    } else {
                        blackCheckPre = DirectionCheck.pawn(piece, blackKing);
                    }
                }
            }
            if (whiteCheckPre) whiteCheck = true;
            if (blackCheckPre) blackCheck = true;
        }

        if (whiteCheck && blackCheck) return 3;
        else if (whiteCheck) return 1;
        else if (blackCheck) return 2;
        else return 0;
    }
}
