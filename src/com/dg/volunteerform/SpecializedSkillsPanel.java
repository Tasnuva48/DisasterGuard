/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.dg.volunteerform;

import com.dg.dbconnection.VolunteerData;
import javax.swing.*;
import java.awt.*;

public class SpecializedSkillsPanel extends javax.swing.JPanel {

    /**
     * Creates new form SpecializedSkillsPanel
     */
    public SpecializedSkillsPanel() {
        initComponents();
        setBackground(Color.WHITE);
        setOpaque(false);

        MedicalTrainingOption.setEnabled(false);
        VehicleType.setEnabled(false);

        VolunteerBanglaSkill.setEnabled(false);
        VolunteerEnglishSkill.setEnabled(false);
        VolunteerRegionalLanguageSkill.setEnabled(false);

        VolunteerITSkill.setEnabled(false);
        VolunteerEngineeringSkill.setEnabled(false);
        VolunteerCommunicationsSkill.setEnabled(false);
        VolunteerMedicalTraining.addActionListener(this::VolunteerMedicalTrainingActionPerformed);
        VolunteerDriving.addActionListener(this::VolunteerDrivingActionPerformed);
        VolunteerLanguageSkills.addActionListener(this::VolunteerLanguageSkillsActionPerformed);
        VolunteerTechnicalSkill.addActionListener(this::VolunteerTechnicalSkillActionPerformed);

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
    // Medical Training

    private void VolunteerMedicalTrainingActionPerformed(java.awt.event.ActionEvent evt) {
        MedicalTrainingOption.setEnabled(VolunteerMedicalTraining.isSelected());
    }

// Driving
    private void VolunteerDrivingActionPerformed(java.awt.event.ActionEvent evt) {
        VehicleType.setEnabled(VolunteerDriving.isSelected());
    }

// Language Skills
    private void VolunteerLanguageSkillsActionPerformed(java.awt.event.ActionEvent evt) {
        boolean selected = VolunteerLanguageSkills.isSelected();
        VolunteerBanglaSkill.setEnabled(selected);
        VolunteerEnglishSkill.setEnabled(selected);
        VolunteerRegionalLanguageSkill.setEnabled(selected);
    }

// Technical Skills
    private void VolunteerTechnicalSkillActionPerformed(java.awt.event.ActionEvent evt) {
        boolean selected = VolunteerTechnicalSkill.isSelected();
        VolunteerITSkill.setEnabled(selected);
        VolunteerEngineeringSkill.setEnabled(selected);
        VolunteerCommunicationsSkill.setEnabled(selected);
    }

// Check medical training
    public boolean validateMedicalTraining() throws ValidationException {
        if (VolunteerMedicalTraining.isSelected()) {
            if (MedicalTrainingOption.getSelectedIndex() == 0) {
                MedicalTrainingOption.requestFocus();
                throw new ValidationException("Please select your Medical Training option.");
            }
        }
        return true;
    }

// Check driving
    public boolean validateDriving() throws ValidationException {
        if (VolunteerDriving.isSelected()) {
            if (VehicleType.getSelectedIndex() == 0) {
                VehicleType.requestFocus();
                throw new ValidationException("Please select your Vehicle Type.");
            }
        }
        return true;
    }

// Check language skills
    public boolean validateLanguageSkills() throws ValidationException {
        if (VolunteerLanguageSkills.isSelected()) {
            if (!VolunteerBanglaSkill.isSelected() && !VolunteerEnglishSkill.isSelected() && !VolunteerRegionalLanguageSkill.isSelected()) {
                throw new ValidationException("Please select at least one language skill.");
            }
        }
        return true;
    }

// Check technical skills
    public boolean validateTechnicalSkills() throws ValidationException {
        if (VolunteerTechnicalSkill.isSelected()) {
            if (!VolunteerITSkill.isSelected() && !VolunteerEngineeringSkill.isSelected() && !VolunteerCommunicationsSkill.isSelected()) {
                throw new ValidationException("Please select at least one technical skill.");
            }
        }
        return true;
    }

// Optional skills
    public boolean validateOptionalSkills() throws ValidationException {
        if (!VolunteerCookingSkill.isSelected() && !VolunteerCounsellingSkill.isSelected()
                && !VolunteerTeachingSkill.isSelected() && !VolunteerConstructionSkill.isSelected()
                && !VolunteerMedicalTraining.isSelected() && !VolunteerSearchAndRescue.isSelected()
                && !VolunteerSwimming.isSelected() && !VolunteerDriving.isSelected()
                && !VolunteerLanguageSkills.isSelected() && !VolunteerTechnicalSkill.isSelected()) {
            throw new ValidationException("Please select at least one skill.");
        }
        return true;
    }

// Combined validation
    public boolean validateAllInputs() throws ValidationException {
        return validateOptionalSkills()
                && validateMedicalTraining()
                && validateDriving()
                && validateLanguageSkills()
                && validateTechnicalSkills();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSlider1 = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        VolunteerMedicalTraining = new javax.swing.JCheckBox();
        MedicalTrainingOption = new javax.swing.JComboBox<>();
        VolunteerSearchAndRescue = new javax.swing.JCheckBox();
        VolunteerSwimming = new javax.swing.JCheckBox();
        VolunteerDriving = new javax.swing.JCheckBox();
        VehicleType = new javax.swing.JComboBox<>();
        VolunteerLanguageSkills = new javax.swing.JCheckBox();
        VolunteerBanglaSkill = new javax.swing.JCheckBox();
        VolunteerEnglishSkill = new javax.swing.JCheckBox();
        VolunteerRegionalLanguageSkill = new javax.swing.JCheckBox();
        VolunteerTechnicalSkill = new javax.swing.JCheckBox();
        VolunteerITSkill = new javax.swing.JCheckBox();
        VolunteerCommunicationsSkill = new javax.swing.JCheckBox();
        VolunteerEngineeringSkill = new javax.swing.JCheckBox();
        VolunteerCookingSkill = new javax.swing.JCheckBox();
        VolunteerCounsellingSkill = new javax.swing.JCheckBox();
        VolunteerTeachingSkill = new javax.swing.JCheckBox();
        VolunteerConstructionSkill = new javax.swing.JCheckBox();
        SpecializedSkillsPanelPreviousButton = new javax.swing.JButton();
        SpecializedSkillsPanelNextButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 20)); // NOI18N
        jLabel1.setText("Specialized Skills");

