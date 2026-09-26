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

    public static long pawnMove(long gauss, int pawnPos, int movePos, int promotionPieceInt) {
        int[] pieces = GaussHelper.getPiecesArr(gauss);
        int length = Constants.BOARD_LENGTH;
        boolean whiteTurn = pieces[Constants.TURN_INDEX] == 1;


        boolean firstRowMove = movePos / length == 0;
        boolean lastRowMove = movePos / length == 8;

        if ((whiteTurn && firstRowMove) || (!whiteTurn && lastRowMove)) {
            return -1;
        } else if ((!whiteTurn && firstRowMove) || (whiteTurn && lastRowMove)) {
            // promotion
        }

        if (Math.abs(pawnPos - movePos) == length) { // vertical move by one
            Move.changeValues(pieces, 500 + pawnPos, movePos, Constants.ENP_DEFAULT); // side effect, therefore dont have to assign
            return GaussHelper.longFromArr(pieces);
        }

        if (Math.abs(pawnPos - movePos) == length * 2) { // vertical move by two
            boolean validInBetween = Move.validVerticalMove(pieces, length, pawnPos, movePos);
            if (!validInBetween) return -1;

            int[] moved = Move.changeValues(pieces, 500 + pawnPos, movePos, Constants.ENP_DEFAULT);
            if (HasEnPassant.forMovedPawnByTwoSquares(moved, movePos)) {
                moved[Constants.EN_PASSANT_INDEX] = movePos % length;
            }

            return GaussHelper.longFromArr(moved);
        }

        if (pieces[Constants.EN_PASSANT_INDEX] != Constants.ENP_DEFAULT) {
            long result = enPassantProbe(pieces, pawnPos, movePos);
            if (result != -1) return result;

        }
        // result is a diagonal capture


        return -1;
    }

    public static long enPassantProbe(int[] pieces, int pawnPos, int movePos) {
        int length = Constants.BOARD_LENGTH;
        boolean whiteTurn = pieces[Constants.TURN_INDEX] == 1;

        int enRow = whiteTurn ? 4 : 3;
        int enCol = pieces[Constants.EN_PASSANT_INDEX];
        boolean correctRow = pawnPos / length == enRow;
        boolean correctCol = Math.abs(enCol - pawnPos % length) == 1;

        int movePosRow = whiteTurn ? enRow + 1 : enRow - 1;
        int movePosCol = pieces[Constants.EN_PASSANT_INDEX];
        boolean correctMoveRow = movePos / length == movePosRow;
        boolean correctMoveCol = movePos / length == movePosCol;

        if (correctRow && correctCol && correctMoveRow && correctMoveCol) {
            return enPassant(pieces, pawnPos, movePos);
        }

        return -1;
    }

    public static long enPassant(int[] pieces, int pawnPos, int movePos) {
        for (int i = Constants.DELIMITER_INDEX + 1; i < pieces.length; i++) {
            boolean whiteTurn = pieces[Constants.TURN_INDEX] == 1;

            int capturedPos = whiteTurn ? movePos - Constants.BOARD_LENGTH : movePos + Constants.BOARD_LENGTH;
            if (pieces[i] % 100 == capturedPos) {
                pieces[i] = 0;
                boolean isCapturedWhite = !whiteTurn;
                if (isCapturedWhite) pieces[Constants.DELIMITER_INDEX]--;
            }

            if (pieces[i] == pawnPos) pieces[i] = 500 + movePos;
        }

        return GaussHelper.longFromArr(pieces);
    }

}
