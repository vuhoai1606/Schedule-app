package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class RegisterDAO {
    public static boolean isUsernameOrEmailTaken(String username, String email) {
        String checkQuery = "SELECT id FROM users WHERE username = ? OR email = ?";
        try (Connection conn = connectDb.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, username);
            checkStmt.setString(2, email);
            ResultSet rs = checkStmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean registerUser(String username, String email, String password) {
        String insertQuery = "INSERT INTO users (id, username, email, password_hash) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectDb.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            String encryptedPassword = util.AESEncryption.encrypt(password);
            String userId = UUID.randomUUID().toString();

            insertStmt.setString(1, userId);
            insertStmt.setString(2, username);
            insertStmt.setString(3, email);
            insertStmt.setString(4, encryptedPassword);
            insertStmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}