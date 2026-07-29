package dev.michalrelich.tablebase.frontend;

import java.util.Comparator;

public class Piece implements Comparable<Piece> {

    private PieceType type;
    private PieceColor color;

    public Piece(PieceType type, PieceColor color) {
        this.type = type;
        this.color = color;
    }

    @Override
    public int compareTo(Piece o) {
        int result = Comparator.<PieceType> naturalOrder().compare(this.getType(), o.getType());
        if (result != 0) return result;

        return Comparator.<PieceColor> naturalOrder().compare(this.getColor(), o.getColor());
    }

    public enum PieceType implements Comparable<PieceType> {
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

        return (color == PieceColor.WHITE ? name : (char) (name + ('a' - 'A'))) + "";
    }

    public PieceType getType() {
        return type;
    }

    public PieceColor getColor() {
        return color;
    }
}
