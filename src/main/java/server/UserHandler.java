package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

/**
 * Created by Eduard on 10/1/2017.
 */

//Personal thread for every new connected client
public class UserHandler extends Thread {
    private String login;
    private String password;
    private String splitSign = "/split/";
    private Socket socket;
    private boolean authorization = false;
    private DataInputStream in;
    private DataOutputStream out;

    public UserHandler(Socket socket, String login) {
        setDaemon(false);
        start();
        this.socket = socket;
        this.login = login;
    }

    public void run() {
        try {
            System.out.println("New client: " + socket.getChannel() + " id=" + login);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            multiThread();

        } catch (SocketException ec) {
            System.out.println("(userhandler) Client off: " + login + " / " + Thread.currentThread() + " id=" + login);

            MainServer.removeFromOnlineUsers(login);

            try {
              if (authorization==true) MainServer.distributionToOnlineUsers("newMessage" + splitSign + "(server): "+login + " is disconnect");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// parser client messages, onlineChecker,
    private void multiThread() {
        Thread incomingMessages = new Thread(new Runnable() {
            @Override
            public void run() {
                int countCheckerIteration = 0;
                try {
                    while (true) {

                        //-проверку входящих и их сплит
                        if (in.available() != 0) {
                            String tempIncome = in.readUTF();
                            String[] fromClient = tempIncome.split(splitSign);
//                            System.out.println(login + " "  + ", income= " + tempIncome + "   - autorization = " + authorization);
                            System.out.printf("%-10s in:  %s\n", login, tempIncome);
                            switch (fromClient[0]) {

                                //signUp
                                case "WowNewUser": {
                                    if(MainServer.signUpNewUser(fromClient[1], fromClient[2]))
                                        sendMessage("WowNewUser" + splitSign + "true");
                                    else sendMessage("WowNewUser" + splitSign + "false");
                                    break;
                                }

                                //authorization
                                case "logpas": {
                                    if (fromClient[1].equals("") | fromClient.length != 3)
                                        sendMessage("logpas" + splitSign + "emptyField");
                                    else {
                                        if (MainServer.checkerAuthorizationExcel(fromClient[1], fromClient[2])) {
                                            login = fromClient[1];
                                            sendMessage("logpas" + splitSign + "goodboy");
                                            authorization = true;
                                            System.out.println(login + " has autoraized / " + Thread.currentThread() + " / id=" + login);
                                            MainServer.distributionToOnlineUsers("newMessage" + splitSign + "(server): "+login + " connected");
                                        } else sendMessage("logpas" + splitSign + "badboy");
                                    }
                                    break;
                                }

                                // new messages for users
                                case "newMessage": {
                                    MainServer.distributionToOnlineUsers((fromClient[0] + splitSign + fromClient[1]));
                                    break;
                                }

                            }
                        }

//                        //отправка трекер
                        if (countCheckerIteration == 20) {
                            sendMessage("serverOn/split/eeee");
                            countCheckerIteration = 0;
                        }

                        Thread.sleep(100);
                        countCheckerIteration++;
                    }
                } catch (SocketException ec) {
                    System.out.println("(online checker) Client disconnect: " + login + " / " + Thread.currentThread() + " id=" + login);

                    MainServer.removeFromOnlineUsers(login);

                    try {
                        if (authorization==true)  MainServer.distributionToOnlineUsers("newMessage" + splitSign + "(server): "+ login + " is disconnect");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        incomingMessages.start();
    }

    public synchronized void sendMessage(String message) throws Exception {
//        System.out.println(login + " " + ", outcome= " + message + ". autorization = " + authorization);
        System.out.printf("%-10s out: %-30s +%s\n", login, message, new Date());
        out.writeUTF(message);
        out.flush();
    }

    public String getLogin() {
        return login;
    }
}
