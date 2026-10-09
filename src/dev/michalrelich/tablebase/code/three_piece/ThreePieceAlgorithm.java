package dev.michalrelich.tablebase.code.three_piece;

import dev.michalrelich.engine.backend.helper.Check;
import dev.michalrelich.engine.backend.positioncheck.PositionCheck;
import dev.michalrelich.tablebase.code.Helpers;
import dev.michalrelich.tablebase.code.MoveGenerator;

import java.nio.file.Path;
import java.util.Arrays;

public class ThreePieceAlgorithm {

    public static void algorithm(int pieceInt) {



    }

    public static void KXKLoop(int pieceInt) {
        int index = 0;
        int allPositions = ThreePieceConstants.KXKPositions(5);

        String name = Helpers.getFileNameThreePiece(pieceInt);
        assert name != null;
        Path path = Path.of("data", "three_piece", name, name + ".bin");

        boolean firstIteration = true;

        while (index < allPositions) {
            for (int turn = 1; turn <= 2; turn++) {
                for (int kingOne = 0; kingOne <= 63; kingOne++) {

                    boolean proceed = pieceInt == 5 ? Helpers.isLeftHalfKingPosition(kingOne) :
                            Helpers.isEighthKingPosition(kingOne);

                    if (!proceed) continue;
                    for (int kingTwo = 0; kingTwo <= 63; kingTwo++) {
                        for (int piece = 0; piece <= 63; piece++) {
                            int[] arr = {turn, 8, kingOne, kingTwo, 1, pieceInt * 100 + piece};
                            if (PositionCheck.checkPosition(arr)) {
                                int[][] moves = MoveGenerator.generateMoves(arr);

                                if (firstIteration) {
                                    if (isACheckmateOrStalemate(moves) && Check.isInCheck(arr) != 0) { // runs isInCheck twice incl. in checkPosition
                                        byte value = (byte) (arr[0] == 1 ? 1 : -1);
                                        Helpers.rewriteByteForIntIndex(path, index, value);

                                        System.out.println("Found a checkmate for position: " + Arrays.toString(arr));
                                    }
                                } else {
                                    return;
                                }

                                // at the end
                                index++;
                            }
                        }
                    }
                }
            }

            firstIteration = false;
        }
    }

    public static boolean isACheckmateOrStalemate(int[][] moves) {
        return moves[0][0] == 0;
    }

    public static int[] getPositionByIndex(int index) {
        return null;
    }
}
