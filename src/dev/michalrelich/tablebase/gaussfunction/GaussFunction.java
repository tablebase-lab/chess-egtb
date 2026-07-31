package dev.michalrelich.tablebase.gaussfunction;

// preliminary Gauss function, intended for 3 piece endgames (2 Kings + 1 Piece)
// transforms a Board layout into a single integer
// the integer has 3-6 digits, each piece is translated into 1 or 2 digits
// the piece is assigned digits at the right
// the position of each piece is 0 - 63, therefore the maximum integer would be 646464, an illegal position, the
// largest legal integer would be 636162
// 0 corresponds to a1, 63 to h8

import dev.michalrelich.tablebase.frontend.Board;
import dev.michalrelich.tablebase.frontend.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;

public class GaussFunction {

    public static long gaussFunction(Board board, boolean printFormattedResult) {
        var pieceInfo = board.getBoard();
        System.out.println(pieceInfo);
        board.checkPieceConditions();

        List<String> list = new ArrayList<>(5);
        boolean addDelimiter = true;
        for (var entry : pieceInfo.entrySet()) {
            Piece.PieceType type = entry.getKey().getType();
            Piece.PieceColor color = entry.getKey().getColor();
            NavigableSet<Integer> locations = entry.getValue();

            if (color == Piece.PieceColor.BLACK && addDelimiter) {
                addDelimiter = false;
                list.add("9");
            }

            if (type == Piece.PieceType.KING) {
                String s = locations.getFirst() < 10 ? "9" + locations.getFirst() : locations.getFirst() + "";
                switch (color) {
                    case WHITE -> list.addFirst(s);
                    case BLACK -> list.add(1, s);
                }
                continue;
            }

            for (var loc : locations) {
                list.add((loc < 10 ? "0" : "") + type.ordinal() + loc);
            }
        }

        String s = String.join("", list);
        if (printFormattedResult) System.out.println(String.join("_", list));

        return Long.parseLong(s);
    }

    public static Board inverse(long gauss) {
        return null;
    }

}
