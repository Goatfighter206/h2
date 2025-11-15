package com.h2learning;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Basic H2 Database Example - In-Memory Database
 * 
 * This example demonstrates:
 * 1. Creating an in-memory H2 database connection
 * 2. Creating a table
 * 3. Inserting data
 * 4. Querying data
 * 5. Properly closing resources
 */
public class BasicH2Example {
    
    // JDBC URL for H2 in-memory database
    private static final String JDBC_URL = "jdbc:h2:mem:testdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    
    public static void main(String[] args) {
        System.out.println("=== H2 Database Basic Example ===\n");
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            // 1. Establish connection
            System.out.println("1. Connecting to H2 database...");
            conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("   Connected successfully!\n");
            
            // 2. Create a table
            System.out.println("2. Creating USERS table...");
            stmt = conn.createStatement();
            String createTableSQL = "CREATE TABLE USERS (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "NAME VARCHAR(100), " +
                    "EMAIL VARCHAR(100), " +
                    "AGE INT)";
            stmt.execute(createTableSQL);
            System.out.println("   Table created successfully!\n");
            
            // 3. Insert data
            System.out.println("3. Inserting sample data...");
            stmt.execute("INSERT INTO USERS (NAME, EMAIL, AGE) VALUES ('Alice Johnson', 'alice@example.com', 28)");
            stmt.execute("INSERT INTO USERS (NAME, EMAIL, AGE) VALUES ('Bob Smith', 'bob@example.com', 35)");
            stmt.execute("INSERT INTO USERS (NAME, EMAIL, AGE) VALUES ('Charlie Brown', 'charlie@example.com', 42)");
            System.out.println("   Data inserted successfully!\n");
            
            // 4. Query data
            System.out.println("4. Querying all users...");
            rs = stmt.executeQuery("SELECT * FROM USERS");
            
            System.out.println("\n   Results:");
            System.out.println("   +-------------------------------------------------+");
            System.out.println("   | ID | Name            | Email                | Age |");
            System.out.println("   +-------------------------------------------------+");
            
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String email = rs.getString("EMAIL");
                int age = rs.getInt("AGE");
                
                System.out.printf("   | %-2d | %-15s | %-20s | %-3d |%n", id, name, email, age);
            }
            System.out.println("   +-------------------------------------------------+\n");
            
            // 5. Update data
            System.out.println("5. Updating a user's age...");
            stmt.execute("UPDATE USERS SET AGE = 29 WHERE NAME = 'Alice Johnson'");
            System.out.println("   Update successful!\n");
            
            // 6. Delete data
            System.out.println("6. Deleting a user...");
            stmt.execute("DELETE FROM USERS WHERE NAME = 'Charlie Brown'");
            System.out.println("   Delete successful!\n");
            
            // 7. Query updated data
            System.out.println("7. Querying users after updates...");
            rs = stmt.executeQuery("SELECT * FROM USERS");
            
            System.out.println("\n   Results:");
            System.out.println("   +-------------------------------------------------+");
            System.out.println("   | ID | Name            | Email                | Age |");
            System.out.println("   +-------------------------------------------------+");
            
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String email = rs.getString("EMAIL");
                int age = rs.getInt("AGE");
                
                System.out.printf("   | %-2d | %-15s | %-20s | %-3d |%n", id, name, email, age);
            }
            System.out.println("   +-------------------------------------------------+\n");
            
            System.out.println("=== Example completed successfully! ===");
            
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 8. Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                System.out.println("\nResources closed successfully.");
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
