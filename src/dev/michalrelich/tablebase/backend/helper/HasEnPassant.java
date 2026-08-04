package dev.michalrelich.tablebase.backend.helper;

import dev.michalrelich.tablebase.frontend.Piece;

public class HasEnPassant {

    // needs position check

    public static Piece.PieceColor forLong(long gauss) { // returns the color for which en passant works, or null

        int[] pieces = GaussHelper.getPiecesArr(gauss);

        return Piece.PieceColor.WHITE;
    }
}
