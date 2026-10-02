package dev.michalrelich.tablebase.code;

import dev.michalrelich.engine.backend.Constants;

public class Helpers {

    // intended for a loop from 0 to 27
    public static boolean isEighthKingPosition(int pos) {
        return pos <= 3 || (pos >= 9 && pos <= 11) || pos == 18 || pos == 19 || pos == 27;
    }

    public static boolean isLeftHalfKingPosition(int pos) {
        int length = Constants.BOARD_LENGTH;

        if (length % 2 != 0) return false;

        return pos % length < length / 2 + 1;
    }
}
