/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.dg.volunteerdashboard;

import com.dg.homepage.Homepage1;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author USER
 */
public class VolunteerDashboard extends javax.swing.JFrame {

    VolunteerMenu VolunteerMenubar1;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VolunteerDashboard.class.getName());

    /**
     * Creates new form VolunteerDashboard
     */
//    public VolunteerDashboard() {
//        initComponents();
//        //loadMenu();
//         applyMenuGradient(); 
//         
//         
//         
//         ///paknami
//         
//         
//    }
    private String username;

    public VolunteerDashboard(String username) {
        this.username = username;
        initComponents();

        SwingUtilities.invokeLater(() -> {
            ViewVolunteerProfile profileFrame = new ViewVolunteerProfile(ContentPanel, username, true);

            ContentPanel.add(profileFrame);

            int margin = 20;
            profileFrame.setBounds(
                    margin,
                    margin,
                    ContentPanel.getWidth() - 2 * margin,
                    ContentPanel.getHeight() - 2 * margin
            );

            profileFrame.setVisible(true);

            try {
                profileFrame.setSelected(true);
                profileFrame.setMaximum(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            ContentPanel.revalidate();
            ContentPanel.repaint();
        });

        VolunteerMenubar1 = new VolunteerMenu();
        applyMenuGradient();

        setLocationRelativeTo(null);
        // Profile button listener
        VolunteerMenubar1.getBtnProfile().addActionListener(e -> openProfile());
        VolunteerMenubar1.getBtnAlert().addActionListener(e -> viewAlerts());
        VolunteerMenubar1.getBtnWork().addActionListener(e -> viewTasks());
        //adminMenubar1.getBtnViewVolunteer().addActionListener(e -> {

        //ViewVolunteersFrame viewVolunteers = new ViewVolunteersFrame();
        //jDesktopPane1.add(viewVolunteers);
        // Make same size as desktop
        int margin = 40;
        VolunteerMenubar1.getBtnLogout().addActionListener(e -> {

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
//    viewVolunteers.setBounds(
//        margin,
//        margin,
//        jDesktopPane1.getWidth()-2*margin,
//        jDesktopPane1.getHeight()-2*margin
//    );

//    viewVolunteers.setVisible(true);
//
//    try {
//        viewVolunteers.setSelected(true);
//        viewVolunteers.setMaximum(true);
//    } catch (Exception ex) {
//        ex.printStackTrace();
//    }
//});
    }

    private void openProfile() {
        // Open the profile frame for this user
        ViewVolunteerProfile profileFrame = new ViewVolunteerProfile(ContentPanel, username, true);

        ContentPanel.add(profileFrame);

        // Optional: make profile frame fill the desktop
        int margin = 20;
        profileFrame.setBounds(
                margin,
                margin,
                ContentPanel.getWidth() - 2 * margin,
                ContentPanel.getHeight() - 2 * margin
        );

        profileFrame.setVisible(true);

        try {
            profileFrame.setSelected(true);  // Focus on the internal frame
            profileFrame.setMaximum(true);   // Maximize inside desktop pane
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void viewAlerts() {
        // Open the profile frame for this user
        ViewVolunteerAlertsFrame alert = new ViewVolunteerAlertsFrame(ContentPanel, username);

        ContentPanel.add(alert);

        // Optional: make profile frame fill the desktop
        int margin = 20;
        alert.setBounds(
                margin,
                margin,
                ContentPanel.getWidth() - 2 * margin,
                ContentPanel.getHeight() - 2 * margin
        );

        alert.setVisible(true);

        try {
            alert.setSelected(true);  // Focus on the internal frame
            alert.setMaximum(true);   // Maximize inside desktop pane
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void viewTasks() {
        // Open the profile frame for this user
        VolunteerTasksFrame tasks = new VolunteerTasksFrame(ContentPanel, username);

        ContentPanel.add(tasks);

        // Optional: make profile frame fill the desktop
        int margin = 20;
        tasks.setBounds(
                margin,
                margin,
                ContentPanel.getWidth() - 2 * margin,
                ContentPanel.getHeight() - 2 * margin
        );

        tasks.setVisible(true);

        try {
            tasks.setSelected(true);  // Focus on the internal frame
            tasks.setMaximum(true);   // Maximize inside desktop pane
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*  private void applyMenuGradient() {
    GradientMenuPanel gradientPanel = new GradientMenuPanel();
    gradientPanel.setLayout(menuContainerPanel.getLayout());
    gradientPanel.setPreferredSize(menuContainerPanel.getPreferredSize());

    // add menu inside gradient panel
    gradientPanel.add(new VolunteerMenu());

    this.remove(menuContainerPanel);
    this.add(gradientPanel, java.awt.BorderLayout.WEST);

    this.revalidate();
    this.repaint();
}*/
 /*private void applyMenuGradient() {
    GradientMenuPanel gradientPanel = new GradientMenuPanel();

    gradientPanel.setLayout(new java.awt.BorderLayout());
    gradientPanel.setPreferredSize(menuContainerPanel.getPreferredSize());

    // add menu INSIDE gradient
    gradientPanel.add(new VolunteerMenu(), java.awt.BorderLayout.CENTER);

    // replace old menu container
    getContentPane().remove(menuContainerPanel);
    getContentPane().add(gradientPanel, java.awt.BorderLayout.LINE_START);

    getContentPane().revalidate();
    getContentPane().repaint();
}*/
    private void applyMenuGradient() {
        GradientMenuPanel gradientPanel = new GradientMenuPanel();

        // FORCE SIZE (THIS IS THE KEY)
        gradientPanel.setPreferredSize(menuContainerPanel.getPreferredSize());
        gradientPanel.setMinimumSize(menuContainerPanel.getMinimumSize());
        gradientPanel.setMaximumSize(menuContainerPanel.getMaximumSize());

        gradientPanel.setLayout(new java.awt.BorderLayout());

        // add menu inside gradient
        gradientPanel.add(VolunteerMenubar1, java.awt.BorderLayout.CENTER);

        // replace panel
        getContentPane().remove(menuContainerPanel);
        getContentPane().add(gradientPanel, java.awt.BorderLayout.LINE_START);

        getContentPane().revalidate();
        getContentPane().repaint();
    }

    private void loadMenu() {
        menuContainerPanel.removeAll();
        menuContainerPanel.setLayout(new java.awt.BorderLayout());

        VolunteerMenu menu = new VolunteerMenu();
        menuContainerPanel.add(menu, java.awt.BorderLayout.CENTER);

        menuContainerPanel.revalidate();
        menuContainerPanel.repaint();
    }

//private void showInternalFrame(JInternalFrame frame) {
//    desktopPane.removeAll();
//    desktopPane.add(frame);
//    frame.setVisible(true);
//    try {
//        frame.setMaximum(true); // full size
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}
//public void showAdminTable() {
//    //ContentPanel.removeAll();
//
//    ViewAdminsFrame adminFrame = new ViewAdminsFrame();
//    adminFrame.setVisible(true);
//
//    ContentPanel.add(adminFrame);
//    try {
//        adminFrame.setSelected(true);
//    } catch (java.beans.PropertyVetoException e) {
//        e.printStackTrace();
//    }
//
//    ContentPanel.revalidate();
//    ContentPanel.repaint();
//}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ContentPanel = new javax.swing.JDesktopPane();
        menuContainerPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout ContentPanelLayout = new javax.swing.GroupLayout(ContentPanel);
        ContentPanel.setLayout(ContentPanelLayout);
        ContentPanelLayout.setHorizontalGroup(
            ContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 519, Short.MAX_VALUE)
        );
        ContentPanelLayout.setVerticalGroup(
            ContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        getContentPane().add(ContentPanel, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout menuContainerPanelLayout = new javax.swing.GroupLayout(menuContainerPanel);
        menuContainerPanel.setLayout(menuContainerPanelLayout);
        menuContainerPanelLayout.setHorizontalGroup(
            menuContainerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 215, Short.MAX_VALUE)
        );
        menuContainerPanelLayout.setVerticalGroup(
            menuContainerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 538, Short.MAX_VALUE)
        );

        getContentPane().add(menuContainerPanel, java.awt.BorderLayout.PAGE_START);

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
        java.awt.EventQueue.invokeLater(() -> new VolunteerDashboard("jTextField1").setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane ContentPanel;
    private javax.swing.JPanel menuContainerPanel;
    // End of variables declaration//GEN-END:variables
}

/*class GradientMenuPanel extends javax.swing.JPanel {
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;

        g2d.setRenderingHint(
            java.awt.RenderingHints.KEY_RENDERING,
            java.awt.RenderingHints.VALUE_RENDER_QUALITY
        );

        java.awt.Color top = new java.awt.Color(230, 235, 240); // light
        java.awt.Color bottom = new java.awt.Color(52, 120, 246); // blue

        java.awt.GradientPaint gp =
            new java.awt.GradientPaint(0, 0, top, 0, getHeight(), bottom);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
 */
class GradientMenuPanel extends javax.swing.JPanel {

    public GradientMenuPanel() {
        setOpaque(true);
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;

        g2d.setRenderingHint(
                java.awt.RenderingHints.KEY_RENDERING,
                java.awt.RenderingHints.VALUE_RENDER_QUALITY
        );

        java.awt.Color top = new java.awt.Color(30, 0, 179);
        java.awt.Color bottom = new java.awt.Color(106, 77, 255);

        java.awt.GradientPaint gp
                = new java.awt.GradientPaint(0, 0, top, 0, getHeight(), bottom);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
