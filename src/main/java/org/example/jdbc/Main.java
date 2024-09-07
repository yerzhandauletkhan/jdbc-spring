package org.example.jdbc;

import org.example.jdbc.SqlQueries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.jdbc.JdbcUtils.*;

public class Main {
    public static void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {

                int id = resultSet.getInt("ID");
                String lastName = resultSet.getString("LASTNAME");
                double salary = resultSet.getDouble("SALARY");
                int department = resultSet.getInt("DEPARTMENT");
                int manager = resultSet.getInt("MANAGER");


                System.out.printf("ID: %d, Last Name: %s, Salary: %.2f, Department: %d, Manager: %d%n",
                        id, lastName, salary, department, manager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






    public static void main(String[] args) {
        JdbcUtils.checkConnection();
        String dropTables = "DROP TABLE IF EXISTS EMPLOYEE; DROP TABLE IF EXISTS DEPARTMENT;";
        executeUpdate(dropTables);


        String createTables = "CREATE TABLE DEPARTMENT ("
                + "ID SERIAL PRIMARY KEY, "
                + "NAME VARCHAR(100) NOT NULL);"
                + "CREATE TABLE EMPLOYEE ("
                + "ID SERIAL PRIMARY KEY, "
                + "LASTNAME VARCHAR(100) NOT NULL, "
                + "SALARY DECIMAL(10, 2) NOT NULL, "
                + "DEPARTMENT INT, "
                + "MANAGER INT, "
                + "FOREIGN KEY (DEPARTMENT) REFERENCES DEPARTMENT(ID), "
                + "FOREIGN KEY (MANAGER) REFERENCES EMPLOYEE(ID));";
        executeUpdate(createTables);


        String insertDepartment1 = "INSERT INTO DEPARTMENT (NAME) VALUES ('Sales')";
        String insertDepartment2 = "INSERT INTO DEPARTMENT (NAME) VALUES ('Marketing')";
        String insertDepartment3 = "INSERT INTO DEPARTMENT (NAME) VALUES ('IT')";

        executeUpdate(insertDepartment1);
        executeUpdate(insertDepartment2);
        executeUpdate(insertDepartment3);



        String insertEmployee1 = "INSERT INTO EMPLOYEE (LASTNAME, SALARY, DEPARTMENT, MANAGER) VALUES ('Smith', 2500, 1, NULL)";
        String insertEmployee2 = "INSERT INTO EMPLOYEE (LASTNAME, SALARY, DEPARTMENT, MANAGER) VALUES ('Johnson', 3000, 2, NULL)";
        String insertEmployee3 = "INSERT INTO EMPLOYEE (LASTNAME, SALARY, DEPARTMENT, MANAGER) VALUES ('Williams', 1800, 3, NULL)";

        executeUpdate(insertEmployee1);
        executeUpdate(insertEmployee2);
        executeUpdate(insertEmployee3);



        SqlQueries queries = new SqlQueries();

        System.out.println("All employees sorted by last name:");
        executeQuery(queries.select01);






    }




}
