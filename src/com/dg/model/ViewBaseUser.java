/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.model;

/**
 *
 * @author samih
 */


public class ViewBaseUser {
    protected int id;
    protected String fullName;
    protected String birthDate;
    protected String gender;
    protected String nid;
    protected String phoneNumber;
    protected String email;
    protected String presentDivision;
    protected String presentDistrict;
    protected String permanentDivision;
    protected String permanentDistrict;
    protected String bloodGroup;
    protected String medicalTraining;
    protected String searchAndRescue;
    protected String swimming;
    protected String driving;
    protected String languageSkills;
    protected String technicalSkills;
    protected String physicalFitnessLevel;
    protected String previousDisasterExp;
    protected String username;
    protected String status;

    public ViewBaseUser(int id, String fullName, String birthDate, String gender, String nid,
                    String phoneNumber, String email, String presentDivision, String presentDistrict,
                    String permanentDivision, String permanentDistrict, String bloodGroup,
                    String medicalTraining, String searchAndRescue, String swimming, String driving,
                    String languageSkills, String technicalSkills, String physicalFitnessLevel,
                    String previousDisasterExp, String username, String status) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.nid = nid;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.presentDivision = presentDivision;
        this.presentDistrict = presentDistrict;
        this.permanentDivision = permanentDivision;
        this.permanentDistrict = permanentDistrict;
        this.bloodGroup = bloodGroup;
        this.medicalTraining = medicalTraining;
        this.searchAndRescue = searchAndRescue;
        this.swimming = swimming;
        this.driving = driving;
        this.languageSkills = languageSkills;
        this.technicalSkills = technicalSkills;
        this.physicalFitnessLevel = physicalFitnessLevel;
        this.previousDisasterExp = previousDisasterExp;
        this.username = username;
        this.status = status;
    }

    // Getters
    public int getId() { return id; }
    public String getFullName() { return fullName; }
    public String getBirthDate() { return birthDate; }
    public String getGender() { return gender; }
    public String getNid() { return nid; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public String getPresentDivision() { return presentDivision; }
    public String getPresentDistrict() { return presentDistrict; }
    public String getPermanentDivision() { return permanentDivision; }
    public String getPermanentDistrict() { return permanentDistrict; }
    public String getBloodGroup() { return bloodGroup; }
    public String getMedicalTraining() { return medicalTraining; }
    public String getSearchAndRescue() { return searchAndRescue; }
    public String getSwimming() { return swimming; }
    public String getDriving() { return driving; }
    public String getLanguageSkills() { return languageSkills; }
    public String getTechnicalSkills() { return technicalSkills; }
    public String getPhysicalFitnessLevel() { return physicalFitnessLevel; }
    public String getPreviousDisasterExp() { return previousDisasterExp; }
    public String getUsername() { return username; }
    public String getStatus() { return status; }
}
