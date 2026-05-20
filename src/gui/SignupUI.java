package src.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import src.db.DBconnection;

public class SignupUI extends JFrame {
    public SignupUI() {

        // Main panel with title hospital management on title bar user
        setTitle("Hospital Management System - Signup");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color bgColor = Color.decode("#F2EFE7"); // Background Color
        Color fgColor = Color.decode("#00A19B"); // Forground Color

        // MAIN PANEL (SPLIT) into the Border Layout
        setLayout(new BorderLayout());

        // LEFT PANEL (IMAGE) we add the image in the left side
        JPanel leftPanel = new JPanel() {
            Image image = new ImageIcon("images/login2.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int panelWidth = getWidth();
                int panelHeight = getHeight();

                int imgWidth = image.getWidth(null);
                int imgHeight = image.getHeight(null);

                double scale = Math.max(
                        (double) panelWidth / imgWidth,
                        (double) panelHeight / imgHeight);

                int newWidth = (int) (imgWidth * scale);
                int newHeight = (int) (imgHeight * scale);

                int x = (panelWidth - newWidth) / 2;
                int y = (panelHeight - newHeight) / 2;

                g.drawImage(image, x, y, newWidth, newHeight, this);
            }
        };
        // we use different panel for each one field
        JPanel roleContainer = new JPanel();
        roleContainer.setLayout(null);
        roleContainer.setBounds(0, 150, 425, 450);
        roleContainer.setBackground(bgColor);

        JPanel doctorPanel = new JPanel();
        doctorPanel.setBounds(0, 0, 425, 450);
        doctorPanel.setLayout(null);
        doctorPanel.setBackground(bgColor);

        JPanel receptionistPanel = new JPanel();
        receptionistPanel.setBounds(0, 0, 425, 450);
        receptionistPanel.setLayout(null);
        receptionistPanel.setBackground(bgColor);

        // RIGHT PANEL (Main Login credentials) it contain all credential we can get
        // according to role
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(425, 0)); // we can adjust the right panel from here
        rightPanel.setBackground(bgColor);
        rightPanel.setLayout(null);

        // Main title sign up
        JLabel title = new JLabel("Sign UP");
        title.setBounds(130, 20, 500, 80);
        title.setFont(new Font("Segoe UI", Font.BOLD, 50));
        title.setForeground(fgColor); // use the main forground color here

        // use role to change the credential of the sign up
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(95, 140, 100, 25);
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        roleLabel.setForeground(fgColor);

        String[] roles = { "Admin", "Doctor", "Receptionist", "Nurse" };
        RoundedComboBox<String> roleBox = new RoundedComboBox<>(roles);
        roleBox.setBounds(150, 130, 220, 40);
        roleBox.setFont(new Font("Segoe UI", Font.BOLD, 12));
        roleBox.setForeground(Color.BLACK);

        // Name fields that get full name
        JLabel firstname = new JLabel("Full Name:");
        firstname.setBounds(60, 187, 100, 25);
        firstname.setFont(new Font("Segoe UI", Font.BOLD, 14));
        firstname.setForeground(fgColor);

        RoundedTextField nameField = new RoundedTextField(20);
        nameField.setBounds(150, 183, 220, 30);
        nameField.setMargin(new Insets(5, 10, 5, 10));

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        genderLabel.setForeground(fgColor);
        genderLabel.setBounds(81, 238, 100, 25);

        String[] gender = { "Male", "Female", "Other" };
        RoundedComboBox<String> genderBox = new RoundedComboBox<>(gender);
        genderBox.setBounds(150, 230, 220, 40);
        genderBox.setFont(new Font("Segoe UI", Font.BOLD, 12));
        genderBox.setForeground(Color.BLACK);

        JLabel EmailLable = new JLabel("Email:");
        EmailLable.setBounds(94, 285, 100, 25);
        EmailLable.setFont(new Font("Segoe UI", Font.BOLD, 14));
        EmailLable.setForeground(fgColor);

        RoundedTextField EmailField = new RoundedTextField(20);
        EmailField.setBounds(150, 280, 220, 30);
        EmailField.setMargin(new Insets(5, 10, 5, 10));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(72, 335, 100, 25);
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        usernameLabel.setForeground(fgColor);

        RoundedTextField usernamTextField = new RoundedTextField(20);
        usernamTextField.setBounds(150, 330, 220, 30);
        usernamTextField.setMargin(new Insets(5, 10, 5, 10));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(73, 385, 100, 25);
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passwordLabel.setForeground(fgColor);

        RoundedPasswordField passwordTextField = new RoundedPasswordField(20);
        passwordTextField.setBounds(150, 380, 220, 30);

        JLabel cPasswordLabel = new JLabel("Confirm Password:");
        cPasswordLabel.setBounds(20, 435, 150, 25);
        cPasswordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cPasswordLabel.setForeground(fgColor);

