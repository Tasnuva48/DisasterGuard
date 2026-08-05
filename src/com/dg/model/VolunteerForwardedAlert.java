/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.model;

import java.sql.Timestamp;

public class VolunteerForwardedAlert extends VolunteerAlert {

    private int alertId;
    private String title;
    private String type;
    private String adminUsername;
    private Timestamp forwardedAt;

    public VolunteerForwardedAlert(int alertId, String title, String type, String adminUsername,
                                   String responseStatus, Timestamp respondedAt, Timestamp forwardedAt) {
        super(null, null, responseStatus, respondedAt); // username & volunteerName can be null here
        this.alertId = alertId;
        this.title = title;
        this.type = type;
        this.adminUsername = adminUsername;
        this.forwardedAt = forwardedAt;
    }

    public int getAlertId() { return alertId; }
    public String getTitle() { return title; }
    public String getType() { return type; }
    public String getAdminUsername() { return adminUsername; }
    public Timestamp getForwardedAt() { return forwardedAt; }
}