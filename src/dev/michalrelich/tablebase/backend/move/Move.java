package dev.michalrelich.tablebase.backend.move;

import dev.michalrelich.tablebase.backend.Constants;
import dev.michalrelich.tablebase.backend.helper.Check;
import dev.michalrelich.tablebase.backend.helper.GaussHelper;
import dev.michalrelich.tablebase.backend.positioncheck.DirectionCheck;

public class Move {

    // counts on gauss being valid, fullPieceInt being valid, and movePos being 0-63
    // returns -1 if move can't be performed, else returns modified gauss number
    // todo: moveWhileInCheck

    // this method counts that the king isn't in check
    public static long move(long gauss, int fullPieceInt, int movePos) {
        int piecePos = fullPieceInt % 100;
        int length = Constants.BOARD_LENGTH;
        int[] pieces = GaussHelper.getPiecesArr(gauss);

        if (piecePos == movePos) return -1;
        if (!canMove(pieces, fullPieceInt, movePos) || movePosCheck(pieces, fullPieceInt, movePos)) return -1;

        // by here all that's left is to check between pieces
        // for the King and Pawn helpers it just returns the long itself i guess
        // for the final position i gotta check if the king is in check
        // maybe i refactor the methods by getting pieceIndex from one loop so i dont have to run it every time

        long finalLong;

        switch (fullPieceInt / 100) {
            // not done yet
            case 5 -> finalLong = GaussHelper.getLongByIndex(gauss, 1) % 2 == 0 ?
                        PawnMove.enPassantMove(gauss, fullPieceInt, movePos) : PawnMove.pawnMove(gauss, fullPieceInt, movePos);
            // not done yet
            case 0 -> finalLong = validKingMove(pieces, fullPieceInt, movePos);

            // done
            default -> {
                if (!checkInBetweenPieces(pieces, length, movePos, movePos)) return -1; // horse is handled within the method
                for (int i = 0; i < pieces.length; i++) {
                    if (pieces[i] == fullPieceInt) pieces[i] = fullPieceInt / 100 + movePos;
                    if (pieces[i] == movePos) pieces[i] = 0;
                }

                finalLong = GaussHelper.longFromArr(pieces);
            }
        }

        // not done yet
        return Check.isInCheck(finalLong) != -1 ? finalLong : -1;
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

    // DONE
    // checks the movePos for a piece of same color / king
    public static boolean movePosCheck(int[] pieces, int fullPieceInt, int movePos) {
        int piecePos = fullPieceInt % 100;

        boolean isPieceWhite = true;
        boolean isFoundWhite = true;
        boolean delimiter = false;
        boolean found = false;
        for (int i : pieces) {
            if (i < 10) {
                if (i == 9) delimiter = true;
                continue;
            }

            int iPos = i >= 100 ? i % 100 : i;

            if (iPos == piecePos) isPieceWhite = !delimiter;

            if (iPos == movePos) {
                if (i / 100 == 0) {
                    System.out.println("Attempted to capture a king at " + iPos);
                    return false; // a king is at the desired position
                }
                isFoundWhite = !delimiter;
                found = true;
            }
        }

        if (found) {
            return isPieceWhite != isFoundWhite;
        }

        return true;
    }


    public static boolean checkInBetweenPieces(int[] pieces, int length, int fullPieceInt, int movePos) {
        if (fullPieceInt / 100 == 4) return true; // horse

        int piecePos = fullPieceInt % 100;

        if (piecePos / length == movePos / length) { // the move is horizontal
            return validHorizontalMove(pieces, piecePos, movePos);
        } else if (piecePos % length == movePos % length) { // the move is vertical
            return validVerticalMove(pieces, length, piecePos, movePos);
        } else if (fullPieceInt / 100 != 4) { // the move is diagonal
            return validDiagonalMove(pieces, length, piecePos, movePos);
        }

        System.err.println("Unexpected result in checkInBetweenPieces");
        return false;
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

            if (randomPiece % length != piecePos % length) continue;

            boolean isBetweenOne = randomPiece / length > piecePos / length && randomPiece / length < movePos / length;
            boolean isBetweenTwo = randomPiece / length > movePos / length && randomPiece / length < piecePos / length;

            if (isBetweenOne || isBetweenTwo) return false;
        }

        return true;
    }

    public static boolean validDiagonalMove(int[] pieces, int length, int piecePos, int movePos) {
        for (int piece : pieces) {
            if (piece / 10 == 0) continue; // the delimiter and the turn info

            boolean isBetween = (piece > piecePos && piece < movePos) || (piece > movePos && piece < piecePos);
            int randomPiece = piece % 100;

            if (piecePos % (length + 1) == movePos % (length + 1)) { // diagonal from left to right
                if (randomPiece % (length + 1) == piecePos % (length + 1) && isBetween) return false;
            } else { // diagonal from right to left
                if (randomPiece % (length - 1) == piecePos % (length - 1) && isBetween) return false;
            }
        }

        return true;
    }

    public static long validKingMove(int[] pieces, int kingPos, int movePos) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] / 10 == 0) continue; // the delimiter and the turn info

            int randomPiece = pieces[i] % 100;

            // for kings
            if ( (pieces[i] < 100 && pieces[i] >= 0) &&
                    DirectionCheck.king(movePos, randomPiece)) return -1; // king moves near the other king

            if (randomPiece % 100 == movePos) { // we already know it's not a king from canMove
                pieces[i] = 0;
            }

            if (randomPiece == kingPos) {
                pieces[i] = kingPos + movePos; // ???
            }
        }

        return GaussHelper.longFromArr(pieces);
    }
}
