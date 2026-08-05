/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dg.admindashboard;

/**
 *
 * @author samih
 */
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminProfileFrame extends javax.swing.JInternalFrame {

    private JDesktopPane desktop;

    /**
     * Creates new form AdminProfileFrame
     *
     * @param desktop
     */
    public AdminProfileFrame(JDesktopPane desktop, String username, boolean showEditButton) {
        initComponents();

        Color normalColor = new Color(97, 4, 95);     // merun
        Color hoverColor = new Color(122, 7, 77);      // lighter merun

        btnEdit.setBackground(normalColor);

        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEdit.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEdit.setBackground(normalColor);
            }
        });

        getContentPane().setBackground(new java.awt.Color(255, 255, 255));
        // scrollbar invisible
        //jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        //jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        // clean look
        //jScrollPane1.setBorder(null);
        this.desktop = desktop;
        lbUsername.setText(username);
        btnEdit.setVisible(showEditButton);
        //mainInfoPanel.setLayout(new BoxLayout(mainInfoPanel, BoxLayout.Y_AXIS));
        disastersManagementTrainingPanel.setLayout(
                new FlowLayout(FlowLayout.LEFT, 10, 10)
        );

        disastersHandledPanel.setLayout(
                new FlowLayout(FlowLayout.LEFT, 10, 10)
        );

        skillsPanel.setLayout(
                new FlowLayout(FlowLayout.LEFT, 10, 10)
        );

        // Add panels in the desired order
        /* mainInfoPanel.add(personalInfoCardPanel);
        mainInfoPanel.add(addressPanel);
        mainInfoPanel.add(educationalInfoCardPanel);
        mainInfoPanel.add(workCardPanel);                    // Work panel first
        mainInfoPanel.add(disasterManagementCertificatePanel); // Disaster Training below Work
        mainInfoPanel.add(jPanel2);       // Disasters handled panel
        mainInfoPanel.add(skillsCardPanel);                 // Skills panel
        mainInfoPanel.add(buttonPanel); */                // Button panel at bottom
        // Refresh layout
        mainInfoPanel.revalidate();
        mainInfoPanel.repaint();

        loadAdminProfile(username);
        SwingUtilities.invokeLater(() -> {
            mainInfoPanel.revalidate();
            mainInfoPanel.repaint();
        });
        btnEdit.addActionListener(e -> openEditFrame(username));
    }

    private void openEditFrame(String username) {

        this.dispose(); // close profile

        AdminEditProfileFrame edit
                = new AdminEditProfileFrame(desktop, username);

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

    private JLabel createBadge(String text) {

        JLabel badge = new JLabel(text);

        badge.setOpaque(true);
        badge.setBackground(new Color(33, 150, 243)); // Blue
        badge.setForeground(Color.WHITE);

        badge.setFont(new Font("Segoe UI", Font.BOLD, 12));

        badge.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(30, 136, 229)),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                )
        );

        return badge;
    }

    public void loadAdminProfile(String username) {

        String sql = "SELECT * FROM admin_info WHERE username = ?";

        try (Connection conn = com.dg.dbconnection.SQLiteConnect.Connectordb(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, username);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                // ================= PERSONAL INFO =================
                String name = rs.getString("full_name");

                int day = rs.getInt("birth_date");
                String month = rs.getString("birth_month");
                int year = rs.getInt("birth_year");

                String birthDate = day + " " + month + " " + year;

                String gender = rs.getString("gender");
                String nid = rs.getString("nid");
                String blood = rs.getString("blood_group");

                lbName.setText(name);
                lbBirthDate.setText(": " + birthDate);
                lbGender.setText(": " + gender);
                lbNid.setText(": " + nid);
                lbBloodGroup.setText(": " + blood);

                // ================= CONTACT INFO =================
                String phone = rs.getString("phone_number");
                String email = rs.getString("email");
                String altPhone = rs.getString("alternative_phone_number");
                String emergencyPhone = rs.getString("emergency_contact_number");

                lbPhoneNumber.setText(": " + phone);
                lbEmail.setText(": " + email);

                if (altPhone != null && !altPhone.isEmpty()) {
                    lbAlternativeNumber.setText(": " + altPhone);
                } else {
                    lbAlternativeNumber.setText(": N/A");
                }

                lbEmergencyContactNumber.setText(": " + emergencyPhone);

                // ================= ADDRESS INFO =================
                String presentDivision = rs.getString("present_division");
                String presentDistrict = rs.getString("present_district");

                String permanentDivision = rs.getString("permanent_division");
                String permanentDistrict = rs.getString("permanent_district");

                String presentAddress = formatAddress(
                        presentDistrict,
                        presentDivision
                );

                String permanentAddress = formatAddress(
                        permanentDistrict,
                        permanentDivision
                );

                lbPresentAddress.setText(presentAddress);
                lbPermanentAddress.setText(permanentAddress);
                // ================= EDUCATION INFO =================

                String education = rs.getString("higher_education");
                String major = rs.getString("major_subject_field");
                String university = rs.getString("university_institution");

                String eduText
                        = "<b>" + education + "</b> in " + major
                        + "<br>" + university;

                lbEducation.setText("<html>" + eduText + "</html>");
// ================= WORK INFO =================

                String designation = rs.getString("official_designation");
                String department = rs.getString("organization_department");
                String orgType = rs.getString("organization_type");

                int joinDay = rs.getInt("date_of_joining_day");
                String joinMonth = rs.getString("date_of_joining_month");
                int joinYear = rs.getInt("date_of_joining_year");

                String joiningDate = joinDay + " " + joinMonth + " " + joinYear;

// Format work text
                String workText
                        = "<b>" + designation + "</b> at " + department
                        + "<br>" + orgType
                        + "<br>Since: " + joiningDate;

                lbWork.setText("<html>" + workText + "</html>");
// ================= TRAINING BADGES =================
// Training badges
// Training badges
                String[][] trainings = {
                    {"emergency_response_training", "Emergency Response"},
                    {"disaster_risk_reduction_certification", "Disaster Risk Reduction"},
                    {"first_aid_cpr_certified", "First Aid & CPR"},
                    {"crisis_management_training", "Crisis Management"},
                    {"volunteer_coordination_training", "Volunteer Coordination"},
                    {"gis_mapping_disaster_management", "GIS Mapping"},
                    {"community_disaster_preparedness", "Community Preparedness"}
                };
                addBadges(rs, disastersManagementTrainingPanel, trainings, "No Formal Training");

// Disasters handled
                String[][] disasters = {
                    {"floods", "Floods"},
                    {"cyclones", "Cyclones"},
                    {"storms", "Storms"},
                    {"earthquakes", "Earthquakes"},
                    {"fires", "Fires"},
                    {"landslides", "Landslides"},
                    {"river_erosion", "River Erosion"}
                };
                addBadges(rs, disastersHandledPanel, disasters, "None");

// Skills
                loadSkills(rs);

            } else {
                JOptionPane.showMessageDialog(this, "Admin not found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading profile: " + e.getMessage());
        }
    }

    private String formatAddress(String district, String division) {

        if (district == null) {
            district = "";
        }
        if (division == null) {
            division = "";
        }

        district = district.trim();
        division = division.trim();

        // If same → show only once
        if (district.equalsIgnoreCase(division)) {

            return district + ", Bangladesh";

        } else {

            return district + ", " + division + ", Bangladesh";
        }
    }

    /**
     * Adds badges dynamically to a panel based on DB columns and labels.
     * Demonstrates polymorphism: same method handles both training and disaster
     * badges.
     */
    /**
     * Adds badges to a panel. Shows negative badge automatically if all columns
     * are "No" or empty.
     */
    private boolean addBadges(ResultSet rs, JPanel panel,
            String[][] items, String noDataMsg) {

        panel.removeAll();
        boolean added = false;

        try {

            for (String[] item : items) {

                String column = item[0];
                String label = item[1];

                String value = rs.getString(column);

                if (value != null
                        && !value.trim().isEmpty()
                        && !value.equalsIgnoreCase("No")) {

                    JLabel badge = createBadge(label);

                    panel.add(badge); // FlowLayout handles position
                    added = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If nothing found → show negative badge
        if (!added) {

            JLabel badge = createBadge(noDataMsg);
            panel.add(badge);
        }

        panel.revalidate();
        panel.repaint();

        return added;
    }

    private void loadSkills(ResultSet rs) {

        skillsPanel.removeAll();
        boolean hasAny = false;

        try {

            /* ===============================
           1) YES / NO SKILLS
        =============================== */
            String[][] yesNoSkills = {
                {"swimming", "Swimming"},
                {"driving", "Driving"},
                {"search_and_rescue", "Search & Rescue"}
            };

            for (String[] skill : yesNoSkills) {

                String column = skill[0];
                String label = skill[1];

                String value = rs.getString(column);

                if ("Yes".equalsIgnoreCase(value)) {

                    JLabel badge = createBadge(label);

                    skillsPanel.add(badge);
                    hasAny = true;
                }
            }


            /* ===============================
           2) MEDICAL / LANGUAGE / TECHNICAL
        =============================== */
            String[][] textSkills = {
                {"medical_training", "Medical"},
                {"language_skills", "Language"},
                {"technical_skills", "Technical"}
            };

            for (String[] skill : textSkills) {

                String column = skill[0];
                String prefix = skill[1];

                String value = rs.getString(column);

                if (value == null
                        || value.trim().isEmpty()
                        || value.equalsIgnoreCase("No")) {

                    continue;
                }

                String[] items = value.split(",");

                for (String s : items) {

                    String name = s.trim();

                    if (name.isEmpty()) {
                        continue;
                    }

                    String badgeText = prefix + ": " + name;

                    JLabel badge = createBadge(badgeText);

                    skillsPanel.add(badge);
                    hasAny = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        /* ===============================
       3) NO SKILLS AT ALL
    =============================== */
        if (!hasAny) {

            JLabel badge = createBadge("No Skills Available");
            skillsPanel.add(badge);
        }

        skillsPanel.revalidate();
        skillsPanel.repaint();
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
        mainInfoPanel = new javax.swing.JPanel();
        iconPanel = new javax.swing.JPanel();
        lbProfilePic = new javax.swing.JLabel();
        lbUsername = new javax.swing.JLabel();
        lbName = new javax.swing.JLabel();
        personalInfoCardPanel = new javax.swing.JPanel();
        personalInfoPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbBirthDate = new javax.swing.JLabel();
        lbGender = new javax.swing.JLabel();
        lbNid = new javax.swing.JLabel();
        lbBloodGroup = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        contactInfoCardPanel = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbPhoneNumber = new javax.swing.JLabel();
        lbEmail = new javax.swing.JLabel();
        lbAlternativeNumber = new javax.swing.JLabel();
        lbEmergencyContactNumber = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        addressPanel = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lbPresentAddress = new javax.swing.JLabel();
        lbPermanentAddress = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        educationalInfoCardPanel = new javax.swing.JPanel();
        educationPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lbEducation = new javax.swing.JLabel();
        workCardPanel = new javax.swing.JPanel();
        workPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lbWork = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        disastersHandledPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        disasterManagementCertificatianCardPanel = new javax.swing.JPanel();
        disasterManagementCertificatePanel = new javax.swing.JPanel();
        disastersManagementTrainingPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        skillsCardPanel = new javax.swing.JPanel();
        skillsPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));

        mainInfoPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainInfoPanel.setPreferredSize(new java.awt.Dimension(1050, 2200));
        mainInfoPanel.setRequestFocusEnabled(false);
        mainInfoPanel.setVerifyInputWhenFocusTarget(false);

        iconPanel.setBackground(new java.awt.Color(255, 255, 255));

        lbProfilePic.setBackground(new java.awt.Color(255, 255, 255));
        lbProfilePic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/adminicon.png"))); // NOI18N

        lbUsername.setBackground(new java.awt.Color(255, 255, 255));
        lbUsername.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbUsername.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 0, 153), new java.awt.Color(255, 102, 255)));
        lbUsername.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lbName.setBackground(new java.awt.Color(255, 255, 255));
        lbName.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        lbName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbName.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbName.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbName.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout iconPanelLayout = new javax.swing.GroupLayout(iconPanel);
        iconPanel.setLayout(iconPanelLayout);
        iconPanelLayout.setHorizontalGroup(
            iconPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(iconPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbProfilePic, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(iconPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        iconPanelLayout.setVerticalGroup(
            iconPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(iconPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(iconPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbProfilePic)
                    .addGroup(iconPanelLayout.createSequentialGroup()
                        .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        personalInfoCardPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 3, true));
        personalInfoCardPanel.setPreferredSize(new java.awt.Dimension(300, 212));
        personalInfoCardPanel.setLayout(new java.awt.CardLayout());

        personalInfoPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/personaldetailsicon.png"))); // NOI18N
        jLabel1.setText("Personal Details");

        lbBirthDate.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N

        lbGender.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N

        lbNid.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N

        lbBloodGroup.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(170, 7, 107));
        jLabel3.setText("Blood Group");

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(170, 7, 107));
        jLabel9.setText("NID No.");

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(170, 7, 107));
        jLabel11.setText("Gender");

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(170, 7, 107));
        jLabel12.setText("Birth Date");

        javax.swing.GroupLayout personalInfoPanelLayout = new javax.swing.GroupLayout(personalInfoPanel);
        personalInfoPanel.setLayout(personalInfoPanelLayout);
        personalInfoPanelLayout.setHorizontalGroup(
            personalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personalInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(personalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                    .addGroup(personalInfoPanelLayout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbBirthDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(personalInfoPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbBloodGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(personalInfoPanelLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbGender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(personalInfoPanelLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(lbNid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        personalInfoPanelLayout.setVerticalGroup(
            personalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personalInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbBirthDate, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(lbGender, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(lbNid, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(lbBloodGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        personalInfoCardPanel.add(personalInfoPanel, "card2");

        contactInfoCardPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 3, true));
        contactInfoCardPanel.setPreferredSize(new java.awt.Dimension(300, 212));
        contactInfoCardPanel.setLayout(new java.awt.CardLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setPreferredSize(new java.awt.Dimension(300, 212));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/contactdetailsicon.png"))); // NOI18N
        jLabel2.setText("Contact Details");

        lbPhoneNumber.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N

        lbEmail.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N

        lbAlternativeNumber.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N

        lbEmergencyContactNumber.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(170, 7, 107));
        jLabel13.setText("Phone No.");

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(170, 7, 107));
        jLabel14.setText("Email");

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(170, 7, 107));
        jLabel15.setText("Altenative Phone No.");

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(170, 7, 107));
        jLabel16.setText("Emergency Phone No.");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbAlternativeNumber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbPhoneNumber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbEmergencyContactNumber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbAlternativeNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbEmergencyContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addContainerGap())
        );

        contactInfoCardPanel.add(jPanel7, "card2");

        addressPanel.setBackground(new java.awt.Color(255, 255, 255));
        addressPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 3, true));
        addressPanel.setPreferredSize(new java.awt.Dimension(618, 150));
        addressPanel.setLayout(new java.awt.CardLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/addressinfoicon.png"))); // NOI18N
        jLabel4.setText("Address Details");
        jLabel4.setPreferredSize(new java.awt.Dimension(600, 25));

        lbPresentAddress.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        lbPresentAddress.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbPresentAddress.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(170, 7, 107), 1, true));
        lbPresentAddress.setPreferredSize(new java.awt.Dimension(600, 25));

        lbPermanentAddress.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        lbPermanentAddress.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbPermanentAddress.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(170, 7, 107), 1, true));
        lbPermanentAddress.setPreferredSize(new java.awt.Dimension(374, 25));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(170, 7, 107));
        jLabel17.setText("Permanent Address");

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(170, 7, 107));
        jLabel18.setText("Present Address");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbPermanentAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbPresentAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbPresentAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbPermanentAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        addressPanel.add(jPanel8, "card2");

        educationalInfoCardPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 3, true));
        educationalInfoCardPanel.setPreferredSize(new java.awt.Dimension(400, 212));
        educationalInfoCardPanel.setLayout(new java.awt.CardLayout());

        educationPanel.setBackground(new java.awt.Color(255, 255, 255));
        educationPanel.setPreferredSize(new java.awt.Dimension(1050, 200));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/educationinfoicon.png"))); // NOI18N
        jLabel5.setText("Educational Info");
        jLabel5.setPreferredSize(new java.awt.Dimension(382, 25));

        lbEducation.setBackground(new java.awt.Color(255, 255, 255));
        lbEducation.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        lbEducation.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbEducation.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lbEducation.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbEducation.setPreferredSize(new java.awt.Dimension(400, 212));
        lbEducation.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout educationPanelLayout = new javax.swing.GroupLayout(educationPanel);
        educationPanel.setLayout(educationPanelLayout);
        educationPanelLayout.setHorizontalGroup(
            educationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(educationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(educationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbEducation, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE))
                .addContainerGap())
        );
        educationPanelLayout.setVerticalGroup(
            educationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(educationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbEducation, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                .addContainerGap())
        );

        educationalInfoCardPanel.add(educationPanel, "card2");

        workCardPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 3, true));
        workCardPanel.setPreferredSize(new java.awt.Dimension(400, 150));
        workCardPanel.setLayout(new java.awt.CardLayout());

        workPanel.setBackground(new java.awt.Color(255, 255, 255));
        workPanel.setPreferredSize(new java.awt.Dimension(400, 150));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/professionalinfoicon.png"))); // NOI18N
        jLabel6.setText("Professional Details");
        jLabel6.setPreferredSize(new java.awt.Dimension(382, 25));

        lbWork.setBackground(new java.awt.Color(255, 255, 255));
        lbWork.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        lbWork.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbWork.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lbWork.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbWork.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout workPanelLayout = new javax.swing.GroupLayout(workPanel);
        workPanel.setLayout(workPanelLayout);
        workPanelLayout.setHorizontalGroup(
            workPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(workPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(workPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbWork, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        workPanelLayout.setVerticalGroup(
            workPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(workPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbWork, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        workCardPanel.add(workPanel, "card2");

        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 3, true));
        jPanel3.setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(1038, 111));

        disastersHandledPanel.setBackground(new java.awt.Color(255, 255, 255));
        disastersHandledPanel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        disastersHandledPanel.setPreferredSize(new java.awt.Dimension(1026, 68));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/disasterhandledicon.png"))); // NOI18N
        jLabel10.setText("Types Of Disaster Handled");
        jLabel10.setPreferredSize(new java.awt.Dimension(1026, 25));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(disastersHandledPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 816, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(disastersHandledPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        jPanel3.add(jPanel2, "card2");

        disasterManagementCertificatianCardPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 3, true));
        disasterManagementCertificatianCardPanel.setLayout(new java.awt.CardLayout());

        disasterManagementCertificatePanel.setBackground(new java.awt.Color(255, 255, 255));
        disasterManagementCertificatePanel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        disasterManagementCertificatePanel.setPreferredSize(new java.awt.Dimension(1038, 140));

        disastersManagementTrainingPanel.setBackground(new java.awt.Color(255, 255, 255));
        disastersManagementTrainingPanel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        disastersManagementTrainingPanel.setPreferredSize(new java.awt.Dimension(1026, 138));

        javax.swing.GroupLayout disastersManagementTrainingPanelLayout = new javax.swing.GroupLayout(disastersManagementTrainingPanel);
        disastersManagementTrainingPanel.setLayout(disastersManagementTrainingPanelLayout);
        disastersManagementTrainingPanelLayout.setHorizontalGroup(
            disastersManagementTrainingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 816, Short.MAX_VALUE)
        );
        disastersManagementTrainingPanelLayout.setVerticalGroup(
            disastersManagementTrainingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/certificationicon.png"))); // NOI18N
        jLabel7.setText("Disaster Management Training Certification");
        jLabel7.setPreferredSize(new java.awt.Dimension(1026, 25));

        javax.swing.GroupLayout disasterManagementCertificatePanelLayout = new javax.swing.GroupLayout(disasterManagementCertificatePanel);
        disasterManagementCertificatePanel.setLayout(disasterManagementCertificatePanelLayout);
        disasterManagementCertificatePanelLayout.setHorizontalGroup(
            disasterManagementCertificatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(disasterManagementCertificatePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(disasterManagementCertificatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(disastersManagementTrainingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 816, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        disasterManagementCertificatePanelLayout.setVerticalGroup(
            disasterManagementCertificatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, disasterManagementCertificatePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(disastersManagementTrainingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        disasterManagementCertificatianCardPanel.add(disasterManagementCertificatePanel, "card2");

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 3, true));
        jPanel1.setLayout(new java.awt.CardLayout());

        skillsCardPanel.setBackground(new java.awt.Color(255, 255, 255));

        skillsPanel.setBackground(new java.awt.Color(255, 255, 255));
        skillsPanel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        skillsPanel.setPreferredSize(new java.awt.Dimension(1036, 90));

        javax.swing.GroupLayout skillsPanelLayout = new javax.swing.GroupLayout(skillsPanel);
        skillsPanel.setLayout(skillsPanelLayout);
        skillsPanelLayout.setHorizontalGroup(
            skillsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 816, Short.MAX_VALUE)
        );
        skillsPanelLayout.setVerticalGroup(
            skillsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 111, Short.MAX_VALUE)
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/skillsicon.png"))); // NOI18N
        jLabel8.setText("Your Skills");
        jLabel8.setPreferredSize(new java.awt.Dimension(1026, 25));

        javax.swing.GroupLayout skillsCardPanelLayout = new javax.swing.GroupLayout(skillsCardPanel);
        skillsCardPanel.setLayout(skillsCardPanelLayout);
        skillsCardPanelLayout.setHorizontalGroup(
            skillsCardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(skillsCardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(skillsCardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(skillsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 816, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        skillsCardPanelLayout.setVerticalGroup(
            skillsCardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, skillsCardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(skillsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(skillsCardPanel, "card2");

        btnEdit.setBackground(new java.awt.Color(153, 0, 153));
        btnEdit.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("Edit Info");
        btnEdit.setBorder(null);
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout mainInfoPanelLayout = new javax.swing.GroupLayout(mainInfoPanel);
        mainInfoPanel.setLayout(mainInfoPanelLayout);
        mainInfoPanelLayout.setHorizontalGroup(
            mainInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainInfoPanelLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(mainInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainInfoPanelLayout.createSequentialGroup()
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82))
                    .addGroup(mainInfoPanelLayout.createSequentialGroup()
                        .addGroup(mainInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainInfoPanelLayout.createSequentialGroup()
                                .addGroup(mainInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(mainInfoPanelLayout.createSequentialGroup()
                                        .addComponent(personalInfoCardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(contactInfoCardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 834, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(disasterManagementCertificatianCardPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 834, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 834, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(mainInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(educationalInfoCardPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                                    .addComponent(addressPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(workCardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                            .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(25, Short.MAX_VALUE))))
        );
        mainInfoPanelLayout.setVerticalGroup(
            mainInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(mainInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(personalInfoCardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(contactInfoCardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(workCardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(mainInfoPanelLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(disasterManagementCertificatianCardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(addressPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(educationalInfoCardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 18, Short.MAX_VALUE)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1288, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainInfoPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addressPanel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JPanel contactInfoCardPanel;
    private javax.swing.JPanel disasterManagementCertificatePanel;
    private javax.swing.JPanel disasterManagementCertificatianCardPanel;
    private javax.swing.JPanel disastersHandledPanel;
    private javax.swing.JPanel disastersManagementTrainingPanel;
    private javax.swing.JPanel educationPanel;
    private javax.swing.JPanel educationalInfoCardPanel;
    private javax.swing.JPanel iconPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
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
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lbAlternativeNumber;
    private javax.swing.JLabel lbBirthDate;
    private javax.swing.JLabel lbBloodGroup;
    private javax.swing.JLabel lbEducation;
    private javax.swing.JLabel lbEmail;
    private javax.swing.JLabel lbEmergencyContactNumber;
    private javax.swing.JLabel lbGender;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbNid;
    private javax.swing.JLabel lbPermanentAddress;
    private javax.swing.JLabel lbPhoneNumber;
    private javax.swing.JLabel lbPresentAddress;
    private javax.swing.JLabel lbProfilePic;
    private javax.swing.JLabel lbUsername;
    private javax.swing.JLabel lbWork;
    private javax.swing.JPanel mainInfoPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel personalInfoCardPanel;
    private javax.swing.JPanel personalInfoPanel;
    private javax.swing.JPanel skillsCardPanel;
    private javax.swing.JPanel skillsPanel;
    private javax.swing.JPanel workCardPanel;
    private javax.swing.JPanel workPanel;
    // End of variables declaration//GEN-END:variables
}
