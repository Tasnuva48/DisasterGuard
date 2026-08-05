/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dg.spadmindashboard;

/**
 *
 * @author samih
 */
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import com.dg.model.Alert;
import com.dg.dao.*;

import org.jdesktop.swingx.JXDatePicker;
import com.dg.dbconnection.SQLiteConnect;

public class GenerateAlertsFrame extends javax.swing.JInternalFrame {

    private JXDatePicker datePicker;
    private String superAdminUsername;
    private JDesktopPane desktop;
    private java.util.Map<String, String[]> districtMap;

    /**
     * Creates new form GenerateAlertsFrame
     */
    public GenerateAlertsFrame(JDesktopPane desktop, String username) {
        initComponents();

// Hover Effect
        btnSend.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSend.setBackground(Color.decode("#C1272D")); // Darker red on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSend.setBackground(Color.decode("#E63946"));
            }
        });

        btnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnClear.setBackground(Color.decode("#495057")); // Darker red on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnClear.setBackground(Color.decode("#6C757D"));
            }
        });

        this.superAdminUsername = username;
        this.desktop = desktop;
        initDatePicker();
        initDistrictData();
        setupDivisionListeners();
        // Start with No Expiry unchecked
        chkNoExpiry.setSelected(false);
        datePicker.setEnabled(true);
        timeSpinner.setEnabled(true);

