package com.dg.model;

import java.util.Date;

public class TaskAssignment {

    private int id;
    private int taskId;
    private String volunteerUsername;
    private String assignedBy;
    private Date assignedAt;
    private String status;
    private Date completedAt;

    public TaskAssignment(int taskId,
                          String volunteerUsername,
                          String assignedBy,
                          String status) {

        this.taskId = taskId;
        this.volunteerUsername = volunteerUsername;
        this.assignedBy = assignedBy;
        this.status = status;
    }

    // Constructor for reading from DB
    public TaskAssignment(int id,
                          int taskId,
                          String volunteerUsername,
                          String assignedBy,
                          Date assignedAt,
                          String status,
                          Date completedAt) {

        this.id = id;
        this.taskId = taskId;
        this.volunteerUsername = volunteerUsername;
        this.assignedBy = assignedBy;
        this.assignedAt = assignedAt;
        this.status = status;
        this.completedAt = completedAt;
    }

    // Getters
    public int getId() { return id; }
    public int getTaskId() { return taskId; }
    public String getVolunteerUsername() { return volunteerUsername; }
    public String getAssignedBy() { return assignedBy; }
    public Date getAssignedAt() { return assignedAt; }
    public String getStatus() { return status; }
    public Date getCompletedAt() { return completedAt; }
}