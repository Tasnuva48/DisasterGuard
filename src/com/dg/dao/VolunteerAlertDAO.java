/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.dao;

import com.dg.dbconnection.SQLiteConnect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.dg.model.*;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class VolunteerAlertDAO {

    // Send alert to all volunteers in same division as admin
    public void sendToVolunteersByDivision(int alertId,
                                          String adminUsername) {

        String sql = """
            INSERT OR IGNORE INTO volunteer_alerts
            (alert_id, volunteer_username, admin_username)

            SELECT ?, v.username, a.username

            FROM volunteer_info v
            JOIN admin_info a
              ON v.present_division = a.present_division

            WHERE a.username = ? AND v.status='Approved'
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
    // 🔥 Insert all already forwarded alerts for a newly approved volunteer
// Insert volunteers for already forwarded alerts (for newly approved volunteer)
// Insert forwarded alerts for newly approved volunteer
public void insertForwardedAlertsForNewVolunteer(String volunteerUsername, Connection conn) {

    String sql = """
        INSERT OR IGNORE INTO volunteer_alerts
        (alert_id, volunteer_username, admin_username)

        SELECT
            af.alert_id,
            v.username,
            af.admin_username

        FROM alert_forward_status af
        JOIN alerts 
            ON alerts.id = af.alert_id
            AND(alerts.expires_at is null or  alerts.expires_at >= date('now'))
        JOIN admin_info a
            ON af.admin_username = a.username

        JOIN volunteer_info v
            ON v.username = ?
            AND v.present_division = a.present_division
    """;

    try{
         PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, volunteerUsername);
        pst.executeUpdate();
        pst.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    // Volunteer responds to alert
    public void updateVolunteerResponse(int alertId,
                                        String volunteerUsername,
                                        String status) {

        String sql = """
            UPDATE volunteer_alerts
            SET response_status = ?,
                responded_at = CURRENT_TIMESTAMP
            WHERE alert_id = ?
              AND volunteer_username = ?
        """;

        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst =
                     conn.prepareStatement(sql)) {

            pst.setString(1, status);
            pst.setInt(2, alertId);
            pst.setString(3, volunteerUsername);

            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Object[]> getAcceptedVolunteersForAlert(int alertId) {

    List<Object[]> list = new ArrayList<>();

    String sql = """
        SELECT 
            v.full_name,
            v.username,
            v.present_division,
            v.present_district
        FROM volunteer_alerts va
        JOIN volunteer_info v
            ON va.volunteer_username = v.username
        WHERE va.alert_id = ?
        AND va.response_status = 'Yes'
    """;

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, alertId);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {

            Object[] row = new Object[]{
                    rs.getString("full_name"),
                    rs.getString("username"),
                    rs.getString("present_division"),
                    rs.getString("present_district")
            };

            list.add(row);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}


    // Get all volunteer responses for an alert (for admin view)
    public ResultSet getResponsesByAlert(int alertId)
            throws SQLException {

        String sql = """
            SELECT
                v.full_name,
                v.phone_number,
                va.volunteer_username,
                va.response_status,
                va.responded_at

            FROM volunteer_alerts va

            JOIN volunteer_info v
              ON va.volunteer_username = v.username

            WHERE va.alert_id = ?

            ORDER BY va.responded_at DESC
        """;

        Connection conn = SQLiteConnect.Connectordb();

        PreparedStatement pst =
                conn.prepareStatement(sql);

        pst.setInt(1, alertId);

        return pst.executeQuery();
    }


    // Check if admin already sent alert
    public boolean alreadySent(int alertId, String adminUsername) {

        String sql = """
            SELECT id FROM volunteer_alerts
            WHERE alert_id = ?
              AND admin_username = ?
            LIMIT 1
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
    public List<Object[]> getVolunteersWithAssignmentStatus(int alertId, int taskId) {

    List<Object[]> list = new ArrayList<>();

    String sql = """
    SELECT 
        v.full_name,
        v.username,
        v.present_division,
        v.present_district,
        COALESCE(ta.status, 'Not Assigned') AS task_status,
        ta.completed_at
    FROM volunteer_alerts va
    JOIN volunteer_info v
        ON va.volunteer_username = v.username
    LEFT JOIN task_assignments ta
        ON ta.task_id = ?
        AND ta.volunteer_username = v.username
    WHERE va.alert_id = ?
      AND va.response_status = 'Yes'
    ORDER BY
        CASE
            WHEN ta.status IS NULL THEN 1
            WHEN ta.status = 'Rejected' THEN 2
            WHEN ta.status = 'Assigned' THEN 3
            WHEN ta.status = 'Accepted' THEN 4
            WHEN ta.status = 'Completed' THEN 5
        END,
        v.full_name ASC
""";

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, taskId);
        pst.setInt(2, alertId);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {

            Timestamp completedAt = rs.getTimestamp("completed_at"); // can be null

            Object[] row = new Object[]{
                    rs.getString("full_name"),
                    rs.getString("username"),
                    rs.getString("present_division"),
                    rs.getString("present_district"),
                    rs.getString("task_status"),
                    completedAt != null 
                        ? new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(completedAt)
                        : ""  // empty string if not completed
            };

            list.add(row);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
    /*
    public List<Object[]> getAssignedTasksForVolunteer(String volunteerUsername) {
    List<Object[]> list = new ArrayList<>();

    String sql = """
        SELECT 
            a.title AS alert_title,
            t.task_title,
            ta.assigned_by,
            ta.assigned_at,
            ta.status,
            ta.completed_at
        FROM task_assignments ta
        JOIN tasks t ON ta.task_id = t.id
        JOIN alerts a ON t.alert_id = a.id
        JOIN volunteer_alerts va 
            ON va.alert_id = a.id 
            AND va.volunteer_username = ta.volunteer_username
        WHERE ta.volunteer_username = ?
          AND va.response_status = 'Yes'
        ORDER BY
            CASE ta.status
                WHEN 'Assigned' THEN 1
                WHEN 'Accepted' THEN 2
                WHEN 'Rejected' THEN 3
                WHEN 'Completed' THEN 4
                ELSE 5
            END,
            ta.assigned_at DESC
    """;

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, volunteerUsername);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Timestamp completedAtTs = rs.getTimestamp("completed_at"); // can be null
    String completedAtStr = null;

    if (completedAtTs != null) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        completedAtStr = sdf.format(completedAtTs);
    }

    Object[] row = new Object[]{
        rs.getString("alert_title"),
        rs.getString("task_title"),
        rs.getString("assigned_by"),
        rs.getString("assigned_at"),
        rs.getString("status"),
        completedAtStr // nicely formatted, or null if not completed
    };

    list.add(row);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
    */
   public List<Object[]> getAssignedTasksForVolunteer(String volunteerUsername) {
    List<Object[]> list = new ArrayList<>();

    String sql = """
        SELECT 
            ta.task_id,             -- hidden column
            a.title AS alert_title,
            t.task_title,
            ta.assigned_by,
            ta.assigned_at,
            ta.status,
            ta.completed_at
        FROM task_assignments ta
        JOIN tasks t ON ta.task_id = t.id
        JOIN alerts a ON t.alert_id = a.id
        JOIN volunteer_alerts va 
            ON va.alert_id = a.id 
            AND va.volunteer_username = ta.volunteer_username
        WHERE ta.volunteer_username = ?
          AND va.response_status = 'Yes'
        ORDER BY
            CASE ta.status
                WHEN 'Assigned' THEN 1
                WHEN 'Accepted' THEN 2
                WHEN 'Rejected' THEN 3
                WHEN 'Completed' THEN 4
                ELSE 5
            END,
            ta.assigned_at DESC
    """;

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, volunteerUsername);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            // Hidden taskId
            int taskId = rs.getInt("task_id");

            // Format assigned_at (optional)
            Timestamp assignedAtTs = rs.getTimestamp("assigned_at");
            String assignedAtStr = assignedAtTs != null
                    ? new SimpleDateFormat("yyyy-MM-dd HH:mm").format(assignedAtTs)
                    : "";

            // Format completed_at safely
            Timestamp completedAtTs = rs.getTimestamp("completed_at");
            String completedAtStr = completedAtTs != null
                    ? new SimpleDateFormat("yyyy-MM-dd HH:mm").format(completedAtTs)
                    : "";

            Object[] row = new Object[]{
                taskId,               // hidden column
                rs.getString("alert_title"),
                rs.getString("task_title"),
                rs.getString("assigned_by"),
                assignedAtStr,
                rs.getString("status"),
                completedAtStr
            };

            list.add(row);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
    public boolean markTaskAsCompleted(int taskId, String volunteerUsername) {
    String sql = "UPDATE task_assignments " +
                 "SET status = 'Completed', completed_at = ? " +
                 "WHERE task_id = ? AND volunteer_username = ?";

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        // Current timestamp
        Timestamp now = new Timestamp(System.currentTimeMillis());
        pst.setTimestamp(1, now);          // completed_at
        pst.setInt(2, taskId);             // task_id
        pst.setString(3, volunteerUsername); // volunteer_username

        int updated = pst.executeUpdate();
        return updated > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    public Task getTaskDetail(int taskId) {

    String sql = """
        SELECT 
            t.id,
            t.task_title,
            t.task_description,
            t.deadline,
            a.title AS alert_title
        FROM tasks t
        JOIN alerts a ON t.alert_id = a.id
        WHERE t.id = ?
    """;

    Task task = null;

    try(Connection conn = SQLiteConnect.Connectordb();
        PreparedStatement pst = conn.prepareStatement(sql)){

        pst.setInt(1, taskId);

        ResultSet rs = pst.executeQuery();

        if(rs.next()){

            String deadline = rs.getString("deadline");
                LocalDate date = null;

                if (deadline != null) {
                    date = LocalDate.parse(deadline);
                }

            task = new Task(
                    rs.getInt("id"),
                    rs.getString("alert_title"),
                    rs.getString("task_title"),
                    rs.getString("task_description"),
                    date
            );
        }

    }catch(Exception e){
        e.printStackTrace();
    }

    return task;
}
    public boolean updateTaskStatus(int taskId, String username, String status){

    String sql = """
        UPDATE task_assignments
        SET status = ?
        WHERE task_id = ? AND volunteer_username = ?
    """;

    try(Connection conn = SQLiteConnect.Connectordb();
        PreparedStatement pst = conn.prepareStatement(sql)){

        pst.setString(1, status);
        pst.setInt(2, taskId);
        pst.setString(3, username);

        return pst.executeUpdate() > 0;

    }catch(Exception e){
        e.printStackTrace();
    }

    return false;
}
    /*
    public List<Object[]> getVolunteersWithAssignmentStatus(int alertId, int taskId) {

    List<Object[]> list = new ArrayList<>();

    String sql = """
    SELECT 
        v.full_name,
        v.username,
        v.present_division,
        v.present_district,
        COALESCE(ta.status, 'Not Assigned') AS task_status

    FROM volunteer_alerts va
    JOIN volunteer_info v
        ON va.volunteer_username = v.username

    LEFT JOIN task_assignments ta
        ON ta.task_id = ?
        AND ta.volunteer_username = v.username

    WHERE va.alert_id = ?
      AND va.response_status = 'Yes'

    ORDER BY
        CASE
            WHEN ta.status IS NULL THEN 1
            WHEN ta.status = 'Rejected' THEN 2
            WHEN ta.status = 'Assigned' THEN 3
            WHEN ta.status = 'Accepted' THEN 4
            WHEN ta.status = 'Completed' THEN 5
        END,
        v.full_name ASC
""";

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, taskId);
        pst.setInt(2, alertId);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {

            Object[] row = new Object[]{
                    rs.getString("full_name"),
                    rs.getString("username"),
                    rs.getString("present_division"),
                    rs.getString("present_district"),
                    rs.getString("task_status")
            };

            list.add(row);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
    */
    /*
    public List<VolunteerAlert> getResponsesByAlertList(int alertId,String adminUsername) throws SQLException {
    List<VolunteerAlert> list = new ArrayList<>();

    String sql = """
        SELECT 
            v.username, 
            v.full_name,
            COALESCE(va.response_status, 'Pending') AS response_status,
            va.responded_at
        FROM volunteer_alerts va
        JOIN volunteer_info v
          ON va.volunteer_username = v.username
        WHERE va.alert_id = ?
        ORDER BY 
            CASE 
                WHEN va.response_status = 'Pending' THEN 0
                WHEN va.response_status = 'Yes' THEN 1
                WHEN va.response_status = 'No' THEN 2
            END,
            v.full_name ASC
    """;

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, alertId);

        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                VolunteerAlert va = new VolunteerAlert(
                    rs.getString("username"),
                    rs.getString("full_name"),
                    rs.getString("response_status"),  // "Yes", "No", or "Pending"
                    rs.getTimestamp("responded_at")
                );
                list.add(va);
            }
        }
    }

    return list;
}
*/
    public List<VolunteerAlert> getResponsesByAlertList(int alertId, String adminUsername) throws SQLException {
    List<VolunteerAlert> list = new ArrayList<>();

    String sql = """
        SELECT 
            v.username, 
            v.full_name,
            COALESCE(va.response_status, 'Pending') AS response_status,
            va.responded_at
        FROM volunteer_alerts va
        JOIN volunteer_info v
            ON va.volunteer_username = v.username
        JOIN admin_info a
            ON a.username = ?
        WHERE va.alert_id = ?
          AND v.present_division = a.present_division
        ORDER BY 
            CASE 
                WHEN va.response_status = 'Pending' THEN 0
                WHEN va.response_status = 'Yes' THEN 1
                WHEN va.response_status = 'No' THEN 2
            END,
            v.full_name ASC
    """;

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, adminUsername);
        pst.setInt(2, alertId);

        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                VolunteerAlert va = new VolunteerAlert(
                    rs.getString("username"),
                    rs.getString("full_name"),
                    rs.getString("response_status"),
                    rs.getTimestamp("responded_at")
                );
                list.add(va);
            }
        }
    }

    return list;
}
    /*
    public List<VolunteerForwardedAlert> getForwardedAlerts(String volunteerUsername) throws SQLException {
    List<VolunteerForwardedAlert> list = new ArrayList<>();

    String sql = """
        SELECT 
            a.id AS alert_id,
            a.title,
            a.alert_type,
            af.admin_username,
            va.response_status,
            va.responded_at,
            af.forwarded_at
        FROM volunteer_alerts va
        JOIN alert_forward_status af
          ON va.alert_id = af.alert_id AND va.admin_username = af.admin_username
        JOIN alerts a
          ON va.alert_id = a.id
        WHERE va.volunteer_username = ?
        ORDER BY 
            CASE WHEN va.response_status='Pending' THEN 0 ELSE 1 END,
            af.forwarded_at DESC
    """;

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, volunteerUsername);

        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                VolunteerForwardedAlert vfa = new VolunteerForwardedAlert(
                    rs.getInt("alert_id"),
                    rs.getString("title"),
                    rs.getString("alert_type"),
                    rs.getString("admin_username"),
                    rs.getString("response_status"),
                    rs.getTimestamp("responded_at"),
                    rs.getTimestamp("forwarded_at")
                );
                list.add(vfa);
            }
        }
    }

    return list;
}
    */
    public List<VolunteerForwardedAlert> getForwardedAlerts(String volunteerUsername) throws SQLException {
    List<VolunteerForwardedAlert> list = new ArrayList<>();

    String sql = """
        SELECT 
            a.id AS alert_id,
            a.title,
            a.alert_type,
            af.admin_username,
            va.response_status,
            va.responded_at,
            af.forwarded_at
        FROM volunteer_alerts va
        JOIN alert_forward_status af
          ON va.alert_id = af.alert_id 
          AND va.admin_username = af.admin_username
        JOIN alerts a
          ON va.alert_id = a.id
        WHERE 
            va.volunteer_username = ?
            AND (
                a.expires_at IS NULL
                OR a.expires_at > CURRENT_TIMESTAMP
            )
        ORDER BY 
            CASE WHEN va.response_status='Pending' THEN 0 ELSE 1 END,
            af.forwarded_at DESC
    """;

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, volunteerUsername);

        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                VolunteerForwardedAlert vfa = new VolunteerForwardedAlert(
                    rs.getInt("alert_id"),
                    rs.getString("title"),
                    rs.getString("alert_type"),
                    rs.getString("admin_username"),
                    rs.getString("response_status"),
                    rs.getTimestamp("responded_at"),
                    rs.getTimestamp("forwarded_at")
                );
                list.add(vfa);
            }
        }
    }

    return list;
}
}
