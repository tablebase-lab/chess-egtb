package dev.michalrelich.tablebase.backend.move;

import dev.michalrelich.tablebase.backend.helper.DirectionCheck;
import dev.michalrelich.tablebase.backend.helper.GaussHelper;
import dev.michalrelich.tablebase.frontend.Board;

public class Move {

    // handles captures as well. enpassant logic is NOT here but in the move generator (if i pass enpassant movepos and it's in correct position the method will move it)
    // counts on the fullPieceInt being valid and in gauss
    // the method itself mostly checks if there aren't any pieces in the way of the two positions
    public static long move(long gauss, int fullPieceInt, int movePos, boolean enPassant) {

        if (movePos > 63 || movePos < 0) return -1;

        if (fullPieceInt / 100 == 5) {
            return enPassant ? PawnMove.enPassantMove(gauss, fullPieceInt, movePos) : PawnMove.pawnMove(gauss, fullPieceInt, movePos);
        }

        int length = Board.BOARD_LENGTH;

        int[] pieces = GaussHelper.getPiecesArr(gauss);
        if (!canMove(pieces, fullPieceInt, movePos)) return -1;

        int piecePos = fullPieceInt % 100;
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] / 10 == 0) continue; // the delimiter and the turn info

            int piece = pieces[i] % 100;

            // for knights all of these are ignored except the last if
            boolean b = (piece > piecePos && piece < movePos) || (piece > movePos && piece < piecePos);
            if (piecePos / length == movePos / length) { // the move is horizontal
                if (b)
                    return -1;

            } else if (piecePos % length == movePos % length) { // the move is vertical
                if ((piece / length > piecePos / length && piece / length < movePos / length && piece % length == piecePos % length) ||
                        (piece / length > movePos / length && piece / length < piecePos / length)) return -1;

            } else { // the move is diagonal
                if (piecePos % (length + 1) == movePos % (length + 1)) { // diagonal from left to right
                    if (piece % (length + 1) == piecePos && b) return -1;
                } else { // diagonal from right to left
                    if (piece % (length - 1) == piecePos && b) return -1;
                }
            }
            // for kings
            if ((fullPieceInt < 100 && fullPieceInt >= 10) && (pieces[i] < 100 && pieces[i] >= 10) &&
                    DirectionCheck.king(movePos, piece)) return -1; // king moves near the other king

            if (piece % 100 == movePos) { // we already know it's not a king from canMove
                pieces[i] = 0;
            }

            if (piece == piecePos) {
                pieces[i] = (fullPieceInt / 100 * 100) + movePos;
            }
        }


        return GaussHelper.longFromArr(pieces);
    }

    public static long move(long gauss, int fullPieceInt, int movePos) {
        return move(gauss, fullPieceInt, movePos, false);
    }

    // focuses purely on if it's possible on an empty board and  if the destination doesn't have the same color piece, NOT on if there are any pieces in the way
    public static boolean canMove(int[] pieces, int fullPieceInt, int movePos) {

        int piecePos = fullPieceInt % 100;
        boolean canMove = switch (fullPieceInt / 100) {
            case 0 -> DirectionCheck.king(piecePos, movePos); // since king has two digits it will be 0
            case 1 -> DirectionCheck.queen(piecePos, movePos);
            case 2 -> DirectionCheck.rook(piecePos, movePos);
            case 3 -> DirectionCheck.bishop(piecePos, movePos);
            case 4 -> DirectionCheck.knight(piecePos, movePos);
            case 5 -> DirectionCheck.pawn(piecePos, movePos, pieces[0] <= 2);
            default -> false;
        };

        if (!canMove) return false;

        boolean isPieceWhite = true;
        boolean isFoundWhite = true;
        boolean foundDelimiter = false;
        boolean found = false;
        for (int i : pieces) {
            if (i < 10) {
                if (i != 9) continue;
                foundDelimiter = true;
                continue;
            }

            int iPos = i >= 100 ? i % 100 : i;

            if (iPos >= 64) iPos = iPos % 10; // for single digit king positions like 98
            if (iPos == piecePos) isPieceWhite = !foundDelimiter;
            if (iPos == movePos) {
                if (i / 100 == 0) {
                    System.out.println("Attempted to capture a king at " + iPos);
                    return false; // a king is at the desired position
                }
                isFoundWhite = !foundDelimiter;
                found = true;
            }
        }

        boolean b = !isPieceWhite && pieces[0] > 2 || isPieceWhite && pieces[0] <= 2; // so we don't move with non-turn color
        if (found) {
            return b && isPieceWhite != isFoundWhite;
        }

        return b;
    }

}
