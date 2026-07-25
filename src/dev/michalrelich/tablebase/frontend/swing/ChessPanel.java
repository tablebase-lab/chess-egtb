package dev.michalrelich.tablebase.frontend.swing;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ChessPanel extends JPanel {

    private Image image;
    private int imageHeight;
    private int imageWidth;
    private final int IMAGE_MARGIN = 100;

    public ChessPanel() {
        ImageIcon ii = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("img.png")));
        image = ii.getImage();
        imageHeight = image.getHeight(this);
        imageWidth = image.getWidth(this);
        this.setPreferredSize(new Dimension(imageWidth + IMAGE_MARGIN * 2, imageHeight + IMAGE_MARGIN * 2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 100, 100, null);
    }
}
