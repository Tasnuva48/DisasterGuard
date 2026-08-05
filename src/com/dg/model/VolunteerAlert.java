/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.model;

import java.sql.Timestamp;

public class VolunteerAlert {
    private String username;
    private String volunteerName;
    private String responseStatus;
    private Timestamp respondedAt;

    public VolunteerAlert(String username, String volunteerName, String responseStatus, Timestamp respondedAt) {
        this.username = username;
        this.volunteerName = volunteerName;
        this.responseStatus = responseStatus;
        this.respondedAt = respondedAt;
    }

    public String getUsername() { return username; }
    public String getVolunteerName() { return volunteerName; }
    public String getResponseStatus() { return responseStatus; }
    public Timestamp getRespondedAt() { return respondedAt; }
}
