package model;

public class checkStrongPass {
    // Hàm kiểm tra mật khẩu mạnh
    public static boolean isPasswordStrong(String password) {
        if (password.length() < 6) return false;
        if (!password.matches(".*[a-z].*")) return false;                       // ít nhất 1 chữ thường
        if (!password.matches(".*[A-Z].*")) return false;                       // ít nhất 1 chữ HOA
        if (!password.matches(".*[!@#$%^&*()\\-_=+{};:,<.>].*")) return false;  // ít nhất 1 ký tự đặc biệt
        if (!password.matches(".*\\d.*")) return false;                         // ít nhất 1 số
        return true;
    }
}
