package com.h2learning;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * H2 Database Example with Prepared Statements
 * 
 * This example demonstrates:
 * 1. Using PreparedStatement for safe SQL execution
 * 2. Protection against SQL injection
 * 3. Parameterized queries
 * 4. Batch operations
 */
public class PreparedStatementExample {
    
    private static final String JDBC_URL = "jdbc:h2:mem:prepareddb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    
    public static void main(String[] args) {
        System.out.println("=== H2 PreparedStatement Example ===\n");
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            
            // Create table
            System.out.println("1. Creating PRODUCTS table...");
            conn.createStatement().execute(
                "CREATE TABLE PRODUCTS (" +
                "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                "NAME VARCHAR(100), " +
                "PRICE DECIMAL(10, 2), " +
                "QUANTITY INT)"
            );
            System.out.println("   Table created!\n");
            
            // Insert with PreparedStatement
            System.out.println("2. Inserting products using PreparedStatement...");
            String insertSQL = "INSERT INTO PRODUCTS (NAME, PRICE, QUANTITY) VALUES (?, ?, ?)";
            
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                // Insert product 1
                pstmt.setString(1, "Laptop");
                pstmt.setDouble(2, 999.99);
                pstmt.setInt(3, 10);
                pstmt.executeUpdate();
                
                // Insert product 2
                pstmt.setString(1, "Mouse");
                pstmt.setDouble(2, 25.50);
                pstmt.setInt(3, 50);
                pstmt.executeUpdate();
                
                // Insert product 3
                pstmt.setString(1, "Keyboard");
                pstmt.setDouble(2, 75.00);
                pstmt.setInt(3, 30);
                pstmt.executeUpdate();
            }
            System.out.println("   Products inserted!\n");
            
            // Query with PreparedStatement
            System.out.println("3. Querying products with price > $50...");
            String querySQL = "SELECT * FROM PRODUCTS WHERE PRICE > ?";
            
            try (PreparedStatement pstmt = conn.prepareStatement(querySQL)) {
                pstmt.setDouble(1, 50.0);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    System.out.println("\n   Results:");
                    System.out.println("   +----------------------------------------+");
                    System.out.println("   | ID | Name     | Price    | Quantity |");
                    System.out.println("   +----------------------------------------+");
                    
                    while (rs.next()) {
                        System.out.printf("   | %-2d | %-8s | $%-7.2f | %-8d |%n",
                            rs.getInt("ID"),
                            rs.getString("NAME"),
                            rs.getDouble("PRICE"),
                            rs.getInt("QUANTITY"));
                    }
                    System.out.println("   +----------------------------------------+\n");
                }
            }
            
            // Batch insert
            System.out.println("4. Batch inserting multiple products...");
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                // Add multiple batches
                pstmt.setString(1, "Monitor");
                pstmt.setDouble(2, 299.99);
                pstmt.setInt(3, 15);
                pstmt.addBatch();
                
                pstmt.setString(1, "Webcam");
                pstmt.setDouble(2, 89.99);
                pstmt.setInt(3, 25);
                pstmt.addBatch();
                
                // Execute batch
                int[] results = pstmt.executeBatch();
                System.out.printf("   Batch insert completed: %d records inserted\n\n", results.length);
            }
            
            // Query all products
            System.out.println("5. All products in database:");
            try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM PRODUCTS")) {
                System.out.println("\n   +----------------------------------------+");
                System.out.println("   | ID | Name     | Price    | Quantity |");
                System.out.println("   +----------------------------------------+");
                
                while (rs.next()) {
                    System.out.printf("   | %-2d | %-8s | $%-7.2f | %-8d |%n",
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getDouble("PRICE"),
                        rs.getInt("QUANTITY"));
                }
                System.out.println("   +----------------------------------------+\n");
            }
            
            System.out.println("=== Example completed successfully! ===");
            
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
