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

public class SuperAdminProfileFrame extends javax.swing.JInternalFrame {

    private JDesktopPane desktop;
    private String username;

    /**
     * Creates new form SuperAdminProfileFrame
     */
    public SuperAdminProfileFrame(JDesktopPane desktop, String username) {
        initComponents();

        // scrollbar invisible
        jScrollPane2.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        jScrollPane2.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        // clean look
        jScrollPane2.setBorder(null);

        this.desktop = desktop;
        this.username = username;
        // Set BoxLayout for main panel
        //mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Set FlowLayout for badges panels
        trainingPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        disastersPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        // Set GridLayout for OrgInfoPanel and contactInfoPanel
        //OrgInfoPanel.setLayout(new GridLayout(5, 2, 10, 10));
        //contactInfoPanel.setLayout(new GridLayout(6, 2, 10, 10));
        loadProfileFromDB(username); // populate data
        btnEditProfile.addActionListener(e -> openEditFrame());

    }

    private void openEditFrame() {

        this.dispose(); // close profile

        SuperAdminEditProfileFrame edit
                = new SuperAdminEditProfileFrame(desktop, username);

        desktop.add(edit);

        int margin = 40;

        edit.setBounds(
                margin,
                margin,
                desktop.getWidth() - 2 * margin,
                desktop.getHeight() - 2 * margin
        );

        edit.setVisible(true);

        try {
            edit.setSelected(true);
            edit.setMaximum(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadProfileFromDB(String username) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = SQLiteConnect.Connectordb();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed!");
                return;
            }

            String sql = "SELECT * FROM superadmin_info WHERE username = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, username); // Make sure this matches DB

            rs = pst.executeQuery();

            if (rs.next()) {
                // Organization Info
                int id = rs.getInt("id");
                lbOrgName.setText(rs.getString("org_name"));
                lbRegNumber.setText(rs.getString("registration_number"));
                lbOrgType.setText(rs.getString("org_type"));
                ibEstYear.setText(String.valueOf(rs.getInt("established_year")));
                lbOrgAddress.setText(rs.getString("organization_address"));

                // Contact Info
                lbOffEmail.setText(rs.getString("official_email"));
                lbOffPhoneNumber.setText(rs.getString("official_phone_number"));
                lbContactPersonName.setText(rs.getString("contact_person_name"));
                lbContactPersonDesgn.setText(rs.getString("contact_person_designation"));
                lbContactPersonPhone.setText(rs.getString("contact_person_phone"));
                lbContactPersonEmail.setText(rs.getString("contact_person_email"));

                // Trainings
                trainingPanel.removeAll();
                addBadgeIfYes(trainingPanel, "Emergency Response Training", rs.getString("emergency_response_training"));
                addBadgeIfYes(trainingPanel, "Disaster Risk Reduction", rs.getString("disaster_risk_reduction_certification"));
                addBadgeIfYes(trainingPanel, "First Aid / CPR", rs.getString("first_aid_cpr_certified"));
                addBadgeIfYes(trainingPanel, "Crisis Management", rs.getString("crisis_management_training"));
                addBadgeIfYes(trainingPanel, "Volunteer Coordination", rs.getString("volunteer_coordination_training"));
                addBadgeIfYes(trainingPanel, "GIS Mapping", rs.getString("gis_mapping_disaster_management"));
                addBadgeIfYes(trainingPanel, "Community Preparedness", rs.getString("community_disaster_preparedness"));

                // Disasters handled
                disastersPanel.removeAll();
                boolean hasDisasters = false;
                String[] disasters = {"floods", "cyclones", "storms", "earthquakes", "fires", "landslides", "river_erosion"};
                for (String d : disasters) {
                    if (rs.getString(d).equalsIgnoreCase("Yes")) {
                        addSingleBadge(disastersPanel, capitalize(d), new Color(244, 67, 54));
                        hasDisasters = true;
                    }
                }
                if (!hasDisasters) {
                    addSingleBadge(disastersPanel, "No Disasters Handled", Color.GRAY);
                }

                trainingPanel.revalidate();
                trainingPanel.repaint();
                disastersPanel.revalidate();
                disastersPanel.repaint();

            } else {
                JOptionPane.showMessageDialog(this, "SuperAdmin not found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }
    }

    private void addBadgeIfYes(JPanel panel, String label, String value) {
        if (value != null && value.equalsIgnoreCase("Yes")) {
            addSingleBadge(panel, label, new Color(76, 175, 80));
        }
    }

    private void addSingleBadge(JPanel parentPanel, String text, Color badgeColor) {
        JPanel badge = new JPanel();
        badge.setBackground(badgeColor);
        badge.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        badge.setOpaque(true);
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        badge.add(lbl);
        parentPanel.add(badge);
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public javax.swing.JButton getBtnEditProfile() {
        return btnEditProfile;
    }

    public javax.swing.JPanel getBtnPanel() {
        return buttonPanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        buttonPanel = new javax.swing.JPanel();
        btnEditProfile = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        trainingPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        disastersPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        OrgInfoPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbOrgName = new javax.swing.JLabel();
        lbRegNumber = new javax.swing.JLabel();
        lbOrgType = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ibEstYear = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbOrgAddress = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        contactInfoPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lbOffEmail = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbOffPhoneNumber = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbContactPersonName = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbContactPersonDesgn = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbContactPersonPhone = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbContactPersonEmail = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(1300, 750));

        jScrollPane2.setPreferredSize(new java.awt.Dimension(912, 800));
        jScrollPane2.setViewportView(mainPanel);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setPreferredSize(new java.awt.Dimension(910, 750));

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/organizationaldestailslogo.png"))); // NOI18N
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        buttonPanel.setBackground(new java.awt.Color(255, 255, 255));

        btnEditProfile.setBackground(new java.awt.Color(51, 0, 204));
        btnEditProfile.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 16)); // NOI18N
        btnEditProfile.setForeground(new java.awt.Color(255, 255, 255));
        btnEditProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/edit-24.png"))); // NOI18N
        btnEditProfile.setText("Edit Profile");
        btnEditProfile.setBorderPainted(false);
        btnEditProfile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGap(579, 579, 579)
                .addComponent(btnEditProfile)
                .addContainerGap(558, Short.MAX_VALUE))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(btnEditProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 204, 204));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/certificationbages.png"))); // NOI18N

        trainingPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(trainingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(trainingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 0, 255));
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/disasterwerespondtologo.png"))); // NOI18N

        disastersPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(disastersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1068, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(disastersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 350));
        jPanel2.setLayout(new java.awt.CardLayout());

        OrgInfoPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 204));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgname.png"))); // NOI18N
        jLabel1.setText("Organization Name");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 204));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgreg.png"))); // NOI18N
        jLabel2.setText("Registration Number");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 204));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgtype.png"))); // NOI18N
        jLabel3.setText("Organization Type");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        lbOrgName.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        lbOrgName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));
        lbOrgName.setOpaque(true);

        lbRegNumber.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        lbRegNumber.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));
        lbRegNumber.setOpaque(true);

        lbOrgType.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        lbOrgType.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));
        lbOrgType.setOpaque(true);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 204));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgestdyear.png"))); // NOI18N
        jLabel4.setText("Established Year");

        ibEstYear.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        ibEstYear.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));
        ibEstYear.setOpaque(true);

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 204));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgaddress.png"))); // NOI18N
        jLabel5.setText("Organization Address");

        lbOrgAddress.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        lbOrgAddress.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));
        lbOrgAddress.setOpaque(true);

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 0, 204));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgbasicinfoicon.png"))); // NOI18N
        jLabel12.setText("Basic Info");
        jLabel12.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(102, 102, 102)));

        javax.swing.GroupLayout OrgInfoPanelLayout = new javax.swing.GroupLayout(OrgInfoPanel);
        OrgInfoPanel.setLayout(OrgInfoPanelLayout);
        OrgInfoPanelLayout.setHorizontalGroup(
            OrgInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OrgInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OrgInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbOrgName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbRegNumber, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbOrgType, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ibEstYear, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                    .addComponent(lbOrgAddress, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        OrgInfoPanelLayout.setVerticalGroup(
            OrgInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OrgInfoPanelLayout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbOrgName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbRegNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbOrgType, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ibEstYear, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbOrgAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.add(OrgInfoPanel, "card2");

        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        jPanel4.setPreferredSize(new java.awt.Dimension(600, 350));
        jPanel4.setLayout(new java.awt.CardLayout());

        contactInfoPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 204));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgmail.png"))); // NOI18N
        jLabel6.setText("Official Email");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        lbOffEmail.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        lbOffEmail.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));
        lbOffEmail.setOpaque(true);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 204));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgphn.png"))); // NOI18N
        jLabel7.setText("Official Phone Number");

        lbOffPhoneNumber.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        lbOffPhoneNumber.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));
        lbOffPhoneNumber.setOpaque(true);

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 204));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgperson.png"))); // NOI18N
        jLabel8.setText("Contact Person Name");

        lbContactPersonName.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        lbContactPersonName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));
        lbContactPersonName.setOpaque(true);

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 204));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgpersondesignation.png"))); // NOI18N
        jLabel9.setText("Contact Person Designation");

        lbContactPersonDesgn.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        lbContactPersonDesgn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));
        lbContactPersonDesgn.setOpaque(true);

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 204));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgphn.png"))); // NOI18N
        jLabel10.setText("Contact Person Phone");
        jLabel10.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel10.setPreferredSize(new java.awt.Dimension(582, 20));

        lbContactPersonPhone.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        lbContactPersonPhone.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));
        lbContactPersonPhone.setOpaque(true);
        lbContactPersonPhone.setPreferredSize(new java.awt.Dimension(582, 18));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 204));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgmail.png"))); // NOI18N
        jLabel11.setText("Contact Person Email");
        jLabel11.setPreferredSize(new java.awt.Dimension(582, 18));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 0, 204));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgcontact.png"))); // NOI18N
        jLabel13.setText("Contact Info");
        jLabel13.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(102, 102, 102)));

        lbContactPersonEmail.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        lbContactPersonEmail.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));
        lbContactPersonEmail.setOpaque(true);

        javax.swing.GroupLayout contactInfoPanelLayout = new javax.swing.GroupLayout(contactInfoPanel);
        contactInfoPanel.setLayout(contactInfoPanelLayout);
        contactInfoPanelLayout.setHorizontalGroup(
            contactInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contactInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contactInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contactInfoPanelLayout.createSequentialGroup()
                        .addGroup(contactInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbOffEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbOffPhoneNumber, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbContactPersonName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                            .addComponent(lbContactPersonDesgn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, contactInfoPanelLayout.createSequentialGroup()
                                .addComponent(lbContactPersonPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(lbContactPersonEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(contactInfoPanelLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        contactInfoPanelLayout.setVerticalGroup(
            contactInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contactInfoPanelLayout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbOffEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbOffPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbContactPersonName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbContactPersonDesgn, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contactInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contactInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbContactPersonPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbContactPersonEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61))
        );

        jPanel4.add(contactInfoPanel, "card2");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGap(515, 515, 515)
                .addComponent(jLabel14)
                .addGap(528, 528, 528))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(64, 64, 64)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPane2.setViewportView(mainPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1288, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 733, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel OrgInfoPanel;
    private javax.swing.JButton btnEditProfile;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel contactInfoPanel;
    private javax.swing.JPanel disastersPanel;
    private javax.swing.JLabel ibEstYear;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbContactPersonDesgn;
    private javax.swing.JLabel lbContactPersonEmail;
    private javax.swing.JLabel lbContactPersonName;
    private javax.swing.JLabel lbContactPersonPhone;
    private javax.swing.JLabel lbOffEmail;
    private javax.swing.JLabel lbOffPhoneNumber;
    private javax.swing.JLabel lbOrgAddress;
    private javax.swing.JLabel lbOrgName;
    private javax.swing.JLabel lbOrgType;
    private javax.swing.JLabel lbRegNumber;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel trainingPanel;
    // End of variables declaration//GEN-END:variables
}
