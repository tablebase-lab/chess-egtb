package dev.michalrelich.tablebase.code;

import dev.michalrelich.engine.backend.Constants;
import dev.michalrelich.engine.backend.move.Move;

public class MoveGenerator {

    public static int[][] generateMoves(int[] pieces) {
        int[][] validMoveResults = new int[EGTBConstants.MAX_MOVES_IN_A_POSITION][pieces.length];
        int lastUnusedIndex = 0;

        boolean isPieceWhite = true;
        boolean isTurnWhite = pieces[Constants.TURN_INDEX] == 1;

        for (int i = 0; i < pieces.length; i++) {
            if (Constants.skipNonPositionIndex(i)) continue;
            if (pieces[Constants.DELIMITER_INDEX] == i - (Constants.DELIMITER_INDEX + 1)) isPieceWhite = false;

            boolean wrongKingTurn = (isTurnWhite && i == Constants.BLACK_KING_INDEX) ||
                    (!isTurnWhite && i == Constants.WHITE_KING_INDEX);
            if (isPieceWhite != isTurnWhite || wrongKingTurn) continue;

            int fullPiece = pieces[i];
            if (fullPiece / 100 != 5) {
                for (int j = 0; j <= 63; j++) {
                    int[] moveResult = Move.move(pieces, fullPiece, j);
                    if (moveResult == null) continue;

                    validMoveResults[lastUnusedIndex] = moveResult;
                    lastUnusedIndex++;
                }
            } else {
                for (int k = 0; k <= 63; k++) {
                    for (int j = 1; j <= 4; j++) {
                        int[] moveResult = Move.move(pieces, fullPiece, k, j);
                        if (moveResult == null) continue;

                        validMoveResults[lastUnusedIndex] = moveResult;
                        lastUnusedIndex++;
                    }
                }
            }
        }

        return validMoveResults;
    }
}
