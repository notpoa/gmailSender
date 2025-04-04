package org.example;

import java.sql.*;
import java.util.ArrayList;

public class DataFetcher {
    public static Object[][] getTableData() {
        String query = "SELECT month_name, value FROM monthly_data ORDER BY id";
        ArrayList<Object[]> rows = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String month = rs.getString("month_name");
                double value = rs.getDouble("value");
                rows.add(new Object[]{month, value});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows.toArray(new Object[0][]);
    }
}
