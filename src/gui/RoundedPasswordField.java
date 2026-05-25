package src.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RoundedPasswordField extends JPanel {

    private JPasswordField passwordField;

    private JButton showButton;

    private boolean passwordVisible = false;

    private String hint = "";

    public RoundedPasswordField(int columns) {

        setLayout(new BorderLayout());

        setOpaque(false);

        // PASSWORD FIELD
        passwordField = new JPasswordField(columns) {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                // DRAW HINT
                if (getPassword().length == 0 &&
                        !isFocusOwner() &&
                        !hint.isEmpty()) {

                    Graphics2D g2 = (Graphics2D) g.create();

                    g2.setRenderingHint(
                            RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

                    g2.setColor(new Color(150, 150, 150));

                    g2.setFont(getFont());

                    Insets in = getInsets();

                    FontMetrics fm = g2.getFontMetrics();

                    g2.drawString(
                            hint,
                            in.left,
                            getHeight() / 2 + fm.getAscent() / 2 - 2);

                    g2.dispose();
                }
            }
        };

        passwordField.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        10,
                        5,
                        10));

        passwordField.setOpaque(false);

        passwordField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14));

        passwordField.setEchoChar('•');

        // SHOW BUTTON
        showButton = new JButton("Show");

        showButton.setFocusPainted(false);

        showButton.setBorderPainted(false);

        showButton.setContentAreaFilled(false);

        showButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR));

        showButton.setForeground(
                Color.decode("#00A19B"));

        // TOGGLE PASSWORD
        showButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (passwordVisible) {

                    passwordField.setEchoChar('•');

                    showButton.setText("Show");

                    passwordVisible = false;

                } else {

                    passwordField.setEchoChar((char) 0);

                    showButton.setText("Hide");

                    passwordVisible = true;
                }
            }
        });
        passwordField.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {

                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {

                repaint();
            }
        });
        passwordField.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {

                    public void insertUpdate(
                            javax.swing.event.DocumentEvent e) {

                        repaint();
                    }

                    public void removeUpdate(
                            javax.swing.event.DocumentEvent e) {

                        repaint();
                    }

                    public void changedUpdate(
                            javax.swing.event.DocumentEvent e) {

                        repaint();
                    }
                });

        add(passwordField, BorderLayout.CENTER);

        add(showButton, BorderLayout.EAST);
    }

    // =========================
    // GET PASSWORD
    // =========================
    public String getText() {

        return new String(passwordField.getPassword());
    }

    // =========================
    // SET PASSWORD
    // =========================
    public void setText(String text) {

        passwordField.setText(text);
    }

    // =========================
    // SET MARGIN
    // =========================
    public void setMargin(Insets insets) {

        passwordField.setMargin(insets);
    }

    // =========================
    // SET HINT
    // =========================
    public void setHint(String hint) {

        this.hint = hint;

        repaint();
    }

    // =========================
    // CUSTOM ROUNDED DESIGN
    // =========================
    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

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
                25,
                25);

        // BORDER
        g2.setColor(new Color(220, 220, 220));

        g2.drawRoundRect(
                0,
                0,
                getWidth() - 1,
                getHeight() - 1,
                25,
                25);

        super.paintComponent(g);
    }
}