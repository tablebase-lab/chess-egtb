package dev.michalrelich.tablebase.backend.helper;

// GaussGenerator will generate a number with the correct digits
//
public class PositionCheck {

    public static boolean checkPosition(long gauss) {

        int[] pieces = GaussHelper.getPiecesArr(gauss);

        if (!checkPiecePosition(pieces)) return false;

        return true;

    }

    private static boolean checkPiecePosition(int[] pieces) {
        if (pieces.length < 5) return false; // the en passant / turn prefix + position of 2 kings + the 9 delimiter + atleast one piece

        if (!(pieces[0] >= 1 && pieces[0] <= 4)) return false;

        if (pieces[1] < 10 || pieces[1] >= 100 || pieces[2] < 10 || pieces[2] >= 100) return false;
        if ((pieces[1] >= 64 && pieces[1] < 90) || (pieces[2] >= 64 && pieces[2] < 90)) return false;

        boolean hasDelimiter = false;
        for (int i = 2; i < pieces.length; i++) {
            if (pieces[i] == 9 && !hasDelimiter) {
                hasDelimiter = true;
                continue;
            } else if (pieces[i] == 9 && hasDelimiter) {
                return false;
            }

            if (pieces[i] % 100 > 63 || pieces[i] < 100 || pieces[i] > 563) {
                return false;
            }

            for (int j = 3; j < pieces.length; j++) {
                if (pieces[i] == pieces[j]) return false;
            }
        }

        return true;
    }

    private static void kingsCheck(int[] pieces) {

    }

    private static void pawnsCheck(int[] pieces) {

    }
}
