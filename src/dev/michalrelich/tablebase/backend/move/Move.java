package dev.michalrelich.tablebase.backend.move;

import dev.michalrelich.tablebase.backend.helper.DirectionCheck;
import dev.michalrelich.tablebase.backend.helper.GaussHelper;
import dev.michalrelich.tablebase.frontend.Board;

public class Move {

    // handles captures as well. enpassant logic is NOT here but in the move generator (if i pass enpassant movepos and it's in correct position the method will move it)
    // counts on the fullPieceInt being valid and in gauss
    public static long move(int gauss, int fullPieceInt, int movePos) {
        int length = Board.BOARD_LENGTH;

        int[] pieces = GaussHelper.getPiecesArr(gauss);
        if (!canMove(pieces, fullPieceInt, movePos)) return -1;

        int piecePos = fullPieceInt % 100;
        if (fullPieceInt / 100 < 4) { // therefore not a pawn or a knight
            for (int piece : pieces) {
                boolean b = (piece > piecePos && piece < movePos) || (piece > movePos && piece < piecePos);
                if (piecePos / length == movePos / length) { // the move is horizontal
                    if (b)
                        return -1;

                } else if (piecePos % length == movePos % length) { // the move is vertical
                    if ((piece / length > piecePos / length && piece / length < movePos / length) ||
                            (piece / length > movePos / length && piece / length < piecePos / length)) return -1;

                } else { // the move is diagonal
                    if (piecePos % (length + 1) == movePos % (length + 1)) { // diagonal from left to right
                        if (piece % (length + 1) == piecePos && b) return -1;
                    } else { // diagonal from right to left
                        if (piece % (length - 1) == piecePos && b) return -1;
                    }
                }
            }
        }

        if (fullPieceInt / 100 == 5) {

        }

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

            if (i == piecePos) isPieceWhite = !foundDelimiter;
            if (i == movePos) isFoundWhite = !foundDelimiter;
        }

        return isPieceWhite != isFoundWhite;
    }

}
