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
    private final static int SERVER_PORT = 8989;
    private static TemporarySQLdata[] dataUsers;
    private static ArrayList<UserHandler> onlineUsers;
    public static String doYouKnowDeWay = new String(System.getProperty("user.dir") + "\\UsersData.xlsx");
    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;

    public static void main(String[] args) throws IOException {
        server = new ServerSocket(SERVER_PORT);
        onlineUsers = new ArrayList<UserHandler>();

        // Create temporary dataUsers


        //initiate data user into excel
        startDataUsersIntoExcel();


        //create user thread and add in dataOnline users
        while (true) try {
            Thread.sleep(10);
            socket = server.accept();

            //checker free temporary unauthorization idLogin
            int id = 1;
            String temporaryLogin = "new user " + id;
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
                        temporaryLogin = "new user " + id;
                    }
                } while (idFree == false);
            }
            onlineUsers.add(new UserHandler(socket, temporaryLogin));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void startDataUsersIntoExcel() {
        if (!new File(doYouKnowDeWay).exists()) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(doYouKnowDeWay)) {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Users");
                workbook.write(fileOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileInputStream fileInputStream = new FileInputStream(doYouKnowDeWay)) {
            workbook = new XSSFWorkbook(fileInputStream);
            Iterator<Row> iteratorRows = workbook.getSheetAt(0).rowIterator();
            while (iteratorRows.hasNext()) {
                Row row = iteratorRows.next();
                row.getCell(0).getStringCellValue();
                row.getCell(1).getStringCellValue();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException nul) {
            JOptionPane.showMessageDialog(null, "ошибка в файле с логинами и паролями");
            System.exit(0);
        }

    }

    public static synchronized boolean checkerAuthorizationExcel(String login, String password) {
        boolean result = false;
        try (FileInputStream fileInputStream = new FileInputStream(doYouKnowDeWay)) {
            workbook = new XSSFWorkbook(fileInputStream);
            Iterator<Row> iteratorRows = workbook.getSheetAt(0).rowIterator();
            while (iteratorRows.hasNext() | result == true) {
                Row row = iteratorRows.next();
                if (row.getCell(0).getStringCellValue().equals(login) & row.getCell(1).getStringCellValue().equals(password)) {
                    result = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException nul) {
            JOptionPane.showMessageDialog(null, "ошибка в файле с логинами и паролями");
        }
        return result;
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
        boolean result = true;
        System.out.println(login+" / " + password);
        try (FileInputStream fileInputStream = new FileInputStream(doYouKnowDeWay)) {
            workbook = new XSSFWorkbook(fileInputStream);
            Iterator<Row> iteratorRows = workbook.getSheetAt(0).rowIterator();
            while (iteratorRows.hasNext()) {
                Row row = iteratorRows.next();
                if (row.getCell(0).getStringCellValue().equals(login)) result = false;
            }
            if (result){
                sheet = workbook.getSheetAt(0);

                int freeRow = sheet.getLastRowNum()+1;
                try (FileOutputStream fileOutputStream = new FileOutputStream(doYouKnowDeWay)) {
                    workbook = new XSSFWorkbook(fileInputStream);
                    sheet.createRow(freeRow).createCell(0).setCellValue(login);
                    sheet.createRow(freeRow).createCell(1).setCellValue(password);
                    workbook.write(fileOutputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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