// Toggle date & time pickers when checkbox changes
        chkNoExpiry.addActionListener(e -> {
            boolean noExpiry = chkNoExpiry.isSelected();
            datePicker.setEnabled(!noExpiry);
            timeSpinner.setEnabled(!noExpiry);
        });
        setupActions();

    }
    private JSpinner timeSpinner; // add this at class level with datePicker

    private void initDatePicker() {
        datePicker = new JXDatePicker();
        datePicker.setFormats(new SimpleDateFormat("yyyy-MM-dd"));
        datePicker.setDate(new Date()); // default to today

        // Time spinner
        SpinnerDateModel timeModel = new SpinnerDateModel();
        timeSpinner = new JSpinner(timeModel);
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm:ss")); // show hours:minutes:seconds
        timeSpinner.setValue(new Date()); // default to current time

        // Panel layout
        datePickerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        datePickerPanel.add(datePicker);
        datePickerPanel.add(timeSpinner);
    }

    private void setupActions() {
        // Send alert
        btnSend.addActionListener(e -> {
            try {
                sendAlert();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        // Clear fields
        btnClear.addActionListener(e -> clearFields());
    }

    private void initDistrictData() {
        districtMap = new java.util.HashMap<>();

        districtMap.put("Dhaka", new String[]{
            "Dhaka", "Gazipur", "Narayanganj", "Narsingdi",
            "Tangail", "Manikganj", "Munshiganj", "Faridpur",
            "Gopalganj", "Madaripur", "Rajbari", "Shariatpur", "Kishoreganj"
        });

        districtMap.put("Chattogram", new String[]{
            "Brahmanbaria", "Chattogram", "Cox’s Bazar", "Bandarban",
            "Khagrachhari", "Rangamati", "Cumilla",
            "Feni", "Noakhali", "Lakshmipur", "Chandpur"
        });

        districtMap.put("Rajshahi", new String[]{
            "Rajshahi", "Bogura", "Naogaon",
            "Natore", "Chapainawabganj", "Joypurhat",
            "Pabna", "Sirajganj"
        });

        districtMap.put("Khulna", new String[]{
            "Khulna", "Jashore", "Satkhira",
            "Bagerhat", "Narail", "Jhenaidah",
            "Chuadanga", "Meherpur", "Magura"
        });

        districtMap.put("Barishal", new String[]{
            "Barishal", "Bhola", "Patuakhali",
            "Barguna", "Pirojpur", "Jhalokathi"
        });

        districtMap.put("Sylhet", new String[]{
            "Sylhet", "Moulvibazar", "Habiganj", "Sunamganj"
        });

        districtMap.put("Rangpur", new String[]{
            "Rangpur", "Dinajpur", "Kurigram",
            "Gaibandha", "Nilphamari", "Lalmonirhat",
            "Panchagarh", "Thakurgaon"
        });

        districtMap.put("Mymensingh", new String[]{
            "Mymensingh", "Jamalpur", "Sherpur", "Netrokona"
        });
    }

    private void setupDivisionListeners() {

        cmbDivision.addActionListener(e -> {
            updateDistricts(
                    cmbDivision,
                    cmbDistrict
            );
        });

    }

    private void updateDistricts(
            JComboBox<String> divisionBox,
            JComboBox<String> districtBox) {

        String selectedDivision = (String) divisionBox.getSelectedItem();

        districtBox.removeAllItems();
        districtBox.addItem("---Select District---");

        if (districtMap.containsKey(selectedDivision)) {
            for (String district : districtMap.get(selectedDivision)) {
                districtBox.addItem(district);
            }
        }
    }

    private void sendAlert() {
        try {
            // 1️⃣ Get values from UI
            String title = txtTitle.getText().trim();
            String message = txtMessage.getText().trim();
            String type = cmbAlertType.getSelectedItem().toString();
            String division = cmbDivision.getSelectedItem().toString();
            String district = cmbDistrict.getSelectedItem().toString();
            String createdBy = superAdminUsername;
            String address = txtAddress.getText().trim();

            // 2️⃣ Validate inputs
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a title.");
                return;
            }
            if (message.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a message.");
                return;
            }
            if (type.equals("---Select Type---")) {
                JOptionPane.showMessageDialog(this, "Please select an alert type.");
                return;
            }
            if (division.equals("---Select Division---")) {
                JOptionPane.showMessageDialog(this, "Please select a division.");
                return;
            }
            if (district.equals("---Select District---")) {
                JOptionPane.showMessageDialog(this, "Please select a district.");
                return;
            }
            if (address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the disaster address.");
                return;
            }

            // 3️⃣ Handle expiry date
            Date expiryDate = null;
            if (!chkNoExpiry.isSelected()) {
                Date datePart = datePicker.getDate();
                Date timePart = (Date) timeSpinner.getValue();
                if (datePart == null || timePart == null) {
                    JOptionPane.showMessageDialog(this, "Please select a valid expiry date and time.");
                    return;
                }
                Calendar calDate = Calendar.getInstance();
                calDate.setTime(datePart);
                Calendar calTime = Calendar.getInstance();
                calTime.setTime(timePart);
                calDate.set(Calendar.HOUR_OF_DAY, calTime.get(Calendar.HOUR_OF_DAY));
                calDate.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
                calDate.set(Calendar.SECOND, calTime.get(Calendar.SECOND));
                expiryDate = calDate.getTime();
                if (expiryDate.before(new Date())) {
                    JOptionPane.showMessageDialog(this, "Expiry date and time cannot be in the past.");
                    return;
                }
            }

            // 4️⃣ Create Alert object
            Alert alert = new Alert(title, message, type, division, district, address, createdBy, expiryDate);

            // 5️⃣ Insert alert using DAO and get alert ID
            AlertDAO dao = new AlertDAO();
            int alertId = dao.insertAlert(alert);

            if (alertId > 0) {
                // 6️⃣ Insert status rows for all admins
                AlertStatusDAO statusDAO = new AlertStatusDAO();
                statusDAO.insertStatusForAllAdmins(alertId);

                JOptionPane.showMessageDialog(this, "Alert sent successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to send alert!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        txtTitle.setText("");
        txtMessage.setText("");
        cmbAlertType.setSelectedIndex(0);
        cmbDivision.setSelectedIndex(0);
        cmbDistrict.setSelectedIndex(0);
        txtAddress.setText("");
        datePicker.setDate(new Date());
        chkNoExpiry.setSelected(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTitle = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMessage = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        cmbAlertType = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cmbDivision = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cmbDistrict = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        datePickerPanel = new javax.swing.JPanel();
        chkNoExpiry = new javax.swing.JCheckBox();
        jPanel8 = new javax.swing.JPanel();
        btnClear = new javax.swing.JButton();
        btnSend = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Disaster Alert");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 20)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/warningicon.png"))); // NOI18N
        jLabel7.setText("Create Disaster Alert");

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Send emergency alert to specific location");
        jLabel8.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(102, 102, 102)));
        jLabel8.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        jPanel2.setLayout(new java.awt.CardLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(242, 242, 242), 3, true));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/alertinfoicon.png"))); // NOI18N
        jLabel9.setText("Alert Information");
        jLabel9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel1.setText("Title");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel2.setText("Message");

        txtTitle.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N

        txtMessage.setColumns(20);
        txtMessage.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        txtMessage.setRows(5);
        jScrollPane1.setViewportView(txtMessage);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel3.setText("Alert Type");

        cmbAlertType.setBackground(new java.awt.Color(242, 242, 242));
        cmbAlertType.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        cmbAlertType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Select Type---", "Emergency", "Warning", "Info", " " }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                            .addComponent(cmbAlertType, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTitle))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(txtTitle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbAlertType, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        jPanel2.add(jPanel3, "card2");

        jPanel4.setLayout(new java.awt.CardLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(242, 242, 242), 3, true));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/locationicon.png"))); // NOI18N
        jLabel4.setText("Location");
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));

        cmbDivision.setBackground(new java.awt.Color(242, 242, 242));
        cmbDivision.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        cmbDivision.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Select Division---", "Barishal", "Chattogram", "Dhaka", "Khulna", "Mymensingh", "Rajshahi", "Rangpur", "Sylhet" }));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel10.setText("Division");

        cmbDistrict.setBackground(new java.awt.Color(242, 242, 242));
        cmbDistrict.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        cmbDistrict.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Select District---", "Bagerhat", "Bandarban", "Barguna", "Barishal", "Bhola", "Bogura", "Brahmanbaria", "Chandpur", "Chapainawabganj", "Chattogram", "Chuadanga", "Cox’s Bazar", "Cumilla", "Dhaka", "Dinajpur", "Faridpur", "Feni", "Gaibandha", "Gazipur", "Gopalganj", "Habiganj", "Jamalpur", "Jashore", "Jhalokathi", "Jhenaidah", "Joypurhat", "Khagrachhari", "Khulna", "Kishoreganj", "Kurigram", "Kushtia", "Lakshmipur", "Lalmonirhat", "Madaripur", "Magura", "Manikganj", "Meherpur", "Moulvibazar", "Munshiganj", "Mymensingh", "Naogaon", "Narail", "Narayanganj", "Narsingdi", "Natore", "Netrokona", "Nilphamari", "Noakhali", "Pabna", "Panchagarh", "Patuakhali", "Pirojpur", "Rajbari", "Rajshahi", "Rangamati", "Rangpur", "Satkhira", "Shariatpur", "Sherpur", "Sirajganj", "Sunamganj", "Sylhet", "Tangail", "Thakurgaon" }));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel11.setText("District");

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel6.setText("Address");

        txtAddress.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbDivision, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(cmbDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbDivision, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(cmbDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        jPanel4.add(jPanel6, "card2");

        jPanel5.setLayout(new java.awt.CardLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(242, 242, 242), 3, true));

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/expiryicon.png"))); // NOI18N
        jLabel12.setText("Expiry");
        jLabel12.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Expiry Date");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel5.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        datePickerPanel.setBackground(new java.awt.Color(255, 255, 255));
        datePickerPanel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N

        javax.swing.GroupLayout datePickerPanelLayout = new javax.swing.GroupLayout(datePickerPanel);
        datePickerPanel.setLayout(datePickerPanelLayout);
        datePickerPanelLayout.setHorizontalGroup(
            datePickerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 625, Short.MAX_VALUE)
        );
        datePickerPanelLayout.setVerticalGroup(
            datePickerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        chkNoExpiry.setBackground(new java.awt.Color(255, 255, 255));
        chkNoExpiry.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        chkNoExpiry.setText("No expiry");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(datePickerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(chkNoExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(datePickerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(chkNoExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel7, "card2");

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        btnClear.setBackground(new java.awt.Color(108, 117, 125));
        btnClear.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Clear");
        btnClear.setBorder(null);
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnSend.setBackground(new java.awt.Color(230, 57, 70));
        btnSend.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        btnSend.setForeground(new java.awt.Color(255, 255, 255));
        btnSend.setText("Send Alert");
        btnSend.setBorder(null);
        btnSend.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 44, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 44, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnSend;
    private javax.swing.JCheckBox chkNoExpiry;
    private javax.swing.JComboBox<String> cmbAlertType;
    private javax.swing.JComboBox<String> cmbDistrict;
    private javax.swing.JComboBox<String> cmbDivision;
    private javax.swing.JPanel datePickerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextArea txtMessage;
    private javax.swing.JTextField txtTitle;
    // End of variables declaration//GEN-END:variables
}
