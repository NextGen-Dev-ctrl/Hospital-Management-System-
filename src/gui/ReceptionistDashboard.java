package src.gui;

import src.db.DBconnection;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ReceptionistDashboard extends JFrame {

        private JTable appointmentTable;
        private DefaultTableModel model;

        private JTextField searchField;

        private Color bgColor = Color.decode("#F2EFE7");
        private Color primary = Color.decode("#00A19B");

        // for receptionist details

        private int receptionistId;
        private String receptionistNameText;

        public ReceptionistDashboard(int receptionistId, String receptionistNameText) {

                // FRAME
                setTitle("Appointment Dashboard");
                setSize(1400, 800);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLayout(new BorderLayout());

                // SIDEBAR
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

                JButton patientsBtn = createSidebarButton("Patients");

                patientsBtn.setBounds(0, 200, 220, 45);

                patientsBtn.addActionListener(e -> {

                        new patientPortal_Reception(this.receptionistId, this.receptionistNameText);
                        dispose();
                });

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

                // ACTIVE
                appointmentsBtn.setBackground(Color.WHITE);
                appointmentsBtn.setForeground(primary);

                sideBar.add(logo);
                sideBar.add(appointmentsBtn);
                sideBar.add(patientsBtn);
                sideBar.add(logoutBtn);

                // MAIN PANEL
                JPanel mainPanel = new JPanel();
                mainPanel.setBackground(bgColor);
                mainPanel.setLayout(null);

                // TOP BAR
                JPanel topBar = new JPanel();
                topBar.setBounds(0, 0, 1180, 80);
                topBar.setBackground(Color.WHITE);
                topBar.setLayout(null);

                JLabel dashboardTitle = new JLabel("Receptionist Dashboard");
                dashboardTitle.setBounds(30, 25, 250, 30);
                dashboardTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
                dashboardTitle.setForeground(new Color(40,40,40));

                this.receptionistId = receptionistId;
                this.receptionistNameText = receptionistNameText;

                JLabel receptionistName = new JLabel("<html>" + this.receptionistNameText + "<br>Receptionist ID: "
                                + this.receptionistId + "</html>");
                receptionistName.setBounds(780, 15, 250, 40);
                receptionistName.setFont(new Font("Segoe UI", Font.BOLD, 16));
                receptionistName.setForeground(primary);

                ImageIcon icon = new ImageIcon(new ImageIcon("images/profile_image.png").getImage()
                                .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                receptionistName.setIcon(icon);

                // ✅ Move icon to the right side
                receptionistName.setHorizontalTextPosition(SwingConstants.LEFT);
                receptionistName.setHorizontalAlignment(SwingConstants.RIGHT);
                topBar.add(receptionistName);
                topBar.add(dashboardTitle);

                JLabel title = new JLabel("Appointment Dashboard");
                title.setBounds(40, 110, 400, 40);
                title.setFont(new Font("Segoe UI", Font.BOLD, 28));
                title.setForeground(primary);

                JLabel subtitle = new JLabel("Manage your appointments electronically and efficiently.");
                subtitle.setBounds(40, 150, 400, 25);
                subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                subtitle.setForeground(new Color(40,40,40));
                // SEARCH
                searchField = new RoundedTextField(20);

                searchField.setBounds(40, 200, 700, 40);

                searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));

                ((RoundedTextField) searchField).setHint("Search by patient name...");

                // ADD BUTTON
                RoundedButton addBtn = new RoundedButton("Add Appointment");

                addBtn.setBounds(800, 200, 180, 40);

                addBtn.setBackground(primary);
                addBtn.setForeground(Color.WHITE);

                // TABLE
                String[] columns = {

                                "ID",
                                "Patient Name",
                                "Age",
                                "Gender",
                                "Disease",
                                "Doctor",
                                "<html>Appointment<br>Date</html>",
                                "<html>Appointment<br>Time</html>",
                                "Status"
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

                // ADD COMPONENTS
                mainPanel.add(topBar);
                mainPanel.add(title);
                mainPanel.add(searchField);
                mainPanel.add(addBtn);
                mainPanel.add(scrollPane);
                mainPanel.add(subtitle);

                add(sideBar, BorderLayout.WEST);
                add(mainPanel, BorderLayout.CENTER);

                // LOAD DATA
                loadAppointments();

                // SEARCH
                searchField.addKeyListener(
                                new java.awt.event.KeyAdapter() {

                                        public void keyReleased(
                                                        java.awt.event.KeyEvent e) {

                                                searchAppointments(
                                                                searchField.getText());
                                        }
                                });

                // ADD APPOINTMENT
                addBtn.addActionListener(e -> {

                        addAppointment();
                });

                setVisible(true);
        }

        // ====================================
        // LOAD APPOINTMENTS
        // ====================================
        private void loadAppointments() {

                try {

                        Connection con = DBconnection.getConnection();

                        String query = "SELECT patients.*, doctors.full_name AS doctor_name " +
                                        "FROM patients " +
                                        "LEFT JOIN doctors " +
                                        "ON patients.doctor_id = doctors.doctor_id";

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

                                                rs.getString("appointment_date"),

                                                rs.getString("appointment_time"),

                                                rs.getString("status")
                                });
                        }

                } catch (Exception ex) {

                        ex.printStackTrace();
                }
        }

        // ====================================
        // SEARCH APPOINTMENTS
        // ====================================
        private void searchAppointments(String keyword) {

                try {

                        Connection con = DBconnection.getConnection();

                        String query = "SELECT patients.*, doctors.full_name AS doctor_name " +
                                        "FROM patients " +
                                        "LEFT JOIN doctors " +
                                        "ON patients.doctor_id = doctors.doctor_id " +
                                        "WHERE patients.full_name LIKE ?";

                        PreparedStatement pst = con.prepareStatement(query);

                        pst.setString(1,
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

                                                rs.getString("appointment_date"),

                                                rs.getString("appointment_time"),

                                                rs.getString("status")
                                });
                        }

                } catch (Exception ex) {

                        ex.printStackTrace();
                }
        }

        // ====================================
        // ADD APPOINTMENT
        // ====================================
        private void addAppointment() {

                // =====================================
                // DIALOG
                // =====================================
                JDialog dialog = new JDialog(this,
                                "Add Appointment",
                                true);

                dialog.setSize(550, 650);

                dialog.setLocationRelativeTo(this);

                dialog.setLayout(null);

                dialog.getContentPane().setBackground(bgColor);

                // =====================================
                // TITLE
                // =====================================
                JLabel title = new JLabel("New Appointment");

                title.setBounds(30, 20, 300, 40);

                title.setFont(
                                new Font("Segoe UI",
                                                Font.BOLD,
                                                28));

                title.setForeground(primary);

                dialog.add(title);

                // =====================================
                // FULL NAME
                // =====================================
                JLabel nameLabel = new JLabel("Full Name");

                nameLabel.setBounds(40, 90, 120, 25);

                RoundedTextField nameField = new RoundedTextField(20);

                nameField.setBounds(40, 120, 450, 40);

                dialog.add(nameLabel);

                dialog.add(nameField);

                // =====================================
                // AGE
                // =====================================
                JLabel ageLabel = new JLabel("Age");

                ageLabel.setBounds(40, 180, 120, 25);

                RoundedTextField ageField = new RoundedTextField(20);

                ageField.setBounds(40, 210, 200, 40);

                dialog.add(ageLabel);

                dialog.add(ageField);

                // =====================================
                // GENDER
                // =====================================
                JLabel genderLabel = new JLabel("Gender");

                genderLabel.setBounds(290, 180, 120, 25);

                String[] genders = {
                                "Male",
                                "Female",
                                "Other"
                };

                RoundedComboBox<String> genderBox = new RoundedComboBox<String>(genders);

                genderBox.setBounds(290, 210, 200, 40);

                dialog.add(genderLabel);

                dialog.add(genderBox);

                // =====================================
                // DISEASE
                // =====================================
                JLabel diseaseLabel = new JLabel("Disease");

                diseaseLabel.setBounds(40, 270, 120, 25);

                RoundedTextField diseaseField = new RoundedTextField(20);

                diseaseField.setBounds(40, 300, 450, 40);

                dialog.add(diseaseLabel);

                dialog.add(diseaseField);

                // =====================================
                // DOCTOR
                // =====================================
                JLabel doctorLabel = new JLabel("Doctor");

                doctorLabel.setBounds(40, 360, 120, 25);

                RoundedComboBox<String> doctorBox = new RoundedComboBox<String>();

                doctorBox.setBounds(40, 390, 450, 40);

                dialog.add(doctorLabel);

                dialog.add(doctorBox);

                // =====================================
                // DATE
                // =====================================
                JLabel dateLabel = new JLabel("Appointment Date");

                dateLabel.setBounds(40, 450, 150, 25);

                JSpinner dateSpinner = new JSpinner(
                                new SpinnerDateModel());

                JSpinner.DateEditor editor = new JSpinner.DateEditor(
                                dateSpinner,
                                "dd-MM-yyyy");

                dateSpinner.setEditor(editor);

                dateSpinner.setBounds(40, 480, 200, 40);

                dialog.add(dateLabel);

                dialog.add(dateSpinner);

                // =====================================
                // TIME LABEL
                // =====================================
                JLabel timingLabel = new JLabel("Doctor Time:");

                timingLabel.setBounds(260, 460, 220, 25);

                timingLabel.setForeground(primary);

                // =====================================
                // FEE LABEL
                // =====================================
                JLabel feeLabel = new JLabel("Consultation Fee:");

                feeLabel.setBounds(260, 490, 220, 25);

                feeLabel.setForeground(primary);

                dialog.add(timingLabel);

                dialog.add(feeLabel);

                // =====================================
                // LOAD DOCTORS
                // =====================================
                try {

                        Connection con = DBconnection.getConnection();

                        String query = "SELECT * FROM doctors";

                        PreparedStatement pst = con.prepareStatement(query);

                        ResultSet rs = pst.executeQuery();

                        while (rs.next()) {

                                String item = rs.getInt("doctor_id")
                                                + " - " +
                                                rs.getString("full_name")
                                                + " (" +
                                                rs.getString("specialization")
                                                + ")";

                                doctorBox.addItem(item);
                        }

                } catch (Exception ex) {

                        ex.printStackTrace();
                }

                // =====================================
                // DOCTOR SELECTION
                // =====================================
                doctorBox.addActionListener(e -> {

                        try {

                                String selected = doctorBox.getSelectedItem().toString();

                                int doctorId = Integer.parseInt(
                                                selected.split(" - ")[0]);

                                Connection con = DBconnection.getConnection();

                                String query = "SELECT * FROM doctors " +
                                                "WHERE doctor_id=?";

                                PreparedStatement pst = con.prepareStatement(query);

                                pst.setInt(1, doctorId);

                                ResultSet rs = pst.executeQuery();

                                if (rs.next()) {

                                        timingLabel.setText(
                                                        "Doctor Time: "
                                                                        + rs.getString("available_from")
                                                                        + " to "
                                                                        + rs.getString("available_to"));

                                        feeLabel.setText(
                                                        "Fee: Rs. "
                                                                        + rs.getString("consultation_fee"));
                                }

                        } catch (Exception ex) {

                                ex.printStackTrace();
                        }
                });

                // =====================================
                // SAVE BUTTON
                // =====================================
                RoundedButton saveBtn = new RoundedButton("Save Appointment");

                saveBtn.setBounds(150, 550, 220, 45);

                saveBtn.setBackground(primary);

                saveBtn.setForeground(Color.WHITE);

                dialog.add(saveBtn);

                // =====================================
                // SAVE LOGIC
                // =====================================
                saveBtn.addActionListener(e -> {

                        // VALIDATION
                        if (nameField.getText().trim().isEmpty()
                                        || ageField.getText().trim().isEmpty()
                                        || diseaseField.getText().trim().isEmpty()) {

                                JOptionPane.showMessageDialog(
                                                dialog,
                                                "Please fill all fields!");

                                return;
                        }

                        try {

                                Connection con = DBconnection.getConnection();

                                String selectedDoctor = doctorBox.getSelectedItem().toString();

                                int doctorId = Integer.parseInt(
                                                selectedDoctor.split(" - ")[0]);

                                // =====================================
                                // GET DATE
                                // =====================================
                                java.util.Date selectedDate = (java.util.Date) dateSpinner.getValue();

                                java.sql.Date sqlDate = new java.sql.Date(
                                                selectedDate.getTime());

                                // =====================================
                                // GET DOCTOR TIMING
                                // =====================================
                                String doctorQuery = "SELECT available_from, available_to " +
                                                "FROM doctors WHERE doctor_id=?";

                                PreparedStatement doctorPst = con.prepareStatement(doctorQuery);

                                doctorPst.setInt(1, doctorId);

                                ResultSet doctorRs = doctorPst.executeQuery();

                                Time newAppointmentTime = null;

                                if (doctorRs.next()) {

                                        Time startTime = doctorRs.getTime("available_from");

                                        Time endTime = doctorRs.getTime("available_to");

                                        // CHECK NULL TIME
                                        if (startTime == null || endTime == null) {

                                                JOptionPane.showMessageDialog(
                                                                dialog,
                                                                "Doctor timing not set!");

                                                return;
                                        }

                                        // =====================================
                                        // LAST APPOINTMENT
                                        // =====================================
                                        String slotQuery = "SELECT appointment_time " +
                                                        "FROM patients " +
                                                        "WHERE doctor_id=? " +
                                                        "AND appointment_date=? " +
                                                        "ORDER BY appointment_time DESC " +
                                                        "LIMIT 1";

                                        PreparedStatement slotPst = con.prepareStatement(slotQuery);

                                        slotPst.setInt(1, doctorId);

                                        slotPst.setDate(2, sqlDate);

                                        ResultSet slotRs = slotPst.executeQuery();

                                        // FIRST PATIENT
                                        if (!slotRs.next()) {

                                                newAppointmentTime = startTime;
                                        }

                                        // NEXT SLOT
                                        else {

                                                Time lastTime = slotRs.getTime(
                                                                "appointment_time");

                                                long nextMillis = lastTime.getTime()
                                                                + (8 * 60 * 1000);

                                                newAppointmentTime = new Time(nextMillis);
                                        }

                                        // FULL SLOTS
                                        if (newAppointmentTime.after(endTime)
                                                        || newAppointmentTime.equals(endTime)) {

                                                java.sql.Date today = new java.sql.Date(
                                                                System.currentTimeMillis());

                                                // CURRENT DAY FULL
                                                if (sqlDate.equals(today)) {

                                                        JOptionPane.showMessageDialog(
                                                                        dialog,
                                                                        "Today's slots are full!\nPlease select another day.");

                                                        return;
                                                }

                                                // FUTURE DATE
                                                else {

                                                        JOptionPane.showMessageDialog(
                                                                        dialog,
                                                                        "Selected day slots are full!");

                                                        return;
                                                }
                                        }
                                }

                                // =====================================
                                // INSERT PATIENT
                                // =====================================
                                String insertQuery = "INSERT INTO patients(" +
                                                "full_name, age, gender, disease," +
                                                "doctor_id, appointment_date," +
                                                "appointment_time, status)" +
                                                "VALUES(?,?,?,?,?,?,?,?)";

                                PreparedStatement pst = con.prepareStatement(insertQuery);

                                pst.setString(1,
                                                nameField.getText());

                                pst.setInt(2,
                                                Integer.parseInt(
                                                                ageField.getText()));

                                pst.setString(3,
                                                genderBox.getSelectedItem().toString());

                                pst.setString(4,
                                                diseaseField.getText());

                                pst.setInt(5,
                                                doctorId);

                                pst.setDate(6,
                                                sqlDate);

                                pst.setTime(7,
                                                newAppointmentTime);

                                pst.setString(8,
                                                "Pending");

                                pst.executeUpdate();

                                JOptionPane.showMessageDialog(
                                                dialog,
                                                "Appointment Added!");

                                dialog.dispose();

                                loadAppointments();

                        } catch (Exception ex) {

                                ex.printStackTrace();
                        }
                });

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
