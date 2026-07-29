package dev.michalrelich.tablebase.frontend;

import dev.michalrelich.tablebase.exceptions.InvalidBoardException;
import dev.michalrelich.tablebase.frontend.swing.App;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Board {

    private Piece[][] board = new Piece[8][8];

    public boolean addToBoard(Piece piece, int row, int col) {
        if (row > 8 || col > 8 || row < 0 || col < 0) return false;

        board[row - 1][col - 1] = piece;
        return true;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void launchApp() {
        App.loadBoard(this);
        App.launch();
    }

    public Map<Piece, Integer> getPieceInfo() {
        ArrayList<Piece> pieces = new ArrayList<>(3);
        ArrayList<Integer> positions = new ArrayList<>(3);

        int kingCount = 0;
        int pieceCount = 0;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                Piece.PieceType type = board[row][col].getType();
                Piece.PieceColor color = board[row][col].getColor();
                Piece piece = new Piece(type, color);

                if (type == null || color == null) {
                    continue;
                }

                if (type == Piece.PieceType.KING) {
                    switch (color) {
                        case WHITE -> {
                            pieces.addFirst(piece);
                            positions.addFirst(col + board.length * row);
                            kingCount++;
                        }
                        case BLACK -> {
                            pieces.add(1, piece);
                            positions.add(1, col + board.length * row);
                            kingCount++;
                        }
                    }
                    continue;
                }

                if (color == Piece.PieceColor.WHITE) {
                    pieces.add(2, piece);
                    positions.add(2, col + board.length * row);
                    pieceCount++;
                } else {
                    pieces.addLast(piece);
                    positions.addLast(col + board.length * row);
                    pieceCount++;
                }
            }
        }

        if (kingCount != 2) {
            throw new InvalidBoardException("e");
        }

        Map<Piece, Integer> map = new TreeMap<>();

        for (int i = 0; i < pieces.size(); i++) {
            map.put(pieces.get(i), positions.get(i));
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (Piece[] pieces : board) {
            for (int j = 0; j < pieces.length; j++) {
                if (j == 0) sb.append("| ");
                if (pieces[j] == null) {
                    sb.append("  | ");
                    continue;
                }
                sb.append(pieces[j]).append(" | ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
