package dev.michalrelich.tablebase.backend.positioncheck;

import dev.michalrelich.tablebase.backend.Constants;
import dev.michalrelich.tablebase.backend.helper.GaussHelper;

import static dev.michalrelich.tablebase.backend.Constants.ENP_DEF;

public class HasEnPassant {

    // checks for en passant in a position it assumes is valid. Doesn't take checks etc. into consideration
    // gauss will be called with an 8 for both methods, not that it matters

    // will be called in a position with a new move - a pawn moved by two squares.
    // there can be up to two pawns that can do the capture but the boolean doesn't care about that it just focuses on the
    // possible column
    public static boolean forMovedPawnByTwoSquares(long gauss, int movedPawnPos) {
        return false;
    }

    // done
    // returns all columns that can possibly do en passant, since we don't know, used in PositionGenerator
    public static int[] forPosition(long gauss) {
        int[] pieces = GaussHelper.getPiecesArr(gauss);
        int[] enPassantCols = {ENP_DEF, ENP_DEF, ENP_DEF, ENP_DEF, ENP_DEF}; // since theoretically no more than 5 en passants can be in one position

        // two loops, one for white pieces, second for black, so each pair is evaluated exactly once
        int starterIndex = Constants.DELIMITER_INDEX + 1;
        boolean turn = pieces[Constants.TURN_INDEX] == Constants.WHITE_TURN;

        for (int i = starterIndex; i < pieces.length; i++) {
            if (pieces[i] / 100 != 5) continue;
            int pieceOne = pieces[i] % 100;

            boolean isOneWhite = i - starterIndex < pieces[Constants.DELIMITER_INDEX];
            if (!isOneWhite) continue;

            for (int j = starterIndex; j < pieces.length; j++) {
                if (pieces[j] / 100 != 5) continue;
                int pieceTwo = pieces[j] % 100;

                boolean isTwoWhite = j - starterIndex < pieces[Constants.DELIMITER_INDEX];
                if (isTwoWhite) continue;

                int row = turn ? 4 : 3; // row as in pos / 8, so 0-7
                boolean correctRows = pieceOne / Constants.BOARD_LENGTH == pieceTwo / Constants.BOARD_LENGTH &&
                        pieceOne / Constants.BOARD_LENGTH == row;
                if (Math.abs(pieceOne - pieceTwo) == 1 && correctRows) {
                    int enPassantCol = turn ? pieceTwo % Constants.BOARD_LENGTH : pieceOne % Constants.BOARD_LENGTH;

                    for (int k = 0; k < enPassantCols.length; k++) {
                        if (enPassantCols[k] != ENP_DEF && enPassantCols[k] == enPassantCol) break;

                        if (enPassantCols[k] == ENP_DEF) {
                            enPassantCols[k] = enPassantCol;
                            break;
                        }
                    }
                }
            }
        }

        return enPassantCols;
    }
}
