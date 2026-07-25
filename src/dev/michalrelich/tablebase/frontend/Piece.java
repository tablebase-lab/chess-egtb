package dev.michalrelich.tablebase.frontend;

public class Piece {

    private PieceType type;
    private PieceColor color;

    public Piece(PieceType type, PieceColor color) {
        this.type = type;
        this.color = color;
    }

    public enum PieceType {
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

    public char asChar() {
        char name = this.type.name().charAt(0);

        return color == PieceColor.WHITE ? name : (char) (name + ('a' - 'A'));
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
