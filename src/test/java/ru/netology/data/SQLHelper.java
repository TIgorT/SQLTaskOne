package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM auth_codes");
        runner.execute(connection, "DELETE FROM card_transactions");
        runner.execute(connection, "DELETE FROM cards");
        runner.execute(connection, "DELETE FROM users");
    }

    @SneakyThrows
    public static void cleaningTheTableWithVerificationCodes() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM auth_codes");
    }


    @SneakyThrows
    public static DataHelper.VerificationCode getTheVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER by created DESC LIMIT 1";
        var conn = getConn();
        var code = runner.query(conn, codeSQL, new ScalarHandler<String>());
        return new DataHelper.VerificationCode(code);
    }

    @SneakyThrows
    public static DataHelper.VerificationCode getOutdatedTheVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER by created  LIMIT 1 ";
        var conn = getConn();
        var code = runner.query(conn, codeSQL, new ScalarHandler<String>());
        return new DataHelper.VerificationCode(code);
    }

    @SneakyThrows
    public static String getUserStatus(String login) {
        var sqlQuery = "SELECT status FROM users WHERE login IN (?);";
        var conn = getConn();
        return runner.query(conn, sqlQuery, new ScalarHandler<String>(), login);
    }

}
