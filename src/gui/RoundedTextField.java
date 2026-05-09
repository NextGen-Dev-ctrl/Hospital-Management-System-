// package src.gui;

// import javax.swing.*;
// import java.awt.*;

// public class RoundedTextField extends JTextField {
//     private String hint = "";
//     public RoundedTextField(int columns) {
//         super(columns);
//         setOpaque(false);
//     }

//     @Override
//     protected void paintComponent(Graphics g) {
//         Graphics2D g2 = (Graphics2D) g;

//         g2.setRenderingHint(
//             RenderingHints.KEY_ANTIALIASING,
//             RenderingHints.VALUE_ANTIALIAS_ON
//         );

//         g2.setColor(Color.WHITE);
//         g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);



//         super.paintComponent(g);
//     }

//     @Override
//     protected void paintBorder(Graphics g) {
//         Graphics2D g2 = (Graphics2D) g;

//         g2.setColor(Color.GRAY);
//         g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);
//     }
//     public void setHint(String hint) {

//             this.hint = hint;

//             repaint();
//         }
//         @Override
//         protected void paintComponent(Graphics g) {

//             super.paintComponent(g);

//             if(getText().isEmpty()) {

//                 Graphics2D g2 =
//                         (Graphics2D) g.create();

//                 g2.setColor(Color.GRAY);

//                 g2.setFont(getFont());

//                 g2.drawString(
//                         hint,
//                         15,
//                         22);

//                 g2.dispose();
//             }
//         }
// }
package src.gui;

import javax.swing.*;
import java.awt.*;

public class RoundedTextField extends JTextField {
    private String hint = "";

    public RoundedTextField(int columns) {
        super(columns);
        setOpaque(false); // ensures custom painting works
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw rounded background
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

        g2.dispose();

        // Paint text field content
        super.paintComponent(g);

        // Draw hint if empty
        if (getText().isEmpty() && hint != null && !hint.isEmpty()) {
            Graphics2D gHint = (Graphics2D) g.create();
            gHint.setColor(Color.GRAY);
            gHint.setFont(getFont());
            gHint.drawString(hint, 15, getHeight() / 2 + g.getFontMetrics().getAscent() / 2 - 2);
            gHint.dispose();
        }
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
        g2.dispose();
    }

    public void setHint(String hint) {
        this.hint = hint;
        repaint();
    }
}
