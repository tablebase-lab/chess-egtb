package dev.michalrelich.tablebase.frontend;

import java.util.Comparator;

public class Piece implements Comparable<Piece> {

    private final PieceType type;
    private final PieceColor color;

    public Piece(PieceType type, PieceColor color) {
        this.type = type;
        this.color = color;
    }

    @Override
    public int compareTo(Piece o) {
        int result = Comparator.<PieceColor> naturalOrder().compare(this.getColor(), o.getColor());
        if (result != 0) return result;

        return Comparator.<PieceType> naturalOrder().compare(this.getType(), o.getType());
    }

    public enum PieceType implements Comparable<PieceType> { // CODE RELIANT ON ORDINAL! (in GaussFunction and for sorting)
        KING,
        QUEEN,
        ROOK,
        BISHOP,
        KNIGHT,
        PAWN

    }

    public enum PieceColor {
        WHITE,
        BLACK
    }

    @Override
    public String toString() {
        char name = this.type.name().charAt(0);
        boolean isWhite = color == PieceColor.WHITE;

        if (this.type == PieceType.KNIGHT) return isWhite ? "N" : "n";
        return isWhite ? name + "" : (char) (name + ('a' - 'A')) + "";
    }

    public PieceType getType() {
        return type;
    }

    public PieceColor getColor() {
        return color;
    }
}
