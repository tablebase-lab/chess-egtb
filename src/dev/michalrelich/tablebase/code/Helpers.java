package dev.michalrelich.tablebase.code;

import dev.michalrelich.engine.backend.Constants;

import java.util.Arrays;

public class Helpers {

    public static final int[] turnIndices = precomputeTurnIndices(); // is correct

    // intended for a loop from 0 to 27
    public static boolean isEighthKingPosition(int pos) {
        return pos <= 3 || (pos >= 9 && pos <= 11) || pos == 18 || pos == 19 || pos == 27;
    }

    public static boolean isLeftHalfKingPosition(int pos) {
        int length = Constants.BOARD_LENGTH;
        return pos % length < length / 2 + 1;
    }

    // only one i didnt check yet
    public static int[] flipPos(int[] pos, boolean isEndgameWithPawn) {
        int whiteKingPos = pos[Constants.WHITE_KING_INDEX];
        if (isEighthKingPosition(whiteKingPos)) return pos;

        int[] posNew = Arrays.copyOf(pos, pos.length);

        if (isEndgameWithPawn) {
            if (!isLeftHalfKingPosition(posNew[Constants.WHITE_KING_INDEX])) {
                flipHorizontal(posNew);
                return posNew;
            }
        }

        int eight = getEighth(whiteKingPos);

        boolean failed = false;
        switch (eight) {
            case 1 -> { return pos; }
            case 4 -> flipVertical(posNew);
            case 5 -> {
                flipVertical(posNew);
                flipHorizontal(posNew);
            }
            case 8 -> flipHorizontal(posNew);
            default -> failed = true;
        }

        if (failed) {
            turnToLeftByOne(posNew);
            switch (eight) { // if 3 we don't do anything more
                case 2 -> flipHorizontal(posNew);
                case 6 -> flipVertical(posNew);
                case 7 -> {
                    flipVertical(posNew);
                    flipHorizontal(posNew);
                }
            }
        }

        return posNew;
    }

    public static void flipHorizontal(int[] fullPos) {
        int length = Constants.BOARD_LENGTH;

        for (int i = 0; i < fullPos.length; i++) {
            if (Constants.skipNonPositionIndex(i)) continue;

            int pos = fullPos[i] % 100;
            boolean isOnLeftHalf = pos % length <= 3;

            if (isOnLeftHalf) {
                fullPos[i] = (fullPos[i] / 100 * 100) + pos + (length / 2 - pos % length - 1) * 2 + 1;
            } else {
                fullPos[i] =  (fullPos[i] / 100 * 100) + pos - (pos % length - length / 2) * 2 - 1;
            }
        }
    }

    public static int[] flipVertical(int[] fullPos) {
        int length = Constants.BOARD_LENGTH;

        for (int i = 0; i < fullPos.length; i++) {
            if (Constants.skipNonPositionIndex(i)) continue;

            int pos = fullPos[i] % 100;
            boolean isOnBottomHalf = pos / length <= 3;

            if (isOnBottomHalf) {
                fullPos[i] = (fullPos[i] / 100 * 100) + pos + length * ((length / 2 - pos / length - 1) * 2 + 1);
            } else {
                fullPos[i] =  (fullPos[i] / 100 * 100) + pos + length * ((length / 2 - pos / length) * 2 - 1);
            }
        }

        return fullPos;
    }

    // CORRECT!
    public static void turnToLeftByOne(int[] pos) {

        for (int i = 0; i < pos.length; i++) {
            if (Constants.skipNonPositionIndex(i)) continue;
            int resultPos = turnIndices[pos[i] % 100];

            pos[i] = pos[i] / 100 * 100 + resultPos;
        }
    }

    public static int[] precomputeTurnIndices() {
        int[] arr = new int[64];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = getFlippedPosByOne(i);
        }

        return arr;
    }

    public static int getFlippedPosByOne(int pos) {
        int[] centers = {3, 24, 32, 59, 60, 39, 31, 4};
        int length = Constants.BOARD_LENGTH;

        int concretePos = pos % 100;
        int eighth = getEighth(concretePos);

        int resultEighth = switch (eighth) {
            case 1, 2 -> eighth + 6;
            case 3, 4, 5, 6, 7, 8 -> eighth - 2;
            default -> -1;
        };

        int distanceFromCoreToCenter = switch (eighth) {
            case 1, 8 -> concretePos / length;
            case 2, 3 -> concretePos % length;
            case 4, 5 -> length - 1 - concretePos / length;
            case 6, 7 -> length - 1 - (concretePos % length);
            default -> -1;
        };

        int distanceFromCoreToSide = switch (eighth) {
            case 1, 4 -> (length / 2 - 1) - concretePos % length;
            case 2, 7 -> (length / 2 - 1) - concretePos / length;
            case 3, 6 -> concretePos / length - length / 2;
            case 5, 8 -> concretePos % length - length / 2;
            default -> -1;
        };

        int eighthCenter = centers[resultEighth - 1];
        int resultPos = switch (resultEighth) {
            case 1 -> eighthCenter + 8 * distanceFromCoreToCenter - distanceFromCoreToSide;
            case 2 -> eighthCenter + distanceFromCoreToCenter - 8 * distanceFromCoreToSide;
            case 3 -> eighthCenter + distanceFromCoreToCenter + 8 * distanceFromCoreToSide;
            case 4 -> eighthCenter - 8 * distanceFromCoreToCenter - distanceFromCoreToSide;
            case 5 -> eighthCenter - 8 * distanceFromCoreToCenter + distanceFromCoreToSide;
            case 6 -> eighthCenter - distanceFromCoreToCenter + 8 * distanceFromCoreToSide;
            case 7 -> eighthCenter - distanceFromCoreToCenter - 8 * distanceFromCoreToSide;
            case 8 -> eighthCenter + 8 * distanceFromCoreToCenter + distanceFromCoreToSide;
            default -> -1;
        };

        return resultPos;
    }

    public static int getEighth(int pos) {
        int length = Constants.BOARD_LENGTH;

        boolean leftHalf = pos % length < length / 2;
        boolean bottomHalf = pos / length < length / 2;

        int quarter = bottomHalf
                ? (leftHalf ? 1 : 4)
                : (leftHalf ? 2 : 3);

        int onDiagonalResult = 1 + (quarter - 1) * 2;

        int addResult = switch (quarter) {
            case 1 -> 1;
            case 2 -> 3;
            case 3 -> 6;
            case 4 -> 8;
            default -> -1;
        };

        int rest = switch (quarter) {
            case 1 -> 2;
            case 2 -> 4;
            case 3 -> 5;
            case 4 -> 7;
            default -> -1;
        };

        if (mainDiagonalCheck(pos, quarter)) return onDiagonalResult;
        else if (mainDiagonalCheck(pos + 8, quarter) || mainDiagonalCheck(pos + 16, quarter)
                || mainDiagonalCheck(pos + 24, quarter)) {
            return addResult;
        }
        else return rest;
    }

    public static boolean mainDiagonalCheck(int pos, int quarter) {
        int length = Constants.BOARD_LENGTH;

        boolean ascendingDiagonal = quarter == 1 || quarter == 3;

        if (ascendingDiagonal) return  pos / length == pos % length;

        return pos / length + pos % length == length - 1;
    }

    public static String getFileNameThreePiece(int pieceInt) {
        return switch (pieceInt) {
            case 1 -> "KQK";
            case 2 -> "KRK";
            case 5 -> "KPK";
            default -> null;
        };
    }
}
