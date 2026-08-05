/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dg.spadmindashboard;

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
import javax.swing.table.TableColumn;
import com.dg.model.*;
import com.dg.dao.*;
import java.util.List;
import java.util.ArrayList;

public class ViewAdminsFrame extends javax.swing.JInternalFrame implements ViewUserFrame {

    /**
     * Creates new form ViewVolunteersFrame
     */
    public ViewAdminsFrame() {
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
        
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
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
                buttonPanel.setBounds(0, height - bottomHeight, width, bottomHeight);

                // Set table panel bounds dynamically
                tablePanel.setBounds(0, topHeight, width, height - topHeight - bottomHeight);
            }
        });
        setResizable(true);
        setMaximizable(true);

        setClosable(true);
        setIconifiable(true);
        setupAdminTable();
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        ButtonGroup group = new ButtonGroup();

        group.add(rbPending);
        group.add(rbApproved);
        group.add(rbRejected);
        group.add(rbAll);

        rbPending.setSelected(true);

        loadData("Pending");
        autoResizeTableColumns(tblAdmins);
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
        btnApprove.addActionListener(e -> updateSelectedAdminsStatus("Approved"));
        btnReject.addActionListener(e -> updateSelectedAdminsStatus("Rejected"));
        btnClear.addActionListener(e -> tblAdmins.clearSelection());
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
        autoResizeTableColumns(tblAdmins);

        // Show approve/reject/clear buttons only for Pending
        buttonPanel.setVisible(status.equalsIgnoreCase("Pending"));
    }
    @Override
public void setupTable() {
    setupAdminTable();   // 👈 specific implementation
}
@Override
public void loadData(String status) {
    loadAdmins(status);   // reuse your existing method
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

            String sql = "UPDATE admin_info SET status = ? WHERE id = ?";
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
                    String username = tblAdmins.getValueAt(row, 24).toString(); // email as username
                    String password = generateRandomPassword(8); // generate 8-char password

                    PreparedStatement pstInsert = conn.prepareStatement(
                            "INSERT INTO users (name, username, password, designation_type) VALUES (?, ?, ?, ?)"
                    );
                    pstInsert.setString(1, name);
                    pstInsert.setString(2, username);
                    pstInsert.setString(3, password);
                    pstInsert.setString(4, "Admin"); // designation type
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
                loadData("Rejected");       // show rejected admins
            } else {
                loadData(getSelectedStatusFilter()); // normal reload
            }

            tblAdmins.clearSelection();

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
            tblAdmins.setEnabled(true);
        }
    }
*/
private void updateSelectedAdminsStatus(String newStatus) {
    int[] selectedRows = tblAdmins.getSelectedRows();

    if (selectedRows.length == 0) {
        JOptionPane.showMessageDialog(this, "No rows selected!");
        return;
    }

    List<Integer> ids = new ArrayList<>();
    List<String> names = new ArrayList<>();
    List<String> usernames = new ArrayList<>();

    for (int row : selectedRows) {
        ids.add((Integer) tblAdmins.getValueAt(row, 0));
        names.add(tblAdmins.getValueAt(row, 1).toString());
        usernames.add(tblAdmins.getValueAt(row, 24).toString());
    }

    tblAdmins.setEnabled(false); // disable while processing
    ViewAdminDAO dao = new ViewAdminDAO();
    boolean success = dao.updateAdminsStatus(ids, names, usernames, newStatus);
    tblAdmins.setEnabled(true);

    if (success) {
        JOptionPane.showMessageDialog(this, "Status updated successfully!");
        // reload table
        if (newStatus.equalsIgnoreCase("Rejected")) {
            rbRejected.setSelected(true);
            loadAdmins("Rejected");
        } else {
            loadAdmins(getSelectedStatusFilter());
        }
        tblAdmins.clearSelection();
    } else {
        JOptionPane.showMessageDialog(this, "Error updating status!");
    }
}

    private void setupAdminTable() {
        // Create a DefaultTableModel
        DefaultTableModel model = new DefaultTableModel();

        // Add columns
        String[] columns = {
            "ID", "Full Name", "Birth Date", "Gender", "NID",
            "Phone Number", "Email", "Present Division", "Present District",
            "Permanent Division", "Permanent District", "Office",
            "Higher Education", "Official Designation", "Organization Type", "Blood Group",
            "Medical Training", "Search & Rescue", "Swimming", "Driving",
            "Language Skills", "Technical Skills", "Physical Fitness Level",
            "Previous Disaster Exp", "Username", "Status"
        };

        for (String col : columns) {
            model.addColumn(col);
        }

        // Assign model to table
        tblAdmins.setModel(model);

        tblAdmins.setBackground(Color.WHITE);
        tblAdmins.setFillsViewportHeight(true);
        jScrollPane1.getViewport().setBackground(Color.WHITE);

        // Header styling
        JTableHeader header = tblAdmins.getTableHeader();
        header.setBackground(new Color(10, 28, 54));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        tblAdmins.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
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

        // Optional: make row height taller
        tblAdmins.setRowHeight(30);
        DefaultTableCellRenderer zebraRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Selection color maintain kora
                if (isSelected) {
                    c.setBackground(new Color(8, 114, 138));
                    c.setForeground(Color.WHITE);
                } else {
                    // Zebra effect
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(245, 249, 255));
                    }
                    c.setForeground(Color.BLACK);
                }
                // **Center text in cells**
                if (c instanceof JLabel) {
                    ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                }
                return c;
            }
        };

