package dev.michalrelich.tablebase.frontend.swing;

import dev.michalrelich.tablebase.frontend.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ChessPanel extends JPanel {

    private Image boardImage;
    private BufferedImage piecesImage;
    private Image[] pieces;
    private final int IMAGE_MARGIN = 100;

    public ChessPanel() {
        ImageIcon ii = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("img.png")));
        boardImage = ii.getImage();
        this.setPreferredSize(new Dimension(boardImage.getWidth(this) + IMAGE_MARGIN * 2,
                boardImage.getHeight(this) + IMAGE_MARGIN * 2));

        try {
            piecesImage = ImageIO.read(new File("img_1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadPiece(Piece piece, int position) { // position 1 - 64
        int x = 0;
        int y = 0;

        switch (piece.getType()) {
            case KING -> {}
            case QUEEN -> x = piecesImage.getWidth() / 6;
            case BISHOP -> x = piecesImage.getWidth() / 6 * 2;
            case KNIGHT -> x = piecesImage.getWidth() / 6 * 3;
            case ROOK -> x = piecesImage.getWidth() / 6 * 4;
            case PAWN -> x = piecesImage.getWidth() / 6  * 5;
        }

        switch (piece.getColor()) {
            case WHITE -> {}
            case BLACK -> y = piecesImage.getHeight() / 2;
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(boardImage, 100, 100, null);
    }
}
