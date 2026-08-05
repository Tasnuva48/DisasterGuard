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

import com.dg.volunteerdashboard.*;

public class VolunteerUser extends User {
    public VolunteerUser(String username) {
        super(username);
    }

    @Override
    public void openDashboard() {
        VolunteerDashboard vaf = new VolunteerDashboard(username);
        vaf.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vaf.setVisible(true);
    }
}