package dev.michalrelich.tablebase.backend.helper;

public class LongHelper {

    private static final long[] POW10 = {
            1L, 10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L,
            100000000L, 1000000000L, 10000000000L, 100000000000L, 1000000000000L,
            10000000000000L, 100000000000000L, 1000000000000000L, 10000000000000000L,
            100000000000000000L, 1000000000000000000L
    };
    // helpers designed for the chess tablebase. used for positive longs only.

    public static long getLongByIndex(long number, int beginIndex, int endIndex) { // end is non-inclusive
        int length = getLongLength(number);

        if (beginIndex < 0 || beginIndex >= endIndex || endIndex > length + 1) throw new IllegalArgumentException();

        number = number % (POW10[length - beginIndex]);
        number = number / (POW10[length - endIndex]);

        return number;
    }

    public static int getLongLength(long number) {
        if (number <= 0) throw new IllegalArgumentException("Non-positive argument.");

        int digitCount = 1;

        while (true) {
            number /= 10;
            if (number == 0) break;

            digitCount++;
        }

        return digitCount;
    }
}
