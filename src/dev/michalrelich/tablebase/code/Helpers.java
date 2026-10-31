package dev.michalrelich.tablebase.code;

import dev.michalrelich.engine.backend.Constants;

import java.util.Arrays;

public class Helpers {

    // intended for a loop from 0 to 27
    public static boolean isEighthKingPosition(int pos) {
        return pos <= 3 || (pos >= 9 && pos <= 11) || pos == 18 || pos == 19 || pos == 27;
    }

    public static boolean isLeftHalfKingPosition(int pos) {
        int length = Constants.BOARD_LENGTH;
        return pos % length < length / 2 + 1;
    }

    public static int[] flipPos(int[] pos, boolean isEndgameWithPawn) {
        int[] posNew = Arrays.copyOf(pos, pos.length);

        if (isEndgameWithPawn) {
            if (!isLeftHalfKingPosition(posNew[Constants.WHITE_KING_INDEX])) {
                return flipHorizontal(posNew);
            }
        }

        return posNew;
    }

    public static int[] flipHorizontal(int[] fullPos) {
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

        return fullPos;
    }

    public static int[] flipVertical() {
        return null;
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
