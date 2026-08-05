/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dg.volunteerdashboard;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import com.dg.dbconnection.*;
import java.util.List;
import com.dg.dao.VolunteerDAO;    // ADD THIS
import com.dg.dao.UserDAO;          // ADD THIS
import com.dg.model.Volunteer;
import static java.awt.SystemColor.text;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author USER
 */
public class VolunteerEditProfile extends javax.swing.JInternalFrame {

    private JDesktopPane desktop;
    private java.util.Map<String, String[]> districtMap;
    /**
     * Creates new form VolunteerEditProfile
     */
//    public VolunteerEditProfile() {
//        initComponents();
//    }
    private String oldUsername;

    public VolunteerEditProfile(JDesktopPane desktop, String username) {

        UIManager.put("ComboBox.background", Color.WHITE);
        UIManager.put("ComboBox.selectionBackground", new Color(67, 160, 71));
        UIManager.put("ComboBox.foreground", new Color(50, 50, 50));

        initComponents();
        //  jLabel1.setText("<html><u>Personal Details</u></html>");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 204)));
        Contact.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 204)));
        jLabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 204)));
        //jLabel5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 204)));
        //jLabel7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 204)));
        jLabel13.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 204)));
        jLabel14.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 204)));
        jLabel9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 204)));
//        jScrollPane1.setPreferredSize(new Dimension(900, 600)); // fixed visible area
//jScrollPane1.setMaximumSize(new Dimension(900, 600));   // prevent it from growing

        /*styleComboBox(CmbDate);
        styleComboBox(CmbMonth);
        styleComboBox(CmbYear);
        styleComboBox(CmbBloodGroup);
        styleComboBox(CmbPresentDivision);
        styleComboBox(CmbPresentDistrict);
        styleComboBox(CmbPermanentDivision);
        styleComboBox(CmbPermanentDistrict);
        styleComboBox(MedicalTrainingCmbBox);
        styleComboBox(DrivingCmbBox);
         */
        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);
        //mainPanel.setPreferredSize(new java.awt.Dimension(1076, 750));
        styleSaveButton();
        setWhiteBackground();
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(RbtnMale);
        genderGroup.add(RbtnFemale);
        genderGroup.add(RbtnOther);
//        // After initComponents();
//SwingUtilities.invokeLater(() -> {
//    int totalHeight = 200;
//  for (Component comp : mainPanel.getComponents()) {
//       totalHeight += comp.getPreferredSize().height + 10; // add some spacing
//  }
//    mainPanel.setPreferredSize(new Dimension(
//       mainPanel.getWidth(), // keep current width
//       totalHeight
//   ));
        ////    
//   jScrollPane1.revalidate();// update scroll pane
//   jScrollPane1.repaint();
//});
//jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);
       this.desktop = desktop;
        this.oldUsername = username; // STORE OLD USERNAME
        loadProfile(username);
        initDistrictData();       // Load division → district mapping
        setupDivisionListeners(); // Add listeners for dynamic district selection
