/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dg.admindashboard;

import javax.swing.JDesktopPane;

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

import org.jdesktop.swingx.JXDatePicker;
import com.dg.dbconnection.SQLiteConnect;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import com.dg.spadmindashboard.*;

public class TaskListForAssignment extends javax.swing.JInternalFrame {

    /**
     * Creates new form TaskListForAssignment
     */
    private int alertId;
    private String alertTitle;
    private JDesktopPane desktop;
    private String adminUsername;

    public TaskListForAssignment(JDesktopPane desktop, int alertId, String alertTitle, String username) {
        initComponents();
        
        Color normalColorbtnAssign = new Color(97, 4, 95);     // merun
        Color hoverColorbtnAssign = new Color(122, 7, 77);      // lighter merun

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
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        // clean look
        jScrollPane1.setBorder(null);
        this.desktop = desktop;
        this.alertId = alertId;
        this.alertTitle = alertTitle;
        this.adminUsername = username;
        this.setTitle("Tasks for " + alertTitle);
        setupTaskTable();
        loadTasks();
        SwingUtilities.invokeLater(() -> adjustColumnWidths());
    }

    private void setupTaskTable() {

        DefaultTableModel model = new DefaultTableModel();

        model.setColumnIdentifiers(new String[]{
            "ID",
            "Task Name",
            "Description",
            "Deadline"
        });

        tblTasks.setModel(model);  //  This overrides the auto model

        tblTasks.setAutoCreateRowSorter(true);
        tblTasks.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblTasks.getTableHeader().setResizingAllowed(true);
        tblTasks.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        tblTasks.setRowSelectionAllowed(true);

        tblTasks.setRowHeight(30);
        tblTasks.setBackground(Color.WHITE);
        tblTasks.setFillsViewportHeight(true);
        jScrollPane1.getViewport().setBackground(Color.WHITE);

// ===== HEADER DESIGN =====
        JTableHeader header = tblTasks.getTableHeader();
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

        // Scrollbars
        jScrollPane1.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        jScrollPane1.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        // Wrap description
        TextAreaRenderer renderer = new TextAreaRenderer();
        tblTasks.getColumnModel().getColumn(1).setCellRenderer(renderer);
        tblTasks.getColumnModel().getColumn(2).setCellRenderer(renderer);
        tblTasks.getColumnModel().getColumn(3).setCellRenderer(renderer);

        tblTasks.getColumnModel().getColumn(0).setMinWidth(0);
        tblTasks.getColumnModel().getColumn(0).setMaxWidth(0);
        tblTasks.getColumnModel().getColumn(0).setWidth(0);

        tblTasks.getColumnModel().getColumn(1).setPreferredWidth(130);
        tblTasks.getColumnModel().getColumn(2).setPreferredWidth(500);
        tblTasks.getColumnModel().getColumn(3).setPreferredWidth(120);
    }

    class TextAreaRenderer extends JTextArea implements javax.swing.table.TableCellRenderer {

        public TextAreaRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        @Override
        public java.awt.Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {

            setText(value == null ? "" : value.toString());

            if (isSelected) {
                setBackground(new Color(8, 114, 138));
                setForeground(Color.WHITE);
            } else {
                if (row % 2 == 0) {
                    setBackground(Color.WHITE);
                } else {
                    setBackground(new Color(245, 249, 255));
                }
                setForeground(Color.BLACK);
            }

            // Auto adjust row height
            setSize(table.getColumnModel().getColumn(column).getWidth(), Short.MAX_VALUE);

            int preferredHeight = getPreferredSize().height;

            if (table.getRowHeight(row) < preferredHeight) {
                table.setRowHeight(row, preferredHeight);
            }

            return this;
        }
    }

    private void loadTasks() {

        try {
            System.out.println("Alert ID inside TaskListForAssignment: " + alertId);

            TaskDAO dao = new TaskDAO();

            // Get tasks from database
            java.util.List<Task> taskList
                    = dao.getTasksByAlertId(alertId);
            System.out.println("Tasks fetched: " + taskList.size());   // 👈 ADD THIS

            DefaultTableModel model
                    = (DefaultTableModel) tblTasks.getModel();

            model.setRowCount(0); // clear table

            for (Task t : taskList) {

                model.addRow(new Object[]{
                    t.getId(),
                    t.getTaskTitle(),
                    t.getTaskDescription(),
                    t.getDeadline() == null ? "No Expiry" : t.getDeadline()
                });
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(this,
                    "Error loading tasks: " + e.getMessage());
        }
    }

    private void adjustColumnWidths() {

        for (int col = 1; col <= 3; col++) {

            int maxWidth = 50;

            TableCellRenderer headerRenderer
                    = tblTasks.getTableHeader().getDefaultRenderer();

            Component headerComp
                    = headerRenderer.getTableCellRendererComponent(
                            tblTasks,
                            tblTasks.getColumnName(col),
                            false, false, 0, col);

            maxWidth = Math.max(maxWidth,
                    headerComp.getPreferredSize().width);

            for (int row = 0; row < tblTasks.getRowCount(); row++) {

                TableCellRenderer renderer
                        = tblTasks.getCellRenderer(row, col);

                Component comp
                        = tblTasks.prepareRenderer(renderer, row, col);

                maxWidth = Math.max(maxWidth,
                        comp.getPreferredSize().width);
            }

            if (col == 2) {
                tblTasks.getColumnModel().getColumn(col)
                        .setPreferredWidth(500);
            } else {
                tblTasks.getColumnModel().getColumn(col)
                        .setPreferredWidth(maxWidth + 20);
            }
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
        tblTasks = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnAssign = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jScrollPane1.setViewportView(tblTasks);

        tblTasks.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblTasks);

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/admindashboardlogo/tasklist.png"))); // NOI18N
        jLabel1.setText("Alert Task List");
        jLabel1.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Select a task to assign it to volunteers");
        jLabel2.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, new java.awt.Color(102, 0, 102)));

        btnAssign.setBackground(new java.awt.Color(97, 4, 95));
        btnAssign.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAssign.setForeground(new java.awt.Color(255, 255, 255));
        btnAssign.setText("Assign");
        btnAssign.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAssign.addActionListener(this::btnAssignActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(569, 569, 569)
                .addComponent(btnAssign, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(569, 569, 569))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnAssign, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tablePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAssignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAssignActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblTasks.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Select a task first");
            return;
        }

        DefaultTableModel model
                = (DefaultTableModel) tblTasks.getModel();

        int modelRow
                = tblTasks.convertRowIndexToModel(selectedRow);

        int taskId = (int) model.getValueAt(modelRow, 0);
        String taskName = model.getValueAt(modelRow, 1).toString();

        // ✅ Open assignment frame
        AssignTasksToVolunteers frame
                = new AssignTasksToVolunteers(desktop, taskId, taskName, alertId, adminUsername);

        desktop.add(frame);
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

    }//GEN-LAST:event_btnAssignActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAssign;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JTable tblTasks;
    // End of variables declaration//GEN-END:variables
}
