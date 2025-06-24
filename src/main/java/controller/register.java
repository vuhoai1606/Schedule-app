package controller;

import dao.RegisterDAO;
import view.LoginFormGUI;
import view.RegisterFormGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class register {
    public static void attachRegisterAction(RegisterFormGUI registerForm) {
        registerForm.getRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = registerForm.getMailField().getText().trim();
                String username = registerForm.getUsernameField().getText().trim();
                String password = new String(registerForm.getPasswordField().getPassword());
                String rePassword = new String(registerForm.getRePasswordField().getPassword());

                String validationMessage = validateUserInput(email, username, password, rePassword);
                if (!validationMessage.isEmpty()) {
                    JOptionPane.showMessageDialog(null, validationMessage);
                    return;
                }

                if (RegisterDAO.isUsernameOrEmailTaken(username, email)) {
                    JOptionPane.showMessageDialog(null, "Username hoặc Email đã tồn tại.");
                    return;
                }

                String code = generateVerificationCode();
                boolean mailSent = util.sendMail.sendVerificationCode(email, code);
                if (!mailSent) {
                    JOptionPane.showMessageDialog(null, "Không thể gửi email xác nhận. Vui lòng kiểm tra lại email.");
                    return;
                }

                String inputCode = JOptionPane.showInputDialog(null,
                        "Nhập mã xác nhận đã gửi tới email của bạn:", "Xác nhận Email", JOptionPane.INFORMATION_MESSAGE);
                if (inputCode != null && inputCode.equals(code)) {
                    if (RegisterDAO.registerUser(username, email, password)) {
                        registerForm.dispose();
                        new LoginFormGUI().setVisible(true);
                        JOptionPane.showMessageDialog(null, "Đăng ký thành công!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi đăng ký. Vui lòng thử lại.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Sai mã xác nhận.");
                }
            }
        });

        registerForm.getLoginLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                registerForm.dispose();
                new LoginFormGUI().setVisible(true);
            }
        });
    }

    private static String validateUserInput(String mail, String username, String password, String rePassword) {
        if (mail.isEmpty() || username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
            return "Vui lòng nhập đầy đủ tất cả các trường.";
        }
        if (username.length() < 6) {
            return "Tên người dùng phải có ít nhất 6 ký tự.";
        }
        if (!password.equals(rePassword)) {
            return "Mật khẩu không trùng khớp.";
        }
        if (!model.checkStrongPass.isPasswordStrong(password)) {
            return "Mật khẩu chưa đủ mạnh (phải có cả chữ hoa, số, ký tự đặc biệt, độ dài...).";
        }
        return "";
    }

    private static String generateVerificationCode() {
        int code = (int)(Math.random() * 900000) + 100000;
        return String.valueOf(code);
    }
}