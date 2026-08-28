package dev.michalrelich.tablebase.frontend;

import dev.michalrelich.tablebase.backend.helper.HasEnPassant;
import dev.michalrelich.tablebase.backend.helper.PositionCheck;
import dev.michalrelich.tablebase.exceptions.InvalidBoardException;
import dev.michalrelich.tablebase.frontend.swing.App;
import dev.michalrelich.tablebase.gaussfunction.GaussFunction;

import java.util.*;

public class Board {

    public static final int BOARD_LENGTH = 8;
    public static final int MAX_PIECE_COUNT = 3;
    private final Map<Piece, NavigableSet<Integer>> board = new TreeMap<>();
    private boolean isEnPassant;
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

    public void checkPieceConditions() {
        if (!PositionCheck.checkPosition(GaussFunction.gaussFunction(this, false)))
            throw new InvalidBoardException("Invalid board!");
    }

    public void launchApp() {
        App app = new App();
        app.loadBoard(this);
        app.launch();
    }

    public boolean isEnPassant() {
        return isEnPassant;
    }

    public boolean setEnPassant() {

        this.isEnPassant = HasEnPassant.forLong(GaussFunction.gaussFunction(this, false)) != -1;

        System.out.println(this.isEnPassant);
        return this.isEnPassant;
    }

    public Piece.PieceColor getTurn() {
        return turn;
    }

    public void setTurn(Piece.PieceColor turn) {
        this.turn = turn;
    }
}
