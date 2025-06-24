package view;

import model.CommonConstants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.*;
import java.time.temporal.WeekFields;

public class MiniCalendarGUI extends JPanel {
    public YearMonth currentYearMonth;
    private JLabel monthYearLabel;
    private JPanel calendarGrid;
    public LocalDate currentDate;
    private JLabel undoIcon;

    public MiniCalendarGUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 270));
        setBackground(CommonConstants.SECONDARY_COLOR);

        currentYearMonth = YearMonth.now();

        currentDate = LocalDate.now();

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CommonConstants.SECONDARY_COLOR);

        JButton prevButton = new JButton("<");
        prevButton.setFocusPainted(false);
        prevButton.setBorderPainted(false);
        prevButton.setContentAreaFilled(false);
        prevButton.setForeground(Color.WHITE);
        prevButton.addActionListener(e -> {
            currentDate = currentDate.minusMonths(1);
            updateCalendar();
        });

        JButton nextButton = new JButton(">");
        nextButton.setFocusPainted(false);
        nextButton.setBorderPainted(false);
        nextButton.setContentAreaFilled(false);
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(e -> {
            currentDate = currentDate.plusMonths(1);
            updateCalendar();
        });

        monthYearLabel = new JLabel("", SwingConstants.CENTER);
        monthYearLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        monthYearLabel.setForeground(Color.WHITE);

        undoIcon = new JLabel(new ImageIcon("src/main/resources/undo.png"));
        undoIcon.setVisible(false); // Ẩn ban đầu
        undoIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        undoIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentDate = LocalDate.now();
                updateCalendar();
            }
        });

        // Tạo một panel con để chứa monthYearLabel và undoIcon
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(CommonConstants.SECONDARY_COLOR);
        centerPanel.add(monthYearLabel, BorderLayout.CENTER);
        centerPanel.add(undoIcon, BorderLayout.EAST);

        header.add(prevButton, BorderLayout.WEST);
        header.add(centerPanel, BorderLayout.CENTER);
        header.add(nextButton, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // Calendar grid
        calendarGrid = new JPanel(new GridLayout(7, 8));
        calendarGrid.setBackground(CommonConstants.SECONDARY_COLOR);
        add(calendarGrid, BorderLayout.CENTER);

        updateCalendar();
    }

    public void updateCalendar() {
        calendarGrid.removeAll();

        // Add header (first row)
        calendarGrid.add(new JLabel(""));

        String[] days = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
        for (String day : days) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
            dayLabel.setForeground(CommonConstants.TERTIARY_COLOR);
            calendarGrid.add(dayLabel);
        }

        YearMonth yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonth());
        LocalDate firstOfMonth = currentDate.withDayOfMonth(1);
        int startDay = firstOfMonth.getDayOfWeek().getValue();
        if (startDay == 7) startDay = 0;

        LocalDate startDate = firstOfMonth.minusDays(startDay);
        LocalDate today = LocalDate.now();

        for (int week = 0; week < 6; week++) {
            LocalDate firstDayOfWeek = startDate.plusDays(week * 7L);
            int weekNumber = firstDayOfWeek.get(WeekFields.ISO.weekOfYear());

            JLabel weekLabel = new JLabel(String.valueOf(weekNumber), SwingConstants.CENTER);
            weekLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
            weekLabel.setForeground(new Color(200, 200, 200));
            calendarGrid.add(weekLabel);

            for (int dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++) {
                LocalDate thisDay = startDate.plusDays(week * 7L + dayOfWeek);
                boolean isCurrentMonth = thisDay.getMonth().equals(currentDate.getMonth());

                JLabel dayLabel = new JLabel(String.valueOf(thisDay.getDayOfMonth()), SwingConstants.CENTER);
                dayLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
                dayLabel.setForeground(isCurrentMonth ? Color.WHITE : Color.LIGHT_GRAY);

                if (thisDay.isEqual(today)) {
                    dayLabel.setOpaque(true);
                    dayLabel.setBackground(Color.decode("#f15550"));
                    dayLabel.setForeground(Color.WHITE);
                }

                calendarGrid.add(dayLabel);
            }
        }

        // Cập nhật hiển thị icon undo
        YearMonth currentMonth = YearMonth.now();
        undoIcon.setVisible(!currentDate.getMonth().equals(currentMonth.getMonth()) || currentDate.getYear() != currentMonth.getYear());
        // Cập nhật hiển thị monthYearLabel với tên tháng rút gọn còn 3 ký tự
        monthYearLabel.setText(currentDate.getMonth().toString().substring(0, 3).toUpperCase() + " " + currentDate.getYear());
        revalidate();
        repaint();
    }
}
