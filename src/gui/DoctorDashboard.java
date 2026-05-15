package src.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import src.db.DBconnection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DoctorDashboard extends JFrame {
        private JButton dashboardBtn;
        private JButton patientBtn;
        private JButton appointmentBtn;
        private JButton prescriptionBtn;
        private JButton logoutBtn;
        private JLabel bpValueLabel;
        private JLabel weightValueLabel;
        private JLabel tempValueLabel;
        private JLabel heartValueLabel;
        private int currentPatientId;
        private JLabel patientTitle;
        private JLabel patientInfo;
        private roundedTextArea notesArea;
        // For patient queue we use the global private variable
        private JPanel patientCard1;
        private JPanel patientCard2;
        private JPanel patientCard3;

        private JLabel patient1Name;
        private JLabel patient1Disease;
        private JLabel patient1Status;

        private JLabel patient2Name;
        private JLabel patient2Disease;
        private JLabel patient2Status;

        private JLabel patient3Name;
        private JLabel patient3Disease;
        private JLabel patient3Status;

        // for the showing of doctor details according to it
        private int doctorId;
        private String doctorName;
        private String specialization;

        public DoctorDashboard(int doctorId, String doctorName, String specialization) {
                // FRAME
                setTitle("Doctor Dashboard");
                setSize(1400, 800);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLayout(new BorderLayout());

                // COLORS
                Color bgColor = Color.decode("#F2EFE7");
                Color primary = Color.decode("#00A19B");
                Color darkText = new Color(40, 40, 40);

                // =========================
                // LEFT SIDEBAR
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

                prescriptionBtn = createSidebarButton("Prescriptions");
                prescriptionBtn.setBounds(0, 310, 220, 45);

                logoutBtn = createSidebarButton("Logout");
                logoutBtn.setBounds(0, 585, 220, 45);

                sideBar.add(logo);
                sideBar.add(dashboardBtn);
                sideBar.add(patientBtn);
                sideBar.add(appointmentBtn);
                sideBar.add(prescriptionBtn);
                sideBar.add(logoutBtn);

                // logic for the active bar from the left side

                dashboardBtn.addActionListener(e -> {
                        setActiveButton(dashboardBtn);
                });

                patientBtn.addActionListener(e -> {
                        setActiveButton(patientBtn);
                });

                appointmentBtn.addActionListener(e -> {
                        setActiveButton(appointmentBtn);
                });

                prescriptionBtn.addActionListener(e -> {
                        setActiveButton(prescriptionBtn);
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
                        }
                        else {
                                setActiveButton(dashboardBtn);
                        }
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

                this.doctorId = doctorId;
                this.doctorName = doctorName;
                this.specialization = specialization;

                JLabel doctorInfo = new JLabel("<html>" + doctorName + "<br>" + specialization + "</html>");
                doctorInfo.setBounds(800, 25, 200, 50);
                doctorInfo.setFont(new Font("Segoe UI", Font.BOLD, 18));
                doctorInfo.setForeground(primary);

                JLabel profilePic = new JLabel();
                profilePic.setBounds(960, 25, 40, 40);
                ImageIcon icon = new ImageIcon(
                                new ImageIcon("images/profile_image.png")
                                                .getImage()
                                                .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                profilePic.setIcon(icon);

                // ✅ Align icon to the right side of the label
                profilePic.setHorizontalAlignment(SwingConstants.RIGHT);

                JLabel dashboardTitle = new JLabel("Doctor Dashboard");
                dashboardTitle.setBounds(30, 25, 200, 30);
                dashboardTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
                dashboardTitle.setForeground(darkText);

                topBar.add(doctorInfo);
                topBar.add(profilePic);
                topBar.add(dashboardTitle);
                // =========================
                // PATIENT QUEUE PANEL
                // =========================
                JPanel queuePanel = new RoundedPanel(30);
                queuePanel.setBounds(30, 110, 260, 560);
                queuePanel.setBackground(Color.WHITE);
                queuePanel.setLayout(null);

                JLabel queueTitle = new JLabel("Patient Queue");
                queueTitle.setBounds(5, 20, 220, 30);
                queueTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
                queueTitle.setIcon(new ImageIcon(new ImageIcon("images/queue.png").getImage().getScaledInstance(55, 70,
                                Image.SCALE_SMOOTH)));

                // PATIENT CARD 1
                patientCard1 = new RoundedPanel(20);
                patientCard1.setBounds(20, 80, 220, 110);
                patientCard1.setBackground(primary);
                patientCard1.setLayout(null);

                patient1Name = new JLabel();
                patient1Name.setBounds(20, 15, 200, 25);
                patient1Name.setForeground(Color.WHITE);
                patient1Name.setFont(new Font("Segoe UI", Font.BOLD, 18));

                patient1Disease = new JLabel();
                patient1Disease.setBounds(20, 45, 150, 20);
                patient1Disease.setForeground(Color.WHITE);

                patient1Status = new JLabel("READY");
                patient1Status.setBounds(20, 75, 80, 20);
                patient1Status.setForeground(Color.WHITE);

                patientCard1.add(patient1Name);
                patientCard1.add(patient1Disease);
                patientCard1.add(patient1Status);

                // PATIENT CARD 2
                patientCard2 = new RoundedPanel(20);
                patientCard2.setBounds(20, 220, 220, 110);
                patientCard2.setBackground(bgColor);
                patientCard2.setLayout(null);

                patient2Name = new JLabel();
                patient2Name.setBounds(20, 15, 200, 25);
                patient2Name.setFont(new Font("Segoe UI", Font.BOLD, 16));

                patient2Disease = new JLabel();
                patient2Disease.setBounds(20, 45, 150, 20);

                patient2Status = new JLabel("PENDING");
                patient2Status.setBounds(20, 75, 80, 20);
                patient2Status.setForeground(primary);

                patientCard2.add(patient2Name);
                patientCard2.add(patient2Disease);
                patientCard2.add(patient2Status);

                // PATIENT CARD 3
                patientCard3 = new RoundedPanel(20);
                patientCard3.setBounds(20, 360, 220, 110);
                patientCard3.setBackground(bgColor);
                patientCard3.setLayout(null);

                patient3Name = new JLabel();
                patient3Name.setBounds(20, 15, 200, 25);
                patient3Name.setFont(new Font("Segoe UI", Font.BOLD, 16));

                patient3Disease = new JLabel();
                patient3Disease.setBounds(20, 45, 150, 20);

                patient3Status = new JLabel("PENDING");
                patient3Status.setBounds(20, 75, 80, 20);
                patient3Status.setForeground(primary);

                patientCard3.add(patient3Name);
                patientCard3.add(patient3Disease);
                patientCard3.add(patient3Status);

                queuePanel.add(queueTitle);
                queuePanel.add(patientCard1);
                queuePanel.add(patientCard2);
                queuePanel.add(patientCard3);

                // =========================
                // PATIENT DETAILS
                // =========================
                patientTitle = new JLabel();
                patientTitle.setBounds(320, 120, 400, 40);
                patientTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
                patientTitle.setForeground(primary);

                patientInfo = new JLabel();
                patientInfo.setBounds(320, 160, 400, 25);
                patientInfo.setFont(new Font("Segoe UI", Font.PLAIN, 16));

                // =========================
                // VITAL CARDS
                // =========================
                JPanel bpCard = createVitalCard(
                                "BLOOD PRESSURE",
                                "120/80",
                                "mmHg",
                                320,
                                220);

                JPanel weightCard = createVitalCard(
                                "WEIGHT (Kg)",
                                "72",
                                "",
                                510,
                                220);

                JPanel tempCard = createVitalCard(
                                "TEMPERATURE (°F)",
                                "98.6",
                                "",
                                700,
                                220);

                JPanel heartCard = createVitalCard(
                                "HEART RATE (bpm)",
                                "72",
                                "",
                                885,
                                220);

                // =========================
                // NOTES AREA
                // =========================
                JLabel notesLabel = new JLabel("Diagnosis & Consultation Notes");
                notesLabel.setBounds(320, 350, 350, 30);
                notesLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
                notesLabel.setForeground(primary);

                RoundedButton nextPatientBtn = new RoundedButton("Complete & Next Patient");
                nextPatientBtn.setBounds(810, 350, 200, 30);
                nextPatientBtn.setMargin(new Insets(5, 10, 5, 10));
                nextPatientBtn.setBackground(primary);
                nextPatientBtn.setForeground(Color.WHITE);
                nextPatientBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

                notesArea = new roundedTextArea();
                notesArea.setHint("Type your diagnostic notes here...");
                notesArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                notesArea.setLineWrap(true);
                notesArea.setWrapStyleWord(true);

                JScrollPane scrollPane = new JScrollPane(notesArea);
                scrollPane.setBounds(320, 400, 700, 200);
                scrollPane.setBorder(null);
                scrollPane.getViewport().setOpaque(false);
                scrollPane.setOpaque(false);

                // BUTTONS
                RoundedButton recordBtn = new RoundedButton("View Records");
                recordBtn.setBounds(710, 120, 150, 40);
                recordBtn.setMargin(new Insets(5, 10, 5, 10));
                recordBtn.setBackground(Color.WHITE);
                recordBtn.setForeground(primary);

                RoundedButton prescriptionBtn2 = new RoundedButton("Write Prescription");
                prescriptionBtn2.setBounds(880, 120, 150, 40);
                prescriptionBtn2.setMargin(new Insets(5, 10, 5, 10));
                prescriptionBtn2.setBackground(primary);
                prescriptionBtn2.setForeground(Color.WHITE);

                // prescripton button action listener to open the prescription writing interface
                // which can be implemented as a new JFrame or a JDialog
                prescriptionBtn2.addActionListener(e -> {

                        openPrescriptionDialog();

                });
                // ADD COMPONENTS
                mainPanel.add(topBar);
                mainPanel.add(queuePanel);

                mainPanel.add(patientTitle);
                mainPanel.add(patientInfo);

                mainPanel.add(bpCard);
                mainPanel.add(weightCard);
                mainPanel.add(tempCard);
                mainPanel.add(heartCard);

                mainPanel.add(notesLabel);
                mainPanel.add(scrollPane);
                mainPanel.add(nextPatientBtn);
                mainPanel.add(recordBtn);
                mainPanel.add(prescriptionBtn2);

                add(sideBar, BorderLayout.WEST);
                add(mainPanel, BorderLayout.CENTER);
                setActiveButton(dashboardBtn);

                // Data base fatching logic for the patient details and vitals can be
                // implemented here using JDBC and the DBconnection class
                try {

                        Connection con = DBconnection.getConnection();

                        String query = "SELECT * FROM patients WHERE doctor_id = ? LIMIT 1";

                        PreparedStatement pst = con.prepareStatement(query);

                        pst.setInt(1, 1); // doctor id

                        ResultSet rs = pst.executeQuery();

                        if (rs.next()) {

                                // PATIENT NAME
                                patientTitle.setText(rs.getString("full_name"));

                                // PATIENT INFO
                                patientInfo.setText("Patient ID: PAT-" + rs.getInt("patient_id") + "  •  "
                                                + rs.getInt("age") + " Years  •  " + rs.getString("gender"));

                                // UPDATE VITAL CARDS
                                bpValueLabel.setText(rs.getString("blood_pressure"));

                                weightValueLabel.setText(rs.getString("weight"));

                                tempValueLabel.setText(rs.getString("temperature"));

                                heartValueLabel.setText(rs.getString("heart_rate"));

                                bpCard.repaint();
                                weightCard.repaint();
                                tempCard.repaint();
                                heartCard.repaint();
                        }

                } catch (Exception ex) {

                        ex.printStackTrace();
                }
                // next patient button logic to update the patient record and load the next
                // patient in the queue and also add consultant notes and the prescriptions
                // automatically
                nextPatientBtn.addActionListener(e -> {
                        try {

                                Connection con = DBconnection.getConnection();

                                // ===================================
                                // INSERT INTO OLD PATIENTS
                                // ===================================

                                String insertQuery = "INSERT INTO old_patients (" +
                                                "patient_id, full_name, age, gender, disease," +
                                                "blood_pressure, weight, temperature, heart_rate," +
                                                "notes, doctor_id)" +
                                                " SELECT patient_id, full_name, age, gender, disease," +
                                                "blood_pressure, weight, temperature, heart_rate," +
                                                "?, doctor_id " +
                                                "FROM patients WHERE patient_id = ?";

                                PreparedStatement insertPst = con.prepareStatement(insertQuery);

                                // NOTES
                                if (notesArea.getText().trim().isEmpty()) {
                                        insertPst.setNull(1, Types.NULL);
                                } else {
                                        insertPst.setString(1, notesArea.getText());
                                }

                                // CURRENT PATIENT ID
                                insertPst.setInt(2, currentPatientId);

                                insertPst.executeUpdate();

                                // ===================================
                                // DELETE FROM ACTIVE PATIENT TABLE
                                // ===================================

                                String deleteQuery = "DELETE FROM patients " +
                                                "WHERE patient_id = ?";

                                PreparedStatement deletePst = con.prepareStatement(deleteQuery);

                                deletePst.setInt(1, currentPatientId);

                                deletePst.executeUpdate();

                                // ===================================
                                // NEXT PATIENT READY
                                // ===================================

                                String nextQuery = "UPDATE patients SET status = 'Ready' " +
                                                "WHERE patient_id = (" +
                                                "SELECT patient_id FROM (" +
                                                "SELECT patient_id FROM patients " +
                                                "WHERE status = 'Pending' " +
                                                "ORDER BY patient_id ASC " +
                                                "LIMIT 1" +
                                                ") temp)";

                                PreparedStatement pst2 = con.prepareStatement(nextQuery);

                                pst2.executeUpdate();

                                // REFRESH
                                loadPatientQueue();
                                loadNextPatient();

                                notesArea.setText("");
                                // prescriptionArea.setText("");

                                JOptionPane.showMessageDialog(
                                                null,
                                                "Patient Completed!");

                        } catch (Exception ex) {

                                ex.printStackTrace();
                        }
                });
                loadPatientQueue();
                loadNextPatient();
                setVisible(true);
        }

        // constructor is end
        // ====================================
        // SIDEBAR BUTTON
        // ====================================
        JButton createSidebarButton(String text) {

                JButton btn = new JButton(text);

                btn.setFocusPainted(false);
                btn.setBorderPainted(false);

                btn.setBackground(Color.WHITE);
                btn.setForeground(Color.decode("#00A19B"));

                btn.setFont(new Font("Segoe UI", Font.BOLD, 16));

                return btn;
        }

        // active button
        private void setActiveButton(JButton activeBtn) {

                JButton[] buttons = { dashboardBtn, patientBtn, appointmentBtn, prescriptionBtn, logoutBtn };

                for (JButton btn : buttons) {

                        // NORMAL BUTTON
                        btn.setBackground(Color.decode("#008B86"));
                        btn.setForeground(Color.WHITE);
                }

                // ACTIVE BUTTON
                activeBtn.setBackground(Color.WHITE);
                activeBtn.setForeground(Color.decode("#00A19B"));
        }

        // ====================================
        // VITAL CARD
        // ====================================
        private JPanel createVitalCard(
                        String title,
                        String value,
                        String unit,
                        int x,
                        int y) {

                JPanel panel = new RoundedPanel(25);

                panel.setBounds(x, y, 160, 100);
                panel.setBackground(Color.WHITE);
                panel.setLayout(null);

                JLabel titleLabel = new JLabel();
                titleLabel.setText(title);
                titleLabel.setBounds(20, 15, 150, 20);
                titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));

                JLabel valueLabel = new JLabel(value);

                if (title.contains("BLOOD")) {
                        bpValueLabel = valueLabel;
                }

                else if (title.contains("WEIGHT")) {
                        weightValueLabel = valueLabel;
                }

                else if (title.contains("TEMPERATURE")) {
                        tempValueLabel = valueLabel;
                }

                else if (title.contains("HEART")) {
                        heartValueLabel = valueLabel;
                }
                valueLabel.setBounds(20, 50, 100, 40);
                valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
                valueLabel.setForeground(Color.decode("#00A19B"));

                JLabel unitLabel = new JLabel();
                unitLabel.setText(unit);
                unitLabel.setBounds(110, 60, 60, 20);

                panel.add(titleLabel);
                panel.add(valueLabel);
                panel.add(unitLabel);

                return panel;
        }

        private void loadNextPatient() {

                try {

                        Connection con = DBconnection.getConnection();

                        String query = "SELECT * FROM patients " +
                                        "WHERE doctor_id = ? " +
                                        "AND status IN ('Ready','Pending') " +
                                        "AND nurse_status = 'Completed' " +
                                        "ORDER BY " +
                                        "CASE " +
                                        "WHEN status = 'Ready' THEN 1 " +
                                        "WHEN status = 'Pending' THEN 2 " +
                                        "END, patient_id ASC " +
                                        "LIMIT 1";

                        PreparedStatement pst = con.prepareStatement(query);

                        pst.setInt(1, doctorId);

                        ResultSet rs = pst.executeQuery();

                        // PATIENT FOUND
                        if (rs.next()) {

                                currentPatientId = rs.getInt("patient_id");

                                patientTitle.setText(
                                                rs.getString("full_name"));

                                patientInfo.setText(
                                                "Patient ID: PAT-" +
                                                                rs.getInt("patient_id") +
                                                                " • " +
                                                                rs.getInt("age") +
                                                                " Years • " +
                                                                rs.getString("gender"));

                                bpValueLabel.setText(
                                                rs.getString("blood_pressure"));

                                weightValueLabel.setText(
                                                rs.getString("weight"));

                                tempValueLabel.setText(
                                                rs.getString("temperature"));

                                heartValueLabel.setText(
                                                rs.getString("heart_rate"));

                                // CLEAR INPUTS

                                notesArea.setText("");
                        }

                        // NO MORE PATIENTS
                        else {

                                JOptionPane.showMessageDialog(
                                                null,
                                                "No more patients in queue!");

                                patientTitle.setText(
                                                "No Patient");

                                patientInfo.setText("");

                                bpValueLabel.setText("--");

                                weightValueLabel.setText("--");

                                tempValueLabel.setText("--");

                                heartValueLabel.setText("--");
                        }

                } catch (Exception ex) {

                        ex.printStackTrace();
                }
        }

        // queue method to reset the queue
        private void loadPatientQueue() {

                try {
                        // RESET ALL CARDS
                        patient1Name.setText("");
                        patient1Disease.setText("");
                        patient1Status.setText("");

                        patient2Name.setText("");
                        patient2Disease.setText("");
                        patient2Status.setText("");

                        patient3Name.setText("");
                        patient3Disease.setText("");
                        patient3Status.setText("");

                        Connection con = DBconnection.getConnection();

                        String query = "SELECT * FROM patients " +
                                        "WHERE doctor_id = ? " +
                                        "AND nurse_status = 'Completed' " +
                                        "AND status IN ('Ready', 'Pending') " +
                                        "ORDER BY " +
                                        "CASE " +
                                        "WHEN status = 'Ready' THEN 1 " +
                                        "WHEN status = 'Pending' THEN 2 " +
                                        "END, patient_id ASC " +
                                        "LIMIT 3";

                        PreparedStatement pst = con.prepareStatement(query);

                        pst.setInt(1, doctorId);

                        ResultSet rs = pst.executeQuery();

                        int count = 0;

                        while (rs.next()) {

                                count++;

                                String name = rs.getString("full_name");

                                String disease = rs.getString("disease");

                                String status = rs.getString("status");

                                // CURRENT PATIENT
                                if (count == 1) {

                                        patient1Name.setText(name);

                                        patient1Disease.setText(disease);

                                        patient1Status.setText(status);

                                        currentPatientId = rs.getInt("patient_id");
                                }

                                // SECOND PATIENT
                                else if (count == 2) {

                                        patient2Name.setText(name);

                                        patient2Disease.setText(disease);

                                        patient2Status.setText(status);
                                }

                                // THIRD PATIENT
                                else if (count == 3) {

                                        patient3Name.setText(name);

                                        patient3Disease.setText(disease);

                                        patient3Status.setText(status);
                                }
                        }

                } catch (Exception ex) {

                        ex.printStackTrace();
                }
        }

        // method to open the prescription writing dialog
        private void openPrescriptionDialog() {

                JDialog dialog = new JDialog(this, "Digital Prescription Pad", true);
                dialog.setSize(680, 480);
                dialog.setLocationRelativeTo(this);
                dialog.setLayout(null);
                dialog.setBackground(Color.decode("#E4DDD3"));

                Color primary = Color.decode("#00A19B");

                JLabel title = new JLabel("Digital Prescription Pad");
                title.setBounds(35, 20, 300, 35);
                title.setFont(new Font("Segoe UI", Font.BOLD, 24));
                title.setForeground(primary);

                RoundedTextField medicineField = new RoundedTextField(20);
                medicineField.setBounds(30, 80, 600, 40);

                DefaultComboBoxModel<String> medicineModel = new DefaultComboBoxModel<>();
                RoundedComboBox<String> medicineBox = new RoundedComboBox<>(medicineModel);
                medicineBox.setBounds(30, 130, 600, 40);

                // medicine list panel to hold the list of medicines fetched from the database
                // and displayed in a scrollable pane
                JPanel medicineListPanel = new JPanel();
                medicineListPanel.setLayout(new BoxLayout(medicineListPanel, BoxLayout.Y_AXIS));
                medicineListPanel.setBackground(Color.WHITE);

                JScrollPane medicineScroll = new JScrollPane(medicineListPanel);
                medicineScroll.setBounds(30, 180, 620, 180);

                // add madicine button to add the medicine from the text field to the combo box
                // and also save it in the database with the current patient id and also add a
                // delete button to remove the medicine from the combo box and also from the
                // database
                RoundedButton addMedicineBtn = new RoundedButton("Add Medicine");
                addMedicineBtn.setBounds(30, 370, 130, 40);
                addMedicineBtn.setBackground(primary);
                addMedicineBtn.setForeground(Color.WHITE);

                // add madicine button action listener to add the medicine from the text field
                // to the combo box and also save it in the database with the current patient id
                // and also add a delete button to remove the medicine from the combo box and
                // also from the database
                addMedicineBtn.addActionListener(e -> {
                        JPanel itemPanel = new JPanel();
                        itemPanel.setLayout(null);
                        itemPanel.setPreferredSize(new Dimension(580, 60));
                        itemPanel.setBackground(Color.WHITE);

                        DefaultComboBoxModel<String> newModel = new DefaultComboBoxModel<>();
                        for (int i = 0; i < medicineModel.getSize(); i++) {
                                newModel.addElement(medicineModel.getElementAt(i));
                        }
                        RoundedComboBox<String> medBox = new RoundedComboBox<>(newModel);
                        medBox.setBounds(10, 10, 220, 35);

                        RoundedTextField dosageField = new RoundedTextField(20);
                        dosageField.setBounds(250, 10, 100, 35);
                        dosageField.setHint("Dosage (mg)");

                        RoundedTextField frequencyField = new RoundedTextField(20);
                        frequencyField.setBounds(360, 10, 150, 35);
                        frequencyField.setHint("Frequency (times/day)");

                        RoundedButton removeBtn = new RoundedButton("X");
                        removeBtn.setBounds(520, 10, 45, 35);
                        removeBtn.setBackground(Color.RED);
                        removeBtn.setForeground(Color.WHITE);

                        itemPanel.add(medBox);
                        itemPanel.add(dosageField);
                        itemPanel.add(frequencyField);
                        itemPanel.add(removeBtn);

                        medicineListPanel.add(itemPanel);
                        medicineListPanel.revalidate();
                        medicineListPanel.repaint();

                        removeBtn.addActionListener(ev -> {
                                medicineListPanel.remove(itemPanel);
                                medicineListPanel.revalidate();
                                medicineListPanel.repaint();
                        });
                });
                // LOAD MEDICINES FROM DATABASE
                try {

                        Connection con = DBconnection.getConnection();

                        String query = "SELECT * FROM medicines";

                        PreparedStatement pst = con.prepareStatement(query);

                        ResultSet rs = pst.executeQuery();

                        while (rs.next()) {

                                String medicine = rs.getString("medicine_name");

                                int stock = rs.getInt("stock");

                                medicineModel.addElement(
                                                medicine + " (Stock: " + stock + ")");
                        }

                } catch (Exception ex) {

                        ex.printStackTrace();
                }
                // create send button to send the prescription to the pharmacy and also save the
                // prescription in the database with the current patient id and also reduce the
                // stock of the medicine in the database automatically when the prescription is
                // sent to the pharmacy
                RoundedButton sendBtn = new RoundedButton("Send to Pharmacy");

                sendBtn.setBounds(430, 370, 220, 40);

                sendBtn.setBackground(primary);
                sendBtn.setForeground(Color.WHITE);

                // Auto search filter to search for medicines in the database and display them
                // in the combo box as the user types in the medicine text field

                medicineField.addKeyListener(new KeyAdapter() {

                        @Override
                        public void keyReleased(KeyEvent e) {

                                String search = medicineField.getText().toLowerCase();

                                medicineModel.removeAllElements();

                                try {

                                        Connection con = DBconnection.getConnection();

                                        String query = "SELECT * FROM medicines " +
                                                        "WHERE medicine_name LIKE ?";

                                        PreparedStatement pst = con.prepareStatement(query);

                                        pst.setString(1,
                                                        "%" + search + "%");

                                        ResultSet rs = pst.executeQuery();

                                        while (rs.next()) {

                                                medicineModel.addElement(
                                                                rs.getString("medicine_name") +
                                                                                " (Stock: " +
                                                                                rs.getInt("stock") + ")");
                                        }

                                } catch (Exception ex) {

                                        ex.printStackTrace();
                                }
                        }
                });
                // save multiple medicines with dosage and frequency to the database with the
                // current patient id and also reduce the stock of the medicine in the database
                // automatically when the prescription is sent to the pharmacy
                sendBtn.addActionListener(e -> {
                        try {
                                Connection con = DBconnection.getConnection();

                                // Check if patient exists and is still in queue
                                String checkQuery = "SELECT COUNT(*) FROM patients WHERE patient_id = ? AND status != 'Completed'";
                                PreparedStatement checkPst = con.prepareStatement(checkQuery);
                                checkPst.setInt(1, currentPatientId);
                                ResultSet checkRs = checkPst.executeQuery();

                                if (checkRs.next() && checkRs.getInt(1) == 0) {
                                        // No active patient found
                                        JOptionPane.showMessageDialog(
                                                        dialog,
                                                        "No active patient in queue. Prescription cannot be sent!");
                                        return; // stop here
                                }

                                // If patient exists, proceed with prescription insert
                                Component[] components = medicineListPanel.getComponents();

                                for (Component comp : components) {
                                        JPanel row = (JPanel) comp;

                                        RoundedComboBox<String> medBox = (RoundedComboBox<String>) row.getComponent(0);
                                        RoundedTextField dosage = (RoundedTextField) row.getComponent(1);
                                        RoundedTextField frequency = (RoundedTextField) row.getComponent(2);

                                        String medicine = medBox.getSelectedItem().toString();
                                        medicine = medicine.split("\\(")[0].trim();

                                        // =====================================
                                        // GET MEDICINE TYPE FROM DATABASE
                                        // =====================================

                                        String typeQuery = "SELECT medicine_type FROM medicines " +
                                                        "WHERE medicine_name = ?";

                                        PreparedStatement typePst = con.prepareStatement(typeQuery);

                                        typePst.setString(1, medicine);

                                        ResultSet typeRs = typePst.executeQuery();

                                        String medicineType = "";

                                        if (typeRs.next()) {

                                                medicineType = typeRs.getString("medicine_type");
                                        }

                                        // INSERT PRESCRIPTION
                                        String query = "INSERT INTO prescriptions " +
                                                        "(patient_id, medicine_name, dosage, frequency, prescribed_by,medicine_type,nurse_status,status) "
                                                        +
                                                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                                        PreparedStatement pst = con.prepareStatement(query);
                                        pst.setInt(1, currentPatientId);
                                        pst.setString(2, medicine);
                                        pst.setString(3, dosage.getText());
                                        pst.setString(4, frequency.getText());
                                        pst.setInt(5, 1); // doctor id
                                        pst.setString(8, "Pending");
                                        pst.setString(6, medicineType);
                                        if (medicineType.equalsIgnoreCase("Injection")) {

                                                pst.setString(7, "Pending");

                                        } else {

                                                pst.setString(7, "Not Required");
                                        }
                                        pst.executeUpdate();

                                        // AUTO STOCK REDUCE
                                        String stockQuery = "UPDATE medicines SET stock = stock - 1 WHERE medicine_name = ?";
                                        PreparedStatement stockPst = con.prepareStatement(stockQuery);
                                        stockPst.setString(1, medicine);
                                        stockPst.executeUpdate();
                                }

                                JOptionPane.showMessageDialog(dialog, "Prescription Sent!");
                                dialog.dispose();

                        } catch (Exception ex) {
                                ex.printStackTrace();
                        }
                });

                dialog.add(title);
                dialog.add(medicineField);
                dialog.add(medicineBox);
                dialog.add(sendBtn);
                dialog.add(addMedicineBtn);
                dialog.add(medicineScroll);

                dialog.setVisible(true);
        }
}
