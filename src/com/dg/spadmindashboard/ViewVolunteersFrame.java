/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dg.spadmindashboard;

import com.dg.dao.ViewVolunteerDAO;
import com.dg.model.ViewVolunteer;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author samih
 */
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class ViewVolunteersFrame extends javax.swing.JInternalFrame implements ViewUserFrame{

    /**
     * Creates new form ViewVolunteersFrame
     */
    public ViewVolunteersFrame() {
        initComponents();
        
        // scrollbar invisible
       // jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
       // jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        // clean look
        jScrollPane1.setBorder(null);
        
        // ---- নিচে add করো ----
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                int width = getWidth();
                int height = getHeight();
                int topHeight = rbuttonsPanel.getHeight(); // উপরের radio button panel

                // tablePanel height adjust
                tablePanel.setBounds(0, topHeight, width, height - topHeight);
                tablePanel.revalidate();
                tablePanel.repaint();
            }
        });
        /*this.addComponentListener(new java.awt.event.ComponentAdapter() {
    @Override
    public void componentResized(java.awt.event.ComponentEvent evt) {
        int width = getWidth();
        int height = getHeight();

        // Fixed heights
        int topHeight = 60;   // rbuttonsPanel
        int bottomHeight = 100; // buttonPanel

        // Set top panel bounds
        rbuttonsPanel.setBounds(0, 0, width, topHeight);

        // Set bottom panel bounds
        //buttonPanel.setBounds(0, height - bottomHeight, width, bottomHeight);

        // Set table panel bounds dynamically
        tablePanel.setBounds(0, topHeight, width, height - topHeight - bottomHeight);
    }
});*/
        setResizable(true);
        setMaximizable(true);

        setClosable(true);
        setIconifiable(true);
        setupVolunteerTable();
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        ButtonGroup group = new ButtonGroup();

        group.add(rbPending);
        group.add(rbApproved);
        group.add(rbRejected);
        group.add(rbAll);

        rbPending.setSelected(true);

        loadData("Pending");
        autoResizeTableColumns(tblVolunteers);

        // --- তারপর add করো auto width loop ---
        /* for (int col = 0; col < tblAdmins.getColumnCount(); col++) {
            int maxWidth = 0;

            // 1️⃣ Check header width
            TableCellRenderer headerRenderer = tblAdmins.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(
                    tblAdmins, tblAdmins.getColumnName(col), false, false, 0, col);
            maxWidth = headerComp.getPreferredSize().width;

            // 2️⃣ Check all row cells
            for (int row = 0; row < tblAdmins.getRowCount(); row++) {
                TableCellRenderer cellRenderer = tblAdmins.getCellRenderer(row, col);
                Component comp = tblAdmins.prepareRenderer(cellRenderer, row, col);
                maxWidth = Math.max(maxWidth, comp.getPreferredSize().width);
            }

            // 3️⃣ Add some padding
            maxWidth += 10;

            // 4️⃣ Set preferred width
            tblAdmins.getColumnModel().getColumn(col).setPreferredWidth(maxWidth);
        }*/

 /*for (int col = 0; col < tblAdmins.getColumnCount(); col++) {
    int maxWidth = 50; 
    for (int row = 0; row < tblAdmins.getRowCount(); row++) {
        TableCellRenderer renderer = tblAdmins.getCellRenderer(row, col);
        Component comp = tblAdmins.prepareRenderer(renderer, row, col);
        maxWidth = Math.max(comp.getPreferredSize().width + 10, maxWidth);
    }
    TableCellRenderer headerRenderer = tblAdmins.getTableHeader().getDefaultRenderer();
    Component headerComp = headerRenderer.getTableCellRendererComponent(
            tblAdmins, tblAdmins.getColumnName(col), false, false, 0, col);
    maxWidth = Math.max(maxWidth, headerComp.getPreferredSize().width + 10);
    tblAdmins.getColumnModel().getColumn(col).setPreferredWidth(maxWidth);
}*/
        //buttonPanel.setVisible(true);
        setupActions();
    }
    @Override

    public void setupActions() {
        // --- Radio button actions ---
        rbPending.addActionListener(e -> handleRadioButton("Pending"));
        rbApproved.addActionListener(e -> handleRadioButton("Approved"));
        rbRejected.addActionListener(e -> handleRadioButton("Rejected"));
        rbAll.addActionListener(e -> handleRadioButton("All"));

        // --- Normal button actions ---
        // btnApprove.addActionListener(e -> updateSelectedAdminsStatus("Approved"));
        //btnReject.addActionListener(e -> updateSelectedAdminsStatus("Rejected"));
        //btnClear.addActionListener(e -> tblAdmins.clearSelection());
    }
