/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.model;

import java.util.Date;

public class Alert {
    private int id;
    private String title;
    private String message;
    private String type;
    private String division;
    private String district;
    private String address;
    private String createdBy;
    private Date createdAt;
    private Date expiresAt;

    public Alert(String title, String message, String type,
                 String division, String district,String address, String createdBy, Date expiresAt) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.division = division;
        this.district = district;
        this.address=address;
        this.createdBy = createdBy;
        this.expiresAt = expiresAt;
    }
    public Alert(int id,
                 String title,
                 String message,
                 String type,
                 String division,
                 String district,
                 String address,
                 String createdBy,
                 Date expiresAt) {

        this.id = id;   //  SET ID

        this.title = title;
        this.message = message;
        this.type = type;
        this.division = division;
        this.district = district;
        this.address = address;
        this.createdBy = createdBy;
        this.expiresAt = expiresAt;
    }

    
    public int getId() {
        return id;
    }

    // Getters
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getAddress() { return address; }
    
    public String getType() { return type; }
    public String getDivision() { return division; }
    public String getDistrict() { return district; }
    public String getCreatedBy() { return createdBy; }
    public Date getCreatedAt() { return createdAt; }
    public Date getExpiresAt() { return expiresAt; }

    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}