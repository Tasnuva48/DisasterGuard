/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.dg.adminform;

import com.dg.dbconnection.AdminData;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

public class AddressPanel extends javax.swing.JPanel {
    

    /**
     * Creates new form AddressPanel
     */
    private java.util.Map<String, String[]> districtMap;
    private Border normalTextFieldBorder;

    public AddressPanel() {
        initComponents();
        PresentAddressDistrict.setSelectedIndex(0);
        PermanentAddressDistrict.setSelectedIndex(0);

        normalTextFieldBorder = AdminOfficeBuildingNo.getBorder();
        setBackground(Color.WHITE);
        setOpaque(false);
        initDistrictData();
        setupDivisionListeners();
        addResetListener(AdminOfficeBuildingNo);
        addResetListener(AdminOfficeRoadNo);
        addResetListener(AdminOfficeArea);
        addResetListener(AdminOfficePostalCode);
        addResetListener(AdminOfficePhoneNumber);

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

        PresentAddressDivision.addActionListener(e -> {
            updateDistricts(
                    PresentAddressDivision,
                    PresentAddressDistrict
            );
        });

        PermanentAddressDivision.addActionListener(e -> {
            updateDistricts(
                    PermanentAddressDivision,
                    PermanentAddressDistrict
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = 25;
        int shadow = 8;

        // Shadow
        g2.setColor(new Color(0, 0, 0, 60));
        g2.fillRoundRect(shadow, shadow,
                getWidth() - shadow,
                getHeight() - shadow,
                arc, arc);

        // Background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0,
                getWidth() - shadow,
                getHeight() - shadow,
                arc, arc);

        g2.dispose();
    }
    // ============================
// Field validation function

    private void showError(String message, JTextField field) {
        JOptionPane.showMessageDialog(this, message);
        field.requestFocus();
        field.setBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED)
        );
    }

    private void resetBorder(JTextField field) {
        field.setBorder(normalTextFieldBorder);
    }

    private void addResetListener(JTextField field) {
        field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                resetBorder(field);
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                resetBorder(field);
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                resetBorder(field);
            }
        });
    }

    private boolean validateFields() {

        // Present Address
        if (PresentAddressDivision.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select Present Address Division"
            );
            PresentAddressDivision.requestFocus();
            return false;
        }

        if (PresentAddressDistrict.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select Present Address District"
            );
            PresentAddressDistrict.requestFocus();
            return false;
        }

        // Permanent Address
        if (PermanentAddressDivision.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select Permanent Address Division"
            );
            PermanentAddressDivision.requestFocus();
            return false;
        }

        if (PermanentAddressDistrict.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select Permanent Address District"
            );
            PermanentAddressDistrict.requestFocus();
            return false;
        }

        // Building / House No
        if (AdminOfficeBuildingNo.getText().trim().isEmpty()) {
            showError("Building / House No is required", AdminOfficeBuildingNo);
            return false;
        }

        // Road / Street No
        if (AdminOfficeRoadNo.getText().trim().isEmpty()) {
            showError("Road / Street No is required", AdminOfficeRoadNo);
            return false;
        }

        // Area / Locality
        if (AdminOfficeArea.getText().trim().isEmpty()) {
            showError("Area / Locality is required", AdminOfficeArea);
            return false;
        }

        // Postal Code (Bangladesh: 4 digits)
        String postalCode = AdminOfficePostalCode.getText().trim();
        if (postalCode.isEmpty()) {
            showError("Postal Code is required", AdminOfficePostalCode);
            return false;
        }
        if (!postalCode.matches("\\d{4}")) {
            showError("Postal Code must be exactly 4 digits", AdminOfficePostalCode);
            return false;
        }

        // Office Phone Number (Bangladesh)
        String phone = AdminOfficePhoneNumber.getText().trim();
        if (phone.isEmpty()) {
            showError("Office phone number is required", AdminOfficePhoneNumber);
            return false;
        }
        if (!phone.matches("01\\d{9}")) {
            showError("Enter a valid Bangladeshi phone number (01XXXXXXXXX)",
                    AdminOfficePhoneNumber);
            return false;
        }

        return true;
    }

