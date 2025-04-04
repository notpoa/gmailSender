package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3307/sampledb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String user = "root";         // change to your MySQL username
        String password = "2Z3VTpdF@nq!sZ-"; // change to your MySQL password

        return DriverManager.getConnection(url, user, password);
    }
}
