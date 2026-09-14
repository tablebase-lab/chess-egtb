package dev.michalrelich.tablebase.backend.move;

import dev.michalrelich.tablebase.backend.helper.DirectionCheck;
import dev.michalrelich.tablebase.backend.helper.GaussHelper;
import dev.michalrelich.tablebase.frontend.Board;

public class Move {

    // handles captures as well. enpassant logic is NOT here but in the move generator (if i pass enpassant movepos and it's in correct position the method will move it)
    // counts on the fullPieceInt being valid and in gauss
    // the method itself mostly checks if there aren't any pieces in the way of the two positions

    // todo: check PawnMove and enPassantMove, make sure if you are in Check you have to block it or go away! Include checkMove??
    public static long move(long gauss, int fullPieceInt, int movePos, boolean invalidCheck) {
        int piecePos = fullPieceInt % 100;
        int length = Board.BOARD_LENGTH;
        int[] pieces = GaussHelper.getPiecesArr(gauss);

        if (piecePos == movePos) return -1;
        if (!canMove(pieces, fullPieceInt, movePos)) return -1;

        if (invalidCheck) return invalidCheck(); // for calls from PositionGenerator to PositionCheck to Check to here

        if (!canMoveToPos(pieces, fullPieceInt, movePos)) return -1;

        if (fullPieceInt / 100 == 5) {
            return GaussHelper.getLongByIndex(0, 1) % 2 == 0 ?
                    PawnMove.enPassantMove(gauss, fullPieceInt, movePos) : PawnMove.pawnMove(gauss, fullPieceInt, movePos);
        }


        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] / 10 == 0) continue; // the delimiter and the turn info

            int randomPiece = pieces[i] % 100;

            // for kings
            if ((fullPieceInt < 100 && fullPieceInt >= 10) && (pieces[i] < 100 && pieces[i] >= 10) &&
                    DirectionCheck.king(movePos, randomPiece)) return -1; // king moves near the other king

            if (randomPiece % 100 == movePos) { // we already know it's not a king from canMove
                pieces[i] = 0;
            }

            if (randomPiece == piecePos) {
                pieces[i] = (fullPieceInt / 100 * 100) + movePos;
            }
        }

        return GaussHelper.longFromArr(pieces);
    }

    // DONE
    // focuses purely on if it's possible
    public static boolean canMove(int[] pieces, int fullPieceInt, int movePos) {

        int piecePos = fullPieceInt % 100;

        return switch (fullPieceInt / 100) {
            case 0 -> DirectionCheck.king(piecePos, movePos); // since king has two digits it will be 0
            case 1 -> DirectionCheck.queen(piecePos, movePos);
            case 2 -> DirectionCheck.rook(piecePos, movePos);
            case 3 -> DirectionCheck.bishop(piecePos, movePos);
            case 4 -> DirectionCheck.knight(piecePos, movePos);
            case 5 -> DirectionCheck.pawn(piecePos, movePos, pieces[0] <= 2);
            default -> false;
        };
    }

    // focuses on if the movePos has a piece of a different color AND the color of our piece is the same as the turn
    public static boolean canMoveToPos(int[] pieces, int fullPieceInt, int movePos) {
        int piecePos = fullPieceInt % 100;

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

            if (iPos == piecePos) isPieceWhite = !foundDelimiter;

            // THIS BREAKS THE CODE FOR INVALID CHECK POSITIONS
            if (iPos == movePos) {
                if (i / 100 == 0) {
                    System.out.println("Attempted to capture a king at " + iPos);
                    return false; // a king is at the desired position
                }
                isFoundWhite = !foundDelimiter;
                found = true;
            }
        }

        boolean b = isPiecesMove(pieces[0], isPieceWhite); // so we don't move with non-turn color
        if (found) {
            return b && isPieceWhite != isFoundWhite;
        }

        return b;
    }

    public static long move(long gauss, int fullPieceInt, int movePos) {
        return move(gauss, fullPieceInt, movePos, false);
    }

    // helper for canMoveToPos
    public static boolean isPiecesMove(int firstDigit, boolean isPieceWhite) {
        return !isPieceWhite && firstDigit > 2 || isPieceWhite && firstDigit <= 2;
    }

    public static boolean checkInBetweenPieces(int[] pieces, int length, int fullPieceInt, int movePos) {
        int piecePos = fullPieceInt % 100;

        if (piecePos / length == movePos / length) { // the move is horizontal
            return validHorizontalMove(pieces, piecePos, movePos);
        } else if (piecePos % length == movePos % length) { // the move is vertical
            return validVerticalMove(pieces, length, piecePos, movePos);
        } else if (fullPieceInt / 100 != 4) { // the move is diagonal
            return validDiagonalMove(pieces, length, piecePos, movePos);
        }

        return true; //?
    }


    public static boolean validHorizontalMove(int[] pieces, int piecePos, int movePos) {
        for (int piece : pieces) {
            if (piece / 10 == 0) continue; // the delimiter and the turn info
            int randomPiece = piece % 100;

            boolean isBetween = (randomPiece > piecePos && randomPiece < movePos) || (randomPiece > movePos && randomPiece < piecePos);
            if (isBetween) return false;
        }

        return true;
    }

    public static boolean validVerticalMove(int[] pieces, int length, int piecePos, int movePos) {
        for (int piece : pieces) {
            if (piece / 10 == 0) continue; // the delimiter and the turn info
            int randomPiece = piece % 100;

            boolean isBetweenOne = randomPiece / length > piecePos / length && randomPiece / length < movePos / length;
            boolean isBetweenTwo = randomPiece / length > movePos / length && randomPiece / length < piecePos / length;

            if (isBetweenOne || isBetweenTwo) return false;
        }

        return true;
    }

    public static boolean validDiagonalMove(int[] pieces, int length, int piecePos, int movePos) {
        for (int piece : pieces) {
            if (piece / 10 == 0) continue; // the delimiter and the turn info

            boolean b = (piece > piecePos && piece < movePos) || (piece > movePos && piece < piecePos);
            int randomPiece = piece % 100;

            if (piecePos % (length + 1) == movePos % (length + 1)) { // diagonal from left to right
                if (randomPiece % (length + 1) == piecePos && b) return false;
            } else { // diagonal from right to left
                if (randomPiece % (length - 1) == piecePos && b) return false;
            }
        }

        return true;
    }

    public static long invalidCheck() {
        return -1;
    }

}
