package com.keyin.model;

import com.keyin.client.DatabaseConnection;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
public class HealthDataDao {
    public static boolean createHealthData(HealthData healthData) { /* insert health data into database */
        String query = "insert into health_data(user_id, weight, height, steps, heart_rate, date) values("+healthData.getUserId()+","+healthData.getWeight()+","+healthData.getHeight()+","+healthData.getSteps()+","+healthData.getHeartRate()+",'"+healthData.getDate()+"')";

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
    public HealthData getHealthDataById(int id) { /* get health data by id from database */
        HealthData healthData = null;
        String query = "select * from health_data where id="+id+" ";
        Statement stmt = null;
        try {
            stmt = DatabaseConnection.con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            healthData = (HealthData) stmt.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
            return healthData;
    }
    public static List<HealthData> getHealthDataByUserId(int userId) { /* get health data by user id from database */
        List <HealthData> healthData = new ArrayList<>();
        HealthData healthDataObject = null;
        ResultSet rs = null;
        String query = "select * from health_data where user_id="+userId+" ";
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
    public boolean updateHealthData(HealthData healthData) { /* update health data in the database */
        // Prepare the SQL query
        String query = "update "+healthData.getId()+ "set userID= "+healthData.getUserId()+ ", weight= "+healthData.getWeight()+ ", height= "+healthData.getHeight()+ ", steps= "+healthData.getSteps()+", heartRate= "+healthData.getHeight() + ", date= '"+healthData.getDate()+"'";
        // Database logic to get update user Using Prepared Statement
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
    public boolean deleteHealthData(int id) { /* delete health data from the database */
        // Prepare the SQL query
        String query = "delete from health_data where id="+id+" ";
        // Database logic to delete user
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
