/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.dao;
import com.dg.dbconnection.SQLiteConnect;
import com.dg.model.Volunteer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.dg.volunteerdashboard.VolunteerEditProfile;

/**
 *
 * @author USER
 */
public class VolunteerDAO {
   /* public Volunteer getVolunteerByUsername(String username) {

        Volunteer v = null;

        String sql = "SELECT * FROM volunteer_info WHERE username=?";

        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                v = new Volunteer();

                v.setUsername(rs.getString("username"));
                v.setFullName(rs.getString("full_name"));
                v.setGender(rs.getString("gender"));
                v.setPhone(rs.getString("phone_number"));
                v.setEmail(rs.getString("email"));
                v.setProfession(rs.getString("profession"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }*/
    public Volunteer getVolunteerByUsername(String username) {
        Volunteer v = null;
        String sql = "SELECT * FROM volunteer_info WHERE username=?";
        
        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                v = new Volunteer();
                
                // ================= BASIC INFO =================
                v.setUsername(rs.getString("username"));
                v.setFullName(rs.getString("full_name"));
                v.setGender(rs.getString("gender"));
                v.setNid(rs.getString("nid"));
                v.setBloodGroup(rs.getString("blood_group"));
                
                // ================= BIRTH DATE =================
                v.setBirthDate(rs.getInt("birth_date"));
                v.setBirthMonth(rs.getString("birth_month"));
                v.setBirthYear(rs.getInt("birth_year"));
                
                // ================= CONTACT INFO =================
                v.setPhoneNumber(rs.getString("phone_number"));
                v.setEmail(rs.getString("email"));
                v.setEmergencyContact(rs.getString("emergency_contact_number"));
                
                // ================= ADDRESS =================
                v.setPresentDivision(rs.getString("present_division"));
                v.setPresentDistrict(rs.getString("present_district"));
                v.setPermanentDivision(rs.getString("permanent_division"));
                v.setPermanentDistrict(rs.getString("permanent_district"));
                
                // ================= EDUCATION & WORK =================
                v.setUniversityName(rs.getString("university_name"));
                v.setProfession(rs.getString("profession"));
               // v.setTrainings(rs.getString("disasters_worked_on"));
                // ================= TRAININGS =================
//                v.setEmergencyResponseTraining(rs.getString("emergency_response_training"));
//                v.setDisasterRiskReductionCertification(rs.getString("disaster_risk_reduction_certification"));
//                v.setFirstAidCprCertified(rs.getString("first_aid_cpr_certified"));
//                v.setCrisisManagementTraining(rs.getString("crisis_management_training"));
//                v.setVolunteerCoordinationTraining(rs.getString("volunteer_coordination_training"));
//                v.setGisMappingDisasterManagement(rs.getString("gis_mapping_disaster_management"));
//                v.setCommunityDisasterPreparedness(rs.getString("community_disaster_preparedness"));
//                
//                // ================= DISASTERS HANDLED =================
//                v.setFloods(rs.getString("floods"));
//                v.setCyclones(rs.getString("cyclones"));
//                v.setStorms(rs.getString("storms"));
//                v.setEarthquakes(rs.getString("earthquakes"));
//                v.setFires(rs.getString("fires"));
//                v.setLandslides(rs.getString("landslides"));
//                v.setRiverErosion(rs.getString("river_erosion"));
//                
//                // ================= SKILLS =================
              v.setSwimming(rs.getString("swimming"));
                v.setDriving(rs.getString("driving"));
                v.setSearchAndRescue(rs.getString("search_and_rescue"));
                v.setMedicalTraining(rs.getString("medical_training"));
            
               
               
               v.setLanguageSkills(rs.getString("language_skills"));
                v.setTechnicalSkills(rs.getString("technical_skills"));
               // v.setDisastersHandled("disasters_worked_on");
               
               String disastersText = rs.getString("disasters_worked_on");

               
               
               
               
List<String> disastersList = new ArrayList<>();
if (disastersText != null && !disastersText.trim().isEmpty()) {
    for (String d : disastersText.split(",")) {
        disastersList.add(d.trim());
    }
}

v.setDisastersHandled(disastersList);

               
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error fetching volunteer: " + e.getMessage());
        }
        
