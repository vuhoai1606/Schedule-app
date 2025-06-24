package controller;

import dao.LoginDAO;
import view.ForgotPassFormGUI;
import view.LoginFormGUI;
import view.RegisterFormGUI;
import view.ScheduleAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class login {
    public static String currentUserId = null; // Biến lưu user_id toàn cục

    public static void attachLoginAction(LoginFormGUI loginForm) {
        loginForm.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginForm.getUsernameField().getText();
                String password = new String(loginForm.getPasswordField().getPassword());

                currentUserId = LoginDAO.validateLogin(username, password);
                if (currentUserId != null) {
                    JOptionPane.showMessageDialog(loginForm, "Login thành công!");
                    if (loginForm.getRememberMeCheckBox().isSelected()) {
                        util.Remember.saveCredentials(username, password);
                    } else {
                        util.Remember.clearCredentials();
                    }
                    loginForm.dispose();
                    new ScheduleAppGUI().setVisible(true); // Mở ScheduleAppGUI với user_id
                } else {
                    JOptionPane.showMessageDialog(loginForm, "Sai thông tin đăng nhập!");
                    int option = JOptionPane.showConfirmDialog(
                            loginForm,
                            "Tài khoản không tồn tại. Bạn có muốn đăng ký không?",
                            "Thông báo",
                            JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        loginForm.dispose();
                        new RegisterFormGUI().setVisible(true);
                    }
                }
            }
        });

        // FORGOT PASSWORD LABEL
        loginForm.getForgotPasswordLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loginForm.dispose();
                new ForgotPassFormGUI().setVisible(true);
            }
        });

        // REGISTER LABEL
        loginForm.getRegisterLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loginForm.dispose();
                new RegisterFormGUI().setVisible(true);
            }
        });
    }
}