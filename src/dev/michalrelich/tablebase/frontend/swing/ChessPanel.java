package dev.michalrelich.tablebase.frontend.swing;

import dev.michalrelich.tablebase.frontend.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ChessPanel extends JPanel {

    private class PieceImage {
        private BufferedImage image;
        private int x;
        private int y;

        public PieceImage(BufferedImage image, int x, int y) {
            this.image = image;
            this.x = x;
            this.y = y;
        }
    }

    private BufferedImage boardImage;
    private BufferedImage piecesImage;
    private ArrayList<PieceImage> pieces = new ArrayList<>();
    private final int IMAGE_MARGIN = 100;

    public ChessPanel() {

        try {
        boardImage = ImageIO.read(Objects.requireNonNull(
                this.getClass().getResource("/dev/michalrelich/tablebase/resources/board.png")));
        piecesImage = ImageIO.read(Objects.requireNonNull(
                this.getClass().getResource("/dev/michalrelich/tablebase/resources/pieces.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.setPreferredSize(new Dimension(boardImage.getWidth(this) + IMAGE_MARGIN * 2,
                boardImage.getHeight(this) + IMAGE_MARGIN * 2));
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

        BufferedImage image = piecesImage.getSubimage(x, y,
                piecesImage.getWidth() / 6, piecesImage.getHeight() / 2);

        pieces.add(new PieceImage(image, 100, 100)); // UNFINISHED HERE!
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(boardImage, 100, 100, null);

        pieces.forEach(p -> g.drawImage(p.image, p.x, p.y,
                    null));
    }
}
