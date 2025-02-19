package org.EMS.BackEnd.utils;

import org.EMS.BackEnd.persistence.GoogleSheetsService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class SheetsQuickstart {
    public static void main(String[] args) {
        try {
            GoogleSheetsService sheetsService = new GoogleSheetsService("1jON4npL3MPJoahVn0rfjXpcqzdLNs1cAlsrCI6F60Ds");
            String range = "Sheet1!B2:E";
            List<List<Object>> data = sheetsService.readData(range);

            if (data == null || data.isEmpty()) {
                System.out.println("No data found.");
            } else {
                for (List<Object> row : data) {
                    System.out.println(row);
                }
            }
        } catch (IOException | GeneralSecurityException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