        RoundedPasswordField cPasswordTextField = new RoundedPasswordField(20);
        cPasswordTextField.setBounds(150, 430, 220, 30);

        RoundedButton signupButton = new RoundedButton("Sign Up");
        signupButton.setBounds(80, 490, 280, 30);
        signupButton.setMargin(new Insets(5, 10, 5, 10));
        signupButton.setForeground(bgColor);
        signupButton.setBackground(fgColor);

        // already have account option
        JLabel loginText = new JLabel("<html>Already have an account? <u>Login</u></html>");
        loginText.setBounds(120, 530, 250, 30);
        loginText.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginText.setForeground(fgColor);
        loginText.setCursor(new Cursor(Cursor.HAND_CURSOR));

        rightPanel.add(title);
        rightPanel.add(firstname);
        rightPanel.add(nameField);
        rightPanel.add(genderLabel);
        rightPanel.add(genderBox);
        rightPanel.add(roleLabel);
        rightPanel.add(roleBox);
        rightPanel.add(EmailLable);
        rightPanel.add(EmailField);
        rightPanel.add(usernameLabel);
        rightPanel.add(usernamTextField);
        rightPanel.add(passwordLabel);
        rightPanel.add(passwordTextField);
        rightPanel.add(cPasswordLabel);
        rightPanel.add(cPasswordTextField);
        rightPanel.add(signupButton);
        rightPanel.add(loginText);
        // ADD BOTH PANELS on to the frame
        add(leftPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // logic that send back to the login page for the alreaday hava account field
        loginText.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                dispose(); // close signup page

                new LoginUI(); // open login page
            }
        });

        // checking the format of the credentail that it is valid
        // also if the user can enter all the credentials that the data is saved in DB
        signupButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // GET DATA
                String fullname = nameField.getText().trim();
                String gender = (String) genderBox.getSelectedItem();
                String role = (String) roleBox.getSelectedItem();
                String email = EmailField.getText().trim();
                String username = usernamTextField.getText().trim();
                String password = passwordTextField.getText();
                String confirmPassword = cPasswordTextField.getText();

                // VALIDATION

                // FULL NAME
                if (fullname.isEmpty()) {

                    JOptionPane.showMessageDialog(null,
                            "Full Name is required!");

                    nameField.requestFocus();
                    return;
                }

                // EMAIL
                if (email.isEmpty()) {

                    JOptionPane.showMessageDialog(null,
                            "Email is required!");

                    EmailField.requestFocus();
                    return;
                }

                // EMAIL FORMAT CHECK
                if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {

                    JOptionPane.showMessageDialog(null,
                            "Invalid Email Format!");

                    EmailField.requestFocus();
                    return;
                }

                // USERNAME
                if (username.isEmpty()) {

                    JOptionPane.showMessageDialog(null,
                            "Username is required!");

                    usernamTextField.requestFocus();
                    return;
                }

                // NO SPACES + ONLY LETTERS NUMBERS UNDERSCORE
                if (!username.matches("^[A-Za-z0-9_]+$")) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Username can only contain letters, numbers and underscore (_) with no spaces!");

                    return;
                }

                // PASSWORD
                if (password.isEmpty()) {

                    JOptionPane.showMessageDialog(null,
                            "Password is required!");

                    return;
                }

                // CONFIRM PASSWORD
                if (confirmPassword.isEmpty()) {

                    JOptionPane.showMessageDialog(null,
                            "Confirm Password is required!");

                    return;
                }

                // PASSWORD MATCH CHECK
                if (!password.equals(confirmPassword)) {

                    JOptionPane.showMessageDialog(null,
                            "Passwords do not match!");

                    return;
                }

                // DATABASE INSERT
                try {

                    Connection con = DBconnection.getConnection();

                    String query = "INSERT INTO users " + "(fullname, gender, role, email, username, password) "
                            + "VALUES (?, ?, ?, ?, ?, ?)";

                    PreparedStatement pst = con.prepareStatement(query);

                    pst.setString(1, fullname);
                    pst.setString(2, gender);
                    pst.setString(3, role);
                    pst.setString(4, email);
                    pst.setString(5, username);
                    pst.setString(6, password);

                    int rows = pst.executeUpdate();

                    if (rows > 0) {

                        JOptionPane.showMessageDialog(null,
                                "Signup Successful!");

                        // RESET FIELDS
                        nameField.setText("");
                        EmailField.setText("");
                        usernamTextField.setText("");
                        passwordTextField.setText("");
                        cPasswordTextField.setText("");

                        roleBox.setSelectedIndex(0);
                        genderBox.setSelectedIndex(0);

                    } else {

                        JOptionPane.showMessageDialog(null,
                                "Signup Failed!");
                    }

                    pst.close();
                    con.close();

                } catch (Exception ex) {

                    ex.printStackTrace();

                    JOptionPane.showMessageDialog(null,
                            "Database Error!");
                }
            }
        });

        setVisible(true);
    }

}
