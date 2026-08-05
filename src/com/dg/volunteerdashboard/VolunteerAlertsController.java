package com.dg.volunteerdashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import com.dg.dao.VolunteerAlertDAO;
import com.dg.model.VolunteerForwardedAlert;
import com.dg.admindashboard.BaseAlertsController;

public class VolunteerAlertsController extends BaseAlertsController {

    private JInternalFrame parent; // optional, for showing messages relative to frame

    public VolunteerAlertsController(JTable table, String username, JDesktopPane desktop, JInternalFrame parent){
        super(table, username, desktop);
        this.parent = parent;
    }

    @Override
    public void setupTable() {
        String[] cols = {"ID", "Title", "Type", "Admin", "Status", "Forwarded At"};
        table.setModel(createReadOnlyModel(cols));
    }

    @Override
    public void loadAlerts() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try {
            VolunteerAlertDAO dao = new VolunteerAlertDAO();
            List<VolunteerForwardedAlert> alerts = dao.getForwardedAlerts(username);

            for (VolunteerForwardedAlert vfa : alerts) {
                model.addRow(new Object[]{
                    vfa.getAlertId(),
                    vfa.getTitle(),
                    vfa.getType(),
                    vfa.getAdminUsername(),
                    vfa.getResponseStatus(),
                    vfa.getForwardedAt()
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent != null ? parent : table, "Error loading alerts");
        }
    }

    @Override
    public void openDetails() {
        int row = table.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(parent != null ? parent : table,"Please select an alert");
            return;
        }

        int alertId = (int)table.getValueAt(row, 0);
        VolunteerAlertDetailsFrame frame = new VolunteerAlertDetailsFrame(desktop, username, alertId);
        desktop.add(frame);

        int margin = 40;
        frame.setBounds(margin, margin, desktop.getWidth()-2*margin, desktop.getHeight()-2*margin);
        frame.setVisible(true);

        try {
            frame.setSelected(true);
            frame.setMaximum(true);
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        loadAlerts(); // refresh table after opening details
    }
}