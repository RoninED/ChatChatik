package client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;


/**
 * Created by Eduard on 10/1/2017.
 */
public class MainClient {
    private static Socket socket;
    private static int SERVER_PORT = 8700;
    private static String SERVER_HOST = "localhost";
    private static DataInputStream in;
    private static DataOutputStream out;
    private static boolean authorization = false;
    private static String login;
    private static int countCheckerIteration = 0;
    private static String splitSign = "/split/";
    private static WindowChat windowChat;
    private static WindowAuthorization windowAuthorization;
    private static WindowSignUP windowSignUP;


    public static void main(String[] args) throws IOException {
        try {
        /*
        Подключение к серверу
         */
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            /*
            Дополнительный поток:
            -проверку входящих и их сплит
            -проверка каждую 10 итерацию трек сервера
            */
            iterationThread();
            authorizationStage();


        } catch (ConnectException ce) {
            JOptionPane.showMessageDialog(null, "Сервер недоступен");
            System.exit(0);

        }


    }

    //additional thread for incoming messages - split them and make next different instructions
    private static void iterationThread() {
        Thread incoming = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {

                        //проверку входящих и их сплит
                        if (in.available() != 0) {
                            String tempIn = in.readUTF();
                            String[] fromServer = tempIn.split(splitSign);
//                            System.out.println(new Date() + ", income= " + fromServer[0] + "/" + fromServer[1]);
                            System.out.printf("in:  %s\n", tempIn);
                            switch (fromServer[0]) {
                                case "logpas": {
                                    switch (fromServer[1]) {

                                        case "goodboy": {
                                            authorization = true;
                                            windowAuthorization.dispose();
                                            chatStage();
                                            windowChat.getNewMessage().grabFocus();
                                        }
                                        break;

                                        case "emptyField": {
                                            windowAuthorization.setTitle("wtf - empty field");
                                        }
                                        break;

                                        case "badboy": {
                                            windowAuthorization.setTitle("Wrong, wtf");
                                        }
                                        break;
                                    }
                                }
                                break;

                                case "newMessage": {
                                    if (authorization) {
                                        windowChat.getChat().setText(windowChat.getChat().getText() + "\n" + fromServer[1]);
                                        windowChat.getChat().setCaretPosition(windowChat.getChat().getDocument().getLength());
                                    }
                                }
                                break;

                                case "WowNewUser": {
                                    if (fromServer [1].equals("true")){
                                        windowSignUP.setTitle("Success");
                                    }
                                    else windowSignUP.setTitle("Is used");
                                }
                                break;
                            }
                        }

                        //отправка трекер
                        if (countCheckerIteration == 20) {
                            sendMessage("clientOn/split/eeee");
                            countCheckerIteration = 0;
                        }

                        Thread.sleep(100);
                        countCheckerIteration++;
                    }
                } catch (SocketException ec) {
                    JOptionPane.showMessageDialog(null, "Nobody at home((");
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        incoming.start();
    }

    //help iterationThread with empty messages, also can add checker bad messages
    public static void newMessage (String message) throws Exception {
        if (!(message.equals(""))) sendMessage("newMessage" + splitSign + login + ": " + message);
    }

    public static void authorizationStage() {
        windowAuthorization = new WindowAuthorization();
        windowAuthorization.setVisible(true);

        windowAuthorization.getSignInButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    login = windowAuthorization.getLoginField().getText();
                    sendMessage("logpas" + splitSign + login + splitSign + String.valueOf((windowAuthorization.getPasswordField().getPassword())));
                    Thread.sleep(500);
                } catch (IOException ioe) {
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        windowAuthorization.getLoginField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent es) {
                if (es.getKeyCode() == KeyEvent.VK_ENTER) windowAuthorization.getPasswordField().grabFocus();
            }
        });



        windowAuthorization.getPasswordField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent es) {
                if (es.getKeyCode() == KeyEvent.VK_ENTER) windowAuthorization.getSignInButton().doClick();
            }
        });

        windowAuthorization.getServerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverWindowStage();
            }
        });

        windowAuthorization.getSignUpButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUpStage();
            }
        });
    }

    public static void chatStage() {
        windowChat = new WindowChat();
        windowChat.setTitle("Yo " + login);
        windowChat.setVisible(true);

        windowChat.getSendButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    newMessage(windowChat.getNewMessage().getText());
                    windowChat.getNewMessage().setText("");
                    windowChat.getNewMessage().grabFocus();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        windowChat.getNewMessage().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent es) {
                if (es.getKeyCode() == KeyEvent.VK_ENTER) windowChat.getSendButton().doClick();
            }
        });
    }

    //when client changing server
    public static void serverWindowStage () {
        windowAuthorization.setVisible(false);
        final WindowServer windowServer = new WindowServer();
        windowServer.getHostField().setText(getServerHost());
        windowServer.getPortField().setText(String.valueOf(getServerPort()));

        windowServer.getHostField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent es) {
                if (es.getKeyCode() == KeyEvent.VK_ENTER) windowServer.getPortField().grabFocus();
            }
        });

        windowServer.getPortField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent es) {
                if (es.getKeyCode() == KeyEvent.VK_ENTER) windowServer.getAcceptButton().doClick();
            }
        });

        windowServer.getAcceptButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setServerHost(windowServer.getHostField().getText());
                setServerPort(Integer.parseInt(windowServer.getPortField().getText()));
                authorizationStage();
                windowAuthorization.setTitle(getServerHost()+"/"+getServerPort());
                windowServer.dispose();
            }
        });

    }

    public static void sendMessage(String message) throws Exception {
//        System.out.println(new Date() + ", outcome= " + message);
        System.out.printf("out: %-30s %s\n", message, new Date());
        out.writeUTF(message);
        out.flush();
    }

    public static void signUpStage () {
        windowSignUP = new WindowSignUP();
        windowSignUP.setVisible(true);
        windowAuthorization.setVisible(false);

        windowSignUP.getSignupButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if (windowSignUP.getLoginField().getText().equals("") | windowSignUP.getPasswordField().getText().equals("")) JOptionPane.showMessageDialog(null, "Заполните все строки");
               else {
                   try {
                       sendMessage("WowNewUser" + splitSign + windowSignUP.getLoginField().getText() + splitSign + windowSignUP.getPasswordField().getText());
                   } catch (Exception e1) {
                       e1.printStackTrace();
                   }
               }

            }
        });

        windowSignUP.getSigninButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowAuthorization.setVisible(true);
                windowSignUP.setVisible(false);
            }
        });
    }


//GETTER and SETTER
//-------------------------------------------------------------------------------------------

    public static int getServerPort() {
        return SERVER_PORT;
    }

    public static String getServerHost() {
        return SERVER_HOST;
    }

    public static void setServerPort(int serverPort) {
        SERVER_PORT = serverPort;
    }

    public static void setServerHost(String serverHost) {
        SERVER_HOST = serverHost;
    }
}

