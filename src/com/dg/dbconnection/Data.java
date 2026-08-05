/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.dbconnection;
import java.util.Random;

public abstract class Data {
    // Common personal info
    private String fullName;
    private int birthDate;
    private String birthMonth;
    private int birthYear;
    private String gender;
    private String nid;

    // Common contact info
    private String phoneNumber;
    private String email;
    private String bloodGroup;
    private String emergencyContactNumber;

    // Common address info
    private String presentDivision;
    private String presentDistrict;
    private String permanentDivision;
    private String permanentDistrict;

    // System info
    private String username;
    private final String status="Pending";

    // Getters and Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; this.username=generateUsername();}

    public int getBirthDate() { return birthDate; }
    public void setBirthDate(int birthDate) { this.birthDate = birthDate; }

    public String getBirthMonth() { return birthMonth; }
    public void setBirthMonth(String birthMonth) { this.birthMonth = birthMonth; }

    public int getBirthYear() { return birthYear; }
    public void setBirthYear(int birthYear) { this.birthYear = birthYear; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getNid() { return nid; }
    public void setNid(String nid) { this.nid = nid; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getEmergencyContactNumber() { return emergencyContactNumber; }
    public void setEmergencyContactNumber(String emergencyContactNumber) { this.emergencyContactNumber = emergencyContactNumber; }

    public String getPresentDivision() { return presentDivision; }
    public void setPresentDivision(String presentDivision) { this.presentDivision = presentDivision; }

    public String getPresentDistrict() { return presentDistrict; }
    public void setPresentDistrict(String presentDistrict) { this.presentDistrict = presentDistrict; }

    public String getPermanentDivision() { return permanentDivision; }
    public void setPermanentDivision(String permanentDivision) { this.permanentDivision = permanentDivision; }

    public String getPermanentDistrict() { return permanentDistrict; }
    public void setPermanentDistrict(String permanentDistrict) { this.permanentDistrict = permanentDistrict; }

    public String getUsername() { return username; }
    public abstract  String generateUsername(); 

    public String getStatus() { return status; }

    
    
}
