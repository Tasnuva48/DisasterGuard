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

public class VolunteerDTO {
    public static void insertVolunteer(VolunteerData v) {
        String sql = "INSERT INTO volunteer_info (full_name, birth_date, birth_month, birth_year, gender, nid, " +
                "phone_number, email, blood_group, emergency_contact_number, " +
                "present_division, present_district, permanent_division, permanent_district, " +
                "school_name, college_name, university_name, profession, " +
                "medical_training, search_and_rescue, swimming, driving, language_skills, technical_skills, cooking, counselling, teaching, construction_work, " +
                "physical_fitness_level, asthma, allergy, back_problems, none_problems, lift_heavy_objects, work_difficult_terrain, " +
                "previous_disaster_experience, disasters_worked_on, roles_performed, organizations_worked_with, username, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";

        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getFullName());
            ps.setInt(2, v.getBirthDate());
            ps.setString(3, v.getBirthMonth());
            ps.setInt(4, v.getBirthYear());
            ps.setString(5, v.getGender());
            ps.setString(6, v.getNid());

            ps.setString(7, v.getPhoneNumber());
            ps.setString(8, v.getEmail());
            ps.setString(9, v.getBloodGroup());
            ps.setString(10, v.getEmergencyContactNumber());

            ps.setString(11, v.getPresentDivision());
            ps.setString(12, v.getPresentDistrict());
            ps.setString(13, v.getPermanentDivision());
            ps.setString(14, v.getPermanentDistrict());

            ps.setString(15, v.getSchoolName());
            ps.setString(16, v.getCollegeName());
            ps.setString(17, v.getUniversityName());
            ps.setString(18, v.getProfession());

            ps.setString(19, v.getMedicalTraining());
            ps.setString(20, v.getSearchAndRescue());
            ps.setString(21, v.getSwimming());
            ps.setString(22, v.getDriving());
            ps.setString(23, v.getLanguageSkills());
            ps.setString(24, v.getTechnicalSkills());
            ps.setString(25, v.getCooking());
            ps.setString(26, v.getCounselling());
            ps.setString(27, v.getTeaching());
            ps.setString(28, v.getConstructionWork());

            ps.setString(29, v.getPhysicalFitnessLevel());
            ps.setString(30, v.getAsthma());
            ps.setString(31, v.getAllergy());
            ps.setString(32, v.getBackProblems());
            ps.setString(33, v.getNoneProblems());

            ps.setString(34, v.getLiftHeavyObjects());
            ps.setString(35, v.getWorkDifficultTerrain());

            ps.setString(36, v.getPreviousDisasterExperience());
            ps.setString(37, v.getDisastersWorkedOn());
            ps.setString(38, v.getRolesPerformed());
            ps.setString(39, v.getOrganizationsWorkedWith());

            ps.setString(40, v.getUsername());
            ps.setString(41, v.getStatus());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Volunteer information inserted successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}

