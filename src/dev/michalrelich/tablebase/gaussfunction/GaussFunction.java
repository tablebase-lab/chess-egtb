package dev.michalrelich.tablebase.gaussfunction;

// preliminary Gauss function, intended for 3 piece endgames (2 Kings + 1 Piece)
// transforms a Board layout into a single integer
// the integer has 3-6 digits, each piece is translated into 1 or 2 digits
// the piece is assigned digits at the right
// the position of each piece is 0 - 63, therefore the maximum integer would be 646464, an illegal position, the
// largest legal integer would be 636162
// 0 corresponds to a1, 63 to h8

import dev.michalrelich.tablebase.exceptions.InvalidBoardException;
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

        String s = gauss + "";
        if (((s.length() + 1) % 3 != 0) || s.length() < 5 || s.length() > 14)
            throw new InvalidBoardException("Gauss number has an incorrect number of digits");

        Board b = new Board();

        for (int i = 0; i <= 4; i += 2) {
            int kingPos = Integer.parseInt(s.substring(i, i + 2));
            kingPos = kingPos > 64 ? kingPos / 10 : kingPos;

            b.addToBoard(new Piece(Piece.PieceType.KING,
                    i == 0 ? Piece.PieceColor.WHITE : Piece.PieceColor.BLACK), kingPos);
        }

        String pieces = s.substring(4); // need to figure out where delimiter is
        for (int i = 0; i <= pieces.length(); i += 3) {

        }

        return null;
    }

}
