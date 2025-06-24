package controller;

import dao.ScheduleDAO;
import view.BigCalendarGUI;
import view.CrudGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;

public class BigCalendar {
    private BigCalendarGUI bigCalendarGUI;
    private CrudGUI crudGUI;
    private final int MAX_DAYS = 365;
    private Map<LocalDate, List<ScheduleDAO.ScheduleData>> scheduleMap = new HashMap<>();

    public BigCalendar(BigCalendarGUI bigCalendarGUI, CrudGUI crudGUI) {
        this.bigCalendarGUI = bigCalendarGUI;
        this.crudGUI = crudGUI;
        bigCalendarGUI.setBigCalendarController(this); // Gán controller cho GUI

        // Thêm MouseListener cho từng ô ngày trong BigCalendarGUI
        for (LocalDate date : bigCalendarGUI.dayCellMap.keySet()) {
            bigCalendarGUI.dayCellMap.get(date).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        LocalDate selectedDate = bigCalendarGUI.dayCellMap.get(date).getDate();
                        crudGUI.getStartDateLabel().setText(selectedDate.toString());
                        JPanel eventContentPanel = (JPanel) crudGUI.getComponent(1);
                        JButton createEventButton = (JButton) crudGUI.getComponent(0);
                        eventContentPanel.setVisible(true);
                        createEventButton.setVisible(false);
                        crudGUI.revalidate();
                        crudGUI.repaint();
                    }
                }
            });
        }

        generateRepeatedSchedules();
        updateSchedulesDisplay();

    }

    private void generateRepeatedSchedules() {
        List<ScheduleDAO.ScheduleData> schedules = ScheduleDAO.getAllSchedules();
        LocalDate oneYearLater = LocalDate.now().plusYears(1);
        scheduleMap.clear();

        for (ScheduleDAO.ScheduleData schedule : schedules) {
            LocalDate startDate = schedule.startTime.toLocalDate();
            LocalDate endDate = schedule.endTime.toLocalDate();
            String repeatPattern = schedule.repeatPattern;

            if ("none".equals(repeatPattern)) {
                scheduleMap.computeIfAbsent(startDate, k -> new ArrayList<>()).add(schedule);
            } else {
                LocalDate currentDate = startDate;
                while (!currentDate.isAfter(oneYearLater)) {
                    scheduleMap.computeIfAbsent(currentDate, k -> new ArrayList<>()).add(schedule);
                    if ("daily".equals(repeatPattern)) {
                        currentDate = currentDate.plusDays(1);
                    } else if ("weekly".equals(repeatPattern)) {
                        currentDate = currentDate.plusWeeks(1);
                    } else if ("monthly".equals(repeatPattern)) {
                        currentDate = currentDate.plusMonths(1);
                    } else if ("yearly".equals(repeatPattern)) {
                        currentDate = currentDate.plusYears(1);
                        break; // Yearly repeats only once for simplicity
                    }
                }
            }
        }
    }

    public Map<LocalDate, List<ScheduleDAO.ScheduleData>> getScheduleMap() {
        return scheduleMap;
    }

    // Cập nhật hiển thị schedules
    public void updateSchedulesDisplay() {
        List<ScheduleDAO.ScheduleData> allSchedules = new ArrayList<>();
        for (List<ScheduleDAO.ScheduleData> schedules : scheduleMap.values()) {
            allSchedules.addAll(schedules);
        }
        bigCalendarGUI.renderSchedules(allSchedules);
    }

    // Xử lý sự kiện khi click vào schedule panel
    public void onScheduleClick(ScheduleDAO.ScheduleData schedule, JPanel schedulePanel, JLabel titleLabel) {
        // Populate CrudGUI fields
        crudGUI.getTitleField().setText(schedule.title);
        crudGUI.getStartDateLabel().setText(schedule.startTime.toLocalDate().toString());
        crudGUI.getEndDateLabel().setText(schedule.endTime.toLocalDate().toString());
        crudGUI.getDescriptionArea().setText(""); // Thêm description nếu có trong tương lai
        crudGUI.getColorComboBox().setSelectedItem(new CrudGUI.ColorIcon(Color.decode(schedule.color), ""));
        crudGUI.getEventContentPanel().setVisible(true);
        crudGUI.getCreateEventButton().setVisible(false);

        // Highlight selected panel
        LocalDate startDate = schedule.startTime.toLocalDate();
        LocalDate endDate = schedule.endTime.toLocalDate();
        schedulePanel.setBackground(Color.decode(schedule.color));
        titleLabel.setForeground(Color.WHITE);

        // Reset on close
        crudGUI.getCloseLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                schedulePanel.setBackground(startDate.isEqual(endDate) ? new Color(0, 0, 0, 0) : Color.WHITE);
                titleLabel.setForeground(Color.decode(schedule.color));
                crudGUI.revalidate();
                crudGUI.repaint();
            }
        });
    }
}