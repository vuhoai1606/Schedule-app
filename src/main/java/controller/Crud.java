package controller;

import dao.AlarmDAO;
import dao.ScheduleDAO;
import view.CrudGUI;
import model.CommonConstants;
import view.MiniCalendar;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import javax.sound.sampled.LineEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class Crud {

    private CrudGUI crudGUI;
    private boolean isToggleOn = true;
    private boolean isAllDayToggleOn = false;
    private Clip audioClip;
    private boolean isPlaying = false;

    public Crud(CrudGUI crudGUI) {
        this.crudGUI = crudGUI;

        // Nút tạo sự kiện
        JButton createEventButton = crudGUI.getCreateEventButton();
        JPanel eventContentPanel = crudGUI.getEventContentPanel();
        JLabel closeLabel = crudGUI.getCloseLabel();
        JLabel allDayToggleIcon = crudGUI.getAllDayToggleIcon();
        JLabel toggleIcon = crudGUI.getAlarmToggleIcon();
        JLabel playIcon = crudGUI.getPlayIcon();

        // Các thành phần liên quan đến thời gian kết thúc
        JLabel endDateLabel = crudGUI.getEndDateLabel();
        JComboBox<String> endHour = crudGUI.getEndHour();
        JComboBox<String> endMinute = crudGUI.getEndMinute();
        JComboBox<String> endSecond = crudGUI.getEndSecond();

        // Nhắc trước và âm thanh
        JComboBox<String> reminderBox = crudGUI.getReminderBox();
        JComboBox<CrudGUI.SoundItem> soundComboBox = crudGUI.getSoundComboBox();

        // Bắt sự kiện khi nhấn nút tạo sự kiện
        createEventButton.addActionListener(e -> {
            eventContentPanel.setVisible(true);
            createEventButton.setVisible(false);
            crudGUI.revalidate();
            crudGUI.repaint();
        });

        // Đóng popup khi bấm X
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                eventContentPanel.setVisible(false);
                createEventButton.setVisible(true);
                crudGUI.revalidate();
                crudGUI.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                closeLabel.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeLabel.setForeground(CommonConstants.TERTIARY_COLOR);
            }
        });

        // Chọn ngày bắt đầu
        crudGUI.getStartDateLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(crudGUI), "Chọn ngày bắt đầu", true);
                dialog.add(new MiniCalendar(crudGUI.getStartDateLabel()));
                dialog.pack();
                dialog.setLocationRelativeTo(crudGUI.getStartDateLabel());
                dialog.setVisible(true);
            }
        });

        // Chọn ngày kết thúc
        endDateLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (endDateLabel.isEnabled()) {
                    JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(crudGUI), "Chọn ngày kết thúc", true);
                    dialog.add(new MiniCalendar(endDateLabel));
                    dialog.pack();
                    dialog.setLocationRelativeTo(endDateLabel);
                    dialog.setVisible(true);
                }
            }
        });

        // Toggle all-day
        allDayToggleIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isAllDayToggleOn = !isAllDayToggleOn;
                allDayToggleIcon.setIcon(new ImageIcon(isAllDayToggleOn ? "src/main/resources/toggle_on.png" : "src/main/resources/toggle_off.png"));

                endDateLabel.setEnabled(!isAllDayToggleOn);
                endHour.setEnabled(!isAllDayToggleOn);
                endMinute.setEnabled(!isAllDayToggleOn);
                endSecond.setEnabled(!isAllDayToggleOn);

                Color bgColor = isAllDayToggleOn ? Color.LIGHT_GRAY : Color.WHITE;
                endDateLabel.setBackground(bgColor);
                endHour.setBackground(bgColor);
                endMinute.setBackground(bgColor);
                endSecond.setBackground(bgColor);

                crudGUI.revalidate();
                crudGUI.repaint();
            }
        });

        // Toggle âm thanh
        toggleIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isToggleOn = !isToggleOn;
                toggleIcon.setIcon(new ImageIcon(isToggleOn ? "src/main/resources/toggle_on.png" : "src/main/resources/toggle_off.png"));

                crudGUI.getReminderBox().setEnabled(isToggleOn);
                crudGUI.getSoundComboBox().setEnabled(isToggleOn);
                crudGUI.getReminderBox().setBackground(isToggleOn ? Color.WHITE : Color.LIGHT_GRAY);

                crudGUI.revalidate();
                crudGUI.repaint();
            }
        });

        // Icon play âm thanh
        playIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CrudGUI.SoundItem selected = (CrudGUI.SoundItem) crudGUI.getSoundComboBox().getSelectedItem();
                if (selected == null || selected.getPath().equals("other")) return;

                if (!isPlaying) {
                    playSound(selected.getPath());
                } else {
                    stopSound();
                }
            }
        });

        // Nút thêm
        crudGUI.getAddButton().addActionListener(e -> {
            try {
                String title = crudGUI.getTitleField().getText();
                String description = crudGUI.getDescriptionArea().getText();
                String startDate = crudGUI.getStartDateLabel().getText();
                String startH = (String) crudGUI.getStartHour().getSelectedItem();
                String startM = (String) crudGUI.getStartMinute().getSelectedItem();
                String startS = (String) crudGUI.getStartSecond().getSelectedItem();
                LocalDateTime startTime = LocalDateTime.parse(startDate + "T" + startH + ":" + startM + ":" + startS);

                LocalDateTime endTime;
                if (isAllDayToggleOn) {
                    endTime = startTime.withHour(23).withMinute(59).withSecond(59);
                } else {
                    String endDate = crudGUI.getEndDateLabel().getText();
                    String endH = (String) crudGUI.getEndHour().getSelectedItem();
                    String endM = (String) crudGUI.getEndMinute().getSelectedItem();
                    String endS = (String) crudGUI.getEndSecond().getSelectedItem();
                    endTime = LocalDateTime.parse(endDate + "T" + endH + ":" + endM + ":" + endS);
                }

                String repeatPattern = (String) crudGUI.getRepeatCombo().getSelectedItem();
                CrudGUI.ColorIcon selectedColor = (CrudGUI.ColorIcon) crudGUI.getColorComboBox().getSelectedItem();
                String color = selectedColor.getHex();

                // 1. Tạo scheduleId trước để dùng cho cả 2 bảng
                String scheduleId = UUID.randomUUID().toString();

                // 2. Thêm schedule
                boolean scheduleSuccess = ScheduleDAO.addSchedule(
                        scheduleId,
                        title,
                        description,
                        startTime,
                        endTime,
                        isAllDayToggleOn,
                        repeatPattern,
                        color
                );

                // 3. Thêm alarm ngay cả khi toggle OFF (is_active = false, alarmTime & soundURL = null)
                boolean alarmSuccess = AlarmDAO.addAlarm(
                        scheduleId,
                        isToggleOn,
                        isToggleOn ? startTime.minusMinutes(Integer.parseInt((String) crudGUI.getReminderBox().getSelectedItem())) : null,
                        isToggleOn ? ((CrudGUI.SoundItem) crudGUI.getSoundComboBox().getSelectedItem()).getPath() : null
                );

                if (scheduleSuccess && alarmSuccess) {
                    JOptionPane.showMessageDialog(crudGUI, "Thêm lịch trình thành công!");
                } else {
                    JOptionPane.showMessageDialog(crudGUI, "Thêm lịch trình thất bại!");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(crudGUI, "Đã xảy ra lỗi khi thêm lịch trình.");
            }
        });

        // Inside Crud.java, add these action listeners after the addButton listener
        crudGUI.getUpdateButton().addActionListener(e -> {
            try {
                String scheduleId = UUID.randomUUID().toString(); // Generate new ID if editing a repeated instance
                String title = crudGUI.getTitleField().getText();
                String description = crudGUI.getDescriptionArea().getText();
                String startDate = crudGUI.getStartDateLabel().getText();
                String startH = (String) crudGUI.getStartHour().getSelectedItem();
                String startM = (String) crudGUI.getStartMinute().getSelectedItem();
                String startS = (String) crudGUI.getStartSecond().getSelectedItem();
                LocalDateTime startTime = LocalDateTime.parse(startDate + "T" + startH + ":" + startM + ":" + startS);

                LocalDateTime endTime;
                if (isAllDayToggleOn) {
                    endTime = startTime.withHour(23).withMinute(59).withSecond(59);
                } else {
                    String endDate = crudGUI.getEndDateLabel().getText();
                    String endH = (String) crudGUI.getEndHour().getSelectedItem();
                    String endM = (String) crudGUI.getEndMinute().getSelectedItem();
                    String endS = (String) crudGUI.getEndSecond().getSelectedItem();
                    endTime = LocalDateTime.parse(endDate + "T" + endH + ":" + endM + ":" + endS);
                }

                String repeatPattern = (String) crudGUI.getRepeatCombo().getSelectedItem();
                CrudGUI.ColorIcon selectedColor = (CrudGUI.ColorIcon) crudGUI.getColorComboBox().getSelectedItem();
                String color = selectedColor.getHex();

                // Update existing schedule or create override if it's a repeated instance
                boolean success = ScheduleDAO.updateSchedule(scheduleId, title, description, startTime, endTime, isAllDayToggleOn, repeatPattern, color);
                if (success) {
                    JOptionPane.showMessageDialog(crudGUI, "Cập nhật lịch trình thành công!");
                } else {
                    JOptionPane.showMessageDialog(crudGUI, "Cập nhật lịch trình thất bại!");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(crudGUI, "Đã xảy ra lỗi khi cập nhật lịch trình.");
            }
        });

        crudGUI.getDeleteButton().addActionListener(e -> {
            try {
                // Assume scheduleId is stored somewhere (e.g., in a selectedScheduleId variable)
                String scheduleId = "some_schedule_id"; // Replace with actual ID retrieval logic
                boolean success = ScheduleDAO.deleteSchedule(scheduleId);
                if (success) {
                    JOptionPane.showMessageDialog(crudGUI, "Xóa lịch trình thành công!");
                    crudGUI.getEventContentPanel().setVisible(false);
                    crudGUI.getCreateEventButton().setVisible(true);
                    crudGUI.revalidate();
                    crudGUI.repaint();
                } else {
                    JOptionPane.showMessageDialog(crudGUI, "Xóa lịch trình thất bại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(crudGUI, "Đã xảy ra lỗi khi xóa lịch trình.");
            }
        });

    }

    private void playSound(String path) {
        try {
            stopSound(); // Dừng âm thanh đang phát (nếu có)

            File soundFile = new File(path);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioIn);
            audioClip.start();

            isPlaying = true;
            crudGUI.getPlayIcon().setIcon(new ImageIcon("src/main/resources/pause_circle.png"));

            // Khi phát xong thì tự chuyển về play icon
            audioClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP && isPlaying) {
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
}