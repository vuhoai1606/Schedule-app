package controller;

import dao.AlarmDAO;
import dao.ScheduleDAO;
import view.CrudGUI;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.time.LocalDateTime;
import java.util.UUID;

public class Crud {
    private CrudGUI crudGUI;
    private BigCalendar calendarController; // Thêm tham chiếu đến BigCalendarController
    private String currentScheduleId; // Biến để lưu ID của lịch trình đang chỉnh sửa/xóa
    private boolean isToggleOn = true;
    private boolean isAllDayToggleOn = false;
    private Clip audioClip;
    private boolean isPlaying = false;

    public Crud(CrudGUI crudGUI) {
        this.crudGUI = crudGUI;
        setupEventListeners();
    }

    // Setter để gán BigCalendarController
    public void setCalendarController(BigCalendar calendarController) {
        this.calendarController = calendarController;
    }

    private void setupEventListeners() {
        crudGUI.getCreateEventButton().addActionListener(e -> onCreateEvent());
        crudGUI.getCloseLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) { onClose(); }
        });
        crudGUI.getStartDateLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) { onStartDateClick(); }
        });
        crudGUI.getEndDateLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) { onEndDateClick(); }
        });
        crudGUI.getAllDayToggleIcon().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) { toggleAllDay(); }
        });
        crudGUI.getAlarmToggleIcon().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) { toggleAlarm(); }
        });
        crudGUI.getPlayIcon().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) { playSound(); }
        });
        crudGUI.getAddButton().addActionListener(e -> onAdd());
        crudGUI.getUpdateButton().addActionListener(e -> onUpdate());
        crudGUI.getDeleteButton().addActionListener(e -> onDelete());
    }

    private void onCreateEvent() {
        crudGUI.getEventContentPanel().setVisible(true);
        crudGUI.getCreateEventButton().setVisible(false);
        currentScheduleId = null; // Reset ID khi tạo mới
    }

    private void onClose() {
        crudGUI.getEventContentPanel().setVisible(false);
        crudGUI.getCreateEventButton().setVisible(true);
        currentScheduleId = null; // Reset ID khi đóng
    }

    private void onStartDateClick() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(crudGUI), "Chọn ngày bắt đầu", true);
        dialog.add(new view.MiniCalendar(crudGUI.getStartDateLabel()));
        dialog.pack();
        dialog.setLocationRelativeTo(crudGUI.getStartDateLabel());
        dialog.setVisible(true);
    }

    private void onEndDateClick() {
        if (crudGUI.getEndDateLabel().isEnabled()) {
            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(crudGUI), "Chọn ngày kết thúc", true);
            dialog.add(new view.MiniCalendar(crudGUI.getEndDateLabel()));
            dialog.pack();
            dialog.setLocationRelativeTo(crudGUI.getEndDateLabel());
            dialog.setVisible(true);
        }
    }

    private void toggleAllDay() {
        isAllDayToggleOn = !isAllDayToggleOn;
        crudGUI.getAllDayToggleIcon().setIcon(new ImageIcon(isAllDayToggleOn ? "src/main/resources/toggle_on.png" : "src/main/resources/toggle_off.png"));
        boolean enabled = !isAllDayToggleOn;
        crudGUI.getEndDateLabel().setEnabled(enabled);
        crudGUI.getEndHour().setEnabled(enabled);
        crudGUI.getEndMinute().setEnabled(enabled);
        crudGUI.getEndSecond().setEnabled(enabled);
        Color bgColor = isAllDayToggleOn ? Color.LIGHT_GRAY : Color.WHITE;
        crudGUI.getEndDateLabel().setBackground(bgColor);
        crudGUI.getEndHour().setBackground(bgColor);
        crudGUI.getEndMinute().setBackground(bgColor);
        crudGUI.getEndSecond().setBackground(bgColor);
    }

    private void toggleAlarm() {
        isToggleOn = !isToggleOn;
        crudGUI.getAlarmToggleIcon().setIcon(new ImageIcon(isToggleOn ? "src/main/resources/toggle_on.png" : "src/main/resources/toggle_off.png"));
        crudGUI.getReminderBox().setEnabled(isToggleOn);
        crudGUI.getSoundComboBox().setEnabled(isToggleOn);
        crudGUI.getReminderBox().setBackground(isToggleOn ? Color.WHITE : Color.LIGHT_GRAY);
    }

    private void playSound() {
        CrudGUI.SoundItem selected = (CrudGUI.SoundItem) crudGUI.getSoundComboBox().getSelectedItem();
        if (selected == null || selected.getPath().equals("other")) return;

        if (!isPlaying) {
            playSound(selected.getPath());
        } else {
            stopSound();
        }
    }

    private void onAdd() {
        try {
            String title = crudGUI.getTitleField().getText();
            String description = crudGUI.getDescriptionArea().getText();
            String startDate = crudGUI.getStartDateLabel().getText();
            String startH = (String) crudGUI.getStartHour().getSelectedItem();
            String startM = (String) crudGUI.getStartMinute().getSelectedItem();
            String startS = (String) crudGUI.getStartSecond().getSelectedItem();
            LocalDateTime startTime = LocalDateTime.parse(startDate + "T" + startH + ":" + startM + ":" + startS);

            LocalDateTime endTime = isAllDayToggleOn
                    ? startTime.withHour(23).withMinute(59).withSecond(59)
                    : LocalDateTime.parse(crudGUI.getEndDateLabel().getText() + "T" + (String) crudGUI.getEndHour().getSelectedItem() + ":" +
                    (String) crudGUI.getEndMinute().getSelectedItem() + ":" + (String) crudGUI.getEndSecond().getSelectedItem());

            String repeatPattern = (String) crudGUI.getRepeatCombo().getSelectedItem();
            CrudGUI.ColorIcon selectedColor = (CrudGUI.ColorIcon) crudGUI.getColorComboBox().getSelectedItem();
            String color = selectedColor.getHex();

            String scheduleId = UUID.randomUUID().toString();
            boolean scheduleSuccess = ScheduleDAO.addSchedule(scheduleId, title, description, startTime, endTime, isAllDayToggleOn, repeatPattern, color);
            boolean alarmSuccess = AlarmDAO.addAlarm(scheduleId, isToggleOn,
                    isToggleOn ? startTime.minusMinutes(Integer.parseInt((String) crudGUI.getReminderBox().getSelectedItem())) : null,
                    isToggleOn ? ((CrudGUI.SoundItem) crudGUI.getSoundComboBox().getSelectedItem()).getPath() : null);

            if (scheduleSuccess && alarmSuccess) {
                JOptionPane.showMessageDialog(crudGUI, "Thêm lịch trình thành công!");
                if (calendarController != null) {
                    calendarController.refreshSchedules(); // Gọi refreshSchedules nếu calendarController đã được gán
                }
                onClose(); // Đóng form sau khi thêm thành công
            } else {
                JOptionPane.showMessageDialog(crudGUI, "Thêm lịch trình thất bại!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(crudGUI, "Đã xảy ra lỗi khi thêm lịch trình.");
        }
    }

    private void onUpdate() {
        try {
            if (currentScheduleId == null) {
                JOptionPane.showMessageDialog(crudGUI, "Không tìm thấy ID lịch trình để cập nhật!");
                return;
            }

            String title = crudGUI.getTitleField().getText();
            String description = crudGUI.getDescriptionArea().getText();
            String startDate = crudGUI.getStartDateLabel().getText();
            String startH = (String) crudGUI.getStartHour().getSelectedItem();
            String startM = (String) crudGUI.getStartMinute().getSelectedItem();
            String startS = (String) crudGUI.getStartSecond().getSelectedItem();
            LocalDateTime startTime = LocalDateTime.parse(startDate + "T" + startH + ":" + startM + ":" + startS);

            LocalDateTime endTime = isAllDayToggleOn
                    ? startTime.withHour(23).withMinute(59).withSecond(59)
                    : LocalDateTime.parse(crudGUI.getEndDateLabel().getText() + "T" + (String) crudGUI.getEndHour().getSelectedItem() + ":" +
                    (String) crudGUI.getEndMinute().getSelectedItem() + ":" + (String) crudGUI.getEndSecond().getSelectedItem());

            String repeatPattern = (String) crudGUI.getRepeatCombo().getSelectedItem();
            CrudGUI.ColorIcon selectedColor = (CrudGUI.ColorIcon) crudGUI.getColorComboBox().getSelectedItem();
            String color = selectedColor.getHex();

            boolean success = ScheduleDAO.updateSchedule(currentScheduleId, title, description, startTime, endTime, isAllDayToggleOn, repeatPattern, color);
            if (success) {
                JOptionPane.showMessageDialog(crudGUI, "Cập nhật lịch trình thành công!");
                if (calendarController != null) {
                    calendarController.refreshSchedules(); // Gọi refreshSchedules nếu calendarController đã được gán
                }
            } else {
                JOptionPane.showMessageDialog(crudGUI, "Cập nhật lịch trình thất bại!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(crudGUI, "Đã xảy ra lỗi khi cập nhật lịch trình.");
        }
    }

    private void onDelete() {
        try {
            if (currentScheduleId == null) {
                JOptionPane.showMessageDialog(crudGUI, "Không tìm thấy ID lịch trình để xóa!");
                return;
            }

            boolean success = ScheduleDAO.deleteSchedule(currentScheduleId);
            if (success) {
                JOptionPane.showMessageDialog(crudGUI, "Xóa lịch trình thành công!");
                crudGUI.getEventContentPanel().setVisible(false);
                crudGUI.getCreateEventButton().setVisible(true);
                if (calendarController != null) {
                    calendarController.refreshSchedules(); // Gọi refreshSchedules nếu calendarController đã được gán
                }
            } else {
                JOptionPane.showMessageDialog(crudGUI, "Xóa lịch trình thất bại!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(crudGUI, "Đã xảy ra lỗi khi xóa lịch trình.");
        }
    }

    private void playSound(String path) {
        try {
            stopSound();
            File soundFile = new File(path);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioIn);
            audioClip.start();
            isPlaying = true;
            crudGUI.getPlayIcon().setIcon(new ImageIcon("src/main/resources/pause_circle.png"));
            audioClip.addLineListener(event -> {
                if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP && isPlaying) {
                    isPlaying = false;
                    audioClip.close();
                    crudGUI.getPlayIcon().setIcon(new ImageIcon("src/main/resources/play_circle.png"));
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể phát âm thanh: " + path);
        }
    }

    private void stopSound() {
        if (audioClip != null && audioClip.isRunning()) {
            audioClip.stop();
            audioClip.close();
        }
        isPlaying = false;
        crudGUI.getPlayIcon().setIcon(new ImageIcon("src/main/resources/play_circle.png"));
    }

    // Phương thức để cập nhật currentScheduleId từ bên ngoài (sẽ được gọi từ BigCalendar)
    public void setCurrentScheduleId(String scheduleId) {
        this.currentScheduleId = scheduleId;
    }
}