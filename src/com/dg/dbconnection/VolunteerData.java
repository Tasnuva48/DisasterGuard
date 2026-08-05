/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.dbconnection;

import java.util.Random;

/**
 *
 * @author samih
 */


public class VolunteerData extends Data {
    // Education & Profession
    private String schoolName;
    private String collegeName;
    private String universityName;
    private String profession;

    // Optional Skills
    private String medicalTraining;
    private String searchAndRescue;
    private String swimming;
    private String driving;
    private String languageSkills;
    private String technicalSkills;
    private String cooking;
    private String counselling;
    private String teaching;
    private String constructionWork;

    // Physical Fitness
    private String physicalFitnessLevel;

    // Health Issues
    private String asthma;
    private String allergy;
    private String backProblems;
    private String noneProblems;

    // Abilities
    private String liftHeavyObjects;
    private String workDifficultTerrain;

    // Previous Disaster Experience
    private String previousDisasterExperience;
    private String disastersWorkedOn;
    private String rolesPerformed;
    private String organizationsWorkedWith;

    // Getters and Setters for all above
    public String getSchoolName() { return schoolName; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }

    public String getCollegeName() { return collegeName; }
    public void setCollegeName(String collegeName) { this.collegeName = collegeName; }

    public String getUniversityName() { return universityName; }
    public void setUniversityName(String universityName) { this.universityName = universityName; }

    public String getProfession() { return profession; }
    public void setProfession(String profession) { this.profession = profession; }

    public String getMedicalTraining() { return medicalTraining; }
    public void setMedicalTraining(String medicalTraining) { this.medicalTraining = medicalTraining; }

    public String getSearchAndRescue() { return searchAndRescue; }
    public void setSearchAndRescue(String searchAndRescue) { this.searchAndRescue = searchAndRescue; }

    public String getSwimming() { return swimming; }
    public void setSwimming(String swimming) { this.swimming = swimming; }

    public String getDriving() { return driving; }
    public void setDriving(String driving) { this.driving = driving; }

    public String getLanguageSkills() { return languageSkills; }
    public void setLanguageSkills(String languageSkills) { this.languageSkills = languageSkills; }

    public String getTechnicalSkills() { return technicalSkills; }
    public void setTechnicalSkills(String technicalSkills) { this.technicalSkills = technicalSkills; }

    public String getCooking() { return cooking; }
    public void setCooking(String cooking) { this.cooking = cooking; }

    public String getCounselling() { return counselling; }
    public void setCounselling(String counselling) { this.counselling = counselling; }

    public String getTeaching() { return teaching; }
    public void setTeaching(String teaching) { this.teaching = teaching; }

    public String getConstructionWork() { return constructionWork; }
    public void setConstructionWork(String constructionWork) { this.constructionWork = constructionWork; }

    public String getPhysicalFitnessLevel() { return physicalFitnessLevel; }
    public void setPhysicalFitnessLevel(String physicalFitnessLevel) { this.physicalFitnessLevel = physicalFitnessLevel; }

    public String getAsthma() { return asthma; }
    public void setAsthma(String asthma) { this.asthma = asthma; }

    public String getAllergy() { return allergy; }
    public void setAllergy(String allergy) { this.allergy = allergy; }

    public String getBackProblems() { return backProblems; }
    public void setBackProblems(String backProblems) { this.backProblems = backProblems; }

    public String getNoneProblems() { return noneProblems; }
    public void setNoneProblems(String noneProblems) { this.noneProblems = noneProblems; }

    public String getLiftHeavyObjects() { return liftHeavyObjects; }
    public void setLiftHeavyObjects(String liftHeavyObjects) { this.liftHeavyObjects = liftHeavyObjects; }

    public String getWorkDifficultTerrain() { return workDifficultTerrain; }
    public void setWorkDifficultTerrain(String workDifficultTerrain) { this.workDifficultTerrain = workDifficultTerrain; }

    public String getPreviousDisasterExperience() { return previousDisasterExperience; }
    public void setPreviousDisasterExperience(String previousDisasterExperience) { this.previousDisasterExperience = previousDisasterExperience; }

    public String getDisastersWorkedOn() { return disastersWorkedOn; }
    public void setDisastersWorkedOn(String disastersWorkedOn) { this.disastersWorkedOn = disastersWorkedOn; }

    public String getRolesPerformed() { return rolesPerformed; }
    public void setRolesPerformed(String rolesPerformed) { this.rolesPerformed = rolesPerformed; }

    public String getOrganizationsWorkedWith() { return organizationsWorkedWith; }
    public void setOrganizationsWorkedWith(String organizationsWorkedWith) { this.organizationsWorkedWith = organizationsWorkedWith; }
    @Override
    public  String generateUsername() {
        Random random = new Random();

        // 4 random uppercase letters
        String letters = "";
        for (int i = 0; i < 4; i++) {
            letters += (char) ('A' + random.nextInt(26));
        }

        // 4 random digits
        String digits = "";
        for (int i = 0; i < 4; i++) {
            digits += random.nextInt(10);
        }

        return "VOL" + letters + digits;
    }
}