// Apply to all columns
        for (int i = 0; i < tblAdmins.getColumnCount(); i++) {
            tblAdmins.getColumnModel().getColumn(i).setCellRenderer(zebraRenderer);
        }
        // Enable sorting
        tblAdmins.setAutoCreateRowSorter(true);

        // Set column widths (adjust as needed)
        for (int i = 0; i < tblAdmins.getColumnCount(); i++) {
            TableColumn column = tblAdmins.getColumnModel().getColumn(i);

            int width = 120;

            TableCellRenderer renderer = header.getDefaultRenderer();
            Component comp = renderer.getTableCellRendererComponent(
                    tblAdmins,
                    column.getHeaderValue(),
                    false,
                    false,
                    0,
                    i
            );

            width = Math.max(width, comp.getPreferredSize().width + 20);

            column.setPreferredWidth(width);
        }
        // Apply wrapping renderer to all columns
        /*TextAreaRenderer renderer = new TextAreaRenderer();
        for (int i = 0; i < tblAdmins.getColumnCount(); i++) {
            tblAdmins.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }*/

// Disable automatic resizing so horizontal scroll works
        tblAdmins.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    private void autoResizeTableColumns(JTable table) {
        for (int col = 0; col < table.getColumnCount(); col++) {
            int maxWidth = 0;

            // header width
            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(
                    table, table.getColumnName(col), false, false, 0, col);
            maxWidth = headerComp.getPreferredSize().width;

            // cell content width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, col);
                Component comp = table.prepareRenderer(cellRenderer, row, col);
                maxWidth = Math.max(maxWidth, comp.getPreferredSize().width);
            }

            // padding
            maxWidth += 15;

            // set column width
            table.getColumnModel().getColumn(col).setPreferredWidth(maxWidth);
        }
    }
