/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.dbconnection;

/**
 *
 * @author samih
 */


import java.sql.*;
import javax.swing.JOptionPane;

public class AdminDTO {

    public static void insertAdmin(AdminData a) {
        String sql = "INSERT INTO admin_info (" +
                "full_name, birth_date, birth_month, birth_year, gender, nid, " +
                "phone_number, email,alternative_phone_number, emergency_contact_name, " +
                "emergency_contact_relationship, emergency_contact_number,blood_group, " +
                "present_division, present_district, permanent_division, permanent_district, " +
                "building_house_no, road_street_no, area_locality, postal_code, " +
                "office_building_name, office_phone_number, " +
                "higher_education, major_subject_field, university_institution, official_designation, organization_department, organization_type, employee_official_id_number, date_of_joining_day, date_of_joining_month, date_of_joining_year, " +
                "medical_training, search_and_rescue, swimming, driving, language_skills, technical_skills, " +
                "physical_fitness_level, asthma, allergy, back_problems, none_problems, lift_heavy_objects, " +
                "emergency_response_training, disaster_risk_reduction_certification, first_aid_cpr_certified, crisis_management_training, volunteer_coordination_training, gis_mapping_disaster_management, community_disaster_preparedness, no_formal_training, " +
                "previous_disaster_experience, floods, cyclones, storms, earthquakes, fires, landslides, river_erosion, none_disaster, significant_disaster_description, " +
                "username, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 

        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int i = 1;

            // Common personal info
            ps.setString(i++, a.getFullName());
            ps.setInt(i++, a.getBirthDate());
            ps.setString(i++, a.getBirthMonth());
            ps.setInt(i++, a.getBirthYear());
            ps.setString(i++, a.getGender());
            ps.setString(i++, a.getNid());

            // Contact info
            ps.setString(i++, a.getPhoneNumber());
            ps.setString(i++, a.getEmail());
            ps.setString(i++, a.getAlternativePhoneNumber());
           
            ps.setString(i++, a.getEmergencyContactName());
            ps.setString(i++, a.getEmergencyContactRelationship());
            ps.setString(i++, a.getEmergencyContactNumber());
             ps.setString(i++, a.getBloodGroup());

            // Address info
            ps.setString(i++, a.getPresentDivision());
            ps.setString(i++, a.getPresentDistrict());
            ps.setString(i++, a.getPermanentDivision());
            ps.setString(i++, a.getPermanentDistrict());

            // Non-optional fields
            ps.setString(i++, a.getBuildingHouseNo());
            ps.setString(i++, a.getRoadStreetNo());
            ps.setString(i++, a.getAreaLocality());
            ps.setString(i++, a.getPostalCode());

            // Office info
            ps.setString(i++, a.getOfficeBuildingName());
            ps.setString(i++, a.getOfficePhoneNumber());

            // Education & Profession
            ps.setString(i++, a.getHigherEducation());
            ps.setString(i++, a.getMajorSubjectField());
            ps.setString(i++, a.getUniversityInstitution());
            ps.setString(i++, a.getOfficialDesignation());
            ps.setString(i++, a.getOrganizationDepartment());
            ps.setString(i++, a.getOrganizationType());
            ps.setString(i++, a.getEmployeeOfficialIdNumber());
            ps.setInt(i++, a.getDateOfJoiningDay());
            ps.setString(i++, a.getDateOfJoiningMonth());
            ps.setInt(i++, a.getDateOfJoiningYear());

            // Optional Skills
            ps.setString(i++, a.getMedicalTraining());
            ps.setString(i++, a.getSearchAndRescue());
            ps.setString(i++, a.getSwimming());
            ps.setString(i++, a.getDriving());
            ps.setString(i++, a.getLanguageSkills());
            ps.setString(i++, a.getTechnicalSkills());

            // Physical Fitness & Health
            ps.setString(i++, a.getPhysicalFitnessLevel());
            ps.setString(i++, a.getAsthma());
            ps.setString(i++, a.getAllergy());
            ps.setString(i++, a.getBackProblems());
            ps.setString(i++, a.getNoneProblems());
            ps.setString(i++, a.getLiftHeavyObjects());

            // Disaster Training / Certification
            ps.setString(i++, a.getEmergencyResponseTraining());
            ps.setString(i++, a.getDisasterRiskReductionCertification());
            ps.setString(i++, a.getFirstAidCprCertified());
            ps.setString(i++, a.getCrisisManagementTraining());
            ps.setString(i++, a.getVolunteerCoordinationTraining());
            ps.setString(i++, a.getGisMappingDisasterManagement());
            ps.setString(i++, a.getCommunityDisasterPreparedness());
            ps.setString(i++, a.getNoFormalTraining());

            // Previous Disaster Experience
            ps.setString(i++, a.getPreviousDisasterExperience());
            ps.setString(i++, a.getFloods());
            ps.setString(i++, a.getCyclones());
            ps.setString(i++, a.getStorms());
            ps.setString(i++, a.getEarthquakes());
            ps.setString(i++, a.getFires());
            ps.setString(i++, a.getLandslides());
            ps.setString(i++, a.getRiverErosion());
            ps.setString(i++, a.getNoneDisaster());
            ps.setString(i++, a.getSignificantDisasterDescription());

            // System Info
            ps.setString(i++, a.getUsername());
            ps.setString(i++, a.getStatus());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Admin information inserted successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
