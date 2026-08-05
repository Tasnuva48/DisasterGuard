/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.dg.volunteerform;

import com.dg.dbconnection.VolunteerData;
import javax.swing.*;
import java.awt.*;
public class AddressPanel extends javax.swing.JPanel {

    /**
     * Creates new form AddressPanel
     */
    private java.util.Map<String, String[]> districtMap;

    public AddressPanel() {
        initComponents();
        setBackground(Color.WHITE);
        setOpaque(false);
        initDistrictData();
        setupDivisionListeners();

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
/*
    private boolean validateFields() {

        // Present Address
        if (PresentAddressDivision.getSelectedIndex() == 0
                || PresentAddressDistrict.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select Present Address (Division & District)");
            return false;
        }

        // Permanent Address
        if (PermanentAddressDivision.getSelectedIndex() == 0
                || PermanentAddressDistrict.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select Permanent Address (Division & District)");
            return false;
        }

        // School
        if (txtVolunteerSchool.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter School name");
            txtVolunteerSchool.requestFocus();
            return false;
        }

        // College
        if (txtVolunteerCollege.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter College name");
            txtVolunteerCollege.requestFocus();
            return false;
        }

        // University
        if (txtVolunteerUniversity.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter University name");
            txtVolunteerUniversity.requestFocus();
            return false;
        }

        // Profession
        if (txtVolunteerProfession.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter Profession");
            txtVolunteerProfession.requestFocus();
            return false;
        }

        return true;
    }
    */
    private void validateFields() throws ValidationException {

    // Present Address
    if (PresentAddressDivision.getSelectedIndex() == 0) {
        PresentAddressDivision.requestFocus();
        throw new ValidationException("Please select Present Division!");
    }

    if (PresentAddressDistrict.getSelectedIndex() == 0) {
        PresentAddressDistrict.requestFocus();
        throw new ValidationException("Please select Present District!");
    }

    // Permanent Address
    if (PermanentAddressDivision.getSelectedIndex() == 0) {
        PermanentAddressDivision.requestFocus();
        throw new ValidationException("Please select Permanent Division!");
    }

    if (PermanentAddressDistrict.getSelectedIndex() == 0) {
        PermanentAddressDistrict.requestFocus();
        throw new ValidationException("Please select Permanent District!");
    }

    // School
    if (txtVolunteerSchool.getText().trim().isEmpty()) {
        txtVolunteerSchool.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.RED));
        txtVolunteerSchool.requestFocus();
        throw new ValidationException("School name is required!");
    } else {
        txtVolunteerSchool.setBorder(BorderFactory.createMatteBorder(0,0,2,0,new Color(0,153,153)));
    }

    // College
    if (txtVolunteerCollege.getText().trim().isEmpty()) {
        txtVolunteerCollege.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.RED));
        txtVolunteerCollege.requestFocus();
        throw new ValidationException("College name is required!");
    } else {
        txtVolunteerCollege.setBorder(BorderFactory.createMatteBorder(0,0,2,0,new Color(0,153,153)));
    }

    // University
    if (txtVolunteerUniversity.getText().trim().isEmpty()) {
        txtVolunteerUniversity.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.RED));
        txtVolunteerUniversity.requestFocus();
        throw new ValidationException("University name is required!");
    } else {
        txtVolunteerUniversity.setBorder(BorderFactory.createMatteBorder(0,0,2,0,new Color(0,153,153)));
    }

    // Profession
    if (txtVolunteerProfession.getText().trim().isEmpty()) {
        txtVolunteerProfession.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.RED));
        txtVolunteerProfession.requestFocus();
        throw new ValidationException("Profession is required!");
    } else {
        txtVolunteerProfession.setBorder(BorderFactory.createMatteBorder(0,0,2,0,new Color(0,153,153)));
    }
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
        txtVolunteerSchool = new javax.swing.JTextField();
        txtVolunteerCollege = new javax.swing.JTextField();
        txtVolunteerUniversity = new javax.swing.JTextField();
        txtVolunteerProfession = new javax.swing.JTextField();
        AddressPanelPreviousButton = new javax.swing.JButton();
        AddressPanelNextButton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 153)));

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 20)); // NOI18N
        jLabel1.setText("Location Information");

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
        Education.setText("Qualification");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel4.setText("School");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel5.setText("College");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel6.setText("University");

        txtVolunteerSchool.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 153)));

        txtVolunteerCollege.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 153)));

        txtVolunteerUniversity.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 153)));

        txtVolunteerProfession.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 153)));

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
        jLabel9.setText("Profession");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtVolunteerSchool)
                            .addComponent(txtVolunteerCollege)
                            .addComponent(txtVolunteerUniversity)
                            .addComponent(txtVolunteerProfession, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(34, 34, 34)
                        .addComponent(PresentAddressDivision, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(PresentAddressDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(AddressPanelPreviousButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 460, Short.MAX_VALUE)
                        .addComponent(AddressPanelNextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(txtVolunteerSchool, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtVolunteerCollege, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtVolunteerUniversity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtVolunteerProfession, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddressPanelPreviousButton)
                    .addComponent(AddressPanelNextButton))
                .addGap(32, 32, 32))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {PresentAddressDistrict, PresentAddressDivision, jLabel2});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {PermanentAddressDistrict, PermanentAddressDivision, jLabel3});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel4, jLabel5, jLabel6, jLabel9, txtVolunteerCollege, txtVolunteerProfession, txtVolunteerSchool, txtVolunteerUniversity});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {AddressPanelNextButton, AddressPanelPreviousButton});

    }// </editor-fold>//GEN-END:initComponents

    private void AddressPanelPreviousButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddressPanelPreviousButtonActionPerformed
        // TODO add your handling code here:
        MainVolunteerFrame frame = (MainVolunteerFrame) SwingUtilities.getWindowAncestor(this);
        frame.showCard("PERSONAL");
    }//GEN-LAST:event_AddressPanelPreviousButtonActionPerformed

    private void AddressPanelNextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddressPanelNextButtonActionPerformed
        // TODO add your handling code here:
        
        try
        {
            validateFields();
        

        MainVolunteerFrame frame = (MainVolunteerFrame) SwingUtilities.getWindowAncestor(this);
        VolunteerData volunteer=frame.getVolunteerData();
        volunteer.setPresentDivision(PresentAddressDivision.getSelectedItem().toString());
    volunteer.setPresentDistrict(PresentAddressDistrict.getSelectedItem().toString());

    volunteer.setPermanentDivision(PermanentAddressDivision.getSelectedItem().toString());
    volunteer.setPermanentDistrict(PermanentAddressDistrict.getSelectedItem().toString());

    // 4️⃣ Set Education & Profession Information
    volunteer.setSchoolName(txtVolunteerSchool.getText().trim());
    volunteer.setCollegeName(txtVolunteerCollege.getText().trim());
    volunteer.setUniversityName(txtVolunteerUniversity.getText().trim());
    volunteer.setProfession(txtVolunteerProfession.getText().trim());
        
        frame.showCard("SKILLS");
        }
        catch (ValidationException e) {
    JOptionPane.showMessageDialog(this, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
}
    }//GEN-LAST:event_AddressPanelNextButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddressPanelNextButton;
    private javax.swing.JButton AddressPanelPreviousButton;
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
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtVolunteerCollege;
    private javax.swing.JTextField txtVolunteerProfession;
    private javax.swing.JTextField txtVolunteerSchool;
    private javax.swing.JTextField txtVolunteerUniversity;
    // End of variables declaration//GEN-END:variables
}
