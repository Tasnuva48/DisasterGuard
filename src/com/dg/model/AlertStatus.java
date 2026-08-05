/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.model;

public class AlertStatus {
    private int id;
    private String title;
    private String type;
    private String createdAt;
    private int readAdmins;
    private int totalAdmins;

    public AlertStatus(int id, String title, String type, String createdAt, int readAdmins, int totalAdmins) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.createdAt = createdAt;
        this.readAdmins = readAdmins;
        this.totalAdmins = totalAdmins;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getType() { return type; }
    public String getCreatedAt() { return createdAt; }
    public int getReadAdmins() { return readAdmins; }
    public int getTotalAdmins() { return totalAdmins; }
}