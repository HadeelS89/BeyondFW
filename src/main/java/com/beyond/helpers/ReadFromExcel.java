package com.beyond.helpers;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;


public class ReadFromExcel {

    static XSSFWorkbook wb;
    static XSSFSheet sheet1;

    public ReadFromExcel(String Excelpath) throws IOException {
        // TODO Auto-generated method stub

        try {
            File file = new File(Excelpath);

            //Create an object of FileInputStream class to read excel file

            FileInputStream fis = new FileInputStream(file);
            wb = new XSSFWorkbook(fis);

        } catch (Exception e) {

        }
    }



    public static String getData1(String sheetName, String column) {
        sheet1 = wb.getSheet(sheetName);
        DataFormatter formatter = new DataFormatter();
        Random random = new Random();
        Iterator<Row> rows = sheet1.rowIterator();
        Row row1 = rows.next();
        //get columns size
        Iterator<Cell> cols = row1.cellIterator();

        int row = 0;
        XSSFCell cell = null;
        int col = 0;
        while (cols.hasNext()) {
            cell = sheet1.getRow(row).getCell(col);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell.getStringCellValue().equalsIgnoreCase(column)) {
                break;
            }
            col++;
        }

        int newRow = sheet1.getLastRowNum();
        int randomRow = ActionsHelper.getRandomNumber(1, newRow+1);

        cell = sheet1.getRow(randomRow).getCell(col);

        String data = formatter.formatCellValue(cell);
        return data;
    }
    public static String getDataWithoutRand(String sheetName, String column,int Row) {

        sheet1 = wb.getSheet(sheetName);
        DataFormatter formatter = new DataFormatter();
        //Random random = new Random();
        Iterator<Row> rows = sheet1.rowIterator();
        Row row1 = rows.next();
        //get columns size
        Iterator<Cell> cols = row1.cellIterator();

        int row = 0;
        XSSFCell cell = null;
        int col = 0;
        while (cols.hasNext()) {
            cell = sheet1.getRow(row).getCell(col);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell.getStringCellValue().equalsIgnoreCase(column)) {
                break;
            }
            col++;
        }

        int newRow = sheet1.getLastRowNum()+1;
        //int randomRow = ActionsHelper.getRandomNumber(1, newRow+1);

        cell = sheet1.getRow(Row).getCell(col);

        String data = formatter.formatCellValue(cell);
        return data;
    }

    public static HashMap<Integer, String> getDataWithoutRand1(String sheetName, String column,int Row) {
        HashMap<Integer, String> sheetMap = new HashMap<>();

        sheet1 = wb.getSheet(sheetName);
        DataFormatter formatter = new DataFormatter();
        //Random random = new Random();
        Iterator<Row> rows = sheet1.rowIterator();
        Row row1 = rows.next();
        //get columns size
        Iterator<Cell> cols = row1.cellIterator();

        int row = 0;
        XSSFCell cell = null;
        int col = 0;
        while (cols.hasNext()) {
            cell = sheet1.getRow(row).getCell(col);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell.getStringCellValue().equalsIgnoreCase(column)) {
                break;
            }
            col++;
        }

        int newRow = sheet1.getLastRowNum()+1;
        //int randomRow = ActionsHelper.getRandomNumber(1, newRow+1);

        cell = sheet1.getRow(Row).getCell(col);

        String data = formatter.formatCellValue(cell);
        sheetMap.put(Row, data);
        return sheetMap;
    }

    public static void main(String[] args) throws IOException {

        //System.out.println(ActionsHelper.getRandomNumber(0, 10));
        String value = ReadWriteHelper.readFromExcel("programData", "Configuration", "AgeRangeMax");
        System.out.println(value);
    }


    public int GetRowCount(String SheetName) {

        int row = wb.getSheet(SheetName).getLastRowNum();

        row = row + 1;

        return row;
    }
}