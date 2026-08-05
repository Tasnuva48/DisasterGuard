/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.dao;
import com.dg.dbconnection.SQLiteConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author USER
 */
public class UserDAO {
     public String getPassword(String username) {
        String sql = "SELECT password FROM users WHERE username=?";
        
        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return rs.getString("password");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting password: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Update username and password in users table
     * Used when saving edited profile
     */
    public boolean updateUserCredentials(String oldUsername, String newUsername, String password) {
        String sql = "UPDATE users SET username=?, password=? WHERE username=?";
        
        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, newUsername);
            pst.setString(2, password);
            pst.setString(3, oldUsername);
            
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating user credentials: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Validate user login credentials
     * Returns true if username and password match
     */
    public boolean validateLogin(String username, String password) {
        String sql = "SELECT password FROM users WHERE username=?";
        
        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return password.equals(storedPassword);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error validating login: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Check if username already exists
     * Useful for registration/username change validation
     */
    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username=?";
        
        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error checking username: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Create new user account
     * Used during volunteer registration
     */
    public boolean createUser(String username, String password, String userType) {
        String sql = "INSERT INTO users (username, password, user_type) VALUES (?, ?, ?)";
        
        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, userType);
            
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error creating user: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete user account
     * Used when removing a volunteer
     */
    public boolean deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username=?";
        
        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, username);
            
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Change user password
     * Separate method for password-only changes
     */
    public boolean changePassword(String username, String newPassword) {
        String sql = "UPDATE users SET password=? WHERE username=?";
        
        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, newPassword);
            pst.setString(2, username);
            
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error changing password: " + e.getMessage());
            return false;
        }
    }
}
