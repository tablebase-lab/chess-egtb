package dev.michalrelich.tablebase.backend.positioncheck;

// GaussGenerator will generate a number with the correct digits (INCLUDING REPEATING ONES)
// CLASS DOESN'T VALIDATE EN PASSANTS


import dev.michalrelich.tablebase.backend.Constants;
import dev.michalrelich.tablebase.backend.helper.Check;
import dev.michalrelich.tablebase.backend.helper.GaussHelper;

public class PositionCheck {

    public static boolean checkPosition(long gauss) {
        int[] pieces = GaussHelper.getPiecesArr(gauss);
        return piecesOnSameSquareCheck(pieces) && kingsCheck(pieces) && pawnsCheck(pieces) && checkCheck(gauss);
    }

    // checks for pieces on same square
    private static boolean piecesOnSameSquareCheck(int[] pieces) {
        for (int i = Constants.DELIMITER_INDEX + 1; i < pieces.length; i++) {
            for (int j = Constants.DELIMITER_INDEX + 1; j < pieces.length; j++) {
                if (pieces[i] % 100 == pieces[j] % 100 && i != j) return false;
            }
        }

        return true;
    }

    private static boolean kingsCheck(int[] pieces) {
        int kingOne = pieces[Constants.WHITE_KING_INDEX];
        int kingTwo = pieces[Constants.BLACK_KING_INDEX];

        return !DirectionCheck.king(kingOne, kingTwo);
    }

    private static boolean pawnsCheck(int[] pieces) {
        for (int i = Constants.DELIMITER_INDEX + 1; i < pieces.length; i++) {
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
