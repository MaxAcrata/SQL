package ru.netology.banklogin.data;

import java.sql.*;


public class SQLHelper {
    private SQLHelper() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/app", // имя БД из docker-compose
                    "app",                             // имя пользователя
                    "pass"                             // пароль
            );
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось подключиться к базе данных", e);
        }
    }


    public static void cleanAuthCodes() {
        try (var conn = getConnection();
             var stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM auth_codes");
            stmt.executeUpdate("DELETE FROM card_transactions");
            stmt.executeUpdate("DELETE FROM cards");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при очистке", e);
        }
    }


    public static DataHelper.VerificationCode getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        try (
                var conn = getConnection();
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery(codeSQL)
        ) {
            if (rs.next()) {
                return new DataHelper.VerificationCode(rs.getString("code"));
            } else {
                throw new RuntimeException("Код подтверждения не найден");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении кода подтверждения", e);
        }
    }

    public static String getUserStatus(String login) {
        var sql = "SELECT status FROM users WHERE login = ?";
        try (var conn = getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении статуса пользователя", e);
        }
        return null;
    }
}