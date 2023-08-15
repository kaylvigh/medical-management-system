package com.keyin.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import com.keyin.client.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;
public class DoctorPortalDao {
    private UserDao userDao;
    private HealthDataDao healthDataDao;

    public DoctorPortalDao() {
        userDao = new UserDao();
        healthDataDao = new HealthDataDao();
    }

    public static boolean createDoctor(Doctor doctor) {
        String hashedPassword = BCrypt.hashpw(doctor.getPassword(), BCrypt.gensalt());
        String query = "insert into users(first_name, last_name, email, password, is_doctor, medicalLicense_number, specialization) values('"+doctor.getFirstName()+"','"+doctor.getLastName()+"','"+doctor.getEmail()+"','"+hashedPassword+"',"+doctor.isDoctor()+",'"+doctor.getMedicalLicenseNumber()+"','"+doctor.getSpecialization()+"')";
        Statement stmt = null;
        try {
            stmt = DatabaseConnection.con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int x = 0;
        try {
            x = stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (x > 0);
    }

    public static boolean updateDoctor(Doctor doctor) {
        String query = "update "+doctor.getId()+" set firstName= '"+doctor.getFirstName()+"',lastName= '"+doctor.getLastName()+"' ,email= '"+doctor.getEmail()+"' ,password= '"+doctor.getPassword()+"' ,medicalLicenseNumber= '"+doctor.getMedicalLicenseNumber()+"' ,specialization= '"+doctor.getSpecialization()+"'";
        Statement stmt = null;
        try {
            stmt = DatabaseConnection.con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int x = 0;
        try {
            x = stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (x > 0);
    }
    public static boolean deleteDoctor(int id) {
        String query = "delete from users where id="+id+" ";
        Statement stmt = null;
        try {
            stmt = DatabaseConnection.con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int x = 0;
        try {
            x = stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (x > 0);
    }

    public static Doctor getDoctorById(int doctorId) {
        Doctor doctor = null;
        ResultSet rs = null;

        String query = "select * from users where id="+doctorId+" and is_doctor = true";

        Statement stmt = null;
        try {
            stmt = DatabaseConnection.con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            rs = stmt.executeQuery(query);
            if (!rs.next()){
                return doctor;
            }
            doctor = new Doctor(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("password"), rs.getBoolean("is_doctor"), rs.getString("medicalLicense_number"), rs.getString("specialization"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return doctor;
    }

    public static List<User> getPatientsByDoctorId(int doctorId) {
        List <User> patients = new ArrayList<>();
        User patient = null;
        ResultSet rs = null;
        String query = "select doctor_patient.patient_id, users.* from doctor_patient join users on doctor_patient.patient_id = users.id where doctor_id="+doctorId+"";
        Statement stmt = null;
        try {
            stmt = DatabaseConnection.con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
            try {
                while (rs.next()) {
                    patient = new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("password"), rs.getBoolean("is_doctor"));
                    patients.add(patient);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return patients;
    }

    public List<HealthData> getHealthDataByPatientId(int patientId) {
        List<HealthData> healthData = new ArrayList<>();
        HealthData healthDataObject = null;
        ResultSet rs = null;

        String query = "select doctor_patient.patient_id, health_data.* from doctor_patient join health_data on doctor_patient.patient_id = health_data.user_id where patient_id=" + patientId + "";
        Statement stmt = null;
        try {
            stmt = DatabaseConnection.con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            while (rs.next()) {
                healthDataObject = new HealthData(rs.getInt("id"), rs.getInt("user_id"), rs.getDouble("weight"), rs.getDouble("height"), rs.getInt("steps"), rs.getInt("heart_rate"), rs.getString("date"));
                healthData.add(healthDataObject);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return healthData;
    }

    public static boolean addPatient(int doctorId, int patientId) {
        String query = "insert into doctor_patient(doctor_id, patient_id) values(" + doctorId + "," + patientId + ")";
        Statement stmt = null;
        try {
            stmt = DatabaseConnection.con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int x = 0;
        try {
            x = stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (x > 0);
    }
}

