package dev.michalrelich.tablebase.code.three_piece;

import dev.michalrelich.engine.backend.positioncheck.PositionCheck;
import dev.michalrelich.tablebase.code.Helpers;
import dev.michalrelich.tablebase.code.MoveGenerator;

public class ThreePieceAlgorithm {

    public static void algorithm(int pieceInt) {



    }

    public static void KXKLoop(int pieceInt) {
        int count = 0;
        int allPositions = ThreePieceConstants.KXKPositions(5);

        boolean firstIteration = true;

        while (count < allPositions) {
            for (int turn = 1; turn <= 2; turn++) {
                for (int kingOne = 0; kingOne <= 63; kingOne++) {

                    boolean proceed = pieceInt == 5 ? Helpers.isLeftHalfKingPosition(kingOne) :
                            Helpers.isEighthKingPosition(kingOne);

                    if (!proceed) continue;
                    for (int kingTwo = 0; kingTwo <= 63; kingTwo++) {
                        for (int piece = 0; piece <= 63; piece++) {
                            int[] arr = {turn, 8, kingOne, kingTwo, 1, 5 * 100 + piece};
                            if (PositionCheck.checkPosition(arr)) {
                                int[][] moves = MoveGenerator.generateMoves(arr);

                                if (firstIteration) {

                                }
                                count++;
                            }
                        }
                    }
                }
            }

            firstIteration = false;
        }
    }
}
