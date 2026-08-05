/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.dg.volunteerform;

import com.dg.homepage.Homepage1;
import com.dg.dbconnection.VolunteerDTO;
import com.dg.dbconnection.VolunteerData;
import javax.swing.*;
import java.awt.*;
public class PhysicalCapabilitiesExperiencePanel extends javax.swing.JPanel {

    /**
     * Creates new form PhysicalCapabilitiesExperiencePanel
     */
    public PhysicalCapabilitiesExperiencePanel() {
        initComponents();
        setBackground(Color.WHITE);
        setOpaque(false);
        ButtonGroup liftGroup = new ButtonGroup();
        liftGroup.add(VolunteerLiftHeavyObjectsYes);
        liftGroup.add(VolunteerLiftHeavyObjectsNo);

        ButtonGroup terrainGroup = new ButtonGroup();
        terrainGroup.add(VolunteerWorkDifficultTerrainYes);
        terrainGroup.add(VolunteerWorkDifficultTerrainNo);

        ButtonGroup experienceGroup = new ButtonGroup();
        experienceGroup.add(VolunteerPreviousExperienceYes);
        experienceGroup.add(VolunteerPreviousExperienceNo);
        OrganizationsWorkedWith.setLineWrap(true);
    OrganizationsWorkedWith.setWrapStyleWord(true);
    DisastersWorkedOn.setLineWrap(true);
    DisastersWorkedOn.setWrapStyleWord(true);
    VolunteerRolesPerformed.setLineWrap(true);
    VolunteerRolesPerformed.setWrapStyleWord(true);

    jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    jScrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    VolunteerPreviousExperienceYes.addActionListener(e -> {
        OrganizationsWorkedWith.setEnabled(true);
        DisastersWorkedOn.setEnabled(true);
        VolunteerRolesPerformed.setEnabled(true);
    });

    VolunteerPreviousExperienceNo.addActionListener(e -> {
        OrganizationsWorkedWith.setEnabled(false);
        DisastersWorkedOn.setEnabled(false);
        VolunteerRolesPerformed.setEnabled(false);

        
    });

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

    private boolean validateForm() {
        //Physical Fitness Level
        if (VolunteerPhysicalFitnessLevel.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select your physical fitness level.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Lift heavy objects
        if (!VolunteerLiftHeavyObjectsYes.isSelected() && !VolunteerLiftHeavyObjectsNo.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please indicate if you can lift heavy objects.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Work in difficult terrain
        if (!VolunteerWorkDifficultTerrainYes.isSelected() && !VolunteerWorkDifficultTerrainNo.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please indicate if you can work in difficult terrain.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Previous disaster experience
        if (!VolunteerPreviousExperienceYes.isSelected() && !VolunteerPreviousExperienceNo.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please indicate if you have previous disaster relief experience.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // If YES, text areas must be filled
        if (VolunteerPreviousExperienceYes.isSelected()) {
            if (DisastersWorkedOn.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please specify which disasters you have worked on.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (VolunteerRolesPerformed.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please specify your roles performed.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (OrganizationsWorkedWith.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please specify the organizations you have worked with.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        // Health conditions (at least one OR None)
        boolean healthSelected = VolunteerAsthma.isSelected() || VolunteerAllergy.isSelected() || VolunteerBackProblems.isSelected() || VolunteerNoneHealthCondition.isSelected();
        if (!healthSelected) {
            JOptionPane.showMessageDialog(this, "Please select at least one health condition or 'None'.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // None select
        if (VolunteerNoneHealthCondition.isSelected()) {
            VolunteerAsthma.setSelected(false);
            VolunteerAllergy.setSelected(false);
            VolunteerBackProblems.setSelected(false);
        }

        return true; // All validations passed
    }

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
        VolunteerPhysicalFitnessLevel = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        VolunteerAsthma = new javax.swing.JCheckBox();
        VolunteerAllergy = new javax.swing.JCheckBox();
        VolunteerBackProblems = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        VolunteerLiftHeavyObjectsYes = new javax.swing.JRadioButton();
        VolunteerLiftHeavyObjectsNo = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        VolunteerWorkDifficultTerrainYes = new javax.swing.JRadioButton();
        VolunteerWorkDifficultTerrainNo = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        VolunteerPreviousExperienceYes = new javax.swing.JRadioButton();
        VolunteerPreviousExperienceNo = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        OrganizationsWorkedWith = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        DisastersWorkedOn = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        VolunteerRolesPerformed = new javax.swing.JTextArea();
        PhysicalCapabilitiesExperiencePanelPreviousButton = new javax.swing.JButton();
        PhysicalCapabilitiesExperiencePanelSubmitButton = new javax.swing.JButton();
        VolunteerNoneHealthCondition = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 20)); // NOI18N
        jLabel1.setText("Physical Capabilities");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel2.setText("Physical Fitness Level");

        VolunteerPhysicalFitnessLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Select Level---", "Excellent", "Good", "Average", "Limited Mobility" }));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel3.setText("Any Health Issue ?");

        VolunteerAsthma.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        VolunteerAsthma.setText("Asthma");

        VolunteerAllergy.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        VolunteerAllergy.setText("Allergy");

        VolunteerBackProblems.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        VolunteerBackProblems.setText("Back Problems");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel4.setText("Can lift heavy objects ?");

        VolunteerLiftHeavyObjectsYes.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        VolunteerLiftHeavyObjectsYes.setText("YES");

        VolunteerLiftHeavyObjectsNo.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        VolunteerLiftHeavyObjectsNo.setText("NO");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel5.setText("Can work in difficult terrain ?");

        VolunteerWorkDifficultTerrainYes.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        VolunteerWorkDifficultTerrainYes.setText("YES");

        VolunteerWorkDifficultTerrainNo.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        VolunteerWorkDifficultTerrainNo.setText("NO");

        jLabel6.setFont(new java.awt.Font("Arial Black", 0, 20)); // NOI18N
        jLabel6.setText("Experience");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel7.setText("Previous Disaster Relief Experience ?");

        VolunteerPreviousExperienceYes.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        VolunteerPreviousExperienceYes.setText("YES");

        VolunteerPreviousExperienceNo.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        VolunteerPreviousExperienceNo.setText("NO");
        VolunteerPreviousExperienceNo.addActionListener(this::VolunteerPreviousExperienceNoActionPerformed);

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel8.setText("If YES, describe :");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel9.setText("Which disasters worked on");

        OrganizationsWorkedWith.setColumns(20);
        OrganizationsWorkedWith.setRows(5);
        jScrollPane1.setViewportView(OrganizationsWorkedWith);

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel10.setText("What roles performed");

        DisastersWorkedOn.setColumns(20);
        DisastersWorkedOn.setRows(5);
        jScrollPane2.setViewportView(DisastersWorkedOn);

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel11.setText("Organizations worked with");

        VolunteerRolesPerformed.setColumns(20);
        VolunteerRolesPerformed.setRows(5);
        jScrollPane3.setViewportView(VolunteerRolesPerformed);

        PhysicalCapabilitiesExperiencePanelPreviousButton.setBackground(new java.awt.Color(0, 153, 153));
        PhysicalCapabilitiesExperiencePanelPreviousButton.setFont(new java.awt.Font("Arial Black", 0, 16)); // NOI18N
        PhysicalCapabilitiesExperiencePanelPreviousButton.setForeground(new java.awt.Color(204, 255, 255));
        PhysicalCapabilitiesExperiencePanelPreviousButton.setText("Previous");
        PhysicalCapabilitiesExperiencePanelPreviousButton.addActionListener(this::PhysicalCapabilitiesExperiencePanelPreviousButtonActionPerformed);

        PhysicalCapabilitiesExperiencePanelSubmitButton.setBackground(new java.awt.Color(0, 153, 153));
        PhysicalCapabilitiesExperiencePanelSubmitButton.setFont(new java.awt.Font("Arial Black", 0, 16)); // NOI18N
        PhysicalCapabilitiesExperiencePanelSubmitButton.setForeground(new java.awt.Color(204, 255, 255));
        PhysicalCapabilitiesExperiencePanelSubmitButton.setText("Submit");
        PhysicalCapabilitiesExperiencePanelSubmitButton.addActionListener(this::PhysicalCapabilitiesExperiencePanelSubmitButtonActionPerformed);

        VolunteerNoneHealthCondition.setText("None");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(VolunteerWorkDifficultTerrainYes)
                                .addGap(30, 30, 30)
                                .addComponent(VolunteerWorkDifficultTerrainNo))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3))
                                        .addGap(30, 30, 30)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(VolunteerPhysicalFitnessLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(VolunteerAsthma)
                                                    .addComponent(VolunteerLiftHeavyObjectsYes))
                                                .addGap(30, 30, 30)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(VolunteerLiftHeavyObjectsNo)
                                                    .addComponent(VolunteerAllergy)))))
                                    .addComponent(jLabel4)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(30, 30, 30)
                                        .addComponent(VolunteerPreviousExperienceYes)))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(VolunteerPreviousExperienceNo)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(VolunteerBackProblems)
                                        .addGap(30, 30, 30)
                                        .addComponent(VolunteerNoneHealthCondition)))))
                        .addGap(39, 152, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(PhysicalCapabilitiesExperiencePanelPreviousButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 460, Short.MAX_VALUE)
                        .addComponent(PhysicalCapabilitiesExperiencePanelSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1)
                                    .addComponent(jScrollPane2)))
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 95, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(VolunteerPhysicalFitnessLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(VolunteerAsthma)
                    .addComponent(VolunteerAllergy)
                    .addComponent(VolunteerBackProblems)
                    .addComponent(VolunteerNoneHealthCondition))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(VolunteerLiftHeavyObjectsYes, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(VolunteerLiftHeavyObjectsNo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(VolunteerWorkDifficultTerrainYes)
                    .addComponent(VolunteerWorkDifficultTerrainNo))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(VolunteerPreviousExperienceYes)
                    .addComponent(VolunteerPreviousExperienceNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PhysicalCapabilitiesExperiencePanelSubmitButton)
                            .addComponent(PhysicalCapabilitiesExperiencePanelPreviousButton)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jScrollPane1, jScrollPane3});

    }// </editor-fold>//GEN-END:initComponents

    private void PhysicalCapabilitiesExperiencePanelSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PhysicalCapabilitiesExperiencePanelSubmitButtonActionPerformed
        // TODO add your handling code here:
        
        if (!validateForm()) {
            return;
        }
         MainVolunteerFrame frame = (MainVolunteerFrame) SwingUtilities.getWindowAncestor(this);
        VolunteerData volunteer=frame.getVolunteerData();
        volunteer.setPhysicalFitnessLevel(
        VolunteerPhysicalFitnessLevel.getSelectedIndex() == 0 ? "No" : VolunteerPhysicalFitnessLevel.getSelectedItem().toString()
    );

    // 2️⃣ Lift heavy objects
    if (VolunteerLiftHeavyObjectsYes.isSelected()) {
        volunteer.setLiftHeavyObjects("Yes");
    } else {
        volunteer.setLiftHeavyObjects("No"); // default
    }

    // 3️⃣ Work in difficult terrain
    if (VolunteerWorkDifficultTerrainYes.isSelected()) {
        volunteer.setWorkDifficultTerrain("Yes");
    } else {
        volunteer.setWorkDifficultTerrain("No"); // default
    }

    // 4️⃣ Previous disaster experience
    if (VolunteerPreviousExperienceYes.isSelected()) {
        volunteer.setPreviousDisasterExperience("Yes");

        // Text areas: if empty, default to "None"
        volunteer.setDisastersWorkedOn(
            DisastersWorkedOn.getText().trim().isEmpty() ? "None" : DisastersWorkedOn.getText().trim()
        );

        volunteer.setRolesPerformed(
            VolunteerRolesPerformed.getText().trim().isEmpty() ? "None" : VolunteerRolesPerformed.getText().trim()
        );

        volunteer.setOrganizationsWorkedWith(
            OrganizationsWorkedWith.getText().trim().isEmpty() ? "None" : OrganizationsWorkedWith.getText().trim()
        );
    } else {
        
        
        volunteer.setPreviousDisasterExperience("No");
        volunteer.setDisastersWorkedOn("None");
        volunteer.setRolesPerformed("None");
        volunteer.setOrganizationsWorkedWith("None");
    }

    // 5️⃣ Health Issues
    volunteer.setAsthma(VolunteerAsthma.isSelected() ? "Yes" : "No");
    volunteer.setAllergy(VolunteerAllergy.isSelected() ? "Yes" : "No");
    volunteer.setBackProblems(VolunteerBackProblems.isSelected() ? "Yes" : "No");
    volunteer.setNoneProblems(VolunteerNoneHealthCondition.isSelected() ? "Yes" : "No");
    VolunteerDTO.insertVolunteer(volunteer);


        JOptionPane.showMessageDialog(
                this,
                "Your information has been submitted successfully!\nThank you for volunteering.",
                "Submission Successful",
                JOptionPane.INFORMATION_MESSAGE
        );
        frame.setVisible(false);
        
        Homepage1 homepage=new Homepage1();
        homepage.setVisible(true);
    }//GEN-LAST:event_PhysicalCapabilitiesExperiencePanelSubmitButtonActionPerformed

    private void VolunteerPreviousExperienceNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VolunteerPreviousExperienceNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VolunteerPreviousExperienceNoActionPerformed

    private void PhysicalCapabilitiesExperiencePanelPreviousButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PhysicalCapabilitiesExperiencePanelPreviousButtonActionPerformed
        // TODO add your handling code here:
        MainVolunteerFrame frame = (MainVolunteerFrame) SwingUtilities.getWindowAncestor(this);
        frame.showCard("SKILLS");
    }//GEN-LAST:event_PhysicalCapabilitiesExperiencePanelPreviousButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea DisastersWorkedOn;
    private javax.swing.JTextArea OrganizationsWorkedWith;
    private javax.swing.JButton PhysicalCapabilitiesExperiencePanelPreviousButton;
    private javax.swing.JButton PhysicalCapabilitiesExperiencePanelSubmitButton;
    private javax.swing.JCheckBox VolunteerAllergy;
    private javax.swing.JCheckBox VolunteerAsthma;
    private javax.swing.JCheckBox VolunteerBackProblems;
    private javax.swing.JRadioButton VolunteerLiftHeavyObjectsNo;
    private javax.swing.JRadioButton VolunteerLiftHeavyObjectsYes;
    private javax.swing.JCheckBox VolunteerNoneHealthCondition;
    private javax.swing.JComboBox<String> VolunteerPhysicalFitnessLevel;
    private javax.swing.JRadioButton VolunteerPreviousExperienceNo;
    private javax.swing.JRadioButton VolunteerPreviousExperienceYes;
    private javax.swing.JTextArea VolunteerRolesPerformed;
    private javax.swing.JRadioButton VolunteerWorkDifficultTerrainNo;
    private javax.swing.JRadioButton VolunteerWorkDifficultTerrainYes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
