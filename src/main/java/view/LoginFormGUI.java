package view;

import model.CommonConstants;

import javax.swing.*;
import java.awt.*;

public class LoginFormGUI extends Form{
    private JButton loginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox rememberMeCheckBox;
    private JLabel forgotPasswordLabel, registerLabel;

    public JButton getLoginButton() {
        return loginButton;
    }
    public JTextField getUsernameField() {
        return usernameField;
    }
    public JPasswordField getPasswordField() {
        return passwordField;
    }
    public JCheckBox getRememberMeCheckBox() {return rememberMeCheckBox;}
    public JLabel getForgotPasswordLabel() {return forgotPasswordLabel;}
    public JLabel getRegisterLabel() {return registerLabel;}

    public LoginFormGUI() {
        super("Login");
        addGuiComponents();
        String[] creds = util.Remember.loadCredentials();
        if (creds[0] != null && creds[1] != null) {
            usernameField.setText(creds[0]);
            passwordField.setText(creds[1]);
            rememberMeCheckBox.setSelected(true);
        }
        controller.login.attachLoginAction(this);
    }

    private void addGuiComponents(){
        JLabel loginLabel = new JLabel("Login");
        loginLabel.setBounds(0, 25, 520, 100);
        loginLabel.setForeground(CommonConstants.TEXT_COLOR);
        loginLabel.setFont(new Font("Dialog", Font.BOLD, 40));
        // center text
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(loginLabel);

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

        // create password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 335, 400, 25);
        passwordLabel.setForeground(CommonConstants.TEXT_COLOR);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        // create password field
        passwordField = new JPasswordField();
        passwordField.setBounds(30, 365, 450, 55);
        passwordField.setBackground(CommonConstants.SECONDARY_COLOR);
        passwordField.setForeground(CommonConstants.TEXT_COLOR);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(passwordLabel);
        add(passwordField);

        // create Remember Me
        rememberMeCheckBox = new JCheckBox("Remember me");
        rememberMeCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        rememberMeCheckBox.setForeground(CommonConstants.TEXT_COLOR);
        rememberMeCheckBox.setBackground(CommonConstants.PRIMARY_COLOR);
        rememberMeCheckBox.setFont(new Font("Arial", Font.BOLD, 16));
        rememberMeCheckBox.setBounds(30, 440, 135, 30);
        add(rememberMeCheckBox);

        // create forgot password label
        forgotPasswordLabel  = new JLabel("Forgot password?");
        forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotPasswordLabel.setForeground(CommonConstants.TEXT_COLOR);
        forgotPasswordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        forgotPasswordLabel.setBounds(340, 440, 145, 30);
        add(forgotPasswordLabel);

        // create login button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Dialog", Font.BOLD, 20));
        loginButton.setForeground(CommonConstants.PRIMARY_COLOR);
        // change the cursor to a hand when hover over the button
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setBackground(CommonConstants.TEXT_COLOR);
        loginButton.setBounds(125, 540, 250, 50);
        add(loginButton);

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