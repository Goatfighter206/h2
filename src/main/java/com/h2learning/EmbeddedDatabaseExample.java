package com.h2learning;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * H2 Embedded (File-based) Database Example
 * 
 * This example demonstrates:
 * 1. Creating a file-based H2 database
 * 2. Data persistence across connections
 * 3. Database files storage
 */
public class EmbeddedDatabaseExample {
    
    // JDBC URL for file-based H2 database
    // This will create database files in ./data directory
    private static final String JDBC_URL = "jdbc:h2:./data/mydb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    
    public static void main(String[] args) {
        System.out.println("=== H2 Embedded Database Example ===\n");
        
        // First connection - create and populate
        System.out.println("Part 1: Creating database and adding data...");
        createAndPopulateDatabase();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Second connection - verify persistence
        System.out.println("Part 2: Reconnecting to verify data persistence...");
        verifyDataPersistence();
        
        System.out.println("\n=== Example completed successfully! ===");
        System.out.println("\nNote: Database files are stored in ./data/ directory");
        System.out.println("Files: mydb.mv.db (data file)");
    }
    
    private static void createAndPopulateDatabase() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            System.out.println("1. Connected to embedded database");
            
            // Drop table if exists
            stmt.execute("DROP TABLE IF EXISTS BOOKS");
            
            // Create table
            System.out.println("2. Creating BOOKS table...");
            stmt.execute(
                "CREATE TABLE BOOKS (" +
                "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                "TITLE VARCHAR(200), " +
                "AUTHOR VARCHAR(100), " +
                "PUBLISH_YEAR INT, " +
                "ISBN VARCHAR(20))"
            );
            
            // Insert data
            System.out.println("3. Inserting book records...");
            stmt.execute("INSERT INTO BOOKS (TITLE, AUTHOR, PUBLISH_YEAR, ISBN) VALUES " +
                "('The Great Gatsby', 'F. Scott Fitzgerald', 1925, '978-0-7432-7356-5')");
            stmt.execute("INSERT INTO BOOKS (TITLE, AUTHOR, PUBLISH_YEAR, ISBN) VALUES " +
                "('To Kill a Mockingbird', 'Harper Lee', 1960, '978-0-06-112008-4')");
            stmt.execute("INSERT INTO BOOKS (TITLE, AUTHOR, PUBLISH_YEAR, ISBN) VALUES " +
                "('1984', 'George Orwell', 1949, '978-0-452-28423-4')");
            
            System.out.println("4. Data inserted successfully!");
            
            // Display data
            ResultSet rs = stmt.executeQuery("SELECT * FROM BOOKS");
            displayBooks(rs);
            
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void verifyDataPersistence() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            System.out.println("1. Reconnected to database");
            System.out.println("2. Querying data to verify persistence...");
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM BOOKS");
            displayBooks(rs);
            
            System.out.println("\n3. Data persisted successfully!");
            
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void displayBooks(ResultSet rs) throws SQLException {
        System.out.println("\n   Books in database:");
        System.out.println("   " + "-".repeat(90));
        System.out.printf("   | %-2s | %-30s | %-20s | %-4s | %-18s |%n",
            "ID", "Title", "Author", "Year", "ISBN");
        System.out.println("   " + "-".repeat(90));
        
        while (rs.next()) {
            System.out.printf("   | %-2d | %-30s | %-20s | %-4d | %-18s |%n",
                rs.getInt("ID"),
                rs.getString("TITLE"),
                rs.getString("AUTHOR"),
                rs.getInt("PUBLISH_YEAR"),
                rs.getString("ISBN"));
        }
        System.out.println("   " + "-".repeat(90));
    }
}
