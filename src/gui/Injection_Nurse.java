package src.gui;

import src.db.DBconnection;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Injection_Nurse extends JFrame {

        private JButton pendingBtn, injectionBtn, logoutBtn;
        private JTable injectionTable;

        private RoundedTextField searchField;
        private DefaultTableModel injectionModel;

        private Color bgColor = Color.decode("#F2EFE7");
        private Color primary = Color.decode("#00A19B");

        private int nurseId;
        private String nurseName;

        public Injection_Nurse(int nurseId, String nurseName) {

                this.nurseId = nurseId;
                this.nurseName = nurseName;

                // FRAME
                setTitle("Nurse Dashboard");

                setSize(1400, 800);

                setLocationRelativeTo(null);

                setDefaultCloseOperation(EXIT_ON_CLOSE);

                setLayout(new BorderLayout());

                // =========================
                // SIDEBAR
                // =========================
                JPanel sideBar = new JPanel();

                sideBar.setPreferredSize(
                                new Dimension(220, 0));

                sideBar.setBackground(primary);

                sideBar.setLayout(null);

                JLabel logo = new JLabel("MediSync HMS");

                logo.setBounds(20, 40, 200, 40);

                logo.setForeground(Color.WHITE);

                logo.setFont(
                                new Font("Segoe UI",
                                                Font.BOLD,
                                                24));

                this.pendingBtn = createSidebarButton(
                                "Pending Patients");

                this.pendingBtn.setBounds(
                                0,
                                140,
                                220,
                                45);

                this.injectionBtn = createSidebarButton(
                                "Injection Tasks");

                this.injectionBtn.setBounds(
                                0,
                                200,
                                220,
                                45);

                this.logoutBtn = createSidebarButton(
                                "Logout");

                this.logoutBtn.setBounds(
                                0,
                                580,
                                220,
                                45);

                sideBar.add(logo);

                sideBar.add(pendingBtn);

                sideBar.add(injectionBtn);

                sideBar.add(logoutBtn);

                setActiveButton(injectionBtn);

                this.pendingBtn.addActionListener(e -> {
                        setActiveButton(pendingBtn);
                        new NurseDashboard(nurseId, nurseName);
                        dispose();
                });

                // =========================
                // MAIN PANEL
                // =========================
                JPanel mainPanel = new JPanel();

                mainPanel.setBackground(bgColor);

                mainPanel.setLayout(null);

                // TOP BAR
                JPanel topBar = new JPanel();

                topBar.setBounds(0, 0, 1180, 80);

                topBar.setBackground(Color.WHITE);

                topBar.setLayout(null);

                JLabel dashboardTitle = new JLabel("Nurse Dashboard");
                dashboardTitle.setBounds(30, 25, 250, 30);
                dashboardTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
                dashboardTitle.setForeground(new Color(40, 40, 40));

                JLabel nurseLabel = new JLabel("<html>" + nurseName + "<br>Nurse ID: " + nurseId + "</html>");

                ImageIcon icon = new ImageIcon(new ImageIcon("images/profile_image.png").getImage()
                                .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                nurseLabel.setIcon(icon);

                // ✅ Move icon to the right side
                nurseLabel.setHorizontalTextPosition(SwingConstants.LEFT);
                nurseLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                topBar.add(nurseLabel);
                topBar.add(dashboardTitle);

                nurseLabel.setBounds(
                                780,
                                15,
                                250,
                                40);

                nurseLabel.setFont(
                                new Font("Segoe UI",
                                                Font.BOLD,
                                                16));

                nurseLabel.setForeground(primary);

                topBar.add(nurseLabel);

                JLabel subtitle = new JLabel("Manage your injection tasks for the day.");
                subtitle.setBounds(40, 150, 400, 25);
                subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                subtitle.setForeground(new Color(40, 40, 40));

                JLabel title = new JLabel("Injection Tasks");

                title.setBounds(
                                40,
                                110,
                                400,
                                40);

                title.setFont(
                                new Font("Segoe UI",
                                                Font.BOLD,
                                                28));

                title.setForeground(primary);

                // search field
                searchField = new RoundedTextField(20);
                searchField.setBounds(40, 200, 700, 40);
                searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                ((RoundedTextField) searchField).setHint("Search by patient name...");

                // =========================
                // INJECTION TABLE
                // =========================

                String[] injectionColumns = {

                                "<html>Prescription<b>ID</html>",
                                "Patient",
                                "Medicine",
                                "Dosage",
                                "Frequency"
                };

                injectionModel = new DefaultTableModel(
                                injectionColumns,
                                0);

                injectionTable = new JTable(injectionModel);

                injectionTable.setRowHeight(40);

                injectionTable.setFont(
                                new Font("Segoe UI", Font.PLAIN, 14));

                injectionTable.getTableHeader().setFont(
                                new Font("Segoe UI", Font.BOLD, 14));

                injectionTable.getTableHeader().setPreferredSize(new Dimension(100, 50));

                injectionTable.getTableHeader().setBackground(primary);

                injectionTable.getTableHeader().setForeground(Color.WHITE);

                JScrollPane injectionScroll = new JScrollPane(injectionTable);

                injectionTable.getColumnModel().getColumn(0).setPreferredWidth(100); // ID

                injectionTable.getColumnModel().getColumn(1).setPreferredWidth(270); // Patient Name

                injectionTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Medicine

                injectionTable.getColumnModel().getColumn(3).setPreferredWidth(150);

                injectionTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Dosage

                DefaultTableCellRenderer center = new DefaultTableCellRenderer();

                center.setHorizontalAlignment(JLabel.CENTER);

                injectionTable.getColumnModel().getColumn(0).setCellRenderer(center);

                injectionTable.getColumnModel().getColumn(3).setCellRenderer(center);

                injectionTable.getColumnModel().getColumn(4).setCellRenderer(center);

                injectionScroll.setBounds(
                                40,
                                260,
                                990,
                                350);

                RoundedButton completeInjectionBtn = new RoundedButton("Mark Injection Complete");

                completeInjectionBtn.setBounds(
                                800,
                                200,
                                200,
                                40);

                completeInjectionBtn.setBackground(primary);

                completeInjectionBtn.setForeground(Color.WHITE);

                // ADD COMPONENTS
                mainPanel.add(topBar);

                mainPanel.add(title);

                mainPanel.add(injectionScroll);

                mainPanel.add(completeInjectionBtn);

                mainPanel.add(subtitle);

                mainPanel.add(searchField);

                add(sideBar, BorderLayout.WEST);

                add(mainPanel, BorderLayout.CENTER);

                // SEARCH
                searchField.addKeyListener(
                                new java.awt.event.KeyAdapter() {

                                        public void keyReleased(
                                                        java.awt.event.KeyEvent e) {

                                                searchInjectionPatients(
                                                                searchField.getText());
                                        }
                                });

                // LOAD DATA
                loadInjectionTasks();

                // =========================
                // COMPLETE INJECTION
                // =========================
                completeInjectionBtn.addActionListener(e -> {

                        int row = injectionTable.getSelectedRow();

                        if (row == -1) {

                                JOptionPane.showMessageDialog(
                                                null,
                                                "Select injection task!");

                                return;
                        }

                        int prescriptionId = Integer.parseInt(
                                        injectionModel.getValueAt(
                                                        row,
                                                        0).toString());

                        try {

                                Connection con = DBconnection.getConnection();

                                // =========================================
                                // COMPLETE NURSE TASK
                                // =========================================

                                String updateQuery = "UPDATE prescriptions SET " +
                                                "nurse_status = 'Completed' " +
                                                "WHERE prescription_id = ?";

                                PreparedStatement updatePst = con.prepareStatement(updateQuery);

                                updatePst.setInt(
                                                1,
                                                prescriptionId);

                                updatePst.executeUpdate();

                                // =========================================
                                // CHECK BOTH STATUS
                                // =========================================

                                String checkQuery = "SELECT * FROM prescriptions " +
                                                "WHERE prescription_id = ?";

                                PreparedStatement checkPst = con.prepareStatement(checkQuery);

                                checkPst.setInt(
                                                1,
                                                prescriptionId);

                                ResultSet rs = checkPst.executeQuery();

                                if (rs.next()) {

                                        String pharmacyStatus = rs.getString("status");

                                        String nurseStatus = rs.getString("nurse_status");

                                        // =====================================
                                        // MOVE TO OLD TABLE
                                        // =====================================

                                        if (pharmacyStatus.equalsIgnoreCase("Completed")
                                                        &&
                                                        nurseStatus.equalsIgnoreCase("Completed")) {

                                                String insertOldQuery = "INSERT INTO old_prescriptions " +

                                                                "(prescription_id, patient_id, medicine_name, dosage, frequency, prescribed_by, medicine_type, nurse_status, status) "
                                                                +

                                                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                                                PreparedStatement oldPst = con.prepareStatement(insertOldQuery);

                                                oldPst.setInt(
                                                                1,
                                                                rs.getInt("prescription_id"));

                                                oldPst.setInt(
                                                                2,
                                                                rs.getInt("patient_id"));

                                                oldPst.setString(
                                                                3,
                                                                rs.getString("medicine_name"));

                                                oldPst.setString(
                                                                4,
                                                                rs.getString("dosage"));

                                                oldPst.setString(
                                                                5,
                                                                rs.getString("frequency"));

                                                oldPst.setInt(
                                                                6,
                                                                rs.getInt("prescribed_by"));

                                                oldPst.setString(
                                                                7,
                                                                rs.getString("medicine_type"));

                                                oldPst.setString(
                                                                8,
                                                                rs.getString("nurse_status"));

                                                oldPst.setString(
                                                                9,
                                                                rs.getString("status"));

                                                oldPst.executeUpdate();

                                                // =====================================
                                                // DELETE FROM ACTIVE PRESCRIPTIONS
                                                // =====================================

                                                String deleteQuery = "DELETE FROM prescriptions " +
                                                                "WHERE prescription_id = ?";

                                                PreparedStatement deletePst = con.prepareStatement(deleteQuery);

                                                deletePst.setInt(
                                                                1,
                                                                prescriptionId);

                                                deletePst.executeUpdate();
                                        }
                                }

                                JOptionPane.showMessageDialog(
                                                null,
                                                "Injection Completed!");

                                loadInjectionTasks();

                        } catch (Exception ex) {

                                ex.printStackTrace();
                        }
                });

                // LOGOUT
                logoutBtn.addActionListener(e -> {
                        setActiveButton(logoutBtn);
                        int confirm = JOptionPane.showConfirmDialog(
                                        null,
                                        "Do you really want to logout?",
                                        "Logout",
                                        JOptionPane.YES_NO_OPTION);

                        if (confirm == JOptionPane.YES_OPTION) {

                                new LoginUI();

                                dispose();
                        } else {
                                setActiveButton(injectionBtn);
                        }
                });

                setVisible(true);
        }

        // ====================================
        // LOAD INJECTION TASKS
        // ====================================
        private void loadInjectionTasks() {

                try {

                        Connection con = DBconnection.getConnection();

                        String query = "SELECT prescriptions.*, old_patients.full_name " +
                                        "FROM prescriptions " +
                                        "JOIN old_patients " +
                                        "ON prescriptions.patient_id = old_patients.patient_id " +
                                        "WHERE prescriptions.medicine_type = 'Injection' " +
                                        "AND prescriptions.nurse_status = 'Pending'";

                        PreparedStatement pst = con.prepareStatement(query);

                        ResultSet rs = pst.executeQuery();

                        injectionModel.setRowCount(0);

                        while (rs.next()) {

                                injectionModel.addRow(new Object[] {

                                                rs.getInt("prescription_id"),

                                                rs.getString("full_name"),

                                                rs.getString("medicine_name"),

                                                rs.getString("dosage"),

                                                rs.getString("frequency")
                                });
                        }

                } catch (Exception ex) {

                        ex.printStackTrace();
                }
        }

        // ====================================
        // SIDEBAR BUTTON
        // ====================================
        private JButton createSidebarButton(
                        String text) {

                JButton btn = new JButton(text);

                btn.setFocusPainted(false);

                btn.setBorderPainted(false);

                btn.setBackground(primary);

                btn.setForeground(Color.WHITE);

                btn.setFont(
                                new Font("Segoe UI",
                                                Font.BOLD,
                                                15));

                return btn;
        }

        private void setActiveButton(JButton activeBtn) {

                JButton[] buttons = { pendingBtn, injectionBtn, logoutBtn, };

                for (JButton btn : buttons) {

                        // NORMAL BUTTON
                        btn.setBackground(Color.decode("#008B86"));
                        btn.setForeground(Color.WHITE);
                }

                // ACTIVE BUTTON
                activeBtn.setBackground(Color.WHITE);
                activeBtn.setForeground(Color.decode("#00A19B"));
        }

        private void searchInjectionPatients(String keyword) {

                try {

                        Connection con = DBconnection.getConnection();

                        String query = "SELECT prescriptions.*, " +
                                        "old_patients.full_name " +
                                        "FROM prescriptions " +
                                        "LEFT JOIN old_patients " +
                                        "ON prescriptions.patient_id = old_patients.patient_id " +
                                        "WHERE old_patients.full_name LIKE ? " +
                                        "AND prescriptions.medicine_type = 'Injection' " +
                                        "AND prescriptions.nurse_status = 'Pending'";

                        PreparedStatement pst = con.prepareStatement(query);

                        pst.setString(
                                        1,
                                        "%" + keyword + "%");

                        ResultSet rs = pst.executeQuery();

                        injectionModel.setRowCount(0);

                        while (rs.next()) {

                                injectionModel.addRow(new Object[] {

                                                rs.getInt("prescription_id"),

                                                rs.getString("full_name"),

                                                rs.getString("medicine_name"),

                                                rs.getString("dosage"),

                                                rs.getString("frequency")
                                });
                        }

                } catch (Exception ex) {

                        ex.printStackTrace();
                }
        }
}