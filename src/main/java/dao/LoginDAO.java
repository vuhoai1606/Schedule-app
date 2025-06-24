package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    public static String validateLogin(String username, String password) {
        String query = "SELECT id, password_hash FROM users WHERE username = ?";
        String userId = null;

        try (Connection conn = connectDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String encryptedPassword = rs.getString("password_hash");
                String decryptedPassword = util.AESEncryption.decrypt(encryptedPassword);
                if (password.equals(decryptedPassword)) {
                    userId = rs.getString("id"); // Lưu user_id
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId; // Trả về userId nếu thành công, null nếu thất bại
    }
}