package ru.netology.service.mode;

import java.sql.*;

public class DBUtils {

    private static final String POSTGRES_URL = "jdbc:postgresql://localhost:5432/app";
    private static final String POSTGRES_USER = "app";
    private static final String POSTGRES_PASSWORD = "pass";

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/app";
    private static final String MYSQL_USER = "app";
    private static final String MYSQL_PASSWORD = "pass";

    public static Connection getPostgresConnection() throws SQLException {
        return DriverManager.getConnection(POSTGRES_URL, POSTGRES_USER, POSTGRES_PASSWORD);
    }

    public static Connection getMysqlConnection() throws SQLException {
        return DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
    }

    public static String getLastPaymentStatusPostgres() {
        String status = null;
        String query = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        try (Connection conn = getPostgresConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                status = rs.getString("status");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении последнего статуса", e);
        }
        return status;
    }

    public static String getLastPaymentStatusMysql() {
        String status = null;
        String query = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        try (Connection conn = getMysqlConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                status = rs.getString("status");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении последнего статуса", e);
        }
        return status;
    }
}
