package com.dg.dao;

import com.dg.model.Alert;
import com.dg.dbconnection.SQLiteConnect;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AlertDAO {

    // 1️⃣ Insert alert and return generated ID
    public int insertAlert(Alert alert) throws Exception {
       String sql = """
    INSERT INTO alerts
    (title, message, alert_type, division, district, address, created_by, expires_at)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
""";

        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, alert.getTitle());
            pst.setString(2, alert.getMessage());
            pst.setString(3, alert.getType());
            pst.setString(4, alert.getDivision());
            pst.setString(5, alert.getDistrict());
            pst.setString(6, alert.getAddress());
            pst.setString(7, alert.getCreatedBy());

            if (alert.getExpiresAt() != null) {
                pst.setString(8,
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .format(alert.getExpiresAt()));
            } else {
                pst.setNull(8, Types.VARCHAR);
            }

            pst.executeUpdate();

            // Get generated ID
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0; // failed
    }

    // 2️⃣ Update alert status (Active / Inactive)
    public boolean updateStatus(int alertId, String status) throws SQLException {
        String sql = "UPDATE alerts SET status = ? WHERE id = ?";
        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, status);
            pst.setInt(2, alertId);
            return pst.executeUpdate() > 0;
        }
    }

    // 3️⃣ Fetch all alerts
    public List<Alert> getAllAlerts() throws SQLException {
        List<Alert> alerts = new ArrayList<>();
        String sql = "SELECT * FROM alerts ORDER BY created_at DESC";

        try (Connection conn = SQLiteConnect.Connectordb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Alert alert = new Alert(
        rs.getString("title"),
        rs.getString("message"),
        rs.getString("alert_type"),
        rs.getString("division"),
        rs.getString("district"),
        rs.getString("address"),      // ✅ ADD THIS
        rs.getString("created_by"),
        rs.getTimestamp("expires_at")
);
                alerts.add(alert);
            }
        }

        return alerts;
    }
public List<Alert> getAllAlertsWithId() throws SQLException {
        List<Alert> alerts = new ArrayList<>();
        String sql = "SELECT * FROM alerts ORDER BY created_at DESC";

        try (Connection conn = SQLiteConnect.Connectordb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Alert alert = new Alert(
                        rs.getInt("id"),
                        
        rs.getString("title"),
        rs.getString("message"),
        rs.getString("alert_type"),
        rs.getString("division"),
        rs.getString("district"),
        rs.getString("address"),      // ✅ ADD THIS
        rs.getString("created_by"),
        rs.getTimestamp("expires_at")
);
                alerts.add(alert);
            }
        }

        return alerts;
    }

    // 4️⃣ Optional: fetch single alert by ID
    public Alert getAlertById(int alertId) throws SQLException {
    String sql = "SELECT * FROM alerts WHERE id = ?";
    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {
        pst.setInt(1, alertId);
        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                Alert alert = new Alert(
    rs.getString("title"),
    rs.getString("message"),
    rs.getString("alert_type"),
    rs.getString("division"),
    rs.getString("district"),
    rs.getString("address"),      // ✅ ADD
    rs.getString("created_by"),
    rs.getTimestamp("expires_at")
);
                alert.setCreatedAt(rs.getTimestamp("created_at")); // set created_at
                return alert;
            }
        }
    }
    return null;
}
}