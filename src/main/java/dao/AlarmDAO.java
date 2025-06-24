package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AlarmDAO {
    private static final String[] SOUND_PATHS = {
            "src/main/resources/pip_pip.wav",
            "src/main/resources/reng_reng.wav",
            "src/main/resources/iphone_alarm.wav",
            "src/main/resources/samsung_alarm_1.wav",
            "src/main/resources/samsung_alarm_2.wav",
            "other"
    };

    public static boolean addAlarm(String scheduleId, boolean isActive, LocalDateTime alarmTime, String soundUrl) {
        String insertQuery = "INSERT INTO alarms (schedule_id, is_active, alarm_time, sound_url) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setString(1, scheduleId);
            stmt.setBoolean(2, isActive);
            stmt.setObject(3, alarmTime);
            stmt.setString(4, soundUrl);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateAlarm(String alarmId, boolean isActive, LocalDateTime alarmTime, String soundUrl) {
        String updateQuery = "UPDATE alarms SET is_active = ?, alarm_time = ?, sound_url = ? WHERE id = ?";
        try (Connection conn = connectDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setBoolean(1, isActive);
            stmt.setObject(2, alarmTime);
            stmt.setString(3, soundUrl);
            stmt.setString(4, alarmId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteAlarm(String alarmId) {
        String deleteQuery = "DELETE FROM alarms WHERE id = ?";
        try (Connection conn = connectDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            stmt.setString(1, alarmId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}