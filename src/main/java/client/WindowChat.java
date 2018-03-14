package client;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

/**
 * Created by Eduard on 9/30/2017.
 */
public class WindowChat extends JFrame {
    private JTextArea chat;
    private JTextField newMessage;
    private JButton sendButton;


    public WindowChat() {
        setSize(200, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));
        setResizable(false);
        setLayout(new BorderLayout());


        chat = new JTextArea();
        chat.setEditable(false);
        chat.setLineWrap(true);
        chat.setWrapStyleWord(true);
        add(new JScrollPane(chat), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);
        sendButton = new JButton("SEND");
        bottomPanel.add(sendButton, BorderLayout.EAST);
        newMessage = new JTextField();
        bottomPanel.add(newMessage, BorderLayout.CENTER);
        setVisible(true);
    }

    public JTextArea getChat() {
        return chat;
    }

    public JTextField getNewMessage() {
        return newMessage;
    }

    public JButton getSendButton() {
        return sendButton;
    }

}
