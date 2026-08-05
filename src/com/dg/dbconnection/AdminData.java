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


public class AdminData extends Data {
    // Optional contact
    private String alternativePhoneNumber;
    private String emergencyContactName;
    private String emergencyContactRelationship;

    // Address details not in Data
    private String buildingHouseNo;
    private String roadStreetNo;
    private String areaLocality;
    private String postalCode;

    // Office Info
    private String officeBuildingName;
    private String officePhoneNumber;

    // Education & Profession
    private String higherEducation;
    private String majorSubjectField;
    private String universityInstitution;
    private String officialDesignation;
    private String organizationDepartment;
    private String organizationType;
    private String employeeOfficialIdNumber;
    private int dateOfJoiningDay;
    private String dateOfJoiningMonth;
    private int dateOfJoiningYear;

    // Optional Skills
    private String medicalTraining;
    private String searchAndRescue;
    private String swimming;
    private String driving;
    private String languageSkills;
    private String technicalSkills;

    // Physical Fitness & Health
    private String physicalFitnessLevel;
    private String asthma;
    private String allergy;
    private String backProblems;
    private String noneProblems;
    private String liftHeavyObjects;

    // Disaster Training / Certification
    private String emergencyResponseTraining;
    private String disasterRiskReductionCertification;
    private String firstAidCprCertified;
    private String crisisManagementTraining;
    private String volunteerCoordinationTraining;
    private String gisMappingDisasterManagement;
    private String communityDisasterPreparedness;
    private String noFormalTraining;

    // Previous Disaster Experience
    private String previousDisasterExperience;
    private String floods;
    private String cyclones;
    private String storms;
    private String earthquakes;
    private String fires;
    private String landslides;
    private String riverErosion;
    private String noneDisaster;
    private String significantDisasterDescription;

    // Getters & Setters for all above
    // Example for a few fields:
    public String getAlternativePhoneNumber() { return alternativePhoneNumber; }
    public void setAlternativePhoneNumber(String alternativePhoneNumber) { this.alternativePhoneNumber = alternativePhoneNumber; }

    public String getBuildingHouseNo() { return buildingHouseNo; }
    public void setBuildingHouseNo(String buildingHouseNo) { this.buildingHouseNo = buildingHouseNo; }

    public String getMedicalTraining() { return medicalTraining; }
    public void setMedicalTraining(String medicalTraining) { this.medicalTraining = medicalTraining; }

    public String getPreviousDisasterExperience() { return previousDisasterExperience; }
    public void setPreviousDisasterExperience(String previousDisasterExperience) { this.previousDisasterExperience = previousDisasterExperience; }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactRelationship() {
        return emergencyContactRelationship;
    }

    public void setEmergencyContactRelationship(String emergencyContactRelationship) {
        this.emergencyContactRelationship = emergencyContactRelationship;
    }

    public String getRoadStreetNo() {
        return roadStreetNo;
    }

    public void setRoadStreetNo(String roadStreetNo) {
        this.roadStreetNo = roadStreetNo;
    }

    public String getAreaLocality() {
        return areaLocality;
    }

