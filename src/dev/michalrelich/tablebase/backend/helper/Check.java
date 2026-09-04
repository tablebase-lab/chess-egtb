package dev.michalrelich.tablebase.backend.helper;

import dev.michalrelich.tablebase.backend.move.Move;

public class Check {
    // returns a 0 if no one is in check, a 1 if white, a 2 if black, a 3 if both

    // too long of a method, needs shortening
    public static int isInCheck(long gauss) {

        int[] pieces = GaussHelper.getPiecesArr(gauss);

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

            switch (pieces[i] / 100) {
                case 1 -> {
                    if (isBlack) {
                        whiteCheckPre = Move.move(gauss, pieces[i], whiteKing, false) != 1; // IDK WHAT TO DO WITH ENPASSANT HERE!
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
                        whiteCheckPre = DirectionCheck.pawn(piece, whiteKing, false);
                    } else {
                        blackCheckPre = DirectionCheck.pawn(piece, blackKing, true);
                    }
                }
            }
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
