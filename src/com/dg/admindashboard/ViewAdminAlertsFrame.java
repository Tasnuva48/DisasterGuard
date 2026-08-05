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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class ViewAdminAlertsFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form ViewAdminAlertsFrame
     */
    private String username;
    private JDesktopPane desktop;
    private BaseAlertsController controller;

    public ViewAdminAlertsFrame(JDesktopPane desktop, String username) {
        initComponents();

// 1. View Details (Primary Blue)
        Color normalView = new Color(25, 118, 210);
        Color hoverView = new Color(33, 150, 243);
        btnView.setBackground(normalView);
        btnView.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnView.setBackground(hoverView);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnView.setBackground(normalView);
            }
        });

// 2. Volunteer Responses (Distinct Purple)
        Color normalVol = new Color(106, 27, 154);
        Color hoverVol = new Color(142, 36, 170);
        btnVolunteerResponses.setBackground(normalVol);
        btnVolunteerResponses.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVolunteerResponses.setBackground(hoverVol);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVolunteerResponses.setBackground(normalVol);
            } // Fixed variable
        });

// 3. Refresh (System Teal)
        Color normalRef = new Color(0, 121, 107);
        Color hoverRef = new Color(0, 150, 136);
        btnRefresh.setBackground(normalRef);
        btnRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRefresh.setBackground(hoverRef);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRefresh.setBackground(normalRef);
            }
        });

        // scrollbar invisible
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        // clean look
        jScrollPane1.setBorder(null);
        this.username = username;
        this.desktop = desktop;
        controller = new AdminAlertsController(tblAlerts, username, desktop, this);

        controller.setupTable(); // sets columns
        controller.loadAlerts(); // loads data

        // ===== TABLE BASIC STYLE =====
        tblAlerts.setRowHeight(30);
        tblAlerts.setBackground(Color.WHITE);
        tblAlerts.setFillsViewportHeight(true);
        jScrollPane1.getViewport().setBackground(Color.WHITE);

// ===== HEADER STYLE =====
        JTableHeader header = tblAlerts.getTableHeader();
        header.setBackground(new Color(10, 28, 54));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {

                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                label.setHorizontalAlignment(JLabel.CENTER);
                label.setBackground(new Color(10, 28, 54));
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setOpaque(true);

                return label;
            }
        });

// ===== ZEBRA + CENTER =====
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {

                JLabel c = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                // ===== DEFAULT TEXT COLOR =====
                c.setForeground(Color.BLACK);

                // ===== ZEBRA BACKGROUND =====
                if (isSelected) {
                    c.setBackground(new Color(8, 114, 138));
                    c.setForeground(Color.WHITE);
                } else {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(245, 249, 255));
                    }
                }

                // ==============================
                // 🔥 TYPE COLOR (Column 2)
                // ==============================
                if (!isSelected && column == 2 && value != null) {

                    String type = value.toString().toLowerCase();

                    if (type.equalsIgnoreCase("emergency")) {
                        c.setForeground(new Color(198, 40, 40));

                    } else if (type.equalsIgnoreCase("warning")) {
                        c.setForeground(new Color(245, 124, 0));

                    } else if (type.equalsIgnoreCase("info")) {
                        c.setForeground(new Color(21, 101, 192));
                    }
                }

                // ==============================
                // 🔥 READ / UNREAD COLOR (Column 3)
                // ==============================
                if (!isSelected && column == 3 && value != null) {

                    String status = value.toString().toLowerCase();

                    if (status.equalsIgnoreCase("unread")) {
                        c.setForeground(new Color(220, 0, 0)); // Red

                    } else if (status.equalsIgnoreCase("read")) {
                        c.setForeground(new Color(46, 125, 50)); // Green
                    }
                }

                // ==============================
