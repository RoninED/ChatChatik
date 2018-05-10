package client;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Eduard on 9/27/2017.
 */
public class WindowSignUP extends JFrame {
    private JTextField loginField =new JTextField();
    private JTextField passwordField =new JTextField();
    private JButton signupButton = new JButton("Sign Up");
    private JButton signinButton = new JButton("Sign In");

    public WindowSignUP() throws HeadlessException {
        setSize(200, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1));
        setResizable(false);
        setTitle("SignUp");
        add(new JLabel("Login"));
        add(loginField);
        add(new JLabel("Password"));
        add(passwordField);
        add(signupButton);
        add(signinButton);
        setVisible(true);
    }

    public JTextField getLoginField() {
        return loginField;
    }

    public JTextField getPasswordField() {
        return passwordField;
    }

    public JButton getSignupButton() {
        return signupButton;
    }

    public void setLoginField(JTextField loginField) {
        this.loginField = loginField;
    }

    public void setPasswordField(JTextField passwordField) {
        this.passwordField = passwordField;
    }

    public JButton getSigninButton() {
        return signinButton;
    }
}
