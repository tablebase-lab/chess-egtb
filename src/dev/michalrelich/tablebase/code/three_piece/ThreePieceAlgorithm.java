package dev.michalrelich.tablebase.code.three_piece;

import dev.michalrelich.engine.backend.positioncheck.PositionCheck;
import dev.michalrelich.tablebase.code.Helpers;

public class ThreePieceAlgorithm {

    public static void algorithm(int pieceInt) {
        int count = 0;
        for (int turn = 1; turn <= 2; turn++) {
            for (int kingOne = 0; kingOne <= 27; kingOne++) {
                kingOne = Helpers.returnEightKingPosition(kingOne);

                for (int kingTwo = 0; kingTwo <= 63; kingTwo++) {
                    for (int piece = 0; piece <= 63; piece++) {
                        int[] arr = {turn, 8, kingOne, kingTwo, 1, pieceInt * 100 + piece};
                        if (PositionCheck.checkPosition(arr)) count++;
                    }
                }
            }
        }
    }
}
