package dev.michalrelich.tablebase.backend.positioncheck;

public class HasEnPassant {

    // returns whether the side on turn can do en passant, counts that the movedPawn moved by 2 squares
    public static boolean forMovedPawn(long gauss, int movedPawn) {
        return false;
    }

    // returns all pawns that can possibly do en passant, since we don't know
    public static int[] forPosition() {
        return new int[0];
    }
}
