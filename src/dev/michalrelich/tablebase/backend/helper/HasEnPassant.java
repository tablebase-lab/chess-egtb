package dev.michalrelich.tablebase.backend.helper;

import dev.michalrelich.tablebase.frontend.Board;

public class HasEnPassant {

    // checks if a position has two pawns next to each other on rows 4,5 (depending on the specified color)

    public static boolean forLong(long gauss) { // returns the color for which en passant works, or null

        int[] pieces = GaussHelper.getPiecesArr(gauss);
        boolean whitesTurn = pieces[0] <= 2;

        for (int i = 3; i < Board.MAX_PIECE_COUNT + 4; i++) { // 2 kings + black/white delimiter + turn specifier
            if (!validPieceCheck(pieces[i], whitesTurn)) continue;
            for (int j = i; j < Board.MAX_PIECE_COUNT + 4; j++) {
                if (!validPieceCheck(pieces[j], whitesTurn)) continue;

                if (pieces[i] - pieces[j] == 1 || pieces[i] - pieces[j] == -1) {// for example 557 - 556
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean validPieceCheck(int piece, boolean whitesTurn) {
        if (GaussHelper.getLongByIndex(piece, 0, 1) != 5 ) return false;
        int row = whitesTurn ? Board.BOARD_LENGTH - 3 : 4;

        return GaussHelper.getLongByIndex(piece, 1) / Board.BOARD_LENGTH + 1 == row;
    }
}
