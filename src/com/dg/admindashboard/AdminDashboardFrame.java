/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.dg.admindashboard;

/**
 *
 * @author samih
 */
import com.dg.homepage.Homepage1;
import javax.swing.*;
import java.awt.*;

public class AdminDashboardFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminDashboardFrame.class.getName());

    /**
     * Creates new form AdminDashboardFrame
     */
    private String username;

    public AdminDashboardFrame(String username) {
        this.username = username;
        initComponents();
        setLocationRelativeTo(null);

        // Load default background internal frame
        SwingUtilities.invokeLater(() -> {

            AdminProfileFrame profileFrame
                    = new AdminProfileFrame(jDesktopPane1, username, true);

            jDesktopPane1.add(profileFrame);

            int margin = 20;

            profileFrame.setBounds(
                    margin,
                    margin,
                    jDesktopPane1.getWidth() - 2 * margin,
                    jDesktopPane1.getHeight() - 2 * margin
            );

            profileFrame.setVisible(true);

            try {
                profileFrame.setSelected(true);
                profileFrame.setMaximum(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            jDesktopPane1.revalidate();
            jDesktopPane1.repaint();
        });

        // Profile button listener
        adminMenubar1.getBtnProfile().addActionListener(e -> openProfile());
        adminMenubar1.getBtnViewVolunteer().addActionListener(e -> {

            ViewVolunteersFrame viewVolunteers = new ViewVolunteersFrame(username);

            jDesktopPane1.add(viewVolunteers);

            // Make same size as desktop
            int margin = 40;
            viewVolunteers.setBounds(
                    margin,
                    margin,
                    jDesktopPane1.getWidth() - 2 * margin,
                    jDesktopPane1.getHeight() - 2 * margin
            );

            viewVolunteers.setVisible(true);

            try {
                viewVolunteers.setSelected(true);
                viewVolunteers.setMaximum(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        adminMenubar1.getBtnAssignment().addActionListener(e -> {

            TaskAssignmentByAdmin task = new TaskAssignmentByAdmin(jDesktopPane1, username);

            jDesktopPane1.add(task);

            // Make same size as desktop
            int margin = 40;
            task.setBounds(
                    margin,
                    margin,
                    jDesktopPane1.getWidth() - 2 * margin,
                    jDesktopPane1.getHeight() - 2 * margin
            );

            task.setVisible(true);

            try {
                task.setSelected(true);
                task.setMaximum(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        adminMenubar1.getBtnSearch().addActionListener(e -> {

            SearchVolunteerFrame volunteer = new SearchVolunteerFrame(jDesktopPane1, username);

            jDesktopPane1.add(volunteer);

            // Make same size as desktop
            int margin = 40;
            volunteer.setBounds(
                    margin,
                    margin,
                    jDesktopPane1.getWidth() - 2 * margin,
                    jDesktopPane1.getHeight() - 2 * margin
            );

            volunteer.setVisible(true);

            try {
                volunteer.setSelected(true);
                volunteer.setMaximum(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        adminMenubar1.getBtnViewAlerts().addActionListener(e -> {

            ViewAdminAlertsFrame viewAlert = new ViewAdminAlertsFrame(jDesktopPane1, username);

            jDesktopPane1.add(viewAlert);

            // Make same size as desktop
            int margin = 40;
            viewAlert.setBounds(
                    margin,
                    margin,
                    jDesktopPane1.getWidth() - 2 * margin,
                    jDesktopPane1.getHeight() - 2 * margin
            );

            viewAlert.setVisible(true);

            try {
                viewAlert.setSelected(true);
                viewAlert.setMaximum(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        adminMenubar1.getBtnLogout().addActionListener(e -> {

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

    private void openProfile() {
        SwingUtilities.invokeLater(() -> {

            AdminProfileFrame profileFrame = new AdminProfileFrame(jDesktopPane1, username, true);

            jDesktopPane1.add(profileFrame);

            int margin = 20;
            int width = jDesktopPane1.getWidth() > 0 ? jDesktopPane1.getWidth() - 2 * margin : 600;
            int height = jDesktopPane1.getHeight() > 0 ? jDesktopPane1.getHeight() - 2 * margin : 400;

            profileFrame.setBounds(margin, margin, width, height);

            profileFrame.setVisible(true);

            try {
                profileFrame.setSelected(true);
                profileFrame.setMaximum(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            jDesktopPane1.revalidate();
            jDesktopPane1.repaint();
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        adminMenubar1 = new com.dg.admindashboard.AdminMenubar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jDesktopPane1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 691, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 618, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(adminMenubar1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jDesktopPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
            .addComponent(adminMenubar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.dg.admindashboard.AdminMenubar adminMenubar1;
    private javax.swing.JDesktopPane jDesktopPane1;
    // End of variables declaration//GEN-END:variables
}
