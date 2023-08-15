package com.keyin.service;

import com.keyin.client.DatabaseConnection;
import com.keyin.model.MedicineReminder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;



/**
 * The MedicineReminderManager class should have methods to add reminders, get reminders
 *  1. for a specific user, and
 *  2. get reminders that are DUE for a specific user.
 *
 *  You'll need to integrate this class with your application and database logic to
 *  1. store,
 *  2. update, and
 *  3. delete reminders as needed.
 */

public class MedicineReminderManager {
    private List<MedicineReminder> reminders;

    public MedicineReminderManager() {
        this.reminders = new ArrayList<>();
    }

    public void addReminder(MedicineReminder reminder) {
        reminders.add(reminder);
    }

    public void deleteReminder(MedicineReminder reminder) { reminders.remove(reminder); }

    public void updateReminder(MedicineReminder reminder) { reminders.set(reminders.indexOf(this),reminder); }

    public static List<MedicineReminder> getRemindersForUser(int userId) {
        List<MedicineReminder> userReminders = new ArrayList<MedicineReminder>();
        ResultSet rs = null;
        MedicineReminder reminder = null;
        // Write your logic here
        String query = "select * from medicine_reminders where user_id="+userId+" ";
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
                reminder = new MedicineReminder(rs.getInt("id"), rs.getInt("user_id"), rs.getString("medicine_name"), rs.getString("dosage"), rs.getString("schedule"), rs.getString("start_date"), rs.getString("end_date"));
                userReminders.add(reminder);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userReminders;
    }

    public static List<MedicineReminder> getDueReminders(int userId) {

        List<MedicineReminder> dueReminders = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ResultSet rs = null;
        MedicineReminder reminder = null;

        String query = "select * from medicine_reminders where end_date < '"+now+"'";
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
                reminder = new MedicineReminder(rs.getInt("id"), rs.getInt("user_id"), rs.getString("medicine_name"), rs.getString("dosage"), rs.getString("schedule"), rs.getString("start_date"), rs.getString("end_date"));
                dueReminders.add(reminder);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dueReminders;

    }

    // ---database stuff---

    // implement update
    public boolean updateMedicineReminder(MedicineReminder reminder){

        String query = "update "+reminder.getId()+ "set userID= "+reminder.getUserId()+ ", medicine name= "+reminder.getMedicineName()+ ", dosage= "+reminder.getDosage()+ ", schedule= "+reminder.getSchedule()+", start date= "+reminder.getStartDate() + ", end date= "+reminder.getEndDate()+"";
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
        return x > 0;
    }

    //implement creating the reminder
    public static boolean createMedicineReminder(MedicineReminder reminder){
        String query = "insert into medicine_reminders (user_id, medicine_name, dosage, schedule, start_date, end_date) values("+reminder.getUserId()+",'"+reminder.getMedicineName()+"','"+reminder.getDosage()+"','"+reminder.getSchedule()+"','"+reminder.getStartDate()+"','"+reminder.getEndDate()+"')";
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
        return x > 0;
    }

    //implement delete
    public boolean deleteMedicineReminder(int id) {
        // Prepare the SQL query
        String query = "delete from medicineReminder where id="+id+" ";
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
        return x > 0;
    }


}
