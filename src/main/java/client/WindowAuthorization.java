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
    private JButton deleteButton = new JButton("Delete account");
    private JMenuBar menuBar = new JMenuBar();
    private JMenuItem deleteMenu = new JMenuItem ();
    private JMenuItem serverMenu = new JMenuItem ();
    private JMenuItem signUpMenu = new JMenuItem ();

    public WindowAuthorization() throws HeadlessException {
        setSize(200, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));
        setResizable(false);
        add(new JLabel("Login"));
        add(loginField);
        add(new JLabel("Password"));
        add(passwordField);
        add(signInButton);
        menuBar.add(menu ());
        setJMenuBar(menuBar);
        setVisible(true);
    }

    public JMenu menu (){
        JMenu settingsMenu = new JMenu("Settings");
        deleteMenu = new JMenuItem("Delete account");
        signUpMenu = new JMenuItem("Sign up");
        serverMenu = new JMenuItem("Server");
        settingsMenu.add(deleteMenu);
        settingsMenu.add(signUpMenu);
        settingsMenu.add(serverMenu);
        return settingsMenu;
    }

    public JMenuItem getServerMenu() {
        return serverMenu;
    }

    public JMenuItem getSignUpMenu() {
        return signUpMenu;
    }

    public JMenuItem getDeleteMenu() {
        return deleteMenu;
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

    public void windowActive (boolean active) {
        signInButton.setEnabled(active);
        menuBar.setEnabled(active);
        loginField.setEnabled(active);
        passwordField.setEnabled(active);
    }

}
