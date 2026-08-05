/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.homepage;

/**
 *
 * @author samih
 */
import javax.swing.*;
import com.dg.admindashboard.*;

public class AdminUser extends User {
    public AdminUser(String username) {
        super(username);
    }

    @Override
    public void openDashboard() {
        AdminDashboardFrame adf = new AdminDashboardFrame(username);
        adf.setExtendedState(JFrame.MAXIMIZED_BOTH);
        adf.setVisible(true);
    }
}