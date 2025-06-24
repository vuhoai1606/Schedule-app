package view;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.*;

import controller.BigCalendar;
import model.CommonConstants;

public class BigCalendarGUI extends JPanel {
    public YearMonth currentYearMonth;
    private JPanel daysPanel;
    private JLabel monthYearLabel;
    public final Map<LocalDate, DayCellPanel> dayCellMap = new HashMap<>();
    private BigCalendar controller;

    public BigCalendarGUI() {
        setLayout(new BorderLayout());
        setBackground(CommonConstants.PRIMARY_COLOR);

        currentYearMonth = YearMonth.now();

        // Header navigation
        JPanel navPanel = new JPanel(new BorderLayout());
        navPanel.setBackground(CommonConstants.PRIMARY_COLOR);

        monthYearLabel = new JLabel("", SwingConstants.CENTER);
        monthYearLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        monthYearLabel.setForeground(Color.WHITE);

        navPanel.add(monthYearLabel, BorderLayout.CENTER);

        add(navPanel, BorderLayout.NORTH);

        // Combine header and days panel
        JPanel calendarPanel = new JPanel(new GridBagLayout());
        calendarPanel.setBackground(CommonConstants.PRIMARY_COLOR);
        add(calendarPanel, BorderLayout.CENTER);

        // Header row (W, Sun, Mon, ...)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;
        gbc.weighty = 0;

        for (int i = 0; i < 8; i++) {
            gbc.gridx = i;
            gbc.weightx = (i == 0) ? 0 : 1;

            JLabel label;
            if (i == 0) {
                label = new JLabel("W", SwingConstants.CENTER);
                label.setPreferredSize(new Dimension(20, 20));
            } else {
                String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
                label = new JLabel(days[i - 1], SwingConstants.CENTER);
                label.setPreferredSize(new Dimension(100, 20));
            }
            label.setFont(new Font("Tahoma", Font.BOLD, 14));
            label.setForeground(CommonConstants.PRIMARY_COLOR);
            label.setBackground(CommonConstants.TERTIARY_COLOR);
            label.setOpaque(true);

            calendarPanel.add(label, gbc);
        }

        // Days panel
        daysPanel = new JPanel(new GridBagLayout());
        daysPanel.setBackground(CommonConstants.PRIMARY_COLOR);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 8;
        gbc.weightx = 1;
        gbc.weighty = 1;
        calendarPanel.add(daysPanel, gbc);

        updateCalendar();
    }

    public void setController(BigCalendar controller) {
        this.controller = controller;
    }

    public void updateCalendar() {
        daysPanel.removeAll();
        dayCellMap.clear();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;

        LocalDate firstOfMonth = currentYearMonth.atDay(1);
        int startDayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        int offset = startDayOfWeek % 7;
        LocalDate startDate = firstOfMonth.minusDays(offset);

        for (int week = 0; week < 6; week++) {
            LocalDate weekDate = startDate.plusDays(week * 7L);
            int weekNumber = weekDate.get(WeekFields.ISO.weekOfYear());
            gbc.gridx = 0;
            gbc.gridy = week;
            gbc.weightx = 0;
            JLabel weekLabel = new JLabel(String.valueOf(weekNumber), SwingConstants.CENTER);
            weekLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
            weekLabel.setForeground(Color.LIGHT_GRAY);
            weekLabel.setPreferredSize(new Dimension(20, 80));
            daysPanel.add(weekLabel, gbc);

            for (int day = 0; day < 7; day++) {
                LocalDate date = startDate.plusDays(week * 7L + day);
                DayCellPanel dayCell = new DayCellPanel(date, currentYearMonth);
                gbc.gridx = day + 1;
                gbc.weightx = 1;
                daysPanel.add(dayCell, gbc);
                dayCellMap.put(date, dayCell);
                dayCell.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        if (e.getClickCount() == 2 && controller != null) {
                            controller.onDateDoubleClick(date);
                        }
                    }
                });
            }
        }

        monthYearLabel.setText(currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentYearMonth.getYear());

        if (controller != null) {
            controller.updateSchedulePanels();
        }

        revalidate();
        repaint();
    }

    public void renderSchedulePanel(LocalDate date, String title, String color, JPanel panel) {
        DayCellPanel dayCell = dayCellMap.get(date);
        if (dayCell != null) {
            panel.setBackground(Color.decode(color));
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JLabel titleLabel = new JLabel(title);
            titleLabel.setForeground(Color.WHITE);
            panel.add(titleLabel);
            dayCell.add(panel);
            revalidate();
            repaint();
        }
    }

    public void clearSchedulePanels(LocalDate date) {
        DayCellPanel dayCell = dayCellMap.get(date);
        if (dayCell != null) {
            dayCell.removeAll();
            revalidate();
            repaint();
        }
    }

    public class DayCellPanel extends JPanel {
        private LocalDate date;
        private boolean isCurrentMonth;

        public DayCellPanel(LocalDate date, YearMonth currentMonth) {
            this.date = date;
            setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
            setBackground(CommonConstants.PRIMARY_COLOR);
            setBorder(BorderFactory.createLineBorder(Color.GRAY));

            isCurrentMonth = date.getMonth().equals(currentMonth.getMonth());
            boolean isToday = date.equals(LocalDate.now());

            String dayText = String.valueOf(date.getDayOfMonth());
            if (date.getDayOfMonth() == 1) {
                dayText = date.getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault()) + " " + dayText;
            }

            JLabel dayLabel = new JLabel(dayText);
            dayLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
            dayLabel.setForeground(isCurrentMonth ? Color.WHITE : Color.LIGHT_GRAY);
            dayLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            if (isToday) {
                dayLabel.setOpaque(true);
                dayLabel.setBackground(Color.decode("#f15550"));
                dayLabel.setForeground(Color.WHITE);
            }
            add(dayLabel);
        }

        public LocalDate getDate() {
            return date;
        }

        public boolean isCurrentMonth() {
            return isCurrentMonth;
        }
    }
}