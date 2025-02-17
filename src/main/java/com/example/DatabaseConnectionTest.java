package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        String url = "jdbc:Altibase://10.255.100.90:20300/mydb";
        String username = "esnadm";
        String password = "esnadm#03";

        try {
            // Altibase 드라이버 클래스 로드
            Class.forName("Altibase.jdbc.driver.AltibaseDriver");

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                System.out.println("Connection successful!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Altibase JDBC Driver not found. Please ensure the driver is in the classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed.");
            e.printStackTrace();
        }
    }
}