//
//    // Attach save button action
        BtnSave.addActionListener(e -> saveProfile());
    }

    private void styleSaveButton() {
        BtnSave.setText("Save");
        BtnSave.setBackground(new Color(253, 126, 20));
        BtnSave.setForeground(Color.WHITE);
        BtnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        BtnSave.setFocusPainted(false);
        BtnSave.setBorderPainted(false);
        BtnSave.setContentAreaFilled(true);
        BtnSave.setOpaque(true);
        BtnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnSave.setPreferredSize(new java.awt.Dimension(160, 40));

        // Hover effect
        BtnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                BtnSave.setBackground(new Color(0, 80, 170));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                BtnSave.setBackground(new Color(253, 126, 20));
            }
        });
    }

    private void setWhiteBackground() {
        java.awt.Color white = new java.awt.Color(255, 255, 255);

        getContentPane().setBackground(white);

        // set all visible panels to white
        mainPanel.setBackground(white);

        MedicalTrainingCheckBox.setOpaque(true);
        SearchAndRescueCheckBoox.setOpaque(true);
        SwimmingCheckBox.setOpaque(true);
        DrivingCheckBox.setOpaque(true);
        LanguageSkillCheckBox.setOpaque(true);
        TechnicalSkillCheckBox.setOpaque(true);

        //gradientPanel.setBackground(white); // if exists
    }

    private void styleComboBox(JComboBox<String> box) {
        box.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        box.setBackground(Color.WHITE);
        box.setForeground(new Color(50, 50, 50));

        box.setOpaque(true);   // ⭐ IMPORTANT
        box.setFocusable(false);
        box.setPreferredSize(new Dimension(box.getPreferredSize().width, 30));

        // If editable, fix editor background too
        if (box.isEditable()) {
            Component editor = box.getEditor().getEditorComponent();
            editor.setBackground(Color.WHITE);
        }

        // Remove default gray UI effect
        box.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton btn = new JButton("▼");
                btn.setBackground(new Color(33, 150, 243));
                btn.setForeground(Color.WHITE);
                btn.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
                btn.setFocusPainted(false);
                btn.setOpaque(true);
                return btn;
            }
        });

        // Border
        box.setBorder(BorderFactory.createLineBorder(new Color(33, 150, 243), 1));

        // Renderer (dropdown items)
        box.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                    Object value, int index, boolean isSelected, boolean cellHasFocus) {

                JLabel lbl = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                lbl.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));

                if (isSelected) {
                    lbl.setBackground(new Color(67, 160, 71));
                    lbl.setForeground(Color.WHITE);
                } else {
                    lbl.setBackground(Color.WHITE);
                    lbl.setForeground(new Color(50, 50, 50));
                }

                lbl.setOpaque(true);
                return lbl;
            }
        });
    }

    /*  private void styleComboBox(JComboBox<String> box) {
    box.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    box.setBackground(Color.WHITE);
    box.setForeground(new Color(50, 50, 50));

    // Custom UI to control arrow button color
    box.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
        @Override
        protected JButton createArrowButton() {
            JButton btn = new JButton("▼");
            btn.setBackground(new Color(33, 150, 243));  // blue background
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            btn.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setOpaque(true);
            return btn;
        }
    });

    // Blue border around entire combobox
    box.setBorder(BorderFactory.createLineBorder(new Color(33, 150, 243), 1));

    // Green highlight on hover/selected items in dropdown
    box.setRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list,
                Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel lbl = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            lbl.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
            if (isSelected) {
                lbl.setBackground(new Color(67, 160, 71)); // green
                lbl.setForeground(Color.WHITE);
            } else {
                lbl.setBackground(Color.WHITE);
                lbl.setForeground(new Color(50, 50, 50));
            }
            lbl.setOpaque(true);
            return lbl;
        }
    });

    // Auto-select on hover
    box.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
        @Override
        public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
            SwingUtilities.invokeLater(() -> {
                for (int i = 0; i < box.getUI().getAccessibleChildrenCount(box); i++) {
                    Object child = box.getUI().getAccessibleChild(box, i);
                    if (child instanceof javax.swing.plaf.basic.ComboPopup) {
                        JList<?> list = ((javax.swing.plaf.basic.ComboPopup) child).getList();
                        list.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                            @Override
                            public void mouseMoved(java.awt.event.MouseEvent ev) {
                                int idx = list.locationToIndex(ev.getPoint());
                                if (idx >= 0) {
                                    list.setSelectedIndex(idx);
                                    box.setSelectedIndex(idx);
                                }
                            }
                        });
                    }
                }
            });
        }
        @Override public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {}
        @Override public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {}
    });
}*/
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
        selectedDivision = selectedDivision.trim();
        districtBox.addItem("---Select District---");

        if (districtMap.containsKey(selectedDivision)) {
            for (String district : districtMap.get(selectedDivision)) {
                districtBox.addItem(district);
            }
        }
    }

    private void setupDivisionListeners() {
        CmbPresentDivision.addActionListener(e -> updateDistricts(CmbPresentDivision, CmbPresentDistrict));
        CmbPermanentDivision.addActionListener(e -> updateDistricts(CmbPermanentDivision, CmbPermanentDistrict));
    }

    private void loadProfile(String username) {
        try {
            // Load volunteer info using DAO
            VolunteerDAO volunteerDAO = new VolunteerDAO();
            Volunteer v = volunteerDAO.getVolunteerByUsername(username);

            if (v == null) {
                JOptionPane.showMessageDialog(this, "No profile found for this volunteer!");
                return;
            }

            System.out.println("=== DEBUG INFO ===");
            System.out.println("Loaded name: " + v.getFullName());
            System.out.println("Loaded username: " + v.getUsername());
            System.out.println("Username is null? " + (v.getUsername() == null));
            System.out.println("Username is empty? " + (v.getUsername() != null && v.getUsername().isEmpty()));

            // ================= POPULATE FORM FIELDS =================
            // Personal Info
            TxtName.setText(v.getFullName());
            CmbDate.setSelectedItem(String.valueOf(v.getBirthDate()));
            CmbMonth.setSelectedItem(v.getBirthMonth());
            CmbYear.setSelectedItem(String.valueOf(v.getBirthYear()));

            // Gender
            String gender = v.getGender();
            if ("Male".equalsIgnoreCase(gender)) {
                RbtnMale.setSelected(true);
            } else if ("Female".equalsIgnoreCase(gender)) {
                RbtnFemale.setSelected(true);
            } else {
                RbtnOther.setSelected(true);
            }

            TxtNID.setText(v.getNid());
            CmbBloodGroup.setSelectedItem(v.getBloodGroup());

            // Contact Info
            TxtPhoneNumber.setText(v.getPhoneNumber());
            TxtEmail.setText(v.getEmail());
            TxtEmergencyContactNo.setText(v.getEmergencyContact());

            // Address Info
            CmbPresentDivision.setSelectedItem(v.getPresentDivision());
            CmbPresentDistrict.setSelectedItem(v.getPresentDistrict());
            CmbPermanentDivision.setSelectedItem(v.getPermanentDivision());
            CmbPermanentDistrict.setSelectedItem(v.getPermanentDistrict());

            // Education & Work
            TxtUniversity.setText(v.getUniversityName());
            TxtWork.setText(v.getProfession());

// Disasters Handled
            List<String> handled = v.getDisastersHandled();

// Set up listeners first (always, regardless of data)
            cbNone.addItemListener(e -> {
                if (cbNone.isSelected()) {
                    cbFlood.setSelected(false);
                    cbEarthquake.setSelected(false);
                    Cbstorm.setSelected(false);
                    cbCyclone.setSelected(false);
                    cbLandslide.setSelected(false);
                    cbFire.setSelected(false);
                    cbRiver.setSelected(false);
                }
            });

            java.awt.event.ItemListener disasterListener = e -> {
                if (((javax.swing.JCheckBox) e.getSource()).isSelected()) {
                    cbNone.setSelected(false);
                }
            };
            cbFlood.addItemListener(disasterListener);
            cbEarthquake.addItemListener(disasterListener);
            Cbstorm.addItemListener(disasterListener);
            cbCyclone.addItemListener(disasterListener);
            cbLandslide.addItemListener(disasterListener);
            cbFire.addItemListener(disasterListener);
            cbRiver.addItemListener(disasterListener);

// Now load the data
            if (handled == null || handled.isEmpty()
                    || (handled.size() == 1 && handled.get(0).equalsIgnoreCase("No"))) {
                cbNone.setSelected(true);
            } else {
                for (String d : handled) {
                    d = d.trim();
                    if (d.equalsIgnoreCase("Floods")) {
                        cbFlood.setSelected(true);
                    }
                    if (d.equalsIgnoreCase("Earthquakes")) {
                        cbEarthquake.setSelected(true);
                    }
                    if (d.equalsIgnoreCase("Storms")) {
                        Cbstorm.setSelected(true);
                    }
                    if (d.equalsIgnoreCase("Cyclones")) {
                        cbCyclone.setSelected(true);
                    }
                    if (d.equalsIgnoreCase("Landslides")) {
                        cbLandslide.setSelected(true);
                    }
                    if (d.equalsIgnoreCase("Fires")) {
                        cbFire.setSelected(true);
                    }
                    if (d.equalsIgnoreCase("River Erosion")) {
                        cbRiver.setSelected(true);
                    }
                }
            } // ← if/else closes here properly

// Now all the medical, driving, swimming code continues normally...
//String medicalDB = v.getMedicalTraining();
// ... rest of your code
            String medicalDB = v.getMedicalTraining();
            // VolunteerEditProfile VEdit=new VolunteerEditProfile
            if (medicalDB == null || medicalDB.equalsIgnoreCase("No")) {
                MedicalTrainingCheckBox.setSelected(false);
                MedicalTrainingCmbBox.setEnabled(false);
                MedicalTrainingCmbBox.setSelectedIndex(0); // "---Select One---"
            } else {
                MedicalTrainingCheckBox.setSelected(true);
                MedicalTrainingCmbBox.setEnabled(true);
                MedicalTrainingCmbBox.setSelectedItem(medicalDB); // load from DB
            }
            MedicalTrainingCheckBox.addItemListener(e -> {
                if (MedicalTrainingCheckBox.isSelected()) {
                    MedicalTrainingCmbBox.setEnabled(true);
                    if (MedicalTrainingCmbBox.getSelectedIndex() == 0) {
                        // keep "---Select One---" as default when checked manually
                        MedicalTrainingCmbBox.setSelectedIndex(0);
                    }
                } else {
                    MedicalTrainingCmbBox.setEnabled(false);
                    MedicalTrainingCmbBox.setSelectedIndex(0);
                }
            });
            String drivingDB = v.getDriving();

            if (drivingDB == null || drivingDB.equalsIgnoreCase("No")) {
                DrivingCheckBox.setSelected(false);
                DrivingCmbBox.setEnabled(false);
                DrivingCmbBox.setSelectedIndex(0); // "---Select One---"
            } else {
                DrivingCheckBox.setSelected(true);
                DrivingCmbBox.setEnabled(true);
                DrivingCmbBox.setSelectedItem(drivingDB); // load from DB
            }
            // Add this after the techDB block
            TechnicalSkillCheckBox.addItemListener(e -> {
                if (TechnicalSkillCheckBox.isSelected()) {
                    EngineeringCh.setEnabled(true);
                    ITCh.setEnabled(true);
                    CommunicationCh.setEnabled(true);
                } else {
                    EngineeringCh.setEnabled(false);
                    ITCh.setEnabled(false);
                    CommunicationCh.setEnabled(false);
                    EngineeringCh.setSelected(false);
                    ITCh.setSelected(false);
                    CommunicationCh.setSelected(false);
                }
            });

// Add this after the langDB block
            LanguageSkillCheckBox.addItemListener(e -> {
                if (LanguageSkillCheckBox.isSelected()) {
                    BanglaCh.setEnabled(true);
                    EnglishCh.setEnabled(true);
                    RegionalCh.setEnabled(true);
                } else {
                    BanglaCh.setEnabled(false);
                    EnglishCh.setEnabled(false);
                    RegionalCh.setEnabled(false);
                    BanglaCh.setSelected(false);
                    EnglishCh.setSelected(false);
                    RegionalCh.setSelected(false);
                }
            });

// Add ItemListener to handle manual checking
            DrivingCheckBox.addItemListener(e -> {
                if (DrivingCheckBox.isSelected()) {
                    DrivingCmbBox.setEnabled(true);
                    if (DrivingCmbBox.getSelectedIndex() == 0) {
                        // keep "---Select One---" as default when checked manually
                        DrivingCmbBox.setSelectedIndex(0);
                    }
                } else {
                    DrivingCmbBox.setEnabled(false);
                    DrivingCmbBox.setSelectedIndex(0);
                }
            });
            String Swim = v.getSwimming();

            if (Swim.equalsIgnoreCase("Yes")) {
                SwimmingCheckBox.setSelected(true);
            } else {
                SwimmingCheckBox.setSelected(false);
            }

            // ===== Search & Rescue =====
            if (v.getSearchAndRescue().equalsIgnoreCase("Yes")) {
                SearchAndRescueCheckBoox.setSelected(true);
            } else {
                SearchAndRescueCheckBoox.setSelected(false);
            }

            String langDB = v.getLanguageSkills();

            if (langDB.trim().equalsIgnoreCase("No")) {
                LanguageSkillCheckBox.setSelected(false);
                BanglaCh.setEnabled(false);
                EnglishCh.setEnabled(false);
                RegionalCh.setEnabled(false);

                BanglaCh.setSelected(false);
                EnglishCh.setSelected(false);
                RegionalCh.setSelected(false);
            } else {
                LanguageSkillCheckBox.setSelected(true);

                BanglaCh.setEnabled(true);
                EnglishCh.setEnabled(true);
                RegionalCh.setEnabled(true);

                String[] langs = langDB.split(",");
                for (String l : langs) {
                    l = l.trim();
                    if (l.equalsIgnoreCase("Bangla")) {
                        BanglaCh.setSelected(true);
                    }
                    if (l.equalsIgnoreCase("English")) {
                        EnglishCh.setSelected(true);
                    }
                    if (l.equalsIgnoreCase("Regional Language")) {
                        RegionalCh.setSelected(true);
                    }
                }
            }

            String techDB = v.getTechnicalSkills();
            if (techDB.trim().equalsIgnoreCase("No")) {
                TechnicalSkillCheckBox.setSelected(false);
                EngineeringCh.setEnabled(false);
                ITCh.setEnabled(false);
                CommunicationCh.setEnabled(false);

                EngineeringCh.setSelected(false);
                ITCh.setSelected(false);
                CommunicationCh.setSelected(false);
            } else {
                TechnicalSkillCheckBox.setSelected(true);

                EngineeringCh.setEnabled(true);
                ITCh.setEnabled(true);
                CommunicationCh.setEnabled(true);

                String[] techs = techDB.split(",");
                for (String t : techs) {
                    t = t.trim();
                    if (t.equalsIgnoreCase("Engineering")) {
                        EngineeringCh.setSelected(true);
                    }
                    if (t.equalsIgnoreCase("IT")) {
                        ITCh.setSelected(true);
                    }
                    if (t.equalsIgnoreCase("Communications")) {
                        CommunicationCh.setSelected(true);
                    }
                }
            }

            // System Info
            TxtUsername.setText(v.getUsername());

            // Load password from users table
            UserDAO userDAO = new UserDAO();
            String password = userDAO.getPassword(v.getUsername());
            System.out.println("Loaded password: " + password);
            System.out.println("Password is null? " + (password == null));
            System.out.println("==================");
            TxtPassword.setText(password != null ? password : "");

        } catch (Exception ex) {  // ✅ CATCH IS INSIDE THE METHOD!
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading profile: " + ex.getMessage());
        }
    }  // ✅ METHOD ENDS HERE   

    private boolean validateProfile() {
        // ===== Name =====
        String name = TxtName.getText().trim();
        if (name.isEmpty() || !name.matches("[a-zA-Z\\s]{3,50}")) {
            JOptionPane.showMessageDialog(this, "Enter a valid name (letters and spaces only, 3-50 characters).");
            TxtName.requestFocus();
            return false;
        }

        // ===== NID =====
        String nid = TxtNID.getText().trim();
        if (!nid.matches("\\d{10,17}")) { // Bangladesh NID: 10-17 digits
            JOptionPane.showMessageDialog(this, "Enter a valid NID number (10-17 digits).");
            TxtNID.requestFocus();
            return false;
        }

        // ===== Phone Numbers =====
        String phone = TxtPhoneNumber.getText().trim();
        if (!phone.matches("01\\d{8,11}")) { // Bangladesh mobile: starts with 01 and 11 digits total
            JOptionPane.showMessageDialog(this, "Enter a valid phone number (Bangladesh format, e.g., 017XXXXXXXX).");
            TxtPhoneNumber.requestFocus();
            return false;
        }

//    String altPhone = txtAlternativePhoneNumber.getText().trim();
//    if (!altPhone.isEmpty() && !altPhone.matches("01\\d{8,11}")) {
//        JOptionPane.showMessageDialog(this, "Enter a valid alternative phone number or leave empty.");
//        txtAlternativePhoneNumber.requestFocus();
//        return false;
//    }
        String emergencyPhone = TxtEmergencyContactNo.getText().trim();
        if (!emergencyPhone.matches("01\\d{8,11}")) {
            JOptionPane.showMessageDialog(this, "Enter a valid emergency contact number (Bangladesh format).");
            TxtEmergencyContactNo.requestFocus();
            return false;
        }

        // ===== Email =====
        String email = TxtEmail.getText().trim();
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,6}$")) {
            JOptionPane.showMessageDialog(this, "Enter a valid email address.");
            TxtEmail.requestFocus();
            return false;
        }

        // ===== Gender =====
        if (!RbtnMale.isSelected() && !RbtnFemale.isSelected() && !RbtnOther.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select a gender.");
            return false;
        }

        // ===== Username =====
        String username = TxtUsername.getText().trim();
        if (username.isEmpty() || !username.matches("[a-zA-Z0-9._]{4,20}")) {
            JOptionPane.showMessageDialog(this, "Username must be 4-20 characters (letters, numbers, ., _).");
            TxtUsername.requestFocus();
            return false;
        }

        // ===== Password =====
        String password = TxtPassword.getText();
