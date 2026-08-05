/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dg.spadmindashboard;

import javax.swing.*;
import java.awt.*;
import org.jdesktop.swingx.JXDatePicker;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import com.dg.dao.*;
import com.dg.model.*;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class TaskListFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form TaskListFrame
     */
    private JDesktopPane desktop;
    private String username;
    private String alertTitle;
    private int alertId;
    private JXDatePicker deadlinePicker;

    public TaskListFrame(JDesktopPane desktopPane, String username, int alertId, String alertTitle) {
        initComponents();

        // scrollbar invisible
        jScrollPane2.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        jScrollPane2.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        // clean look
        jScrollPane2.setBorder(null);
        
        this.desktop = desktopPane;
        this.username = username;
        this.alertId = alertId;
        this.alertTitle = alertTitle;
        lblAlertTitle.setText(alertTitle);
        initDatePicker();
        //  No Expiry logic
        chkNoExpiry.setSelected(false);
        deadlinePicker.setEnabled(true);

        chkNoExpiry.addActionListener(e -> {

            boolean noExpiry = chkNoExpiry.isSelected();

            deadlinePicker.setEnabled(!noExpiry);

        });

        setupDescriptionScroll();   // ADD THIS
        setupTaskTable();
        //
        loadTasks();

    }

    private void setupDescriptionScroll() {

        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);

        jScrollPane1.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        jScrollPane1.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
    }

    private void initDatePicker() {

        deadlinePicker = new JXDatePicker();

        deadlinePicker.setFormats(new SimpleDateFormat("yyyy-MM-dd"));
        deadlinePicker.setDate(new Date()); // default today

        datePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        datePanel.add(deadlinePicker);
    }

