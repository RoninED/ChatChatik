package server;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelDataUsers {
    private static String doYouKnowDeWay = new String(System.getProperty("user.dir") + "\\UsersData.xlsx");
    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;
    private static int row;
    private static int col;
    private static String message;

    public boolean foundAndCheckLoginAndPassword(String login, String password) {
        boolean result = false;

        for (int i = 0; i < workbook.getSheetAt(0).getLastRowNum() + 1; i++) {
            try {

                if (workbook.getSheetAt(0).getRow(i).getCell(0).getStringCellValue().equals(login)
                        & workbook.getSheetAt(0).getRow(i).getCell(1).getStringCellValue().equals(password)) {
                    result = true;
                    break;
                }
            } catch (NullPointerException npe) {
            }
        }
        return result;
    }

    public ExcelDataUsers() {
        createOrUseExistFile();
    }

    public static void createOrUseExistFile() {
        if (!new File(doYouKnowDeWay).exists()) {
            System.out.println("File does't exist");
            try (FileOutputStream fileOutputStream = new FileOutputStream(doYouKnowDeWay)) {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("1");
                workbook.write(fileOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File exist");
            try (FileInputStream fileInputStream = new FileInputStream(doYouKnowDeWay)) {
                workbook = new XSSFWorkbook(new FileInputStream(doYouKnowDeWay));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean signUp(String login, String password) {
        boolean result = true;

        for (int i = 0; i < workbook.getSheetAt(0).getLastRowNum() + 1; i++) {
            try {
                if (workbook.getSheetAt(0).getRow(i).getCell(0).getStringCellValue().equals(login)) {
                    result = false;
                    break;
                }
            } catch (NullPointerException npe) {
            }
        }

        if (result) {
            int freeRow = checkerFreeRowPlace();
            writeToExcel(login, 0, freeRow);
            writeToExcel(password, 1, freeRow);
        }

        return result;
    }


    public static void writeToExcel(String message, int col, int row) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(doYouKnowDeWay)) {
            try {
                sheet = workbook.getSheetAt(0);
                sheet.getRow(row).getCell(col).setCellValue(message);
            } catch (NullPointerException npe) {
                try {
                    sheet.getRow(row).createCell(col).setCellValue(message);
                } catch (NullPointerException npe2) {
                    sheet.createRow(row).createCell(col).setCellValue(message);
                }
            }
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readCell(int col, int row) {
        try {
            System.out.println(workbook.getSheetAt(0).getRow(row).getCell(col).getStringCellValue());
        } catch (NullPointerException npe) {
            System.out.println("");
        }

    }

    public static int checkerFreeRowPlace() {
        int tempA = 0;
        for (int i = 0; i < workbook.getSheetAt(0).getLastRowNum() + 2; i++) {
            try {
                if (workbook.getSheetAt(0).getRow(i).getCell(0).getStringCellValue().equals("")) {
                    tempA = i;
                    break;
                }
            } catch (NullPointerException npe) {
                tempA = i;
                break;
            }
        }
        return tempA;
    }

    public static void writeInFirstFreeCellRow(String message) {
        writeToExcel(message, 0, checkerFreeRowPlace());
    }

    public static void showAllIn0col() {

        for (int i = 0; i < workbook.getSheetAt(0).getLastRowNum() + 1; i++) {
            try {
                System.out.println(workbook.getSheetAt(0).getRow(i).getCell(0));
            } catch (NullPointerException npe) {
                System.out.println("-(empty)");
            }
        }
    }

    public static void deleteCell(int row, int col) {

        try (FileOutputStream fileOutputStream = new FileOutputStream(doYouKnowDeWay)) {
            FileInputStream fileInputStream = new FileInputStream(doYouKnowDeWay);
            workbook.getSheetAt(0).getRow(row).removeCell(workbook.getSheetAt(0).getRow(row).getCell(col));
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException npe) {
            System.out.println("-(The cell is already deleted");
        }
    }
}
