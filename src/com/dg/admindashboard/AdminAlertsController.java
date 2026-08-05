/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.admindashboard;

/**
 *
 * @author samih
 */
import com.dg.dbconnection.SQLiteConnect;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import com.dg.dao.*;

public class AdminAlertsController extends BaseAlertsController {

    private JInternalFrame parent;

    public AdminAlertsController(JTable table, String username, JDesktopPane desktop, JInternalFrame parent) {
        super(table, username, desktop);
        this.parent = parent;
    }

    @Override
    public void setupTable() {
        String[] cols = {"ID", "Title", "Type", "Read Status", "Forward Status", "Date"};
        table.setModel(createReadOnlyModel(cols));
    }

    @Override
    public void loadAlerts() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try (Connection conn = SQLiteConnect.Connectordb()) {
            AlertStatusDAO dao = new AlertStatusDAO();
            ResultSet rs = dao.getAlertsForAdmin(username, conn);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("alert_type"),
                    rs.getString("read_status"),
                    rs.getString("forward_status"),
                    rs.getString("created_at")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Error loading alerts");
        }
    }

    @Override
    public void openDetails() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(parent, "Please select an alert");
            return;
        }

        int alertId = (int) table.getValueAt(row, 0);
        AdminAlertDetailsFrame frame = new AdminAlertDetailsFrame(desktop, username, alertId);
        desktop.add(frame);

        int margin = 40;
        frame.setBounds(margin, margin, desktop.getWidth() - 2 * margin, desktop.getHeight() - 2 * margin);
        frame.setVisible(true);
        try {
            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        loadAlerts(); // refresh list after opening details
    }

    // Method for volunteer responses button
    public void openVolunteerResponses() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(parent, "Please select an alert");
            return;
        }

        int alertId = (int) table.getValueAt(row, 0);
        VolunteerResponsesFrame frame = new VolunteerResponsesFrame(desktop, username, alertId);
        desktop.add(frame);

        int margin = 40;
        frame.setBounds(margin, margin, desktop.getWidth() - 2 * margin, desktop.getHeight() - 2 * margin);
        frame.setVisible(true);
        try {
            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