//    if (password.isEmpty() || !password.matches("(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}")) {
//        JOptionPane.showMessageDialog(this, "Password must be 6-20 characters with letters and numbers.");
//        TxtPassword.requestFocus();
//        return false;
//    }
        if (password.isEmpty() || !password.matches("(?=.*[A-Za-z])(?=.*\\d).{6,20}")) {
            JOptionPane.showMessageDialog(this, "Password must be 6-20 characters with letters and numbers.");
            TxtPassword.requestFocus();
            return false;
        }

        // ===== Major Subject =====
//    if (TxtMajorSubject.getText().trim().isEmpty()) {
//        JOptionPane.showMessageDialog(this, "Please enter your major subject/field.");
//        TxtMajorSubject.requestFocus();
//        return false;
//    }
        // ===== University =====
        if (TxtUniversity.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your university/institution.");
            TxtUniversity.requestFocus();
            return false;
        }

        // ===== Official Designation & Org Dept =====
//    if (txtOfficialDesgn.getText().trim().isEmpty()) {
//        JOptionPane.showMessageDialog(this, "Official designation cannot be empty.");
//        txtOfficialDesgn.requestFocus();
//        return false;
//    }
//    if (txtOrgDept.getText().trim().isEmpty()) {
//        JOptionPane.showMessageDialog(this, "Organization department cannot be empty.");
//        txtOrgDept.requestFocus();
//        return false;
//    }
        // ===== Dropdowns =====
        if (CmbDate.getSelectedItem() == null
                || CmbMonth.getSelectedItem() == null
                || CmbYear.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select your complete date of birth.");
            return false;
        }

        if (CmbPresentDivision.getSelectedItem() == null || CmbPresentDistrict.getSelectedItem() == null
                || CmbPermanentDivision.getSelectedItem() == null || CmbPermanentDistrict.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select complete present and permanent address.");
            return false;
        }

