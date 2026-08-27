package dev.michalrelich.tablebase.backend.helper;

import dev.michalrelich.tablebase.frontend.Board;

public class HasEnPassant {

    // checks if a position can have en passant --> two pawns on the right rank and no pawns behind the pawn that was pushed up.
    public static boolean forLong(long gauss) {

        int[] pieces = GaussHelper.getPiecesArr(gauss);
        boolean whitesTurn = pieces[0] <= 2;

        boolean isPawnOneWhite = true;

        for (int i = 3; i < Board.MAX_PIECE_COUNT + 4; i++) { // 2 kings + black/white delimiter + turn specifier
            if (!validPieceCheck(pieces[i], whitesTurn)) continue;
            if (pieces[i] == 9) isPawnOneWhite = false;

            boolean isPawnTwoWhite = true;
            for (int j = i; j < Board.MAX_PIECE_COUNT + 4; j++) {

                if (!validPieceCheck(pieces[j], whitesTurn)) continue;
                if (pieces[j] == 9) isPawnTwoWhite = false;

                if (pieces[i] - pieces[j] == 1 || pieces[i] - pieces[j] == -1) {// for example 557 - 556
                    if (isPawnOneWhite == isPawnTwoWhite) continue;

                    int chosenOne = isPawnOneWhite != whitesTurn ? pieces[i] : pieces[j];
                    if (whitesTurn) {
                        for (int piece : pieces) {
                            if (piece == chosenOne + Board.BOARD_LENGTH || piece == chosenOne + Board.BOARD_LENGTH * 2) break;
                            return true;
                        }
                    } else {
                        for (int piece : pieces) {
                            if (piece == chosenOne - Board.BOARD_LENGTH || piece == chosenOne - Board.BOARD_LENGTH * 2) break;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private static boolean validPieceCheck(int piece, boolean whitesTurn) {
        if (piece / 100 != 5) return false;
        int row = whitesTurn ? Board.BOARD_LENGTH - 3 : 4;

        return GaussHelper.getLongByIndex(piece, 1) / Board.BOARD_LENGTH + 1 == row;
    }
}
