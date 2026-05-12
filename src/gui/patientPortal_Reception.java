package src.gui;

import src.db.DBconnection;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class patientPortal_Reception extends JFrame {

    private JTable patientTable;
    private DefaultTableModel model;

    private JTextField searchField;

    private Color bgColor = Color.decode("#F2EFE7");
    private Color primary = Color.decode("#00A19B");

    private int receptionistId;
    private String receptionistNameText;

    public patientPortal_Reception(int receptionistId, String receptionistNameText) {

        // =========================
        // FRAME
        // =========================
        setTitle("Receptionist Portal");
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // =========================
        // SIDEBAR
        // =========================
        JPanel sideBar = new JPanel();
        sideBar.setPreferredSize(new Dimension(220, 0));
        sideBar.setBackground(primary);
        sideBar.setLayout(null);

        JLabel logo = new JLabel("MediSync HMS");
        logo.setBounds(20, 40, 200, 40);
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JButton appointmentsBtn = createSidebarButton("Appointments");

        appointmentsBtn.setBounds(0, 140, 220, 45);

        appointmentsBtn.addActionListener(e -> {

            new ReceptionistDashboard(this.receptionistId, this.receptionistNameText);
            dispose();
        });

        JButton patientsBtn = createSidebarButton("Patients");

        patientsBtn.setBounds(0, 200, 220, 45);

        JButton logoutBtn = createSidebarButton("Logout");

        logoutBtn.setBounds(0, 580, 220, 45);

        logoutBtn.addActionListener(e -> {

            logoutBtn.setBackground(Color.WHITE);
            logoutBtn.setForeground(primary);
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to logout?",
                    "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginUI();
                dispose();
            }
        });
        // ACTIVE BUTTON
        patientsBtn.setBackground(Color.WHITE);
        patientsBtn.setForeground(primary);

        sideBar.add(logo);
        sideBar.add(appointmentsBtn);
        sideBar.add(patientsBtn);
        sideBar.add(logoutBtn);

        // =========================
        // MAIN PANEL
        // =========================
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(bgColor);
        mainPanel.setLayout(null);

        // =========================
        // TOP BAR
        // =========================
        this.receptionistId = receptionistId;
        this.receptionistNameText = receptionistNameText;

        JPanel topBar = new JPanel();
        topBar.setBounds(0, 0, 1180, 80);
        topBar.setBackground(Color.WHITE);
        topBar.setLayout(null);

        JLabel dashboardTitle = new JLabel("Receptionist Dashboard");
        dashboardTitle.setBounds(30, 25, 250, 30);
        dashboardTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        dashboardTitle.setForeground(new Color(40, 40, 40));

        JLabel receptionistName = new JLabel(
                "<html>" + this.receptionistNameText + "<br>Receptionist ID: " + this.receptionistId + "</html>");
        receptionistName.setBounds(780, 15, 250, 40);
        receptionistName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        receptionistName.setForeground(primary);

        ImageIcon icon = new ImageIcon(
                new ImageIcon("images/profile_image.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        receptionistName.setIcon(icon);

        // ✅ Move icon to the right side
        receptionistName.setHorizontalTextPosition(SwingConstants.LEFT);
        receptionistName.setHorizontalAlignment(SwingConstants.RIGHT);
        topBar.add(receptionistName);
        topBar.add(dashboardTitle);

        // =========================
        // TITLE
        // =========================
        JLabel title = new JLabel("Patient Directory");

        title.setBounds(40, 110, 400, 40);

        title.setFont(
                new Font("Segoe UI", Font.BOLD, 28));

        title.setForeground(primary);

        JLabel subtitle = new JLabel("Search and manage electronic health records");

        subtitle.setBounds(40, 145, 400, 25);

        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        subtitle.setForeground(new Color(40, 40, 40));

        // =========================
        // SEARCH FIELD
        // =========================
        searchField = new RoundedTextField(20);

        searchField.setBounds(40, 200, 700, 40);

        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        searchField.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        ((RoundedTextField) searchField).setHint("Search by patient name...");

        // =========================
        // SEARCH DOCTOR BUTTON
        // =========================
        RoundedButton searchDoctorBtn = new RoundedButton("Search Available Doctors");
        searchDoctorBtn.setBounds(790, 200, 220, 40);
        searchDoctorBtn.setBackground(primary);
        searchDoctorBtn.setForeground(Color.WHITE);

        searchDoctorBtn.addActionListener(e -> {

            openDoctorAvailabilityDialog();
        });

        // =========================
        // TABLE
        // =========================
        String[] columns = {

                "ID",

                "Full Name",

                "Age",

                "Gender",

                "Disease",

                "Doctor",

                "Completed At"
        };

        model = new DefaultTableModel(columns, 0);

        patientTable = new JTable(model);

        patientTable.setRowHeight(35);

        patientTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        patientTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        patientTable.getTableHeader().setPreferredSize(new Dimension(100, 50));

        patientTable.getTableHeader().setBackground(primary);

        patientTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(patientTable);

        scrollPane.setBounds(40, 270, 990, 350);

        patientTable.getColumnModel().getColumn(0).setPreferredWidth(40); // ID

        patientTable.getColumnModel().getColumn(1).setPreferredWidth(220); // Full Name

        patientTable.getColumnModel().getColumn(2).setPreferredWidth(70); // Age

        patientTable.getColumnModel().getColumn(3).setPreferredWidth(70); // Gender

        patientTable.getColumnModel().getColumn(4).setPreferredWidth(190); // Disease

        patientTable.getColumnModel().getColumn(5).setPreferredWidth(200); // Doctor

        patientTable.getColumnModel().getColumn(6).setPreferredWidth(180); // Completed At

        patientTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();

        center.setHorizontalAlignment(JLabel.CENTER);

        patientTable.getColumnModel().getColumn(0).setCellRenderer(center);

        patientTable.getColumnModel().getColumn(2).setCellRenderer(center);

        patientTable.getColumnModel().getColumn(3).setCellRenderer(center);

        patientTable.getColumnModel().getColumn(6).setCellRenderer(center);

        // =========================
        // ADD COMPONENTS
        // =========================
        mainPanel.add(topBar);

        mainPanel.add(title);
        mainPanel.add(subtitle);

        mainPanel.add(searchField);

        mainPanel.add(searchDoctorBtn);

        mainPanel.add(scrollPane);

        add(sideBar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        // =========================
        // LOAD DATA
        // =========================
        loadPatients();

        // =========================
        // SEARCH LOGIC
        // =========================
        searchField.addKeyListener(
                new java.awt.event.KeyAdapter() {

                    public void keyReleased(
                            java.awt.event.KeyEvent e) {

                        searchPatients(
                                searchField.getText());
                    }
                });

        setVisible(true);
    }

    // ====================================
    // LOAD PATIENTS
    // ====================================
    private void loadPatients() {

        try {

            Connection con = DBconnection.getConnection();

            String query = "SELECT old_patients.*, doctors.full_name AS doctor_name " +
                    "FROM old_patients " +
                    "LEFT JOIN doctors " +
                    "ON old_patients.doctor_id = doctors.doctor_id " +
                    "ORDER BY completed_at DESC";

            PreparedStatement pst = con.prepareStatement(query);

            ResultSet rs = pst.executeQuery();

            model.setRowCount(0);

            while (rs.next()) {

                model.addRow(new Object[] {

                        rs.getInt("patient_id"),

                        rs.getString("full_name"),

                        rs.getInt("age"),

                        rs.getString("gender"),

                        rs.getString("disease"),

                        rs.getString("doctor_name"),

                        rs.getTimestamp("completed_at")
                });
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    // ====================================
    // SEARCH PATIENTS
    // ====================================
    private void searchPatients(String keyword) {

        try {

            Connection con = DBconnection.getConnection();

            String query = "SELECT old_patients.*, doctors.full_name AS doctor_name " +
                    "FROM old_patients " +
                    "LEFT JOIN doctors " +
                    "ON old_patients.doctor_id = doctors.doctor_id " +
                    "WHERE old_patients.full_name LIKE ?";

            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(
                    1,
                    "%" + keyword + "%");

            ResultSet rs = pst.executeQuery();

            model.setRowCount(0);

            while (rs.next()) {

                model.addRow(new Object[] {

                        rs.getInt("patient_id"),

                        rs.getString("full_name"),

                        rs.getInt("age"),

                        rs.getString("gender"),

                        rs.getString("disease"),

                        rs.getString("doctor_name"),

                        rs.getTimestamp("completed_at")
                });
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    // check the doctor availabilty at the current time
    private void openDoctorAvailabilityDialog() {

        JDialog dialog = new JDialog(this,
                "Available Doctors",
                true);

        dialog.setSize(700, 500);

        dialog.setLocationRelativeTo(this);

        dialog.setLayout(null);

        dialog.setBackground(bgColor);

        // SEARCH FIELD
        RoundedTextField searchDoctorField = new RoundedTextField(20);

        searchDoctorField.setBounds(
                30,
                20,
                620,
                40);

        searchDoctorField.setFont(
                new Font("Segoe UI",
                        Font.PLAIN,
                        15));

        // TABLE
        String[] columns = {

                "ID",
                "Doctor Name",
                "Specialization",
                "Available From",
                "Available To",
                "Fee"
        };

        DefaultTableModel doctorModel = new DefaultTableModel(columns, 0);

        JTable doctorTable = new JTable(doctorModel);

        doctorTable.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 14));

        doctorTable.getTableHeader().setPreferredSize(new Dimension(100, 50));

        doctorTable.getTableHeader().setBackground(primary);

        doctorTable.getTableHeader().setForeground(Color.WHITE);

        doctorTable.setRowHeight(35);
        doctorTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(doctorTable);

        doctorTable.getColumnModel().getColumn(0).setPreferredWidth(50);

        doctorTable.getColumnModel().getColumn(1).setPreferredWidth(166);

        doctorTable.getColumnModel().getColumn(2).setPreferredWidth(120);

        doctorTable.getColumnModel().getColumn(3).setPreferredWidth(120);

        doctorTable.getColumnModel().getColumn(4).setPreferredWidth(100);

        doctorTable.getColumnModel().getColumn(5).setPreferredWidth(60);

        doctorTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();

        center.setHorizontalAlignment(JLabel.CENTER);

        doctorTable.getColumnModel().getColumn(0).setCellRenderer(center);

        doctorTable.getColumnModel().getColumn(3).setCellRenderer(center);

        doctorTable.getColumnModel().getColumn(4).setCellRenderer(center);

        doctorTable.getColumnModel().getColumn(5).setCellRenderer(center);

        scrollPane.setBounds(
                30,
                80,
                620,
                330);

        // LOAD DOCTORS
        try {

            Connection con = DBconnection.getConnection();

            String query = "SELECT * FROM doctors";

            PreparedStatement pst = con.prepareStatement(query);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                doctorModel.addRow(new Object[] {

                        rs.getInt("doctor_id"),

                        rs.getString("full_name"),

                        rs.getString("specialization"),

                        rs.getString("available_from"),

                        rs.getString("available_to"),

                        rs.getString("consultation_fee")
                });
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        // SEARCH FILTER
        searchDoctorField.addKeyListener(
                new java.awt.event.KeyAdapter() {

                    public void keyReleased(
                            java.awt.event.KeyEvent e) {

                        String keyword = searchDoctorField.getText();

                        doctorModel.setRowCount(0);

                        try {

                            Connection con = DBconnection.getConnection();

                            String query = "SELECT * FROM doctors " +
                                    "WHERE full_name LIKE ? " +
                                    "OR specialization LIKE ?";

                            PreparedStatement pst = con.prepareStatement(query);

                            pst.setString(
                                    1,
                                    "%" + keyword + "%");

                            pst.setString(
                                    2,
                                    "%" + keyword + "%");

                            ResultSet rs = pst.executeQuery();

                            while (rs.next()) {

                                doctorModel.addRow(new Object[] {

                                        rs.getInt("doctor_id"),

                                        rs.getString("full_name"),

                                        rs.getString("specialization"),

                                        rs.getString("available_from"),

                                        rs.getString("available_to"),

                                        rs.getString("consultation_fee")
                                });
                            }

                        } catch (Exception ex) {

                            ex.printStackTrace();
                        }
                    }
                });

        dialog.add(searchDoctorField);

        dialog.add(scrollPane);

        dialog.setVisible(true);
    }

    // ====================================
    // SIDEBAR BUTTON
    // ====================================
    private JButton createSidebarButton(String text) {

        JButton btn = new JButton(text);

        btn.setFocusPainted(false);

        btn.setBorderPainted(false);

        btn.setBackground(primary);

        btn.setForeground(Color.WHITE);

        btn.setFont(
                new Font("Segoe UI", Font.BOLD, 15));

        return btn;
    }
}
