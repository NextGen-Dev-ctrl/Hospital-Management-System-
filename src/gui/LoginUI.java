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

        // Main title We display the hospital name there
        JLabel title = new JLabel("MediSync HMS");
        title.setBounds(70, 100, 500, 80);
        title.setFont(new Font("Segoe UI", Font.BOLD, 40));
        title.setForeground(fgColor); // use the main forground color here

        // username label and text field for input
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(70, 200, 100, 25);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(fgColor);

        RoundedTextField usernameField = new RoundedTextField(20);
        usernameField.setBounds(150, 200, 200, 30);
        usernameField.setMargin(new Insets(5, 10, 5, 10));

        // password label and password field which cannot show password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(70, 250, 100, 25);
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passLabel.setForeground(fgColor);

        RoundedPasswordField passwordField = new RoundedPasswordField(20);
        passwordField.setBounds(150, 250, 200, 30);

        // role label and role combo box which use to show the list of roles
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(75, 300, 100, 25);
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        roleLabel.setForeground(fgColor);

        String[] roles = { "Admin", "Doctor", "Receptionist", "Nurse" };
        RoundedComboBox<String> roleBox = new RoundedComboBox<>(roles);
        roleBox.setBounds(150, 300, 200, 40);
        roleBox.setFont(new Font("Segoe UI", Font.BOLD, 12));
        roleBox.setForeground(Color.BLACK);

        // login button
        RoundedButton loginBtn = new RoundedButton("Login");
        loginBtn.setBounds(100, 370, 100, 40);
        loginBtn.setMargin(new Insets(5, 10, 5, 10));
        loginBtn.setBackground(fgColor);
        loginBtn.setForeground(bgColor);

        // Singup button
        RoundedButton signupBtn = new RoundedButton("Signup");
        signupBtn.setBounds(250, 370, 100, 40);
        signupBtn.setMargin(new Insets(5, 10, 5, 10));
        signupBtn.setBackground(fgColor);
        signupBtn.setForeground(bgColor);

        // Forget password field
        JLabel forgot = new JLabel("Forgot Password?");
        forgot.setBounds(180, 430, 150, 20);
        forgot.setForeground(fgColor);

        // Add all component on the pannel
        rightPanel.add(title);
        rightPanel.add(userLabel);
        rightPanel.add(usernameField);
        rightPanel.add(passLabel);
        rightPanel.add(passwordField);
        rightPanel.add(roleLabel);
        rightPanel.add(roleBox);
        rightPanel.add(loginBtn);
        rightPanel.add(signupBtn);
        rightPanel.add(forgot);

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

                                String specialization = doctorRs.getString("specialization");

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
                        else if(role.equals("Nurse"))
                        {
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