package view;

import model.CommonConstants;

import javax.swing.*;
import java.awt.*;

public class RegisterFormGUI extends Form{
    private JTextField mailField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField rePasswordField;
    private JButton registerButton;
    private JLabel loginLabel;

    public JButton getRegisterButton() {
        return registerButton;
    }
    public JLabel getLoginLabel() {
        return loginLabel;
    }
    public JTextField getMailField() {
        return mailField;
    }
    public JTextField getUsernameField() {
        return usernameField;
    }
    public JPasswordField getPasswordField() {
        return passwordField;
    }
    public JPasswordField getRePasswordField() {
        return rePasswordField;
    }

    public RegisterFormGUI() {
        super("Register");
        addGuiComponents();
        controller.register.attachRegisterAction(this);
    }

    private void addGuiComponents(){
        JLabel registerLabel = new JLabel("Register");
        registerLabel.setBounds(0, 25, 520, 100);
        registerLabel.setForeground(CommonConstants.TEXT_COLOR);
        registerLabel.setFont(new Font("Dialog", Font.BOLD, 40));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(registerLabel);

        // create mail label
        JLabel mailLabel = new JLabel("Mail:");
        mailLabel.setBounds(30, 115, 400, 25);
        mailLabel.setForeground(CommonConstants.TEXT_COLOR);
        mailLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        // create mail text field
        mailField = new JTextField();
        mailField.setBounds(30, 150, 450, 55);
        mailField.setBackground(CommonConstants.SECONDARY_COLOR);
        mailField.setForeground(CommonConstants.TEXT_COLOR);
        mailField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(mailLabel);
        add(mailField);

        // create username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(30, 220, 400, 25);
        usernameLabel.setForeground(CommonConstants.TEXT_COLOR);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        // create username text field
        usernameField = new JTextField();
        usernameField.setBounds(30, 255, 450, 55);
        usernameField.setBackground(CommonConstants.SECONDARY_COLOR);
        usernameField.setForeground(CommonConstants.TEXT_COLOR);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(usernameLabel);
        add(usernameField);

        // create password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 325, 400, 25);
        passwordLabel.setForeground(CommonConstants.TEXT_COLOR);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        // create password field
        passwordField = new JPasswordField();
        passwordField.setBounds(30, 360, 450, 55);
        passwordField.setBackground(CommonConstants.SECONDARY_COLOR);
        passwordField.setForeground(CommonConstants.TEXT_COLOR);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(passwordLabel);
        add(passwordField);

        // create re-enter password label
        JLabel rePasswordLabel = new JLabel("Re-enter Password:");
        rePasswordLabel.setBounds(30, 430, 400, 25);
        rePasswordLabel.setForeground(CommonConstants.TEXT_COLOR);
        rePasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        // create re-enter password field
        rePasswordField = new JPasswordField();
        rePasswordField.setBounds(30, 465, 450, 55);
        rePasswordField.setBackground(CommonConstants.SECONDARY_COLOR);
        rePasswordField.setForeground(CommonConstants.TEXT_COLOR);
        rePasswordField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(rePasswordLabel);
        add(rePasswordField);

        // create register button
        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Dialog", Font.BOLD, 20));
        registerButton.setForeground(CommonConstants.PRIMARY_COLOR);

        // change the cursor to a hand when hover over the button
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerButton.setBackground(CommonConstants.TEXT_COLOR);
        registerButton.setBounds(125, 540, 250, 50);
        add(registerButton);

        // create register label (used to load the register GUI)
        loginLabel = new JLabel("Have an account? Login Here");
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLabel.setForeground(CommonConstants.TEXT_COLOR);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 16));
        loginLabel.setBounds(125, 600, 250, 30);
        add(loginLabel);
    }

}
