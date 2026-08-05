/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.model;

import java.util.Date;

public class AdminAlertStatus {
    private String username;
    private String fullName;
    private String readStatus;
    private Date readAt;

    public AdminAlertStatus(String username, String fullName, String readStatus, Date readAt) {
        this.username = username;
        this.fullName = fullName;
        this.readStatus = readStatus;
        this.readAt = readAt;
    }

    // Getters
    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public String getReadStatus() { return readStatus; }
    public Date getReadAt() { return readAt; }
}