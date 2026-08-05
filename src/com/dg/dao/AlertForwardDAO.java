package com.dg.dao;

import com.dg.dbconnection.SQLiteConnect;
import java.sql.*;

public class AlertForwardDAO {

    // Mark alert as forwarded by admin
    public void markAsForwarded(int alertId, String adminUsername) {

        String sql = """
            INSERT INTO alert_forward_status
            (alert_id, admin_username)

            VALUES (?, ?)

            ON CONFLICT(alert_id, admin_username)
            DO NOTHING
        """;

        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst =
                     conn.prepareStatement(sql)) {

            pst.setInt(1, alertId);
            pst.setString(2, adminUsername);

            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Check if alert is already forwarded
    public boolean isForwarded(int alertId, String adminUsername) {

        String sql = """
            SELECT id FROM alert_forward_status
            WHERE alert_id = ? AND admin_username = ?
        """;

        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst =
                     conn.prepareStatement(sql)) {

            pst.setInt(1, alertId);
            pst.setString(2, adminUsername);

            ResultSet rs = pst.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}