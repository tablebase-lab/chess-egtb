package dev.michalrelich.tablebase.code;

public class Helpers {

    // intended for a loop from 0 to 27
    public static int returnEightKingPosition(int pos) {
        if (pos == 4) pos = 9;
        if (pos == 12) pos = 18;
        if (pos == 20) pos = 27;

        return pos;
    }
}