// 🔥 FORWARDED / NOT FORWARDED (Column 4)
// ==============================
                if (!isSelected && column == 4 && value != null) {

                    String forward = value.toString().toLowerCase();

                    if (forward.equalsIgnoreCase("Not Forwarded")) {
                        c.setForeground(new Color(106, 27, 154)); // Red

                    } else if (forward.equalsIgnoreCase("Forwarded")) {
                        c.setForeground(new Color(0, 150, 0)); // Green
                    }
                }

                c.setHorizontalAlignment(JLabel.CENTER);
                c.setOpaque(true);

                return c;
            }
        };

        for (int i = 0; i < tblAlerts.getColumnCount(); i++) {
            tblAlerts.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        tblAlerts.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        btnRefresh.addActionListener(e -> controller.loadAlerts());
        btnView.addActionListener(e -> controller.openDetails());
        btnVolunteerResponses.addActionListener(e -> ((AdminAlertsController) controller).openVolunteerResponses());

    }

    /*
    private void setupTable() {

    DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // make table read-only
        }
    };

    model.setColumnIdentifiers(new String[]{
        "ID",
        "Title",
        "Type",
        "Read Status",
        "Forward Status",
        "Date"
    });

    tblAlerts.setModel(model);
}
   private void loadAlerts() {

    DefaultTableModel model =
            (DefaultTableModel) tblAlerts.getModel();

    model.setRowCount(0);

    try (Connection conn = SQLiteConnect.Connectordb()) {

        AlertStatusDAO dao = new AlertStatusDAO();

        ResultSet rs = dao.getAlertsForAdmin(username, conn);

        while (rs.next()) {

            model.addRow(new Object[]{
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("alert_type"),
                rs.getString("read_status"),
                rs.getString("forward_status"),
                rs.getString("created_at")
            });
        }

    } catch (Exception e) {
        e.printStackTrace();

        JOptionPane.showMessageDialog(this,
                "Error loading alerts");
    }
}
   // In ViewAdminAlertsFrame.java

private void openVolunteerResponses() {
    int row = tblAlerts.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Please select an alert first");
        return;
    }

    int alertId = (int) tblAlerts.getValueAt(row, 0);

    VolunteerResponsesFrame frame = new VolunteerResponsesFrame(desktop, username, alertId);
    desktop.add(frame);

    int margin = 40;
    frame.setBounds(margin, margin,
            desktop.getWidth() - 2 * margin,
            desktop.getHeight() - 2 * margin);

    frame.setVisible(true);

    try {
        frame.setSelected(true);
        frame.setMaximum(true);
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}
   private void openDetails() {

    int row = tblAlerts.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(this,
                "Please select an alert first");
        return;
    }

    int alertId = (int) tblAlerts.getValueAt(row, 0);

    // Mark as read
    AlertStatusDAO dao = new AlertStatusDAO();
    dao.markAsRead(alertId, username);

    // Open details frame
    AdminAlertDetailsFrame frame =
            new AdminAlertDetailsFrame(desktop,
                    username,
                    alertId);
    desktop.add(frame);

    int margin=40;
    frame.setBounds(
        margin,
        margin,
        desktop.getWidth()-2*margin,
        desktop.getHeight()-2*margin
    );

    frame.setVisible(true);

    try {
        frame.setSelected(true);
        frame.setMaximum(true);
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    loadAlerts(); // refresh list
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tablePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAlerts = new javax.swing.JTable();
        buttonPanel = new javax.swing.JPanel();
        btnView = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnVolunteerResponses = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("View Alerts");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 23)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/overview.png"))); // NOI18N
        jLabel1.setText("All Alert Overview");
        jLabel1.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("View and monitor all alerts and their status");
        jLabel2.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(tblAlerts);

        tblAlerts.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblAlerts);

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
        );

        buttonPanel.setBackground(new java.awt.Color(255, 255, 255));
        buttonPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, new java.awt.Color(102, 0, 102)));

        btnView.setBackground(new java.awt.Color(25, 118, 210));
        btnView.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnView.setForeground(new java.awt.Color(255, 255, 255));
        btnView.setText("View Details");
        btnView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnRefresh.setBackground(new java.awt.Color(0, 121, 207));
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Refresh");
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnVolunteerResponses.setBackground(new java.awt.Color(106, 27, 154));
        btnVolunteerResponses.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnVolunteerResponses.setForeground(new java.awt.Color(255, 255, 255));
        btnVolunteerResponses.setText("Volunteer Responses");
        btnVolunteerResponses.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGap(245, 245, 245)
                .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 175, Short.MAX_VALUE)
                .addComponent(btnVolunteerResponses, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 175, Short.MAX_VALUE)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(246, 246, 246))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVolunteerResponses, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tablePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnView;
    private javax.swing.JButton btnVolunteerResponses;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JTable tblAlerts;
    // End of variables declaration//GEN-END:variables
}
