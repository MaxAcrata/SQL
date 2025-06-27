package ru.netology.banklogin.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/app";
    private static final String USER = "app";
    private static final String PASSWORD = "pass";

    private SQLHelper() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось подключиться к базе данных", e);
        }
    }


    public static void cleanDatabase() {
        executeUpdate("DELETE FROM auth_codes");
        executeUpdate("DELETE FROM card_transactions");
        executeUpdate("DELETE FROM cards");
        executeUpdate("DELETE FROM users");
    }

    private static void executeUpdate(String sql) {
        var runner = new QueryRunner();
        try (var conn = getConnection()) {
            runner.update(conn, sql);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка выполнения SQL: " + sql, e);
        }
    }

    public static String getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        var runner = new QueryRunner();
        try (var conn = getConnection()) {
            return runner.query(conn, codeSQL, new ScalarHandler<>());
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении кода подтверждения", e);
        }
    }

    public static String getUserStatus(String login) {
        var sql = "SELECT status FROM users WHERE login = ?";
        var runner = new QueryRunner();
        try (var conn = getConnection()) {
            return runner.query(conn, sql, new ScalarHandler<>(), login);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении статуса пользователя", e);
        }
    }

    public static void resetUserStatus(String login) {
        var sql = "UPDATE users SET status = 'active' WHERE login = ?";
        var runner = new QueryRunner();
        try (var conn = getConnection()) {
            runner.update(conn, sql, login);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сбросе статуса пользователя", e);
        }
    }

    public static void createTestUser() {
        var sql = "INSERT INTO users (id, login, password, status) VALUES (?, ?, ?, ?)";
        var runner = new QueryRunner();
        try (var conn = getConnection()) {
            runner.update(conn, sql, "1", "vasya", "$2a$10$SzzuZrV7idB7JkTsSaNWMeKMYk0bFlheQZg7hArN4XddC6CEB1rVm", "active");
        } catch (SQLException e) {
            if (!e.getMessage().contains("Duplicate entry")) {
                throw new RuntimeException("Ошибка при создании тестового пользователя", e);
            }
        }
    }
}