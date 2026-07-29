package dev.michalrelich.tablebase.gaussfunction;

// preliminary Gauss function, intended for 3 piece endgames (2 Kings + 1 Piece)
// transforms a Board layout into a single integer
// the integer has 3-6 digits, each piece is translated into 1 or 2 digits
// the piece is assigned digits at the right
// the position of each piece is 0 - 63, therefore the maximum integer would be 646464, an illegal position, the
// largest legal integer would be 636162
// 0 corresponds to a1, 63 to h8

import dev.michalrelich.tablebase.frontend.Board;

public class GaussFunction {

    public static long toLong(Board board) {
//        long number = 0;
//        Piece[][] arr = board.getBoard();
//        int count = 0;
//
//        for (int i = 0; i <= arr.length; i++) {
//            for (int j = 0; j <= arr.length; j++) {
//                if (arr[i][j].getType() == Piece.PieceType.KING) {
//                    count == 0 ? count
//                }
//            }
//        }

        return 0;
    }

    public static Board toBoard(int gaussNumber) {
        return null;
    }
}
