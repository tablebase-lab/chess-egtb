package dev.michalrelich.tablebase.gaussfunction;

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
                list.add(type.ordinal() + (loc < 10 ? "0" : "") + loc);
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

        for (int i = 0; i <= 2; i += 2) {
            int kingPos = Integer.parseInt(s.substring(i, i + 2));
            kingPos = kingPos > 64 ? kingPos % 10 : kingPos;

            b.addToBoard(new Piece(Piece.PieceType.KING,
                    i == 0 ? Piece.PieceColor.WHITE : Piece.PieceColor.BLACK), kingPos);
        }

        String pieces = s.substring(4);

        int delimiter = -1;
        for (int i = 0; i < pieces.length(); i += 3) {
            if (pieces.charAt(i) == '9') {
                delimiter = i;
                break;
            }
        }

        boolean white = true;
        for (int i = 0; i + 3 <= pieces.length(); i += 3) {
            if (i + 1 > delimiter) {
                if (white) {
                    i++;
                    white = false;
                }
            }

            String piece = pieces.substring(i, i + 3);
            Piece.PieceType type = Piece.PieceType.values()[Integer.parseInt(piece.charAt(0) + "")];
            Piece.PieceColor color = white ? Piece.PieceColor.WHITE : Piece.PieceColor.BLACK;
            int position = Integer.parseInt(piece.substring(1, 3));
            b.addToBoard(new Piece(type, color), position);
        }

        return b;
    }

}
