/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.dg.volunteerform;

import com.dg.dbconnection.VolunteerData;
import javax.swing.*;
import java.awt.*;

public class MainVolunteerFrame extends javax.swing.JFrame {

    private CardLayout cardLayout;
    private VolunteerData volunteerData = new VolunteerData();

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainVolunteerFrame.class.getName());

    public MainVolunteerFrame() {
        initComponents();
        setTitle("Volunteer Registration Form");
        setMinimumSize(new Dimension(900, 800));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        if (mainVolunteerPanel1.getLayout() instanceof CardLayout cardLayout1) {
            cardLayout = cardLayout1;
            cardLayout.show(mainVolunteerPanel1, "PERSONAL");
        }
        pack();
    }
    public VolunteerData getVolunteerData() {
    return volunteerData;
}


    public void showCard(String name) {
        cardLayout.show(mainVolunteerPanel1, name);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel1 = new com.dg.volunteerform.HeaderPanel();
        mainVolunteerPanel1 = new com.dg.volunteerform.MainVolunteerPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(300, 80));
        setResizable(false);

        javax.swing.GroupLayout headerPanel1Layout = new javax.swing.GroupLayout(headerPanel1);
        headerPanel1.setLayout(headerPanel1Layout);
        headerPanel1Layout.setHorizontalGroup(
            headerPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        headerPanel1Layout.setVerticalGroup(
            headerPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 133, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainVolunteerPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 630, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainVolunteerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MainVolunteerFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.dg.volunteerform.HeaderPanel headerPanel1;
    private com.dg.volunteerform.MainVolunteerPanel mainVolunteerPanel1;
    // End of variables declaration//GEN-END:variables
}
