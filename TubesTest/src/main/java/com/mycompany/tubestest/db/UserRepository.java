package com.mycompany.tubestest.db;

import com.mycompany.tubestest.Admin;
import com.mycompany.tubestest.Customer;
import com.mycompany.tubestest.Staff;
import com.mycompany.tubestest.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public List<User> findAll() throws SQLException {
        String sql = "SELECT id, name, role, is_member FROM users ORDER BY id";
        List<User> users = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User user = mapRow(rs);
                if (user != null) {
                    users.add(user);
                }
            }
        }
        return users;
    }

    public User findById(String id) throws SQLException {
        String sql = "SELECT id, name, role, is_member FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id.trim().toUpperCase());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return mapRow(rs);
            }
        }
    }

    public boolean exists(String id) throws SQLException {
        return findById(id) != null;
    }

    public int countOrdersByCustomer(String customerId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM orders WHERE customer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customerId.trim().toUpperCase());
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public boolean deleteById(String id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id.trim().toUpperCase());
            return ps.executeUpdate() > 0;
        }
    }

    public void updateAdmin(String oldId, String newId, String newName) throws SQLException {
        String oldKey = oldId.trim().toUpperCase();
        String newKey = newId.trim().toUpperCase();
        String sql = "UPDATE users SET id = ?, name = ? WHERE id = ? AND role = 'Admin'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newKey);
            ps.setString(2, newName.trim());
            ps.setString(3, oldKey);
            if (ps.executeUpdate() != 1) {
                throw new SQLException("Admin tidak ditemukan atau gagal diperbarui.");
            }
        }
    }

    public void insertCustomer(String id, String name, boolean isMember) throws SQLException {
        String sql = "INSERT INTO users (id, name, role, is_member) VALUES (?, ?, 'Customer', ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id.trim().toUpperCase());
            ps.setString(2, name.trim());
            ps.setBoolean(3, isMember);
            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new SQLException("Gagal menyimpan customer ke database.");
            }
        }
    }

    public void insertStaff(String id, String name) throws SQLException {
        insertSimple(id, name, "Staff");
    }

    public void insertAdmin(String id, String name) throws SQLException {
        insertSimple(id, name, "Admin");
    }

    private void insertSimple(String id, String name, String role) throws SQLException {
        String sql = "INSERT INTO users (id, name, role, is_member) VALUES (?, ?, ?, 0)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id.trim().toUpperCase());
            ps.setString(2, name.trim());
            ps.setString(3, role);
            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new SQLException("Gagal menyimpan user ke database.");
            }
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String name = rs.getString("name");
        String role = rs.getString("role");
        boolean isMember = rs.getBoolean("is_member");

        return switch (role) {
            case "Customer" -> new Customer(id, name, isMember);
            case "Staff" -> new Staff(id, name);
            case "Admin" -> new Admin(id, name);
            default -> null;
        };
    }
}
