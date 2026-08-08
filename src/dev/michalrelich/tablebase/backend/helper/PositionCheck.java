package dev.michalrelich.tablebase.backend.helper;

import java.util.Arrays;

// GaussGenerator will generate a number with the correct digits
//
public class PositionCheck {

    public static boolean checkPosition(long gauss) {

        int[] pieces = GaussHelper.getPiecesArr(gauss);
        for (int i = 4; i < pieces.length; i++) {
            if (pieces[i] == 0) {
                pieces = Arrays.copyOf(pieces, i);
                break;
            }
        }

        return checkPiecePosition(pieces) && kingsCheck(pieces) && pawnsCheck(pieces) && checkCheck(pieces);
    }

    private static boolean checkPiecePosition(int[] pieces) {
        if (pieces.length < 5) return false; // the en passant / turn prefix + position of 2 kings + the 9 delimiter
        // + at least one piece

        if (!(pieces[0] >= 1 && pieces[0] <= 4)) return false;

        if (pieces[1] < 10 || pieces[1] >= 100 || pieces[2] < 10 || pieces[2] >= 100) return false;
        if ((pieces[1] >= 64 && pieces[1] < 90) || (pieces[2] >= 64 && pieces[2] < 90)) return false;

        boolean hasDelimiter = false;
        for (int i = 3; i < pieces.length; i++) {
            if (pieces[i] == 9 && !hasDelimiter) {
                hasDelimiter = true;
                continue;
            } else if (pieces[i] == 9 && hasDelimiter) {
                return false;
            }

            if (pieces[i] % 100 > 63 || pieces[i] < 100 || pieces[i] > 563) {
                return false;
            }

            for (int j = 4; j < pieces.length; j++) {
                if (pieces[i] == pieces[j]) return false;
            }
        }

        return true;
    }

    private static boolean kingsCheck(int[] pieces) {
        int kingOne = pieces[1] % 100;
        int kingTwo = pieces[2] % 100;

        if (kingOne == kingTwo + 1 || kingOne == kingTwo - 1) return false;
        if (kingOne == kingTwo + 8 || kingOne == kingTwo - 8) return false;
        if (kingOne == kingTwo + 7 || kingOne == kingTwo - 7) return false;
        if (kingOne == kingTwo + 9 || kingOne == kingTwo - 9) return false;

        return true;
    }

    private static boolean pawnsCheck(int[] pieces) {
        for (int i = 3; i < pieces.length; i++) {
            if ((pieces[i] % 100) != 5) {
                continue;
            }

            if (i < 8 || i >= 56) return false;
        }

        return true;
    }

    // needs is in check method, will check if the side that didn't move had check
    private static boolean checkCheck(int[] pieces) {
        int check = Check.isInCheck(pieces);

        if (check == 3) return false;

        boolean whiteTurn = switch (pieces[0]) {
            case 1,2 -> true;
            default -> false;
        };

        if (check == 2 && !whiteTurn) return false;
        if (check == 1 && whiteTurn) return false;

        return true;
    }
}
