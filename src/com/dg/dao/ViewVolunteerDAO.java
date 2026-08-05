/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.dao;

/**
 *
 * @author samih
 */


import com.dg.model.*;
import com.dg.util.EmailUtil;
import com.dg.dbconnection.SQLiteConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViewVolunteerDAO {

    public List<ViewVolunteer> getVolunteers(String statusFilter) {
        List<ViewVolunteer> list = new ArrayList<>();
       String sql = "SELECT "
                    + "id, "
                    + "full_name, "
                    + "birth_date || '-' || birth_month || '-' || birth_year AS birth_date, "
                    + "gender, "
                    + "nid, "
                    + "phone_number, "
                    + "email, "
                    + "present_division, "
                    + "present_district, "
                    + "permanent_division, "
                    + "permanent_district, "
                    + "blood_group, "
                    + "medical_training, "
                    + "search_and_rescue, "
                    + "swimming, "
                    + "driving, "
                    + "language_skills, "
                    + "technical_skills, "
                    + "physical_fitness_level, "
                    + "previous_disaster_experience, "
                    + "profession, "
                    + "roles_performed, "
                    + "username, "
                    + "status "
                    + "FROM volunteer_info";

        if (!statusFilter.equalsIgnoreCase("All")) {
            sql += " WHERE status = ?";
        }

        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            if (!statusFilter.equalsIgnoreCase("All")) {
                pst.setString(1, statusFilter);
            }

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ViewVolunteer v = new ViewVolunteer(
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
                    rs.getString("blood_group"),
                    rs.getString("medical_training"),
                    rs.getString("search_and_rescue"),
                    rs.getString("swimming"),
                    rs.getString("driving"),
                    rs.getString("language_skills"),
                    rs.getString("technical_skills"),
                    rs.getString("physical_fitness_level"),
                    rs.getString("previous_disaster_experience"),
                    rs.getString("profession"), // NEW
                    rs.getString("roles_performed"), // NEW
                    rs.getString("username"), // always last
                    rs.getString("status") // always last
                );
                list.add(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public boolean updateVolunteersStatus(List<Integer> ids, List<String> names, List<String> usernames, String newStatus) {
    if (ids == null || ids.isEmpty()) return false;

    Connection conn = null;
    PreparedStatement pstUpdate = null;

    try {
        conn = SQLiteConnect.Connectordb();
        conn.setAutoCommit(false); // start transaction

        String sqlUpdate = "UPDATE volunteer_info SET status = ? WHERE id = ?";
        pstUpdate = conn.prepareStatement(sqlUpdate);

        for (int i = 0; i < ids.size(); i++) {
            int id = ids.get(i);

            // --- Update status ---
            pstUpdate.setString(1, newStatus);
            pstUpdate.setInt(2, id);
            pstUpdate.addBatch();

            // --- Insert into users if approved ---
            if ("Approved".equalsIgnoreCase(newStatus)) {
                String password = generateRandomPassword(8);
                String sqlInsert = "INSERT INTO users (name, username, password, designation_type) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstInsert = conn.prepareStatement(sqlInsert)) {
                    pstInsert.setString(1, names.get(i));
                    pstInsert.setString(2, usernames.get(i));
                    pstInsert.setString(3, password);
                    pstInsert.setString(4, "Volunteer");
                    pstInsert.executeUpdate();
                }

                // ✅ Sync forwarded alerts for this volunteer
                VolunteerAlertDAO alertDao = new VolunteerAlertDAO();
                alertDao.insertForwardedAlertsForNewVolunteer(usernames.get(i), conn);
                 // ✅ Get email using username
                    String email = getEmailByUsername(conn, usernames.get(i));

                    // ✅ Send email in background thread
                    String usernameFinal = usernames.get(i);
                    String passwordFinal = password;

                    new Thread(() -> {
                        EmailUtil.sendEmail(email, usernameFinal, passwordFinal);
                    }).start();
            }
        }

        pstUpdate.executeBatch();
        conn.commit();
        return true;

    } catch (Exception e) {
        e.printStackTrace();
        try { if (conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
        return false;
    } finally {
        try { if (pstUpdate != null) pstUpdate.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
    }
}
     // ✅ Get email by username
    private String getEmailByUsername(Connection conn, String username) {
        String email = null;
        String sql = "SELECT email FROM volunteer_info WHERE username = ?";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                email = rs.getString("email");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return email;
    }


// --- Helper method for password generation ---
private String generateRandomPassword(int length) {
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*";
    StringBuilder sb = new StringBuilder();
    java.util.Random random = new java.util.Random();
    for (int i = 0; i < length; i++) {
        sb.append(chars.charAt(random.nextInt(chars.length())));
    }
    return sb.toString();
}
}
