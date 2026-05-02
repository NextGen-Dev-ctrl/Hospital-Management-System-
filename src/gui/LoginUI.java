package src.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import src.db.*;

public class LoginUI extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;
    JComboBox<String> roleBox;

    public LoginUI() {
        setTitle("Hospital Management System - Login");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color bgColor = Color.decode("#FFF8F0");
        Color primary = Color.decode("#C08552");    //number 2 color palate
        Color dark = Color.decode("#4B2E2B");

        // Color bgColor = Color.decode("#F3EEEA");
        // Color primary = Color.decode("#776B5D");
        // Color dark = Color.decode("#B0A695");     
        
        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setLayout(null);

        JLabel title = new JLabel("Hospital System");
        title.setBounds(100, 30, 250, 30);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        title.setForeground(dark);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 100, 100, 25);

        usernameField = new JTextField();
        usernameField.setBounds(150, 100, 150, 25);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 140, 100, 25);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 140, 150, 25);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(50, 180, 100, 25);

        String[] roles = {"Admin", "Doctor", "Receptionist"};
        roleBox = new JComboBox<>(roles);
        roleBox.setBounds(150, 180, 150, 25);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(120, 240, 120, 35);
        loginBtn.setBackground(primary);
        loginBtn.setForeground(Color.WHITE);

        panel.add(title);
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);
        panel.add(roleLabel);
        panel.add(roleBox);
        panel.add(loginBtn);

        add(panel);

        // Button Action
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = usernameField.getText();
                String pass = new String(passwordField.getPassword());
                String role = (String) roleBox.getSelectedItem();

                // TEMP LOGIN (later connect DB)
                if (user.equals("admin") && pass.equals("123")) {
                    new DashboardUI(role);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Login!");
                }
            }
        });
//     loginBtn.addActionListener(new ActionListener() {
//     public void actionPerformed(ActionEvent e) {

//         String user = usernameField.getText();
//         String pass = new String(passwordField.getPassword());
//         String role = (String) roleBox.getSelectedItem();

//         try {
//             Connection con = DBConnection.getConnection();

//             String query = "SELECT * FROM users WHERE username=? AND password=? AND role=?";
//             PreparedStatement pst = con.prepareStatement(query);

//             pst.setString(1, user);
//             pst.setString(2, pass);
//             pst.setString(3, role);

//             ResultSet rs = pst.executeQuery();

//             if (rs.next()) {
//                 JOptionPane.showMessageDialog(null, "Login Successful!");

//                 new DashboardUI(role);
//                 dispose();
//             } else {
//                 JOptionPane.showMessageDialog(null, "Invalid Username or Password!");
//             }

//             con.close();

//         } catch (Exception ex) {
//             ex.printStackTrace();
//         }
//     }
// });

        setVisible(true);
    }
}