/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dg.spadmindashboard;

/**
 *
 * @author samih
 */
public interface ViewUserFrame {

    void setupActions();

    String getSelectedStatusFilter();

    void handleRadioButton(String status);

    void setupTable();     // will be implemented differently

    void loadData(String status);  // generic instead of loadAdmins/loadVolunteers
}