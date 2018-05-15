package server;

import javax.swing.*;
import java.awt.*;

public class ServerWindow extends JFrame {
    private JButton close = new JButton("Close server");
    private JButton settings = new JButton("Settings");

    public ServerWindow() throws HeadlessException {
        setSize(200, 300);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 1));
        setResizable(false);
        setTitle("SignUp");
        add(close);
        add(settings);
        setVisible(true);
    }

    public JButton getClose() {
        return close;
    }

    public JButton getSettings() {
        return settings;
    }
}


