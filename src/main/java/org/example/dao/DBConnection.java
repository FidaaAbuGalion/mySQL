package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String HOST ="jdbc:mysql://localhost:3306/studentdb?useSSL=false";
    private static final String USERNAME ="root";
    private static final String PASSWORD ="F.i.d.a.2003";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //System.out.println("Driver loaded");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Failed to load JDBC driver.", e);
        }
    }

    public static Connection getConnection(){
        try {
            connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
           // System.out.println("Connected to database");
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            return null;
        }
        return connection;
    }
}
