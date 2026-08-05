/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dg.admindashboard;

import com.dg.spadmindashboard.*;
import com.dg.admindashboard.AdminProfileFrame;
import com.dg.dbconnection.SQLiteConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import com.dg.volunteerdashboard.*;
import java.awt.Color;
import com.dg.dao.UserSearchDAO;
import com.dg.model.UserSearchResult;
import java.util.List;

/**
 *
 * @author samih
 */
public class SearchVolunteerFrame extends SearchFrameBase {

    /**
     * Creates new form SearchVolunteerFrame
     */
    private JDesktopPane desktop;
    private String username;

    public SearchVolunteerFrame(JDesktopPane desktop,String adminUsername) {
        super();
        //initComponents();
        this.desktop = desktop;
        this.username=adminUsername;

        // -------------- ekhane add korbe ----------------
        // Internal frame er content pane background white
        getContentPane().setBackground(Color.WHITE);

        // jodi table er panel thake, panel-er background-o white
        if (getPanelTable() != null) {
            getPanelTable().setBackground(Color.WHITE);
        }
        // -------------------------------------------------

        getBtnView().setVisible(false);
        getBtnView().setEnabled(false);
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Full Name", "Username", "Location"}, 0
        );
        getTblResults().setModel(model);
        getTblResults().setDefaultEditor(Object.class, null);
        getTblResults().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getTblResults().getSelectionModel().addListSelectionListener(e -> {
            boolean rowSelected = getTblResults().getSelectedRow() != -1;
            getBtnView().setEnabled(rowSelected); // enable if row selected
        });
        getTxtSearch().addActionListener(e -> {
            String query = getTxtSearch().getText().trim();

            // Ignore watermark text
            if (query.isEmpty() || query.equals("Search by name or username...")) {
                // Optionally clear previous results

                model.setRowCount(0);
                return;
            }

            performSearch(query);
            showResults();
        });
        getBtnView().addActionListener(e -> {
            openSelectedProfile();
        });
        getBtnClear().addActionListener(e -> clearSearch());

        // 2️⃣ MouseListener for table row double-click
        getTblResults().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Only act on double-click
                if (evt.getClickCount() == 2) {
                    openSelectedProfile();

                }
            }
        });
    }
    /*

    @Override
    protected void performSearch(String query) {
        // Get table model
        DefaultTableModel model = (DefaultTableModel) getTblResults().getModel();
        model.setRowCount(0); // Clear previous rows

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            // Connect to DB
            conn = SQLiteConnect.Connectordb();

            // SQL query: search by full_name or username
            String sql = "SELECT full_name, username, present_district || ', ' || present_division AS location "
                    + "FROM volunteer_info "
                    + "WHERE full_name LIKE ? OR username LIKE ?";

            pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + query + "%");
            pst.setString(2, "%" + query + "%");

            rs = pst.executeQuery();

            // Populate table
            while (rs.next()) {
                String fullName = rs.getString("full_name");
                String username = rs.getString("username");
                String location = rs.getString("location");

                model.addRow(new Object[]{fullName, username, location});
            }
            if (model.getRowCount() > 0) {
                showResults(); // already shows panel
                getBtnView().setVisible(true); // now show View button
                getTblResults().getTableHeader().setVisible(true); //
            } else {
                getBtnView().setVisible(false); // hide if no results
                getPanelTable().setVisible(false);
                getTblResults().getTableHeader().setVisible(false);
            }

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No volunteer found for: " + query);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    */
    @Override
protected void performSearch(String query) {

    DefaultTableModel model = (DefaultTableModel) getTblResults().getModel();
    model.setRowCount(0);

    UserSearchDAO dao = new UserSearchDAO();

    // 🔥 Use new DAO method (IMPORTANT CHANGE)
    List<UserSearchResult> list =
            dao.searchVolunteersByAdminDivision(username, query);

    for (UserSearchResult user : list) {
        model.addRow(new Object[]{
            user.getFullName(),
            user.getUsername(),
            user.getLocation()
        });
    }

    if (!list.isEmpty()) {
        showResults();
        getBtnView().setVisible(true);
        getTblResults().getTableHeader().setVisible(true);
    } else {
        getBtnView().setVisible(false);
        getPanelTable().setVisible(false);
        getTblResults().getTableHeader().setVisible(false);

        JOptionPane.showMessageDialog(this,
                "No volunteers found in your division for: " + query);
    }
}

    protected void openSelectedProfile() {

        int row = getTblResults().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row first!");
            return;
        }

        String username = getTblResults().getValueAt(row, 1).toString();

        // Create a new profile frame
        ViewVolunteerProfile profileFrame = new ViewVolunteerProfile(desktop, username, false);

        // Add it to desktop
        desktop.add(profileFrame);

        int margin = 40;

        // Set bounds to fill the desktop with margins
        profileFrame.setBounds(
                margin,
                margin,
                desktop.getWidth() - 2 * margin,
                desktop.getHeight() - 2 * margin
        );

        profileFrame.setVisible(true);

        try {
            profileFrame.setSelected(true); // bring to front
            profileFrame.setMaximum(true);  // maximize inside desktop
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

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 274, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
