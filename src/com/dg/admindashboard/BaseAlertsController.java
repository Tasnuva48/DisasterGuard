/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.admindashboard;

/**
 *
 * @author samih
 */
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
public abstract class BaseAlertsController {
    protected JTable table;
    protected String username;
    protected JDesktopPane desktop;

    public BaseAlertsController(JTable table, String username, JDesktopPane desktop){
        this.table = table;
        this.username = username;
        this.desktop = desktop;
    }

    // Helper to create a read-only table model
    protected DefaultTableModel createReadOnlyModel(String[] columns){
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col){ return false; }
        };
    }

    // Abstract methods to override in subclasses
    public abstract void setupTable();
    public abstract void loadAlerts();
    public abstract void openDetails();
}