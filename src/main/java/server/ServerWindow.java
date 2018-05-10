package server;

import javax.swing.*;
import java.awt.*;

public class ServerWindow extends JFrame {
    private JTextField loginField = new JTextField();
    private JTextField passwordField = new JTextField();
    private JButton signupButton = new JButton("Sign Up");
    private JButton signinButton = new JButton("Sign In");

    public ServerWindow() throws HeadlessException {
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


}


