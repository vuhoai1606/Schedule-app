package view;

import javax.swing.*;
import java.awt.*;

import model.CommonConstants;

public class CrudGUI extends JPanel {

    private JButton createEventButton;
    private JLabel closeLabel;
    private JPanel eventContentPanel;

    private JTextField titleField;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JComboBox<String> startHour;
    private JComboBox<String> startMinute;
    private JComboBox<String> startSecond;
    private JComboBox<String> endHour;
    private JComboBox<String> endMinute;
    private JComboBox<String> endSecond;
    private JLabel allDayToggleIcon;
    private JComboBox<String> repeatCombo;
    private JLabel alarmToggleIcon;
    private JComboBox<String> reminderBox;
    private JComboBox<SoundItem> soundComboBox;
    private JLabel playIcon;
    private JComboBox<ColorIcon> colorComboBox;
    private JTextArea descriptionArea;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public CrudGUI() {
        setPreferredSize(new Dimension(310, 960));
        setBackground(CommonConstants.SECONDARY_COLOR);
        setLayout(null);

        createEventButton = new JButton("+ Create Event");
        createEventButton.setFont(new Font("Tahoma", Font.BOLD, 23));
        createEventButton.setForeground(CommonConstants.PRIMARY_COLOR);
        createEventButton.setBackground(CommonConstants.TERTIARY_COLOR);
        createEventButton.setBounds(40, 80, 230, 80);
        createEventButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createEventButton.setFocusPainted(false);
        createEventButton.setBorderPainted(false);

        add(createEventButton);

        // các trường để nhập dữ liệu
        eventContentPanel = new JPanel(null);
        eventContentPanel.setBounds(0, 0, 310, 960);
        eventContentPanel.setBackground(CommonConstants.SECONDARY_COLOR);

        int labelWidth = 80;
        int fieldWidth = 270;
        int height = 25;
        int padding = 10;
        int y = 20; // Giá trị y ban đầu

        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        Font titleFont = new Font("Arial", Font.BOLD, 20);

        // Thêm nút X để đóng eventContentPanel
        closeLabel = new JLabel("X");
        closeLabel.setFont(labelFont);
        closeLabel.setForeground(CommonConstants.TERTIARY_COLOR);
        closeLabel.setBounds(275, 10, 20, 20);
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        eventContentPanel.add(closeLabel);

        // Tên sự kiện
        y += 20;// Dịch chuyển "Tên sự kiện" xuống thêm 20
        JLabel titleLabel = new JLabel("Event:");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(20, y, fieldWidth, height);
        eventContentPanel.add(titleLabel);

        y += height + 5;
        titleField = new JTextField();
        titleField.setFont(titleFont);
        titleField.setBounds(20, y, fieldWidth, height);
        eventContentPanel.add(titleField);

        y += height + 15;

        // Bắt đầu
        JLabel startLabel = new JLabel("Start:");
        startLabel.setFont(labelFont);
        startLabel.setForeground(Color.WHITE);
        startLabel.setBounds(20, y, fieldWidth, height);
        eventContentPanel.add(startLabel);

        y += height + 5;
        startDateLabel = new JLabel("YYYY-MM-DD");
        startDateLabel.setFont(labelFont);
        startDateLabel.setForeground(Color.LIGHT_GRAY);
        startDateLabel.setOpaque(true);
        startDateLabel.setBackground(Color.WHITE);
        startDateLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        startDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        startDateLabel.setBounds(20, y, fieldWidth, height);
        eventContentPanel.add(startDateLabel);

        y += height + 5;
        JLabel hLabel = new JLabel("h:");
        hLabel.setForeground(Color.WHITE);
        hLabel.setFont(labelFont);
        hLabel.setBounds(20, y, 20, height);
        eventContentPanel.add(hLabel);

        startHour = new JComboBox<>();
        for (int i = 0; i < 24; i++) startHour.addItem(String.format("%02d", i));
        startHour.setFont(labelFont);
        startHour.setBounds(40, y, 60, height);
        eventContentPanel.add(startHour);

        JLabel mLabel = new JLabel("m:");
        mLabel.setForeground(Color.WHITE);
        mLabel.setFont(labelFont);
        mLabel.setBounds(115, y, 20, height);
        eventContentPanel.add(mLabel);

        startMinute = new JComboBox<>();
        for (int i = 0; i < 60; i++) startMinute.addItem(String.format("%02d", i));
        startMinute.setFont(labelFont);
        startMinute.setBounds(135, y, 60, height);
        eventContentPanel.add(startMinute);

        JLabel sLabel = new JLabel("s:");
        sLabel.setForeground(Color.WHITE);
        sLabel.setFont(labelFont);
        sLabel.setBounds(210, y, 20, height);
        eventContentPanel.add(sLabel);

        startSecond = new JComboBox<>();
        for (int i = 0; i < 60; i++) startSecond.addItem(String.format("%02d", i));
        startSecond.setFont(labelFont);
        startSecond.setBounds(230, y, 60, height);
        eventContentPanel.add(startSecond);

        y += height + 15;

        // Kết thúc
        JLabel endLabel = new JLabel("End:");
        endLabel.setFont(labelFont);
        endLabel.setForeground(Color.WHITE);
        endLabel.setBounds(20, y, fieldWidth, height);
        eventContentPanel.add(endLabel);

        y += height + 5;
        endDateLabel = new JLabel("YYYY-MM-DD");
        endDateLabel.setFont(labelFont);
        endDateLabel.setForeground(Color.LIGHT_GRAY);
        endDateLabel.setOpaque(true);
        endDateLabel.setBackground(Color.WHITE);
        endDateLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        endDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        endDateLabel.setBounds(20, y, fieldWidth, height);
        eventContentPanel.add(endDateLabel);

        y += height + 5;
        JLabel ehLabel = new JLabel("h:");
        ehLabel.setForeground(Color.WHITE);
        ehLabel.setFont(labelFont);
        ehLabel.setBounds(20, y, 20, height);
        eventContentPanel.add(ehLabel);

        endHour = new JComboBox<>();
        for (int i = 0; i < 24; i++) endHour.addItem(String.format("%02d", i));
        endHour.setFont(labelFont);
        endHour.setBounds(40, y, 60, height);
        eventContentPanel.add(endHour);

        JLabel emLabel = new JLabel("m:");
        emLabel.setForeground(Color.WHITE);
        emLabel.setFont(labelFont);
        emLabel.setBounds(115, y, 20, height);
        eventContentPanel.add(emLabel);

        endMinute = new JComboBox<>();
        for (int i = 0; i < 60; i++) endMinute.addItem(String.format("%02d", i));
        endMinute.setFont(labelFont);
        endMinute.setBounds(135, y, 60, height);
        eventContentPanel.add(endMinute);

        JLabel esLabel = new JLabel("s:");
        esLabel.setForeground(Color.WHITE);
        esLabel.setFont(labelFont);
        esLabel.setBounds(210, y, 20, height);
        eventContentPanel.add(esLabel);

        endSecond = new JComboBox<>();
        for (int i = 0; i < 60; i++) endSecond.addItem(String.format("%02d", i));
        endSecond.setFont(labelFont);
        endSecond.setBounds(230, y, 60, height);
        eventContentPanel.add(endSecond);

        y += height + 15;

        // All-day
        JLabel allDayLabel = new JLabel("All-day:");
        allDayLabel.setFont(labelFont);
        allDayLabel.setForeground(Color.WHITE);
        allDayLabel.setBounds(20, y, 60, height);
        eventContentPanel.add(allDayLabel);

        allDayToggleIcon = new JLabel();
        allDayToggleIcon.setIcon(new ImageIcon("src/main/resources/toggle_off.png"));
        allDayToggleIcon.setBounds(80, y, 25, 25);
        allDayToggleIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        eventContentPanel.add(allDayToggleIcon);

        y += height + 15;

        // Lặp lại
        JLabel repeatLabel = new JLabel("Repeat:");
        repeatLabel.setFont(labelFont);
        repeatLabel.setForeground(Color.WHITE);
        repeatLabel.setBounds(20, y, fieldWidth / 2, height);
        eventContentPanel.add(repeatLabel);

        y += height + 5;
        repeatCombo = new JComboBox<>(new String[]{"none", "daily", "weekly", "monthly", "yearly"});
        repeatCombo.setFont(labelFont);
        repeatCombo.setBounds(20, y, fieldWidth, height);
        eventContentPanel.add(repeatCombo);

        y += height + 15;

        // Âm thanh báo thức
        JLabel soundLabel = new JLabel("Alarm:");
        soundLabel.setFont(labelFont);
        soundLabel.setForeground(Color.WHITE);
        soundLabel.setBounds(20, y, 60, height);
        eventContentPanel.add(soundLabel);

        alarmToggleIcon = new JLabel();
        alarmToggleIcon.setIcon(new ImageIcon("src/main/resources/toggle_on.png"));
        alarmToggleIcon.setBounds(80, y, 25, 25);
        alarmToggleIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        eventContentPanel.add(alarmToggleIcon);

        y += height + 5;
        SoundItem[] soundItems = {
                new SoundItem("pip pip", "src/main/resources/pip_pip.wav"),
                new SoundItem("reng reng", "src/main/resources/reng_reng.wav"),
                new SoundItem("iphone alarm", "src/main/resources/iphone_alarm.wav"),
                new SoundItem("samsung alarm 1", "src/main/resources/samsung_alarm_1.wav"),
                new SoundItem("samsung alarm 2", "src/main/resources/samsung_alarm_2.wav"),
                new SoundItem("other", "other")
        };
        soundComboBox = new JComboBox<>(soundItems);
        soundComboBox.setRenderer(new SoundItemRenderer());
        soundComboBox.setFont(labelFont);
        soundComboBox.setBounds(20, y, 240, height);
        eventContentPanel.add(soundComboBox);

        playIcon = new JLabel();
        playIcon.setIcon(new ImageIcon("src/main/resources/play_circle.png"));
        playIcon.setBounds(265, y, 25, 25);
        playIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        eventContentPanel.add(playIcon);

        y += height + 15;

        // Nhắc trước
        JLabel reminderLabel = new JLabel("Reminder (minutes):");
        reminderLabel.setFont(labelFont);
        reminderLabel.setForeground(Color.WHITE);
        reminderLabel.setBounds(20, y, fieldWidth, height);
        eventContentPanel.add(reminderLabel);

        y += height + 5;
        reminderBox = new JComboBox<>(new String[]{"0", "5", "10", "15", "30", "60"});
        reminderBox.setFont(labelFont);
        reminderBox.setEditable(true);
        reminderBox.setBounds(20, y, fieldWidth, height);
        eventContentPanel.add(reminderBox);

        y += height + 15;

        // Màu sắc
        JLabel colorLabel = new JLabel("Color:");
        colorLabel.setFont(labelFont);
        colorLabel.setForeground(Color.WHITE);
        colorLabel.setBounds(20, y, fieldWidth, height);
        eventContentPanel.add(colorLabel);

        y += height + 5;
        colorComboBox = new JComboBox<>(new ColorIcon[]{
                new ColorIcon(Color.decode("#ed201d"), "Đỏ"),
                new ColorIcon(Color.decode("#fd7941"), "Cam"),
                new ColorIcon(Color.decode("#f4be40"), "Vàng"),
                new ColorIcon(Color.decode("#5ecc89"), "Lục"),
                new ColorIcon(Color.decode("#4ca8df"), "Lam"),
                new ColorIcon(Color.decode("#985df6"), "Tím"),
                new ColorIcon(Color.decode("#b8b8b8"), "Xám")
        });
        colorComboBox.setRenderer(new ColorIconRenderer());
        colorComboBox.setFont(labelFont);
        colorComboBox.setForeground(Color.BLACK);
        colorComboBox.setBounds(20, y, fieldWidth, height);
        eventContentPanel.add(colorComboBox);

        y += height + 15;

        // Mô tả
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(labelFont);
        descriptionLabel.setForeground(Color.WHITE);
        descriptionLabel.setBounds(20, y, fieldWidth, height);
        eventContentPanel.add(descriptionLabel);

        y += height + 5;
        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setFont(labelFont);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setBounds(20, y, fieldWidth, 100);
        eventContentPanel.add(scrollPane);

        y += 110;

        // Nút Thêm / Sửa / Xóa
        addButton = new JButton("Add");
        addButton.setBounds(20, y, 80, 30);
        addButton.setBackground(CommonConstants.TERTIARY_COLOR);
        addButton.setForeground(CommonConstants.PRIMARY_COLOR);
        eventContentPanel.add(addButton);

        updateButton = new JButton("Edit");
        updateButton.setBounds(110, y, 80, 30);
        updateButton.setBackground(CommonConstants.TERTIARY_COLOR);
        updateButton.setForeground(CommonConstants.PRIMARY_COLOR);
        eventContentPanel.add(updateButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(200, y, 80, 30);
        deleteButton.setBackground(CommonConstants.TERTIARY_COLOR);
        deleteButton.setForeground(CommonConstants.PRIMARY_COLOR);
        eventContentPanel.add(deleteButton);

        add(eventContentPanel);
        eventContentPanel.setVisible(false);

        SwingUtilities.invokeLater(() -> {
            revalidate();
            repaint();
        });
    }

    // Class hỗ trợ để hiển thị âm thanh trong combobox
    public static class SoundItem {
        private String name;
        private String path;

        public SoundItem(String name, String path) {
            this.name = name;
            this.path = path;
        }

        public String getName() { return name; }
        public String getPath() { return path; }

        @Override
        public String toString() { return name; }
    }

    private class SoundItemRenderer extends JLabel implements ListCellRenderer<SoundItem> {
        @Override
        public Component getListCellRendererComponent(JList<? extends SoundItem> list, SoundItem value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(value.getName());
            setFont(new Font("Arial", Font.BOLD, 18)); // Cho to lên
            setOpaque(true);
            setBackground(isSelected ? CommonConstants.TERTIARY_COLOR : Color.WHITE);
            setForeground(isSelected ? CommonConstants.TEXT_COLOR : Color.BLACK);
            setHorizontalTextPosition(JLabel.RIGHT);
            return this;
        }
    }

    // Class hỗ trợ để hiển thị ô màu trong combobox
    public static class ColorIcon {
        private Color color;
        private String name;
        private String hex;

        public ColorIcon(Color color, String name) {
            this.color = color;
            this.name = name;
            this.hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        }

        public Color getColor() {
            return color;
        }

        public String getHex() {
            return hex;
        }

        public String toString() {
            return name;
        }
    }


    // Renderer cho combobox màu
    private class ColorIconRenderer extends JLabel implements ListCellRenderer<ColorIcon> {
        @Override
        public Component getListCellRendererComponent(JList<? extends ColorIcon> list, ColorIcon value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(value.toString());
            setIcon(new ColorSquareIcon(value.getColor(), 20));
            setForeground(isSelected ? CommonConstants.TEXT_COLOR : Color.BLACK);
            return this;
        }
    }

    // Icon hình vuông màu
    private class ColorSquareIcon implements Icon {
        private Color color;
        private int size;

        public ColorSquareIcon(Color color, int size) {
            this.color = color;
            this.size = size;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(color);
            g2d.fillRect(x, y, size, size);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, size, size);
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }
    }

