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
    private JButton deleteButton = new JButton("Delete account");
    private JMenuBar menuBar = new JMenuBar();

    public WindowAuthorization() throws HeadlessException {
        setSize(200, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 1));
        setResizable(false);
        add(new JLabel("Login"));
        add(loginField);
        add(new JLabel("Password"));
        add(passwordField);
        add(signInButton);
        add(signUpButton);
        add(serverButton);
//        menuBar.add(menu)
        setJMenuBar(menuBar);
        setVisible(true);
    }

    public JMenu menu (){
        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem deleteMenu = new JMenuItem();
        settingsMenu.add(deleteMenu);
        return settingsMenu;
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
