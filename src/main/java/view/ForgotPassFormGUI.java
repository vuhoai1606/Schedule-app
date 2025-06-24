package view;

import model.CommonConstants;

import javax.swing.*;
import java.awt.*;

public class ForgotPassFormGUI extends Form{
    private JTextField usernameField;
    private JTextField mailField;
    private JButton changePasswordButton;
    private JLabel loginLabel;
    private JLabel registerLabel;

    public JTextField getUsernameField() {
        return usernameField;
    }
    public JTextField getMailField() {
        return mailField;
    }
    public JButton getChangePasswordButton() {
        return changePasswordButton;
    }
    public JLabel getLoginLabel() {
        return loginLabel;
    }
    public JLabel getRegisterLabel() {
        return registerLabel;
    }

    public ForgotPassFormGUI() {
        super("Login");
        addGuiComponents();
        controller.forgotPass.attachForgotPassActions(this);
    }

    private void addGuiComponents(){
        JLabel forgotPasswordLabel = new JLabel("Forgot Password");
        forgotPasswordLabel.setBounds(0, 25, 520, 100);
        forgotPasswordLabel.setForeground(CommonConstants.TEXT_COLOR);
        forgotPasswordLabel.setFont(new Font("Dialog", Font.BOLD, 40));
        forgotPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(forgotPasswordLabel);

        // create username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(30, 150, 400, 25);
        usernameLabel.setForeground(CommonConstants.TEXT_COLOR);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        // create username text field
        usernameField = new JTextField();
        usernameField.setBounds(30, 185, 450, 55);
        usernameField.setBackground(CommonConstants.SECONDARY_COLOR);
        usernameField.setForeground(CommonConstants.TEXT_COLOR);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(usernameLabel);
        add(usernameField);

        // create mail label
        JLabel mailLabel = new JLabel("Mail:");
        mailLabel.setBounds(30, 335, 400, 25);
        mailLabel.setForeground(CommonConstants.TEXT_COLOR);
        mailLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        // create username text field
        mailField = new JTextField();
        mailField.setBounds(30, 365, 450, 55);
        mailField.setBackground(CommonConstants.SECONDARY_COLOR);
        mailField.setForeground(CommonConstants.TEXT_COLOR);
        mailField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(mailLabel);
        add(mailField);

        // create change password button
        changePasswordButton = new JButton("Change Password");
        changePasswordButton.setFont(new Font("Dialog", Font.BOLD, 20));
        changePasswordButton.setForeground(CommonConstants.PRIMARY_COLOR);

        // change the cursor to a hand when hover over the button
        changePasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        changePasswordButton.setBackground(CommonConstants.TEXT_COLOR);
        changePasswordButton.setBounds(125, 480, 250, 50);
        add(changePasswordButton);

        // create loginLabel password label
        loginLabel = new JLabel("Back to login");
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLabel.setForeground(CommonConstants.TEXT_COLOR);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 16));
        loginLabel.setBounds(125, 560, 250, 30);
        add(loginLabel);

        // create register label (used to load the register GUI)
        registerLabel = new JLabel("Not a user? Register Here");
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerLabel.setForeground(CommonConstants.TEXT_COLOR);
        registerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        registerLabel.setBounds(125, 600, 250, 30);
        add(registerLabel);
    }
}
