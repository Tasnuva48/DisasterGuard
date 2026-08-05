/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dg.admindashboard;

/**
 *
 * @author samih
 */
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

public class AdminAlertDetailsFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form AdminAlertDetailsFrame
     */
    private JDesktopPane desktop;
    private String username;
    private int alertId;

    public AdminAlertDetailsFrame(JDesktopPane desktop, String username, int alertId) {
        initComponents();
        Color normalColor = new Color(97, 4, 95);     // merun
        Color hoverColor = new Color(122, 7, 77);      // lighter merun

        btnForward.setBackground(normalColor);

        btnForward.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnForward.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnForward.setBackground(normalColor);
            }
        });
        getContentPane().setBackground(new java.awt.Color(255, 255, 255));

        this.desktop = desktop;
        this.username = username;
        this.alertId = alertId;
        loadAlertDetails();   // show data
        markAsRead();         // mark read

        btnForward.addActionListener(e -> forwardAlert());
    }

    private void loadAlertDetails() {

        try {

            AlertDAO dao = new AlertDAO();

            Alert alert = dao.getAlertById(alertId);

            if (alert != null) {

                lblTitle.setText(alert.getTitle());
                lblType.setText(alert.getType());

                if (alert.getType().equalsIgnoreCase("Warning")) {
                    lblType.setBackground(new Color(255, 193, 7));   // Yellow
                    lblType.setForeground(Color.BLACK);
                } else if (alert.getType().equalsIgnoreCase("Emergency")) {
                    lblType.setBackground(new Color(220, 53, 69));   // Red
                    lblType.setForeground(Color.WHITE);
                } else if (alert.getType().equalsIgnoreCase("Info")) {
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
                txtMessage.setCaretColor(new java.awt.Color(0, 0, 0, 0));
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(this,
                    "Cannot load alert");
        }
    }

    private void markAsRead() {

        try {

            AlertStatusDAO dao
                    = new AlertStatusDAO();

            if (!dao.isAlreadyRead(alertId, username)) {
                dao.markAsRead(alertId, username);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void forwardAlert() {

        try {

            AlertForwardDAO forwardDAO
                    = new AlertForwardDAO();

            VolunteerAlertDAO volunteerDAO
                    = new VolunteerAlertDAO();

            // Prevent duplicate forwarding
            if (forwardDAO.isForwarded(alertId, username)) {

                JOptionPane.showMessageDialog(this,
                        "This alert is already forwarded.");

                return;
            }

            // Mark as forwarded
            forwardDAO.markAsForwarded(
                    alertId, username);

            // Send to volunteers (by division)
            volunteerDAO.sendToVolunteersByDivision(
                    alertId, username);

            JOptionPane.showMessageDialog(this,
                    "Alert forwarded successfully!");

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(this,
                    "Forward failed!");
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

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblType = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblDivision = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblCreated = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblExpiry = new javax.swing.JLabel();
        lblDistrict = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMessage = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnForward = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Alert Details");
        setOpaque(true);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 612));

        lblTitle.setBackground(new java.awt.Color(255, 255, 255));
        lblTitle.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        lblTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/warningyellow.png"))); // NOI18N
        lblTitle.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));

        lblType.setBackground(new java.awt.Color(255, 255, 255));
        lblType.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblType.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblType.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblType.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 200, 200), 1, true));
        lblType.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblType.setOpaque(true);
        lblType.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(97, 4, 95));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/divisionicon.png"))); // NOI18N
        jLabel3.setText("Division");

        lblDivision.setBackground(new java.awt.Color(248, 248, 248));
        lblDivision.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblDivision.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 200, 200), 1, true));
        lblDivision.setOpaque(true);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(97, 4, 95));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/districicon.png"))); // NOI18N
        jLabel4.setText("District");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(97, 4, 95));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/createdaticon.png"))); // NOI18N
        jLabel5.setText("Created at");

        lblCreated.setBackground(new java.awt.Color(248, 248, 248));
        lblCreated.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblCreated.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 200, 200), 1, true));
        lblCreated.setOpaque(true);

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(97, 4, 95));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/expiresaticon.png"))); // NOI18N
        jLabel6.setText("Expires at");

        lblExpiry.setBackground(new java.awt.Color(248, 248, 248));
        lblExpiry.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblExpiry.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 200, 200), 1, true));
        lblExpiry.setOpaque(true);

        lblDistrict.setBackground(new java.awt.Color(248, 248, 248));
        lblDistrict.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblDistrict.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 200, 200), 1, true));
        lblDistrict.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 840, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addComponent(lblType, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblDivision, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCreated, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDivision, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCreated, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        jPanel2.setPreferredSize(new java.awt.Dimension(1000, 211));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(97, 4, 95));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/messageicon.png"))); // NOI18N
        jLabel7.setText("Message");
        jLabel7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        txtMessage.setEditable(false);
        txtMessage.setBackground(new java.awt.Color(255, 255, 255));
        txtMessage.setColumns(20);
        txtMessage.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtMessage.setRows(5);
        jScrollPane1.setViewportView(txtMessage);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 956, Short.MAX_VALUE)
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/details-48.png"))); // NOI18N
        jLabel8.setText("Alert Details");
        jLabel8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(102, 102, 102)));
        jLabel8.setOpaque(true);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(1000, 100));

        btnForward.setBackground(new java.awt.Color(153, 0, 153));
        btnForward.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        btnForward.setForeground(new java.awt.Color(255, 255, 255));
        btnForward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/forwardmsgicon.png"))); // NOI18N
        btnForward.setText("Forward To Volenteers");
        btnForward.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(355, 355, 355)
                .addComponent(btnForward, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(355, 355, 355))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnForward, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 144, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(144, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnForward;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCreated;
    private javax.swing.JLabel lblDistrict;
    private javax.swing.JLabel lblDivision;
    private javax.swing.JLabel lblExpiry;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblType;
    private javax.swing.JTextArea txtMessage;
    // End of variables declaration//GEN-END:variables
}
