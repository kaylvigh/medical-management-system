package com.keyin;

import com.keyin.client.DatabaseConnection;
import com.keyin.model.*;
import com.keyin.service.MedicineReminderManager;
import com.keyin.service.RecommendationSystem;

import java.util.List;

public class HealthMonitoringApp {

// remember to create constructors for the objects

    /**
     * Test the following functionalities within the Main Application
     *  1. Register a new user
     *  2. Log in the user
     *  3. Add health data
     *  4. Generate recommendations
     *  5. Add a medicine reminder
     *  6. Get reminders for a specific user
     *  7. Get due reminders for a specific user
     *  8. test doctor portal
     */

    public static void main(String[] args) {

        DatabaseConnection db = new DatabaseConnection();

        // test register a new user
        User user = new User(1, "FirstNameExample", "LastName", "Email", "Password", false);
            if (UserDao.createUser(user)) {
                System.out.println("User registration test passed.");
            }
                else {
                System.out.println("User registration test failed.");
            }

        // test Login user (call testLoginUser() here)
        testLoginUser();

        // Add health data
        HealthData healthData = new HealthData(1, 1, 150, 120, 10000, 90, "2022/02/03");
            if (HealthDataDao.createHealthData(healthData)){
            System.out.println("Adding health data test passed.");
            }
                else {
            System.out.println("Adding health data test failed.");
            }
        // Generate recommendations
        List<String> testRecommendations = RecommendationSystem.generateRecommendations(healthData);
            if (testRecommendations.isEmpty()) {
                System.out.println("Recommendation generation test passed.");
            }
            else {
                System.out.println("Recommendation generation test failed.");
            }
        // Add a medicine reminder

        MedicineReminder testReminder = new MedicineReminder(1, 1, "TestMedicine", "20mg", "Daily", "2022/06/07", "2022/09/13");
            if (MedicineReminderManager.createMedicineReminder(testReminder)){
                System.out.println("Creating medicine reminder test passed.");
            }
            else {
                System.out.println("Creating medicine reminder test failed.");
            }
        // Get reminders for a specific user
        List<MedicineReminder> testGetReminders = MedicineReminderManager.getRemindersForUser(1);
            if (!testGetReminders.isEmpty()) {
                System.out.println("Get reminders test passed.");
            }
            else {
                System.out.println("Get reminders generation test failed.");
            }
        // Get due reminders for a specific user
        List<MedicineReminder> testGetDueReminders = MedicineReminderManager.getDueReminders(1);
            if (!testGetDueReminders.isEmpty()) {
                System.out.println("Get due reminders test passed.");
            }
            else {
                System.out.println("Get due reminders generation test failed.");
            }
        //test doctor portal (call testDoctorPortal() here)
        testDoctorPortal();
    }

    public static boolean loginUser(String email, String password) {
        //implement method to login user.
        User user = UserDao.getUserByEmail(email);

        if (user != null) {
            // Compare the stored hashed password with the given password and return result
            if (UserDao.verifyPassword(email, password)) {
                return true;
            }
        }
        return false;

    }

    /**
     * To test the Doctor Portal in your Health Monitoring System, provide a simple test code method that you can add
     * to your main application class.
     * In this method, we'll test the following functionalities:
     * 1. Fetching a doctor by ID
     * 2. Fetching patients associated with a doctor
     * 3. Fetching health data for a specific patient
      */
    public static void testDoctorPortal() {
        // Replace the doctorId with a valid ID from your database
        int doctorId = 2;

        Doctor doctor = new Doctor(2, "DoctorFirstName", "DoctorLastName", "DoctorEmail", "DoctorPassword", true, "123", "General Medicine");
        DoctorPortalDao.createDoctor(doctor);
        DoctorPortalDao.addPatient(2,1);

        // Add code to Fetch the doctor by ID
        // notes(delete later) Class.method(parameter) <-- only for static methods
        Doctor doctorTest = DoctorPortalDao.getDoctorById(doctorId);
        if (doctorTest == null) {
            System.out.println("Fetch doctor by ID failed.");
            return;
        }
        // Add code to Fetch patients associated with the doctor
        List<User> patientsTest = DoctorPortalDao.getPatientsByDoctorId(2);
        // Add code to Fetch health data for the patient
        assert patientsTest != null;
        List<HealthData> patientsDataTest = HealthDataDao.getHealthDataByUserId(patientsTest.get(0).getId());
        // Happy path
        System.out.println("Assuming the doctor has patients:");
        if (!patientsDataTest.isEmpty()) {
            System.out.println("Health data fetch successful.");
        }
        else {
            System.out.println("Health data fetch unsuccessful.");
        }
    }

    /**
     * To test the login user functionality in your Health Monitoring System, you can
     * add a test method to your main application class
     */
    public static void testLoginUser() {
        // Replace the email and password with valid credentials from your database
        String userEmail = "Email";
        String userPassword = "Password";

        boolean loginSuccess = loginUser(userEmail, userPassword);

        // Happy Path
        if (loginSuccess) {
            // Print to console, "Login Successful"
            System.out.println("Successful Login path completed.");
        } else {
            // Print to console, "Incorrect email or password. Please try again.");
            System.out.println("Error-- Successful Login path failed.");
        }

        // Sad Path
        if (!loginSuccess) {
            // Print to console, "Login Successful"
            System.out.println("Failed login path completed.");
            System.out.println("Incorrect email or password. Please try again.");
            System.out.println("Please input the correct username and password.");

        } else {
            System.out.println("Failed login test failed-- Login is working.");
        }
    }

}
