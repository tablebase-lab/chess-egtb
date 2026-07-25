package dev.michalrelich.tablebase.frontend.swing;

import javax.swing.*;

public class App {

    public static void launch() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new ChessPanel());
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
