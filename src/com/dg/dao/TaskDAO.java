/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.dao;

import com.dg.model.Task;
import com.dg.dbconnection.SQLiteConnect;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class TaskDAO {

    public int insertTask(Task task) throws Exception {

        String sql = """
            INSERT INTO tasks
            (alert_id, task_title, task_description, deadline, created_by)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = SQLiteConnect.Connectordb(); PreparedStatement pst
                = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setInt(1, task.getAlertId());
            pst.setString(2, task.getTaskTitle());
            pst.setString(3, task.getTaskDescription());

            //pst.setString(4, sdf.format(task.getDeadline()));
            if (task.getDeadline() != null) {
                pst.setString(4, task.getDeadline().toString());
            } else {
                pst.setNull(4, java.sql.Types.DATE);
            }

            pst.setString(5, task.getCreatedBy());

            pst.executeUpdate();

            // Get generated task id
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    public List<Task> getTasksByAlertId(int alertId) {
       

        List<Task> taskList = new ArrayList<>();

        String sql = """
            SELECT id,alert_id, task_title, task_description, deadline, created_by
            FROM tasks
            WHERE alert_id = ?
            
            """;

        try (Connection conn = SQLiteConnect.Connectordb(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, alertId);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                String deadline = rs.getString("deadline");
                LocalDate date = null;

                if (deadline != null) {
                    date = LocalDate.parse(deadline);
                }

                Task task = new Task(
                        rs.getInt("id"),
                        rs.getInt("alert_id"),
                        rs.getString("task_title"),
                        rs.getString("task_description"),
                        date,
                        rs.getString("created_by")
                );

                taskList.add(task);
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        }

        return taskList;
    }

    public boolean deleteTask(int taskId) {

        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = SQLiteConnect.Connectordb(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, taskId);

            int rows = pst.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