// ============================
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        PresentAddressDivision = new javax.swing.JComboBox<>();
        PresentAddressDistrict = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        PermanentAddressDivision = new javax.swing.JComboBox<>();
        PermanentAddressDistrict = new javax.swing.JComboBox<>();
        Education = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        AdminOfficeBuildingNo = new javax.swing.JTextField();
        AdminOfficeRoadNo = new javax.swing.JTextField();
        AdminOfficeArea = new javax.swing.JTextField();
        AdminOfficePostalCode = new javax.swing.JTextField();
        AddressPanelPreviousButton = new javax.swing.JButton();
        AddressPanelNextButton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        AdminOfficeBuildingName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        AdminOfficePhoneNumber = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 153)));
        setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 20)); // NOI18N
        jLabel1.setText("Residential Address");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel2.setText("Present Address");

        PresentAddressDivision.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Select Division---", "Barishal", "Chattogram", "Dhaka", "Khulna", "Mymensingh", "Rajshahi", "Rangpur", "Sylhet" }));
        PresentAddressDivision.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 153)));

        PresentAddressDistrict.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Select District---", "Bagerhat", "Bandarban", "Barguna", "Barishal", "Bhola", "Bogura", "Brahmanbaria", "Chandpur", "Chapainawabganj", "Chattogram", "Chuadanga", "Cox’s Bazar", "Cumilla", "Dhaka", "Dinajpur", "Faridpur", "Feni", "Gaibandha", "Gazipur", "Gopalganj", "Habiganj", "Jamalpur", "Jashore", "Jhalokathi", "Jhenaidah", "Joypurhat", "Khagrachhari", "Khulna", "Kishoreganj", "Kurigram", "Kushtia", "Lakshmipur", "Lalmonirhat", "Madaripur", "Magura", "Manikganj", "Meherpur", "Moulvibazar", "Munshiganj", "Mymensingh", "Naogaon", "Narail", "Narayanganj", "Narsingdi", "Natore", "Netrokona", "Nilphamari", "Noakhali", "Pabna", "Panchagarh", "Patuakhali", "Pirojpur", "Rajbari", "Rajshahi", "Rangamati", "Rangpur", "Satkhira", "Shariatpur", "Sherpur", "Sirajganj", "Sunamganj", "Sylhet", "Tangail", "Thakurgaon" }));
        PresentAddressDistrict.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 153)));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel3.setText("Permanent Address");

        PermanentAddressDivision.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Select Division---", "Barishal", "Chattogram", "Dhaka", "Khulna", "Mymensingh", "Rajshahi", "Rangpur", "Sylhet" }));
        PermanentAddressDivision.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 153, 153)));

        PermanentAddressDistrict.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Select District---", "Bagerhat", "Bandarban", "Barguna", "Barishal", "Bhola", "Bogura", "Brahmanbaria", "Chandpur", "Chapainawabganj", "Chattogram", "Chuadanga", "Cox’s Bazar", "Cumilla", "Dhaka", "Dinajpur", "Faridpur", "Feni", "Gaibandha", "Gazipur", "Gopalganj", "Habiganj", "Jamalpur", "Jashore", "Jhalokathi", "Jhenaidah", "Joypurhat", "Khagrachhari", "Khulna", "Kishoreganj", "Kurigram", "Kushtia", "Lakshmipur", "Lalmonirhat", "Madaripur", "Magura", "Manikganj", "Meherpur", "Moulvibazar", "Munshiganj", "Mymensingh", "Naogaon", "Narail", "Narayanganj", "Narsingdi", "Natore", "Netrokona", "Nilphamari", "Noakhali", "Pabna", "Panchagarh", "Patuakhali", "Pirojpur", "Rajbari", "Rajshahi", "Rangamati", "Rangpur", "Satkhira", "Shariatpur", "Sherpur", "Sirajganj", "Sunamganj", "Sylhet", "Tangail", "Thakurgaon" }));
        PermanentAddressDistrict.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 153, 153)));

        Education.setFont(new java.awt.Font("Arial Black", 0, 20)); // NOI18N
        Education.setText("Office Address");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel4.setText("Building/House No.");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel5.setText("Road/Street No.");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel6.setText("Area/Locality");

        AdminOfficeBuildingNo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 153)));

        AdminOfficeRoadNo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 153)));

        AdminOfficeArea.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 153)));

        AdminOfficePostalCode.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 153)));

        AddressPanelPreviousButton.setBackground(new java.awt.Color(0, 153, 153));
        AddressPanelPreviousButton.setFont(new java.awt.Font("Arial Black", 0, 16)); // NOI18N
        AddressPanelPreviousButton.setForeground(new java.awt.Color(204, 255, 255));
        AddressPanelPreviousButton.setText("Previous");
        AddressPanelPreviousButton.addActionListener(this::AddressPanelPreviousButtonActionPerformed);

        AddressPanelNextButton.setBackground(new java.awt.Color(0, 153, 153));
        AddressPanelNextButton.setFont(new java.awt.Font("Arial Black", 0, 16)); // NOI18N
        AddressPanelNextButton.setForeground(new java.awt.Color(204, 255, 255));
        AddressPanelNextButton.setText("Next");
        AddressPanelNextButton.addActionListener(this::AddressPanelNextButtonActionPerformed);

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel9.setText("Postal Code");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel7.setText("Office/Building Name");

        AdminOfficeBuildingName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 153)));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel8.setText("Office Phone Number");

        AdminOfficePhoneNumber.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 153)));
        AdminOfficePhoneNumber.addActionListener(this::AdminOfficePhoneNumberActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(AddressPanelPreviousButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 460, Short.MAX_VALUE)
                        .addComponent(AddressPanelNextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(PermanentAddressDivision, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(PermanentAddressDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Education)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(34, 34, 34)
                                .addComponent(PresentAddressDivision, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(PresentAddressDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(AdminOfficeBuildingNo)
                                    .addComponent(AdminOfficeRoadNo)
                                    .addComponent(AdminOfficeArea)
                                    .addComponent(AdminOfficePostalCode, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                                    .addComponent(AdminOfficeBuildingName)
                                    .addComponent(AdminOfficePhoneNumber))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PresentAddressDivision, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PresentAddressDistrict, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PermanentAddressDivision, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PermanentAddressDistrict, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(Education)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(AdminOfficeBuildingNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(AdminOfficeRoadNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(AdminOfficeArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(AdminOfficePostalCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(AdminOfficeBuildingName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(AdminOfficePhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddressPanelPreviousButton)
                    .addComponent(AddressPanelNextButton))
                .addGap(32, 32, 32))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {PresentAddressDistrict, PresentAddressDivision, jLabel2});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {PermanentAddressDistrict, PermanentAddressDivision, jLabel3});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {AdminOfficeArea, AdminOfficeBuildingNo, AdminOfficePostalCode, AdminOfficeRoadNo, jLabel4, jLabel5, jLabel6, jLabel9});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {AddressPanelNextButton, AddressPanelPreviousButton});

    }// </editor-fold>//GEN-END:initComponents

    private void AddressPanelPreviousButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddressPanelPreviousButtonActionPerformed
        // TODO add your handling code here:
        MainAdminFrame frame = (MainAdminFrame) SwingUtilities.getWindowAncestor(this);
        frame.showCard("PERSONAL");
    }//GEN-LAST:event_AddressPanelPreviousButtonActionPerformed

    private void AddressPanelNextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddressPanelNextButtonActionPerformed
        // TODO add your handling code here:
        
        if (!validateFields()) {
            return;
        }

        MainAdminFrame frame = (MainAdminFrame) SwingUtilities.getWindowAncestor(this);
        AdminData admin=frame.getAdminData();
        admin.setPresentDivision(
        PresentAddressDivision.getSelectedItem().toString()
);

admin.setPresentDistrict(
        PresentAddressDistrict.getSelectedItem().toString()
);

/* ===============================
   PERMANENT ADDRESS
   =============================== */
admin.setPermanentDivision(
        PermanentAddressDivision.getSelectedItem().toString()
);

admin.setPermanentDistrict(
        PermanentAddressDistrict.getSelectedItem().toString()
);

/* ===============================
   OFFICE / ADDRESS DETAILS
   =============================== */
admin.setBuildingHouseNo(
        AdminOfficeBuildingNo.getText().trim()
);

admin.setRoadStreetNo(
        AdminOfficeRoadNo.getText().trim()
);

admin.setAreaLocality(
        AdminOfficeArea.getText().trim()
);

admin.setPostalCode(
        AdminOfficePostalCode.getText().trim()
);

/* ===============================
   OFFICE INFO
   =============================== */
admin.setOfficeBuildingName(
        AdminOfficeBuildingName.getText().trim()
);

admin.setOfficePhoneNumber(
        AdminOfficePhoneNumber.getText().trim()
);
        
        frame.showCard("QUALIFICATION");
    }//GEN-LAST:event_AddressPanelNextButtonActionPerformed

    private void AdminOfficePhoneNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminOfficePhoneNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AdminOfficePhoneNumberActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddressPanelNextButton;
    private javax.swing.JButton AddressPanelPreviousButton;
    private javax.swing.JTextField AdminOfficeArea;
    private javax.swing.JTextField AdminOfficeBuildingName;
    private javax.swing.JTextField AdminOfficeBuildingNo;
    private javax.swing.JTextField AdminOfficePhoneNumber;
    private javax.swing.JTextField AdminOfficePostalCode;
    private javax.swing.JTextField AdminOfficeRoadNo;
    private javax.swing.JLabel Education;
    private javax.swing.JComboBox<String> PermanentAddressDistrict;
    private javax.swing.JComboBox<String> PermanentAddressDivision;
    private javax.swing.JComboBox<String> PresentAddressDistrict;
    private javax.swing.JComboBox<String> PresentAddressDivision;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
