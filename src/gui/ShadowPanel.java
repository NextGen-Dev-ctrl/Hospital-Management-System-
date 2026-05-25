package src.gui;

import javax.swing.*;
import java.awt.*;

public class ShadowPanel extends JPanel {

    private int radius = 40;

    private Color shadowColor = new Color(0, 0, 0, 12);

    public ShadowPanel() {

        setOpaque(false);

        // DEFAULT MODERN CARD COLOR
        setBackground(new Color(255, 255, 255, 245));
    }

    // =========================
    // OPTIONAL RADIUS SETTER
    // =========================
    public void setRadius(int radius) {

        this.radius = radius;

        repaint();
    }

    // =========================
    // OPTIONAL SHADOW COLOR
    // =========================
    public void setShadowColor(Color color) {

        this.shadowColor = color;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // =========================
        // SOFT MODERN SHADOW
        // =========================
        g2.setColor(shadowColor);

        g2.fillRoundRect(
                8,
                8,
                getWidth() - 16,
                getHeight() - 16,
                radius,
                radius);

        // =========================
        // MAIN CARD
        // =========================
        g2.setColor(getBackground());

        g2.fillRoundRect(
                0,
                0,
                getWidth() - 16,
                getHeight() - 16,
                radius,
                radius);

        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // SOFT BORDER
        g2.setColor(new Color(230, 230, 230));

        g2.drawRoundRect(
                0,
                0,
                getWidth() - 17,
                getHeight() - 17,
                radius,
                radius);

        g2.dispose();
    }
}