/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author samih
 */
package com.dg.dao;

import com.dg.model.TaskAssignment;
import com.dg.dbconnection.SQLiteConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskAssignmentDAO {

    // =====================================================
    // ✅ Assign Task to Volunteer
    // =====================================================
    public boolean assignTask(TaskAssignment ta) {

    String sql = """
        INSERT INTO task_assignments
        (task_id, volunteer_username, assigned_by, status)
        VALUES (?, ?, ?, ?)
    """;

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, ta.getTaskId());
        pst.setString(2, ta.getVolunteerUsername());
        pst.setString(3, ta.getAssignedBy());
        pst.setString(4, ta.getStatus());

       int rowsAffected = pst.executeUpdate();

        if (rowsAffected == 0) {
            // Duplicate assignment skipped
            System.out.println("Task " + ta.getTaskId() +
                               " is already assigned to " + ta.getVolunteerUsername());
            return false;
        }

        // Insert successful
        return true;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    public List<TaskAssignment> getAssignmentsByTask(int taskId) {

    List<TaskAssignment> list = new ArrayList<>();

    String sql = """
        SELECT *
        FROM task_assignments
        WHERE task_id = ?
    """;

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, taskId);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {

            TaskAssignment ta = new TaskAssignment(
                    rs.getInt("id"),
                    rs.getInt("task_id"),
                    rs.getString("volunteer_username"),
                    rs.getString("assigned_by"),
                    rs.getTimestamp("assigned_at"),
                    rs.getString("status"),
                    rs.getTimestamp("completed_at")
            );

            list.add(ta);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}


    // =====================================================
    // ✅ Get Assigned Volunteers For A Task
    // =====================================================
    /*
    public List<TaskAssignment> getAssignmentsByTask(int taskId) {

        List<TaskAssignment> list = new ArrayList<>();

        String sql = """
            SELECT *
            FROM task_assignments
            WHERE task_id = ?
        """;

        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, taskId);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                TaskAssignment ta = new TaskAssignment(
                        rs.getInt("id"),
                        rs.getInt("task_id"),
                        rs.getString("volunteer_username"),
                        rs.getString("assigned_by"),
                        rs.getString("status"),
                        rs.getTimestamp("assigned_at"),
                        rs.getTimestamp("completed_at")
                );

                list.add(ta);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
*/


    // =====================================================
    // ✅ Update Status (Optional – For Future Use)
    // =====================================================
    public boolean updateStatus(int taskId,
                                String volunteerUsername,
                                String status) {

        String sql = """
            UPDATE task_assignments
            SET status = ?
            WHERE task_id = ?
              AND volunteer_username = ?
        """;

        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, status);
            pst.setInt(2, taskId);
            pst.setString(3, volunteerUsername);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}