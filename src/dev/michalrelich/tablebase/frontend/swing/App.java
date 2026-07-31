package dev.michalrelich.tablebase.frontend.swing;

import dev.michalrelich.tablebase.frontend.Board;
import dev.michalrelich.tablebase.frontend.Piece;

import javax.swing.*;

public class App {

    private final ChessPanel panel = new ChessPanel();

    public void launch() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public void loadPiece(Piece piece, int position) {
        panel.loadPiece(piece, position);
    }

    public void loadBoard(Board board) {
        var map = board.getBoard();

        for (var entry : map.entrySet()) {
            if (entry != null) {
                Piece key = entry.getKey();
                entry.getValue().forEach(p -> loadPiece(key, p));
            }
        }
    }
}
