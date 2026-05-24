package src.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import src.db.DBconnection;

public class LoginUI extends JFrame {
        public LoginUI() {
                // Main panel with title hospital management on title bar user
                setTitle("Hospital Management System");

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

                // RIGHT PANEL (Main Login credentials) it contain usernanme password and role
                // fields
                JPanel rightPanel = new JPanel();
                rightPanel.setPreferredSize(new Dimension(425, 0)); // we can adjust the right panel from here
                rightPanel.setBackground(bgColor);
                rightPanel.setLayout(null);

                ShadowPanel shadowPanel = new ShadowPanel();
                shadowPanel.setBounds(20, 20, 400, 580);
                shadowPanel.setLayout(null);
                rightPanel.add(shadowPanel);

                // Main title We display the hospital name there
                JLabel title = new JLabel("MediSync HMS");
                // title.setBounds(100, 200, 400, 80);
                title.setBounds(0, 40, shadowPanel.getWidth(), 50);
                title.setHorizontalAlignment(SwingConstants.CENTER);
                title.setFont(new Font("Segoe UI", Font.BOLD, 30));
                title.setForeground(fgColor); // use the main forground color here

                JLabel loginText = new JLabel("Login to continue");
                loginText.setBounds(0, 110, shadowPanel.getWidth(), 30);
                loginText.setHorizontalAlignment(SwingConstants.CENTER);
                loginText.setFont(new Font("Segoe UI", Font.BOLD, 13));
                loginText.setForeground(Color.BLACK);

                RoundedTextField usernameField = new RoundedTextField(20);
                usernameField.setBounds(25, 160, 340, 50);
                usernameField.setMargin(new Insets(5, 10, 5, 10));
                usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                usernameField.setForeground(Color.BLACK);
                usernameField.setHint("Enter Username");

                RoundedPasswordField passwordField = new RoundedPasswordField(20);
                passwordField.setBounds(25, 230, 340, 50);
                passwordField.setMargin(new Insets(5, 10, 5, 10));
                passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                passwordField.setForeground(Color.BLACK);
                passwordField.setHint(" Enter Password");

                String[] roles = { "Admin", "Doctor", "Receptionist", "Nurse" };
                RoundedComboBox<String> roleBox = new RoundedComboBox<>(roles);
                roleBox.setBounds(25, 300, 340, 50);
                roleBox.setFont(new Font("Segoe UI", Font.BOLD, 12));
                roleBox.setForeground(Color.BLACK);

                // login button
                RoundedButton loginBtn = new RoundedButton("Login");
                loginBtn.setBounds(25, 370, 340, 50);
                loginBtn.setMargin(new Insets(5, 10, 5, 10));
                loginBtn.setBackground(fgColor);
                loginBtn.setForeground(Color.WHITE);
                // loginBtn.setButtonIcon("images/login.png");

                // Singup button
                RoundedButton signupBtn = new RoundedButton("Signup");
                signupBtn.setBounds(25, 440, 340, 50);
                signupBtn.setMargin(new Insets(5, 10, 5, 10));
                signupBtn.setBackground(Color.WHITE);
                signupBtn.setForeground(fgColor);
                signupBtn.setBorderColor(fgColor);

                // Forget password field
                JLabel forgot = new JLabel("Forgot Password?");
                forgot.setBounds(0, 500, shadowPanel.getWidth(), 30);
                forgot.setHorizontalAlignment(SwingConstants.CENTER);
                forgot.setForeground(fgColor);

                // Add all component on the pannel
                shadowPanel.add(title);
                shadowPanel.add(loginText);
                shadowPanel.add(usernameField);
                shadowPanel.add(passwordField);
                shadowPanel.add(roleBox);
                shadowPanel.add(loginBtn);
                shadowPanel.add(signupBtn);
                shadowPanel.add(forgot);

                // ADD BOTH PANELS on to the frame
                add(leftPanel, BorderLayout.CENTER);
                add(rightPanel, BorderLayout.EAST);
                // Logic of the login button to check the enter username and password is correct
                // It can run the the SQL query to check three credential username password and
                // role if all match then it can give access to the next portal
                loginBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                                String user = usernameField.getText();
                                String pass = new String(passwordField.getText());
                                String role = (String) roleBox.getSelectedItem();
                                try {
                                        Connection con = DBconnection.getConnection();

                                        String query = "SELECT * FROM users WHERE username=? AND password=? AND role=?";
                                        PreparedStatement pst = con.prepareStatement(query);

                                        pst.setString(1, user);
                                        pst.setString(2, pass);
                                        pst.setString(3, role);

                                        ResultSet rs = pst.executeQuery();

                                        if (rs.next()) {

                                                JOptionPane.showMessageDialog(
                                                                null,
                                                                "Login Successful!");

                                                // DOCTOR LOGIN
                                                if (role.equals("Doctor")) {

                                                        String doctorQuery = "SELECT * FROM doctors " +
                                                                        "WHERE username=? AND password=?";

                                                        PreparedStatement doctorPst = con.prepareStatement(doctorQuery);

                                                        doctorPst.setString(1, user);
                                                        doctorPst.setString(2, pass);

                                                        ResultSet doctorRs = doctorPst.executeQuery();

                                                        if (doctorRs.next()) {

                                                                int doctorId = doctorRs.getInt("doctor_id");

                                                                String doctorName = doctorRs.getString("full_name");

                                                                String specialization = doctorRs
                                                                                .getString("specialization");

                                                                new DoctorDashboard(
                                                                                doctorId,
                                                                                doctorName,
                                                                                specialization);

                                                                dispose();
                                                        }
                                                }

                                                // RECEPTIONIST LOGIN
                                                else if (role.equals("Receptionist")) {

                                                        int receptionistId = rs.getInt("id");

                                                        String receptionistNameText = rs.getString("fullname");

                                                        new ReceptionistDashboard(
                                                                        receptionistId,
                                                                        receptionistNameText);

                                                        dispose();
                                                }
                                                // Nurse login
                                                else if (role.equals("Nurse")) {
                                                        String nurseQuery = "SELECT * FROM nurses " +
                                                                        "WHERE username=? AND password=?";

                                                        PreparedStatement nursePst = con.prepareStatement(nurseQuery);

                                                        nursePst.setString(1, user);
                                                        nursePst.setString(2, pass);

                                                        ResultSet nurseRs = nursePst.executeQuery();

                                                        if (nurseRs.next()) {

                                                                int nurseId = nurseRs.getInt("nurse_id");

                                                                String nurseName = nurseRs.getString("full_name");

                                                                new NurseDashboard(
                                                                                nurseId,
                                                                                nurseName);

                                                                dispose();
                                                        }
                                                }
                                                // OTHER ROLES
                                                else {

                                                        new DashboardUI(role);

                                                        dispose();
                                                }
                                        } else {

                                                JOptionPane.showMessageDialog(
                                                                null,
                                                                "Invalid Username or Password!");
                                        }

                                        con.close();

                                } catch (Exception ex) {
                                        ex.printStackTrace();
                                }
                        }
                });

                signupBtn.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                                new SignupUI();
                                dispose();
                        }
                });

                // for the visibility of all the things
                setVisible(true);
        }
}