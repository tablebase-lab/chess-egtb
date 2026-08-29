package dev.michalrelich.tablebase.backend.helper;

import dev.michalrelich.tablebase.frontend.Board;

public class GaussHelper {

    private static final long[] POW10 = {
            1L, 10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L,
            100000000L, 1000000000L, 10000000000L, 100000000000L, 1000000000000L,
            10000000000000L, 100000000000000L, 1000000000000000L, 10000000000000000L,
            100000000000000000L, 1000000000000000000L
    };
    // helpers designed for the chess tablebase. used for positive longs only.

    // done
    public static long getLongByIndex(long number, int beginIndex, int endIndex) { // end is non-inclusive
        int length = getLongLength(number);

        assert !(beginIndex < 0 || beginIndex >= endIndex || endIndex > length + 1):
                "Invalid index: " + beginIndex + ", " + endIndex;

        number = number % (POW10[length - beginIndex]);
        number = number / (POW10[length - endIndex]);

        return number;
    }

    // done
    public static long getLongByIndex(long number, int beginIndex) {
        return getLongByIndex(number, beginIndex, getLongLength(number));
    }

    // done
    public static int getLongLength(long number) {
        assert number > 0: "Negative number: " + number;

        int digitCount = 1;

        while (true) {
            number /= 10;
            if (number == 0) break;

            digitCount++;
        }

        return digitCount;
    }

    public static int[] getPiecesArr(long number) {
        int length = getLongLength(number);
        assert (length >= 9 && length <= 15) : "Invalid number length: " + length;

        int[] arr = new int[Board.MAX_PIECE_COUNT + 2 + 2]; // + 2 kings + 2 numbers for turn and black/white delimiter

        int indexOne = (int) GaussHelper.getLongByIndex(number, 0, 1);
        arr[0] = indexOne;
        arr[1] = (int) GaussHelper.getLongByIndex(number, 1, 3);
        arr[2] = (int) GaussHelper.getLongByIndex(number, 3, 5);

        boolean addDelimiter = true;
        long pieces = GaussHelper.getLongByIndex(number, 5);
        int piecesLength = GaussHelper.getLongLength(pieces);

        for (int i = 0; i < piecesLength; i += 3) {
            if (GaussHelper.getLongByIndex(pieces, i, i + 1) == 9 && addDelimiter) {
                addDelimiter = false;
                arr[3 + i / 3] = 9;
                i++;
            }

            if (i == piecesLength) break;

            arr[3 + i / 3 + (!addDelimiter ? 1 : 0)] = (int) GaussHelper.getLongByIndex(pieces, i, i + 3);
        }

        return arr;
     }

    // shows an array of all pieces (so 1-3-digit ints)


    public static long longFromArr(int[] pieces) {
        long gauss = 0;
        boolean first = true;

        for (int piece : pieces) {
            if (piece == 0) continue;

            if (first) {
                first = false;
                gauss += piece;
                continue;
            }
            int length = GaussHelper.getLongLength(piece);
            if (length == 0) return -1;

            gauss *= POW10[length];
            gauss += piece;
        }

        return gauss;
    }
}
