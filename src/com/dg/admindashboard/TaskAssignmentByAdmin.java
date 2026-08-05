/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dg.admindashboard;

/**
 *
 * @author samih
 */
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import com.dg.model.Alert;
import com.dg.dao.*;

import org.jdesktop.swingx.JXDatePicker;
import com.dg.dbconnection.SQLiteConnect;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class TaskAssignmentByAdmin extends javax.swing.JInternalFrame {

    /**
     * Creates new form TaskAssignmentByAdmin
     */
    private JDesktopPane desktop;
    private String adminUsername;

    public TaskAssignmentByAdmin(JDesktopPane desktop, String username) {
        initComponents();
        
        Color normalColorbtnViewTasks = new Color(97, 4, 95);     // merun
        Color hoverColorbtnViewTasks = new Color(122, 7, 77);      // lighter merun

        btnViewTasks.setBackground(normalColorbtnViewTasks);

        btnViewTasks.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnViewTasks.setBackground(hoverColorbtnViewTasks);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnViewTasks.setBackground(normalColorbtnViewTasks);
            }
        });
        
        // scrollbar invisible
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        // clean look
        jScrollPane1.setBorder(null);
        getContentPane().setBackground(Color.WHITE);
        ((JComponent) getContentPane()).setOpaque(true);

        tablePanel.setBackground(Color.WHITE);
        jPanel1.setBackground(Color.WHITE);

        jScrollPane1.getViewport().setBackground(Color.WHITE);

        tblAlerts.setOpaque(true);
        tblAlerts.setBackground(Color.WHITE);
        this.desktop = desktop;
        this.adminUsername = username;
        setupTable();
        loadAlerts();

        btnViewTasks.addActionListener(e -> openTaskList());
    }

    private void setupTable() {

        DefaultTableModel model = new DefaultTableModel();

        model.setColumnIdentifiers(new String[]{
            "ID",
            "Alert Title",
            "Division",
            "District",
            "Address",
            "Expiry"
        });

        tblAlerts.setModel(model);

        tblAlerts.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        // ===== BASIC UI =====
        tblAlerts.setRowHeight(30);
        tblAlerts.setBackground(Color.WHITE);
        tblAlerts.setFillsViewportHeight(true);
        jScrollPane1.getViewport().setBackground(Color.WHITE);

        // ===== HEADER DESIGN =====
        JTableHeader header = tblAlerts.getTableHeader();
        header.setBackground(new Color(10, 28, 54));   // dark blue
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

        // ===== ZEBRA ROW STYLE =====
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    c.setBackground(new Color(8, 114, 138));
                    c.setForeground(Color.WHITE);
                } else {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(245, 249, 255));
                    }
                    c.setForeground(Color.BLACK);
                }

                if (c instanceof JLabel) {
                    ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                }

                return c;
            }
        };

        for (int i = 0; i < tblAlerts.getColumnCount(); i++) {
            tblAlerts.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        // Hide ID column (আগের মতোই থাকবে)
        tblAlerts.getColumnModel().getColumn(0).setMinWidth(0);
        tblAlerts.getColumnModel().getColumn(0).setMaxWidth(0);
        tblAlerts.getColumnModel().getColumn(0).setWidth(0);

        tblAlerts.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    // =====================================================
    //  LOAD ALERTS FROM DATABASE
    // =====================================================
    private void loadAlerts() {

        try {

            AlertDAO dao = new AlertDAO();
            List<Alert> alerts = dao.getAllAlertsWithId();

            DefaultTableModel model
                    = (DefaultTableModel) tblAlerts.getModel();

            model.setRowCount(0);

            for (Alert a : alerts) {

                model.addRow(new Object[]{
                    a.getId(),
                    a.getTitle(),
                    a.getDivision(),
                    a.getDistrict(),
                    a.getAddress(),
                    a.getExpiresAt() == null
                    ? "No Expiry"
                    : a.getExpiresAt()
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading alerts: " + ex.getMessage());
        }
    }

    // =====================================================
    //  OPEN TASK LIST FOR SELECTED ALERT
    // =====================================================
    private void openTaskList() {

        int selectedRow = tblAlerts.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Select an alert first");
            return;
        }

        DefaultTableModel model
                = (DefaultTableModel) tblAlerts.getModel();

        //  Get hidden alert ID
        int alertId = (int) model.getValueAt(selectedRow, 0);

        String alertTitle
                = model.getValueAt(selectedRow, 1).toString();

        //  Open Task List Frame
        TaskListForAssignment frame
                = new TaskListForAssignment(desktop, alertId, alertTitle, adminUsername);

        desktop.add(frame);
        // Make same size as desktop
        int margin = 40;
        frame.setBounds(
                margin,
                margin,
                desktop.getWidth() - 2 * margin,
                desktop.getHeight() - 2 * margin
        );

        frame.setVisible(true);

        try {
            frame.setSelected(true);
            frame.setMaximum(true);
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

        tablePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAlerts = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnViewTasks = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("All Alerts\n");

        tablePanel.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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
            .addComponent(jScrollPane1)
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, new java.awt.Color(102, 0, 102)));

        btnViewTasks.setBackground(new java.awt.Color(97, 4, 95));
        btnViewTasks.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnViewTasks.setForeground(new java.awt.Color(255, 255, 255));
        btnViewTasks.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/viewtask.png"))); // NOI18N
        btnViewTasks.setText("View Tasks");
        btnViewTasks.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(570, 570, 570)
                .addComponent(btnViewTasks, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(570, 570, 570))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnViewTasks)
                .addGap(20, 20, 20))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/viewandmanage.png"))); // NOI18N
        jLabel1.setText("Assigned Alerts");
        jLabel1.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("View and manage alerts assigned by the administration");
        jLabel2.setOpaque(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnViewTasks;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JTable tblAlerts;
    // End of variables declaration//GEN-END:variables
}
