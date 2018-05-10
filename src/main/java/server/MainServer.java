package server;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Eduard on 10/1/2017.
 */
public class MainServer {
    private static ServerSocket server;
    private static Socket socket;
    private final static int SERVER_PORT = 8700;
    private static TemporarySQLdata[] dataUsers;
    private static ArrayList<UserHandler> onlineUsers;
    private static ExcelDataUsers excelDataUsers;


    public static void main(String[] args) throws IOException {
        server = new ServerSocket(SERVER_PORT);
        onlineUsers = new ArrayList<UserHandler>();
        excelDataUsers = new ExcelDataUsers();

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
        return excelDataUsers.signUp(login,password);
    }


//GETTER and SETTER
//-------------------------------------------------------------------------------------------


    public synchronized static TemporarySQLdata[] getDataUsers() {
        return dataUsers;
    }

    public synchronized static ArrayList<UserHandler> getOnlineUsers() {
        return onlineUsers;
    }

}

