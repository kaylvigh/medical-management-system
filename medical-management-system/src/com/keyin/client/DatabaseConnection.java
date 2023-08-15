package com.keyin.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
//Implement client here
    private String url = "jdbc:mysql://localhost:3306/medical_management_system";
    private String user = "root";
    private String password = "nums";

    public static Connection con;
    public DatabaseConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,user,password);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void close() throws SQLException {
        con.close();
    }
}




