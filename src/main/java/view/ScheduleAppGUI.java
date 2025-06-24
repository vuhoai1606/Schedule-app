package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.YearMonth;

import controller.BigCalendar;
import controller.Crud;
import model.CommonConstants;

public class ScheduleAppGUI extends JFrame {

//    private BigCalendarGUI calendarPanel; // Di chuyển thành biến instance

    public ScheduleAppGUI() {
        setTitle("Tên App");
        setSize(1440, 960);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // ------------------------- Thanh MENU -------------------------
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(CommonConstants.SECONDARY_COLOR);
        menuPanel.setPreferredSize(new Dimension(240, 960));
        menuPanel.setLayout(null);

        // Panel Side Bar
        JPanel sideBarPanel = new JPanel();
        sideBarPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        sideBarPanel.setBackground(CommonConstants.SECONDARY_COLOR);
        sideBarPanel.setBounds(10, 20, 80, 75);
        sideBarPanel.setLayout(null);
        // icon
        ImageIcon sideBar = new ImageIcon("src/main/resources/side_bar.png");
        JLabel sideBarIcon = new JLabel(sideBar);
        sideBarIcon.setBounds(0, 5, 80, 65);

        sideBarPanel.add(sideBarIcon);
        menuPanel.add(sideBarPanel);

        // Thêm Mini Calendar vào menuPanel
        MiniCalendarGUI miniCalendarPanel = new MiniCalendarGUI();
        miniCalendarPanel.setBounds(0, 115, 240, 200);
        menuPanel.add(miniCalendarPanel);

        // Panel Lịch Trình
        JPanel schedulePanel = new JPanel();
        schedulePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        schedulePanel.setBackground(CommonConstants.TERTIARY_COLOR);
        schedulePanel.setBounds(10, 360, 220, 70);
        schedulePanel.setLayout(null);
        // chữ
        JLabel scheduleLabel = new JLabel("Schedules");
        scheduleLabel.setForeground(CommonConstants.PRIMARY_COLOR);
        scheduleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        scheduleLabel.setBounds(100, 10, 100, 50);
        // icon
        ImageIcon schedule = new ImageIcon("src/main/resources/calendar.png");
        JLabel scheduleIcon = new JLabel(schedule);
        scheduleIcon.setBounds(5, 5, 80, 60);

        schedulePanel.add(scheduleLabel);
        schedulePanel.add(scheduleIcon);
        menuPanel.add(schedulePanel);

        // Panel Tin Nhắn
        JPanel messagePanel = new JPanel();
        messagePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        messagePanel.setBackground(CommonConstants.TERTIARY_COLOR);
        messagePanel.setBounds(10, 460, 220, 70);
        messagePanel.setLayout(null);
        // chữ
        JLabel messageLabel = new JLabel("Messages");
        messageLabel.setForeground(CommonConstants.PRIMARY_COLOR);
        messageLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        messageLabel.setBounds(100, 10, 100, 50);
        // icon
        ImageIcon message = new ImageIcon("src/main/resources/speech_bubble.png");
        JLabel messageIcon = new JLabel(message);
        messageIcon.setBounds(5, 5, 80, 60);

        messagePanel.add(messageLabel);
        messagePanel.add(messageIcon);
        menuPanel.add(messagePanel);

        // Panel Profile User
        JPanel profilePanel = new JPanel();
        profilePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profilePanel.setBackground(CommonConstants.SECONDARY_COLOR);
        profilePanel.setBounds(10, 830, 80, 75);
        profilePanel.setLayout(null);
        // icon
        ImageIcon profile = new ImageIcon("src/main/resources/profile_user.png");
        JLabel profileIcon = new JLabel(profile);
        profileIcon.setBounds(0, 5, 80, 65);

        profilePanel.add(profileIcon);
        menuPanel.add(profilePanel);

        add(menuPanel, BorderLayout.WEST);
        // ==================================================================

        // ------------------------- Phần NỘI DUNG -------------------------
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(CommonConstants.PRIMARY_COLOR);
//        contentPanel.setPreferredSize(new Dimension(1200, 960));
        contentPanel.setLayout(new BorderLayout());

        JPanel bigCalendarPanel = new JPanel();
        bigCalendarPanel.setBackground(CommonConstants.PRIMARY_COLOR);
        bigCalendarPanel.setPreferredSize(new Dimension(890, 960));
        bigCalendarPanel.setLayout(null);

        JPanel CrudPanel = new JPanel();
        CrudPanel.setBackground(CommonConstants.PRIMARY_COLOR);
        CrudPanel.setPreferredSize(new Dimension(310, 960));
        CrudPanel.setLayout(null);

        JPanel messagesPanel = new JPanel();
        messagesPanel.setBackground(CommonConstants.PRIMARY_COLOR);
        messagesPanel.setPreferredSize(new Dimension(310, 960));
        messagesPanel.setLayout(null);

        JPanel chatPanel = new JPanel();
        chatPanel.setBackground(CommonConstants.PRIMARY_COLOR);
        chatPanel.setPreferredSize(new Dimension(890, 960));
        chatPanel.setLayout(null);

        CrudGUI crudGui = new CrudGUI();
        crudGui.setBounds(0,0,310,960);
        Crud crudController = new Crud(crudGui);
        CrudPanel.add(crudGui);

        // Thêm BigCalendar controller
        BigCalendarGUI calendarPanel = new BigCalendarGUI();
        calendarPanel.setBounds(18, 70, 870, 850);
        BigCalendar bigCalendarController = new BigCalendar(calendarPanel, crudGui); // Thêm dòng này
        bigCalendarPanel.add(calendarPanel);

        // Today panel
        JPanel todayPanel = new JPanel();
        todayPanel.setBackground(CommonConstants.TERTIARY_COLOR);
        todayPanel.setBounds(700, 10, 70, 36);
        todayPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel todayLabel = new JLabel("Today");
        todayLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        todayPanel.add(todayLabel);
        todayPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                calendarPanel.currentYearMonth = YearMonth.now();
                calendarPanel.updateCalendar();
                miniCalendarPanel.currentDate = LocalDate.now(); // Cập nhật currentDate
                miniCalendarPanel.updateCalendar();
            }
        });
        bigCalendarPanel.add(todayPanel);

        // Prev Pabel
        JPanel prev = new JPanel();
        prev.setBackground(CommonConstants.PRIMARY_COLOR);
        prev.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        prev.setBounds(795, 13, 30, 30);
        prev.setLayout(new BorderLayout());
        prev.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                calendarPanel.currentYearMonth = calendarPanel.currentYearMonth.minusMonths(1);
                calendarPanel.updateCalendar();
            }
        });

        JLabel prevButton = new JLabel();
        prevButton.setIcon(new ImageIcon("src/main/resources/keyboard_arrow_up_30dp.png"));
        prev.add(prevButton, BorderLayout.CENTER);
        bigCalendarPanel.add(prev);

        // Next Panel
        JPanel next = new JPanel();
        next.setBackground(CommonConstants.PRIMARY_COLOR);
        next.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        next.setBounds(837, 13, 30, 30);
        next.setLayout(new BorderLayout());
        next.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                calendarPanel.currentYearMonth = calendarPanel.currentYearMonth.plusMonths(1);
                calendarPanel.updateCalendar();
            }
        });

        JLabel nextButton = new JLabel();
        nextButton.setIcon(new ImageIcon("src/main/resources/keyboard_arrow_down_30dp.png"));
        next.add(nextButton, BorderLayout.CENTER);
        bigCalendarPanel.add(next);

        contentPanel.add(bigCalendarPanel, BorderLayout.WEST);
        contentPanel.add(CrudPanel, BorderLayout.EAST);
        add(contentPanel, BorderLayout.EAST);

        // ==================================================================

    }
}