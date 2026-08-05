/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.model;

import java.time.LocalDate;
import java.util.Date;

public class Task {

    private int id;
    private int alertId;
    private String taskTitle;
    private String taskDescription;
    private LocalDate deadline;
    private String createdBy;
    private String alertTitle;

    public Task(int alertId, String taskTitle,
                String taskDescription,
                LocalDate deadline,
                String createdBy) {
        

        this.alertId = alertId;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.deadline = deadline;
        this.createdBy = createdBy;
    }
    public Task(int id,
                int alertId,
                String taskTitle,
                String taskDescription,
                LocalDate deadline,
                String createdBy) {

        this.id = id;
        this.alertId = alertId;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.deadline = deadline;
        this.createdBy = createdBy;
    }
    public Task(int id, String alertTitle, String taskTitle,
            String taskDescription, LocalDate deadline) {

    this.id = id;
    this.alertTitle = alertTitle;
    this.taskTitle = taskTitle;
    this.taskDescription = taskDescription;
    this.deadline = deadline;
}
    

    // Getters
    public String getAlertTitle() {
    return alertTitle;
}
    public int getAlertId() { return alertId; }
    public String getTaskTitle() { return taskTitle; }
    public String getTaskDescription() { return taskDescription; }
    public LocalDate getDeadline() { return deadline; }
    public String getCreatedBy() { return createdBy; }
    public int getId() {
    return id;
}
}