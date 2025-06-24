package controller;

import dao.ScheduleDAO;
import view.BigCalendarGUI;
import view.CrudGUI;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class BigCalendar {
    private BigCalendarGUI bigCalendarGUI;
    private CrudGUI crudGUI;
    private Map<LocalDate, List<ScheduleDAO.ScheduleData>> scheduleMap = new HashMap<>();
    private Crud crudController; // Thêm tham chiếu đến Crud

    public BigCalendar(BigCalendarGUI bigCalendarGUI, CrudGUI crudGUI) {
        this.bigCalendarGUI = bigCalendarGUI;
        this.crudGUI = crudGUI;
        this.crudController = new Crud(crudGUI); // Khởi tạo Crud
        crudController.setCalendarController(this); // Gán BigCalendar vào Crud
        bigCalendarGUI.setController(this);
        loadSchedulesFromDatabase();
        updateSchedulePanels();
    }

    private void loadSchedulesFromDatabase() {
        List<ScheduleDAO.ScheduleData> schedules = ScheduleDAO.getAllSchedules();
        scheduleMap.clear();
        for (ScheduleDAO.ScheduleData schedule : schedules) {
            LocalDate startDate = schedule.startTime.toLocalDate();
            scheduleMap.computeIfAbsent(startDate, k -> new ArrayList<>()).add(schedule);
        }
    }

    public void updateSchedulePanels() {
        for (LocalDate date : bigCalendarGUI.dayCellMap.keySet()) {
            bigCalendarGUI.clearSchedulePanels(date);
            List<ScheduleDAO.ScheduleData> schedules = scheduleMap.getOrDefault(date, new ArrayList<>());
            for (ScheduleDAO.ScheduleData schedule : schedules) {
                JPanel schedulePanel = new JPanel();
                schedulePanel.setPreferredSize(new Dimension(80, 20));
                bigCalendarGUI.renderSchedulePanel(date, schedule.title, schedule.color, schedulePanel);
                schedulePanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        onScheduleClick(schedule, schedulePanel);
                    }
                });
            }
        }
    }

    public void onDateDoubleClick(LocalDate date) {
        crudGUI.getEventContentPanel().setVisible(true);
        crudGUI.getCreateEventButton().setVisible(false);
        crudGUI.getStartDateLabel().setText(date.toString());
        crudGUI.revalidate();
        crudGUI.repaint();
    }

    public void onScheduleClick(ScheduleDAO.ScheduleData schedule, JPanel schedulePanel) {
        crudGUI.getTitleField().setText(schedule.title);
        crudGUI.getStartDateLabel().setText(schedule.startTime.toLocalDate().toString());
        crudGUI.getEndDateLabel().setText(schedule.endTime.toLocalDate().toString());
        crudGUI.getDescriptionArea().setText(schedule.description != null ? schedule.description : "");
        crudGUI.getColorComboBox().setSelectedItem(new CrudGUI.ColorIcon(Color.decode(schedule.color), ""));
        crudGUI.getEventContentPanel().setVisible(true);
        crudGUI.getCreateEventButton().setVisible(false);
        crudGUI.revalidate();
        crudGUI.repaint();
        if (crudController != null) {
            crudController.setCurrentScheduleId(schedule.id); // Truyền ID của lịch trình
        }
    }

    public void refreshSchedules() {
        loadSchedulesFromDatabase();
        updateSchedulePanels();
    }
}