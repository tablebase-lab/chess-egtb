package dev.michalrelich.tablebase.frontend.swing;

import dev.michalrelich.tablebase.frontend.Board;
import dev.michalrelich.tablebase.frontend.Piece;

import javax.swing.*;

public class App {

    private static final ChessPanel panel = new ChessPanel();

    public static void launch() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public static void loadPiece(Piece piece, int position) {
        panel.loadPiece(piece, position);
    }

    public static void loadBoard(Board board) {

    }
}
