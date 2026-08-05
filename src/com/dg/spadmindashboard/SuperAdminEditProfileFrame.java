/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dg.spadmindashboard;

/**
 *
 * @author samih
 */
import com.dg.dbconnection.SQLiteConnect;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class SuperAdminEditProfileFrame extends javax.swing.JInternalFrame {
    private JDesktopPane desktop;

    /**
     * Creates new form SuperAdminProfileFrame
     */
    public SuperAdminEditProfileFrame(JDesktopPane desktop,String username) {
       initComponents();
        this.desktop=desktop;
        // Set BoxLayout for main panel
        //mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Set FlowLayout for badges panels
        
        // Set GridLayout for OrgInfoPanel and contactInfoPanel
        //OrgInfoPanel.setLayout(new GridLayout(5, 2, 10, 10));
        //contactInfoPanel.setLayout(new GridLayout(6, 2, 10, 10));
        // Load existing profile data
    loadProfile(username);

    // Attach save button action
    btnSave.addActionListener(e -> saveProfile(username));
        
        
        
    
    }
    private void loadProfile(String username) {
    // or fetch dynamically if needed
    String sql = "SELECT * FROM superadmin_info WHERE username = ?";

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, username);

        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                // Organization Info
                txtOrgName.setText(rs.getString("org_name"));
                txtRegNumber.setText(rs.getString("registration_number"));
                txtOrgType.setText(rs.getString("org_type"));
                txtEstYear.setText(String.valueOf(rs.getInt("established_year")));
                txtOrgAddress.setText(rs.getString("organization_address"));
                txtUsername.setText(rs.getString("username"));
                txtPassword.setText(rs.getString("password"));

                // Contact Info
                txtOffEmail.setText(rs.getString("official_email"));
                txtOffPhoneNumber.setText(rs.getString("official_phone_number"));
                txtContactPersonName.setText(rs.getString("contact_person_name"));
                txtConatctPersonDesgn.setText(rs.getString("contact_person_designation"));
                txtContactPersonPhone.setText(rs.getString("contact_person_phone"));
                txtContactPersonEmail.setText(rs.getString("contact_person_email"));
            } else {
                JOptionPane.showMessageDialog(this, "No profile found for this superadmin!");
            }
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading profile: " + ex.getMessage());
    }
}
    private void saveProfile(String username) {
    // Get all text field values
    String orgName = txtOrgName.getText().trim();
    String regNumber = txtRegNumber.getText().trim();
    String orgType = txtOrgType.getText().trim();
    String estYear = txtEstYear.getText().trim();
    String orgAddress = txtOrgAddress.getText().trim();
    String offEmail = txtOffEmail.getText().trim();
    String offPhone = txtOffPhoneNumber.getText().trim();
    String contactName = txtContactPersonName.getText().trim();
    String contactDesgn = txtConatctPersonDesgn.getText().trim();
    String contactPhone = txtContactPersonPhone.getText().trim();
    String contactEmail = txtContactPersonEmail.getText().trim();
    String user = txtUsername.getText().trim();
    String password = txtPassword.getText().trim();

    if(orgName.isEmpty() || user.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Organization Name and Username cannot be empty!");
        return;
    }

    String sql = "UPDATE superadmin_info SET " +
            "org_name = ?, " +
            "registration_number = ?, " +
            "org_type = ?, " +
            "established_year = ?, " +
            "organization_address = ?, " +
            "official_email = ?, " +
            "official_phone_number = ?, " +
            "contact_person_name = ?, " +
            "contact_person_designation = ?, " +
            "contact_person_phone = ?, " +
            "contact_person_email = ?, " +
            "username = ?, " +
            "password = ? " +
            "WHERE username = ?";

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, orgName);
        pst.setString(2, regNumber);
        pst.setString(3, orgType);
        pst.setInt(4, Integer.parseInt(estYear.isEmpty() ? "0" : estYear));
        pst.setString(5, orgAddress);
        pst.setString(6, offEmail);
        pst.setString(7, offPhone);
        pst.setString(8, contactName);
        pst.setString(9, contactDesgn);
        pst.setString(10, contactPhone);
        pst.setString(11, contactEmail);
        pst.setString(12, user);
        pst.setString(13, password);
        pst.setString(14, username); // WHERE clause

        int updated = pst.executeUpdate();
        if(updated > 0) {
            JOptionPane.showMessageDialog(this, "Profile updated successfully!");

            // ✅ After saving, automatically go back to Profile frame
            
                // Dispose current edit frame
                this.dispose();

                // Open profile frame
                SuperAdminProfileFrame profileFrame = new SuperAdminProfileFrame(desktop,user);
                desktop.add(profileFrame);

                int margin = 40;
                profileFrame.setBounds(
                    margin,
                    margin,
                    desktop.getWidth() - 2 * margin,
                    desktop.getHeight() - 2 * margin
                );
                profileFrame.setVisible(true);

                try {
                    profileFrame.setSelected(true);
                    profileFrame.setMaximum(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            

        } else {
            JOptionPane.showMessageDialog(this, "No record found to update.");
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }
}
    
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        buttonPanel = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        OrgInfoPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtOrgName = new javax.swing.JTextField();
        txtRegNumber = new javax.swing.JTextField();
        txtOrgType = new javax.swing.JTextField();
        txtEstYear = new javax.swing.JTextField();
        txtOrgAddress = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        contactInfoPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtOffEmail = new javax.swing.JTextField();
        txtOffPhoneNumber = new javax.swing.JTextField();
        txtContactPersonName = new javax.swing.JTextField();
        txtConatctPersonDesgn = new javax.swing.JTextField();
        txtContactPersonPhone = new javax.swing.JTextField();
        txtContactPersonEmail = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(1300, 750));

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setPreferredSize(new java.awt.Dimension(1300, 750));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 28)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 0, 204));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/EditSuperAdminDetailslogo.png"))); // NOI18N

        buttonPanel.setBackground(new java.awt.Color(255, 255, 255));

        btnSave.setBackground(new java.awt.Color(51, 0, 204));
        btnSave.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgsaveicon.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGap(587, 587, 587)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(btnSave)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        jPanel1.setLayout(new java.awt.CardLayout());

        OrgInfoPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 204));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgname.png"))); // NOI18N
        jLabel1.setText("Organization Name");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 204));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgreg.png"))); // NOI18N
        jLabel2.setText("Registration Number");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 204));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgtype.png"))); // NOI18N
        jLabel3.setText("Organization Type");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 204));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgestdyear.png"))); // NOI18N
        jLabel4.setText("Established Year");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 204));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgaddress.png"))); // NOI18N
        jLabel5.setText("Organization Address");

        txtOrgName.setBackground(new java.awt.Color(242, 242, 242));
        txtOrgName.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        txtOrgName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));
        txtOrgName.addActionListener(this::txtOrgNameActionPerformed);

        txtRegNumber.setBackground(new java.awt.Color(242, 242, 242));
        txtRegNumber.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        txtRegNumber.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 153, 255), 1, true));
        txtRegNumber.addActionListener(this::txtRegNumberActionPerformed);

        txtOrgType.setBackground(new java.awt.Color(242, 242, 242));
        txtOrgType.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        txtOrgType.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));
        txtOrgType.addActionListener(this::txtOrgTypeActionPerformed);

        txtEstYear.setBackground(new java.awt.Color(242, 242, 242));
        txtEstYear.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        txtEstYear.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));
        txtEstYear.addActionListener(this::txtEstYearActionPerformed);

        txtOrgAddress.setBackground(new java.awt.Color(242, 242, 242));
        txtOrgAddress.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        txtOrgAddress.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 204));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/username.png"))); // NOI18N
        jLabel12.setText("Username");

        txtUsername.setBackground(new java.awt.Color(242, 242, 242));
        txtUsername.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        txtUsername.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 51, 204));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgpass.png"))); // NOI18N
        jLabel13.setText("Password");

        txtPassword.setBackground(new java.awt.Color(242, 242, 242));
        txtPassword.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        txtPassword.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));
        txtPassword.addActionListener(this::txtPasswordActionPerformed);

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 102, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgbasicinfoicon.png"))); // NOI18N
        jLabel15.setText("Basic Info");
        jLabel15.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(102, 102, 102)));

        javax.swing.GroupLayout OrgInfoPanelLayout = new javax.swing.GroupLayout(OrgInfoPanel);
        OrgInfoPanel.setLayout(OrgInfoPanelLayout);
        OrgInfoPanelLayout.setHorizontalGroup(
            OrgInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OrgInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OrgInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOrgAddress, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtRegNumber)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOrgName, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUsername)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, OrgInfoPanelLayout.createSequentialGroup()
                        .addGroup(OrgInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtOrgType, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(22, 22, 22)
                        .addGroup(OrgInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEstYear, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(12, 12, 12))
        );
        OrgInfoPanelLayout.setVerticalGroup(
            OrgInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OrgInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOrgName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRegNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(OrgInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(OrgInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOrgType, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstYear, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOrgAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel1.add(OrgInfoPanel, "card2");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        jPanel2.setLayout(new java.awt.CardLayout());

        contactInfoPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 204));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgmail.png"))); // NOI18N
        jLabel6.setText("Official Email");

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 204));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgphn.png"))); // NOI18N
        jLabel7.setText("Official Phone Number");

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 204));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgperson.png"))); // NOI18N
        jLabel8.setText("Contact Person Name");
        jLabel8.setToolTipText("");

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 204));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgpersondesignation.png"))); // NOI18N
        jLabel9.setText("Contact Person Designation");

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 204));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgphn.png"))); // NOI18N
        jLabel10.setText("Contact Person Phone");

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 204));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgmail.png"))); // NOI18N
        jLabel11.setText("Contact Person Email");

        txtOffEmail.setBackground(new java.awt.Color(242, 242, 242));
        txtOffEmail.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        txtOffEmail.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));

        txtOffPhoneNumber.setBackground(new java.awt.Color(242, 242, 242));
        txtOffPhoneNumber.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        txtOffPhoneNumber.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));
        txtOffPhoneNumber.addActionListener(this::txtOffPhoneNumberActionPerformed);

        txtContactPersonName.setBackground(new java.awt.Color(242, 242, 242));
        txtContactPersonName.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        txtContactPersonName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));

        txtConatctPersonDesgn.setBackground(new java.awt.Color(242, 242, 242));
        txtConatctPersonDesgn.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        txtConatctPersonDesgn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));

        txtContactPersonPhone.setBackground(new java.awt.Color(242, 242, 242));
        txtContactPersonPhone.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        txtContactPersonPhone.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));

        txtContactPersonEmail.setBackground(new java.awt.Color(242, 242, 242));
        txtContactPersonEmail.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        txtContactPersonEmail.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 153, 255), 1, true));

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgcontact.png"))); // NOI18N
        jLabel16.setText("Contact Info");
        jLabel16.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(102, 102, 102)));

        javax.swing.GroupLayout contactInfoPanelLayout = new javax.swing.GroupLayout(contactInfoPanel);
        contactInfoPanel.setLayout(contactInfoPanelLayout);
        contactInfoPanelLayout.setHorizontalGroup(
            contactInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contactInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contactInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtContactPersonPhone, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtConatctPersonDesgn, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtContactPersonName, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOffPhoneNumber, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOffEmail, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                    .addComponent(txtContactPersonEmail))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        contactInfoPanelLayout.setVerticalGroup(
            contactInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contactInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOffEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOffPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtContactPersonName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtConatctPersonDesgn, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtContactPersonPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtContactPersonEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );

        jPanel2.add(contactInfoPanel, "card2");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap(20, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(500, 500, 500)
                        .addComponent(jLabel14)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtOrgNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOrgNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOrgNameActionPerformed

    private void txtOffPhoneNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOffPhoneNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOffPhoneNumberActionPerformed

    private void txtOrgTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOrgTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOrgTypeActionPerformed

    private void txtRegNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRegNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegNumberActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void txtEstYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstYearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstYearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel OrgInfoPanel;
    private javax.swing.JButton btnSave;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel contactInfoPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField txtConatctPersonDesgn;
    private javax.swing.JTextField txtContactPersonEmail;
    private javax.swing.JTextField txtContactPersonName;
    private javax.swing.JTextField txtContactPersonPhone;
    private javax.swing.JTextField txtEstYear;
    private javax.swing.JTextField txtOffEmail;
    private javax.swing.JTextField txtOffPhoneNumber;
    private javax.swing.JTextField txtOrgAddress;
    private javax.swing.JTextField txtOrgName;
    private javax.swing.JTextField txtOrgType;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtRegNumber;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
