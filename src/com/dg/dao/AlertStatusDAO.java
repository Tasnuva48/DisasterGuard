/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.dao;
import com.dg.model.*; // Create this model

import java.util.ArrayList;
import java.util.List;

import com.dg.dbconnection.SQLiteConnect;
import java.sql.*;

public class AlertStatusDAO {

    // Insert a status row for an admin
    public void insertStatusForAdmin(int alertId, String adminUsername) throws SQLException {
        String sql = "INSERT INTO alert_read_status(alert_id, admin_username, read_status) VALUES (?, ?, 'Unread')";
        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, alertId);
            pst.setString(2, adminUsername);
            pst.executeUpdate();
        }
    }
    public void insertStatusForNewApprovedAdmin(Connection conn,String adminUsername) throws SQLException {
    String sql = "INSERT INTO alert_read_status (alert_id, admin_username, read_status) " +
                 "SELECT a.id, ?, 'Unread' " +
                 "FROM alerts a " +
                 "WHERE (a.expires_at IS NULL OR a.expires_at > CURRENT_TIMESTAMP) " +
                 "AND NOT EXISTS ( " +
                 "    SELECT 1 FROM alert_read_status ars " +
                 "    WHERE ars.alert_id = a.id AND ars.admin_username = ? " +
                 ")";

    try (PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, adminUsername);
        pst.setString(2, adminUsername);

        pst.executeUpdate();
    }
}

    // Insert status rows for all admins (batch)
    /*
    public void insertStatusForAllAdmins(int alertId) throws SQLException {
        String sql = "INSERT INTO alert_read_status(alert_id, admin_username, read_status) VALUES (?, ?, 'Unread')";
        try (Connection conn = SQLiteConnect.Connectordb();
             Statement stmt = conn.createStatement();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery("SELECT username FROM admin_info");

            while (rs.next()) {
                String adminUsername = rs.getString("username");
                pst.setInt(1, alertId);
                pst.setString(2, adminUsername);
                pst.addBatch();
            }

            pst.executeBatch();
        }
    }
    */
    public void insertStatusForAllAdmins(int alertId) throws SQLException {
    String sqlInsert = "INSERT INTO alert_read_status(alert_id, admin_username, read_status) VALUES (?, ?, 'Unread')";
    String sqlSelect = "SELECT username FROM users WHERE designation_type = 'Admin'";

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pstInsert = conn.prepareStatement(sqlInsert);
         PreparedStatement pstSelect = conn.prepareStatement(sqlSelect);
         ResultSet rs = pstSelect.executeQuery()) {

        while (rs.next()) {
            String adminUsername = rs.getString("username");

            pstInsert.setInt(1, alertId);
            pstInsert.setString(2, adminUsername);
            pstInsert.addBatch();
        }

        pstInsert.executeBatch();
    }
}
    public List<AlertStatus> getAllAlertStatuses() throws SQLException {
    List<AlertStatus> list = new ArrayList<>();
/*
    String sql = """
        SELECT a.id, a.title, a.alert_type, a.created_at,
               COUNT(ars.admin_username) AS totalAdmins,
               SUM(CASE WHEN ars.read_status='Read' THEN 1 ELSE 0 END) AS readAdmins
        FROM alerts a
        LEFT JOIN alert_read_status ars ON a.id = ars.alert_id
        GROUP BY a.id
        ORDER BY a.created_at DESC
    """;
    */
String sql = """
    SELECT a.id, a.title, a.alert_type, a.created_at,
           COUNT(ai.username) AS totalAdmins,
           SUM(CASE WHEN ars.read_status='Read' THEN 1 ELSE 0 END) AS readAdmins
    FROM alerts a
    LEFT JOIN alert_read_status ars ON a.id = ars.alert_id
    LEFT JOIN admin_info ai 
        ON ars.admin_username = ai.username 
        AND ai.status = 'Approved'
    GROUP BY a.id
    ORDER BY a.created_at DESC
""";

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            list.add(new AlertStatus(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("alert_type"),
                rs.getString("created_at"),
                rs.getInt("readAdmins"),
                rs.getInt("totalAdmins")
            ));
        }
    }

    return list;
}
    public List<AdminAlertStatus> getAdminReadStatus(int alertId) throws SQLException {
    List<AdminAlertStatus> list = new ArrayList<>();
    String sql = "SELECT ars.admin_username, u.name AS full_name, ars.read_status, ars.read_at " +
                 "FROM alert_read_status ars " +
                 "JOIN users u ON ars.admin_username = u.username " +
                 "WHERE ars.alert_id = ? AND u.designation_type = 'Admin' " +
                 "ORDER BY u.name ASC";

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {
        pst.setInt(1, alertId);
        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                AdminAlertStatus status = new AdminAlertStatus(
                    rs.getString("admin_username"),
                    rs.getString("full_name"),
                    rs.getString("read_status"),
                    rs.getTimestamp("read_at")
                );
                list.add(status);
            }
        }
    }
    return list;
}
    /*
    public ResultSet getAlertsForAdmin(String username, Connection conn)
        throws SQLException {

    String sql = """
        SELECT 
            a.id,
            a.title,
            a.alert_type,
            a.created_at,

            COALESCE(ars.read_status,'Unread') AS read_status,

            CASE
              WHEN afs.id IS NULL THEN 'Not Forwarded'
              ELSE 'Forwarded'
            END AS forward_status

        FROM alerts a

        LEFT JOIN alert_read_status ars
          ON a.id = ars.alert_id
          AND ars.admin_username = ?

        LEFT JOIN alert_forward_status afs
          ON a.id = afs.alert_id
          AND afs.admin_username = ?

        ORDER BY

        CASE
          WHEN ars.read_status IS NULL AND afs.id IS NULL THEN 1
          WHEN ars.read_status='Read' AND afs.id IS NULL THEN 2
          WHEN ars.read_status IS NULL AND afs.id IS NOT NULL THEN 3
          ELSE 4
        END,

        a.created_at DESC
        """;

    PreparedStatement pst = conn.prepareStatement(sql);

    pst.setString(1, username);
    pst.setString(2, username);

    return pst.executeQuery();
}
*/
    public ResultSet getAlertsForAdmin(String username, Connection conn)
        throws SQLException {

    String sql = """
        SELECT 
            a.id,
            a.title,
            a.alert_type,
            a.created_at,

            COALESCE(ars.read_status,'Unread') AS read_status,

            CASE
              WHEN afs.id IS NULL THEN 'Not Forwarded'
              ELSE 'Forwarded'
            END AS forward_status

        FROM alerts a

        LEFT JOIN alert_read_status ars
          ON a.id = ars.alert_id
          AND ars.admin_username = ?

        LEFT JOIN alert_forward_status afs
          ON a.id = afs.alert_id
          AND afs.admin_username = ?

        WHERE 
            a.expires_at IS NULL
            OR a.expires_at > CURRENT_TIMESTAMP

        ORDER BY

        CASE
          WHEN ars.read_status IS NULL AND afs.id IS NULL THEN 1
          WHEN ars.read_status='Read' AND afs.id IS NULL THEN 2
          WHEN ars.read_status IS NULL AND afs.id IS NOT NULL THEN 3
          ELSE 4
        END,

        a.created_at DESC
        """;

    PreparedStatement pst = conn.prepareStatement(sql);

    pst.setString(1, username);
    pst.setString(2, username);

    return pst.executeQuery();
}
    public boolean isAlreadyRead(int alertId, String username) {
    String sql = """
        SELECT read_status 
        FROM alert_read_status
        WHERE alert_id=? AND admin_username=?
    """;

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, alertId);
        pst.setString(2, username);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return "Read".equalsIgnoreCase(rs.getString("read_status"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false; // not found → treat as unread
}
    public void markAsRead(int alertId, String username) {
    String sql = """
        UPDATE alert_read_status
        SET read_status='Read',
            read_at = DATETIME('now','localtime')
        WHERE alert_id=? AND admin_username=? AND read_status != 'Read'
    """;

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, alertId);
        pst.setString(2, username);

        int rowsUpdated = pst.executeUpdate();
        if (rowsUpdated == 0) {
            // Optional: insert new row if it didn't exist (rare for old alerts)
            String insertSql = "INSERT INTO alert_read_status (alert_id, admin_username, read_status, read_at) " +
                               "VALUES (?, ?, 'Read', DATETIME('now','localtime'))";
            try (PreparedStatement pst2 = conn.prepareStatement(insertSql)) {
                pst2.setInt(1, alertId);
                pst2.setString(2, username);
                pst2.executeUpdate();
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}