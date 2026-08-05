/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.dg.adminform;

import com.dg.homepage.Homepage1;
import com.dg.dbconnection.AdminDTO;
import com.dg.dbconnection.AdminData;
import javax.swing.*;
import java.awt.*;
public class AdminExperiencePanel extends javax.swing.JPanel {

    /**
     * Creates new form PhysicalCapabilitiesExperiencePanel
     */
    public AdminExperiencePanel() {
        initComponents();
        AdminPreviousDisasterResponse.addActionListener(e -> {
    handleDescriptionState();
});
        // Set placeholder for description
    AdminDisasterResponseDescription.setText("Must Include Disaster type, year, your role, key actions");
    AdminDisasterResponseDescription.setForeground(Color.GRAY);

    setBackground(Color.WHITE);
    setOpaque(false);

    // -----------------------------
    // "No Formal Training" checkbox listener
    // -----------------------------
    AdminNoFormalTraining.addActionListener(e -> {
        boolean noFormal = AdminNoFormalTraining.isSelected();

        // baki checkboxes disable/enable
        AdminEmergencyResponseTraining.setEnabled(!noFormal);
        AdminDRRCertification.setEnabled(!noFormal);
        AdminFirstAidCertified.setEnabled(!noFormal);
        AdminCrisisManagementTraining.setEnabled(!noFormal);
        AdminVolunteerCoordinationTraining.setEnabled(!noFormal);
        AdminMappingForDisasterManagement.setEnabled(!noFormal);
        AdminCommunityDisasterPreparedness.setEnabled(!noFormal);

        // jodi "No Formal Training" select hoy, baki sob uncheck
        if (noFormal) {
            AdminEmergencyResponseTraining.setSelected(false);
            AdminDRRCertification.setSelected(false);
            AdminFirstAidCertified.setSelected(false);
            AdminCrisisManagementTraining.setSelected(false);
            AdminVolunteerCoordinationTraining.setSelected(false);
            AdminMappingForDisasterManagement.setSelected(false);
            AdminCommunityDisasterPreparedness.setSelected(false);
        }
    });

    // -----------------------------
    // "None" disaster checkbox listener
    // -----------------------------
    AdminNoneDisaster.addActionListener(e -> {
        boolean noneSelected = AdminNoneDisaster.isSelected();

        // Disable/Enable other disaster checkboxes
        AdminFloods.setEnabled(!noneSelected);
        AdminCyclones.setEnabled(!noneSelected);
        AdminStorms.setEnabled(!noneSelected);
        AdminEarthquakes.setEnabled(!noneSelected);
        AdminFires.setEnabled(!noneSelected);
        AdminLandslides.setEnabled(!noneSelected);
        AdminRiverErosion.setEnabled(!noneSelected);

        // If "None" selected, uncheck others
        if (noneSelected) {
            AdminFloods.setSelected(false);
            AdminCyclones.setSelected(false);
            AdminStorms.setSelected(false);
            AdminEarthquakes.setSelected(false);
            AdminFires.setSelected(false);
            AdminLandslides.setSelected(false);
            AdminRiverErosion.setSelected(false);

            // Disable & clear description
            AdminDisasterResponseDescription.setText("");
            AdminDisasterResponseDescription.setEnabled(false);
            AdminDisasterResponseDescription.setForeground(Color.GRAY);
        } else {
            // Enable description back
            AdminDisasterResponseDescription.setEnabled(true);
            if (AdminDisasterResponseDescription.getText().isEmpty()) {
                AdminDisasterResponseDescription.setText("Must Include Disaster type, year, your role, key actions");
                AdminDisasterResponseDescription.setForeground(Color.GRAY);
            }
            handleDescriptionState();
        }
    });
    // ------------------------------
// Fix JTextArea for multi-line + scrolling
AdminDisasterResponseDescription.setLineWrap(true);
AdminDisasterResponseDescription.setWrapStyleWord(true);

// Scrollbar policy
jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
 handleDescriptionState();

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
    private void handleDescriptionState() {

    boolean noneDisasterSelected = AdminNoneDisaster.isSelected();
    boolean noPreviousExperience =
            AdminPreviousDisasterResponse.getSelectedIndex() == 4;

    if (noneDisasterSelected || noPreviousExperience) {

        AdminDisasterResponseDescription.setText("");
        AdminDisasterResponseDescription.setEnabled(false);

    } else {

        AdminDisasterResponseDescription.setEnabled(true);
    }
}

    private boolean validateForm() {
        // 1. Training/Certification Validation
        if (!AdminEmergencyResponseTraining.isSelected()
                && !AdminDRRCertification.isSelected()
                && !AdminFirstAidCertified.isSelected()
                && !AdminCrisisManagementTraining.isSelected()
                && !AdminVolunteerCoordinationTraining.isSelected()
                && !AdminMappingForDisasterManagement.isSelected()
                && !AdminCommunityDisasterPreparedness.isSelected()
                && !AdminNoFormalTraining.isSelected()) {

            JOptionPane.showMessageDialog(this,
                    "Please select at least one training/certification (or 'No Formal Training').",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // 2. Previous Disaster Response Experience
        if (AdminPreviousDisasterResponse.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select your Previous Disaster Response Experience.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // 3. Disaster Types Handled
        if (!AdminFloods.isSelected()
                && !AdminCyclones.isSelected()
                && !AdminStorms.isSelected()
                && !AdminEarthquakes.isSelected()
                && !AdminFires.isSelected()
                && !AdminLandslides.isSelected()
                && !AdminRiverErosion.isSelected()
                && !AdminNoneDisaster.isSelected()) {

            JOptionPane.showMessageDialog(this,
                    "Please select at least one type of disaster you have handled.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // 4. Disaster Response Description
       if (AdminDisasterResponseDescription.isEnabled()) {

    String desc = AdminDisasterResponseDescription.getText().trim();

    if (desc.isEmpty()
        || desc.equals("Must Include Disaster type, year, your role, key actions")) {

        JOptionPane.showMessageDialog(this,
                "Please describe your most significant disaster response.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }
}

        // All validations passed
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        AdminDisasterResponseDescription = new javax.swing.JTextArea();
        ExperiencePanelPreviousButton = new javax.swing.JButton();
        ExperiencePanelSubmitButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        AdminEmergencyResponseTraining = new javax.swing.JCheckBox();
        AdminDRRCertification = new javax.swing.JCheckBox();
        AdminFirstAidCertified = new javax.swing.JCheckBox();
        AdminCrisisManagementTraining = new javax.swing.JCheckBox();
        AdminVolunteerCoordinationTraining = new javax.swing.JCheckBox();
        AdminMappingForDisasterManagement = new javax.swing.JCheckBox();
        AdminCommunityDisasterPreparedness = new javax.swing.JCheckBox();
        AdminNoFormalTraining = new javax.swing.JCheckBox();
        AdminPreviousDisasterResponse = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        AdminFloods = new javax.swing.JCheckBox();
        AdminCyclones = new javax.swing.JCheckBox();
        AdminStorms = new javax.swing.JCheckBox();
        AdminEarthquakes = new javax.swing.JCheckBox();
        AdminFires = new javax.swing.JCheckBox();
        AdminLandslides = new javax.swing.JCheckBox();
        AdminRiverErosion = new javax.swing.JCheckBox();
        AdminNoneDisaster = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Arial Black", 0, 20)); // NOI18N
        jLabel6.setText("Experience");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel7.setText("Previous Disaster Response Experience ");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel9.setText("Briefly Describe Your Most Significant Disaster Response:");

        AdminDisasterResponseDescription.setColumns(20);
        AdminDisasterResponseDescription.setRows(5);
        AdminDisasterResponseDescription.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AdminDisasterResponseDescriptionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                AdminDisasterResponseDescriptionFocusLost(evt);
            }
        });
        jScrollPane2.setViewportView(AdminDisasterResponseDescription);

        ExperiencePanelPreviousButton.setBackground(new java.awt.Color(0, 153, 153));
        ExperiencePanelPreviousButton.setFont(new java.awt.Font("Arial Black", 0, 16)); // NOI18N
        ExperiencePanelPreviousButton.setForeground(new java.awt.Color(204, 255, 255));
        ExperiencePanelPreviousButton.setText("Previous");
        ExperiencePanelPreviousButton.addActionListener(this::ExperiencePanelPreviousButtonActionPerformed);

        ExperiencePanelSubmitButton.setBackground(new java.awt.Color(0, 153, 153));
        ExperiencePanelSubmitButton.setFont(new java.awt.Font("Arial Black", 0, 16)); // NOI18N
        ExperiencePanelSubmitButton.setForeground(new java.awt.Color(204, 255, 255));
        ExperiencePanelSubmitButton.setText("Submit");
        ExperiencePanelSubmitButton.addActionListener(this::ExperiencePanelSubmitButtonActionPerformed);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel1.setText("Disaster Management Training/Certification");

        AdminEmergencyResponseTraining.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        AdminEmergencyResponseTraining.setText("Emergency Response Training");

        AdminDRRCertification.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        AdminDRRCertification.setText("Disaster Risk Reduction (DRR) Certification");

        AdminFirstAidCertified.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        AdminFirstAidCertified.setText("First Aid & CPR Certified");

        AdminCrisisManagementTraining.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        AdminCrisisManagementTraining.setText("Crisis Management Training");

        AdminVolunteerCoordinationTraining.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        AdminVolunteerCoordinationTraining.setText("Volunteer Coordination Training");

        AdminMappingForDisasterManagement.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        AdminMappingForDisasterManagement.setText("GIS/Mapping for Disaster Management");

        AdminCommunityDisasterPreparedness.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        AdminCommunityDisasterPreparedness.setText("Community Disaster Preparedness");

        AdminNoFormalTraining.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        AdminNoFormalTraining.setText("No Formal Training");

        AdminPreviousDisasterResponse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Select One---", "Yes, extensive (managed 10+ disaster responses)", "Yes, moderate (managed 5-10 responses)", "Yes, limited (managed 1-4 responses)", "No previous experience" }));
        AdminPreviousDisasterResponse.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 153)));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel2.setText("Types of Disasters Handled");

        AdminFloods.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        AdminFloods.setText("Floods");

        AdminCyclones.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        AdminCyclones.setText("Cyclones");

        AdminStorms.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        AdminStorms.setText("Storms");

        AdminEarthquakes.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        AdminEarthquakes.setText("Earthquakes");

        AdminFires.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        AdminFires.setText("Fires");

        AdminLandslides.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        AdminLandslides.setText("Landslides");

        AdminRiverErosion.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        AdminRiverErosion.setText("River Erosion");

        AdminNoneDisaster.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        AdminNoneDisaster.setText("None");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(93, 93, 93)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(AdminCyclones)
                                            .addComponent(AdminFloods))
                                        .addGap(112, 112, 112)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(AdminFires)
                                            .addComponent(AdminLandslides)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(AdminStorms)
                                            .addComponent(AdminEarthquakes))
                                        .addGap(90, 90, 90)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(AdminRiverErosion)
                                            .addComponent(AdminNoneDisaster))))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel7)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(AdminEmergencyResponseTraining)
                                    .addComponent(AdminDRRCertification)
                                    .addComponent(AdminFirstAidCertified)
                                    .addComponent(AdminCrisisManagementTraining))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(AdminPreviousDisasterResponse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(AdminCommunityDisasterPreparedness)
                                    .addComponent(AdminNoFormalTraining)
                                    .addComponent(AdminMappingForDisasterManagement)
                                    .addComponent(AdminVolunteerCoordinationTraining))
                                .addGap(39, 39, 39))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ExperiencePanelPreviousButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ExperiencePanelSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85))))
            .addGroup(layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AdminEmergencyResponseTraining)
                    .addComponent(AdminVolunteerCoordinationTraining))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AdminDRRCertification)
                    .addComponent(AdminMappingForDisasterManagement))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AdminFirstAidCertified)
                    .addComponent(AdminCommunityDisasterPreparedness))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AdminCrisisManagementTraining)
                    .addComponent(AdminNoFormalTraining))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(AdminPreviousDisasterResponse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(AdminFloods)
                    .addComponent(AdminFires))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AdminCyclones)
                    .addComponent(AdminLandslides))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AdminStorms)
                    .addComponent(AdminRiverErosion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AdminEarthquakes)
                    .addComponent(AdminNoneDisaster))
                .addGap(25, 25, 25)
                .addComponent(jLabel9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(92, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ExperiencePanelSubmitButton)
                            .addComponent(ExperiencePanelPreviousButton))
                        .addGap(16, 16, 16))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ExperiencePanelSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExperiencePanelSubmitButtonActionPerformed
        // TODO add your handling code here:
        
        if (!validateForm()) {
            return;
        }
        MainAdminFrame frame = (MainAdminFrame) SwingUtilities.getWindowAncestor(this);
         AdminData admin=frame.getAdminData();
         admin.setEmergencyResponseTraining(AdminEmergencyResponseTraining.isSelected() ? "Yes" : "No");
        admin.setDisasterRiskReductionCertification(AdminDRRCertification.isSelected() ? "Yes" : "No");
        admin.setFirstAidCprCertified(AdminFirstAidCertified.isSelected() ? "Yes" : "No");
        admin.setCrisisManagementTraining(AdminCrisisManagementTraining.isSelected() ? "Yes" : "No");
        admin.setVolunteerCoordinationTraining(AdminVolunteerCoordinationTraining.isSelected() ? "Yes" : "No");
        admin.setGisMappingDisasterManagement(AdminMappingForDisasterManagement.isSelected() ? "Yes" : "No");
        admin.setCommunityDisasterPreparedness(AdminCommunityDisasterPreparedness.isSelected() ? "Yes" : "No");
        admin.setNoFormalTraining(AdminNoFormalTraining.isSelected() ? "Yes" : "No");

        // -------------------------
        // Previous Disaster Response Experience
        // -------------------------
        String previousExperience = AdminPreviousDisasterResponse.getSelectedIndex() > 0
                ? AdminPreviousDisasterResponse.getSelectedItem().toString()
                : "No";
        admin.setPreviousDisasterExperience(previousExperience);

        // -------------------------
        // Types of Disasters Handled
        // -------------------------
        admin.setFloods(AdminFloods.isSelected() ? "Yes" : "No");
        admin.setCyclones(AdminCyclones.isSelected() ? "Yes" : "No");
        admin.setStorms(AdminStorms.isSelected() ? "Yes" : "No");
        admin.setEarthquakes(AdminEarthquakes.isSelected() ? "Yes" : "No");
        admin.setFires(AdminFires.isSelected() ? "Yes" : "No");
        admin.setLandslides(AdminLandslides.isSelected() ? "Yes" : "No");
        admin.setRiverErosion(AdminRiverErosion.isSelected() ? "Yes" : "No");
        admin.setNoneDisaster(AdminNoneDisaster.isSelected() ? "Yes" : "No");

        // -------------------------
        // Description of most significant disaster
        // -------------------------
        String description = AdminDisasterResponseDescription.getText().trim();
        if (description.equals("Must Include Disaster type, year, your role, key actions") || description.isEmpty()) {
            description = "None";
        }
        admin.setSignificantDisasterDescription(description);
        AdminDTO.insertAdmin(admin);
         


        JOptionPane.showMessageDialog(
                this,
                "Your information has been submitted successfully!\nThank you.",
                "Submission Successful",
                JOptionPane.INFORMATION_MESSAGE
        );
        MainAdminFrame maf= (MainAdminFrame) SwingUtilities.getWindowAncestor(this);
        maf.setVisible(false);
        Homepage1 homepage=new Homepage1();
        homepage.setVisible(true);
    }//GEN-LAST:event_ExperiencePanelSubmitButtonActionPerformed

    private void ExperiencePanelPreviousButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExperiencePanelPreviousButtonActionPerformed
        // TODO add your handling code here:
        MainAdminFrame frame = (MainAdminFrame) SwingUtilities.getWindowAncestor(this);
        frame.showCard("SKILLS");
    }//GEN-LAST:event_ExperiencePanelPreviousButtonActionPerformed

    private void AdminDisasterResponseDescriptionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AdminDisasterResponseDescriptionFocusGained
        // TODO add your handling code here:
        if (AdminDisasterResponseDescription.getText().equals("Must Include Disaster type, year, your role, key actions")) {
            AdminDisasterResponseDescription.setText("");
            AdminDisasterResponseDescription.setForeground(Color.BLACK);
        }

    }//GEN-LAST:event_AdminDisasterResponseDescriptionFocusGained

    private void AdminDisasterResponseDescriptionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AdminDisasterResponseDescriptionFocusLost
        // TODO add your handling code here:
        if (AdminDisasterResponseDescription.getText().isEmpty()) {
            AdminDisasterResponseDescription.setText("Must Include Disaster type, year, your role, key actions");
            AdminDisasterResponseDescription.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_AdminDisasterResponseDescriptionFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox AdminCommunityDisasterPreparedness;
    private javax.swing.JCheckBox AdminCrisisManagementTraining;
    private javax.swing.JCheckBox AdminCyclones;
    private javax.swing.JCheckBox AdminDRRCertification;
    private javax.swing.JTextArea AdminDisasterResponseDescription;
    private javax.swing.JCheckBox AdminEarthquakes;
    private javax.swing.JCheckBox AdminEmergencyResponseTraining;
    private javax.swing.JCheckBox AdminFires;
    private javax.swing.JCheckBox AdminFirstAidCertified;
    private javax.swing.JCheckBox AdminFloods;
    private javax.swing.JCheckBox AdminLandslides;
    private javax.swing.JCheckBox AdminMappingForDisasterManagement;
    private javax.swing.JCheckBox AdminNoFormalTraining;
    private javax.swing.JCheckBox AdminNoneDisaster;
    private javax.swing.JComboBox<String> AdminPreviousDisasterResponse;
    private javax.swing.JCheckBox AdminRiverErosion;
    private javax.swing.JCheckBox AdminStorms;
    private javax.swing.JCheckBox AdminVolunteerCoordinationTraining;
    private javax.swing.JButton ExperiencePanelPreviousButton;
    private javax.swing.JButton ExperiencePanelSubmitButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
