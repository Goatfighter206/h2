# H2 Database Learning Project

A comprehensive learning project for H2 Database with practical examples and tutorials.

## What is H2 Database?

H2 is a lightweight, fast, open-source Java SQL database. It's perfect for:
- Learning SQL and JDBC
- Development and testing
- Embedded applications
- In-memory databases

## Features Covered

This project includes examples for:
- ✅ In-memory database operations
- ✅ Embedded (file-based) database
- ✅ Basic CRUD operations (Create, Read, Update, Delete)
- ✅ Prepared statements for safe SQL execution
- ✅ Batch operations
- ✅ Data persistence

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Goatfighter206/h2.git
cd h2
```

### 2. Build the Project

```bash
mvn clean compile
```

### 3. Run Examples

#### Basic H2 Example (In-Memory Database)
```bash
mvn exec:java -Dexec.mainClass="com.h2learning.BasicH2Example"
```

This example demonstrates:
- Creating an in-memory database
- Creating tables
- Inserting, updating, and deleting data
- Querying data

#### PreparedStatement Example
```bash
mvn exec:java -Dexec.mainClass="com.h2learning.PreparedStatementExample"
```

This example shows:
- Using prepared statements to prevent SQL injection
- Parameterized queries
- Batch inserts

#### Embedded Database Example
```bash
mvn exec:java -Dexec.mainClass="com.h2learning.EmbeddedDatabaseExample"
```

This example illustrates:
- Creating a file-based database
- Data persistence across connections
- Database file storage

## Project Structure

```
h2-learning/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── h2learning/
│   │               ├── BasicH2Example.java
│   │               ├── PreparedStatementExample.java
│   │               └── EmbeddedDatabaseExample.java
│   └── test/
│       └── java/
│           └── com/
│               └── h2learning/
├── pom.xml
└── README.md
```

## Key Concepts

### In-Memory Database
- Database exists only in RAM
- Very fast performance
- Data is lost when connection closes
- Perfect for testing
- URL format: `jdbc:h2:mem:dbname`

### Embedded Database
- Database stored as files on disk
- Data persists between runs
- No separate server process needed
- URL format: `jdbc:h2:./path/to/dbfile`

### Server Mode (Not covered in basic examples)
- H2 can run as a standalone server
- Multiple applications can connect
- URL format: `jdbc:h2:tcp://localhost/~/dbname`

## H2 Database Advantages

1. **Lightweight**: Small footprint (~2.5 MB JAR file)
2. **Fast**: Very fast query execution
3. **No Installation**: Embeddable in Java applications
4. **Pure Java**: Platform independent
5. **Multiple Modes**: In-memory, embedded, and server modes
6. **SQL Support**: Standard SQL with extensions
7. **Browser Console**: Built-in web console for database management

## Running the H2 Console

To start the H2 web console:

```bash
mvn exec:java -Dexec.mainClass="org.h2.tools.Console"
```

Then open your browser to: `http://localhost:8082`

Connection settings for in-memory database:
- JDBC URL: `jdbc:h2:mem:testdb`
- User Name: `sa`
- Password: (leave empty)

## Common Operations

### Create Table
```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100)
);
```

### Insert Data
```sql
INSERT INTO users (name, email) VALUES ('John Doe', 'john@example.com');
```

### Query Data
```sql
SELECT * FROM users WHERE name LIKE '%John%';
```

### Update Data
```sql
UPDATE users SET email = 'newemail@example.com' WHERE id = 1;
```

### Delete Data
```sql
DELETE FROM users WHERE id = 1;
```

## Testing

Run tests with:
```bash
mvn test
```

## Additional Resources

- [H2 Database Official Website](http://www.h2database.com/)
- [H2 Documentation](http://www.h2database.com/html/main.html)
- [H2 Tutorial](http://www.h2database.com/html/tutorial.html)
- [JDBC Tutorial](https://docs.oracle.com/javase/tutorial/jdbc/)

## License

This is a learning project for educational purposes.

## Contributing

Feel free to open issues or submit pull requests to improve the learning materials!