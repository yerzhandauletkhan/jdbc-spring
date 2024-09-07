package org.example.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {
    private static final String URL = "jdbc:postgresql://localhost:5432/company_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void checkConnection() {
        try (Connection connection = getConnection()) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Connection failed.");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    public static void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             var resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                // Process the result set
                // For example: print results or map to objects
                System.out.println(resultSet.getString("LASTNAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void executeUpdate(String update) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            int rowsAffected = statement.executeUpdate(update);
            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