@Override
    public String getSelectedStatusFilter() {
        if (rbPending.isSelected()) {
            return "Pending";
        }
        if (rbApproved.isSelected()) {
            return "Approved";
        }
        if (rbRejected.isSelected()) {
            return "Rejected";
        }
        if (rbAll.isSelected()) {
            return "All";
        }
        return "Pending"; // default
    }
@Override
    public void handleRadioButton(String status) {
        // Reload table data based on selected filter
        loadData(status);
        autoResizeTableColumns(tblVolunteers);

        // Show approve/reject/clear buttons only for Pending
        // buttonPanel.setVisible(status.equalsIgnoreCase("Pending"));
    }

    /*
   private void updateSelectedAdminsStatus(String newStatus) {
    int[] selectedRows = tblAdmins.getSelectedRows();
    
    if (selectedRows.length == 0) {
        JOptionPane.showMessageDialog(this, "No rows selected!");
        return;
    }

    Connection conn = null;
    PreparedStatement pst = null;

    try {
        conn = SQLiteConnect.Connectordb();
        conn.setAutoCommit(false); // transaction start

        String sql = "UPDATE volunteer_info SET status = ? WHERE id = ?";
        pst = conn.prepareStatement(sql);

        // Disable table redraw temporarily
        tblAdmins.setEnabled(false);

        for (int row : selectedRows) {
            int id = (int) tblAdmins.getValueAt(row, 0); // ID is column 0

            if (newStatus.equalsIgnoreCase("Approved")) {
                // --- Update status to Approved ---
                pst.setString(1, "Approved");
                pst.setInt(2, id);
                pst.addBatch();

                // --- Insert into 'users' table ---
                String name = tblAdmins.getValueAt(row, 1).toString();
                String username = tblAdmins.getValueAt(row, 22).toString(); // email as username
                String password = generateRandomPassword(8); // generate 8-char password

                PreparedStatement pstInsert = conn.prepareStatement(
                    "INSERT INTO users (name, username, password, designation_type) VALUES (?, ?, ?, ?)"
                );
                pstInsert.setString(1, name);
                pstInsert.setString(2, username);
                pstInsert.setString(3, password);
                pstInsert.setString(4, "Volunteer"); // designation type
                pstInsert.executeUpdate();
                pstInsert.close();

            } else if (newStatus.equalsIgnoreCase("Rejected")) {
                // --- Update status to Rejected ---
                pst.setString(1, "Rejected");
                pst.setInt(2, id);
                pst.addBatch();
            }
        }

        pst.executeBatch();
        conn.commit(); // commit all updates

        JOptionPane.showMessageDialog(this, "Status updated successfully!");

        // --- Reload table according to action ---
        if (newStatus.equalsIgnoreCase("Rejected")) {
            rbRejected.setSelected(true); // select Rejected radio button
            loadAdmins("Rejected");       // show rejected admins
        } else {
            loadAdmins(getSelectedStatusFilter()); // normal reload
        }

        tblAdmins.clearSelection();

    } catch (Exception e) {
        e.printStackTrace();
        try { if (conn != null) conn.rollback(); } catch(Exception ex){ ex.printStackTrace(); }
        JOptionPane.showMessageDialog(this, "Error updating status: " + e.getMessage());
    } finally {
        try {
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Enable table back after update
        tblAdmins.setEnabled(true);
    }
}
     */
    private void autoResizeTableColumns(JTable table) {
        for (int col = 0; col < table.getColumnCount(); col++) {
            int maxWidth = 0;

            // 1️⃣ header width
            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(
                    table, table.getColumnName(col), false, false, 0, col);
            maxWidth = headerComp.getPreferredSize().width;

            // 2️⃣ cell content width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, col);
                Component comp = table.prepareRenderer(cellRenderer, row, col);
                maxWidth = Math.max(maxWidth, comp.getPreferredSize().width);
            }

            // 3️⃣ padding
            maxWidth += 15;

            // 4️⃣ set column width
            table.getColumnModel().getColumn(col).setPreferredWidth(maxWidth);
        }
    }
     @Override
