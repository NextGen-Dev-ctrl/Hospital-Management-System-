package src.gui;

import src.db.DBconnection;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class NurseDashboard extends JFrame {

        private JButton pendingBtn, injectionBtn, logoutBtn;
        private JTable patientTable;
        private DefaultTableModel patientModel;
        private JTextField searchField;
        private Color bgColor = Color.decode("#F2EFE7");
        private Color primary = Color.decode("#00A19B");

        private int nurseId;
        private String nurseName;

        public NurseDashboard(int nurseId, String nurseName) {

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

                sideBar.setPreferredSize(new Dimension(220, 0));

                sideBar.setBackground(primary);

                sideBar.setLayout(null);

                JLabel logo = new JLabel("MediSync HMS");

                logo.setBounds(20, 40, 200, 40);

                logo.setForeground(Color.WHITE);

                logo.setFont(new Font("Segoe UI", Font.BOLD, 24));

                this.pendingBtn = createSidebarButton("Pending Patients");

                this.pendingBtn.setBounds(0, 140, 220, 45);

                this.injectionBtn = createSidebarButton("Injection Tasks");

                this.injectionBtn.setBounds(0, 200, 220, 45);

                this.logoutBtn = createSidebarButton("Logout");

                this.logoutBtn.setBounds(0, 580, 220, 45);

                sideBar.add(logo);

                sideBar.add(pendingBtn);

                sideBar.add(injectionBtn);

                sideBar.add(logoutBtn);

                // pending button active by default
                setActiveButton(pendingBtn);

                // injection button action
                injectionBtn.addActionListener(e -> {
                        new Injection_Nurse(nurseId, nurseName);
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

                JLabel title = new JLabel("Pending Patients");

                JLabel subtitle = new JLabel("Enter Vital Digitally and also manage patient records.");
                subtitle.setBounds(40, 150, 400, 25);
                subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                subtitle.setForeground(new Color(40, 40, 40));

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
                // PATIENT TABLE
                // =========================
                String[] patientColumns = {

                                "ID",
                                "Patient Name",
                                "Age",
                                "Disease",
                                "Doctor",
                                "Nurse Status"
                };

                patientModel = new DefaultTableModel(patientColumns, 0);

                patientTable = new JTable(patientModel);

                patientTable.setRowHeight(40);

                patientTable.setFont(
                                new Font("Segoe UI", Font.PLAIN, 14));

                patientTable.getTableHeader().setFont(
                                new Font("Segoe UI", Font.BOLD, 14));

                patientTable.getTableHeader().setPreferredSize(new Dimension(100, 50));

                patientTable.getTableHeader().setBackground(primary);

                patientTable.getTableHeader().setForeground(Color.WHITE);

                JScrollPane patientScroll = new JScrollPane(patientTable);

                patientTable.getColumnModel().getColumn(0).setPreferredWidth(100); // ID

                patientTable.getColumnModel().getColumn(1).setPreferredWidth(270); // Patient Name

                patientTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Age

                patientTable.getColumnModel().getColumn(3).setPreferredWidth(200); // Disease

                patientTable.getColumnModel().getColumn(4).setPreferredWidth(240); // Doctor

                patientTable.getColumnModel().getColumn(5).setPreferredWidth(130); // Nurse Status

                DefaultTableCellRenderer center = new DefaultTableCellRenderer();

                center.setHorizontalAlignment(JLabel.CENTER);

                patientTable.getColumnModel().getColumn(0).setCellRenderer(center);

                patientTable.getColumnModel().getColumn(2).setCellRenderer(center);

                patientTable.getColumnModel().getColumn(5).setCellRenderer(center);

                patientScroll.setBounds(40, 260, 990, 350);

                // =========================
                // BUTTON
                // =========================
                RoundedButton vitalsBtn = new RoundedButton(
                                "Enter Vitals");

                vitalsBtn.setBounds(800, 200, 200, 40);

                vitalsBtn.setBackground(primary);

                vitalsBtn.setForeground(Color.WHITE);

                // ADD COMPONENTS
                mainPanel.add(topBar);

                mainPanel.add(title);

                mainPanel.add(patientScroll);

                mainPanel.add(vitalsBtn);

                mainPanel.add(searchField);

                mainPanel.add(subtitle);

                // =========================

                add(sideBar, BorderLayout.WEST);

                add(mainPanel, BorderLayout.CENTER);

                // LOAD DATA
                loadPendingPatients();

                // SEARCH
                searchField.addKeyListener(
                                new java.awt.event.KeyAdapter() {

                                        public void keyReleased(
                                                        java.awt.event.KeyEvent e) {

                                                searchPatients(
                                                                searchField.getText());
                                        }
                                });

                // =========================
                // ENTER VITALS
                // =========================

                vitalsBtn.addActionListener(e -> {

                        int row = patientTable.getSelectedRow();

                        if (row == -1) {

                                JOptionPane.showMessageDialog(
                                                null,
                                                "Select patient first!");

                                return;
                        }

                        int patientId = Integer.parseInt(
                                        patientModel.getValueAt(row, 0).toString());

                        // =========================================
                        // DIALOG PANEL
                        // =========================================

                        JDialog dialog = new JDialog(this, "Enter Vitals", true);

                        dialog.setSize(600, 400);

                        dialog.setLocationRelativeTo(this);

                        dialog.setLayout(null);

                        dialog.getContentPane().setBackground(
                                        Color.decode("#E4DDD3"));

                        Color primary = Color.decode("#00A19B");

                        // =========================================
                        // TITLE
                        // =========================================

                        JLabel title1 = new JLabel("Enter Vitals");

                        title1.setBounds(30, 20, 250, 35);

                        title1.setFont(new Font(
                                        "Segoe UI",
                                        Font.BOLD,
                                        28));

                        title1.setForeground(primary);

                        // =========================================
                        // BP
                        // =========================================

                        JLabel bpLabel = new JLabel(
                                        "Blood Pressure (mmHg)");

                        bpLabel.setBounds(40, 80, 220, 25);

                        bpLabel.setForeground(primary);

                        bpLabel.setFont(new Font(
                                        "Segoe UI",
                                        Font.BOLD,
                                        15));

                        SpinnerNumberModel systolicModel = new SpinnerNumberModel(120, 50, 250, 1);

                        JSpinner systolicSpinner = new JSpinner(systolicModel);

                        systolicSpinner.setBounds(40, 110, 90, 30);

                        SpinnerNumberModel diastolicModel = new SpinnerNumberModel(80, 30, 180, 1);

                        JSpinner diastolicSpinner = new JSpinner(diastolicModel);

                        diastolicSpinner.setBounds(150, 110, 90, 30);

                        JLabel slash = new JLabel("/");

                        slash.setBounds(135, 105, 20, 35);

                        slash.setFont(new Font(
                                        "Segoe UI",
                                        Font.BOLD,
                                        22));

                        slash.setForeground(primary);

                        // =========================================
                        // WEIGHT
                        // =========================================

                        JLabel weightLabel = new JLabel("Weight (kg)");

                        weightLabel.setBounds(320, 80, 150, 25);

                        weightLabel.setForeground(primary);

                        weightLabel.setFont(new Font(
                                        "Segoe UI",
                                        Font.BOLD,
                                        15));

                        SpinnerNumberModel weightModel = new SpinnerNumberModel(50, 1, 300, 1);

                        JSpinner weightSpinner = new JSpinner(weightModel);

                        weightSpinner.setBounds(320, 110, 120, 30);

                        // =========================================
                        // TEMPERATURE
                        // =========================================

                        JLabel tempLabel = new JLabel("Temperature (°F)");

                        tempLabel.setBounds(40, 190, 180, 25);

                        tempLabel.setForeground(primary);

                        tempLabel.setFont(new Font(
                                        "Segoe UI",
                                        Font.BOLD,
                                        15));

                        SpinnerNumberModel tempModel = new SpinnerNumberModel(98, 90, 110, 1);

                        JSpinner tempSpinner = new JSpinner(tempModel);

                        tempSpinner.setBounds(40, 220, 120, 30);

                        // =========================================
                        // HEART RATE
                        // =========================================

                        JLabel heartLabel = new JLabel("Heart Rate (BPM)");

                        heartLabel.setBounds(320, 190, 180, 25);

                        heartLabel.setForeground(primary);

                        heartLabel.setFont(new Font(
                                        "Segoe UI",
                                        Font.BOLD,
                                        15));

                        SpinnerNumberModel heartModel = new SpinnerNumberModel(72, 30, 220, 1);

                        JSpinner heartSpinner = new JSpinner(heartModel);

                        heartSpinner.setBounds(320, 220, 120, 30);

                        // =========================================
                        // SAVE BUTTON
                        // =========================================

                        RoundedButton saveBtn = new RoundedButton("Save Vitals");

                        saveBtn.setBounds(200, 300, 180, 45);

                        saveBtn.setBackground(primary);

                        saveBtn.setForeground(Color.WHITE);

                        // =========================================
                        // SAVE LOGIC
                        // =========================================

                        saveBtn.addActionListener(ev -> {

                                try {

                                        String bp = systolicSpinner.getValue().toString()
                                                        + "/"
                                                        + diastolicSpinner.getValue().toString()
                                                        + " mmHg";

                                        String weight = weightSpinner.getValue().toString()
                                                        + " kg";

                                        String temp = tempSpinner.getValue().toString()
                                                        + " °F";

                                        String heart = heartSpinner.getValue().toString()
                                                        + " BPM";

                                        Connection con = DBconnection.getConnection();

                                        String query = "UPDATE patients SET " +

                                                        "blood_pressure = ?, " +

                                                        "weight = ?, " +

                                                        "temperature = ?, " +

                                                        "heart_rate = ?, " +

                                                        "nurse_status = 'Completed', " +

                                                        "status = 'Ready' " +

                                                        "WHERE patient_id = ?";

                                        PreparedStatement pst = con.prepareStatement(query);

                                        pst.setString(1, bp);

                                        pst.setString(2, weight);

                                        pst.setString(3, temp);

                                        pst.setString(4, heart);

                                        pst.setInt(5, patientId);

                                        pst.executeUpdate();

                                        JOptionPane.showMessageDialog(
                                                        dialog,
                                                        "Vitals Added Successfully!");

                                        dialog.dispose();

                                        loadPendingPatients();

                                } catch (Exception ex) {

                                        ex.printStackTrace();
                                }
                        });

                        // =========================================
                        // ADD COMPONENTS
                        // =========================================

                        dialog.add(title1);

                        dialog.add(bpLabel);

                        dialog.add(systolicSpinner);

                        dialog.add(slash);

                        dialog.add(diastolicSpinner);

                        dialog.add(weightLabel);

                        dialog.add(weightSpinner);

                        dialog.add(tempLabel);

                        dialog.add(tempSpinner);

                        dialog.add(heartLabel);

                        dialog.add(heartSpinner);

                        dialog.add(saveBtn);

                        dialog.setVisible(true);
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
                        }
                        else {
                                setActiveButton(pendingBtn);
                        }
                });

                setVisible(true);
        }

        // ====================================
        // LOAD PENDING PATIENTS
        // ====================================
        private void loadPendingPatients() {

                try {

                        Connection con = DBconnection.getConnection();

                        String query = "SELECT patients.*, doctors.full_name AS doctor_name " +
                                        "FROM patients " +
                                        "LEFT JOIN doctors " +
                                        "ON patients.doctor_id = doctors.doctor_id " +
                                        "WHERE nurse_status = 'Pending'";

                        PreparedStatement pst = con.prepareStatement(query);

                        ResultSet rs = pst.executeQuery();

                        patientModel.setRowCount(0);

                        while (rs.next()) {

                                patientModel.addRow(new Object[] {

                                                rs.getInt("patient_id"),

                                                rs.getString("full_name"),

                                                rs.getInt("age"),

                                                rs.getString("disease"),

                                                rs.getString("doctor_name"),

                                                rs.getString("nurse_status")
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

        private void searchPatients(String keyword) {

                try {

                        Connection con = DBconnection.getConnection();

                        String query = "SELECT patients.*, doctors.full_name AS doctor_name " +
                                        "FROM patients " +
                                        "LEFT JOIN doctors " +
                                        "ON patients.doctor_id = doctors.doctor_id " +
                                        "WHERE patients.full_name LIKE ? " +
                                        "AND patients.nurse_status = 'Pending'";

                        PreparedStatement pst = con.prepareStatement(query);

                        pst.setString(
                                        1,
                                        "%" + keyword + "%");

                        ResultSet rs = pst.executeQuery();

                        patientModel.setRowCount(0);

                        while (rs.next()) {

                                patientModel.addRow(new Object[] {

                                                rs.getInt("patient_id"),

                                                rs.getString("full_name"),

                                                rs.getInt("age"),

                                                rs.getString("disease"),

                                                rs.getString("doctor_name"),

                                                rs.getString("nurse_status")
                                });
                        }

                } catch (Exception ex) {

                        ex.printStackTrace();
                }
        }
}