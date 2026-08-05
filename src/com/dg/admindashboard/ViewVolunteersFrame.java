/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dg.admindashboard;

import com.dg.spadmindashboard.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import com.dg.dao.*;

/**
 *
 * @author samih
 */
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.util.List;
import java.util.ArrayList;
import com.dg.model.*;
import com.dg.dao.*;

public class ViewVolunteersFrame extends javax.swing.JInternalFrame implements ViewUserFrame {

    /**
     * Creates new form ViewVolunteersFrame
     */
    private String adminUsername;

    public ViewVolunteersFrame(String adminUsername) {
        initComponents();

        // --- Approve Button ---
        btnApprove.setBackground(new Color(46, 125, 50));
        btnApprove.setForeground(Color.WHITE);
        btnApprove.setFocusPainted(false);
        btnApprove.setBorderPainted(false);
        btnApprove.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnApprove.setBackground(new Color(60, 150, 65));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnApprove.setBackground(new Color(46, 125, 50));
            }
        });

// --- Reject Button ---
        btnReject.setBackground(new Color(204, 0, 0));
        btnReject.setForeground(Color.WHITE);
        btnReject.setFocusPainted(false);
        btnReject.setBorderPainted(false);
        btnReject.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReject.setBackground(new Color(230, 60, 60));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReject.setBackground(new Color(204, 0, 0));
            }
        });

// --- Clear Button ---
        btnClear.setBackground(new Color(117, 117, 117));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFocusPainted(false);
        btnClear.setBorderPainted(false);
        btnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnClear.setBackground(new Color(140, 140, 140));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnClear.setBackground(new Color(117, 117, 117));
            }
        });

        // scrollbar invisible
        //jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        //jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        // clean look
        jScrollPane1.setBorder(null);
        autoResizeTableColumns(tblVolunteers);
        this.adminUsername = adminUsername;

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
        buttonPanel.setVisible(true);
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
        btnApprove.addActionListener(e -> updateSelectedVolunteersStatus("Approved"));
        btnReject.addActionListener(e -> updateSelectedVolunteersStatus("Rejected"));
        btnClear.addActionListener(e -> tblVolunteers.clearSelection());
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
        buttonPanel.setVisible(status.equalsIgnoreCase("Pending"));
    }

    @Override
    public void setupTable() {
        setupVolunteerTable();   // 👈 specific implementation
    }

    @Override
    public void loadData(String status) {
        loadVolunteers(status);   // reuse your existing method
    }

    /*
    private void updateSelectedVolunteersStatus(String newStatus) {
        int[] selectedRows = tblVolunteers.getSelectedRows();

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
            tblVolunteers.setEnabled(false);

            for (int row : selectedRows) {
                int id = (int) tblVolunteers.getValueAt(row, 0); // ID is column 0

                if (newStatus.equalsIgnoreCase("Approved")) {
                    // --- Update status to Approved ---
                    pst.setString(1, "Approved");
                    pst.setInt(2, id);
                    pst.addBatch();

                    // --- Insert into 'users' table ---
                    String name = tblVolunteers.getValueAt(row, 1).toString();
                    String username = tblVolunteers.getValueAt(row, 22).toString(); // email as username
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
                    // ✅ After creating user, sync old forwarded alerts
                    VolunteerAlertDAO alertDao = new VolunteerAlertDAO();
                    alertDao.insertForwardedAlertsForNewVolunteer(username, conn);

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
                loadData("Rejected");       // show rejected admins
            } else {
                loadData(getSelectedStatusFilter()); // normal reload
            }

            tblVolunteers.clearSelection();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Error updating status: " + e.getMessage());
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Enable table back after update
            tblVolunteers.setEnabled(true);
        }
    }
     */
    private void updateSelectedVolunteersStatus(String newStatus) {
        int[] selectedRows = tblVolunteers.getSelectedRows();

        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "No rows selected!");
            return;
        }

        // --- Prepare lists to pass to DAO ---
        List<Integer> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> usernames = new ArrayList<>();

        for (int row : selectedRows) {
            ids.add((Integer) tblVolunteers.getValueAt(row, 0));  // ID column
            names.add(tblVolunteers.getValueAt(row, 1).toString()); // Name column
            usernames.add(tblVolunteers.getValueAt(row, 22).toString()); // Username/email column
        }

        // --- Call DAO method ---
        ViewVolunteerDAO dao = new ViewVolunteerDAO();
        boolean success = dao.updateVolunteersStatus(ids, names, usernames, newStatus);

        if (success) {
            JOptionPane.showMessageDialog(this, "Status updated successfully!");

            // --- Reload table according to action ---
            if (newStatus.equalsIgnoreCase("Rejected")) {
                rbRejected.setSelected(true);
                loadData("Rejected");
            } else {
                loadData(getSelectedStatusFilter());
            }

            tblVolunteers.clearSelection();
        } else {
            JOptionPane.showMessageDialog(this, "Error updating status!");
        }
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

        // Assign model to table
        tblVolunteers.setModel(model);
        tblVolunteers.setBackground(Color.WHITE);
        tblVolunteers.setFillsViewportHeight(true);
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        // Optional: make row height taller
        tblVolunteers.setRowHeight(30);

        // Enable sorting
        tblVolunteers.setAutoCreateRowSorter(true);

        JTableHeader header = tblVolunteers.getTableHeader();
        header.setBackground(new Color(10, 28, 54));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        tblVolunteers.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                label.setBackground(new Color(10, 28, 54));
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setOpaque(true);

                return label;
            }
        });

        DefaultTableCellRenderer zebraRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

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
        for (int i = 0; i < tblVolunteers.getColumnCount(); i++) {
            tblVolunteers.getColumnModel().getColumn(i).setCellRenderer(zebraRenderer);
        }
