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
import com.dg.spadmindashboard.*;

public class SuperAdminUser extends User {
    public SuperAdminUser(String username) {
        super(username);
    }

    @Override
    public void openDashboard() {
        SuperAdminDashboardFrame saf = new SuperAdminDashboardFrame(username);
        saf.setExtendedState(JFrame.MAXIMIZED_BOTH);
        saf.setVisible(true);
    }
}