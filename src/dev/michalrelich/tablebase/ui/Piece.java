package dev.michalrelich.tablebase.ui;

public enum Piece {

    KING,
    QUEEN,
    ROOK,
    BISHOP,
    KNIGHT,
    PAWN;

    public char asChar(boolean white) {
        char name = this.name().charAt(0);

        return white ? name : (char) (name + ('a' - 'A'));
    }


}
