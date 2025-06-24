package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ScheduleDAO {

    private static String userId = controller.login.currentUserId;

    public static boolean addSchedule(String scheduleId, String title, String description, LocalDateTime startTime, LocalDateTime endTime, boolean isAllDay, String repeatPattern, String color) {
        String insertQuery = "INSERT INTO schedules (id, user_id, title, description, start_time, end_time, is_all_day, repeat_pattern, color) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connectDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setString(1, scheduleId);
            stmt.setString(2, userId); // đảm bảo userId được gán trước
            stmt.setString(3, title);
            stmt.setString(4, description);
            stmt.setObject(5, startTime);
            stmt.setObject(6, endTime);
            stmt.setBoolean(7, isAllDay);
            stmt.setString(8, repeatPattern);
            stmt.setString(9, color);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean updateSchedule(String scheduleId, String title, String description, LocalDateTime startTime, LocalDateTime endTime, boolean isAllDay, String repeatPattern, String color) {
        String updateQuery = "UPDATE schedules SET title = ?, description = ?, start_time = ?, end_time = ?, is_all_day = ?, repeat_pattern = ?, color = ? WHERE id = ?";
        try (Connection conn = connectDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setObject(3, startTime);
            stmt.setObject(4, endTime);
            stmt.setBoolean(5, isAllDay);
            stmt.setString(6, repeatPattern);
            stmt.setString(7, color);
            stmt.setString(8, scheduleId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteSchedule(String scheduleId) {
        String deleteQuery = "DELETE FROM schedules WHERE id = ?";
        try (Connection conn = connectDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            stmt.setString(1, scheduleId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Logic để tạo bản ghi riêng lẻ khi chỉnh sửa instance lặp
    public static boolean createOverrideSchedule(String scheduleId, String title, String description, LocalDateTime startTime, LocalDateTime endTime, boolean isAllDay, String repeatPattern, String color) {
        String insertQuery = "INSERT INTO schedules (id, user_id, title, description, start_time, end_time, is_all_day, repeat_pattern, color) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connectDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setString(1, scheduleId);
            stmt.setString(2, userId); // đảm bảo userId được gán trước
            stmt.setString(3, title);
            stmt.setString(4, description);
            stmt.setObject(5, startTime);
            stmt.setObject(6, endTime);
            stmt.setBoolean(7, isAllDay);
            stmt.setString(8, repeatPattern);
            stmt.setString(9, color);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Thêm phương thức để lấy tất cả lịch trình của người dùng
    public static List<ScheduleData> getAllSchedules() {
        List<ScheduleData> schedules = new ArrayList<>();
        String selectQuery = "SELECT id, title, start_time, end_time, color, repeat_pattern FROM schedules WHERE user_id = ?";
        try (Connection conn = connectDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(selectQuery)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                schedules.add(new ScheduleData(
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getObject("start_time", LocalDateTime.class),
                        rs.getObject("end_time", LocalDateTime.class),
                        rs.getString("color"),
                        rs.getString("repeat_pattern")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    // Lớp dữ liệu để chứa thông tin lịch trình
    public static class ScheduleData {
        public String id;
        public String title;
        public LocalDateTime startTime;
        public LocalDateTime endTime;
        public String color;
        public String repeatPattern;

        public ScheduleData(String id, String title, LocalDateTime startTime, LocalDateTime endTime, String color, String repeatPattern) {
            this.id = id;
            this.title = title;
            this.startTime = startTime;
            this.endTime = endTime;
            this.color = color;
            this.repeatPattern = repeatPattern;
        }
    }
}