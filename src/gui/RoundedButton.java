package src.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RoundedButton extends JButton {

    private Color borderColor;

    // =========================
    // HOVER EFFECT
    // =========================
    private Color hoverBackground;

    private Color normalBackground;

    private boolean hovered = false;

    // =========================
    // CUSTOMIZATION
    // =========================
    private int radius = 30;

    private int shadowSize = 6;

    private int shadowOpacity = 20;

    public RoundedButton(String text) {

        super(text);

        setContentAreaFilled(false);

        setFocusPainted(false);

        setBorderPainted(false);

        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // DEFAULT BORDER = BACKGROUND
        borderColor = getBackground();

        // =========================
        // HOVER EFFECT
        // =========================
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                hovered = true;

                normalBackground = getBackground();

                // LIGHT MODERN HOVER EFFECT
                hoverBackground = new Color(
                        Math.min(normalBackground.getRed() + 12, 255),
                        Math.min(normalBackground.getGreen() + 12, 255),
                        Math.min(normalBackground.getBlue() + 12, 255));

                setBackground(hoverBackground);

                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {

                hovered = false;

                setBackground(normalBackground);

                repaint();
            }
        });
    }

    // =========================
    // SET BORDER COLOR
    // =========================
    public void setBorderColor(Color color) {

        this.borderColor = color;

        repaint();
    }

    // =========================
    // GET BORDER COLOR
    // =========================
    public Color getBorderColor() {

        return borderColor;
    }

    // =========================
    // SET RADIUS
    // =========================
    public void setRadius(int radius) {

        this.radius = radius;

        repaint();
    }

    // =========================
    // SET SHADOW SIZE
    // =========================
    public void setShadowSize(int size) {

        this.shadowSize = size;

        repaint();
    }

    // =========================
    // SET SHADOW OPACITY
    // =========================
    public void setShadowOpacity(int opacity) {

        this.shadowOpacity = opacity;

        repaint();
    }

    // =========================
    // OPTIONAL ICON SUPPORT
    // =========================
    public void setButtonIcon(String path) {

        ImageIcon icon = new ImageIcon(path);

        Image img = icon.getImage().getScaledInstance(
                18,
                18,
                Image.SCALE_SMOOTH);

        setIcon(new ImageIcon(img));

        setHorizontalTextPosition(SwingConstants.RIGHT);

        setIconTextGap(10);
    }

    @Override
    public void setBackground(Color bg) {

        super.setBackground(bg);

        // AUTO MATCH BORDER WITH BACKGROUND
        if (borderColor == null) {

            borderColor = bg;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // =========================
        // MODERN SHADOW
        // =========================
        if (hovered) {

            g2.setColor(new Color(0, 0, 0, shadowOpacity));

            g2.fillRoundRect(
                    shadowSize / 2,
                    shadowSize,
                    getWidth() - shadowSize,
                    getHeight() - shadowSize / 2,
                    radius,
                    radius);
        }

        // =========================
        // BUTTON BACKGROUND
        // =========================
        g2.setColor(getBackground());

        g2.fillRoundRect(
                0,
                0,
                getWidth() - 1,
                getHeight() - 1,
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

        // =========================
        // BORDER COLOR
        // =========================
        g2.setColor(borderColor);

        g2.drawRoundRect(
                0,
                0,
                getWidth() - 1,
                getHeight() - 1,
                radius,
                radius);

        g2.dispose();
    }
}