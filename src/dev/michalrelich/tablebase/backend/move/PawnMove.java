package dev.michalrelich.tablebase.backend.move;

import dev.michalrelich.tablebase.backend.Constants;
import dev.michalrelich.tablebase.backend.helper.GaussHelper;
import dev.michalrelich.tablebase.backend.positioncheck.HasEnPassant;

public class PawnMove {

    /* what's already done in move:
    --pawnPos != movePos
    --there isn't a piece of same color / king on movePos
    --the move is either 1 or 2 squares up or 1 square vertically (and the 2 squares is checked for the right row)
    */

    public static long pawnMove(long gauss, int pawnPos, int movePos, int promotionPiece) {
        int[] pieces = GaussHelper.getPiecesArr(gauss);

        if (Math.abs(pawnPos - movePos) == Constants.BOARD_LENGTH) { // vertical move by one
            pieces = Move.changeValues(pieces, 500 + pawnPos, movePos, Constants.ENP_DEFAULT);
            return GaussHelper.longFromArr(pieces);
        }

        if (Math.abs(pawnPos - movePos) == Constants.BOARD_LENGTH * 2) { // vertical move by two
            boolean validInBetween = Move.validVerticalMove(pieces, Constants.BOARD_LENGTH, pawnPos, movePos);
            if (!validInBetween) return -1;

            int[] moved = Move.changeValues(pieces, 500 + pawnPos, movePos, Constants.ENP_DEFAULT);
            if (HasEnPassant.forMovedPawnByTwoSquares(moved, movePos)) {
                moved[Constants.EN_PASSANT_INDEX] = movePos % Constants.BOARD_LENGTH;
            }

            return GaussHelper.longFromArr(moved);
        }

        return -1;
    }



}
