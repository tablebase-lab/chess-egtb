package dev.michalrelich.tablebase.backend.move;

import dev.michalrelich.tablebase.backend.Constants;
import dev.michalrelich.tablebase.backend.helper.Check;
import dev.michalrelich.tablebase.backend.helper.GaussHelper;
import dev.michalrelich.tablebase.backend.positioncheck.DirectionCheck;

public class Move {

    // counts on gauss being valid, fullPieceInt being valid, and movePos being 0-63
    // returns -1 if move can't be performed, else returns modified gauss number

    // method works for inCheck positions aswell because the checks at the end.
    public static long move(long gauss, int fullPieceInt, int movePos, int promotionPiece) {
        int piecePos = fullPieceInt % 100;
        int length = Constants.BOARD_LENGTH;
        int[] pieces = GaussHelper.getPiecesArr(gauss);

        if (piecePos == movePos) return -1;
        if (!canMove(pieces[Constants.TURN_INDEX] == 1, fullPieceInt, movePos) ||
                !movePosCheck(pieces, movePos)) return -1;

        long finalLong;

        if (fullPieceInt / 100 == 5) {
            finalLong = PawnMove.pawnMove(gauss, piecePos, movePos, promotionPiece);
        } else {
            // code for all other pieces except pawns
            if (!checkInBetweenPieces(pieces, length, fullPieceInt, movePos))
                return -1; // horse and king is handled within the method
            pieces = changeValues(pieces, fullPieceInt, movePos, Constants.ENP_DEFAULT);
            finalLong = GaussHelper.longFromArr(pieces);
        }

        // code for all pieces

        boolean checkCheck = Check.isInCheck(finalLong) != -1;
        boolean kingCheck;
        if (fullPieceInt / 100 != 0) {
            kingCheck = true;
        } else {
            kingCheck = !DirectionCheck.king(pieces[Constants.WHITE_KING_INDEX], pieces[Constants.BLACK_KING_INDEX]);
        }

        if (!checkCheck) System.out.println("Somethings wrong with the checks!");
        if (!kingCheck) System.out.println("Kings are near each other!");

        return checkCheck && kingCheck ? finalLong : -1;
    }

    // returns an array due to en passant (even after the move is made the en passant index might be changed)
    public static int[] changeValues(int[] pieces, int fullPieceInt, int movePos, int enPassantCol) {
        for (int i = 0; i < pieces.length; i++) {
            if (Constants.skipNonPositionIndex(i)) continue;

            if (pieces[i] == fullPieceInt) {
                pieces[i] = fullPieceInt / 100 * 100 + movePos;
                continue;
            }
            if (pieces[i] % 100 == movePos) {
                boolean isWhite = i - (Constants.DELIMITER_INDEX + 1) < pieces[Constants.DELIMITER_INDEX];
                if (isWhite) pieces[Constants.DELIMITER_INDEX]--;
                pieces[i] = 0;
            }
        }

        pieces[Constants.TURN_INDEX] = pieces[Constants.TURN_INDEX] == 1 ? 2 : 1;
        pieces[Constants.EN_PASSANT_INDEX] = enPassantCol; // for any non-pawn move logically there won't be en passant anymore if there was
        // the long is handled wholly in the pawn class

        return pieces;
    }

    // all these DONE

    public static boolean checkInBetweenPieces(int[] pieces, int length, int fullPieceInt, int movePos) {
        if (fullPieceInt / 100 == 4 || fullPieceInt / 100 == 0) return true; // horse or king

        int piecePos = fullPieceInt % 100;

        if (piecePos / length == movePos / length) { // the move is horizontal
            return validHorizontalMove(pieces, piecePos, movePos);
        } else if (piecePos % length == movePos % length) { // the move is vertical
            return validVerticalMove(pieces, length, piecePos, movePos);
        } else { // the move is diagonal
            return validDiagonalMove(pieces, length, piecePos, movePos);
        }


    }

    public static boolean validHorizontalMove(int[] pieces, int piecePos, int movePos) {
        for (int i = 0; i < pieces.length; i++) {
            int piece = pieces[i];
            if (Constants.skipNonPositionIndex(i)) continue;

            int randomPiece = piece % 100;

            boolean isBetween = (randomPiece > piecePos && randomPiece < movePos) || (randomPiece > movePos && randomPiece < piecePos);
            if (isBetween) return false;
        }

        return true;
    }

    public static boolean validVerticalMove(int[] pieces, int length, int piecePos, int movePos) {
        for (int i = 0; i < pieces.length; i++) {
            int piece = pieces[i];

            if (Constants.skipNonPositionIndex(i)) continue;

            int randomPiece = piece % 100;
            if (randomPiece % length != piecePos % length) continue;

            boolean isBetweenOne = randomPiece / length > piecePos / length && randomPiece / length < movePos / length;
            boolean isBetweenTwo = randomPiece / length > movePos / length && randomPiece / length < piecePos / length;

            if (isBetweenOne || isBetweenTwo) return false;
        }

        return true;
    }

    public static boolean validDiagonalMove(int[] pieces, int length, int piecePos, int movePos) {
        for (int i = 0; i < pieces.length; i++) {
            int piece = pieces[i];

            if (Constants.skipNonPositionIndex(i)) continue;

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



    // checks the movePos for a piece of same color / king
    public static boolean movePosCheck(int[] pieces, int movePos) {
        boolean isPieceWhite = pieces[0] == 1;

        for (int i = 0; i < pieces.length; i++) {

            if (Constants.skipNonPositionIndex(i)) continue;

            if (pieces[i] % 100 != movePos || i <= Constants.EN_PASSANT_INDEX || i == Constants.DELIMITER_INDEX) continue;
            // from here there's a piece at the movePos

            if (pieces[i] / 100 == 0) return false; // king

            boolean isIteratedPieceWhite = i - (Constants.DELIMITER_INDEX + 1) < pieces[Constants.DELIMITER_INDEX];
            if (isIteratedPieceWhite == isPieceWhite) return false;
        }

        return true;
    }

    // DONE
    // focuses purely on if it's possible
    public static boolean canMove(boolean whiteTurn, int fullPieceInt, int movePos) {

        int piecePos = fullPieceInt % 100;

        return switch (fullPieceInt / 100) {
            case 0 -> DirectionCheck.king(piecePos, movePos); // since king has two digits it will be 0
            case 1 -> DirectionCheck.queen(piecePos, movePos);
            case 2 -> DirectionCheck.rook(piecePos, movePos);
            case 3 -> DirectionCheck.bishop(piecePos, movePos);
            case 4 -> DirectionCheck.knight(piecePos, movePos);
            case 5 -> DirectionCheck.pawn(piecePos, movePos, whiteTurn);
            default -> false;
        };
    }
}
