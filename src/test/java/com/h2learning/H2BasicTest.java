package com.h2learning;

import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Test basic H2 database functionality
 */
public class H2BasicTest {
    
    @Test
    public void testInMemoryDatabase() throws Exception {
        // Create in-memory database
        String jdbcUrl = "jdbc:h2:mem:testdb";
        Connection conn = DriverManager.getConnection(jdbcUrl, "sa", "");
        
        assertNotNull("Connection should not be null", conn);
        
        // Create table
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE TEST (ID INT PRIMARY KEY, NAME VARCHAR(100))");
        
        // Insert data
        stmt.execute("INSERT INTO TEST VALUES (1, 'Test User')");
        
        // Query data
        ResultSet rs = stmt.executeQuery("SELECT * FROM TEST WHERE ID = 1");
        
        assertTrue("Should have at least one result", rs.next());
        assertEquals("Name should match", "Test User", rs.getString("NAME"));
        
        // Cleanup
        rs.close();
        stmt.close();
        conn.close();
    }
    
    @Test
    public void testPreparedStatement() throws Exception {
        String jdbcUrl = "jdbc:h2:mem:preptest";
        Connection conn = DriverManager.getConnection(jdbcUrl, "sa", "");
        
        // Create table
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE USERS (ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(100), AGE INT)");
        
        // Use prepared statement
        var pstmt = conn.prepareStatement("INSERT INTO USERS (NAME, AGE) VALUES (?, ?)");
        pstmt.setString(1, "Alice");
        pstmt.setInt(2, 30);
        int rowsAffected = pstmt.executeUpdate();
        
        assertEquals("Should insert one row", 1, rowsAffected);
        
        // Verify insertion
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as CNT FROM USERS");
        rs.next();
        assertEquals("Should have one user", 1, rs.getInt("CNT"));
        
        // Cleanup
        rs.close();
        pstmt.close();
        stmt.close();
        conn.close();
    }
    
    @Test
    public void testCRUDOperations() throws Exception {
        String jdbcUrl = "jdbc:h2:mem:crudtest";
        Connection conn = DriverManager.getConnection(jdbcUrl, "sa", "");
        Statement stmt = conn.createStatement();
        
        // Create
        stmt.execute("CREATE TABLE ITEMS (ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(100), PRICE DECIMAL(10,2))");
        stmt.execute("INSERT INTO ITEMS (NAME, PRICE) VALUES ('Item1', 10.50)");
        
        // Read
        ResultSet rs = stmt.executeQuery("SELECT * FROM ITEMS WHERE NAME = 'Item1'");
        assertTrue("Should find the item", rs.next());
        assertEquals("Price should match", 10.50, rs.getDouble("PRICE"), 0.01);
        
        // Update
        stmt.execute("UPDATE ITEMS SET PRICE = 15.75 WHERE NAME = 'Item1'");
        rs = stmt.executeQuery("SELECT PRICE FROM ITEMS WHERE NAME = 'Item1'");
        rs.next();
        assertEquals("Updated price should match", 15.75, rs.getDouble("PRICE"), 0.01);
        
        // Delete
        stmt.execute("DELETE FROM ITEMS WHERE NAME = 'Item1'");
        rs = stmt.executeQuery("SELECT COUNT(*) as CNT FROM ITEMS");
        rs.next();
        assertEquals("Should have no items", 0, rs.getInt("CNT"));
        
        // Cleanup
        rs.close();
        stmt.close();
        conn.close();
    }
    
    @Test
    public void testAutoIncrement() throws Exception {
        String jdbcUrl = "jdbc:h2:mem:autoinc";
        Connection conn = DriverManager.getConnection(jdbcUrl, "sa", "");
        Statement stmt = conn.createStatement();
        
        // Create table with auto-increment
        stmt.execute("CREATE TABLE SEQUENCE_TEST (ID INT PRIMARY KEY AUTO_INCREMENT, VAL VARCHAR(50))");
        
        // Insert multiple records
        stmt.execute("INSERT INTO SEQUENCE_TEST (VAL) VALUES ('First')");
        stmt.execute("INSERT INTO SEQUENCE_TEST (VAL) VALUES ('Second')");
        stmt.execute("INSERT INTO SEQUENCE_TEST (VAL) VALUES ('Third')");
        
        // Verify auto-increment
        ResultSet rs = stmt.executeQuery("SELECT * FROM SEQUENCE_TEST ORDER BY ID");
        
        rs.next();
        assertEquals("First ID should be 1", 1, rs.getInt("ID"));
        
        rs.next();
        assertEquals("Second ID should be 2", 2, rs.getInt("ID"));
        
        rs.next();
        assertEquals("Third ID should be 3", 3, rs.getInt("ID"));
        
        // Cleanup
        rs.close();
        stmt.close();
        conn.close();
    }
}
