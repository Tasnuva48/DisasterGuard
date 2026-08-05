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
import com.dg.model.Task;
import com.dg.model.*;

import org.jdesktop.swingx.JXDatePicker;
import com.dg.dbconnection.SQLiteConnect;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import com.dg.spadmindashboard.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class AssignTasksToVolunteers extends javax.swing.JInternalFrame {

    /**
     * Creates new form AssignTasksToVolunteers
     */
    private int taskId;
    private String taskName;
    private int alertId;
    private JDesktopPane desktop;
    private String adminUsername;

    public AssignTasksToVolunteers(JDesktopPane desktop, int taskId, String taskName, int alertId, String username) {
        initComponents();

        Color normalColorbtnClear = new Color(117, 117, 117);     // olive
        Color hoverColorbtnClear = new Color(140, 140, 140);      // lighter green

        btnClear.setBackground(normalColorbtnClear);

        btnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnClear.setBackground(hoverColorbtnClear);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnClear.setBackground(normalColorbtnClear);
            }
        });

        Color normalColorbtnAssign = new Color(20, 77, 0);     // merun
        Color hoverColorbtnAssign = new Color(48, 179, 0);      // lighter merun

        btnAssign.setBackground(normalColorbtnAssign);

        btnAssign.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAssign.setBackground(hoverColorbtnAssign);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAssign.setBackground(normalColorbtnAssign);
            }
        });

        // scrollbar invisible
        //jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
       // jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        // clean look
        //jScrollPane1.setBorder(null);
        this.desktop = desktop;
        this.taskId = taskId;
        this.taskName = taskName;
        this.alertId = alertId;
        this.adminUsername = username;
        this.setTitle(taskName);
        setupVolunteerTable();
        loadVolunteers();
    }

    private void setupVolunteerTable() {

        DefaultTableModel model = new DefaultTableModel();

        model.setColumnIdentifiers(new String[]{
            "Full Name",
            "Username",
            "Division",
            "District",
            "Status",
            "Completed At"
        });

        tblVolunteers.setModel(model);

        tblVolunteers.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        );

        tblVolunteers.setRowSelectionAllowed(true);

        // ===== BASIC UI =====
        tblVolunteers.setRowHeight(30);
        tblVolunteers.setBackground(Color.WHITE);
        tblVolunteers.setFillsViewportHeight(true);
        jScrollPane1.getViewport().setBackground(Color.WHITE);

        // ===== HEADER DESIGN =====
        JTableHeader header = tblVolunteers.getTableHeader();
        header.setBackground(new Color(10, 28, 54));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        // center header text
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

        // ===== ZEBRA + STATUS COLOR =====
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

                // center text
                if (c instanceof JLabel) {
                    ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                }

                // ===== STATUS COLOR =====
                if (!isSelected && column == 4) { // Status column
                    String status = value != null ? value.toString() : "";

                    if (status.equalsIgnoreCase("Completed")) {
                        c.setForeground(new Color(0, 153, 0)); // green
                    } else if (status.equalsIgnoreCase("Assigned")) {
                        c.setForeground(new Color(67, 100, 247)); // blue
                    }
                    else if (status.equalsIgnoreCase("Not Assigned")) {
                        c.setForeground(new Color(204, 0, 0)); // red
                    }
                    else
                    {
                        c.setForeground(new Color(0, 0, 0)); // black
                    }
                }

                return c;
            }
        };

        // apply renderer সব column এ
        for (int i = 0; i < tblVolunteers.getColumnCount(); i++) {
            tblVolunteers.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        // column auto resize
        tblVolunteers.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    private void loadVolunteers() {

        VolunteerAlertDAO dao = new VolunteerAlertDAO();

        List<Object[]> volunteers
                = dao.getVolunteersWithAssignmentStatus(alertId, taskId);

        DefaultTableModel model
                = (DefaultTableModel) tblVolunteers.getModel();

        model.setRowCount(0);

        for (Object[] row : volunteers) {
            model.addRow(row);
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
        tblVolunteers = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnClear = new javax.swing.JButton();
        btnAssign = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(tblVolunteers);

        tblVolunteers.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblVolunteers);

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, new java.awt.Color(102, 0, 102)));

        btnClear.setBackground(new java.awt.Color(0, 153, 0));
        btnClear.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Clear");
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClear.addActionListener(this::btnClearActionPerformed);

        btnAssign.setBackground(new java.awt.Color(153, 0, 153));
        btnAssign.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        btnAssign.setForeground(new java.awt.Color(255, 255, 255));
        btnAssign.setText("Assign");
        btnAssign.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAssign.addActionListener(this::btnAssignActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(300, 300, 300)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 488, Short.MAX_VALUE)
                .addComponent(btnAssign, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(300, 300, 300))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear)
                    .addComponent(btnAssign))
                .addGap(25, 25, 25))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/available.png"))); // NOI18N
        jLabel1.setText("Available Volunteers");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Volunteers who are ready and available to participate in this activity");

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
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tablePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAssignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAssignActionPerformed
        // TODO add your handling code here:
        int[] selectedRows = tblVolunteers.getSelectedRows();

        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this,
                    "Select at least one volunteer");
            return;
        }

        DefaultTableModel model
                = (DefaultTableModel) tblVolunteers.getModel();

        TaskAssignmentDAO dao = new TaskAssignmentDAO();

        for (int row : selectedRows) {

            String volunteerUsername
                    = model.getValueAt(row, 1).toString();

            TaskAssignment assignment
                    = new TaskAssignment(
                            taskId,
                            volunteerUsername,
                            adminUsername,
                            "Assigned"
                    );

            dao.assignTask(assignment);
        }

        JOptionPane.showMessageDialog(this,
                "Task Assigned Successfully");

        //  VERY IMPORTANT — Refresh table
        loadVolunteers();
    }//GEN-LAST:event_btnAssignActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        tblVolunteers.clearSelection();
    }//GEN-LAST:event_btnClearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAssign;
    private javax.swing.JButton btnClear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JTable tblVolunteers;
    // End of variables declaration//GEN-END:variables
}
