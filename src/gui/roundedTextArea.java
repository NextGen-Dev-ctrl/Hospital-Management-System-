package src.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class roundedTextArea extends JTextArea {

    private int radius = 25;

    private String hint = "Write consultation notes here...";

    public roundedTextArea() {

        setOpaque(false);

        setFont(new Font("Segoe UI", Font.PLAIN, 16));

        setLineWrap(true);

        setWrapStyleWord(true);

        setBorder(new EmptyBorder(15, 15, 15, 15));
    }

    // SET CUSTOM HINT
    public void setHint(String hint) {
        this.hint = hint;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // BACKGROUND
        g2.setColor(Color.WHITE);

        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                radius,
                radius);

        super.paintComponent(g);

        // DRAW HINT
        if (getText().isEmpty()) {

            g2.setColor(Color.GRAY);

            g2.setFont(new Font("Segoe UI", Font.ITALIC, 15));

            Insets ins = getInsets();

            g2.drawString(
                    hint,
                    ins.left + 5,
                    ins.top + 15);
        }

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.decode("#00A19B"));

        g2.setStroke(new BasicStroke(2));

        g2.drawRoundRect(
                1,
                1,
                getWidth() - 3,
                getHeight() - 3,
                radius,
                radius);

        g2.dispose();
    }
}