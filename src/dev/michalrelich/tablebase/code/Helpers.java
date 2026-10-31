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

        if (length % 2 != 0) return false;

        return pos % length < length / 2 + 1;
    }

    public static int[] flipPos(int[] pos, boolean isEndgameWithPawn) {
        int[] posNew = Arrays.copyOf(pos, pos.length);

        if (isEndgameWithPawn) {
            if (!isLeftHalfKingPosition(pos[Constants.WHITE_KING_INDEX])) {
                return flipHorizontal(pos);
            }
        }

        return null;
    }

    public static int[] flipHorizontal(int[] pos) {
        int length = Constants.BOARD_LENGTH;
        boolean isOnLeftHalf = pos[Constants.WHITE_KING_INDEX] % length <= 3;
        for (int i = 0; i < pos.length; i++) {
            if (isOnLeftHalf) {
                pos[i] = pos[i] + (length / 2 - pos[i] % length - 1) * 2 + 1;
            } else {
                pos[i] = pos[i] - (length / 2 - pos[i] % length) * 2 - 1;
            }
        }

        return pos;
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
