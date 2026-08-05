/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dg.admindashboard;

/**
 *
 * @author samih
 */
import com.dg.dbconnection.SQLiteConnect;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class AdminEditProfileFrame extends javax.swing.JInternalFrame {

    private JDesktopPane desktop;
    private java.util.Map<String, String[]> districtMap;

    /**
     * Creates new form AdminEditProfileFrame
     */
    private String oldUsername;

    public AdminEditProfileFrame(JDesktopPane desktop, String username) {
        initComponents();

        Color normalColor = new Color(97, 4, 95);     // merun
        Color hoverColor = new Color(122, 7, 77);      // lighter merun

        btnSave.setBackground(normalColor);

        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSave.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSave.setBackground(normalColor);
            }
        });

        getContentPane().setBackground(new java.awt.Color(255, 255, 255));
        // scrollbar invisible
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        // clean look
        jScrollPane1.setBorder(null);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(rbtnMale);
        genderGroup.add(rbtnFemale);
        genderGroup.add(rbtnOther);

        this.desktop = desktop;
        this.oldUsername = username; // STORE OLD USERNAME
        loadProfile(username);
        cmbMedicalTrainingOption.setEnabled(false);
        cmbLicenseOption.setEnabled(false);
        medicalTraining.addActionListener(e -> {
            cmbMedicalTrainingOption.setEnabled(medicalTraining.isSelected());
        });

        driving.addActionListener(e -> {
            cmbLicenseOption.setEnabled(driving.isSelected());
        });
        initDistrictData();       // Load division → district mapping
        setupDivisionListeners(); // Add listeners for dynamic district selection

        // Attach save button action
        btnSave.addActionListener(e -> saveProfile());
    }

    private void setSkillWithCombo(JCheckBox chk, JComboBox<String> combo, String dbValue) {

        if (dbValue == null || dbValue.equalsIgnoreCase("No")) {
            // Checkbox not checked
            chk.setSelected(false);
            combo.setEnabled(false);
            combo.setSelectedIndex(0); // reset to "---Select One---"
        } else {
            // Checkbox was previously checked in DB
            chk.setSelected(true);
            combo.setEnabled(true);

            // Only select dbValue if it exists in combo
            if (dbValue.trim().length() > 0 && combo.getItemCount() > 0) {
                combo.setSelectedItem(dbValue);
            } else {
                combo.setSelectedIndex(0); // fallback
            }
        }
    }

    private void initDistrictData() {
        districtMap = new java.util.HashMap<>();

        districtMap.put("Dhaka", new String[]{"Dhaka", "Gazipur", "Narayanganj", "Narsingdi",
            "Tangail", "Manikganj", "Munshiganj", "Faridpur", "Gopalganj",
            "Madaripur", "Rajbari", "Shariatpur", "Kishoreganj"});

        districtMap.put("Chattogram", new String[]{"Brahmanbaria", "Chattogram", "Cox’s Bazar", "Bandarban",
            "Khagrachhari", "Rangamati", "Cumilla", "Feni", "Noakhali", "Lakshmipur", "Chandpur"});

        districtMap.put("Rajshahi", new String[]{"Rajshahi", "Bogura", "Naogaon", "Natore",
            "Chapainawabganj", "Joypurhat", "Pabna", "Sirajganj"});

        districtMap.put("Khulna", new String[]{"Khulna", "Jashore", "Satkhira", "Bagerhat",
            "Narail", "Jhenaidah", "Chuadanga", "Meherpur", "Magura"});

        districtMap.put("Barishal", new String[]{"Barishal", "Bhola", "Patuakhali", "Barguna", "Pirojpur", "Jhalokathi"});

        districtMap.put("Sylhet", new String[]{"Sylhet", "Moulvibazar", "Habiganj", "Sunamganj"});

        districtMap.put("Rangpur", new String[]{"Rangpur", "Dinajpur", "Kurigram", "Gaibandha",
            "Nilphamari", "Lalmonirhat", "Panchagarh", "Thakurgaon"});

        districtMap.put("Mymensingh", new String[]{"Mymensingh", "Jamalpur", "Sherpur", "Netrokona"});
    }

    private void updateDistricts(JComboBox<String> divisionBox, JComboBox<String> districtBox) {
        String selectedDivision = (String) divisionBox.getSelectedItem();

        districtBox.removeAllItems();
        districtBox.addItem("---Select District---");

        if (districtMap.containsKey(selectedDivision)) {
            for (String district : districtMap.get(selectedDivision)) {
                districtBox.addItem(district);
            }
        }
    }

    private void setupDivisionListeners() {
        cmbPresentDivision.addActionListener(e -> updateDistricts(cmbPresentDivision, cmbPresentDistrict));
        cmbPermanentDivision.addActionListener(e -> updateDistricts(cmbPermanentDivision, cmbPermanentDistrict));
    }

    private void loadProfile(String username) {
        // Load admin info
        String sqlAdmin = "SELECT * FROM admin_info WHERE username = ?";

        try (Connection conn = SQLiteConnect.Connectordb(); PreparedStatement pstAdmin = conn.prepareStatement(sqlAdmin)) {

            pstAdmin.setString(1, username);

            try (ResultSet rs = pstAdmin.executeQuery()) {
                if (rs.next()) {
                    // Personal Info
                    txtName.setText(rs.getString("full_name"));
                    cmbBirthDate.setSelectedItem(String.valueOf(rs.getInt("birth_date")));
                    cmbBirthMonth.setSelectedItem(rs.getString("birth_month"));
                    cmbBirthYear.setSelectedItem(String.valueOf(rs.getInt("birth_year")));

                    String gender = rs.getString("gender");
                    if ("Male".equalsIgnoreCase(gender)) {
                        rbtnMale.setSelected(true);
                    } else if ("Female".equalsIgnoreCase(gender)) {
                        rbtnFemale.setSelected(true);
                    } else {
                        rbtnOther.setSelected(true);
                    }

                    txtNid.setText(rs.getString("nid"));
                    cmbBloodGroup.setSelectedItem(rs.getString("blood_group"));

                    // Contact Info
                    txtPhoneNo.setText(rs.getString("phone_number"));
                    txtEmail.setText(rs.getString("email"));
                    txtAlternativePhoneNumber.setText(rs.getString("alternative_phone_number"));
                    txtEmergencyContactNo.setText(rs.getString("emergency_contact_number"));

                    // Address Info
                    cmbPresentDivision.setSelectedItem(rs.getString("present_division"));
                    cmbPresentDistrict.setSelectedItem(rs.getString("present_district"));
                    cmbPermanentDivision.setSelectedItem(rs.getString("permanent_division"));
                    cmbPermanentDistrict.setSelectedItem(rs.getString("permanent_district"));

                    // Education & Profession
                    cmbHigherEducation.setSelectedItem(rs.getString("higher_education"));
                    txtMajorSubject.setText(rs.getString("major_subject_field"));
                    txtUniversity.setText(rs.getString("university_institution"));
                    txtOfficialDesgn.setText(rs.getString("official_designation"));
                    txtOrgDept.setText(rs.getString("organization_department"));
                    cmbOrgType.setSelectedItem(rs.getString("organization_type"));
                    cmbJoinDate.setSelectedItem(String.valueOf(rs.getInt("date_of_joining_day")));
                    cmdJoinMonth.setSelectedItem(rs.getString("date_of_joining_month"));
                    cmbJoinYear.setSelectedItem(String.valueOf(rs.getInt("date_of_joining_year")));

                    // Set username from admin_info
                    txtUsername.setText(rs.getString("username"));
                    // Load Skills

                    String medicalDB = rs.getString("medical_training");
                    String drivingDB = rs.getString("driving");

                    // Medical Training
                    if (medicalDB == null || medicalDB.equalsIgnoreCase("No")) {
                        medicalTraining.setSelected(false);
                        cmbMedicalTrainingOption.setEnabled(false);
                        cmbMedicalTrainingOption.setSelectedIndex(0); // "---Select One---"
                    } else {
                        medicalTraining.setSelected(true);
                        cmbMedicalTrainingOption.setEnabled(true);
                        cmbMedicalTrainingOption.setSelectedItem(medicalDB); // load from DB
                    }

                    // Add ItemListener to handle manual checking
                    medicalTraining.addItemListener(e -> {
                        if (medicalTraining.isSelected()) {
                            cmbMedicalTrainingOption.setEnabled(true);
                            if (cmbMedicalTrainingOption.getSelectedIndex() == 0) {
                                // keep "---Select One---" as default when checked manually
                                cmbMedicalTrainingOption.setSelectedIndex(0);
                            }
                        } else {
                            cmbMedicalTrainingOption.setEnabled(false);
                            cmbMedicalTrainingOption.setSelectedIndex(0);
                        }
                    });

                    // ===== Driving =====
                    if (drivingDB == null || drivingDB.equalsIgnoreCase("No")) {
                        driving.setSelected(false);
                        cmbLicenseOption.setEnabled(false);
                        cmbLicenseOption.setSelectedIndex(0); // "---Select One---"
                    } else {
                        driving.setSelected(true);
                        cmbLicenseOption.setEnabled(true);
                        cmbLicenseOption.setSelectedItem(drivingDB); // load from DB
                    }

                    // Add ItemListener to handle manual checking
                    driving.addItemListener(e -> {
                        if (driving.isSelected()) {
                            cmbLicenseOption.setEnabled(true);
                            if (cmbLicenseOption.getSelectedIndex() == 0) {
                                // keep "---Select One---" as default when checked manually
                                cmbLicenseOption.setSelectedIndex(0);
                            }
                        } else {
                            cmbLicenseOption.setEnabled(false);
                            cmbLicenseOption.setSelectedIndex(0);
                        }
                    });
                    if (rs.getString("swimming").equalsIgnoreCase("Yes")) {
                        swimming.setSelected(true);
                    } else {
                        swimming.setSelected(false);
                    }

                    // ===== Search & Rescue =====
                    if (rs.getString("search_and_rescue").equalsIgnoreCase("Yes")) {
                        searchAndRescue.setSelected(true);
                    } else {
                        searchAndRescue.setSelected(false);
                    }
                    // ===== Language Skills =====
                    String langDB = rs.getString("language_skills");
                    if (langDB.trim().equalsIgnoreCase("No")) {
                        languageSkills.setSelected(false);
                        banglaSkill.setEnabled(false);
                        englishSkill.setEnabled(false);
                        regionalLanguageSkill.setEnabled(false);

                        banglaSkill.setSelected(false);
                        englishSkill.setSelected(false);
                        regionalLanguageSkill.setSelected(false);
                    } else {
                        languageSkills.setSelected(true);

                        banglaSkill.setEnabled(true);
                        englishSkill.setEnabled(true);
                        regionalLanguageSkill.setEnabled(true);

                        String[] langs = langDB.split(",");
                        for (String l : langs) {
                            l = l.trim();
                            if (l.equalsIgnoreCase("Bangla")) {
                                banglaSkill.setSelected(true);
                            }
                            if (l.equalsIgnoreCase("English")) {
                                englishSkill.setSelected(true);
                            }
                            if (l.equalsIgnoreCase("Regional Language")) {
                                regionalLanguageSkill.setSelected(true);
                            }
                        }
                    }

// ===== Technical Skills =====
                    String techDB = rs.getString("technical_skills");
                    if (techDB.trim().equalsIgnoreCase("No")) {
                        technicalSkills.setSelected(false);
                        engineeringSkill.setEnabled(false);
                        ITSkill.setEnabled(false);
                        communicationsSkill.setEnabled(false);

                        engineeringSkill.setSelected(false);
                        ITSkill.setSelected(false);
                        communicationsSkill.setSelected(false);
                    } else {
                        technicalSkills.setSelected(true);

                        engineeringSkill.setEnabled(true);
                        ITSkill.setEnabled(true);
                        communicationsSkill.setEnabled(true);

                        String[] techs = techDB.split(",");
                        for (String t : techs) {
                            t = t.trim();
                            if (t.equalsIgnoreCase("Engineering")) {
                                engineeringSkill.setSelected(true);
                            }
                            if (t.equalsIgnoreCase("IT")) {
                                ITSkill.setSelected(true);
                            }
                            if (t.equalsIgnoreCase("Communications")) {
                                communicationsSkill.setSelected(true);
                            }
                        }
                    }
// ===== Disaster Management Training / Certification =====
                    FirstAidCertification.setSelected(rs.getString("first_aid_cpr_certified").equalsIgnoreCase("Yes"));
                    GISMapping.setSelected(rs.getString("gis_mapping_disaster_management").equalsIgnoreCase("Yes"));
                    communityDisasterPreparedness.setSelected(rs.getString("community_disaster_preparedness").equalsIgnoreCase("Yes"));
                    crisisManagementTraining.setSelected(rs.getString("crisis_management_training").equalsIgnoreCase("Yes"));
                    emergencyResponseTraining.setSelected(rs.getString("emergency_response_training").equalsIgnoreCase("Yes"));
                    volunteerCoordinationTraining.setSelected(rs.getString("volunteer_coordination_training").equalsIgnoreCase("Yes"));
                    DRRCertification.setSelected(rs.getString("disaster_risk_reduction_certification").equalsIgnoreCase("Yes"));

// No formal training
                    noTraining.setSelected(rs.getString("no_formal_training").equalsIgnoreCase("Yes"));
                    ItemListener uncheckNoTraining = e -> {
                        if (FirstAidCertification.isSelected() || GISMapping.isSelected()
                                || communityDisasterPreparedness.isSelected() || crisisManagementTraining.isSelected()
                                || emergencyResponseTraining.isSelected() || volunteerCoordinationTraining.isSelected()
                                || DRRCertification.isSelected()) {
                            noTraining.setSelected(false);
                        }
                    };

// Attach listener to all checkboxes except noTraining
                    FirstAidCertification.addItemListener(uncheckNoTraining);
                    GISMapping.addItemListener(uncheckNoTraining);
                    communityDisasterPreparedness.addItemListener(uncheckNoTraining);
                    crisisManagementTraining.addItemListener(uncheckNoTraining);
                    emergencyResponseTraining.addItemListener(uncheckNoTraining);
                    volunteerCoordinationTraining.addItemListener(uncheckNoTraining);
                    DRRCertification.addItemListener(uncheckNoTraining);
// ===== Types of Disasters Handled =====
                    boolean anyDisasterYes = false;

// Floods
                    if ("Yes".equalsIgnoreCase(rs.getString("floods"))) {
                        floods.setSelected(true);
                        anyDisasterYes = true;
                    } else {
                        floods.setSelected(false);
                    }

// Cyclones
                    if ("Yes".equalsIgnoreCase(rs.getString("cyclones"))) {
                        cyclones.setSelected(true);
                        anyDisasterYes = true;
                    } else {
                        cyclones.setSelected(false);
                    }

// Storms
                    if ("Yes".equalsIgnoreCase(rs.getString("storms"))) {
                        storms.setSelected(true);
                        anyDisasterYes = true;
                    } else {
                        storms.setSelected(false);
                    }

// Earthquakes
                    if ("Yes".equalsIgnoreCase(rs.getString("earthquakes"))) {
                        earthquakes.setSelected(true);
                        anyDisasterYes = true;
                    } else {
                        earthquakes.setSelected(false);
                    }

// Fires
                    if ("Yes".equalsIgnoreCase(rs.getString("fires"))) {
                        fires.setSelected(true);
                        anyDisasterYes = true;
                    } else {
                        fires.setSelected(false);
                    }

// Landslides
                    if ("Yes".equalsIgnoreCase(rs.getString("landslides"))) {
                        landslides.setSelected(true);
                        anyDisasterYes = true;
                    } else {
                        landslides.setSelected(false);
                    }

// River Erosion
                    if ("Yes".equalsIgnoreCase(rs.getString("river_erosion"))) {
                        riverErosion.setSelected(true);
                        anyDisasterYes = true;
                    } else {
                        riverErosion.setSelected(false);
                    }

// None Disaster
                    if ("Yes".equalsIgnoreCase(rs.getString("none_disaster"))) {
                        noneDisaster.setSelected(!anyDisasterYes); // only check if no other disaster is Yes
                    } else {
                        noneDisaster.setSelected(!anyDisasterYes); // ensure unchecked if any other Yes
                    }
                    ItemListener uncheckNoneDisaster = e -> {
                        if (floods.isSelected() || cyclones.isSelected() || storms.isSelected()
                                || earthquakes.isSelected() || fires.isSelected() || landslides.isSelected()
                                || riverErosion.isSelected()) {
                            noneDisaster.setSelected(false);
                        }
                    };

// Attach listener to all disaster checkboxes except noneDisaster
                    floods.addItemListener(uncheckNoneDisaster);
                    cyclones.addItemListener(uncheckNoneDisaster);
                    storms.addItemListener(uncheckNoneDisaster);
                    earthquakes.addItemListener(uncheckNoneDisaster);
                    fires.addItemListener(uncheckNoneDisaster);
                    landslides.addItemListener(uncheckNoneDisaster);
                    riverErosion.addItemListener(uncheckNoneDisaster);

                } else {
                    JOptionPane.showMessageDialog(this, "No profile found for this admin!");
                    return;
                }
            }

            // 2️⃣ Load password from users table
            String sqlUser = "SELECT password FROM users WHERE username = ? OR name = ?";
            try (PreparedStatement pstUser = conn.prepareStatement(sqlUser)) {
                pstUser.setString(1, username);
                pstUser.setString(2, txtName.getText()); // match by name just in case

                try (ResultSet rsUser = pstUser.executeQuery()) {
                    if (rsUser.next()) {
                        txtPassword.setText(rsUser.getString("password"));
                    } else {
                        txtPassword.setText(""); // fallback if not found
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading profile: " + ex.getMessage());
        }
    }

    private boolean validateProfile() {
        // ===== Name =====
        String name = txtName.getText().trim();
        if (name.isEmpty() || !name.matches("[a-zA-Z\\s]{3,50}")) {
            JOptionPane.showMessageDialog(this, "Enter a valid name (letters and spaces only, 3-50 characters).");
            txtName.requestFocus();
            return false;
        }

        // ===== NID =====
        String nid = txtNid.getText().trim();
        if (!nid.matches("\\d{10,17}")) { // Bangladesh NID: 10-17 digits
            JOptionPane.showMessageDialog(this, "Enter a valid NID number (10-17 digits).");
            txtNid.requestFocus();
            return false;
        }

        // ===== Phone Numbers =====
        String phone = txtPhoneNo.getText().trim();
        if (!phone.matches("01\\d{8,11}")) { // Bangladesh mobile: starts with 01 and 11 digits total
            JOptionPane.showMessageDialog(this, "Enter a valid phone number (Bangladesh format, e.g., 017XXXXXXXX).");
            txtPhoneNo.requestFocus();
            return false;
        }

        String altPhone = txtAlternativePhoneNumber.getText().trim();
        if (!altPhone.isEmpty() && !altPhone.matches("01\\d{8,11}")) {
            JOptionPane.showMessageDialog(this, "Enter a valid alternative phone number or leave empty.");
            txtAlternativePhoneNumber.requestFocus();
            return false;
        }

        String emergencyPhone = txtEmergencyContactNo.getText().trim();
        if (!emergencyPhone.matches("01\\d{8,11}")) {
            JOptionPane.showMessageDialog(this, "Enter a valid emergency contact number (Bangladesh format).");
            txtEmergencyContactNo.requestFocus();
            return false;
        }

        // ===== Email =====
        String email = txtEmail.getText().trim();
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,6}$")) {
            JOptionPane.showMessageDialog(this, "Enter a valid email address.");
            txtEmail.requestFocus();
            return false;
        }

        // ===== Gender =====
        if (!rbtnMale.isSelected() && !rbtnFemale.isSelected() && !rbtnOther.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select a gender.");
            return false;
        }

        // ===== Username =====
        String username = txtUsername.getText().trim();
        if (username.isEmpty() || !username.matches("[a-zA-Z0-9._]{4,20}")) {
            JOptionPane.showMessageDialog(this, "Username must be 4-20 characters (letters, numbers, ., _).");
            txtUsername.requestFocus();
            return false;
        }

        // ===== Password =====
        String password = txtPassword.getText();
        if (password.isEmpty() || !password.matches("(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}")) {
            JOptionPane.showMessageDialog(this, "Password must be 6-20 characters with letters and numbers.");
            txtPassword.requestFocus();
            return false;
        }

        // ===== Major Subject =====
        if (txtMajorSubject.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your major subject/field.");
            txtMajorSubject.requestFocus();
            return false;
        }

        // ===== University =====
        if (txtUniversity.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your university/institution.");
            txtUniversity.requestFocus();
            return false;
        }

        // ===== Official Designation & Org Dept =====
        if (txtOfficialDesgn.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Official designation cannot be empty.");
            txtOfficialDesgn.requestFocus();
            return false;
        }
        if (txtOrgDept.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Organization department cannot be empty.");
            txtOrgDept.requestFocus();
            return false;
        }

        // ===== Dropdowns =====
        if (cmbBirthDate.getSelectedItem() == null
                || cmbBirthMonth.getSelectedItem() == null
                || cmbBirthYear.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select your complete date of birth.");
            return false;
        }

        if (cmbPresentDivision.getSelectedItem() == null || cmbPresentDistrict.getSelectedItem() == null
                || cmbPermanentDivision.getSelectedItem() == null || cmbPermanentDistrict.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select complete present and permanent address.");
            return false;
        }

        if (cmbHigherEducation.getSelectedItem() == null || cmbOrgType.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select higher education and organization type.");
            return false;
        }

        if (cmbJoinDate.getSelectedItem() == null || cmdJoinMonth.getSelectedItem() == null || cmbJoinYear.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select complete joining date.");
            return false;
        }

        // ===== Blood Group =====
        if (cmbBloodGroup.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a blood group.");
            return false;
        }

        return true; // ✅ All validations passed
    }

    private void saveProfile() {
        if (!validateProfile()) {
            return; // stop save if validation fails
        }
        // Validate Medical Training
        if (medicalTraining.isSelected()) {
            if (cmbMedicalTrainingOption.getSelectedIndex() == 0
                    || "---Select One---".equals(cmbMedicalTrainingOption.getSelectedItem())) {
                JOptionPane.showMessageDialog(this,
                        "Please select medical training type");
                return; // stop save
            }
        }

// Validate Driving
        if (driving.isSelected()) {
            if (cmbLicenseOption.getSelectedIndex() == 0
                    || "---Select One---".equals(cmbLicenseOption.getSelectedItem())) {
                JOptionPane.showMessageDialog(this,
                        "Please select driving license type");
                return; // stop save
            }
        }
        if (languageSkills.isSelected()
                && !banglaSkill.isSelected()
                && !englishSkill.isSelected()
                && !regionalLanguageSkill.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select at least one language.");
            return;
        }

// 4️⃣ Validate technical skills
        if (technicalSkills.isSelected()
                && !engineeringSkill.isSelected()
                && !ITSkill.isSelected()
                && !communicationsSkill.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select at least one technical skill.");
            return;
        }

        Connection conn = null;

        try {

            // ================= GET GENDER =================
            String gender = "";
            if (rbtnMale.isSelected()) {
                gender = "Male";
            } else if (rbtnFemale.isSelected()) {
                gender = "Female";
            } else if (rbtnOther.isSelected()) {
                gender = "Other";
            }

            // ================= GET USERNAME =================
            String newUsername = txtUsername.getText();
            String password = txtPassword.getText();
            // ================= GET MEDICAL & DRIVING =================

            String medicalValue;
            if (medicalTraining.isSelected()) {
                medicalValue = cmbMedicalTrainingOption.getSelectedItem().toString();
            } else {
                medicalValue = "No";
            }

            String drivingValue;
            if (driving.isSelected()) {
                drivingValue = cmbLicenseOption.getSelectedItem().toString();
            } else {
                drivingValue = "No";
            }
// ===== Get Swimming & Search&Rescue =====
            String swimmingVal = swimming.isSelected() ? "Yes" : "No";
            String rescueVal = searchAndRescue.isSelected() ? "Yes" : "No";
// ===== Get Language Skills =====
            String languageDB = "No";
            if (languageSkills.isSelected()) {
                List<String> langList = new ArrayList<>();
                if (banglaSkill.isSelected()) {
                    langList.add("Bangla");
                }
                if (englishSkill.isSelected()) {
                    langList.add("English");
                }
                if (regionalLanguageSkill.isSelected()) {
                    langList.add("Regional Language");
                }

                if (!langList.isEmpty()) {
                    languageDB = String.join(",", langList);
                }
            }

// ===== Get Technical Skills =====
            String technicalDB = "No";
            if (technicalSkills.isSelected()) {
                List<String> techList = new ArrayList<>();
                if (engineeringSkill.isSelected()) {
                    techList.add("Engineering");
                }
                if (ITSkill.isSelected()) {
                    techList.add("IT");
                }
                if (communicationsSkill.isSelected()) {
                    techList.add("Communications");
                }

                if (!techList.isEmpty()) {
                    technicalDB = String.join(",", techList);
                }
            }
// ===== Disaster Management Training / Certification =====
            String firstAidVal = FirstAidCertification.isSelected() ? "Yes" : "No";
            String gisMappingVal = GISMapping.isSelected() ? "Yes" : "No";
            String communityDisasterVal = communityDisasterPreparedness.isSelected() ? "Yes" : "No";
            String crisisManagementVal = crisisManagementTraining.isSelected() ? "Yes" : "No";
            String emergencyResponseVal = emergencyResponseTraining.isSelected() ? "Yes" : "No";
            String volunteerCoordinationVal = volunteerCoordinationTraining.isSelected() ? "Yes" : "No";
            String drrCertVal = DRRCertification.isSelected() ? "Yes" : "No";

// noTraining is "Yes" only if all others are "No"
            String noTrainingVal = (!FirstAidCertification.isSelected() && !GISMapping.isSelected()
                    && !communityDisasterPreparedness.isSelected() && !crisisManagementTraining.isSelected()
                    && !emergencyResponseTraining.isSelected() && !volunteerCoordinationTraining.isSelected()
                    && !DRRCertification.isSelected()) ? "Yes" : "No";
// ===== Types of Disasters Handled =====
            String floodsVal = floods.isSelected() ? "Yes" : "No";
            String cyclonesVal = cyclones.isSelected() ? "Yes" : "No";
            String stormsVal = storms.isSelected() ? "Yes" : "No";
            String earthquakesVal = earthquakes.isSelected() ? "Yes" : "No";
            String firesVal = fires.isSelected() ? "Yes" : "No";
            String landslidesVal = landslides.isSelected() ? "Yes" : "No";
            String riverErosionVal = riverErosion.isSelected() ? "Yes" : "No";

// noneDisaster is Yes only if all others are No
            String noneDisasterVal = (!floods.isSelected() && !cyclones.isSelected() && !storms.isSelected()
                    && !earthquakes.isSelected() && !fires.isSelected() && !landslides.isSelected()
                    && !riverErosion.isSelected()) ? "Yes" : "No";

            // ================= SQL =================
            String sqlAdmin
                    = "UPDATE admin_info SET "
                    + "full_name = ?, "
                    + "birth_date = ?, "
                    + "birth_month = ?, "
                    + "birth_year = ?, "
                    + "gender = ?, "
                    + "nid = ?, "
                    + "blood_group = ?, "
                    + "phone_number = ?, "
                    + "email = ?, "
                    + "alternative_phone_number = ?, "
                    + "emergency_contact_number = ?, "
                    + "present_division = ?, "
                    + "present_district = ?, "
                    + "permanent_division = ?, "
                    + "permanent_district = ?, "
                    + "higher_education = ?, "
                    + "major_subject_field = ?, "
                    + "university_institution = ?, "
                    + "official_designation = ?, "
                    + "organization_department = ?, "
                    + "organization_type = ?, "
                    + "date_of_joining_day = ?, "
                    + "date_of_joining_month = ?, "
                    + "date_of_joining_year = ?, "
                    + "medical_training = ?,"
                    + "driving = ?,"
                    + "swimming = ?,"
                    + "search_and_rescue = ?,"
                    + "language_skills = ?, "
                    + // NEW
                    "technical_skills = ?, "
                    + "first_aid_cpr_certified = ?,"
                    + "gis_mapping_disaster_management = ?,"
                    + "community_disaster_preparedness = ?,"
                    + "crisis_management_training = ?,"
                    + "emergency_response_training = ?,"
                    + "volunteer_coordination_training = ?,"
                    + "disaster_risk_reduction_certification = ?,"
                    + "no_formal_training = ?,"
                    + "floods = ?, "
                    + "cyclones = ?, "
                    + "storms = ?, "
                    + "earthquakes = ?, "
                    + "fires = ?, "
                    + "landslides = ?, "
                    + "river_erosion = ?, "
                    + "none_disaster = ?, "
                    + "username = ? "
                    + "WHERE username = ?";

            String sqlUser
                    = "UPDATE users SET username = ?, password = ? WHERE username = ?";

            // ================= CONNECT =================
            conn = SQLiteConnect.Connectordb();
            conn.setAutoCommit(false); // Start transaction

            // ================= UPDATE ADMIN =================
            PreparedStatement pst1 = conn.prepareStatement(sqlAdmin);

            pst1.setString(1, txtName.getText());

            pst1.setInt(2, Integer.parseInt(cmbBirthDate.getSelectedItem().toString()));
            pst1.setString(3, cmbBirthMonth.getSelectedItem().toString());
            pst1.setInt(4, Integer.parseInt(cmbBirthYear.getSelectedItem().toString()));

            pst1.setString(5, gender);

            pst1.setString(6, txtNid.getText());
            pst1.setString(7, cmbBloodGroup.getSelectedItem().toString());

            pst1.setString(8, txtPhoneNo.getText());
            pst1.setString(9, txtEmail.getText());
            pst1.setString(10, txtAlternativePhoneNumber.getText());
            pst1.setString(11, txtEmergencyContactNo.getText());

            pst1.setString(12, cmbPresentDivision.getSelectedItem().toString());
            pst1.setString(13, cmbPresentDistrict.getSelectedItem().toString());

            pst1.setString(14, cmbPermanentDivision.getSelectedItem().toString());
            pst1.setString(15, cmbPermanentDistrict.getSelectedItem().toString());

            pst1.setString(16, cmbHigherEducation.getSelectedItem().toString());

            pst1.setString(17, txtMajorSubject.getText());
            pst1.setString(18, txtUniversity.getText());

            pst1.setString(19, txtOfficialDesgn.getText());
            pst1.setString(20, txtOrgDept.getText());

            pst1.setString(21, cmbOrgType.getSelectedItem().toString());

            pst1.setInt(22, Integer.parseInt(cmbJoinDate.getSelectedItem().toString()));
            pst1.setString(23, cmdJoinMonth.getSelectedItem().toString());
            pst1.setInt(24, Integer.parseInt(cmbJoinYear.getSelectedItem().toString()));

            pst1.setString(25, medicalValue);
            pst1.setString(26, drivingValue);
            pst1.setString(27, swimmingVal);
            pst1.setString(28, rescueVal);

            pst1.setString(29, languageDB);     // Language skills
            pst1.setString(30, technicalDB);    // Technical skills
            pst1.setString(31, firstAidVal);
            pst1.setString(32, gisMappingVal);
            pst1.setString(33, communityDisasterVal);
            pst1.setString(34, crisisManagementVal);
            pst1.setString(35, emergencyResponseVal);
            pst1.setString(36, volunteerCoordinationVal);
            pst1.setString(37, drrCertVal);
            pst1.setString(38, noTrainingVal);

            pst1.setString(39, floodsVal);
            pst1.setString(40, cyclonesVal);
            pst1.setString(41, stormsVal);
            pst1.setString(42, earthquakesVal);
            pst1.setString(43, firesVal);
            pst1.setString(44, landslidesVal);
            pst1.setString(45, riverErosionVal);
            pst1.setString(46, noneDisasterVal);

// username parameters
            pst1.setString(47, newUsername); // username
            pst1.setString(48, oldUsername); // WHERE clause
            int updated1 = pst1.executeUpdate();

            // ================= UPDATE USER TABLE =================
            PreparedStatement pst2 = conn.prepareStatement(sqlUser);

            pst2.setString(1, newUsername);
            pst2.setString(2, password);
            pst2.setString(3, oldUsername);

            int updated2 = pst2.executeUpdate();

            // ================= COMMIT =================
            conn.commit();

            // ================= UPDATE OLD USERNAME =================
            oldUsername = newUsername;
            if (updated1 > 0 && updated2 > 0) {
                JOptionPane.showMessageDialog(this, "Profile Updated Successfully!");
                // Dispose current edit frame
                this.dispose();

                // Open profile frame
                AdminProfileFrame profileFrame = new AdminProfileFrame(desktop, newUsername, true);
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

        } catch (Exception e) {

            // ================= ROLLBACK =================
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();

            JOptionPane.showMessageDialog(this,
                    "Error Updating Profile: " + e.getMessage());

        } finally {

            // ================= CLOSE =================
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

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

        jScrollPane1 = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbBirthDate = new javax.swing.JComboBox<>();
        cmbBirthMonth = new javax.swing.JComboBox<>();
        cmbBirthYear = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        rbtnMale = new javax.swing.JRadioButton();
        rbtnOther = new javax.swing.JRadioButton();
        rbtnFemale = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        txtNid = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbBloodGroup = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cmbPresentDivision = new javax.swing.JComboBox<>();
        cmbPresentDistrict = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        cmbPermanentDivision = new javax.swing.JComboBox<>();
        cmbPermanentDistrict = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtPhoneNo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtAlternativePhoneNumber = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtEmergencyContactNo = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cmbHigherEducation = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        txtMajorSubject = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtUniversity = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtOfficialDesgn = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtOrgDept = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        cmbOrgType = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        cmbJoinDate = new javax.swing.JComboBox<>();
        cmdJoinMonth = new javax.swing.JComboBox<>();
        cmbJoinYear = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        floods = new javax.swing.JCheckBox();
        cyclones = new javax.swing.JCheckBox();
        storms = new javax.swing.JCheckBox();
        earthquakes = new javax.swing.JCheckBox();
        fires = new javax.swing.JCheckBox();
        landslides = new javax.swing.JCheckBox();
        riverErosion = new javax.swing.JCheckBox();
        noneDisaster = new javax.swing.JCheckBox();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        emergencyResponseTraining = new javax.swing.JCheckBox();
        DRRCertification = new javax.swing.JCheckBox();
        FirstAidCertification = new javax.swing.JCheckBox();
        crisisManagementTraining = new javax.swing.JCheckBox();
        volunteerCoordinationTraining = new javax.swing.JCheckBox();
        GISMapping = new javax.swing.JCheckBox();
        communityDisasterPreparedness = new javax.swing.JCheckBox();
        noTraining = new javax.swing.JCheckBox();
        jLabel30 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        medicalTraining = new javax.swing.JCheckBox();
        searchAndRescue = new javax.swing.JCheckBox();
        driving = new javax.swing.JCheckBox();
        cmbLicenseOption = new javax.swing.JComboBox<>();
        languageSkills = new javax.swing.JCheckBox();
        banglaSkill = new javax.swing.JCheckBox();
        englishSkill = new javax.swing.JCheckBox();
        regionalLanguageSkill = new javax.swing.JCheckBox();
        technicalSkills = new javax.swing.JCheckBox();
        ITSkill = new javax.swing.JCheckBox();
        engineeringSkill = new javax.swing.JCheckBox();
        communicationsSkill = new javax.swing.JCheckBox();
        swimming = new javax.swing.JCheckBox();
        cmbMedicalTrainingOption = new javax.swing.JComboBox<>();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jScrollPane1.setViewportView(mainPanel);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 3, true));
        jPanel1.setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(97, 4, 95));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/personaldetailsicon.png"))); // NOI18N
        jLabel1.setText("Personal Info");

        txtName.setBackground(new java.awt.Color(242, 242, 242));
        txtName.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        txtName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(170, 7, 107), 1, true));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(170, 7, 107));
        jLabel2.setText("Name");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(170, 7, 107));
        jLabel3.setText("Date of Birth");

        cmbBirthDate.setBackground(new java.awt.Color(242, 242, 242));
        cmbBirthDate.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cmbBirthDate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        cmbBirthDate.setBorder(null);

        cmbBirthMonth.setBackground(new java.awt.Color(242, 242, 242));
        cmbBirthMonth.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cmbBirthMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May ", "June", "July ", "August", "September", "October", "November", "December" }));
        cmbBirthMonth.setBorder(null);

        cmbBirthYear.setBackground(new java.awt.Color(242, 242, 242));
        cmbBirthYear.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cmbBirthYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2025", "2024", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000", "1999", "1998", "1997", "1996", "1995", "1994", "1993", "1992", "1991", "1990", "1989", "1988", "1987", "1986", "1985", "1984", "1983", "1982", "1981", "1980", "1979", "1978", "1977", "1976", "1975", "1974", "1973", "1972", "1971" }));
        cmbBirthYear.setBorder(null);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(170, 7, 107));
        jLabel4.setText("Gender");

        rbtnMale.setBackground(new java.awt.Color(255, 255, 255));
        rbtnMale.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        rbtnMale.setText("Male");

        rbtnOther.setBackground(new java.awt.Color(255, 255, 255));
        rbtnOther.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        rbtnOther.setText("Other");

        rbtnFemale.setBackground(new java.awt.Color(255, 255, 255));
        rbtnFemale.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        rbtnFemale.setText("Female");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(170, 7, 107));
        jLabel5.setText("NID No.");

        txtNid.setBackground(new java.awt.Color(242, 242, 242));
        txtNid.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        txtNid.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(170, 7, 107), 1, true));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(170, 7, 107));
        jLabel6.setText("Blood Group");

        cmbBloodGroup.setBackground(new java.awt.Color(242, 242, 242));
        cmbBloodGroup.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cmbBloodGroup.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A+", "A-", "AB+", "AB-", "B+", "B-", "O+", "O-" }));
        cmbBloodGroup.setBorder(null);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbBloodGroup, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtName)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(cmbBirthDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbBirthMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbBirthYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(rbtnMale, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rbtnFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(rbtnOther, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNid)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbBirthDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbBirthMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbBirthYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnMale)
                    .addComponent(rbtnFemale)
                    .addComponent(rbtnOther))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbBloodGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jPanel1.add(jPanel2, "card2");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 3, true));
        jPanel3.setLayout(new java.awt.CardLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel4.setPreferredSize(new java.awt.Dimension(294, 151));

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(97, 4, 95));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/addressinfoicon.png"))); // NOI18N
        jLabel12.setText("Address Details");

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(170, 7, 107));
        jLabel13.setText("Present Address");

        cmbPresentDivision.setBackground(new java.awt.Color(242, 242, 242));
        cmbPresentDivision.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cmbPresentDivision.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Barishal", "Chattogram", "Dhaka", "Khulna", "Mymensingh", "Rajshahi", "Rangpur", "Sylhet" }));
        cmbPresentDivision.setBorder(null);

        cmbPresentDistrict.setBackground(new java.awt.Color(242, 242, 242));
        cmbPresentDistrict.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cmbPresentDistrict.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bagerhat", "Bandarban", "Barguna", "Barishal", "Bhola", "Bogura", "Brahmanbaria", "Chandpur", "Chapainawabganj", "Chattogram", "Chuadanga", "Cox’s Bazar", "Cumilla", "Dhaka", "Dinajpur", "Faridpur", "Feni", "Gaibandha", "Gazipur", "Gopalganj", "Habiganj", "Jamalpur", "Jashore", "Jhalokathi", "Jhenaidah", "Joypurhat", "Khagrachhari", "Khulna", "Kishoreganj", "Kurigram", "Kushtia", "Lakshmipur", "Lalmonirhat", "Madaripur", "Magura", "Manikganj", "Meherpur", "Moulvibazar", "Munshiganj", "Mymensingh", "Naogaon", "Narail", "Narayanganj", "Narsingdi", "Natore", "Netrokona", "Nilphamari", "Noakhali", "Pabna", "Panchagarh", "Patuakhali", "Pirojpur", "Rajbari", "Rajshahi", "Rangamati", "Rangpur", "Satkhira", "Shariatpur", "Sherpur", "Sirajganj", "Sunamganj", "Sylhet", "Tangail", "Thakurgaon" }));
        cmbPresentDistrict.setBorder(null);

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(170, 7, 107));
        jLabel14.setText("Permanent Address");

        cmbPermanentDivision.setBackground(new java.awt.Color(242, 242, 242));
        cmbPermanentDivision.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cmbPermanentDivision.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Barishal", "Chattogram", "Dhaka", "Khulna", "Mymensingh", "Rajshahi", "Rangpur", "Sylhet" }));
        cmbPermanentDivision.setBorder(null);

        cmbPermanentDistrict.setBackground(new java.awt.Color(242, 242, 242));
        cmbPermanentDistrict.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cmbPermanentDistrict.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bagerhat", "Bandarban", "Barguna", "Barishal", "Bhola", "Bogura", "Brahmanbaria", "Chandpur", "Chapainawabganj", "Chattogram", "Chuadanga", "Cox’s Bazar", "Cumilla", "Dhaka", "Dinajpur", "Faridpur", "Feni", "Gaibandha", "Gazipur", "Gopalganj", "Habiganj", "Jamalpur", "Jashore", "Jhalokathi", "Jhenaidah", "Joypurhat", "Khagrachhari", "Khulna", "Kishoreganj", "Kurigram", "Kushtia", "Lakshmipur", "Lalmonirhat", "Madaripur", "Magura", "Manikganj", "Meherpur", "Moulvibazar", "Munshiganj", "Mymensingh", "Naogaon", "Narail", "Narayanganj", "Narsingdi", "Natore", "Netrokona", "Nilphamari", "Noakhali", "Pabna", "Panchagarh", "Patuakhali", "Pirojpur", "Rajbari", "Rajshahi", "Rangamati", "Rangpur", "Satkhira", "Shariatpur", "Sherpur", "Sirajganj", "Sunamganj", "Sylhet", "Tangail", "Thakurgaon" }));
        cmbPermanentDistrict.setBorder(null);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cmbPresentDivision, 0, 121, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbPresentDistrict, 0, 155, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cmbPermanentDivision, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbPermanentDistrict, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbPresentDivision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPresentDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbPermanentDivision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPermanentDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        jPanel3.add(jPanel4, "card2");

        jPanel5.setLayout(new java.awt.CardLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 3, true));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(97, 4, 95));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/contactdetailsicon.png"))); // NOI18N
        jLabel7.setText("Contact Details");

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(170, 7, 107));
        jLabel8.setText("Phone Number");

        txtPhoneNo.setBackground(new java.awt.Color(242, 242, 242));
        txtPhoneNo.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        txtPhoneNo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(170, 7, 107), 1, true));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(170, 7, 107));
        jLabel9.setText("Email");

        txtEmail.setBackground(new java.awt.Color(242, 242, 242));
        txtEmail.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        txtEmail.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(170, 7, 107), 1, true));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(170, 7, 107));
        jLabel10.setText("Alternative Phone No.");

        txtAlternativePhoneNumber.setBackground(new java.awt.Color(242, 242, 242));
        txtAlternativePhoneNumber.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        txtAlternativePhoneNumber.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(170, 7, 107), 1, true));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(170, 7, 107));
        jLabel11.setText("Emergency Contact No.");

        txtEmergencyContactNo.setBackground(new java.awt.Color(242, 242, 242));
        txtEmergencyContactNo.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        txtEmergencyContactNo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(170, 7, 107), 1, true));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPhoneNo)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtEmail)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtAlternativePhoneNumber)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                    .addComponent(txtEmergencyContactNo))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPhoneNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAlternativePhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmergencyContactNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jPanel5.add(jPanel6, "card2");

        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 3, true));
        jPanel7.setPreferredSize(new java.awt.Dimension(300, 210));
        jPanel7.setLayout(new java.awt.CardLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(97, 4, 95));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/educationinfoicon.png"))); // NOI18N
        jLabel15.setText("Educational Info");

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(170, 7, 107));
        jLabel16.setText("Higher Education");

        cmbHigherEducation.setBackground(new java.awt.Color(242, 242, 242));
        cmbHigherEducation.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cmbHigherEducation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HSC/Equivalent", "Bachelor's Degree", "Master's Degree", "PhD/Doctoral Degree" }));
        cmbHigherEducation.setBorder(null);

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(170, 7, 107));
        jLabel17.setText("Major Subject/Field");

        txtMajorSubject.setBackground(new java.awt.Color(242, 242, 242));
        txtMajorSubject.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        txtMajorSubject.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(170, 7, 107), 1, true));

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(170, 7, 107));
        jLabel18.setText("University/Institution");

        txtUniversity.setBackground(new java.awt.Color(242, 242, 242));
        txtUniversity.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        txtUniversity.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(170, 7, 107), 1, true));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbHigherEducation, 0, 282, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMajorSubject)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUniversity))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbHigherEducation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMajorSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUniversity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jPanel7.add(jPanel8, "card2");

        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 3, true));
        jPanel9.setPreferredSize(new java.awt.Dimension(300, 250));
        jPanel9.setLayout(new java.awt.CardLayout());

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(97, 4, 95));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/professionalinfoicon.png"))); // NOI18N
        jLabel19.setText("Professional Details");

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(170, 7, 107));
        jLabel20.setText("Official Designation");

        txtOfficialDesgn.setBackground(new java.awt.Color(242, 242, 242));
        txtOfficialDesgn.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        txtOfficialDesgn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(170, 7, 107), 1, true));

        jLabel21.setBackground(new java.awt.Color(255, 255, 255));
        jLabel21.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(170, 7, 107));
        jLabel21.setText("Organization Department");

        txtOrgDept.setBackground(new java.awt.Color(242, 242, 242));
        txtOrgDept.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        txtOrgDept.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(170, 7, 107), 1, true));

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(170, 7, 107));
        jLabel22.setText("Organization Type");

        cmbOrgType.setBackground(new java.awt.Color(242, 242, 242));
        cmbOrgType.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cmbOrgType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Government", "Local Government", "NGO (National)", "NGO (International)", "Other" }));
        cmbOrgType.setBorder(null);

        jLabel23.setBackground(new java.awt.Color(255, 255, 255));
        jLabel23.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(170, 7, 107));
        jLabel23.setText("Joining Date");

        cmbJoinDate.setBackground(new java.awt.Color(242, 242, 242));
        cmbJoinDate.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cmbJoinDate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        cmbJoinDate.setBorder(null);

        cmdJoinMonth.setBackground(new java.awt.Color(242, 242, 242));
        cmdJoinMonth.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cmdJoinMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May ", "June", "July ", "August", "September", "October", "November", "December" }));
        cmdJoinMonth.setBorder(null);

        cmbJoinYear.setBackground(new java.awt.Color(242, 242, 242));
        cmbJoinYear.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cmbJoinYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2025", "2024", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000", "1999", "1998", "1997", "1996", "1995", "1994", "1993", "1992", "1991", "1990", "1989", "1988", "1987", "1986", "1985", "1984", "1983", "1982", "1981", "1980", "1979", "1978", "1977", "1976", "1975", "1974", "1973", "1972", "1971" }));
        cmbJoinYear.setBorder(null);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtOrgDept)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOfficialDesgn)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbOrgType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(cmbJoinDate, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdJoinMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbJoinYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOfficialDesgn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOrgDept, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbOrgType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbJoinDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdJoinMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbJoinYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        jPanel9.add(jPanel10, "card2");

        jPanel11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 3, true));
        jPanel11.setLayout(new java.awt.CardLayout());

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jLabel29.setBackground(new java.awt.Color(255, 255, 255));
        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(97, 4, 95));
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/disasterhandledicon.png"))); // NOI18N
        jLabel29.setText("Types of Disasters Handled");

        floods.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        floods.setText("Floods");

        cyclones.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        cyclones.setText("Cyclones");

        storms.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        storms.setText("Storms");

        earthquakes.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        earthquakes.setText("Earthquakes");

        fires.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        fires.setText("Fires");

        landslides.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        landslides.setText("Landslides");

        riverErosion.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        riverErosion.setText("River Erosion");

        noneDisaster.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        noneDisaster.setText("None");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cyclones, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(storms, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(earthquakes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(floods, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(riverErosion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(landslides, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fires, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(noneDisaster, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(floods)
                    .addComponent(fires))
                .addGap(4, 4, 4)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cyclones)
                    .addComponent(landslides))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(storms)
                    .addComponent(riverErosion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(earthquakes)
                    .addComponent(noneDisaster))
                .addGap(14, 14, 14))
        );

        jPanel11.add(jPanel12, "card2");

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 3, true));
        jPanel14.setPreferredSize(new java.awt.Dimension(617, 80));
        jPanel14.setLayout(new java.awt.CardLayout());

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setPreferredSize(new java.awt.Dimension(611, 80));

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(170, 7, 107));
        jLabel25.setText("Username");

        txtUsername.setBackground(new java.awt.Color(242, 242, 242));
        txtUsername.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        txtUsername.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(170, 7, 107), 1, true));

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(170, 7, 107));
        jLabel26.setText("Password");

        txtPassword.setBackground(new java.awt.Color(242, 242, 242));
        txtPassword.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        txtPassword.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(170, 7, 107), 1, true));

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(97, 4, 95));
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/systeminfoicon.png"))); // NOI18N
        jLabel24.setText("Admin's System Info");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(163, 163, 163))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPassword)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        jPanel14.add(jPanel15, "card2");

        jPanel19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 3, true));
        jPanel19.setLayout(new java.awt.CardLayout());

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        jLabel28.setBackground(new java.awt.Color(255, 255, 255));
        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(97, 4, 95));
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/certificationicon.png"))); // NOI18N
        jLabel28.setText("Disaster Management Training");

        emergencyResponseTraining.setBackground(new java.awt.Color(255, 255, 255));
        emergencyResponseTraining.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        emergencyResponseTraining.setText("Emergency Response Training");

        DRRCertification.setBackground(new java.awt.Color(255, 255, 255));
        DRRCertification.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        DRRCertification.setText("Disaster Risk Reduction Certification");

        FirstAidCertification.setBackground(new java.awt.Color(255, 255, 255));
        FirstAidCertification.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        FirstAidCertification.setText("First Aid & CPR Certified");

        crisisManagementTraining.setBackground(new java.awt.Color(255, 255, 255));
        crisisManagementTraining.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        crisisManagementTraining.setText("Crisis Management Training");

        volunteerCoordinationTraining.setBackground(new java.awt.Color(255, 255, 255));
        volunteerCoordinationTraining.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        volunteerCoordinationTraining.setText("Volunteer Coordination Training");

        GISMapping.setBackground(new java.awt.Color(255, 255, 255));
        GISMapping.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        GISMapping.setText("GIS/Mapping for Disaster Management");

        communityDisasterPreparedness.setBackground(new java.awt.Color(255, 255, 255));
        communityDisasterPreparedness.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        communityDisasterPreparedness.setText("Community Disaster Preparedness");

        noTraining.setBackground(new java.awt.Color(255, 255, 255));
        noTraining.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        noTraining.setText("No Formal Training");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DRRCertification, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(emergencyResponseTraining, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(FirstAidCertification, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(crisisManagementTraining, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(volunteerCoordinationTraining, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(communityDisasterPreparedness, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(noTraining, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(GISMapping)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emergencyResponseTraining)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DRRCertification)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FirstAidCertification)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(crisisManagementTraining)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(volunteerCoordinationTraining)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GISMapping)
                .addGap(2, 2, 2)
                .addComponent(communityDisasterPreparedness)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(noTraining)
                .addContainerGap())
        );

        jPanel19.add(jPanel17, "card2");

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/editadminlogo.png"))); // NOI18N

        btnSave.setBackground(new java.awt.Color(102, 0, 102));
        btnSave.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save Changes");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(this::btnSaveActionPerformed);

        jPanel13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 3, true));
        jPanel13.setLayout(new java.awt.CardLayout());

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        jLabel27.setBackground(new java.awt.Color(255, 255, 255));
        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(97, 4, 95));
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/skillsicon.png"))); // NOI18N
        jLabel27.setText("Admin's Skills");

        medicalTraining.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        medicalTraining.setText("Medical Training");

        searchAndRescue.setBackground(new java.awt.Color(255, 255, 255));
        searchAndRescue.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        searchAndRescue.setText("Search & Rescue");

        driving.setBackground(new java.awt.Color(255, 255, 255));
        driving.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        driving.setText("Driving");

        cmbLicenseOption.setBackground(new java.awt.Color(242, 242, 242));
        cmbLicenseOption.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        cmbLicenseOption.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Select one---", "Car", "Motorcycle", "Bicycle", "Truck" }));
        cmbLicenseOption.setBorder(null);

        languageSkills.setBackground(new java.awt.Color(255, 255, 255));
        languageSkills.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        languageSkills.setText("Language Skills");

        banglaSkill.setBackground(new java.awt.Color(255, 255, 255));
        banglaSkill.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        banglaSkill.setText("Bangla");

        englishSkill.setBackground(new java.awt.Color(255, 255, 255));
        englishSkill.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        englishSkill.setText("English");

        regionalLanguageSkill.setBackground(new java.awt.Color(255, 255, 255));
        regionalLanguageSkill.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        regionalLanguageSkill.setText("Regional Languages");

        technicalSkills.setBackground(new java.awt.Color(255, 255, 255));
        technicalSkills.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        technicalSkills.setText("Technical Skills");

        ITSkill.setBackground(new java.awt.Color(255, 255, 255));
        ITSkill.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        ITSkill.setText("IT");

        engineeringSkill.setBackground(new java.awt.Color(255, 255, 255));
        engineeringSkill.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        engineeringSkill.setText("Engineering");

        communicationsSkill.setBackground(new java.awt.Color(255, 255, 255));
        communicationsSkill.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        communicationsSkill.setText("Communications");

        swimming.setBackground(new java.awt.Color(255, 255, 255));
        swimming.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        swimming.setText("Swimming");
        swimming.addActionListener(this::swimmingActionPerformed);

        cmbMedicalTrainingOption.setBackground(new java.awt.Color(242, 242, 242));
        cmbMedicalTrainingOption.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        cmbMedicalTrainingOption.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Select one---", "Doctor", "Nurse", "First Aid Certified", "Paramedic" }));
        cmbMedicalTrainingOption.setBorder(null);
        cmbMedicalTrainingOption.addActionListener(this::cmbMedicalTrainingOptionActionPerformed);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(ITSkill)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(engineeringSkill)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(communicationsSkill))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(banglaSkill)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(englishSkill)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(regionalLanguageSkill))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(swimming, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(driving)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbLicenseOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(searchAndRescue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                            .addComponent(medicalTraining)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cmbMedicalTrainingOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(languageSkills, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(technicalSkills, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(medicalTraining)
                    .addComponent(cmbMedicalTrainingOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchAndRescue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(swimming)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(driving)
                    .addComponent(cmbLicenseOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(languageSkills)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(banglaSkill)
                    .addComponent(englishSkill)
                    .addComponent(regionalLanguageSkill, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(technicalSkills)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ITSkill)
                    .addComponent(engineeringSkill)
                    .addComponent(communicationsSkill))
                .addGap(18, 18, 18))
        );

        jPanel13.add(jPanel16, "card2");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(17, 17, 17)
                                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(btnSave)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jScrollPane1.setViewportView(mainPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveActionPerformed

    private void swimmingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_swimmingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_swimmingActionPerformed

    private void cmbMedicalTrainingOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMedicalTrainingOptionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbMedicalTrainingOptionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox DRRCertification;
    private javax.swing.JCheckBox FirstAidCertification;
    private javax.swing.JCheckBox GISMapping;
    private javax.swing.JCheckBox ITSkill;
    private javax.swing.JCheckBox banglaSkill;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cmbBirthDate;
    private javax.swing.JComboBox<String> cmbBirthMonth;
    private javax.swing.JComboBox<String> cmbBirthYear;
    private javax.swing.JComboBox<String> cmbBloodGroup;
    private javax.swing.JComboBox<String> cmbHigherEducation;
    private javax.swing.JComboBox<String> cmbJoinDate;
    private javax.swing.JComboBox<String> cmbJoinYear;
    private javax.swing.JComboBox<String> cmbLicenseOption;
    private javax.swing.JComboBox<String> cmbMedicalTrainingOption;
    private javax.swing.JComboBox<String> cmbOrgType;
    private javax.swing.JComboBox<String> cmbPermanentDistrict;
    private javax.swing.JComboBox<String> cmbPermanentDivision;
    private javax.swing.JComboBox<String> cmbPresentDistrict;
    private javax.swing.JComboBox<String> cmbPresentDivision;
    private javax.swing.JComboBox<String> cmdJoinMonth;
    private javax.swing.JCheckBox communicationsSkill;
    private javax.swing.JCheckBox communityDisasterPreparedness;
    private javax.swing.JCheckBox crisisManagementTraining;
    private javax.swing.JCheckBox cyclones;
    private javax.swing.JCheckBox driving;
    private javax.swing.JCheckBox earthquakes;
    private javax.swing.JCheckBox emergencyResponseTraining;
    private javax.swing.JCheckBox engineeringSkill;
    private javax.swing.JCheckBox englishSkill;
    private javax.swing.JCheckBox fires;
    private javax.swing.JCheckBox floods;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox landslides;
    private javax.swing.JCheckBox languageSkills;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JCheckBox medicalTraining;
    private javax.swing.JCheckBox noTraining;
    private javax.swing.JCheckBox noneDisaster;
    private javax.swing.JRadioButton rbtnFemale;
    private javax.swing.JRadioButton rbtnMale;
    private javax.swing.JRadioButton rbtnOther;
    private javax.swing.JCheckBox regionalLanguageSkill;
    private javax.swing.JCheckBox riverErosion;
    private javax.swing.JCheckBox searchAndRescue;
    private javax.swing.JCheckBox storms;
    private javax.swing.JCheckBox swimming;
    private javax.swing.JCheckBox technicalSkills;
    private javax.swing.JTextField txtAlternativePhoneNumber;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmergencyContactNo;
    private javax.swing.JTextField txtMajorSubject;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNid;
    private javax.swing.JTextField txtOfficialDesgn;
    private javax.swing.JTextField txtOrgDept;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtPhoneNo;
    private javax.swing.JTextField txtUniversity;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JCheckBox volunteerCoordinationTraining;
    // End of variables declaration//GEN-END:variables
}
