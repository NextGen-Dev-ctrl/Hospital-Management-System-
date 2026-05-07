package src.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RoundedPasswordField extends JPanel {

    private JPasswordField passwordField;
    private JButton showButton;
    private boolean passwordVisible = false;

    public RoundedPasswordField(int columns) {

        setLayout(new BorderLayout());
        setOpaque(false);

        // PASSWORD FIELD
        passwordField = new JPasswordField(columns);
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        passwordField.setOpaque(false);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setEchoChar('•');

        // SHOW BUTTON
        showButton = new JButton("Show");
        showButton.setFocusPainted(false);
        showButton.setBorderPainted(false);
        showButton.setContentAreaFilled(false);
        showButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

        add(passwordField, BorderLayout.CENTER);
        add(showButton, BorderLayout.EAST);
    }

    // GET PASSWORD
    public String getText() {
        return new String(passwordField.getPassword());
    }

    // SET PASSWORD
    public void setText(String text) {
        passwordField.setText(text);
    }

    // CUSTOM ROUNDED DESIGN
    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // BACKGROUND
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

        // BORDER
        g2.setColor(new Color(200, 200, 200));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);

        super.paintComponent(g);
    }
}