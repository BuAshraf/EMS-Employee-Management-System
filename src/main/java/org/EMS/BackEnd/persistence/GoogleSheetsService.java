
package org.EMS.BackEnd.persistence;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import org.EMS.BackEnd.utils.GoogleSheetsUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GoogleSheetsService implements DataPersistence {
    private static final String APPLICATION_NAME = "EMS Employee Management System";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final Sheets sheetsService;
    private final String spreadsheetId;

    public GoogleSheetsService(String spreadsheetId) throws GeneralSecurityException, IOException {
        this.sheetsService = new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),  // ✅ No casting needed
                JSON_FACTORY,
                GoogleSheetsAuthenticator.getCredentials(GoogleNetHttpTransport.newTrustedTransport()) // ✅ Correct method call
        ).setApplicationName(APPLICATION_NAME).build();

        this.spreadsheetId = spreadsheetId;
    }



    @Override
    public List<List<Object>> readData(String range) throws IOException {
        // ✅ Validate headers before reading data (NO need for specific sheet name)
        if (!GoogleSheetsUtils.validateSheetHeaders(sheetsService, spreadsheetId)) {
            System.out.println("❌ Cannot proceed: Sheet headers are incorrect.");
            return Collections.emptyList();
        }

        ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();
        return response.getValues();
    }



    @Override
    public void writeData(String range, List<List<Object>> values) throws IOException {
        // ✅ Validate headers before writing data (NO need for specific sheet name)
        if (!GoogleSheetsUtils.validateSheetHeaders(sheetsService, spreadsheetId)) {
            System.out.println("❌ Cannot proceed: Sheet headers are incorrect.");
            return;
        }

        ValueRange body = new ValueRange().setValues(values);
        sheetsService.spreadsheets().values().update(spreadsheetId, range, body)
                .setValueInputOption("RAW").execute();
    }



    public void createSheetWithDateAndCopyFormat() throws IOException {
        String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "Employees_" + todayDate;

        Integer firstSheetId = GoogleSheetsUtils.getFirstSheetId(sheetsService, spreadsheetId);
        Integer newSheetId = GoogleSheetsUtils.createSheet(sheetsService, spreadsheetId, sheetName);

        if (newSheetId != null) {
            GoogleSheetsUtils.initializeSheetStructure(sheetsService, spreadsheetId, sheetName);
            if (firstSheetId != null) {
                GoogleSheetsUtils.copySheetFormatting(sheetsService, spreadsheetId, firstSheetId, newSheetId);
            }
        }
    }


    /**
     * ✅ Creates a new sheet and copies the format from "Sheet1".
     */
    public void createSheetWithFormat(String newSheetName) throws IOException {
        Integer firstSheetId = GoogleSheetsUtils.getSheetId(sheetsService, spreadsheetId, "Sheet1");
        Integer newSheetId = GoogleSheetsUtils.createSheet(sheetsService, spreadsheetId, newSheetName);

        if (newSheetId != null) {
            GoogleSheetsUtils.initializeSheetStructure(sheetsService, spreadsheetId, newSheetName);
            if (firstSheetId != null) {
                GoogleSheetsUtils.copySheetFormatting(sheetsService, spreadsheetId, firstSheetId, newSheetId);
                System.out.println("✅ Sheet '" + newSheetName + "' created and formatted based on 'Sheet1'.");
            } else {
                System.out.println("⚠️ 'Sheet1' not found. Creating a new sheet without formatting.");
            }
        }
    }

//    public void createSheet(String sheetName) throws IOException {
//        List<Sheet> sheets = sheetsService.spreadsheets().get(spreadsheetId).execute().getSheets();
//
//        // 🔍 Check if the sheet already exists
//        for (Sheet sheet : sheets) {
//            if (sheet.getProperties().getTitle().equalsIgnoreCase(sheetName)) {
//                System.out.println("❌ Sheet '" + sheetName + "' already exists.");
//                return;
//            }
//        }
//
//        // ➕ Create a new sheet
//        AddSheetRequest addSheetRequest = new AddSheetRequest().setProperties(new SheetProperties().setTitle(sheetName));
//        Request request = new Request().setAddSheet(addSheetRequest);
//        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest().setRequests(Collections.singletonList(request));
//
//        sheetsService.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
//        System.out.println("✅ Sheet '" + sheetName + "' created successfully.");
//    }

    public void deleteSheet(String sheetName) throws IOException {
        List<Sheet> sheets = sheetsService.spreadsheets().get(spreadsheetId).execute().getSheets();
        Integer sheetId = null;

        // 🔍 Find the sheet ID by name
        for (Sheet sheet : sheets) {
            if (sheet.getProperties().getTitle().equalsIgnoreCase(sheetName)) {
                sheetId = sheet.getProperties().getSheetId();
                break;
            }
        }

        if (sheetId == null) {
            System.out.println("❌ Sheet '" + sheetName + "' not found.");
            return;
        }

        // 🗑 Request to delete the sheet
        Request request = new Request().setDeleteSheet(new DeleteSheetRequest().setSheetId(sheetId));
        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest().setRequests(Collections.singletonList(request));

        sheetsService.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
        System.out.println("✅ Sheet '" + sheetName + "' deleted successfully.");
    }


    public void renameSheet(String oldName, String newName) throws IOException {
        GoogleSheetsUtils.renameSheet(sheetsService, spreadsheetId, oldName, newName);
    }
    public void protectSheet(String sheetName, String email) throws IOException {
        GoogleSheetsUtils.protectSheet(sheetsService, spreadsheetId, sheetName, email);
    }
    public List<String> listSheets() throws IOException {
        return GoogleSheetsUtils.listSheets(sheetsService, spreadsheetId);
    }



    public Sheets getSheetsInstance() {
        return sheetsService;
    }

    public String getSpreadsheetId() {
        return spreadsheetId;
    }

}
