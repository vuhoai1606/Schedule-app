package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ForgotPassDAO {
    public static boolean validateEmailForUser(String username, String email) {
        String query = "SELECT * FROM users WHERE username = ? AND email = ?";
        try (Connection conn = connectDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePassword(String email, String newPassword) {
        String query = "UPDATE users SET password_hash = ? WHERE email = ?";
        try (Connection conn = connectDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            String encryptedPassword = util.AESEncryption.encrypt(newPassword);
            stmt.setString(1, encryptedPassword);
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}