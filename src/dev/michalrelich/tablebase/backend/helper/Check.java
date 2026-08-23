package dev.michalrelich.tablebase.backend.helper;

import dev.michalrelich.tablebase.frontend.Board;

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
                        whiteCheckPre = queenCheck(whiteKing, piece);
                    } else {
                        blackCheckPre = queenCheck(blackKing, piece);
                    }
                }
                case 2 -> {
                    if (isBlack) {
                        whiteCheckPre = rookCheck(whiteKing, piece);
                    } else {
                        blackCheckPre = rookCheck(blackKing, piece);
                    }
                }
                case 3 -> {
                    if (isBlack) {
                        whiteCheckPre = bishopCheck(whiteKing, piece);
                    } else {
                        blackCheckPre = bishopCheck(blackKing, piece);
                    }
                }
                case 4 -> {
                    if (isBlack) {
                        whiteCheckPre = knightCheck(whiteKing, piece);
                    } else {
                        blackCheckPre = knightCheck(blackKing, piece);
                    }
                }
                case 5 -> {
                    if (isBlack) {
                        whiteCheckPre = pawnCheck(whiteKing, piece);
                    } else {
                        blackCheckPre = pawnCheck(blackKing, piece);
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

    public static boolean queenCheck(int king, int queen) {
        return horizontalVerticalCheck(king, queen) || diagonalCheck(king, queen);
    }

    public static boolean rookCheck(int king, int rook) {
        return horizontalVerticalCheck(king, rook);
    }

    public static boolean bishopCheck(int king, int bishop) {
        return diagonalCheck(king, bishop);
    }

    public static boolean knightCheck(int king, int knight) {
        int length = Board.BOARD_LENGTH;

        if (knight + length * 2 + 1 == king || knight + length * 2 - 1 == king) return true;
        if (knight - length * 2 + 1 == king || knight - length * 2 - 1 == king) return true;

        if (knight + length + 2 == king || knight + length - 2 == king) return true;
        if (knight - length + 2 == king || knight - length - 2 == king) return true;

        return false;
    }

    public static boolean pawnCheck(int king, int pawn) {
        return pawn + Board.BOARD_LENGTH + 1 == king || pawn + Board.BOARD_LENGTH - 1 == king;
    }

    private static boolean horizontalVerticalCheck(int king, int piece) {
        return DirectionCheck.horizontalVerticalCheck(king, piece);
    }

    // don't like this
    private static boolean diagonalCheck(int king, int piece) {


        return king - piece % Board.BOARD_LENGTH + 1 == 0 || king - piece % Board.BOARD_LENGTH - 1 == 0;
    }
}
