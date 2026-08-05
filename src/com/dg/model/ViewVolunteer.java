/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.model;

/**
 *
 * @author samih
 */


public class ViewVolunteer extends ViewBaseUser {
    private String profession;
    private String rolesPerformed;

    public ViewVolunteer(int id, String fullName, String birthDate, String gender, String nid,
                     String phoneNumber, String email, String presentDivision, String presentDistrict,
                     String permanentDivision, String permanentDistrict, String bloodGroup,
                     String medicalTraining, String searchAndRescue, String swimming, String driving,
                     String languageSkills, String technicalSkills, String physicalFitnessLevel,
                     String previousDisasterExp,String profession, String rolesPerformed, String username, String status
                     ) {
        super(id, fullName, birthDate, gender, nid, phoneNumber, email, presentDivision, presentDistrict,
              permanentDivision, permanentDistrict, bloodGroup, medicalTraining, searchAndRescue,
              swimming, driving, languageSkills, technicalSkills, physicalFitnessLevel,
              previousDisasterExp, username, status);
        this.profession = profession;
        this.rolesPerformed = rolesPerformed;
    }

    public String getProfession() { return profession; }
    public String getRolesPerformed() { return rolesPerformed; }
}