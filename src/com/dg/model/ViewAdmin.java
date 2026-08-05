/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.model;

/**
 *
 * @author samih
 */


public class ViewAdmin extends ViewBaseUser {
    private String office;
    private String higherEducation;
    private String officialDesignation;
    private String organizationType;

    public ViewAdmin(int id, String fullName, String birthDate, String gender, String nid,
                 String phoneNumber, String email, String presentDivision, String presentDistrict,
                 String permanentDivision, String permanentDistrict, String office, String higherEducation, String officialDesignation, String organizationType, String bloodGroup,
                 String medicalTraining, String searchAndRescue, String swimming, String driving,
                 String languageSkills, String technicalSkills, String physicalFitnessLevel,
                 String previousDisasterExp, String username, String status
                ) {
        super(id, fullName, birthDate, gender, nid, phoneNumber, email, presentDivision, presentDistrict,
              permanentDivision, permanentDistrict, bloodGroup, medicalTraining, searchAndRescue,
              swimming, driving, languageSkills, technicalSkills, physicalFitnessLevel,
              previousDisasterExp, username, status);
        this.office = office;
        this.higherEducation = higherEducation;
        this.officialDesignation = officialDesignation;
        this.organizationType = organizationType;
    }

    public String getOffice() { return office; }
    public String getHigherEducation() { return higherEducation; }
    public String getOfficialDesignation() { return officialDesignation; }
    public String getOrganizationType() { return organizationType; }
}