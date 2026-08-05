/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.dao;

import com.dg.model.UserSearchResult;
import com.dg.dbconnection.SQLiteConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserSearchDAO {

    public List<UserSearchResult> searchUsers(String tableName, String query) {

    List<UserSearchResult> list = new ArrayList<>();

    String sql = "SELECT full_name, username, " +
                 "present_district || ', ' || present_division AS location " +
                 "FROM " + tableName +
                 " WHERE (full_name LIKE ? OR username LIKE ?) " +  
                 "AND status = 'Approved'";                         

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, "%" + query + "%");
        pst.setString(2, "%" + query + "%");

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            list.add(new UserSearchResult(
                    rs.getString("full_name"),
                    rs.getString("username"),
                    rs.getString("location")
            ));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
    public List<UserSearchResult> searchVolunteersByAdminDivision(String adminUsername, String query) {

    List<UserSearchResult> list = new ArrayList<>();

    String sql = """
        SELECT v.full_name, v.username,
               v.present_district || ', ' || v.present_division AS location
        FROM volunteer_info v
        JOIN admin_info a
            ON a.username = ?
        WHERE v.present_division = a.present_division
          AND (v.full_name LIKE ? OR v.username LIKE ?)
          AND v.status = 'Approved'   --  volunteer must be approved
          AND a.status = 'Approved'   --  admin must be approved
    """;

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, adminUsername);
        pst.setString(2, "%" + query + "%");
        pst.setString(3, "%" + query + "%");

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            list.add(new UserSearchResult(
                    rs.getString("full_name"),
                    rs.getString("username"),
                    rs.getString("location")
            ));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
}