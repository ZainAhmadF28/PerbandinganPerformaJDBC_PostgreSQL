package com.jdbc.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class untuk konfigurasi koneksi database MySQL
 */
public class DatabaseConfig {
    // Konstanta untuk koneksi database
    private static final String URL = "jdbc:mysql://localhost:3306/db_pegawai?useSSL=false&serverTimezone=Asia/Jakarta&allowPublicKeyRetrieval=true&useLegacyDatetimeCode=false";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // kosong = tidak ada password

    /**
     * Method untuk mendapatkan koneksi ke database
     * @return Connection object
     * @throws SQLException jika koneksi gagal
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver tidak ditemukan: " + e.getMessage());
        }
    }
}
