package dev.michalrelich.tablebase.backend.move;

import dev.michalrelich.tablebase.backend.helper.DirectionCheck;
import dev.michalrelich.tablebase.backend.helper.GaussHelper;

public class Move {

    // handles captures as well. enpassant logic is NOT here but in the move generator (if i pass enpassant movepos and it's in correct position the method will move it)
    // counts on the fullPieceInt being valid and in gauss
    public static long move(int gauss, int fullPieceInt, int movePos) {
        int[] pieces = GaussHelper.getPiecesArr(gauss);
        if (!canMove(pieces, fullPieceInt, movePos)) return -1;


        // there can't be white pieces on even the PATH of the move gotta code that in + the capture thing gotta code that too
        return -1;
    }

    // focuses purely on if it's possible on an empty board and NOT on if there are any pieces in the way
    public static boolean canMove(int[] pieces, int fullPieceInt, int movePos) {

        int piecePos = fullPieceInt % 100;
        boolean canMove = switch (fullPieceInt / 100) {
            case 1 -> DirectionCheck.queen(piecePos, movePos);
            case 2 -> DirectionCheck.rook(piecePos, movePos);
            case 3 -> DirectionCheck.bishop(piecePos, movePos);
            case 4 -> DirectionCheck.knight(piecePos, movePos);
            case 5 -> DirectionCheck.pawn(piecePos, movePos);
            default -> false;
        };

        if (!canMove) return false;

        boolean isPieceWhite = true;
        boolean isFoundWhite = true;
        boolean foundDelimiter = false;
        for (int i : pieces) {
            if (i == 9) foundDelimiter = true;

            if (i == piecePos) isPieceWhite = foundDelimiter;
            if (i == movePos) isFoundWhite = foundDelimiter;
        }

        return isPieceWhite != isFoundWhite;
    }

}
