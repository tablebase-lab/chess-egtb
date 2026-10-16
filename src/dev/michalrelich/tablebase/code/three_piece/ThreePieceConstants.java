package dev.michalrelich.tablebase.code.three_piece;

import dev.michalrelich.engine.backend.positioncheck.PositionCheck;
import dev.michalrelich.tablebase.code.Helpers;

public class ThreePieceConstants {

    public static final int KQK_POSITIONS = KXKPositions(1);
    public static final int KRK_POSITIONS = KXKPositions(2);
    public static final int KPK_POSITIONS = KXKPositions(5);
    public static final String THREE_PIECE_PATH = "data/three_piece";

    public static int KXKPositions(int pieceInt) {
        int count = 0;
        for (int turn = 1; turn <= 2; turn++) {
            for (int kingOne = 0; kingOne <= 63; kingOne++) {
                boolean proceed = pieceInt == 5 ? Helpers.isLeftHalfKingPosition(kingOne) :
                        Helpers.isEighthKingPosition(kingOne);

                if (!proceed) continue;

                for (int kingTwo = 0; kingTwo <= 63; kingTwo++) {
                    for (int piece = 0; piece <= 63; piece++) {
                        int[] arr = {turn, 8, kingOne, kingTwo, 1, pieceInt * 100 + piece};
                        if (PositionCheck.checkPosition(arr)) count++;
                    }
                }
            }
        }

        return count;
    }

    public static final long[][][] indexes = initiateIndex();

    public static long returnIndexFromArray(int pieceInt, int turn, int kingTwo) {
        int pieceIndex = pieceInt == 5 ? 2 : pieceInt - 1;
        return indexes[pieceIndex][turn - 1][kingTwo];
    }

    public static long[][][] initiateIndex() {
        long[][][] arr = new long[3][2][64];

        for (int pieceIndex = 0; pieceIndex <= 2; pieceIndex++) {
            for (int turnIndex = 0; turnIndex <= 1; turnIndex++) {
                for (int blackKingPos = 0; blackKingPos <= 63; blackKingPos++) {
                    int pieceInt = pieceIndex == 2 ? 5 : pieceIndex + 1;
                    int turn = turnIndex + 1;

                    arr[pieceIndex][turnIndex][blackKingPos] = calculateIndex(pieceInt, turn, blackKingPos);
                }
            }
        }

        return arr;
    }

    public static long calculateIndex(int pieceInt, int desiredTurn, int kingTwoPos) {

        long count = 0;
        for (int turn = 1; turn <= 2; turn++) {
            for (int kingOne = 0; kingOne <= 63; kingOne++) {
                boolean proceed = pieceInt == 5 ? Helpers.isLeftHalfKingPosition(kingOne) :
                        Helpers.isEighthKingPosition(kingOne);

                if (!proceed) continue;

                for (int kingTwo = 0; kingTwo <= 63; kingTwo++) {
                    for (int piece = 0; piece <= 63; piece++) {
                        int[] arr = {turn, 8, kingOne, kingTwo, 1, pieceInt * 100 + piece};
                        if (PositionCheck.checkPosition(arr)) {
                            if (turn == desiredTurn && kingTwoPos == kingTwo) return count;
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }
}
