package controller;

import dao.ForgotPassDAO;
import model.CommonConstants;
import util.sendMail;
import view.ForgotPassFormGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class forgotPass {
    private static String currentCode;
    private static String targetEmail;

    public static void attachForgotPassActions(ForgotPassFormGUI form) {
        form.getChangePasswordButton().addActionListener(e -> {
            String username = form.getUsernameField().getText();
            String email = form.getMailField().getText();
            if (!ForgotPassDAO.validateEmailForUser(username, email)) {
                JOptionPane.showMessageDialog(form, "Sai thông tin username hoặc email chưa được đăng ký.");
                return;
            }
            targetEmail = email;
            currentCode = generateVerificationCode();
            sendMail.sendVerificationCode(email, currentCode);
            showVerificationDialog(form);
        });

        form.getLoginLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                form.dispose();
                new view.LoginFormGUI().setVisible(true);
            }
        });

        form.getRegisterLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                form.dispose();
                new view.RegisterFormGUI().setVisible(true);
            }
        });
    }

    private static String generateVerificationCode() {
        int code = (int)(Math.random() * 900000) + 100000;
        return String.valueOf(code);
    }

    private static void showVerificationDialog(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Xác nhận mã OTP", true);
        dialog.setSize(400, 220);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel titleLabel = new JLabel("Nhập mã xác nhận đã gửi tới email của bạn:");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField codeField = new JTextField();
        codeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JPanel resendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        JLabel resendLabel = new JLabel("Gửi lại mã");
        resendLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        resendLabel.setForeground(Color.GRAY);
        resendLabel.setEnabled(false);
        JLabel countdownLabel = new JLabel("(30s)");
        resendPanel.add(resendLabel);
        resendPanel.add(countdownLabel);

        JButton confirmButton = new JButton("Xác nhận");
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.setPreferredSize(new Dimension(100, 30));

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(codeField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(resendPanel);
        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.add(confirmButton, BorderLayout.SOUTH);

        Timer[] timer = new Timer[1];
        final int[] countdown = {30};

        Runnable startCountdown = () -> {
            countdown[0] = 30;
            resendLabel.setForeground(Color.GRAY);
            resendLabel.setEnabled(false);
            countdownLabel.setText("(30s)");

            if (timer[0] != null) timer[0].cancel();
            timer[0] = new Timer();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    countdown[0]--;
                    SwingUtilities.invokeLater(() -> {
                        countdownLabel.setText("(" + countdown[0] + "s)");
                    });
                    if (countdown[0] <= 0) {
                        timer[0].cancel();
                        SwingUtilities.invokeLater(() -> {
                            resendLabel.setForeground(CommonConstants.PRIMARY_COLOR);
                            resendLabel.setEnabled(true);
                            countdownLabel.setText("");
                        });
                    }
                }
            };
            timer[0].scheduleAtFixedRate(task, 1000, 1000);
        };
        startCountdown.run();

        resendLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!resendLabel.isEnabled()) return;
                currentCode = generateVerificationCode();
                util.sendMail.sendVerificationCode(targetEmail, currentCode);
                startCountdown.run();
            }
        });

        confirmButton.addActionListener(e -> {
            String inputCode = codeField.getText().trim();
            if (inputCode.equals(currentCode)) {
                dialog.dispose();
                showResetPasswordDialog(parent);
            } else {
                JOptionPane.showMessageDialog(dialog, "Mã xác minh không đúng!");
            }
        });

        dialog.setVisible(true);
    }

    private static void showResetPasswordDialog(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Đặt lại mật khẩu", true);
        dialog.setSize(400, 240);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel passLabel = new JLabel("Mật khẩu mới:");
        JPasswordField passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel rePassLabel = new JLabel("Nhập lại mật khẩu:");
        JPasswordField rePassField = new JPasswordField();
        rePassField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JButton confirmButton = new JButton("Xác nhận");
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(passLabel);
        contentPanel.add(passField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(rePassLabel);
        contentPanel.add(rePassField);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(confirmButton);

        dialog.add(contentPanel, BorderLayout.CENTER);

        confirmButton.addActionListener(e -> {
            String newPass = new String(passField.getPassword());
            String reNewPass = new String(rePassField.getPassword());

            if (!model.checkStrongPass.isPasswordStrong(newPass)) {
                JOptionPane.showMessageDialog(dialog, "Mật khẩu phải >=6 ký tự, chứa chữ thường, chữ HOA, ký tự đặc biệt và số.");
                return;
            }
            if (!newPass.equals(reNewPass)) {
                JOptionPane.showMessageDialog(dialog, "Mật khẩu nhập lại không khớp!");
                return;
            }

            if (ForgotPassDAO.updatePassword(targetEmail, newPass)) {
                JOptionPane.showMessageDialog(dialog, "Đặt lại mật khẩu thành công!");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Có lỗi xảy ra khi cập nhật mật khẩu.");
            }
        });

        dialog.setVisible(true);
    }
}