/*
    public void loadAdmins(String statusFilter) {
        // 1️⃣ Get the table model
        DefaultTableModel model = (DefaultTableModel) tblAdmins.getModel();

        // 2️⃣ Clear existing rows
        model.setRowCount(0);

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            // 3️⃣ Connect using your SQLiteConnect class
            conn = SQLiteConnect.Connectordb();

            // 4️⃣ SQL query: select only the columns you want, combine birth date
            String sql = "SELECT id, full_name, "
                    + "birth_date || '-' || birth_month || '-' || birth_year AS birth_date, "
                    + "gender, nid, phone_number, email, present_division, present_district, "
                    + "permanent_division, permanent_district, office_building_name AS office, "
                    + "higher_education, official_designation, organization_type, blood_group, "
                    + "medical_training, search_and_rescue, swimming, driving, "
                    + "language_skills, technical_skills, physical_fitness_level, "
                    + "previous_disaster_experience,username, status "
                    + "FROM admin_info";

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
                    rs.getString("office"),
                    rs.getString("higher_education"),
                    rs.getString("official_designation"),
                    rs.getString("organization_type"),
                    rs.getString("blood_group"),
                    rs.getString("medical_training"),
                    rs.getString("search_and_rescue"),
                    rs.getString("swimming"),
                    rs.getString("driving"),
                    rs.getString("language_skills"),
                    rs.getString("technical_skills"),
                    rs.getString("physical_fitness_level"),
                    rs.getString("previous_disaster_experience"),
                    rs.getString("username"),
                    rs.getString("status")
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
    public void loadAdmins(String statusFilter) {
    // 1️⃣ Get the table model
    DefaultTableModel model = (DefaultTableModel) tblAdmins.getModel();

    // 2️⃣ Clear existing rows
    model.setRowCount(0);

    try {
        // 3️⃣ Use DAO to fetch list of admins
        ViewAdminDAO dao = new ViewAdminDAO();
        List<ViewAdmin> admins = dao.getAdmins(statusFilter);

        // 4️⃣ Loop through the list and add rows to table
        for (ViewAdmin admin : admins) {
            Object[] row = {
                admin.getId(),
                admin.getFullName(),
                admin.getBirthDate(),
                admin.getGender(),
                admin.getNid(),
                admin.getPhoneNumber(),
                admin.getEmail(),
                admin.getPresentDivision(),
                admin.getPresentDistrict(),
                admin.getPermanentDivision(),
                admin.getPermanentDistrict(),
                admin.getOffice(),
                admin.getHigherEducation(),
                admin.getOfficialDesignation(),
                admin.getOrganizationType(),
                admin.getBloodGroup(),
                admin.getMedicalTraining(),
                admin.getSearchAndRescue(),
                admin.getSwimming(),
                admin.getDriving(),
                admin.getLanguageSkills(),
                admin.getTechnicalSkills(),
                admin.getPhysicalFitnessLevel(),
                admin.getPreviousDisasterExp(),
                admin.getUsername(),
                admin.getStatus()
            };
            model.addRow(row);
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading admin data: " + e.getMessage());
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
        tblAdmins = new javax.swing.JTable();
        buttonPanel = new javax.swing.JPanel();
        btnApprove = new javax.swing.JButton();
        btnReject = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        rbuttonsPanel.setBackground(new java.awt.Color(255, 255, 255));

        rbPending.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        rbPending.setForeground(new java.awt.Color(153, 153, 0));
        rbPending.setText("Pending");
        rbPending.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        rbApproved.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        rbApproved.setForeground(new java.awt.Color(0, 204, 51));
        rbApproved.setText("Approved");
        rbApproved.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        rbRejected.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        rbRejected.setForeground(new java.awt.Color(204, 0, 0));
        rbRejected.setText("Rejected");
        rbRejected.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        rbAll.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        rbAll.setForeground(new java.awt.Color(51, 0, 255));
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
                .addGap(21, 21, 21)
                .addGroup(rbuttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbPending, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbApproved, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbRejected, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbAll, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        tablePanel.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tblAdmins.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblAdmins);

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
        );

        buttonPanel.setBackground(new java.awt.Color(255, 255, 255));

        btnApprove.setBackground(new java.awt.Color(204, 255, 204));
        btnApprove.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnApprove.setForeground(new java.awt.Color(0, 204, 51));
        btnApprove.setText("Approve");
        btnApprove.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnApprove.addActionListener(this::btnApproveActionPerformed);

        btnReject.setBackground(new java.awt.Color(255, 153, 153));
        btnReject.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnReject.setForeground(new java.awt.Color(204, 51, 0));
        btnReject.setText("Reject");
        btnReject.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnClear.setBackground(new java.awt.Color(255, 255, 204));
        btnClear.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnClear.setForeground(new java.awt.Color(153, 153, 0));
        btnClear.setText("Clear");
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGap(300, 300, 300)
                .addComponent(btnApprove, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnReject, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(301, 301, 301))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnApprove, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReject, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(rbuttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(rbuttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnApproveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApproveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnApproveActionPerformed


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
    private javax.swing.JTable tblAdmins;
    // End of variables declaration//GEN-END:variables
}
