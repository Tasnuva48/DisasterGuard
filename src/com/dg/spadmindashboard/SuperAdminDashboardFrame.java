/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.dg.spadmindashboard;

import com.dg.homepage.Homepage1;
import java.awt.Color;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author TASNUVA
 */
public class SuperAdminDashboardFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SuperAdminDashboardFrame.class.getName());

    /**
     * Creates new form AdminDashboardFrame
     */
    private String username;

    public SuperAdminDashboardFrame(String username) {
        this.username = username;
        initComponents();

        openDefaultProfile(); // default profile internal frame open

        setLocationRelativeTo(null);

        //setBackground(new Color(0, 0, 0, 0));
        setLocationRelativeTo(null);
        superAdminMenuPanel1.getBtnViewAdmin().addActionListener(e -> {

            ViewAdminsFrame viewAdmins = new ViewAdminsFrame();

            jDesktopPane2.add(viewAdmins);

            // Make same size as desktop
            int margin = 40;
            viewAdmins.setBounds(
                    margin,
                    margin,
                    jDesktopPane2.getWidth() - 2 * margin,
                    jDesktopPane2.getHeight() - 2 * margin
            );

            viewAdmins.setVisible(true);

            try {
                viewAdmins.setSelected(true);
                viewAdmins.setMaximum(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        superAdminMenuPanel1.getBtnViewVolunteer().addActionListener(e -> {

            ViewVolunteersFrame viewVolunteers = new ViewVolunteersFrame();

            jDesktopPane2.add(viewVolunteers);
            int margin = 40;

            viewVolunteers.setBounds(
                    margin,
                    margin,
                    jDesktopPane2.getWidth() - 2 * margin,
                    jDesktopPane2.getHeight() - 2 * margin
            );

            viewVolunteers.setVisible(true);

            try {
                viewVolunteers.setSelected(true);
                viewVolunteers.setMaximum(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        superAdminMenuPanel1.getBtnProfile().addActionListener(e -> {

            // 1️⃣ Create the profile frame
            SuperAdminProfileFrame superProfile = new SuperAdminProfileFrame(jDesktopPane2, username);

            // 2️⃣ Add it to desktop pane
            jDesktopPane2.add(superProfile);

            // 3️⃣ Set bounds
            int margin = 40;
            superProfile.setBounds(
                    margin,
                    margin,
                    jDesktopPane2.getWidth() - 2 * margin,
                    jDesktopPane2.getHeight() - 2 * margin
            );

            // 4️⃣ Make it visible
            superProfile.setVisible(true);
            try {
                superProfile.setSelected(true);
                superProfile.setMaximum(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // 5️⃣ Attach listener to the Edit button AFTER the frame is created
        });
        superAdminMenuPanel1.getBtnSearchAdmin().addActionListener(e -> {

            SearchAdminFrame searchAdmins = new SearchAdminFrame(jDesktopPane2);

            jDesktopPane2.add(searchAdmins);
            int margin = 40;

            searchAdmins.setBounds(
                    margin,
                    margin,
                    jDesktopPane2.getWidth() - 2 * margin,
                    jDesktopPane2.getHeight() - 2 * margin
            );

            searchAdmins.setVisible(true);

            try {
                searchAdmins.setSelected(true);
                searchAdmins.setMaximum(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        superAdminMenuPanel1.getBtnSearchVolunteer().addActionListener(e -> {

            SearchVolunteerFrame searchVolunteers = new SearchVolunteerFrame(jDesktopPane2);

            jDesktopPane2.add(searchVolunteers);
            int margin = 40;

            searchVolunteers.setBounds(
                    margin,
                    margin,
                    jDesktopPane2.getWidth() - 2 * margin,
                    jDesktopPane2.getHeight() - 2 * margin
            );

            searchVolunteers.setVisible(true);

            try {
                searchVolunteers.setSelected(true);
                searchVolunteers.setMaximum(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        superAdminMenuPanel1.getBtnDisasterAlert().addActionListener(e -> {

            GenerateAlertsFrame alert = new GenerateAlertsFrame(jDesktopPane2, username);

            jDesktopPane2.add(alert);
            int margin = 40;

            alert.setBounds(
                    margin,
                    margin,
                    jDesktopPane2.getWidth() - 2 * margin,
                    jDesktopPane2.getHeight() - 2 * margin
            );

            alert.setVisible(true);

            try {
                alert.setSelected(true);
                alert.setMaximum(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        superAdminMenuPanel1.getBtnAlertStatus().addActionListener(e -> {

            ViewAlertStatusFrame alertStatus = new ViewAlertStatusFrame(jDesktopPane2, username);

            jDesktopPane2.add(alertStatus);

            // Make same size as desktop
            int margin = 40;
            alertStatus.setBounds(
                    margin,
                    margin,
                    jDesktopPane2.getWidth() - 2 * margin,
                    jDesktopPane2.getHeight() - 2 * margin
            );

            alertStatus.setVisible(true);

            try {
                alertStatus.setSelected(true);
                alertStatus.setMaximum(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        superAdminMenuPanel1.getBtnLogout().addActionListener(e -> {

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {

                // Close current dashboard
                this.dispose();

                // Open homepage / login frame
                new Homepage1().setVisible(true);
                // OR if you have LoginFrame:
                // new LoginFrame().setVisible(true);
            }
        });

    }

    private void openDefaultProfile() {

        SuperAdminProfileFrame superProfile
                = new SuperAdminProfileFrame(jDesktopPane2, username);

        jDesktopPane2.add(superProfile);

        int margin = 40;

        superProfile.setBounds(
                margin,
                margin,
                jDesktopPane2.getWidth() - 2 * margin,
                jDesktopPane2.getHeight() - 2 * margin
        );

        superProfile.setVisible(true);

        try {
            superProfile.setSelected(true);
            superProfile.setMaximum(true);
        } catch (Exception ex) {
            ex.printStackTrace();
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

        superAdminMenuPanel1 = new com.dg.spadmindashboard.SuperAdminMenuPanel();
        jDesktopPane2 = new javax.swing.JDesktopPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jDesktopPane2Layout = new javax.swing.GroupLayout(jDesktopPane2);
        jDesktopPane2.setLayout(jDesktopPane2Layout);
        jDesktopPane2Layout.setHorizontalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 621, Short.MAX_VALUE)
        );
        jDesktopPane2Layout.setVerticalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(superAdminMenuPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDesktopPane2))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDesktopPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(superAdminMenuPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane2;
    private com.dg.spadmindashboard.SuperAdminMenuPanel superAdminMenuPanel1;
    // End of variables declaration//GEN-END:variables
}