//    if (CmbHigherEducation.getSelectedItem() == null || cmbOrgType.getSelectedItem() == null) {
//        JOptionPane.showMessageDialog(this, "Please select higher education and organization type.");
//        return false;
//    }
//    if (cmbJoinDate.getSelectedItem() == null || cmdJoinMonth.getSelectedItem() == null || cmbJoinYear.getSelectedItem() == null) {
//        JOptionPane.showMessageDialog(this, "Please select complete joining date.");
//        return false;
//    }
        // ===== Blood Group =====
        if (CmbBloodGroup.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a blood group.");
            return false;
        }

        return true; // ✅ All validations passed
    }

    private void saveProfile() {
        if (!validateProfile()) {
            return;
        }

        try {
            // ================= CREATE VOLUNTEER OBJECT =================
            Volunteer v = new Volunteer();

            //VolunteerDAO volunteerDAO = new VolunteerDAO();
            // Personal Info
            v.setFullName(TxtName.getText().trim());
            v.setBirthDate(Integer.parseInt(CmbDate.getSelectedItem().toString()));
            v.setBirthMonth(CmbMonth.getSelectedItem().toString());
            v.setBirthYear(Integer.parseInt(CmbYear.getSelectedItem().toString()));

            // Gender
            String gender = "";
            if (RbtnMale.isSelected()) {
                gender = "Male";
            } else if (RbtnFemale.isSelected()) {
                gender = "Female";
            } else if (RbtnOther.isSelected()) {
                gender = "Other";
            }
            v.setGender(gender);

            v.setNid(TxtNID.getText().trim());
            v.setBloodGroup(CmbBloodGroup.getSelectedItem().toString());

            // Contact Info
            v.setPhoneNumber(TxtPhoneNumber.getText().trim());
            v.setEmail(TxtEmail.getText().trim());
            v.setEmergencyContact(TxtEmergencyContactNo.getText().trim());

            // Address Info
            v.setPresentDivision(CmbPresentDivision.getSelectedItem().toString());
            v.setPresentDistrict(CmbPresentDistrict.getSelectedItem().toString());
            v.setPermanentDivision(CmbPermanentDivision.getSelectedItem().toString());
            v.setPermanentDistrict(CmbPermanentDistrict.getSelectedItem().toString());

            // Education & Work
            v.setUniversityName(TxtUniversity.getText().trim());
            v.setProfession(TxtWork.getText().trim());
            //v.setTrainings(TxtTraining.getText().trim());

//List<String> disastersList = new ArrayList<>();
            if (cbNone.isSelected()) {
                v.setDisastersHandled(java.util.Arrays.asList("No"));
            } else {
                List<String> disastersList = new ArrayList<>();
                if (cbFlood.isSelected()) {
                    disastersList.add("Floods");
                }
                if (cbEarthquake.isSelected()) {
                    disastersList.add("Earthquakes");
                }
                if (Cbstorm.isSelected()) {
                    disastersList.add("Storms");
                }
                if (cbCyclone.isSelected()) {
                    disastersList.add("Cyclones");
                }
                if (cbLandslide.isSelected()) {
                    disastersList.add("Landslides");
                }
                if (cbFire.isSelected()) {
                    disastersList.add("Fires");
                }
                if (cbRiver.isSelected()) {
                    disastersList.add("River Erosion");
                }
                v.setDisastersHandled(disastersList);
            }

            if (MedicalTrainingCheckBox.isSelected()) {
                if (MedicalTrainingCmbBox.getSelectedIndex() == 0
                        || "---Select One---".equals(MedicalTrainingCmbBox.getSelectedItem())) {
                    JOptionPane.showMessageDialog(this,
                            "Please select medical training type");
                    return; // stop save
                }
            }

// Validate Driving
            if (DrivingCheckBox.isSelected()) {
                if (DrivingCmbBox.getSelectedIndex() == 0
                        || "---Select One---".equals(DrivingCmbBox.getSelectedItem())) {
                    JOptionPane.showMessageDialog(this,
                            "Please select driving license type");
                    return; // stop save
                }
            }
            if (LanguageSkillCheckBox.isSelected()
                    && !BanglaCh.isSelected()
                    && !EnglishCh.isSelected()
                    && !RegionalCh.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please select at least one language.");
                return;
            }

// 4️⃣ Validate technical skills
            if (TechnicalSkillCheckBox.isSelected()
                    && !EngineeringCh.isSelected()
                    && !ITCh.isSelected()
                    && !CommunicationCh.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please select at least one technical skill.");
                return;
            }

            String medicalValue;
            if (MedicalTrainingCheckBox.isSelected()) {
                medicalValue = MedicalTrainingCmbBox.getSelectedItem().toString();
            } else {
                medicalValue = "No";
            }

            String drivingValue;
            if (DrivingCheckBox.isSelected()) {
                drivingValue = DrivingCmbBox.getSelectedItem().toString();
            } else {
                drivingValue = "No";
            }
// ===== Get Swimming & Search&Rescue =====
            String swimmingVal = SwimmingCheckBox.isSelected() ? "Yes" : "No";
            String rescueVal = SearchAndRescueCheckBoox.isSelected() ? "Yes" : "No";
// ===== Get Language Skills =====
            String languageDB = "No";
            if (LanguageSkillCheckBox.isSelected()) {
                List<String> langList = new ArrayList<>();
                if (BanglaCh.isSelected()) {
                    langList.add("Bangla");
                }
                if (EnglishCh.isSelected()) {
                    langList.add("English");
                }
                if (RegionalCh.isSelected()) {
                    langList.add("Regional Language");
                }

                if (!langList.isEmpty()) {
                    languageDB = String.join(",", langList);
                }
            }

// ===== Get Technical Skills =====
            String technicalDB = "No";
            if (TechnicalSkillCheckBox.isSelected()) {
                List<String> techList = new ArrayList<>();
                if (EngineeringCh.isSelected()) {
                    techList.add("Engineering");
                }
                if (ITCh.isSelected()) {
                    techList.add("IT");
                }
                if (CommunicationCh.isSelected()) {
                    techList.add("Communications");
                }

                if (!techList.isEmpty()) {
                    technicalDB = String.join(",", techList);
                }
            }
            // Username
            String newUsername = TxtUsername.getText().trim();
            v.setUsername(newUsername);

            String password = TxtPassword.getText();

            // ================= UPDATE USING DAO =================
            VolunteerDAO volunteerDAO = new VolunteerDAO();
            Volunteer existing = volunteerDAO.getVolunteerByUsername(oldUsername);

//        if (existing != null) {
//            v.setSwimming(existing.getSwimming());
//            v.setDriving(existing.getDriving());
//            v.setSearchAndRescue(existing.getSearchAndRescue());
//            v.setMedicalTraining(existing.getMedicalTraining());
//            v.setLanguageSkills(existing.getLanguageSkills());
//            v.setTechnicalSkills(existing.getTechnicalSkills());
//        }
// DELETE the existing != null block and replace with:
            v.setSwimming(swimmingVal);
            v.setDriving(drivingValue);
            v.setSearchAndRescue(rescueVal);
            v.setMedicalTraining(medicalValue);
            v.setLanguageSkills(languageDB);
            v.setTechnicalSkills(technicalDB);
            UserDAO userDAO = new UserDAO();

            // Update volunteer_info table
            boolean volunteerUpdated = volunteerDAO.updateVolunteer(v, oldUsername);

            // Update users table
            boolean userUpdated = userDAO.updateUserCredentials(oldUsername, newUsername, password);
            //(volunteerUpdated 
            //userUpdated
            if (volunteerUpdated && userUpdated) {
                JOptionPane.showMessageDialog(this, "Profile Updated Successfully!");

                // Update oldUsername for next save
                oldUsername = newUsername;

                // Close edit frame and open profile view
                this.dispose();

                ViewVolunteerProfile profileFrame = new ViewVolunteerProfile(desktop, newUsername, true);
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
                JOptionPane.showMessageDialog(this, "Error updating profile. Please try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving profile: " + e.getMessage());
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
        BtnSave = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Name = new javax.swing.JLabel();
        TxtName = new javax.swing.JTextField();
        Birth = new javax.swing.JLabel();
        CmbDate = new javax.swing.JComboBox<>();
        CmbMonth = new javax.swing.JComboBox<>();
        CmbYear = new javax.swing.JComboBox<>();
        NID = new javax.swing.JLabel();
        TxtNID = new javax.swing.JTextField();
        Gender = new javax.swing.JLabel();
        RbtnMale = new javax.swing.JRadioButton();
        RbtnFemale = new javax.swing.JRadioButton();
        RbtnOther = new javax.swing.JRadioButton();
        BloodGroup = new javax.swing.JLabel();
        CmbBloodGroup = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        TxtUniversity = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        TxtWork = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        Contact = new javax.swing.JLabel();
        PhoneNumber = new javax.swing.JLabel();
        TxtPhoneNumber = new javax.swing.JTextField();
        laba = new javax.swing.JLabel();
        TxtEmail = new javax.swing.JTextField();
        saba = new javax.swing.JLabel();
        TxtEmergencyContactNo = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        CmbPresentDivision = new javax.swing.JComboBox<>();
        CmbPresentDistrict = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        CmbPermanentDivision = new javax.swing.JComboBox<>();
        CmbPermanentDistrict = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        cbFlood = new javax.swing.JCheckBox();
        cbCyclone = new javax.swing.JCheckBox();
        Cbstorm = new javax.swing.JCheckBox();
        cbEarthquake = new javax.swing.JCheckBox();
        cbNone = new javax.swing.JCheckBox();
        cbLandslide = new javax.swing.JCheckBox();
        cbFire = new javax.swing.JCheckBox();
        cbRiver = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        MedicalTrainingCheckBox = new javax.swing.JCheckBox();
        MedicalTrainingCmbBox = new javax.swing.JComboBox<>();
        SearchAndRescueCheckBoox = new javax.swing.JCheckBox();
        SwimmingCheckBox = new javax.swing.JCheckBox();
        DrivingCheckBox = new javax.swing.JCheckBox();
        DrivingCmbBox = new javax.swing.JComboBox<>();
        LanguageSkillCheckBox = new javax.swing.JCheckBox();
        BanglaCh = new javax.swing.JCheckBox();
        EnglishCh = new javax.swing.JCheckBox();
        RegionalCh = new javax.swing.JCheckBox();
        TechnicalSkillCheckBox = new javax.swing.JCheckBox();
        ITCh = new javax.swing.JCheckBox();
        EngineeringCh = new javax.swing.JCheckBox();
        CommunicationCh = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        TxtUsername = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        TxtPassword = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(1300, 800));

        jScrollPane1.setMaximumSize(new java.awt.Dimension(32767, 2500));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1200, 700));

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setPreferredSize(new java.awt.Dimension(1076, 750));

        BtnSave.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnSave.setText("Save");
        BtnSave.addActionListener(this::BtnSaveActionPerformed);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/ChatGPT Image Feb 20, 2026, 10_45_12 AM.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addContainerGap(1170, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 2, true));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("Personal Details");

        Name.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        Name.setForeground(new java.awt.Color(0, 0, 204));
        Name.setText("Name");

        TxtName.setBackground(new java.awt.Color(242, 242, 242));
        TxtName.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        TxtName.addActionListener(this::TxtNameActionPerformed);

        Birth.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Birth.setForeground(new java.awt.Color(0, 0, 204));
        Birth.setText("Date Of Birth");

        CmbDate.setBackground(new java.awt.Color(242, 242, 242));
        CmbDate.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        CmbDate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        CmbMonth.setBackground(new java.awt.Color(242, 242, 242));
        CmbMonth.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        CmbMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "September", "October", "Novermber", "December" }));

        CmbYear.setBackground(new java.awt.Color(242, 242, 242));
        CmbYear.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        CmbYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000", "1999", "1998", "1997", "1996", "1995", "1994", "1993", "1992", "1991", "1990", "1989", "1988", "1987", "1986", "1985", "1984", "1983", "1982", "1981", "1980", "1979", "1978", "1977", "1976", "1975", "1974", "1973", "1972", "1971", " " }));

        NID.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        NID.setForeground(new java.awt.Color(0, 0, 204));
        NID.setText("NID");

        TxtNID.setBackground(new java.awt.Color(242, 242, 242));
        TxtNID.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N

        Gender.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        Gender.setForeground(new java.awt.Color(0, 0, 204));
        Gender.setText("Gender");

        RbtnMale.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        RbtnMale.setText("Male");

        RbtnFemale.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        RbtnFemale.setText("Female");

        RbtnOther.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        RbtnOther.setText("Other");
        RbtnOther.addActionListener(this::RbtnOtherActionPerformed);

        BloodGroup.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        BloodGroup.setForeground(new java.awt.Color(0, 0, 204));
        BloodGroup.setText("BloodGroup");

        CmbBloodGroup.setBackground(new java.awt.Color(242, 242, 242));
        CmbBloodGroup.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        CmbBloodGroup.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A+", "B+", "AB+", "O+", "A-", "B-", "AB-", "O-" }));

        jLabel6.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 204));
        jLabel6.setText("University ");

        TxtUniversity.setBackground(new java.awt.Color(242, 242, 242));
        TxtUniversity.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        TxtUniversity.addActionListener(this::TxtUniversityActionPerformed);

        jLabel8.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 204));
        jLabel8.setText("Job/Status");

        TxtWork.setBackground(new java.awt.Color(242, 242, 242));
        TxtWork.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(TxtName))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(NID, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(TxtNID))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(TxtUniversity))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(Gender, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(RbtnMale, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(RbtnFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(RbtnOther, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(BloodGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(CmbBloodGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(Birth, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(CmbDate, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(CmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(CmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(TxtWork)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Name)
                    .addComponent(TxtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Birth)
                    .addComponent(CmbDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NID)
                    .addComponent(TxtNID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Gender)
                    .addComponent(RbtnMale)
                    .addComponent(RbtnFemale)
                    .addComponent(RbtnOther))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BloodGroup)
                    .addComponent(CmbBloodGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(TxtUniversity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(TxtWork, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 2, true));

        Contact.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Contact.setForeground(new java.awt.Color(0, 0, 204));
        Contact.setText("Contact Info");

        PhoneNumber.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        PhoneNumber.setForeground(new java.awt.Color(0, 0, 204));
        PhoneNumber.setText("Phone Number");

        TxtPhoneNumber.setBackground(new java.awt.Color(242, 242, 242));
        TxtPhoneNumber.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        TxtPhoneNumber.addActionListener(this::TxtPhoneNumberActionPerformed);

        laba.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        laba.setForeground(new java.awt.Color(0, 0, 204));
        laba.setText("Email");

        TxtEmail.setBackground(new java.awt.Color(242, 242, 242));
        TxtEmail.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N

        saba.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        saba.setForeground(new java.awt.Color(0, 0, 204));
        saba.setText("Emergency Phone No.");

        TxtEmergencyContactNo.setBackground(new java.awt.Color(242, 242, 242));
        TxtEmergencyContactNo.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Contact, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(PhoneNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(TxtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(laba, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(saba, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TxtEmergencyContactNo, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Contact)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PhoneNumber)
                    .addComponent(TxtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(laba)
                    .addComponent(TxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saba)
                    .addComponent(TxtEmergencyContactNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 2, true));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 204));
        jLabel2.setText("Address Info");

        jLabel3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 204));
        jLabel3.setText("Present Address");

        CmbPresentDivision.setBackground(new java.awt.Color(242, 242, 242));
        CmbPresentDivision.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        CmbPresentDivision.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Barishal", "Chattogram", "Dhaka", "Khulna", "Mymensingh", "Rajshahi", "Rangpur", "Sylhet" }));

        CmbPresentDistrict.setBackground(new java.awt.Color(242, 242, 242));
        CmbPresentDistrict.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        CmbPresentDistrict.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bagerhat", "Bandarban", "Barguna", "Barishal", "Bhola", "Bogura", "Brahmanbaria", "Chandpur", "Chapainawabganj", "Chattogram", "Chuadanga", "Cox’s Bazar", "Cumilla", "Dhaka", "Dinajpur", "Faridpur", "Feni", "Gaibandha", "Gazipur", "Gopalganj", "Habiganj", "Jamalpur", "Jashore", "Jhalokathi", "Jhenaidah", "Joypurhat", "Khagrachhari", "Khulna", "Kishoreganj", "Kurigram", "Kushtia", "Lakshmipur", "Lalmonirhat", "Madaripur", "Magura", "Manikganj", "Meherpur", "Moulvibazar", "Munshiganj", "Mymensingh", "Naogaon", "Narail", "Narayanganj", "Narsingdi", "Natore", "Netrokona", "Nilphamari", "Noakhali", "Pabna", "Panchagarh", "Patuakhali", "Pirojpur", "Rajbari", "Rajshahi", "Rangamati", "Rangpur", "Satkhira", "Shariatpur", "Sherpur", "Sirajganj", "Sunamganj", "Sylhet", "Tangail", "Thakurgaon" }));

        jLabel4.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 204));
        jLabel4.setText("Permanent Address");

        CmbPermanentDivision.setBackground(new java.awt.Color(242, 242, 242));
        CmbPermanentDivision.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        CmbPermanentDivision.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Barishal", "Chattogram", "Dhaka", "Khulna", "Mymensingh", "Rajshahi", "Rangpur", "Sylhet " }));
        CmbPermanentDivision.addActionListener(this::CmbPermanentDivisionActionPerformed);

        CmbPermanentDistrict.setBackground(new java.awt.Color(242, 242, 242));
        CmbPermanentDistrict.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        CmbPermanentDistrict.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bagerhat", "Bandarban", "Barguna", "Barishal", "Bhola", "Bogura", "Brahmanbaria", "Chandpur", "Chapainawabganj", "Chattogram", "Chuadanga", "Cox’s Bazar", "Cumilla", "Dhaka", "Dinajpur", "Faridpur", "Feni", "Gaibandha", "Gazipur", "Gopalganj", "Habiganj", "Jamalpur", "Jashore", "Jhalokathi", "Jhenaidah", "Joypurhat", "Khagrachhari", "Khulna", "Kishoreganj", "Kurigram", "Kushtia", "Lakshmipur", "Lalmonirhat", "Madaripur", "Magura", "Manikganj", "Meherpur", "Moulvibazar", "Munshiganj", "Mymensingh", "Naogaon", "Narail", "Narayanganj", "Narsingdi", "Natore", "Netrokona", "Nilphamari", "Noakhali", "Pabna", "Panchagarh", "Patuakhali", "Pirojpur", "Rajbari", "Rajshahi", "Rangamati", "Rangpur", "Satkhira", "Shariatpur", "Sherpur", "Sirajganj", "Sunamganj", "Sylhet", "Tangail", "Thakurgaon" }));
        CmbPermanentDistrict.addActionListener(this::CmbPermanentDistrictActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CmbPermanentDivision, 0, 138, Short.MAX_VALUE)
                            .addComponent(CmbPresentDivision, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CmbPresentDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CmbPermanentDistrict, 0, 0, Short.MAX_VALUE))
                        .addGap(105, 105, 105))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(CmbPermanentDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(CmbPresentDivision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CmbPresentDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(CmbPermanentDivision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(14, 14, 14))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 2, true));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 204));
        jLabel13.setText("Types Of Disasters Handled");

        cbFlood.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cbFlood.setForeground(new java.awt.Color(0, 0, 204));
        cbFlood.setText("Floods");

        cbCyclone.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cbCyclone.setForeground(new java.awt.Color(0, 0, 204));
        cbCyclone.setText("Cyclones");

        Cbstorm.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        Cbstorm.setForeground(new java.awt.Color(0, 0, 204));
        Cbstorm.setText("Storms");

        cbEarthquake.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cbEarthquake.setForeground(new java.awt.Color(0, 0, 204));
        cbEarthquake.setText("Earthquakes");

        cbNone.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cbNone.setForeground(new java.awt.Color(0, 0, 204));
        cbNone.setText("None");

        cbLandslide.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cbLandslide.setForeground(new java.awt.Color(0, 0, 204));
        cbLandslide.setText("Landslides");

        cbFire.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cbFire.setForeground(new java.awt.Color(0, 0, 204));
        cbFire.setText("Fires");

        cbRiver.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        cbRiver.setForeground(new java.awt.Color(0, 0, 204));
        cbRiver.setText("River Erosion");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbFlood, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbRiver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbCyclone, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(cbFire, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Cbstorm, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(cbLandslide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbEarthquake, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbNone))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbFlood)
                    .addComponent(cbCyclone)
                    .addComponent(Cbstorm)
                    .addComponent(cbEarthquake))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbRiver)
                    .addComponent(cbFire)
                    .addComponent(cbLandslide)
                    .addComponent(cbNone))
                .addGap(18, 18, 18))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 2, true));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 204));
        jLabel14.setText("Skills");

        MedicalTrainingCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        MedicalTrainingCheckBox.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        MedicalTrainingCheckBox.setText("Medical Training");
        MedicalTrainingCheckBox.setContentAreaFilled(false);

        MedicalTrainingCmbBox.setBackground(new java.awt.Color(242, 242, 242));
        MedicalTrainingCmbBox.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        MedicalTrainingCmbBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Select one---", "Doctor", "Nurse", "First Aid Certified", "Paramedic" }));

        SearchAndRescueCheckBoox.setBackground(new java.awt.Color(255, 255, 255));
        SearchAndRescueCheckBoox.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        SearchAndRescueCheckBoox.setText("Search And Rescueue");
        SearchAndRescueCheckBoox.setContentAreaFilled(false);

        SwimmingCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        SwimmingCheckBox.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        SwimmingCheckBox.setText("Swimming");
        SwimmingCheckBox.setContentAreaFilled(false);

        DrivingCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        DrivingCheckBox.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        DrivingCheckBox.setText("Driving");
        DrivingCheckBox.setContentAreaFilled(false);

        DrivingCmbBox.setBackground(new java.awt.Color(242, 242, 242));
        DrivingCmbBox.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        DrivingCmbBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Select one---", "Car", "Motorcycle", "Bicycle", "Truck" }));

        LanguageSkillCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        LanguageSkillCheckBox.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        LanguageSkillCheckBox.setText("Language Skill");
        LanguageSkillCheckBox.setContentAreaFilled(false);
        LanguageSkillCheckBox.addActionListener(this::LanguageSkillCheckBoxActionPerformed);

        BanglaCh.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        BanglaCh.setForeground(new java.awt.Color(0, 0, 204));
        BanglaCh.setText("Bangla");

        EnglishCh.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        EnglishCh.setForeground(new java.awt.Color(0, 0, 204));
        EnglishCh.setText("English");

        RegionalCh.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        RegionalCh.setForeground(new java.awt.Color(0, 0, 204));
        RegionalCh.setText("Regional Languages");

        TechnicalSkillCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        TechnicalSkillCheckBox.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        TechnicalSkillCheckBox.setText("Technical Skill");
        TechnicalSkillCheckBox.setContentAreaFilled(false);
        TechnicalSkillCheckBox.addActionListener(this::TechnicalSkillCheckBoxActionPerformed);

        ITCh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ITCh.setForeground(new java.awt.Color(0, 0, 204));
        ITCh.setText("IT");

        EngineeringCh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        EngineeringCh.setForeground(new java.awt.Color(0, 0, 204));
        EngineeringCh.setText("Engineering Skill");

        CommunicationCh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        CommunicationCh.setForeground(new java.awt.Color(0, 0, 204));
        CommunicationCh.setText("Communication Skill");
        CommunicationCh.addActionListener(this::CommunicationChActionPerformed);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                            .addComponent(MedicalTrainingCheckBox)
                            .addGap(18, 18, 18)
                            .addComponent(MedicalTrainingCmbBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addComponent(LanguageSkillCheckBox)
                                    .addGap(17, 17, 17)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                            .addComponent(BanglaCh, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(EnglishCh, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(RegionalCh))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                            .addComponent(ITCh, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(EngineeringCh)
                                            .addGap(18, 18, 18)
                                            .addComponent(CommunicationCh))))
                                .addComponent(TechnicalSkillCheckBox))
                            .addContainerGap()))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SearchAndRescueCheckBoox)
                                    .addComponent(SwimmingCheckBox)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(DrivingCheckBox)
                                        .addGap(18, 18, 18)
                                        .addComponent(DrivingCmbBox, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 258, Short.MAX_VALUE))
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MedicalTrainingCheckBox)
                    .addComponent(MedicalTrainingCmbBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SearchAndRescueCheckBoox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SwimmingCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DrivingCheckBox)
                    .addComponent(DrivingCmbBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LanguageSkillCheckBox)
                    .addComponent(BanglaCh)
                    .addComponent(EnglishCh)
                    .addComponent(RegionalCh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TechnicalSkillCheckBox)
                    .addComponent(ITCh)
                    .addComponent(EngineeringCh)
                    .addComponent(CommunicationCh))
                .addGap(42, 42, 42))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 2, true));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 204));
        jLabel9.setText("System Info");

        jLabel10.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 204));
        jLabel10.setText("Username");

        TxtUsername.setBackground(new java.awt.Color(242, 242, 242));
        TxtUsername.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        TxtUsername.addActionListener(this::TxtUsernameActionPerformed);

        jLabel11.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 204));
        jLabel11.setText("Password");

        TxtPassword.setBackground(new java.awt.Color(242, 242, 242));
        TxtPassword.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        TxtPassword.addActionListener(this::TxtPasswordActionPerformed);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(TxtPassword)
                    .addComponent(TxtUsername, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap(97, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(83, Short.MAX_VALUE))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(583, 583, 583)
                .addComponent(BtnSave)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(20, 20, 20)
                .addComponent(BtnSave)
                .addGap(25, 25, 25))
        );

        jScrollPane1.setViewportView(mainPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1288, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TxtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNameActionPerformed

    private void BtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSaveActionPerformed

    private void TxtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtPasswordActionPerformed

    private void TxtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtUsernameActionPerformed

    private void RbtnOtherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RbtnOtherActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RbtnOtherActionPerformed

    private void TxtPhoneNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtPhoneNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtPhoneNumberActionPerformed

    private void CmbPermanentDivisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CmbPermanentDivisionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbPermanentDivisionActionPerformed

    private void TxtUniversityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtUniversityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtUniversityActionPerformed

    private void TechnicalSkillCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TechnicalSkillCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TechnicalSkillCheckBoxActionPerformed

    private void LanguageSkillCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LanguageSkillCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LanguageSkillCheckBoxActionPerformed

    private void CommunicationChActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CommunicationChActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CommunicationChActionPerformed

    private void CmbPermanentDistrictActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CmbPermanentDistrictActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbPermanentDistrictActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox BanglaCh;
    private javax.swing.JLabel Birth;
    private javax.swing.JLabel BloodGroup;
    private javax.swing.JButton BtnSave;
    private javax.swing.JCheckBox Cbstorm;
    private javax.swing.JComboBox<String> CmbBloodGroup;
    private javax.swing.JComboBox<String> CmbDate;
    private javax.swing.JComboBox<String> CmbMonth;
    private javax.swing.JComboBox<String> CmbPermanentDistrict;
    private javax.swing.JComboBox<String> CmbPermanentDivision;
    private javax.swing.JComboBox<String> CmbPresentDistrict;
    private javax.swing.JComboBox<String> CmbPresentDivision;
    private javax.swing.JComboBox<String> CmbYear;
    private javax.swing.JCheckBox CommunicationCh;
    private javax.swing.JLabel Contact;
    private javax.swing.JCheckBox DrivingCheckBox;
    private javax.swing.JComboBox<String> DrivingCmbBox;
    private javax.swing.JCheckBox EngineeringCh;
    private javax.swing.JCheckBox EnglishCh;
    private javax.swing.JLabel Gender;
    private javax.swing.JCheckBox ITCh;
    private javax.swing.JCheckBox LanguageSkillCheckBox;
    private javax.swing.JCheckBox MedicalTrainingCheckBox;
    private javax.swing.JComboBox<String> MedicalTrainingCmbBox;
    private javax.swing.JLabel NID;
    private javax.swing.JLabel Name;
    private javax.swing.JLabel PhoneNumber;
    private javax.swing.JRadioButton RbtnFemale;
    private javax.swing.JRadioButton RbtnMale;
    private javax.swing.JRadioButton RbtnOther;
    private javax.swing.JCheckBox RegionalCh;
    private javax.swing.JCheckBox SearchAndRescueCheckBoox;
    private javax.swing.JCheckBox SwimmingCheckBox;
    private javax.swing.JCheckBox TechnicalSkillCheckBox;
    private javax.swing.JTextField TxtEmail;
    private javax.swing.JTextField TxtEmergencyContactNo;
    private javax.swing.JTextField TxtNID;
    private javax.swing.JTextField TxtName;
    private javax.swing.JTextField TxtPassword;
    private javax.swing.JTextField TxtPhoneNumber;
    private javax.swing.JTextField TxtUniversity;
    private javax.swing.JTextField TxtUsername;
    private javax.swing.JTextField TxtWork;
    private javax.swing.JCheckBox cbCyclone;
    private javax.swing.JCheckBox cbEarthquake;
    private javax.swing.JCheckBox cbFire;
    private javax.swing.JCheckBox cbFlood;
    private javax.swing.JCheckBox cbLandslide;
    private javax.swing.JCheckBox cbNone;
    private javax.swing.JCheckBox cbRiver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel laba;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel saba;
    // End of variables declaration//GEN-END:variables
}
