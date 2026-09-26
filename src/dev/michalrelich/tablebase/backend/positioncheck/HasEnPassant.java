package dev.michalrelich.tablebase.backend.positioncheck;

import dev.michalrelich.tablebase.backend.Constants;
import dev.michalrelich.tablebase.backend.helper.GaussHelper;

import static dev.michalrelich.tablebase.backend.Constants.ENP_DEFAULT;

public class HasEnPassant {

    // checks for en passant in a position it assumes is valid. Doesn't take checks etc. into consideration
    // gauss will be called with an 8 for both methods, not that it matters

    // done
    // when a pawn moves by 2, this method gets called. just check if there's a pawn of another color on the same row next to the moved pawn
    public static boolean forMovedPawnByTwoSquares(int[] pieces, int movedPawnPos) {
        boolean isMovedWhite = movedPawnPos / Constants.BOARD_LENGTH == 3; // moved from row 1 to 3

        int starterIndex = Constants.DELIMITER_INDEX + 1;
        for (int i = starterIndex; i < pieces.length; i++) {
            if (pieces[i] / 100 != 5) continue;

            int piece = pieces[i] % 100;
            boolean isPieceWhite = i - starterIndex < pieces[Constants.DELIMITER_INDEX];

            boolean difColor = isPieceWhite != isMovedWhite;
            boolean sameRow = piece / Constants.BOARD_LENGTH == movedPawnPos / Constants.BOARD_LENGTH;
            boolean nextToEachOther = Math.abs(piece - movedPawnPos) == 1; // still needs row check, could jump by one row even with 1 difference

            if (difColor && sameRow && nextToEachOther) return true;
        }

        return false;
    }

    // done
    // returns all columns that can possibly do en passant, since we don't know, used in PositionGenerator
    public static int[] forPosition(long gauss) {
        int[] pieces = GaussHelper.getPiecesArr(gauss);
        int[] enPassantCols = {ENP_DEFAULT, ENP_DEFAULT, ENP_DEFAULT, ENP_DEFAULT, ENP_DEFAULT}; // since theoretically no more than 5 en passants can be in one position

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
                        if (enPassantCols[k] != ENP_DEFAULT && enPassantCols[k] == enPassantCol) break;

                        if (enPassantCols[k] == ENP_DEFAULT) {
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
