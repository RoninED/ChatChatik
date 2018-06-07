package server;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Eduard on 9/27/2017.
 */
public class WindowServerSettings extends JFrame {
    private JTextField portField=new JTextField();
    private JButton acceptButton = new JButton("Accept and Exit");
    private JButton closeButton = new JButton("Close without saving");

    public WindowServerSettings() throws HeadlessException {
        setSize(200, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));
        setResizable(false);
        setTitle("ServerAddress");
        add(new JLabel("Port"));
        add(portField);
        add(acceptButton);
        add(closeButton);
        setVisible(true);
    }

    public JTextField getPortField() {
        return portField;
    }

    public JButton getAcceptButton() {
        return acceptButton;
    }

    public JButton getCloseButton() {
        return closeButton;
    }
}
