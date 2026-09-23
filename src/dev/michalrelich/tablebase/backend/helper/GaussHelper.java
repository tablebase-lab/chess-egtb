package dev.michalrelich.tablebase.backend.helper;

import static dev.michalrelich.tablebase.backend.Constants.*;

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
        assert number > 0: "Non-positive number: " + number;

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

        int[] arr = new int[LEAST_POSSIBLE_ARRAY_SIZE + (length - LEAST_POSSIBLE_ARRAY_SIZE) / 3];
        // 2 kings + turn + en passant col + delimiter + the optional pieces
        // for fewer pieces is a smaller array because a larger array won't ever occur from the position anyway (can't spawn pieces)

        arr[TURN_INDEX] = (int) GaussHelper.getLongByIndex(number, TURN_BEGIN, TURN_END);
        arr[EN_PASSANT_INDEX] = (int) GaussHelper.getLongByIndex(number, EN_PASSANT_BEGIN, EN_PASSANT_END);
        arr[WHITE_KING_INDEX] = (int) GaussHelper.getLongByIndex(number, WHITE_KING_BEGIN, WHITE_KING_END);
        arr[BLACK_KING_INDEX] = (int) GaussHelper.getLongByIndex(number, BLACK_KING_BEGIN, BLACK_KING_END);
        arr[DELIMITER_INDEX] = (int) GaussHelper.getLongByIndex(number, DELIMITER_BEGIN, DELIMITER_END);

        int j = DELIMITER_INDEX + 1;
        for (int i = DELIMITER_END; i < length; i += 3) {
            arr[j] = (int) GaussHelper.getLongByIndex(number, i, i + 3);
            j++;
        }

        return arr;
    }

    // shows an array of all pieces (so 1-3-digit ints)


    // bug for 0 positions of kings etc.!
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
