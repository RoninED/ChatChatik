package client;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Eduard on 9/27/2017.
 */
public class WindowServer extends JFrame {
    private JTextField hostField =new JTextField();
    private JTextField portField=new JTextField();
    private JButton acceptButton = new JButton("Accept");

    public WindowServer() throws HeadlessException {
        setSize(300, 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));
        setResizable(false);
        setTitle("ServerAddress");
        add(new JLabel("Host"));
        add(hostField);
        add(new JLabel("Port"));
        add(portField);
        add(acceptButton);
        setVisible(true);
    }

    public JTextField getHostField() {
        return hostField;
    }

    public JTextField getPortField() {
        return portField;
    }

    public JButton getAcceptButton() {
        return acceptButton;
    }

    public void setHostField(JTextField hostField) {
        this.hostField = hostField;
    }

    public void setPortField(JTextField portField) {
        this.portField = portField;
    }

}
