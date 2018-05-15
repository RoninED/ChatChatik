package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Eduard on 10/1/2017.
 */
public class MainServer {
    private static ServerSocket server;
    private static Socket socket;
    private static int serverPort;
    private static ArrayList<UserHandler> onlineUsers;
    private static ExcelDataUsers excelDataUsers;
    private static ServerWindow serverWindow;
    private static WindowServerSettings windowServerSettings;


    public static void main(String[] args) throws IOException {
        excelDataUsers = new ExcelDataUsers();
        setServerPort(excelDataUsers.getPort());
        server = new ServerSocket(serverPort);
        onlineUsers = new ArrayList<UserHandler>();
        windowServerStage();



        //create user thread and add in dataOnline users
        while (true) try {
            Thread.sleep(10);
            socket = server.accept();

            //checker free temporary unauthorization idLogin
            int id = 1;
            String temporaryLogin = "new:" + id;
            boolean idFree;
            if (onlineUsers.size() != 0) {
                do {
                    idFree = true;
                    for (UserHandler uh : onlineUsers
                            ) {
                        if (uh.getLogin().equals(temporaryLogin)) idFree = false;
                    }
                    if (idFree == false) {
                        id++;
                        temporaryLogin = "new:" + id;
                    }
                } while (idFree == false);
            }
            onlineUsers.add(new UserHandler(socket, temporaryLogin));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized boolean checkerAuthorizationExcel(String login, String password) {
        return excelDataUsers.foundAndCheckLoginAndPassword(login, password);
    }

    public static synchronized boolean deleteAccountant(String login, String password) {
        return excelDataUsers.deleteAccount(login, password);
    }

    //Send any message to all new users
    public synchronized static void distributionToOnlineUsers(String message) throws Exception {
        for (UserHandler ou : onlineUsers
                ) {
            ou.sendMessage(message);
        }
    }

    public synchronized static void removeFromOnlineUsers(String login) {
        int sizeData = onlineUsers.size();
        for (int i = 0; i < sizeData; i++) {
            if (login.equals(onlineUsers.get(i).getLogin())) {
                onlineUsers.remove(i);
                break;
            }
        }
    }

    public synchronized static boolean signUpNewUser(String login, String password) {
        return excelDataUsers.signUp(login, password);
    }

    public static void windowServerStage() {
        serverWindow = new ServerWindow();
        serverWindow.setVisible(true);


        serverWindow.getClose().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        serverWindow.getSettings().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverSettingsStage();
            }
        });
    }

    public static void serverSettingsStage() {
        windowServerSettings = new WindowServerSettings();
        windowServerSettings.setVisible(true);
        windowServerSettings.getPortField().setText(String.valueOf(getServerPort()));

        windowServerSettings.getAcceptButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    excelDataUsers.setPort(windowServerSettings.getPortField().getText());
                    System.exit(0);
            }
        });

        windowServerSettings.getCloseButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowServerSettings.setVisible(false);
            }
        });

    }


//GETTER and SETTER
//-------------------------------------------------------------------------------------------

    public synchronized static ArrayList<UserHandler> getOnlineUsers() {
        return onlineUsers;
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static void setServerPort(int serverPort) {
        MainServer.serverPort = serverPort;
    }
}

