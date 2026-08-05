/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dg.volunteerdashboard;

/**
 *
 * @author samih
 */
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import com.dg.dao.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class VolunteerTasksFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form VolunteerTasksFrame
     */
    private JDesktopPane desktop;
    private String username;

    public VolunteerTasksFrame(JDesktopPane desktop, String username) {
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        tablePanel.setBackground(Color.WHITE);
        jPanel1.setBackground(Color.WHITE);
        jPanel2.setBackground(Color.WHITE);

        jScrollPane1.setBackground(Color.WHITE);
        jScrollPane1.getViewport().setBackground(Color.WHITE);

        tblAssignedTasks.setBackground(Color.WHITE);
        desktop.setBackground(Color.WHITE);
        this.desktop = desktop;
        this.username = username;
        setupTasksTable();

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
        btnCompleted.setBackground(normalVol);
        btnCompleted.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCompleted.setBackground(hoverVol);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCompleted.setBackground(normalVol);
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

        // ===== SCROLLBAR HIDE =====
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        jScrollPane1.setBorder(null);

        // ===== TABLE BASIC STYLE =====
        tblAssignedTasks.setRowHeight(30);
        tblAssignedTasks.setBackground(Color.WHITE);
        tblAssignedTasks.setFillsViewportHeight(true);

        // ===== HEADER STYLE =====
        JTableHeader header = tblAssignedTasks.getTableHeader();
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

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {

                JLabel c = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                c.setForeground(Color.BLACK);

                // ===== ZEBRA =====
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

                // ===== STATUS COLOR (Column 5) =====
                if (!isSelected && column == 5 && value != null) {
                    String status = value.toString().toLowerCase();

                    switch (status) {
                        case "assigned" ->
                            c.setForeground(new Color(245, 124, 0)); // orange
                        case "completed" ->
                            c.setForeground(new Color(46, 125, 50)); // green
                        case "accepted" ->
                            c.setForeground(new Color(67, 100, 247)); // blue
                        case "rejected" ->
                            c.setForeground(new Color(198, 40, 40)); // red
                        default ->
                            c.setForeground(new Color(0,0,0)); // black
                    }
                }

                c.setHorizontalAlignment(JLabel.CENTER);
                c.setOpaque(true);

                return c;
            }
        };

        for (int i = 0; i < tblAssignedTasks.getColumnCount(); i++) {
            tblAssignedTasks.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        tblAssignedTasks.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        tblAssignedTasks.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblAssignedTasks.setRowSelectionAllowed(true);
        loadVolunteerTasks();
        btnRefresh.addActionListener(e -> loadVolunteerTasks());
        btnView.addActionListener(e -> viewTaskDetails());
        btnCompleted.addActionListener(e -> markSelectedTasksCompleted());
    }

    private void setupTasksTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
            "taskId", // hidden column
            "Alert Title",
            "Task Title",
            "Assigned By",
            "Assigned At",
            "Status",
            "Completed At"
        });
        tblAssignedTasks.setModel(model);
        tblAssignedTasks.setRowHeight(30);

        // Hide the taskId column
        tblAssignedTasks.getColumnModel().getColumn(0).setMinWidth(0);
        tblAssignedTasks.getColumnModel().getColumn(0).setMaxWidth(0);
        tblAssignedTasks.getColumnModel().getColumn(0).setWidth(0);
        // Stretch remaining columns
        tblAssignedTasks.getTableHeader().setResizingAllowed(true);
        tblAssignedTasks.getTableHeader().setReorderingAllowed(false); // optional: prevent moving columns

        // Set preferred widths for readability
        tblAssignedTasks.getColumnModel().getColumn(1).setPreferredWidth(200); // Alert Title
        tblAssignedTasks.getColumnModel().getColumn(2).setPreferredWidth(250); // Task Title
        tblAssignedTasks.getColumnModel().getColumn(3).setPreferredWidth(120); // Assigned By
        tblAssignedTasks.getColumnModel().getColumn(4).setPreferredWidth(120); // Assigned At
        tblAssignedTasks.getColumnModel().getColumn(5).setPreferredWidth(80);  // Status
        tblAssignedTasks.getColumnModel().getColumn(6).setPreferredWidth(120); // Completed At
    }

    private void loadVolunteerTasks() {
        VolunteerAlertDAO dao = new VolunteerAlertDAO();
        List<Object[]> tasks = dao.getAssignedTasksForVolunteer(username);

        DefaultTableModel model = (DefaultTableModel) tblAssignedTasks.getModel();
        model.setRowCount(0); // clear table

        for (Object[] row : tasks) {
            // row[0] = taskId, row[1..] = alertTitle, taskTitle, etc.
            model.addRow(new Object[]{
                row[0], // taskId hidden
                row[1], // Alert Title
                row[2], // Task Title
                row[3], // Assigned By
                row[4], // Assigned At
                row[5], // Status
                row[6] // Completed At
            });
        }
    }

    private void viewTaskDetails() {
        int selectedRow = tblAssignedTasks.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a task to view details.");
            return;
        }

        // Get task info from selected row
        String alertTitle = tblAssignedTasks.getValueAt(selectedRow, 1).toString();
        String taskTitle = tblAssignedTasks.getValueAt(selectedRow, 2).toString();
        int taskId = (int) tblAssignedTasks.getValueAt(selectedRow, 0); //

        // Open the detail frame
        VolunteerTaskDetailFrame detailFrame
                = new VolunteerTaskDetailFrame(desktop, username, taskId);
        desktop.add(detailFrame);
        int margin = 20;
        detailFrame.setBounds(
                margin,
                margin,
                desktop.getWidth() - 2 * margin,
                desktop.getHeight() - 2 * margin
        );

        detailFrame.setVisible(true);

        try {
            detailFrame.setSelected(true);  // Focus on the internal frame
            detailFrame.setMaximum(true);   // Maximize inside desktop pane
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Optional: refresh table when detail frame closes
    }

    private void markSelectedTasksCompleted() {
        int[] selectedRows = tblAssignedTasks.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Please select at least one task.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblAssignedTasks.getModel();
        VolunteerAlertDAO dao = new VolunteerAlertDAO();

        // Use current timestamp for DB and display
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String displayTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(now);

        for (int row : selectedRows) {
            int taskId = (int) model.getValueAt(row, 0); // hidden taskId column
            boolean success = dao.markTaskAsCompleted(taskId, username);

            if (success) {
                // Update JTable columns
                model.setValueAt("Completed", row, 5);   // Status column
                model.setValueAt(displayTime, row, 6);   // Completed At column
            }
        }

        JOptionPane.showMessageDialog(this, "Selected task(s) marked as completed.");

        // Reload tasks to ensure table is consistent with DB
        loadVolunteerTasks();
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
        tblAssignedTasks = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnView = new javax.swing.JButton();
        btnCompleted = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setOpaque(true);

        jScrollPane1.setViewportView(tblAssignedTasks);

        tblAssignedTasks.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblAssignedTasks);

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, new java.awt.Color(102, 102, 255)));

        btnView.setBackground(new java.awt.Color(25, 118, 210));
        btnView.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnView.setForeground(new java.awt.Color(255, 255, 255));
        btnView.setText("View Details");
        btnView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnCompleted.setBackground(new java.awt.Color(106, 27, 154));
        btnCompleted.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnCompleted.setForeground(new java.awt.Color(255, 255, 255));
        btnCompleted.setText("Mark Completed");
        btnCompleted.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnRefresh.setBackground(new java.awt.Color(0, 121, 107));
        btnRefresh.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Refresh");
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefresh.setPreferredSize(new java.awt.Dimension(72, 34));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 244, Short.MAX_VALUE)
                .addComponent(btnCompleted, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 244, Short.MAX_VALUE)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(200, 200, 200))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCompleted, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/taskdashboard.png"))); // NOI18N
        jLabel1.setText("Task Dashboard");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Track progress and complete your assigned responsibilities");

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
                .addContainerGap())
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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCompleted;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnView;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JTable tblAssignedTasks;
    // End of variables declaration//GEN-END:variables
}