        return v;
    }
    
    /**
     * Update volunteer profile
     */
    public boolean updateVolunteer(Volunteer v,String oldUsername) {
        String sql = "UPDATE volunteer_info SET " +
                "full_name=?, birth_date=?, birth_month=?, birth_year=?, " +
                "gender=?, nid=?, blood_group=?, " +
                "disasters_worked_on=?, " +
                "phone_number=?, email=?, emergency_contact_number=?, " +
                "present_division=?, present_district=?, " +
                "permanent_division=?, permanent_district=?, " +
                "university_name=?, profession=?, " +
                ////"emergency_response_training=?, disaster_risk_reduction_certification=?, " +
               // "first_aid_cpr_certified=?, crisis_management_training=?, " +
               // "volunteer_coordination_training=?, gis_mapping_disaster_management=?, " +
               // "community_disaster_preparedness=?, " +
                //"floods=?, cyclones=?, storms=?, earthquakes=?, fires=?, landslides=?, river_erosion=?, " +
                "swimming=?, driving=?, search_and_rescue=?, " +
                "medical_training=?, language_skills=?, technical_skills=?," +
                "username=? " + 
                "WHERE username=?";
        
        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            // Personal Info
            pst.setString(1, v.getFullName());
            pst.setInt(2, v.getBirthDate());
            pst.setString(3, v.getBirthMonth());
            pst.setInt(4, v.getBirthYear());
            pst.setString(5, v.getGender());
            pst.setString(6, v.getNid());
            pst.setString(7, v.getBloodGroup());
            
            // Contact
             String disastersText = "";
        if (v.getDisastersHandled() != null && !v.getDisastersHandled().isEmpty()) {
            disastersText = String.join(",", v.getDisastersHandled());
        }
         pst.setString(8, disastersText);
            pst.setString(9, v.getPhoneNumber());
            pst.setString(10, v.getEmail());
            pst.setString(11, v.getEmergencyContact());
            
            // Address
            pst.setString(12, v.getPresentDivision());
            pst.setString(13, v.getPresentDistrict());
            pst.setString(14, v.getPermanentDivision());
            pst.setString(15, v.getPermanentDistrict());
            
            // Education & Work
            pst.setString(16, v.getUniversityName());
            pst.setString(17, v.getProfession());
            
            // Trainings
//            pst.setString(17, v.getEmergencyResponseTraining());
//            pst.setString(18, v.getDisasterRiskReductionCertification());
//            pst.setString(19, v.getFirstAidCprCertified());
//            pst.setString(20, v.getCrisisManagementTraining());
//            pst.setString(21, v.getVolunteerCoordinationTraining());
//            pst.setString(22, v.getGisMappingDisasterManagement());
//            pst.setString(23, v.getCommunityDisasterPreparedness());
//            
//            // Disasters
//            pst.setString(24, v.getFloods());
//            pst.setString(25, v.getCyclones());
//            pst.setString(26, v.getStorms());
//            pst.setString(27, v.getEarthquakes());
//            pst.setString(28, v.getFires());
//            pst.setString(29, v.getLandslides());
//            pst.setString(30, v.getRiverErosion());
//            
//            // Skills
           pst.setString(18, v.getSwimming());
          pst.setString(19, v.getDriving());
           pst.setString(20, v.getSearchAndRescue());
           pst.setString(21, v.getMedicalTraining());
            pst.setString(22, v.getLanguageSkills());
            pst.setString(23, v.getTechnicalSkills());
            
            // WHERE clause
            pst.setString(24, v.getUsername());
            pst.setString(25, oldUsername); 
            //String disastersText = String.join(",", v.getDisastersHandled());
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating volunteer: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Check if username exists
     */
    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM volunteer_info WHERE username=?";
        
        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Insert new volunteer (for registration)
     */
    public boolean insertVolunteer(Volunteer v) {
        String sql = "INSERT INTO volunteer_info (" +
                "username, full_name, birth_date, birth_month, birth_year, " +
                "gender, nid, blood_group, " +
                "phone_number, email, emergency_contact_number, " +
                "present_division, present_district, " +
                "permanent_division, permanent_district, " +
                "university_name, profession, " +
               // "emergency_response_training, disaster_risk_reduction_certification, " +
                //"first_aid_cpr_certified, crisis_management_training, " +
               // "volunteer_coordination_training, gis_mapping_disaster_management, " +
               // "community_disaster_preparedness, " +
               // "floods, cyclones, storms, earthquakes, fires, landslides, river_erosion, " +
               // "swimming, driving, search_and_rescue, " +
               /// "medical_training, language_skills, technical_skills" +
                ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, v.getUsername());
            pst.setString(2, v.getFullName());
            pst.setInt(3, v.getBirthDate());
            pst.setString(4, v.getBirthMonth());
            pst.setInt(5, v.getBirthYear());
            pst.setString(6, v.getGender());
            pst.setString(7, v.getNid());
            pst.setString(8, v.getBloodGroup());
            pst.setString(9, v.getPhoneNumber());
            pst.setString(10, v.getEmail());
            pst.setString(11, v.getEmergencyContact());
            pst.setString(12, v.getPresentDivision());
            pst.setString(13, v.getPresentDistrict());
            pst.setString(14, v.getPermanentDivision());
            pst.setString(15, v.getPermanentDistrict());
            pst.setString(16, v.getUniversityName());
            pst.setString(17, v.getProfession());
          
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error inserting volunteer: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete volunteer by username
     */
    public boolean deleteVolunteer(String username) {
        String sql = "DELETE FROM volunteer_info WHERE username=?";
        
        try (Connection conn = SQLiteConnect.Connectordb();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, username);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error deleting volunteer: " + e.getMessage());
            return false;
        }
    }
    public boolean validateLogin(String username, String password) {

    String sql = "SELECT * FROM users WHERE username=? AND password=?";

    try (Connection conn = SQLiteConnect.Connectordb();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, username);
        pst.setString(2, password);

        ResultSet rs = pst.executeQuery();

        return rs.next();

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}