        VolunteerMedicalTraining.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        VolunteerMedicalTraining.setText("Medical Training");

        MedicalTrainingOption.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Select Option---", "Doctor", "Nurse", "First Aid Certified", "Paramedic" }));

        VolunteerSearchAndRescue.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        VolunteerSearchAndRescue.setText("Search & Rescue");

        VolunteerSwimming.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        VolunteerSwimming.setText("Swimming");

        VolunteerDriving.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        VolunteerDriving.setText("Driving");

        VehicleType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Select Vehicle Type---", "Car", "Motorcycle", "Bicycle", "Truck", " " }));

        VolunteerLanguageSkills.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        VolunteerLanguageSkills.setText("Language Skills");

        VolunteerBanglaSkill.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        VolunteerBanglaSkill.setText("Bangla");

        VolunteerEnglishSkill.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        VolunteerEnglishSkill.setText("English");

        VolunteerRegionalLanguageSkill.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        VolunteerRegionalLanguageSkill.setText("Regional Languages");

        VolunteerTechnicalSkill.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        VolunteerTechnicalSkill.setText("Technical Skills");

        VolunteerITSkill.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        VolunteerITSkill.setText("IT");

        VolunteerCommunicationsSkill.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        VolunteerCommunicationsSkill.setText("Communications");
        VolunteerCommunicationsSkill.addActionListener(this::VolunteerCommunicationsSkillActionPerformed);

