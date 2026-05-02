package src.gui;

import javax.swing.*;
import java.awt.*;

public class DashboardUI extends JFrame {

    public DashboardUI(String role) {

        setTitle(role + " Dashboard");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color bgColor = Color.decode("#FFF8F0");
        Color primary = Color.decode("#C08552");
        Color dark = Color.decode("#4B2E2B");

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(bgColor);

        JLabel title = new JLabel("Welcome: " + role);
        title.setBounds(20, 20, 300, 30);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(dark);

        JButton patientBtn = new JButton("Patients");
        patientBtn.setBounds(50, 100, 150, 40);
        patientBtn.setBackground(primary);
        patientBtn.setForeground(Color.WHITE);

        JButton doctorBtn = new JButton("Doctors");
        doctorBtn.setBounds(250, 100, 150, 40);
        doctorBtn.setBackground(primary);
        doctorBtn.setForeground(Color.WHITE);

        JButton appointmentBtn = new JButton("Appointments");
        appointmentBtn.setBounds(450, 100, 150, 40);
        appointmentBtn.setBackground(primary);
        appointmentBtn.setForeground(Color.WHITE);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(550, 20, 100, 30);

        logoutBtn.addActionListener(e -> {
            new LoginUI();
            dispose();
        });

        panel.add(title);
        panel.add(patientBtn);
        panel.add(doctorBtn);
        panel.add(appointmentBtn);
        panel.add(logoutBtn);

        add(panel);
        setVisible(true);
    }
}