package src.gui;

import src.db.DBconnection;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AppointmentPortal_Doctor extends JFrame {

        private JButton dashboardBtn;
        private JButton patientBtn;
        private JButton appointmentBtn;
        private JButton logoutBtn;
        private JTable appointmentTable;
        private DefaultTableModel model;

        private JTextField searchField;

        private Color bgColor = Color.decode("#F2EFE7");
        private Color primary = Color.decode("#00A19B");

        private int doctorId;
        private String doctorName;
        private String specialization;

        public AppointmentPortal_Doctor(int doctorId, String doctorName, String specialization) {

                // =========================
                // FRAME
                // =========================
                setTitle("Doctor Portal");
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

                dashboardBtn = createSidebarButton("Dashboard");
                dashboardBtn.setBounds(0, 130, 220, 45);

                patientBtn = createSidebarButton("Patients");
                patientBtn.setBounds(0, 190, 220, 45);

                appointmentBtn = createSidebarButton("Appointments");
                appointmentBtn.setBounds(0, 250, 220, 45);

                logoutBtn = createSidebarButton("Logout");
                logoutBtn.setBounds(0, 585, 220, 45);

                sideBar.add(logo);
                sideBar.add(dashboardBtn);
                sideBar.add(patientBtn);
                sideBar.add(appointmentBtn);
                sideBar.add(logoutBtn);

                // default active button
                setActiveButton(appointmentBtn);
                // set another button logic
                dashboardBtn.addActionListener(e -> {
                        setActiveButton(dashboardBtn);
                        new DoctorDashboard(doctorId, doctorName, specialization);
                        dispose();
                });
                logoutBtn.addActionListener(e -> {
                        setActiveButton(logoutBtn);
                        int confirm = JOptionPane.showConfirmDialog(
                                        null,
                                        "Are you sure you want to logout?",
                                        "Logout Confirmation",
                                        JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                                new LoginUI();
                                dispose();
                        } else {
                                setActiveButton(appointmentBtn);
                        }
                });
                patientBtn.addActionListener(e -> {
                        setActiveButton(patientBtn);
                        new patientPortal_Doctor(doctorId, doctorName, specialization);
                        dispose();
                });
                // =========================
                // =========================
                // MAIN PANEL
                // =========================
                JPanel mainPanel = new JPanel();
                mainPanel.setBackground(bgColor);
                mainPanel.setLayout(null);

                // =========================
                // TOP BAR
                // =========================
                this.doctorId = doctorId;
                this.doctorName = doctorName;
                this.specialization = specialization;

                JPanel topBar = new JPanel();
                topBar.setBounds(0, 0, 1180, 80);
                topBar.setBackground(Color.WHITE);
                topBar.setLayout(null);

                JLabel dashboardTitle = new JLabel("Doctor Dashboard");
                dashboardTitle.setBounds(30, 25, 250, 30);
                dashboardTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
                dashboardTitle.setForeground(new Color(40, 40, 40));

                JLabel doctorInfo = new JLabel(
                                "<html>" + this.doctorName + "<br>" + this.specialization + "</html>");
                doctorInfo.setBounds(780, 15, 250, 50);
                doctorInfo.setFont(new Font("Segoe UI", Font.BOLD, 16));
                doctorInfo.setForeground(primary);

                ImageIcon icon = new ImageIcon(
                                new ImageIcon("images/profile_image.png").getImage().getScaledInstance(50, 50,
                                                Image.SCALE_SMOOTH));
                doctorInfo.setIcon(icon);

                // ✅ Move icon to the right side
                doctorInfo.setHorizontalTextPosition(SwingConstants.LEFT);
                doctorInfo.setHorizontalAlignment(SwingConstants.RIGHT);
                topBar.add(doctorInfo);
                topBar.add(dashboardTitle);

                // =========================
                // TITLE
                // =========================
                JLabel title = new JLabel("Appointment Records");

                title.setBounds(40, 110, 400, 40);

                title.setFont(
                                new Font("Segoe UI", Font.BOLD, 28));

                title.setForeground(primary);

                JLabel subtitle = new JLabel("Search and manage appointment records");

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
                RoundedButton searchDoctorBtn = new RoundedButton("Delete Appointment");
                searchDoctorBtn.setBounds(790, 200, 220, 40);
                searchDoctorBtn.setBackground(primary);
                searchDoctorBtn.setForeground(Color.WHITE);

                searchDoctorBtn.addActionListener(e -> {

                        DeleteRecord();
                });

                // =========================
                // TABLE
                // =========================
                String[] columns = {

                                "ID",
                                "Patient Name",
                                "Age",
                                "Gender",
                                "Disease",
                                "Doctor",
                                "<html>Appointment<br>Date</html>",
                                "<html>Appointment<br>Time</html>",
                                "<html>Nurse<br>Status</html>"
                };

                model = new DefaultTableModel(columns, 0);

                appointmentTable = new JTable(model);

                appointmentTable.setRowHeight(40);

                appointmentTable.setFont(
                                new Font("Segoe UI", Font.PLAIN, 14));

                appointmentTable.getTableHeader().setFont(
                                new Font("Segoe UI", Font.BOLD, 14));

                appointmentTable.getTableHeader().setPreferredSize(new Dimension(100, 50));

                appointmentTable.getTableHeader().setBackground(primary);

                appointmentTable.getTableHeader().setForeground(Color.WHITE);

                JScrollPane scrollPane = new JScrollPane(appointmentTable);

                appointmentTable.getColumnModel().getColumn(0).setPreferredWidth(40); // ID

                appointmentTable.getColumnModel().getColumn(1).setPreferredWidth(207); // Patient Name

                appointmentTable.getColumnModel().getColumn(2).setPreferredWidth(50); // Age

                appointmentTable.getColumnModel().getColumn(3).setPreferredWidth(60); // Gender

                appointmentTable.getColumnModel().getColumn(4).setPreferredWidth(150); // Disease

                appointmentTable.getColumnModel().getColumn(5).setPreferredWidth(200); // Doctor

                appointmentTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Date

                appointmentTable.getColumnModel().getColumn(7).setPreferredWidth(100); // Time

                appointmentTable.getColumnModel().getColumn(8).setPreferredWidth(80); // Status

                appointmentTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                DefaultTableCellRenderer center = new DefaultTableCellRenderer();

                center.setHorizontalAlignment(JLabel.CENTER);

                appointmentTable.getColumnModel().getColumn(0).setCellRenderer(center);

                appointmentTable.getColumnModel().getColumn(2).setCellRenderer(center);

                appointmentTable.getColumnModel().getColumn(3).setCellRenderer(center);

                appointmentTable.getColumnModel().getColumn(6).setCellRenderer(center);

                appointmentTable.getColumnModel().getColumn(7).setCellRenderer(center);

                appointmentTable.getColumnModel().getColumn(8).setCellRenderer(center);

                scrollPane.setBounds(40, 260, 990, 350);

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

                        String query = "SELECT patients.*, " +
                                        "doctors.full_name AS doctor_name " +
                                        "FROM patients " +
                                        "LEFT JOIN doctors " +
                                        "ON patients.doctor_id = doctors.doctor_id " +
                                        "WHERE patients.doctor_id = ? " +
                                        "ORDER BY patients.appointment_date ASC, " +
                                        "patients.appointment_time ASC";

                        PreparedStatement pst = con.prepareStatement(query);

                        pst.setInt(1, doctorId);

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

                                                rs.getDate("appointment_date"),

                                                rs.getTime("appointment_time"),

                                                rs.getString("nurse_status")
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

                        String query = "SELECT patients.*, " +
                                        "doctors.full_name AS doctor_name " +
                                        "FROM patients " +
                                        "LEFT JOIN doctors " +
                                        "ON patients.doctor_id = doctors.doctor_id " +
                                        "WHERE patients.full_name LIKE ? " +
                                        "AND patients.doctor_id = ? " +
                                        "ORDER BY patients.appointment_date ASC, " +
                                        "patients.appointment_time ASC";

                        PreparedStatement pst = con.prepareStatement(query);

                        pst.setString(
                                        1,
                                        "%" + keyword + "%");

                        pst.setInt(
                                        2,
                                        doctorId);

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

                                                rs.getDate("appointment_date"),

                                                rs.getTime("appointment_time"),

                                                rs.getString("nurse_status")
                                });
                        }

                } catch (Exception ex) {

                        ex.printStackTrace();
                }
        }

        // ====================================
        // DELETE RECORD FUNCTION
        // ====================================
        private void DeleteRecord() {

                // CHECK SELECTED ROW
                int row = appointmentTable.getSelectedRow();

                if (row == -1) {

                        JOptionPane.showMessageDialog(
                                        null,
                                        "Please select a patient record first!");

                        return;
                }

                // GET PATIENT ID
                int patientId = Integer.parseInt(
                                model.getValueAt(row, 0).toString());

                // CONFIRM DELETE
                int confirm = JOptionPane.showConfirmDialog(
                                null,
                                "Do you really want to remove this appointment?",
                                "Delete Confirmation",
                                JOptionPane.YES_NO_OPTION);

                // IF USER PRESSES YES
                if (confirm == JOptionPane.YES_OPTION) {

                        try {

                                Connection con = DBconnection.getConnection();

                                String query = "DELETE FROM patients " +
                                                "WHERE patient_id = ?";

                                PreparedStatement pst = con.prepareStatement(query);

                                pst.setInt(1, patientId);

                                int deleted = pst.executeUpdate();

                                if (deleted > 0) {

                                        JOptionPane.showMessageDialog(
                                                        null,
                                                        "Appointment removed successfully!");

                                        // RELOAD TABLE
                                        loadPatients();

                                } else {

                                        JOptionPane.showMessageDialog(
                                                        null,
                                                        "Record not found!");
                                }

                                pst.close();
                                con.close();

                        } catch (Exception ex) {

                                ex.printStackTrace();
                        }
                }
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

        private void setActiveButton(JButton activeBtn) {

                JButton[] buttons = { dashboardBtn, patientBtn, appointmentBtn, logoutBtn };

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
