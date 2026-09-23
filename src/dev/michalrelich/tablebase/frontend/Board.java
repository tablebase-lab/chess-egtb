package dev.michalrelich.tablebase.frontend;

import dev.michalrelich.tablebase.frontend.swing.App;

import java.util.*;

import static dev.michalrelich.tablebase.backend.Constants.BOARD_LENGTH;

public class Board {

    private final Map<Piece, NavigableSet<Integer>> board = new TreeMap<>();
    private int enPassantCol = 0;
    private Piece.PieceColor turn;

    public Board(Piece.PieceColor turn) {
        this.turn = turn;
    }

    public boolean addToBoard(Piece piece, int row, int col) {
        if (row > BOARD_LENGTH || col > BOARD_LENGTH || row < 0 || col < 0) return false;
        if (piece == null) return false;

        for (var v : board.values()) {
            v.removeIf(position -> position == col - 1 + (row - 1) * BOARD_LENGTH);
        }

        board.computeIfAbsent(piece, _ -> new TreeSet<>())
                .add(col - 1 + (row - 1) * BOARD_LENGTH);

        return true;
    }

    // switch logic to second method?

    public boolean addToBoard(Piece piece, int position) {

        int row = position / BOARD_LENGTH + 1;
        int col = position % BOARD_LENGTH + 1;
        return addToBoard(piece, row, col);
    }

    public NavigableMap<Piece, NavigableSet<Integer>> getBoard() { // or SortedMap, Map?
        NavigableMap<Piece, NavigableSet<Integer>> deepCopy = new TreeMap<>();
        for (var entry : board.entrySet()) {
            deepCopy.put(new Piece(entry.getKey().getType(), entry.getKey().getColor()),
                    new TreeSet<>(entry.getValue()));
        }
        return deepCopy;
    }

    public void launchApp() {
        App app = new App();
        app.loadBoard(this);
        app.launch();
    }

    public Piece.PieceColor getTurn() {
        return turn;
    }

    public void setTurn(Piece.PieceColor turn) {
        this.turn = turn;
    }

    // dummy methods without validation

    public void checkPieceConditions() {
//        if (!PositionCheck.checkPosition(GaussFunction.gaussFunction(this, false)))
//            throw new InvalidBoardException("Invalid board!");
    }

    public int getEnPassantCol() {
        return enPassantCol;
    }

    public void setEnPassantCol(int enPassantCol) {
        this.enPassantCol = enPassantCol;
    }
}
