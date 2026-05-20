package src.gui;

import src.db.DBconnection;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class patientPortal_Doctor extends JFrame {

    private JButton dashboardBtn;
    private JButton patientBtn;
    private JButton appointmentBtn;
    private JButton logoutBtn;
    private JTable patientTable;
    private DefaultTableModel model;

    private JTextField searchField;

    private Color bgColor = Color.decode("#F2EFE7");
    private Color primary = Color.decode("#00A19B");

    private int doctorId;
    private String doctorName;
    private String specialization;

    public patientPortal_Doctor(int doctorId, String doctorName, String specialization) {

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
        setActiveButton(patientBtn);
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
                setActiveButton(patientBtn);
            }
        });
        appointmentBtn.addActionListener(e -> {
            setActiveButton(appointmentBtn);
            new AppointmentPortal_Doctor(doctorId, doctorName, specialization);
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
        RoundedButton searchDoctorBtn = new RoundedButton("View Records");
        searchDoctorBtn.setBounds(790, 200, 220, 40);
        searchDoctorBtn.setBackground(primary);
        searchDoctorBtn.setForeground(Color.WHITE);

        searchDoctorBtn.addActionListener(e -> {

            ViewRecord();
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
            String query = "SELECT " +
                    "old_patients.patient_id, " +
                    "old_patients.full_name, " +
                    "old_patients.age, " +
                    "old_patients.gender, " +
                    "old_patients.disease, " +
                    "doctors.full_name AS doctor_name, " +
                    "old_patients.completed_at " +
                    "FROM old_patients " +
                    "LEFT JOIN doctors " +
                    "ON old_patients.doctor_id = doctors.doctor_id " +
                    "WHERE old_patients.doctor_id = ? " +
                    "ORDER BY old_patients.completed_at DESC";

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

            String query = "SELECT old_patients.*, " +
                    "doctors.full_name AS doctor_name " +
                    "FROM old_patients " +
                    "LEFT JOIN doctors " +
                    "ON old_patients.doctor_id = doctors.doctor_id " +
                    "WHERE old_patients.full_name LIKE ? " +
                    "AND old_patients.doctor_id = ?";

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

                        rs.getTimestamp("completed_at")
                });
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    // check the doctor availabilty at the current time
    private void ViewRecord() {

        // =========================
        // CHECK ROW SELECTED
        // =========================
        int row = patientTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    null,
                    "First select a patient row!");

            return;
        }

        // =========================
        // GET SELECTED PATIENT ID
        // =========================
        int patientId = Integer.parseInt(
                patientTable.getValueAt(row, 0).toString());

        // =========================
        // DIALOG
        // =========================
        JDialog dialog = new JDialog(this, "Patient Medical Record", true);

        dialog.setSize(750, 600);

        dialog.setLocationRelativeTo(this);

        dialog.setLayout(null);

        dialog.getContentPane().setBackground(
                Color.decode("#E4DDD3"));

        Color primary = Color.decode("#00A19B");

        // =========================
        // TITLE
        // =========================
        JLabel title = new JLabel("Patient Medical Record");

        title.setBounds(25, 15, 350, 35);

        title.setFont(
                new Font("Segoe UI", Font.BOLD, 26));

        title.setForeground(primary);

        dialog.add(title);

        // =========================
        // HEALTH DETAILS TITLE
        // =========================
        JLabel healthTitle = new JLabel("Vitals & Notes");

        healthTitle.setBounds(40, 70, 250, 30);

        healthTitle.setFont(
                new Font("Segoe UI", Font.BOLD, 20));

        healthTitle.setForeground(primary);

        dialog.add(healthTitle);

        // =========================
        // LABELS
        // =========================
        JLabel bpLabel = new JLabel("Blood Pressure:");

        bpLabel.setBounds(40, 120, 180, 25);

        bpLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 15));

        bpLabel.setForeground(primary);

        JLabel weightLabel = new JLabel("Weight:");

        weightLabel.setBounds(40, 160, 180, 25);

        weightLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 15));

        weightLabel.setForeground(primary);

        JLabel tempLabel = new JLabel("Temperature:");

        tempLabel.setBounds(40, 200, 180, 25);

        tempLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 15));

        tempLabel.setForeground(primary);

        JLabel heartLabel = new JLabel("Heart Rate:");

        heartLabel.setBounds(40, 240, 180, 25);

        heartLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 15));

        heartLabel.setForeground(primary);

        JLabel notesTitle = new JLabel("Doctor Notes:");

        notesTitle.setBounds(40, 285, 180, 25);

        notesTitle.setFont(
                new Font("Segoe UI", Font.BOLD, 15));

        notesTitle.setForeground(primary);

        // =========================
        // VALUE LABELS
        // =========================
        JLabel bpValue = new JLabel();

        bpValue.setBounds(220, 120, 250, 25);

        JLabel weightValue = new JLabel();

        weightValue.setBounds(220, 160, 250, 25);

        JLabel tempValue = new JLabel();

        tempValue.setBounds(220, 200, 250, 25);

        JLabel heartValue = new JLabel();

        heartValue.setBounds(220, 240, 250, 25);

        JTextArea notesArea = new JTextArea();

        notesArea.setBounds(40, 320, 650, 80);

        notesArea.setLineWrap(true);

        notesArea.setWrapStyleWord(true);

        notesArea.setEditable(false);

        notesArea.setFont(
                new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane notesScroll = new JScrollPane(notesArea);

        notesScroll.setBounds(40, 320, 650, 80);

        dialog.add(bpLabel);
        dialog.add(weightLabel);
        dialog.add(tempLabel);
        dialog.add(heartLabel);

        dialog.add(bpValue);
        dialog.add(weightValue);
        dialog.add(tempValue);
        dialog.add(heartValue);

        dialog.add(notesTitle);

        dialog.add(notesScroll);

        // =========================
        // PRESCRIPTION TITLE
        // =========================
        JLabel prescriptionTitle = new JLabel("Prescription History");

        prescriptionTitle.setBounds(40, 400, 300, 30);

        prescriptionTitle.setFont(
                new Font("Segoe UI", Font.BOLD, 20));

        prescriptionTitle.setForeground(primary);

        dialog.add(prescriptionTitle);

        // =========================
        // TABLE
        // =========================
        String[] columns = {

                "Medicine",
                "Dosage",
                "Frequency"
        };

        DefaultTableModel prescriptionModel = new DefaultTableModel(columns, 0);

        JTable prescriptionTable = new JTable(prescriptionModel);

        prescriptionTable.setRowHeight(30);

        prescriptionTable.setFont(
                new Font("Segoe UI", Font.PLAIN, 13));

        prescriptionTable.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 13));

        prescriptionTable.getTableHeader().setBackground(primary);

        prescriptionTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane tableScroll = new JScrollPane(prescriptionTable);

        tableScroll.setBounds(40, 440, 650, 100);

        dialog.add(tableScroll);

        // =========================
        // LOAD DATA
        // =========================
        try {

            Connection con = DBconnection.getConnection();

            // =========================
            // LOAD OLD PATIENT DATA
            // =========================
            String patientQuery = "SELECT * FROM old_patients " +
                    "WHERE patient_id = ?";

            PreparedStatement patientPst = con.prepareStatement(patientQuery);

            patientPst.setInt(1, patientId);

            ResultSet patientRs = patientPst.executeQuery();

            if (patientRs.next()) {

                bpValue.setText(
                        patientRs.getString("blood_pressure")
                                + " mmHg");

                weightValue.setText(
                        patientRs.getString("weight")
                                + " Kg");

                tempValue.setText(
                        patientRs.getString("temperature")
                                + " °F");

                heartValue.setText(
                        patientRs.getString("heart_rate")
                                + " bpm");

                String notes = patientRs.getString("notes");

                if (notes == null ||
                        notes.trim().isEmpty()) {

                    notesArea.setText(
                            "Doctor did not write any notes.");

                } else {

                    notesArea.setText(notes);
                }
            }

            // =========================
            // LOAD PRESCRIPTIONS
            // =========================
            String prescriptionQuery = "SELECT old_prescriptions.* " +
                    "FROM old_prescriptions " +
                    "WHERE patient_id = ? " +
                    "AND prescribed_by = ?";

            PreparedStatement prescriptionPst = con.prepareStatement(prescriptionQuery);

            prescriptionPst.setInt(1, patientId);

            // CURRENT DOCTOR ID
            prescriptionPst.setInt(2, doctorId);

            ResultSet prescriptionRs = prescriptionPst.executeQuery();

            while (prescriptionRs.next()) {

                prescriptionModel.addRow(new Object[] {

                        prescriptionRs.getString("medicine_name"),

                        prescriptionRs.getString("dosage"),

                        prescriptionRs.getString("frequency")
                });
            }

            con.close();

        } catch (Exception ex) {

            ex.printStackTrace();
        }

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
