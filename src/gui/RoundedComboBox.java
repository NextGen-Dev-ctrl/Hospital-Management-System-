package src.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RoundedComboBox<E> extends JComboBox<E> {

    // ✅ Empty constructor (like default JComboBox)
    public RoundedComboBox() {
        super();
        initUI();
    }

    // ✅ Constructor with array (your original one)
    public RoundedComboBox(E[] items) {
        super(items);
        initUI();
    }

    // ✅ Constructor with ComboBoxModel (like new JComboBox(model))
    public RoundedComboBox(ComboBoxModel<E> model) {
        super(model);
        initUI();
    }

    // Shared UI setup
    private void initUI() {
        setOpaque(false);
        setFocusable(false);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Padding
        setBorder(new EmptyBorder(5, 10, 5, 10));

        // Custom UI
        setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton("▼");
                button.setBorder(BorderFactory.createEmptyBorder());
                button.setContentAreaFilled(false);
                button.setFocusPainted(false);
                button.setForeground(Color.GRAY);
                return button;
            }
        });

        // Renderer for rounded dropdown items
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                label.setBorder(new EmptyBorder(5, 10, 5, 10));
                label.setFont(new Font("Segoe UI", Font.PLAIN, 14));

                return label;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Border color
        g2.setColor(new Color(200, 200, 200));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);

        g2.dispose();
    }
}
