/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dg.spadmindashboard;

/**
 *
 * @author samih
 */
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import com.dg.dbconnection.SQLiteConnect;
import javax.swing.*;
import java.awt.*;
import com.dg.dao.*;
import com.dg.model.*;
import java.util.List;
import java.util.ArrayList;

public class ViewAlertDetailsFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form ViewAlertDetailsFrame
     */
    private JDesktopPane desktop;
    private String username;
    private int alertId;
    private AlertDAO alertDAO;
    private AlertStatusDAO statusDAO;

    public ViewAlertDetailsFrame(JDesktopPane desktopPane, String username, int alertId) {
        initComponents();
        
        // scrollbar invisible
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        // clean look
        jScrollPane1.setBorder(null);
        
        this.desktop = desktopPane;
        this.username = username;
        this.alertId = alertId;
        alertDAO = new AlertDAO();
        statusDAO = new AlertStatusDAO();
        setupAdminTable();

        modernTableUI();
        tablePanel.setBackground(Color.WHITE);
        jPanel1.setBackground(Color.WHITE);
        getContentPane().setBackground(Color.WHITE);
        jScrollPane1.setBackground(Color.WHITE);

        loadAlertInfo();// Step 1: setup columns
        loadAdminReadStatus();    // Step 2: load data

        // Hook buttons
        btnRefresh.addActionListener(e -> loadAdminReadStatus());
        btnClose.addActionListener(e -> this.dispose());
    }

    private void setupAdminTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
            "Username",
            "Full Name",
            "Status",
            "Read At"
        });
        tblAlertDetails.setModel(model);
    }

    private void modernTableUI() {
        tblAlertDetails.setRowHeight(32);
        tblAlertDetails.setShowGrid(false);
        tblAlertDetails.setIntercellSpacing(new Dimension(0, 0));
        tblAlertDetails.setSelectionBackground(new Color(220, 235, 255));
        tblAlertDetails.setSelectionForeground(Color.BLACK);
        tblAlertDetails.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblAlertDetails.setForeground(new Color(60, 60, 60));

        tblAlertDetails.getTableHeader().setDefaultRenderer(
                new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                JLabel header = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                header.setBackground(new Color(9, 30, 58));
                header.setForeground(Color.WHITE);
                header.setFont(new Font("Segoe UI", Font.BOLD, 14));
                header.setHorizontalAlignment(JLabel.CENTER);
                header.setOpaque(true);

                return header;
            }
        }
        );

        tblAlertDetails.setDefaultRenderer(Object.class,
                new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                java.awt.Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground(row % 2 == 0
                            ? Color.WHITE
                            : new Color(245, 249, 255));
                }

                // Status column color
                if (column == 2) {
                    String status = value.toString();

                    if (status.equalsIgnoreCase("Read")) {
                        c.setForeground(new Color(0, 153, 51));   // green
                    } else if (status.equalsIgnoreCase("Unread")) {
                        c.setForeground(Color.RED);               // red
                    } else {
                        c.setForeground(Color.BLACK);
                    }
                } else {
                    c.setForeground(Color.BLACK);
                }

                setHorizontalAlignment(JLabel.CENTER);

                return c;
            }
        });

        jScrollPane1.getViewport().setBackground(Color.WHITE);
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder());

        tblAlertDetails.setBackground(Color.WHITE);
        tblAlertDetails.setFillsViewportHeight(true);
    }

    private void loadAlertInfo() {
        try {
            // ✅ Use DAO to fetch alert
            Alert alert = alertDAO.getAlertById(alertId);
            if (alert != null) {
                lblTitle.setText(alert.getTitle());
                lblType.setText(": " + alert.getType());

                java.text.SimpleDateFormat sdfOutput = new java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a");

                // Format created_at
                if (alert.getCreatedAt() != null) {
                    lblCreated.setText(": " + sdfOutput.format(alert.getCreatedAt()));
                } else {
                    lblCreated.setText("-");
                }

                // Format expires_at
                if (alert.getExpiresAt() != null) {
                    lblExpiry.setText(": " + sdfOutput.format(alert.getExpiresAt()));
                } else {
                    lblExpiry.setText("-");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading alert info.");
        }
    }

    private void loadAdminReadStatus() {
        DefaultTableModel model = (DefaultTableModel) tblAlertDetails.getModel();
        model.setRowCount(0); // Clear previous rows

        int total = 0;
        int readCount = 0;

        try {
            // ✅ Use DAO to get all admin read statuses
            List<AdminAlertStatus> statusList = statusDAO.getAdminReadStatus(alertId);
            java.text.SimpleDateFormat sdfOutput = new java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a");

            for (AdminAlertStatus status : statusList) {
                String readAt = status.getReadAt() != null ? sdfOutput.format(status.getReadAt()) : "-";
                model.addRow(new Object[]{
                    status.getUsername(),
                    status.getFullName(),
                    status.getReadStatus(),
                    readAt
                });

                total++;
                if ("Read".equalsIgnoreCase(status.getReadStatus())) {
                    readCount++;
                }
            }

            // Update summary labels
            lblTotal.setText(": "+String.valueOf(total));
            lblRead.setText(": "+String.valueOf(readCount));
            lblUnread.setText(": "+String.valueOf(total - readCount));

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading alert details.");
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

        alertInfoPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblType = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblCreated = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblExpiry = new javax.swing.JLabel();
        tablePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAlertDetails = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnRefresh = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblRead = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblUnread = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("View Alert Details");

        alertInfoPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        jPanel2.setLayout(new java.awt.CardLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        lblTitle.setBackground(new java.awt.Color(255, 255, 255));
        lblTitle.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        lblTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/warningyellow.png"))); // NOI18N
        lblTitle.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(102, 102, 102)));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/alerttype.png"))); // NOI18N
        jLabel2.setText("Type");

        lblType.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/timeicon.png"))); // NOI18N
        jLabel3.setText("Created");

        lblCreated.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/expiry.png"))); // NOI18N
        jLabel4.setText("Expiry");

        lblExpiry.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblCreated, javax.swing.GroupLayout.DEFAULT_SIZE, 923, Short.MAX_VALUE)
                            .addComponent(lblType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblExpiry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCreated, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.add(jPanel3, "card2");

        javax.swing.GroupLayout alertInfoPanelLayout = new javax.swing.GroupLayout(alertInfoPanel);
        alertInfoPanel.setLayout(alertInfoPanelLayout);
        alertInfoPanelLayout.setHorizontalGroup(
            alertInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, alertInfoPanelLayout.createSequentialGroup()
                .addContainerGap(101, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1046, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );
        alertInfoPanelLayout.setVerticalGroup(
            alertInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(alertInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(tblAlertDetails);

        tblAlertDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblAlertDetails);

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(102, 102, 102)));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(1298, 89));

        btnRefresh.setFont(new java.awt.Font("Segoe UI Variable", 0, 14)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/refresh16.png"))); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnClose.setBackground(new java.awt.Color(204, 0, 0));
        btnClose.setFont(new java.awt.Font("Segoe UI Variable", 0, 14)); // NOI18N
        btnClose.setForeground(new java.awt.Color(255, 255, 255));
        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/close16.png"))); // NOI18N
        btnClose.setText("Close");
        btnClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 0));
        jLabel6.setText("Read");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 204));
        jLabel5.setText("Total");

        lblTotal.setBackground(new java.awt.Color(255, 255, 255));
        lblTotal.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTotal.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        lblRead.setBackground(new java.awt.Color(255, 255, 255));
        lblRead.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        lblRead.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 0, 0));
        jLabel7.setText("Unread");

        lblUnread.setBackground(new java.awt.Color(255, 255, 255));
        lblUnread.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        lblUnread.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblUnread.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1088, 1088, 1088))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRead, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUnread, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRefresh)
                        .addGap(76, 76, 76)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(118, 118, 118))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblRead, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUnread, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel1.add(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tablePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(alertInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(alertInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel alertInfoPanel;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCreated;
    private javax.swing.JLabel lblExpiry;
    private javax.swing.JLabel lblRead;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblType;
    private javax.swing.JLabel lblUnread;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JTable tblAlertDetails;
    // End of variables declaration//GEN-END:variables
}
