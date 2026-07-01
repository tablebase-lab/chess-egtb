package dev.michalrelich.tablebase.gaussfunction;

// preliminary Gauss function, intended for 3 piece endgames (2 Kings + 1 Piece)
// transforms a Board layout into a single integer
// the integer has 3-6 digits, each piece is translated into 1 or 2 digits
// the piece is assigned digits at the right
// the position of each piece is 1 - 64, therefore the maximum integer would be 646464, an illegal position, the
// largest legal integer would be 646263
// 1 corresponds to a1, 64 to h8

import dev.michalrelich.tablebase.ui.Board;

public class GaussFunction {

    public static void toInt(Board board) {

    }

    public static Board toBoard(int gaussNumber) {
        return null;
    }
}