// Disable automatic resizing so horizontal scroll works
        tblVolunteers.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    private void autoResizeTableColumns(JTable table) {
        for (int col = 0; col < table.getColumnCount(); col++) {
            int maxWidth = 0;

            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(
                    table, table.getColumnName(col), false, false, 0, col);
            maxWidth = headerComp.getPreferredSize().width;

            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, col);
                Component comp = table.prepareRenderer(cellRenderer, row, col);
                maxWidth = Math.max(maxWidth, comp.getPreferredSize().width);
            }

            maxWidth += 15;
            table.getColumnModel().getColumn(col).setPreferredWidth(maxWidth);
        }
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

            // 4️⃣ SQL query: select only the columns you want, combine birth 
           
            String sql = "SELECT "
                    + "id, full_name, "
                    + "birth_date || '-' || birth_month || '-' || birth_year AS birth_date, "
                    + "gender, nid, phone_number, email, present_division, present_district, "
                    + "permanent_division, permanent_district, blood_group, medical_training, "
                    + "search_and_rescue, swimming, driving, language_skills, technical_skills, "
                    + "physical_fitness_level, previous_disaster_experience, profession, "
                    + "roles_performed, username, status "
                    + "FROM volunteer_info "
                    + "WHERE present_division = ( "
                    + "   SELECT present_division FROM admin_info WHERE username = ? )";
            // 5️⃣ Apply status filter if not "All"
           
            if (!statusFilter.equalsIgnoreCase("All")) {
                sql += " AND status = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, adminUsername);   // for division
                pst.setString(2, statusFilter);    // for status
            } else {
                pst = conn.prepareStatement(sql);
                pst.setString(1, adminUsername);   // only division
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
            List<ViewVolunteer> volunteers = dao.getVolunteers(statusFilter);

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
    class TextAreaRenderer extends JTextArea implements TableCellRenderer {

        public TextAreaRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            setText(value == null ? "" : value.toString());

            // Handle selection colors
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }

            // Only **increase row height if needed**, do not decrease it
            setSize(table.getColumnModel().getColumn(column).getWidth(), Short.MAX_VALUE);
            int preferredHeight = getPreferredSize().height;
            if (table.getRowHeight(row) < preferredHeight) {
                table.setRowHeight(row, preferredHeight);
            }

            return this;
        }
    }

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
        buttonPanel = new javax.swing.JPanel();
        btnApprove = new javax.swing.JButton();
        btnReject = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1300, 750));

        rbuttonsPanel.setBackground(new java.awt.Color(255, 255, 255));

        rbPending.setBackground(new java.awt.Color(255, 255, 255));
        rbPending.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rbPending.setForeground(new java.awt.Color(204, 204, 0));
        rbPending.setText("Pending");
        rbPending.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        rbApproved.setBackground(new java.awt.Color(255, 255, 255));
        rbApproved.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rbApproved.setForeground(new java.awt.Color(0, 204, 51));
        rbApproved.setText("Approved");
        rbApproved.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        rbRejected.setBackground(new java.awt.Color(255, 255, 255));
        rbRejected.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rbRejected.setForeground(new java.awt.Color(255, 0, 0));
        rbRejected.setText("Rejected");
        rbRejected.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        rbAll.setBackground(new java.awt.Color(255, 255, 255));
        rbAll.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rbAll.setForeground(new java.awt.Color(204, 0, 204));
        rbAll.setText("All");
        rbAll.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rbAll.addActionListener(this::rbAllActionPerformed);

        javax.swing.GroupLayout rbuttonsPanelLayout = new javax.swing.GroupLayout(rbuttonsPanel);
        rbuttonsPanel.setLayout(rbuttonsPanelLayout);
        rbuttonsPanelLayout.setHorizontalGroup(
            rbuttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rbuttonsPanelLayout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(rbPending, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(163, 163, 163)
                .addComponent(rbApproved, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(162, 162, 162)
                .addComponent(rbRejected, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(163, 163, 163)
                .addComponent(rbAll, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(200, 200, 200))
        );
        rbuttonsPanelLayout.setVerticalGroup(
            rbuttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rbuttonsPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(rbuttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbPending, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbApproved, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbRejected, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbAll, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );

        jScrollPane1.setPreferredSize(new java.awt.Dimension(1288, 700));

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
        );

        buttonPanel.setBackground(new java.awt.Color(255, 255, 255));
        buttonPanel.setPreferredSize(new java.awt.Dimension(1288, 110));

        btnApprove.setBackground(new java.awt.Color(204, 255, 204));
        btnApprove.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnApprove.setForeground(new java.awt.Color(0, 153, 0));
        btnApprove.setText("Approve");
        btnApprove.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnReject.setBackground(new java.awt.Color(255, 204, 204));
        btnReject.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnReject.setForeground(new java.awt.Color(204, 0, 0));
        btnReject.setText("Reject");
        btnReject.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnClear.setBackground(new java.awt.Color(255, 255, 204));
        btnClear.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnClear.setForeground(new java.awt.Color(102, 102, 0));
        btnClear.setText("Clear");
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGap(247, 247, 247)
                .addComponent(btnApprove, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnReject, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(247, 247, 247))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnApprove)
                    .addComponent(btnReject)
                    .addComponent(btnClear))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(rbuttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(rbuttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAllActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbAllActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApprove;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnReject;
    private javax.swing.JPanel buttonPanel;
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