        VolunteerEngineeringSkill.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        VolunteerEngineeringSkill.setText("Engineering");

        VolunteerCookingSkill.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        VolunteerCookingSkill.setText("Cooking");

        VolunteerCounsellingSkill.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        VolunteerCounsellingSkill.setText("Counselling");

        VolunteerTeachingSkill.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        VolunteerTeachingSkill.setText("Teaching");

        VolunteerConstructionSkill.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        VolunteerConstructionSkill.setText("Construction/Repair Work");

        SpecializedSkillsPanelPreviousButton.setBackground(new java.awt.Color(0, 153, 153));
        SpecializedSkillsPanelPreviousButton.setFont(new java.awt.Font("Arial Black", 0, 16)); // NOI18N
        SpecializedSkillsPanelPreviousButton.setForeground(new java.awt.Color(204, 255, 255));
        SpecializedSkillsPanelPreviousButton.setText("Previous");
        SpecializedSkillsPanelPreviousButton.addActionListener(this::SpecializedSkillsPanelPreviousButtonActionPerformed);

        SpecializedSkillsPanelNextButton.setBackground(new java.awt.Color(0, 153, 153));
        SpecializedSkillsPanelNextButton.setFont(new java.awt.Font("Arial Black", 0, 16)); // NOI18N
        SpecializedSkillsPanelNextButton.setForeground(new java.awt.Color(204, 255, 255));
        SpecializedSkillsPanelNextButton.setText("Next");
        SpecializedSkillsPanelNextButton.addActionListener(this::SpecializedSkillsPanelNextButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(VolunteerDriving)
                            .addComponent(VolunteerLanguageSkills)
                            .addComponent(VolunteerSwimming)
                            .addComponent(jLabel1)
                            .addComponent(VolunteerMedicalTraining)
                            .addComponent(VolunteerTechnicalSkill)
                            .addComponent(VolunteerSearchAndRescue)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(51, 51, 51)
                                        .addComponent(VolunteerBanglaSkill)
                                        .addGap(50, 50, 50)
                                        .addComponent(VolunteerEnglishSkill))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(VolunteerITSkill)
                                            .addComponent(VolunteerCookingSkill))
                                        .addGap(50, 50, 50)
                                        .addComponent(VolunteerEngineeringSkill))
                                    .addComponent(VolunteerCounsellingSkill)
                                    .addComponent(VolunteerTeachingSkill)
                                    .addComponent(VolunteerConstructionSkill))
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(VolunteerCommunicationsSkill)
                                    .addComponent(VolunteerRegionalLanguageSkill)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(SpecializedSkillsPanelPreviousButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 460, Short.MAX_VALUE)
                                .addComponent(SpecializedSkillsPanelNextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(MedicalTrainingOption, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(VehicleType, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(VolunteerMedicalTraining)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MedicalTrainingOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VolunteerSearchAndRescue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VolunteerSwimming)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VolunteerDriving)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VehicleType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VolunteerLanguageSkills)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(VolunteerBanglaSkill)
                    .addComponent(VolunteerEnglishSkill)
                    .addComponent(VolunteerRegionalLanguageSkill))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VolunteerTechnicalSkill)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(VolunteerITSkill)
                    .addComponent(VolunteerEngineeringSkill)
                    .addComponent(VolunteerCommunicationsSkill))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VolunteerCookingSkill)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VolunteerCounsellingSkill)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VolunteerTeachingSkill)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VolunteerConstructionSkill)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SpecializedSkillsPanelPreviousButton)
                    .addComponent(SpecializedSkillsPanelNextButton))
                .addContainerGap(39, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void VolunteerCommunicationsSkillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VolunteerCommunicationsSkillActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VolunteerCommunicationsSkillActionPerformed

    private void SpecializedSkillsPanelNextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SpecializedSkillsPanelNextButtonActionPerformed
        // TODO add your handling code here:
        try {
            validateAllInputs();

            MainVolunteerFrame frame = (MainVolunteerFrame) SwingUtilities.getWindowAncestor(this);
            VolunteerData volunteer = frame.getVolunteerData();
            volunteer.setMedicalTraining(VolunteerMedicalTraining.isSelected() ? MedicalTrainingOption.getSelectedItem().toString() : "No");
            volunteer.setSearchAndRescue(VolunteerSearchAndRescue.isSelected() ? "Yes" : "No");
            volunteer.setSwimming(VolunteerSwimming.isSelected() ? "Yes" : "No");
            volunteer.setDriving(VolunteerDriving.isSelected() ? VehicleType.getSelectedItem().toString() : "No");
            volunteer.setLanguageSkills(VolunteerLanguageSkills.isSelected()
                    ? (VolunteerBanglaSkill.isSelected() ? "Bangla," : "")
                    + (VolunteerEnglishSkill.isSelected() ? "English," : "")
                    + (VolunteerRegionalLanguageSkill.isSelected() ? "Regional" : "")
                    : "No");
            volunteer.setTechnicalSkills(VolunteerTechnicalSkill.isSelected()
                    ? (VolunteerITSkill.isSelected() ? "IT," : "")
                    + (VolunteerEngineeringSkill.isSelected() ? "Engineering," : "")
                    + (VolunteerCommunicationsSkill.isSelected() ? "Communications" : "")
                    : "No");
            volunteer.setCooking(VolunteerCookingSkill.isSelected() ? "Yes" : "No");
            volunteer.setCounselling(VolunteerCounsellingSkill.isSelected() ? "Yes" : "No");
            volunteer.setTeaching(VolunteerTeachingSkill.isSelected() ? "Yes" : "No");
            volunteer.setConstructionWork(VolunteerConstructionSkill.isSelected() ? "Yes" : "No");
            frame.showCard("EXPERIENCE");
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_SpecializedSkillsPanelNextButtonActionPerformed

    private void SpecializedSkillsPanelPreviousButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SpecializedSkillsPanelPreviousButtonActionPerformed
        // TODO add your handling code here:
        MainVolunteerFrame frame = (MainVolunteerFrame) SwingUtilities.getWindowAncestor(this);
        frame.showCard("ADDRESS");
    }//GEN-LAST:event_SpecializedSkillsPanelPreviousButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> MedicalTrainingOption;
    private javax.swing.JButton SpecializedSkillsPanelNextButton;
    private javax.swing.JButton SpecializedSkillsPanelPreviousButton;
    private javax.swing.JComboBox<String> VehicleType;
    private javax.swing.JCheckBox VolunteerBanglaSkill;
    private javax.swing.JCheckBox VolunteerCommunicationsSkill;
    private javax.swing.JCheckBox VolunteerConstructionSkill;
    private javax.swing.JCheckBox VolunteerCookingSkill;
    private javax.swing.JCheckBox VolunteerCounsellingSkill;
    private javax.swing.JCheckBox VolunteerDriving;
    private javax.swing.JCheckBox VolunteerEngineeringSkill;
    private javax.swing.JCheckBox VolunteerEnglishSkill;
    private javax.swing.JCheckBox VolunteerITSkill;
    private javax.swing.JCheckBox VolunteerLanguageSkills;
    private javax.swing.JCheckBox VolunteerMedicalTraining;
    private javax.swing.JCheckBox VolunteerRegionalLanguageSkill;
    private javax.swing.JCheckBox VolunteerSearchAndRescue;
    private javax.swing.JCheckBox VolunteerSwimming;
    private javax.swing.JCheckBox VolunteerTeachingSkill;
    private javax.swing.JCheckBox VolunteerTechnicalSkill;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSlider jSlider1;
    // End of variables declaration//GEN-END:variables
}
