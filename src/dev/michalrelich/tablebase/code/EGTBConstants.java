package dev.michalrelich.tablebase.code;

import dev.michalrelich.engine.backend.helper.GaussHelper;
import dev.michalrelich.engine.backend.positioncheck.PositionCheck;

public class EGTBConstants {

    public static final int KQK_POSITIONS = KXKPositions(1);
    public static final int KRK_POSITIONS = KXKPositions(2);
    public static final int KPK_POSITIONS = KXKPositions(5);

    public static int KXKPositions(int pieceInt) {
        int count = 0;
        for (int turn = 1; turn <= 2; turn++) {
            for (int kingOne = 0; kingOne <= 27; kingOne++) {
                kingOne = Helpers.returnEightKingPosition(kingOne);

                for (int kingTwo = 0; kingTwo <= 63; kingTwo++) {
                    for (int piece = 0; piece <= 63; piece++) {
                        int[] arr = {turn, 8, kingOne, kingTwo, 1, pieceInt * 100 + piece};
                        if (PositionCheck.checkPosition(GaussHelper.longFromArr(arr))) count++;
                    }
                }
            }
        }

        return count;
    }
}