private void setupTaskTable() {
    // 1️⃣ Model setup
    DefaultTableModel model = new DefaultTableModel();
    model.setColumnIdentifiers(new String[]{"ID", "Task Name", "Description", "Deadline"});
    tblTasks.setModel(model);

    tblTasks.setAutoCreateRowSorter(true);
    tblTasks.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    tblTasks.setFillsViewportHeight(true);
    tblTasks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tblTasks.setRowSelectionAllowed(true);
    tblTasks.setRowHeight(35);  // modern height
    tblTasks.setShowGrid(false);
    tblTasks.setIntercellSpacing(new Dimension(0, 0));
    tblTasks.setFont(new Font("Segoe UI", Font.PLAIN, 14));

    // 2️⃣ ScrollPane
    jScrollPane2.setBorder(BorderFactory.createEmptyBorder());
    jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    // 3️⃣ Wrap text for columns
    TextAreaRenderer renderer = new TextAreaRenderer();
    tblTasks.getColumnModel().getColumn(1).setCellRenderer(renderer);
    tblTasks.getColumnModel().getColumn(2).setCellRenderer(renderer);
    tblTasks.getColumnModel().getColumn(3).setCellRenderer(renderer);

    // Hide ID column
    tblTasks.getColumnModel().getColumn(0).setMinWidth(0);
    tblTasks.getColumnModel().getColumn(0).setMaxWidth(0);
    tblTasks.getColumnModel().getColumn(0).setWidth(0);

    // Column widths
    tblTasks.getColumnModel().getColumn(1).setPreferredWidth(200);
    tblTasks.getColumnModel().getColumn(2).setPreferredWidth(500);
    tblTasks.getColumnModel().getColumn(3).setPreferredWidth(150);

    // 4️⃣ Zebra rows + selection color
    tblTasks.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (isSelected) {
                c.setBackground(new Color(0, 120, 215));  // selection color
                c.setForeground(Color.WHITE);
            } else {
                c.setForeground(Color.BLACK);
                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
            }

            return c;
        }
    });

    // 5️⃣ Header modern style
    JTableHeader header = tblTasks.getTableHeader();
    header.setDefaultRenderer(new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setOpaque(true);
            label.setBackground(new Color(9, 30, 58)); // RGB header color
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(35, 87, 155))); // bottom line
            return label;
        }
    });
    header.setPreferredSize(new Dimension(header.getWidth(), 40));
    tblTasks.getTableHeader().repaint();
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
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
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

            TaskDAO dao = new TaskDAO();

            // Get tasks from database
            java.util.List<Task> taskList
                    = dao.getTasksByAlertId(alertId);

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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tablePanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTasks = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblAlertTitle = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtTaskName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        datePanel = new javax.swing.JPanel();
        chkNoExpiry = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        btnAddTasks = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Listing Tasks");

        jScrollPane2.setPreferredSize(new java.awt.Dimension(1300, 402));
        jScrollPane2.setViewportView(tblTasks);

        tblTasks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jScrollPane2.setViewportView(tblTasks);

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/alerticon.png"))); // NOI18N

        lblAlertTitle.setBackground(new java.awt.Color(255, 255, 255));
        lblAlertTitle.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 20)); // NOI18N
        lblAlertTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAlertTitle.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblAlertTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAlertTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        jPanel4.setLayout(new java.awt.CardLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/tasknameicon.png"))); // NOI18N
        jLabel2.setText("Task Name");

        txtTaskName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTaskName.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/deadlineicon.png"))); // NOI18N
        jLabel4.setText("Deadline");

        datePanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout datePanelLayout = new javax.swing.GroupLayout(datePanel);
        datePanel.setLayout(datePanelLayout);
        datePanelLayout.setHorizontalGroup(
            datePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        datePanelLayout.setVerticalGroup(
            datePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        chkNoExpiry.setBackground(new java.awt.Color(255, 255, 255));
        chkNoExpiry.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        chkNoExpiry.setText("No Expiry");
        chkNoExpiry.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chkNoExpiry.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkNoExpiry.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                    .addComponent(txtTaskName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chkNoExpiry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(datePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTaskName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(datePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkNoExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jPanel4.add(jPanel5, "card2");

        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        jPanel6.setLayout(new java.awt.CardLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/descriptionicon.png"))); // NOI18N
        jLabel3.setText("Task Description");

        txtDescription.setColumns(20);
        txtDescription.setRows(5);
        jScrollPane1.setViewportView(txtDescription);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.add(jPanel7, "card2");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        btnAddTasks.setBackground(new java.awt.Color(0, 204, 51));
        btnAddTasks.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnAddTasks.setForeground(new java.awt.Color(255, 255, 255));
        btnAddTasks.setText("+ADD TASK");
        btnAddTasks.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddTasks.addActionListener(this::btnAddTasksActionPerformed);

        btnClear.setBackground(new java.awt.Color(0, 0, 153));
        btnClear.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/clearicon_1.png"))); // NOI18N
        btnClear.setText("CLEAR");
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClear.addActionListener(this::btnClearActionPerformed);

        btnDelete.setBackground(new java.awt.Color(204, 0, 0));
        btnDelete.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/deleteicon.png"))); // NOI18N
        btnDelete.setText("DELETE");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDelete.addActionListener(this::btnDeleteActionPerformed);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnClear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDelete)
                .addGap(18, 18, 18)
                .addComponent(btnAddTasks)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAddTasks, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnClear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddTasksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTasksActionPerformed
        // TODO add your handling code here:
        String taskName = txtTaskName.getText().trim();
        String description = txtDescription.getText().trim();
        Date deadline = null;

        //  VALIDATION
        if (taskName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Task name required");
            return;
        }

        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Task description required");
            return;
        }

        if (!chkNoExpiry.isSelected()) {

            deadline = deadlinePicker.getDate();

            if (deadline == null) {
                JOptionPane.showMessageDialog(this, "Select deadline");
                return;
            }

            if (deadline.before(new Date())) {
                JOptionPane.showMessageDialog(this, "Deadline cannot be past date");
                return;
            }
        }

        try {

            LocalDate localDate = null;
            if (deadline != null) {
                localDate = deadline.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            }
            //  Create Task object
            Task task = new Task(
                    alertId,
                    taskName,
                    description,
                    localDate,
                    username // super admin
            );

            //  Insert into DB
            TaskDAO dao = new TaskDAO();
            int taskId = dao.insertTask(task);

            if (taskId > 0) {

                //  Add to JTable
                DefaultTableModel model
                        = (DefaultTableModel) tblTasks.getModel();

                model.addRow(new Object[]{
                    taskId,
                    taskName,
                    description,
                    chkNoExpiry.isSelected() ? "No Expiry" : localDate
                });

                JOptionPane.showMessageDialog(this,
                        "Task Added Successfully");

                //  Clear fields
                txtTaskName.setText("");
                txtDescription.setText("");
                deadlinePicker.setDate(new Date());

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Database Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnAddTasksActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int[] selectedRows = tblTasks.getSelectedRows();

        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Select task(s) to delete");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete selected task(s)?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        DefaultTableModel model
                = (DefaultTableModel) tblTasks.getModel();

        TaskDAO dao = new TaskDAO();

        //  IMPORTANT: delete from bottom → top
        for (int i = selectedRows.length - 1; i >= 0; i--) {

            int viewRow = selectedRows[i];

            int modelRow = tblTasks.convertRowIndexToModel(viewRow);

            int taskId = (int) model.getValueAt(modelRow, 0);

            dao.deleteTask(taskId);

            model.removeRow(modelRow);
        }

        JOptionPane.showMessageDialog(this,
                "Selected task(s) deleted successfully");
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        tblTasks.clearSelection();
    }//GEN-LAST:event_btnClearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddTasks;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JCheckBox chkNoExpiry;
    private javax.swing.JPanel datePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAlertTitle;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JTable tblTasks;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtTaskName;
    // End of variables declaration//GEN-END:variables
}
