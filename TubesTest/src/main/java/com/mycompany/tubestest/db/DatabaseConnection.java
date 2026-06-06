package com.mycompany.tubestest.db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Koneksi JDBC ke MySQL (phpMyAdmin).
 */
public final class DatabaseConnection {

    private static final Properties PROPS = new Properties();

    static {
        try {
            loadProperties();
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (IOException | ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private DatabaseConnection() {
    }

    private static void loadProperties() throws IOException {
        try (InputStream in = DatabaseConnection.class.getClassLoader()
                .getResourceAsStream("database.properties")) {
            if (in != null) {
                PROPS.load(in);
                return;
            }
        }

        Path[] candidates = {
                Paths.get("TubesTest/src/main/resources/database.properties"),
                Paths.get("src/main/resources/database.properties"),
                Paths.get("../TubesTest/src/main/resources/database.properties")
        };
        for (Path path : candidates) {
            if (Files.isRegularFile(path)) {
                try (InputStream in = Files.newInputStream(path)) {
                    PROPS.load(in);
                    System.out.println("[DB] Config: " + path.toAbsolutePath());
                    return;
                }
            }
        }
        throw new IllegalStateException(
                "database.properties tidak ditemukan. Cek TubesTest/src/main/resources/");
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(
                PROPS.getProperty("db.url"),
                PROPS.getProperty("db.user"),
                PROPS.getProperty("db.password"));
        conn.setAutoCommit(true);
        return conn;
    }
}
