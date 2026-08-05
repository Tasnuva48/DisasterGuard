/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dg.spadmindashboard;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import com.dg.dbconnection.SQLiteConnect;
import javax.swing.*;
import java.awt.*;
import com.dg.dao.*;
import com.dg.model.*;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
 *
 * @author samih
 */
public class ViewAlertStatusFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form ViewAlertStatusFrame
     */
    private JDesktopPane desktop;
    private String username;

    public ViewAlertStatusFrame(JDesktopPane desktopPane, String username) {
        initComponents();
        
        // scrollbar invisible
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        // clean look
        jScrollPane1.setBorder(null);
        
        this.desktop = desktopPane;
        this.username = username;
        this.getContentPane().setBackground(new Color(255, 255, 255));
        tblAlerts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setupTable();
        loadAlertStatus();

        tblAlerts.getColumnModel().getColumn(2).setCellRenderer(new TypeColorRenderer());
        // সব column-এর লেখা center করা (Type column বাদ দিয়ে)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 3; i < tblAlerts.getColumnCount(); i++) {
            /*if (i != 2) { */// column 2 = Type, যেটার জন্য custom color renderer আছে
                tblAlerts.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            //}
        }

        // Header renderer set করা
        JTableHeader header = tblAlerts.getTableHeader();
        header.setDefaultRenderer(new CustomHeaderRenderer());

        tblAlerts.setFont(new Font("Arial Black MT", Font.PLAIN, 14));
        jScrollPane1.setBackground(new Color(255, 255, 255));
        jScrollPane1.getViewport().setBackground(new Color(255, 255, 255));
        // -----------------------

        addTableDoubleClick();
        // Disable view button initially
        btnView.setEnabled(false);

        // Enable view button only when a row is selected
        tblAlerts.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnView.setEnabled(tblAlerts.getSelectedRow() != -1);
            }
        });
    }

    class CustomHeaderRenderer extends DefaultTableCellRenderer {

        public CustomHeaderRenderer() {
            setOpaque(true); // background দেখানোর জন্য
            setBackground(new Color(9, 30, 58)); // header background color
            setForeground(Color.WHITE);              // header text color
            setHorizontalAlignment(CENTER);         // text center
        }

        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setFont(new Font("Arial Black MT", Font.BOLD, 16)); // header font
            return this;
        }
    }

    class TypeColorRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            JLabel label = (JLabel) super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            label.setOpaque(true);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Arial Black MT", Font.BOLD, 13));

            String type = value.toString();

            if (type.equalsIgnoreCase("Emergency")) {
                label.setBackground(new Color(255, 204, 204));
                label.setForeground(Color.RED);
            } else if (type.equalsIgnoreCase("Warning")) {
                label.setBackground(new Color(255, 229, 204));
                label.setForeground(new Color(255, 140, 0));
            } else if (type.equalsIgnoreCase("Info")) {
                label.setBackground(new Color(204, 229, 255));
                label.setForeground(new Color(0, 153, 255));
            } else {
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
            }

            return label;
        }
    }

    /*class TypeColorRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        String type = value.toString();

        if (type.equalsIgnoreCase("Emergency")) {
            c.setForeground(Color.RED);
        } 
        else if (type.equalsIgnoreCase("Warning")) {
            c.setForeground(new Color(255,140,0));
        } 
        else if (type.equalsIgnoreCase("Info")) {
            c.setForeground(new Color(0,153,255));
        } 
        else {
            c.setForeground(Color.BLACK);
        }

        return c;
    }
}*/
    private void addTableDoubleClick() {

        tblAlerts.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                if (e.getClickCount() == 2) { // Double click

                    openDetails();
                }
            }
        });
    }

    private void setupTable() {

        DefaultTableModel model = new DefaultTableModel();

        model.setColumnIdentifiers(new String[]{
            "ID",
            "Title",
            "Type",
            "Created At",
            "Read Status"
        });

        tblAlerts.setModel(model);

        // Hide ID column
        tblAlerts.getColumnModel()
                .getColumn(0).setMinWidth(0);

        tblAlerts.getColumnModel()
                .getColumn(0).setMaxWidth(0);
    }

    private void loadAlertStatus() {
        DefaultTableModel model = (DefaultTableModel) tblAlerts.getModel();
        model.setRowCount(0); // Clear table

        try {
            AlertStatusDAO dao = new AlertStatusDAO();
            for (AlertStatus status : dao.getAllAlertStatuses()) {
                model.addRow(new Object[]{
                    status.getId(),
                    status.getTitle(),
                    status.getType(),
                    status.getCreatedAt(),
                    status.getReadAdmins() + " / " + status.getTotalAdmins()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error!");
        }
    }

    private void openDetails() {

        int row = tblAlerts.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(this,
                    "Please select an alert first.");

            return;
        }

        int alertId = Integer.parseInt(
                tblAlerts.getValueAt(row, 0).toString()
        );

        ViewAlertDetailsFrame alertDetails = new ViewAlertDetailsFrame(desktop, username, alertId);
        // ADD LISTENER TO CLEAR SELECTION WHEN DETAILS FRAME CLOSES
        alertDetails.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                tblAlerts.clearSelection(); // clear selected row
            }
        });

        // Add it to desktop
        desktop.add(alertDetails);

        int margin = 40;

        // Set bounds to fill the desktop with margins
        alertDetails.setBounds(
                margin,
                margin,
                desktop.getWidth() - 2 * margin,
                desktop.getHeight() - 2 * margin
        );

        alertDetails.setVisible(true);

        try {
            alertDetails.setSelected(true); // bring to front
            alertDetails.setMaximum(true);  // maximize inside desktop
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openTaskList() {

        int row = tblAlerts.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select an alert first.");
            return;
        }

        int alertId = Integer.parseInt(
                tblAlerts.getValueAt(row, 0).toString()
        );

        // ✅ Get Alert Title from column 1 (Title column)
        String alertTitle = tblAlerts.getValueAt(row, 1).toString();

        // ✅ Pass title to Task Frame
        TaskListFrame taskFrame
                = new TaskListFrame(desktop, username, alertId, alertTitle);

        taskFrame.addInternalFrameListener(
                new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(
                    javax.swing.event.InternalFrameEvent e) {
                tblAlerts.clearSelection();
            }
        });

        desktop.add(taskFrame);

        int margin = 40;

        taskFrame.setBounds(
                margin,
                margin,
                desktop.getWidth() - 2 * margin,
                desktop.getHeight() - 2 * margin
        );

        taskFrame.setVisible(true);

        try {
            taskFrame.setSelected(true);
            taskFrame.setMaximum(true);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblAlerts = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnView = new javax.swing.JButton();
        btnTasks = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("View Alert Status");

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1400, 1000));
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
        tblAlerts.setRowHeight(22);
        jScrollPane1.setViewportView(tblAlerts);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/readicon.png"))); // NOI18N
        jLabel1.setText("Alert Read Status");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Monitor user engagement with emergency alert");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, new java.awt.Color(0, 0, 153)));

        btnView.setBackground(new java.awt.Color(204, 255, 255));
        btnView.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnView.setForeground(new java.awt.Color(51, 153, 255));
        btnView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/rightclickicon.png"))); // NOI18N
        btnView.setText("View Details");
        btnView.setBorder(null);
        btnView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnView.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnView.addActionListener(this::btnViewActionPerformed);

        btnTasks.setBackground(new java.awt.Color(204, 255, 204));
        btnTasks.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnTasks.setForeground(new java.awt.Color(0, 102, 153));
        btnTasks.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/thinkdoicon.png"))); // NOI18N
        btnTasks.setText("List Tasks");
        btnTasks.setBorder(null);
        btnTasks.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTasks.addActionListener(this::btnTasksActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTasks, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTasks, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        // TODO add your handling code here:
        openDetails();
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnTasksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTasksActionPerformed
        // TODO add your handling code here:
        openTaskList();
    }//GEN-LAST:event_btnTasksActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTasks;
    private javax.swing.JButton btnView;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAlerts;
    // End of variables declaration//GEN-END:variables
}
