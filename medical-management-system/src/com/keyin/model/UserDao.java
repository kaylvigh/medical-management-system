package com.keyin.model;

import com.keyin.client.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
public class UserDao {
    public static boolean createUser(User user) {
        /* insert user into database */
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        // Prepare the SQL query
        String query = "insert into users(first_name, last_name, email, password, is_doctor) values('"+user.getFirstName()+"','"+user.getLastName()+"','"+user.getEmail()+"','"+hashedPassword+"',"+user.isDoctor()+")";
        // Database logic to insert data using PREPARED Statement
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
    public static User getUserById(int id) { /* get user by id from database */
        User user = null;
        // Prepare the SQL query
        String query = "select * from users where id="+id+" ";
        // Database logic to get data by ID Using Prepared Statement
        Statement stmt = null;
        try {
            stmt = DatabaseConnection.con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            user = (User) stmt.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (user != null)
            return user;
        else
            return null;
    }

    public static User getUserByEmail(String email) { /* get user by email from database */
        User user = null;
        ResultSet rs = null;
        // Prepare the SQL query
        String query = "select * from users where email='"+email+"'";
        // Database logic to get data by ID Using Prepared Statement
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
            if (!rs.next()) {
                return user;
            }
            else {
                try {
                    user = new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("password"), rs.getBoolean("is_doctor"));
                    return user;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean updateUser(User user) {
        // Prepare the SQL query
        String query = "update "+user.getId()+" set firstName= '"+user.getFirstName()+"',lastName= '"+user.getLastName()+"' ,email= '"+user.getEmail()+"' ,password= '"+user.getPassword()+"'";
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
    public static boolean deleteUser(int id) { /* delete user from the database */
        // Prepare the SQL query
        String query = "delete from users where id="+id+" ";
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

    public static boolean verifyPassword (String email, String password) {
        String query = "SELECT password FROM users WHERE email = ?";
        //Implement logic to retrieve password using the Bcrypt
        User user = getUserByEmail(email);
        return(BCrypt.checkpw(password, user.getPassword()));
    }

}