public void setupTable() {
    setupVolunteerTable();   // 👈 specific implementation
}
@Override
public void loadData(String status) {
    loadVolunteers(status);   // reuse your existing method
}

    private void setupVolunteerTable() {
        // Create a DefaultTableModel
        DefaultTableModel model = new DefaultTableModel();

        // Add columns
        String[] columns = {
            "ID", "Full Name", "Birth Date", "Gender", "NID",
            "Phone Number", "Email", "Present Division", "Present District",
            "Permanent Division", "Permanent District",
            "Blood Group", "Medical Training", "Search & Rescue", "Swimming", "Driving",
            "Language Skills", "Technical Skills", "Physical Fitness Level",
            "Previous Disaster Exp",
            "Profession", // NEW
            "Roles Performed", // NEW
            "Username", // always last
            "Status" // always last
        };

        for (String col : columns) {
            model.addColumn(col);
        }

        tblVolunteers.setModel(model);
        tblVolunteers.setRowHeight(30);
        tblVolunteers.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblVolunteers.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // Make table fill scrollpane height even if few rows
        tblVolunteers.setFillsViewportHeight(true);
        tblVolunteers.setBackground(Color.WHITE);  // table background

        // Scrollpane background
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        // --- Modern Table Styling ---

        // Table header color
        JTableHeader header = tblVolunteers.getTableHeader();
        header.setOpaque(true); // old line replace করা
        header.setBackground(new Color(10, 28, 54)); // dark blue
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

// Optional: LookAndFeel override করলে header ঠিক হবে
        tblVolunteers.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                label.setBackground(new Color(10, 28, 54));
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setHorizontalAlignment(JLabel.CENTER); // optional
                label.setOpaque(true);
                return label;
            }
        });

        // Alternating row colors + selection
        tblVolunteers.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(JLabel.CENTER); // center text
                if (isSelected) {
                    label.setBackground(new Color(8, 114, 138));
                    label.setForeground(Color.WHITE);
                } else {
                    if (row % 2 == 0) {
                        label.setBackground(Color.WHITE);
                    } else {
                        label.setBackground(new Color(245, 249, 255));
                    }
                    label.setForeground(Color.BLACK);
                }
                return label;
            }
        });

        // Disable default gridlines
        tblVolunteers.setShowGrid(false);
        tblVolunteers.setIntercellSpacing(new Dimension(0, 0));

        // Wrap text for long cells
        /*TextAreaRenderer renderer = new TextAreaRenderer();
        for (int i = 0; i < tblAdmins.getColumnCount(); i++) {
            tblAdmins.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }*/
        // Preferred widths
        int[] widths = {
            30, 150, 100, 80, 120, 120, 150, 120, 120, 120, 120, 150,
            150, 120, 120, 80,
            100, 100, 100, 100, 120, 120, 120, 120
        };
        for (int i = 0; i < columns.length; i++) {
            tblVolunteers.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        // Scrollpane background white
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }
    /*

    public void loadVolunteers(String statusFilter) {
        // 1️⃣ Get the table model
        DefaultTableModel model = (DefaultTableModel) tblVolunteers.getModel();

        // 2️⃣ Clear existing rows
        model.setRowCount(0);

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            // 3️⃣ Connect using your SQLiteConnect class
            conn = SQLiteConnect.Connectordb();

            // 4️⃣ SQL query: select only the columns you want, combine birth date
            String sql = "SELECT "
                    + "id, "
                    + "full_name, "
                    + "birth_date || '-' || birth_month || '-' || birth_year AS birth_date, "
                    + "gender, "
                    + "nid, "
                    + "phone_number, "
                    + "email, "
                    + "present_division, "
                    + "present_district, "
                    + "permanent_division, "
                    + "permanent_district, "
                    + "blood_group, "
                    + "medical_training, "
                    + "search_and_rescue, "
                    + "swimming, "
                    + "driving, "
                    + "language_skills, "
                    + "technical_skills, "
                    + "physical_fitness_level, "
                    + "previous_disaster_experience, "
                    + "profession, "
                    + "roles_performed, "
                    + "username, "
                    + "status "
                    + "FROM volunteer_info";
            // 5️⃣ Apply status filter if not "All"
            if (!statusFilter.equalsIgnoreCase("All")) {
                sql += " WHERE status = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, statusFilter);
            } else {
                pst = conn.prepareStatement(sql);
            }

            // 6️⃣ Execute query
            rs = pst.executeQuery();

            // 7️⃣ Loop through result set and add rows to table
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("full_name"),
                    rs.getString("birth_date"),
                    rs.getString("gender"),
                    rs.getString("nid"),
                    rs.getString("phone_number"),
                    rs.getString("email"),
                    rs.getString("present_division"),
                    rs.getString("present_district"),
                    rs.getString("permanent_division"),
                    rs.getString("permanent_district"),
                    rs.getString("blood_group"),
                    rs.getString("medical_training"),
                    rs.getString("search_and_rescue"),
                    rs.getString("swimming"),
                    rs.getString("driving"),
                    rs.getString("language_skills"),
                    rs.getString("technical_skills"),
                    rs.getString("physical_fitness_level"),
                    rs.getString("previous_disaster_experience"),
                    rs.getString("profession"), // NEW
                    rs.getString("roles_performed"), // NEW
                    rs.getString("username"), // always last
                    rs.getString("status") // always last
                };

                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading admin data: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    */
    private void loadVolunteers(String statusFilter) {
    // 1️⃣ Get table model
    DefaultTableModel model = (DefaultTableModel) tblVolunteers.getModel();
    model.setRowCount(0); // clear existing rows

    try {
        ViewVolunteerDAO dao = new ViewVolunteerDAO();

        // Fetch all volunteers (DAO handles SQL and filters)
        java.util.List<ViewVolunteer> volunteers = dao.getVolunteers(statusFilter);

        // 2️⃣ Loop through the list and add rows to the table
        for (ViewVolunteer v : volunteers) {
            Object[] row = {
                v.getId(),
                v.getFullName(),
                v.getBirthDate(),
                v.getGender(),
                v.getNid(),
                v.getPhoneNumber(),
                v.getEmail(),
                v.getPresentDivision(),
                v.getPresentDistrict(),
                v.getPermanentDivision(),
                v.getPermanentDistrict(),
                v.getBloodGroup(),
                v.getMedicalTraining(),
                v.getSearchAndRescue(),
                v.getSwimming(),
                v.getDriving(),
                v.getLanguageSkills(),
                v.getTechnicalSkills(),
                v.getPhysicalFitnessLevel(),
                v.getPreviousDisasterExp(),
                v.getProfession(),
                v.getRolesPerformed(),
                v.getUsername(),
                v.getStatus()
            };
            model.addRow(row);
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading volunteers: " + e.getMessage());
    }
}
    // ===== Custom cell renderer to wrap text =====

    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*";
        StringBuilder sb = new StringBuilder();
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rbuttonsPanel = new javax.swing.JPanel();
        rbPending = new javax.swing.JRadioButton();
        rbApproved = new javax.swing.JRadioButton();
        rbRejected = new javax.swing.JRadioButton();
        rbAll = new javax.swing.JRadioButton();
        tablePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVolunteers = new javax.swing.JTable();

        rbuttonsPanel.setBackground(new java.awt.Color(255, 255, 255));

        rbPending.setBackground(new java.awt.Color(255, 255, 255));
        rbPending.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        rbPending.setForeground(new java.awt.Color(153, 153, 0));
        rbPending.setText("Pending");
        rbPending.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        rbApproved.setBackground(new java.awt.Color(255, 255, 255));
        rbApproved.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        rbApproved.setForeground(new java.awt.Color(0, 204, 51));
        rbApproved.setText("Approved");
        rbApproved.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        rbRejected.setBackground(new java.awt.Color(255, 255, 255));
        rbRejected.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        rbRejected.setForeground(new java.awt.Color(204, 0, 0));
        rbRejected.setText("Rejected");
        rbRejected.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        rbAll.setBackground(new java.awt.Color(255, 255, 255));
        rbAll.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        rbAll.setForeground(new java.awt.Color(51, 0, 204));
        rbAll.setText("All");
        rbAll.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout rbuttonsPanelLayout = new javax.swing.GroupLayout(rbuttonsPanel);
        rbuttonsPanel.setLayout(rbuttonsPanelLayout);
        rbuttonsPanelLayout.setHorizontalGroup(
            rbuttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rbuttonsPanelLayout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(rbPending, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(165, 165, 165)
                .addComponent(rbApproved, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(165, 165, 165)
                .addComponent(rbRejected, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(165, 165, 165)
                .addComponent(rbAll, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(200, 200, 200))
        );
        rbuttonsPanelLayout.setVerticalGroup(
            rbuttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rbuttonsPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(rbuttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbPending, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbApproved, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbRejected, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbAll, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

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
            .addComponent(jScrollPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rbuttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(rbuttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbAll;
    private javax.swing.JRadioButton rbApproved;
    private javax.swing.JRadioButton rbPending;
    private javax.swing.JRadioButton rbRejected;
    private javax.swing.JPanel rbuttonsPanel;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JTable tblVolunteers;
    // End of variables declaration//GEN-END:variables
}
