package client;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Eduard on 9/27/2017.
 */
public class WindowAuthorization extends JFrame {
    private JTextField loginField =new JTextField();
    private JPasswordField passwordField=new JPasswordField();
    private JButton signInButton = new JButton("Sign in");
    private JButton signUpButton = new JButton("Sign up");
    private JButton serverButton = new JButton("Server");

    public WindowAuthorization() throws HeadlessException {
        setSize(300, 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2));
        setResizable(false);
        add(new JLabel("Login"));
        add(loginField);
        add(new JLabel("Password"));
        add(passwordField);
        add(signInButton);
        add(signUpButton);
        add(serverButton);
        setVisible(true);
    }

    public JTextField getLoginField() {
        return loginField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getSignInButton() {
        return signInButton;
    }

    public JButton getServerButton() {
        return serverButton;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }
}
