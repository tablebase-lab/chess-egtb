package dev.michalrelich.tablebase.backend.move;

import dev.michalrelich.tablebase.backend.helper.DirectionCheck;
import dev.michalrelich.tablebase.backend.helper.GaussHelper;
import dev.michalrelich.tablebase.backend.helper.HasEnPassant;
import dev.michalrelich.tablebase.frontend.Board;

public class PawnMove {

    public static long pawnMove(int gauss, int pawnPos, int movePos) {
        int length = Board.BOARD_LENGTH;

        boolean whiteTurn = GaussHelper.getLongByIndex(0, 1) <= 2;
        if (DirectionCheck.pawn(pawnPos, movePos, whiteTurn))
            return -1;

        boolean straightOne = whiteTurn ? pawnPos == movePos - length : pawnPos == movePos + length;
        boolean straightTwo = whiteTurn ? pawnPos == movePos - length * 2 : pawnPos == movePos + length;
        int[] pieces = GaussHelper.getPiecesArr(gauss);


        boolean isWhite = true;
        boolean isPawnWhite = true;
        boolean foundCapture = false;
        for (int i = 0; i < pieces.length; i++) {
            int piece = pieces[i] % 100;
            if (pieces[i] == 9) isWhite = false;
            if (pieces[i] % 100 == pawnPos) {
                isPawnWhite = isWhite;
                pieces[i] = movePos;
            }

            if (straightOne) {
                if (piece == pawnPos + length && whiteTurn) return -1;
                else if (piece == pawnPos - length && !whiteTurn) return -1;
                continue;
            }

            if (straightTwo) {
                if (whiteTurn && (piece == pawnPos + length || piece == pawnPos + length * 2)) return -1;
                else if (!whiteTurn && (piece == pawnPos - length || piece == pawnPos - length * 2)) return -1;
                continue;
            }
            // by here it is just captures
            if ((isWhite != isPawnWhite && isPawnWhite && (piece == pawnPos + length + 1 || piece == pawnPos + length - 1)) ||
                    isWhite != isPawnWhite && !isPawnWhite && (piece == pawnPos - length + 1 || piece == pawnPos - length - 1)) {
                pieces[i] = 0;
                foundCapture = true;
            }
        }

        if (!foundCapture) return -1;
        return GaussHelper.longFromArr(pieces);
    }

    public static long enPassantMove(int gauss, int pawnPos, int movePos) {
        if (DirectionCheck.pawn(pawnPos, movePos, GaussHelper.getLongByIndex(0, 1) <= 2))
            return -1;

        int delete = HasEnPassant.forLong(gauss);
        if (delete == -1) return -1;

        int[] pieces = GaussHelper.getPiecesArr(gauss);

        for (int i = 0; i < pieces.length; i++) {
            int piece = pieces[i] / 100;
            if (piece == delete) pieces[i] = 0;
            if (piece == pawnPos) pieces[i] = (pieces[i] / 100 * 100) + movePos;
        }

        return GaussHelper.longFromArr(pieces);
    }
}
