package dev.michalrelich.tablebase.backend.positioncheck;

// GaussGenerator will generate a number with the correct digits (INCLUDING REPEATING ONES)
// CLASS VALIDATES EN PASSANTS


import dev.michalrelich.tablebase.backend.Constants;
import dev.michalrelich.tablebase.backend.helper.Check;
import dev.michalrelich.tablebase.backend.helper.GaussHelper;

public class PositionCheck {

    public static boolean checkPosition(long gauss) {
        int[] pieces = GaussHelper.getPiecesArr(gauss);
        return checkPiecePosition(pieces) && kingsCheck(pieces) && pawnsCheck(pieces) && checkCheck(gauss);
    }

    // checks if the int values are legal and for pieces on same square
    private static boolean checkPiecePosition(int[] pieces) {
        if (pieces.length < 5) return false; // the en passant / turn prefix + position of 2 kings + the delimiter
        // + at least one piece

        if (!(pieces[0] >= 1 && pieces[0] <= 4)) return false;

        if (pieces[1] < 10 || pieces[1] >= 100 || pieces[2] < 10 || pieces[2] >= 100) return false;
        if ((pieces[1] >= 64 && pieces[1] < 90) || (pieces[2] >= 64 && pieces[2] < 90)) return false;
        if (pieces[3] < 0 || pieces[3] > Constants.MAX_NON_KING_PIECES) return false;

        for (int i = 4; i < pieces.length; i++) {

            if (pieces[i] % 100 > 63 || pieces[i] < 100 || pieces[i] > 563) {
                return false;
            }

            for (int j = 4; j < pieces.length; j++) {
                if (pieces[i] % 100 == pieces[j] % 100 && i != j) return false;
            }
        }

        return true;
    }

    private static boolean kingsCheck(int[] pieces) {
        int kingOne = pieces[1];
        int kingTwo = pieces[2];

        return !DirectionCheck.king(kingOne, kingTwo);
    }

    private static boolean pawnsCheck(int[] pieces) {
        for (int i = 3; i < pieces.length; i++) {
            if ((pieces[i] / 100) != 5) {
                continue;
            }

            if (pieces[i] % 100 < 8 || pieces[i] % 100 >= 56) return false;
        }

        return true;
    }

    private static boolean checkCheck(long gauss) {
        return Check.isInCheck(gauss) != -1;
    }
}
