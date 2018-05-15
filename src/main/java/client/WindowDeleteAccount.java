package client;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Eduard on 9/27/2017.
 */
public class WindowDeleteAccount extends JFrame {
    private JTextField loginField =new JTextField();
    private JTextField passwordField =new JTextField();
    private JButton removeButton = new JButton("Remove");
    private JButton signinButton = new JButton("Sign In");

    public WindowDeleteAccount() throws HeadlessException {
        setSize(200, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1));
        setResizable(false);
        setTitle("Remove account");
        add(new JLabel("Login"));
        add(loginField);
        add(new JLabel("Password"));
        add(passwordField);
        add(removeButton);
        add(signinButton);
        setVisible(true);
    }

    public JTextField getLoginField() {
        return loginField;
    }

    public JTextField getPasswordField() {
        return passwordField;
    }

    public JButton getRemoveButton() {
        return removeButton;
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

    public void windowActive (boolean active) {
        removeButton.setEnabled(active);
        signinButton.setEnabled(active);
        loginField.setEnabled(active);
        passwordField.setEnabled(active);
    }
}
