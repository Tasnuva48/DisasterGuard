/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.model;

//import java.awt.List;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class Volunteer {
    
   
// ===== RAW SKILL DATA FROM DATABASE =====
private String swimming;
private String driving;
private String searchAndRescue;
private String medicalTraining;
private String languageSkills;
private String technicalSkills;

    // getters setters
    private String username;
    private String fullName;

    private int birthDate;
    private String birthMonth;
    private int birthYear;

    private String gender;
    private String nid;
    private String bloodGroup;

    // ================= CONTACT =================
    private String phoneNumber;
    private String email;
    private String emergencyContact;

    // ================= ADDRESS =================
    private String presentDivision;
    private String presentDistrict;
    private String permanentDivision;
    private String permanentDistrict;

    // ================= EDUCATION & WORK =================
    private String universityName;
    private String profession;

    // ================= TRAININGS / DISASTERS / SKILLS =================
   //private List<String> trainings;
   private List<String> disastersHandled;
  //private List<String> skills;
   //String skills;
//String disastersHandled;
    // =========================================================
    // ================= GETTERS AND SETTERS ===================
    // =========================================================

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(int birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getPresentDivision() {
        return presentDivision;
    }

    public void setPresentDivision(String presentDivision) {
        this.presentDivision = presentDivision;
    }

    public String getPresentDistrict() {
        return presentDistrict;
    }

    public void setPresentDistrict(String presentDistrict) {
        this.presentDistrict = presentDistrict;
    }

    public String getPermanentDivision() {
        return permanentDivision;
    }

    public void setPermanentDivision(String permanentDivision) {
        this.permanentDivision = permanentDivision;
    }

    public String getPermanentDistrict() {
        return permanentDistrict;
    }

    public void setPermanentDistrict(String permanentDistrict) {
        this.permanentDistrict = permanentDistrict;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

//    public List<String> getTrainings() {
//      // return trainings != null ? trainings : new ArrayList<>();
//      return trainings;
//   }

//   public void setTrainings(List<String> trainings) {
//       this.trainings = trainings;
//}

    public List<String> getDisastersHandled() {
        return disastersHandled;
   }

  public void setDisastersHandled(List<String> disastersHandled) {
       this.disastersHandled = disastersHandled;
   }
//    public List<String> getSkills() {
//        return skills;
//    }
//    }

//   public void setSkills(List<String> skills) {
//        this.skills = skills;
//    }
   public String getSwimming() { return swimming; }
public void setSwimming(String swimming) { this.swimming = swimming; }

public String getDriving() { return driving; }
public void setDriving(String driving) { this.driving = driving; }

public String getSearchAndRescue() { return searchAndRescue; }
public void setSearchAndRescue(String searchAndRescue) { this.searchAndRescue = searchAndRescue; }

public String getMedicalTraining() { return medicalTraining; }
public void setMedicalTraining(String medicalTraining) { this.medicalTraining = medicalTraining; }

public String getLanguageSkills() { return languageSkills; }
public void setLanguageSkills(String languageSkills) { this.languageSkills = languageSkills; }

public String getTechnicalSkills() { return technicalSkills; }
public void setTechnicalSkills(String technicalSkills) { this.technicalSkills = technicalSkills; }


    // =========================================================
    // ================= HELPER METHODS ========================
    // =========================================================

    public String getBirthDateFormatted() {
        return birthDate + " " + birthMonth + " " + birthYear;
    }

//    public String getPresentAddressFormatted() {
//        return presentDistrict + ", " + presentDivision;
//    }
//
//    public String getPermanentAddressFormatted() {
//        return permanentDistrict + ", " + permanentDivision;
//    }
    
    public String getPresentAddressFormatted() {
    return formatAddress(presentDistrict, presentDivision);
}

public String getPermanentAddressFormatted() {
    return formatAddress(permanentDistrict, permanentDivision);
}
     private String formatAddress(String district, String division) {
        if (district == null) district = "";
        if (division == null) division = "";
        
        district = district.trim();
        division = division.trim();
        
        if (district.equalsIgnoreCase(division)) {
            return district + ", Bangladesh";
        } else {
            return district + ", " + division + ", Bangladesh";
        }
    }
    
/*public List<String> getSkills() {
    List<String> skills = new ArrayList<>();
    // Yes/No skills
    if ("Yes".equalsIgnoreCase(swimming)) skills.add("Swimming");
    if ("Yes".equalsIgnoreCase(driving)) skills.add("Driving");
    if ("Yes".equalsIgnoreCase(searchAndRescue)) skills.add("Search & Rescue");
    
    // Text skills with prefixes
//   if (medicalTraining != null && !medicalTraining.trim().isEmpty() && !"No".equalsIgnoreCase(medicalTraining)) {
//       for (String s : medicalTraining.split(",")) {
//           skills.add("Medical: " + s.trim());
//       }
//    }
if ("Yes".equalsIgnoreCase(medicalTraining)) skills.add("Medical Training");
   if (languageSkills != null && !languageSkills.trim().isEmpty() && !"No".equalsIgnoreCase(languageSkills)) {
    StringBuilder sb = new StringBuilder("Language: ");
    String[] langs = languageSkills.split(",");
    for (int i = 0; i < langs.length; i++) {
        sb.append(langs[i].trim());
        if (i < langs.length - 1) sb.append(", "); // add comma between, not after last
    }
    skills.add(sb.toString()); // ✅ adds ONE entry: "Language: Bangla, English, Arabic"
}
    if (technicalSkills != null && !technicalSkills.trim().isEmpty() && !"No".equalsIgnoreCase(technicalSkills)) {
        for (String s : technicalSkills.split(",")) {
            skills.add("Technical: " + s.trim());
        }
    }
    return skills;
}
   */ 
}