    // ====== Public Getters ======
    public JLabel getCloseLabel() { return closeLabel; }
    public JButton getCreateEventButton() { return createEventButton; }
    public JPanel getEventContentPanel() { return eventContentPanel; }

    public JTextField getTitleField() { return titleField; }
    public JLabel getStartDateLabel() { return startDateLabel; }
    public JComboBox<String> getStartHour() { return startHour; }
    public JComboBox<String> getStartMinute() { return startMinute; }
    public JComboBox<String> getStartSecond() { return startSecond; }
    public JLabel getEndDateLabel() { return endDateLabel; }
    public JComboBox<String> getEndHour() { return endHour; }
    public JComboBox<String> getEndMinute() { return endMinute; }
    public JComboBox<String> getEndSecond() { return endSecond; }
    public JLabel getAllDayToggleIcon() { return allDayToggleIcon; }
    public JComboBox<String> getRepeatCombo() { return repeatCombo; }
    public JLabel getAlarmToggleIcon() { return alarmToggleIcon; }
    public JComboBox<SoundItem> getSoundComboBox() { return soundComboBox; }
    public JLabel getPlayIcon() { return playIcon; }
    public JComboBox<String> getReminderBox() { return reminderBox; }
    public JComboBox<ColorIcon> getColorComboBox() { return colorComboBox; }
    public JTextArea getDescriptionArea() { return descriptionArea; }
    public JButton getAddButton() { return addButton; }
    public JButton getUpdateButton() { return updateButton; }
    public JButton getDeleteButton() { return deleteButton; }

}