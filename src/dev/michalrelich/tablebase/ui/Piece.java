package dev.michalrelich.tablebase.ui;

public enum Piece {

    KING,
    QUEEN,
    ROOK,
    BISHOP,
    KNIGHT,
    PAWN;

    private char asChar() {
        return this.name().charAt(0);
    }


}
