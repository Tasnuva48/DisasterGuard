/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.dbconnection;

/**
 *
 * @author samih
 */
import java.sql.*;


import javax.swing.*;
public class SQLiteConnect {
    public static Connection Connectordb()
    {
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:disasterguardproject.db");
        return conn;
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
    
    
    
    
    
    
}
