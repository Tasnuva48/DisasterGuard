/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dg.volunteerdashboard;

/**
 *
 * @author samih
 */
import com.dg.admindashboard.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import com.dg.dao.AlertDAO;
import com.dg.dao.AlertStatusDAO;
import com.dg.model.Alert;
import java.sql.*;
import javax.swing.*;

import com.dg.dao.AlertForwardDAO;
import com.dg.dao.VolunteerAlertDAO;
import java.awt.Color;

public class VolunteerAlertDetailsFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form AdminAlertDetailsFrame
     */
    private JDesktopPane desktop;
    private String username;
    private int alertId;

    public VolunteerAlertDetailsFrame(JDesktopPane desktop, String username, int alertId) {
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        this.desktop = desktop;
        this.username = username;
        this.alertId = alertId;

        Color normalColorbtnClear = new Color(117, 117, 117);     // olive
        Color hoverColorbtnClear = new Color(140, 140, 140);      // lighter green

        btnSubmit.setBackground(normalColorbtnClear);

        btnSubmit.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSubmit.setBackground(hoverColorbtnClear);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSubmit.setBackground(normalColorbtnClear);
            }
        });

// Inside your constructor, after initComponents();
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnYES);
        group.add(rbtnNO);

// Action listener for submit
        btnSubmit.addActionListener(e -> submitResponse());
        loadAlertDetails();

    }

    private void loadAlertDetails() {

        try {

            AlertDAO dao = new AlertDAO();

            Alert alert = dao.getAlertById(alertId);

            if (alert != null) {

                lblTitle.setText(alert.getTitle());
                lblType.setText(alert.getType());

                String type = alert.getType();
                lblType.setText(type);

                if (type.equalsIgnoreCase("Warning")) {
                    lblType.setBackground(new Color(255, 193, 7));   // Yellow
                    lblType.setForeground(Color.BLACK);
                } else if (type.equalsIgnoreCase("Emergency")) {
                    lblType.setBackground(new Color(220, 53, 69));   // Red
                    lblType.setForeground(Color.WHITE);
                } else if (type.equalsIgnoreCase("Info")) {
                    lblType.setBackground(new Color(13, 110, 253));  // Blue
                    lblType.setForeground(Color.WHITE);
                } else {
                    lblType.setBackground(Color.WHITE);
                    lblType.setForeground(Color.BLACK);
                }

                lblDivision.setText(alert.getDivision());
                lblDistrict.setText(alert.getDistrict());

                lblCreated.setText(
                        String.valueOf(alert.getCreatedAt())
                );

                if (alert.getExpiresAt() != null) {
                    lblExpiry.setText(
                            String.valueOf(alert.getExpiresAt())
                    );

                } else {
                    lblExpiry.setText("No expiry");

                }

                txtMessage.setText(alert.getMessage());
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(this,
                    "Cannot load alert");
        }
    }

    private void submitResponse() {
        try {
            // 1️⃣ Determine response
            String response;
            if (rbtnYES.isSelected()) {
                response = "Yes"; // volunteer will work
            } else if (rbtnNO.isSelected()) {
                response = "No"; // volunteer will not work
            } else {
                JOptionPane.showMessageDialog(this, "Please select Yes or No before submitting.");
                return;
            }

            // 2️⃣ Update database
            VolunteerAlertDAO dao = new VolunteerAlertDAO();
            dao.updateVolunteerResponse(alertId, username, response);

            // 3️⃣ Feedback to user
            JOptionPane.showMessageDialog(this, "Your response has been recorded as: " + response);

            // 4️⃣ Disable radio buttons and submit button after submission
            rbtnYES.setEnabled(false);
            rbtnNO.setEnabled(false);
            btnSubmit.setEnabled(false);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to submit your response.");
        }
    }

    /*
    private void openResponses() {

    VolunteerResponsesFrame frame =
            new VolunteerResponsesFrame(alertId);

    desktop.add(frame);

    frame.setVisible(true);
}
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblType = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblDivision = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblCreated = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblDistrict = new javax.swing.JLabel();
        lblExpiry = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMessage = new javax.swing.JTextArea();
        buttonPanel = new javax.swing.JPanel();
        btnSubmit = new javax.swing.JButton();
        lblQuestion = new javax.swing.JLabel();
        rbtnYES = new javax.swing.JRadioButton();
        rbtnNO = new javax.swing.JRadioButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Alert Details");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/orgbasicinfoicon.png"))); // NOI18N
        jLabel8.setText("Alert Details");
        jLabel8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(102, 102, 102)));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));

        lblTitle.setBackground(new java.awt.Color(255, 255, 255));
        lblTitle.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 20)); // NOI18N
        lblTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/warningyellow.png"))); // NOI18N
        lblTitle.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));
        lblTitle.setOpaque(true);

        lblType.setBackground(new java.awt.Color(255, 255, 255));
        lblType.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblType.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblType.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 200, 200), 1, true));
        lblType.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblType.setOpaque(true);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Division");

        lblDivision.setBackground(new java.awt.Color(248, 248, 248));
        lblDivision.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDivision.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 200, 200), 1, true));
        lblDivision.setOpaque(true);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Created At");

        lblCreated.setBackground(new java.awt.Color(248, 248, 248));
        lblCreated.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCreated.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 200, 200), 1, true));
        lblCreated.setOpaque(true);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("District");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Expires At");

        lblDistrict.setBackground(new java.awt.Color(248, 248, 248));
        lblDistrict.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDistrict.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 200, 200), 1, true));
        lblDistrict.setOpaque(true);

        lblExpiry.setBackground(new java.awt.Color(248, 248, 248));
        lblExpiry.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblExpiry.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 200, 200), 1, true));
        lblExpiry.setOpaque(true);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCreated, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDivision, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblType, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, 7))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDivision, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCreated, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));

        jLabel7.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 20)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/message30.png"))); // NOI18N
        jLabel7.setText("Message");
        jLabel7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));

        txtMessage.setEditable(false);
        txtMessage.setColumns(20);
        txtMessage.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMessage.setRows(5);
        jScrollPane1.setViewportView(txtMessage);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 999, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 895, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        buttonPanel.setBackground(new java.awt.Color(255, 255, 255));
        buttonPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true));

        btnSubmit.setBackground(new java.awt.Color(117, 117, 117));
        btnSubmit.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnSubmit.setForeground(new java.awt.Color(255, 255, 255));
        btnSubmit.setText("Submit");
        btnSubmit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblQuestion.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        lblQuestion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/question16.png"))); // NOI18N
        lblQuestion.setText("Would you like to volunteer for this disaster?");

        rbtnYES.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        rbtnYES.setForeground(new java.awt.Color(0, 204, 0));
        rbtnYES.setText("Yes,I want to volunteer");
        rbtnYES.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        rbtnNO.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        rbtnNO.setForeground(new java.awt.Color(204, 0, 0));
        rbtnNO.setText("No,not available");
        rbtnNO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rbtnNO.addActionListener(this::rbtnNOActionPerformed);

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblQuestion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(buttonPanelLayout.createSequentialGroup()
                        .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbtnYES)
                            .addComponent(rbtnNO))
                        .addGap(818, 818, 818)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addGap(815, 815, 815)
                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQuestion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtnYES)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtnNO)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(150, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 150, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbtnNOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnNOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnNOActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSubmit;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCreated;
    private javax.swing.JLabel lblDistrict;
    private javax.swing.JLabel lblDivision;
    private javax.swing.JLabel lblExpiry;
    private javax.swing.JLabel lblQuestion;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblType;
    private javax.swing.JRadioButton rbtnNO;
    private javax.swing.JRadioButton rbtnYES;
    private javax.swing.JTextArea txtMessage;
    // End of variables declaration//GEN-END:variables
}
