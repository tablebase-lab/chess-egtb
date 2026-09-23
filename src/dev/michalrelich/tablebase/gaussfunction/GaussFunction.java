package dev.michalrelich.tablebase.gaussfunction;

import dev.michalrelich.tablebase.frontend.Board;
import dev.michalrelich.tablebase.frontend.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.NavigableSet;

public class GaussFunction {

    public static long gaussFunction(Board board, boolean printFormattedResult) {
        var pieceInfo = board.getBoard();
        System.out.println(pieceInfo);

        List<String> list = getStrings(pieceInfo);

        String prefix = board.getTurn() == Piece.PieceColor.WHITE ? "1" : "2";
        String prefix2 = board.getEnPassantCol() == 0 ? "8" : board.getEnPassantCol() - 1 + ""; // enPassantCol inside Board is 1-8, 0 default

        if (printFormattedResult) System.out.printf("%s_%s_%s%n", prefix, prefix2, String.join("_", list));

        String s = prefix + prefix2 + String.join("", list);
        long gauss = Long.parseLong(s);
//        if (!PositionCheck.checkPosition(gauss))
//            throw new InvalidBoardException("Invalid board!");

        return Long.parseLong(s);
    }

    private static List<String> getStrings(NavigableMap<Piece, NavigableSet<Integer>> pieceInfo) {
        List<String> list = new ArrayList<>(pieceInfo.size() + 2);
        boolean addDelimiter = true;
        int delimiterPosition = 0;
        int whiteExtraPieces = 0;

        for (var entry : pieceInfo.entrySet()) {
            Piece.PieceType type = entry.getKey().getType();
            Piece.PieceColor color = entry.getKey().getColor();
            NavigableSet<Integer> locations = entry.getValue();

            if (color == Piece.PieceColor.BLACK && addDelimiter && type != Piece.PieceType.KING) {
                addDelimiter = false;
                delimiterPosition = whiteExtraPieces;
            }

            whiteExtraPieces += locations.size();

            if (type == Piece.PieceType.KING) {
                String s = locations.getFirst() < 10 ? "0" + locations.getFirst() : locations.getFirst() + "";
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

        int delimiterIndex = addDelimiter ? list.size() - 2 : delimiterPosition - 2;
        list.add(2, delimiterIndex + "");

        return list;
    }

    public static Board inverse(long gauss) {

        String s = gauss + "";
        char prefix = s.charAt(0);
        char prefix2 = s.charAt(1);
        s = s.substring(2);

        Board b = prefix == '1' ? new Board(Piece.PieceColor.WHITE) : new Board(Piece.PieceColor.BLACK);
        if (prefix2 != '8') {
            int col = Integer.parseInt(prefix2 + "") + 1;
            b.setEnPassantCol(col);
        }

        for (int i = 0; i <= 2; i += 2) {
            int kingPos = Integer.parseInt(s.substring(i, i + 2));
            kingPos = kingPos > 64 ? kingPos % 10 : kingPos;

            b.addToBoard(new Piece(Piece.PieceType.KING,
                    i == 0 ? Piece.PieceColor.WHITE : Piece.PieceColor.BLACK), kingPos);
        }

        int delimiter = Integer.parseInt(s.charAt(4) + "");
        String pieces = s.substring(5);

        boolean white = true;
        for (int i = 0; i + 3 <= pieces.length(); i += 3) {
            if (i / 3 >= delimiter && white) {
                white = false;
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
