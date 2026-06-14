package src.gui;

import src.db.DBconnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminDashboard extends JFrame {

        private Color bgColor = Color.decode("#F2EFE7");
        private Color primary = Color.decode("#00A19B");

        private JPanel contentPanel;

        private JLabel doctorsCount;
        private JLabel nursesCount;
        private JLabel patientsCount;
        private JLabel medicinesCount;
        private JLabel prescriptionsCount;
        private JButton dashboardBtn, doctorsBtn, nursesBtn, patientsBtn, medicinesBtn, reportsBtn, settingsBtn, logoutBtn, approveBtn;

        public AdminDashboard() {

                setTitle("Admin Dashboard");
                setSize(1400, 800);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLayout(new BorderLayout());

                createSidebar();
                createMainArea();

                loadStatistics();

                setVisible(true);
        }

        private void createSidebar() {

                JPanel sideBar = new JPanel();
                sideBar.setPreferredSize(new Dimension(220, 0));
                sideBar.setBackground(primary);
                sideBar.setLayout(null);

                JLabel logo = new JLabel("MediSync HMS");
                logo.setBounds(20, 40, 200, 40);
                logo.setForeground(Color.WHITE);
                logo.setFont(new Font("Segoe UI", Font.BOLD, 24));

                dashboardBtn = createSidebarButton("Dashboard");
                dashboardBtn.setBounds(0, 140, 220, 45);

                doctorsBtn = createSidebarButton("Doctors");
                doctorsBtn.setBounds(0, 190, 220, 45);

                nursesBtn = createSidebarButton("Nurses");
                nursesBtn.setBounds(0, 240, 220, 45);

                patientsBtn = createSidebarButton("Patients");
                patientsBtn.setBounds(0, 290, 220, 45);

                medicinesBtn = createSidebarButton("Medicines");
                medicinesBtn.setBounds(0, 340, 220, 45);

                reportsBtn = createSidebarButton("Reports");
                reportsBtn.setBounds(0, 390, 220, 45);

                approveBtn = createSidebarButton("Approve Users");
                approveBtn.setBounds(0, 440, 220, 45);

                settingsBtn = createSidebarButton("Settings");
                settingsBtn.setBounds(0, 490, 220, 45);

                logoutBtn = createSidebarButton("Logout");
                logoutBtn.setBounds(0, 585, 220, 45);

                logoutBtn.addActionListener(e -> {
                        setActiveButton(logoutBtn);
                        int choice = JOptionPane.showConfirmDialog(
                                        this,
                                        "Logout from system?",
                                        "Confirm",
                                        JOptionPane.YES_NO_OPTION);

                        if (choice == JOptionPane.YES_OPTION) {
                                new LoginUI();
                                dispose();
                        }
                });

                setActiveButton(dashboardBtn);
                doctorsBtn.addActionListener(e ->{
                        setActiveButton(doctorsBtn);
                                contentPanel.removeAll();
                                contentPanel.add(new DoctorsPanel_admin());
                                contentPanel.revalidate();
                                contentPanel.repaint();
                        // new DoctorsPanel_admin();
                });
                nursesBtn.addActionListener(e ->{
                        setActiveButton(nursesBtn);
                        contentPanel.removeAll();
                        // contentPanel.add(new NursesPanel_admin());
                        contentPanel.revalidate();
                        contentPanel.repaint();
                });
                patientsBtn.addActionListener(e ->{
                        setActiveButton(patientsBtn);
                        contentPanel.removeAll();
                        contentPanel.add(new PatientsPanel());
                        contentPanel.revalidate();
                        contentPanel.repaint();
                });
                medicinesBtn.addActionListener(e ->{
                        setActiveButton(medicinesBtn);
                        contentPanel.removeAll();
                        contentPanel.add(new MedicinesPanel());
                        contentPanel.revalidate();
                        contentPanel.repaint();
                });
                reportsBtn.addActionListener(e ->{
                        setActiveButton(reportsBtn);
                });
                approveBtn.addActionListener(e ->{
                        setActiveButton(approveBtn);
                        contentPanel.removeAll();
                        contentPanel.add(new ApprovalsPanel());
                        contentPanel.revalidate();
                        contentPanel.repaint();
                });

                sideBar.add(logo);
                sideBar.add(dashboardBtn);
                sideBar.add(doctorsBtn);
                sideBar.add(nursesBtn);
                sideBar.add(patientsBtn);
                sideBar.add(medicinesBtn);
                sideBar.add(reportsBtn);
                sideBar.add(settingsBtn);
                sideBar.add(approveBtn);
                sideBar.add(logoutBtn);

                add(sideBar, BorderLayout.WEST);
        }

        private void createMainArea() {

                JPanel mainPanel = new JPanel();
                mainPanel.setBackground(bgColor);
                mainPanel.setLayout(null);

                JPanel topBar = new JPanel();
                topBar.setBounds(0, 0, 1180, 80);
                topBar.setBackground(Color.WHITE);
                topBar.setLayout(null);

                JLabel title = new JLabel("Admin Dashboard");
                title.setBounds(30, 25, 250, 30);
                title.setFont(new Font("Segoe UI", Font.BOLD, 20));

                JLabel adminLabel = new JLabel("Administrator");
                adminLabel.setBounds(920, 25, 180, 30);
                adminLabel.setForeground(primary);
                adminLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

                topBar.add(title);
                topBar.add(adminLabel);

                mainPanel.add(topBar);

                createStatisticCards(mainPanel);

                contentPanel = new JPanel();
                contentPanel.setBounds(30, 270, 1080, 430);
                contentPanel.setBackground(bgColor);
                contentPanel.setLayout(new GridLayout(1, 1));

                contentPanel.add(new DashboardHomePanel());

                mainPanel.add(contentPanel);

                add(mainPanel, BorderLayout.CENTER);
        }

        private void createStatisticCards(JPanel panel) {

                JPanel doctorsCard = createCard("Doctors");
                doctorsCard.setBounds(30, 110, 170, 120);

                doctorsCount = createValueLabel();
                doctorsCard.add(doctorsCount);

                JPanel nursesCard = createCard("Nurses");
                nursesCard.setBounds(220, 110, 170, 120);

                nursesCount = createValueLabel();
                nursesCard.add(nursesCount);

                JPanel patientsCard = createCard("Patients");
                patientsCard.setBounds(410, 110, 170, 120);

                patientsCount = createValueLabel();
                patientsCard.add(patientsCount);

                JPanel medicinesCard = createCard("Medicines");
                medicinesCard.setBounds(600, 110, 170, 120);

                medicinesCount = createValueLabel();
                medicinesCard.add(medicinesCount);

                JPanel prescriptionsCard = createCard("Prescriptions");
                prescriptionsCard.setBounds(790, 110, 170, 120);

                prescriptionsCount = createValueLabel();
                prescriptionsCard.add(prescriptionsCount);

                panel.add(doctorsCard);
                panel.add(nursesCard);
                panel.add(patientsCard);
                panel.add(medicinesCard);
                panel.add(prescriptionsCard);
        }

        private JPanel createCard(String title) {

                ShadowPanel card = new ShadowPanel();
                card.setShadowColor(new Color(0, 0, 0, 5));
                card.setLayout(null);
                card.setBackground(Color.WHITE);

                JLabel titleLabel = new JLabel(title);
                titleLabel.setBounds(15, 10, 140, 25);
                titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
                titleLabel.setForeground(primary);

                card.add(titleLabel);

                return card;
        }

        private JLabel createValueLabel() {

                JLabel label = new JLabel("0");
                label.setBounds(20, 45, 120, 40);
                label.setFont(new Font("Segoe UI", Font.BOLD, 30));

                return label;
        }

        private JButton createSidebarButton(String text) {

                JButton btn = new JButton(text);

                btn.setFocusPainted(false);
                btn.setBorderPainted(false);

                btn.setBackground(primary);
                btn.setForeground(Color.WHITE);

                btn.setFont(
                                new Font(
                                                "Segoe UI",
                                                Font.BOLD,
                                                15));

                return btn;
        }

        private void loadStatistics() {

                try {

                        Connection con = DBconnection.getConnection();

                        doctorsCount.setText(
                                        getCount(con,
                                                        "SELECT COUNT(*) FROM doctors"));

                        nursesCount.setText(
                                        getCount(con,
                                                        "SELECT COUNT(*) FROM nurses"));

                        patientsCount.setText(
                                        getCount(con,
                                                        "SELECT COUNT(*) FROM patients"));

                        medicinesCount.setText(
                                        getCount(con,
                                                        "SELECT COUNT(*) FROM medicines"));

                        prescriptionsCount.setText(
                                        getCount(con,
                                                        "SELECT COUNT(*) FROM prescriptions"));

                        con.close();

                } catch (Exception ex) {
                        ex.printStackTrace();
                }
        }

        private String getCount(
                        Connection con,
                        String query)
                        throws SQLException {

                PreparedStatement pst = con.prepareStatement(query);

                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                        return String.valueOf(rs.getInt(1));
                }

                return "0";
        }

        private void setActiveButton(JButton activeBtn) {

                JButton[] buttons = { dashboardBtn, doctorsBtn, nursesBtn, patientsBtn, medicinesBtn, reportsBtn, settingsBtn, logoutBtn, approveBtn };

                for (JButton btn : buttons) {

                        // NORMAL BUTTON
                        btn.setBackground(Color.decode("#008B86"));
                        btn.setForeground(Color.WHITE);
                }

                // ACTIVE BUTTON
                activeBtn.setBackground(Color.WHITE);
                activeBtn.setForeground(Color.decode("#00A19B"));
        }
}