    public void setAreaLocality(String areaLocality) {
        this.areaLocality = areaLocality;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getOfficeBuildingName() {
        return officeBuildingName;
    }

    public void setOfficeBuildingName(String officeBuildingName) {
        this.officeBuildingName = officeBuildingName;
    }

    public String getOfficePhoneNumber() {
        return officePhoneNumber;
    }

    public void setOfficePhoneNumber(String officePhoneNumber) {
        this.officePhoneNumber = officePhoneNumber;
    }

    public String getHigherEducation() {
        return higherEducation;
    }

    public void setHigherEducation(String higherEducation) {
        this.higherEducation = higherEducation;
    }

    public String getMajorSubjectField() {
        return majorSubjectField;
    }

    public void setMajorSubjectField(String majorSubjectField) {
        this.majorSubjectField = majorSubjectField;
    }

    public String getUniversityInstitution() {
        return universityInstitution;
    }

    public void setUniversityInstitution(String universityInstitution) {
        this.universityInstitution = universityInstitution;
    }

    public String getOfficialDesignation() {
        return officialDesignation;
    }

    public void setOfficialDesignation(String officialDesignation) {
        this.officialDesignation = officialDesignation;
    }

    public String getOrganizationDepartment() {
        return organizationDepartment;
    }

    public void setOrganizationDepartment(String organizationDepartment) {
        this.organizationDepartment = organizationDepartment;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public String getEmployeeOfficialIdNumber() {
        return employeeOfficialIdNumber;
    }

    public void setEmployeeOfficialIdNumber(String employeeOfficialIdNumber) {
        this.employeeOfficialIdNumber = employeeOfficialIdNumber;
    }

    public int getDateOfJoiningDay() {
        return dateOfJoiningDay;
    }

    public void setDateOfJoiningDay(int dateOfJoiningDay) {
        this.dateOfJoiningDay = dateOfJoiningDay;
    }

    public String getDateOfJoiningMonth() {
        return dateOfJoiningMonth;
    }

    public void setDateOfJoiningMonth(String dateOfJoiningMonth) {
        this.dateOfJoiningMonth = dateOfJoiningMonth;
    }

    public int getDateOfJoiningYear() {
        return dateOfJoiningYear;
    }

    public void setDateOfJoiningYear(int dateOfJoiningYear) {
        this.dateOfJoiningYear = dateOfJoiningYear;
    }

    public String getSearchAndRescue() {
        return searchAndRescue;
    }

    public void setSearchAndRescue(String searchAndRescue) {
        this.searchAndRescue = searchAndRescue;
    }

    public String getSwimming() {
        return swimming;
    }

    public void setSwimming(String swimming) {
        this.swimming = swimming;
    }

    public String getDriving() {
        return driving;
    }

    public void setDriving(String driving) {
        this.driving = driving;
    }

    public String getLanguageSkills() {
        return languageSkills;
    }

    public void setLanguageSkills(String languageSkills) {
        this.languageSkills = languageSkills;
    }

    public String getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(String technicalSkills) {
        this.technicalSkills = technicalSkills;
    }

    public String getPhysicalFitnessLevel() {
        return physicalFitnessLevel;
    }

    public void setPhysicalFitnessLevel(String physicalFitnessLevel) {
        this.physicalFitnessLevel = physicalFitnessLevel;
    }

    public String getAsthma() {
        return asthma;
    }

    public void setAsthma(String asthma) {
        this.asthma = asthma;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getBackProblems() {
        return backProblems;
    }

    public void setBackProblems(String backProblems) {
        this.backProblems = backProblems;
    }

    public String getNoneProblems() {
        return noneProblems;
    }

    public void setNoneProblems(String noneProblems) {
        this.noneProblems = noneProblems;
    }

    public String getLiftHeavyObjects() {
        return liftHeavyObjects;
    }

    public void setLiftHeavyObjects(String liftHeavyObjects) {
        this.liftHeavyObjects = liftHeavyObjects;
    }

    public String getEmergencyResponseTraining() {
        return emergencyResponseTraining;
    }

    public void setEmergencyResponseTraining(String emergencyResponseTraining) {
        this.emergencyResponseTraining = emergencyResponseTraining;
    }

    public String getDisasterRiskReductionCertification() {
        return disasterRiskReductionCertification;
    }

    public void setDisasterRiskReductionCertification(String disasterRiskReductionCertification) {
        this.disasterRiskReductionCertification = disasterRiskReductionCertification;
    }

    public String getFirstAidCprCertified() {
        return firstAidCprCertified;
    }

    public void setFirstAidCprCertified(String firstAidCprCertified) {
        this.firstAidCprCertified = firstAidCprCertified;
    }

    public String getCrisisManagementTraining() {
        return crisisManagementTraining;
    }

    public void setCrisisManagementTraining(String crisisManagementTraining) {
        this.crisisManagementTraining = crisisManagementTraining;
    }

    public String getVolunteerCoordinationTraining() {
        return volunteerCoordinationTraining;
    }

    public void setVolunteerCoordinationTraining(String volunteerCoordinationTraining) {
        this.volunteerCoordinationTraining = volunteerCoordinationTraining;
    }

    public String getGisMappingDisasterManagement() {
        return gisMappingDisasterManagement;
    }

    public void setGisMappingDisasterManagement(String gisMappingDisasterManagement) {
        this.gisMappingDisasterManagement = gisMappingDisasterManagement;
    }

    public String getCommunityDisasterPreparedness() {
        return communityDisasterPreparedness;
    }

    public void setCommunityDisasterPreparedness(String communityDisasterPreparedness) {
        this.communityDisasterPreparedness = communityDisasterPreparedness;
    }

    public String getNoFormalTraining() {
        return noFormalTraining;
    }

    public void setNoFormalTraining(String noFormalTraining) {
        this.noFormalTraining = noFormalTraining;
    }

    public String getFloods() {
        return floods;
    }

    public void setFloods(String floods) {
        this.floods = floods;
    }

    public String getCyclones() {
        return cyclones;
    }

    public void setCyclones(String cyclones) {
        this.cyclones = cyclones;
    }

    public String getStorms() {
        return storms;
    }

    public void setStorms(String storms) {
        this.storms = storms;
    }

    public String getEarthquakes() {
        return earthquakes;
    }

    public void setEarthquakes(String earthquakes) {
        this.earthquakes = earthquakes;
    }

    public String getFires() {
        return fires;
    }

    public void setFires(String fires) {
        this.fires = fires;
    }

    public String getLandslides() {
        return landslides;
    }

    public void setLandslides(String landslides) {
        this.landslides = landslides;
    }

    public String getRiverErosion() {
        return riverErosion;
    }

    public void setRiverErosion(String riverErosion) {
        this.riverErosion = riverErosion;
    }

    public String getNoneDisaster() {
        return noneDisaster;
    }

    public void setNoneDisaster(String noneDisaster) {
        this.noneDisaster = noneDisaster;
    }

    public String getSignificantDisasterDescription() {
        return significantDisasterDescription;
    }

    public void setSignificantDisasterDescription(String significantDisasterDescription) {
        this.significantDisasterDescription = significantDisasterDescription;
    }
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

        return "ADM" + letters + digits;
    }

    
}
