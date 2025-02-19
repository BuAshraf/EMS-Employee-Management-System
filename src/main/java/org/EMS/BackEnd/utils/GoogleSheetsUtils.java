package org.EMS.BackEnd.utils;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoogleSheetsUtils {
    /**
     * Reads data from the specified range in Google Sheets.
     */
    public static List<List<Object>> readData(Sheets sheetsService, String spreadsheetId, String range) throws IOException {
        ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();
        return response.getValues();
    }
    /**
     * Writes data to the specified range in Google Sheets.
     */
    public static void writeData(Sheets sheetsService, String spreadsheetId, String range, List<List<Object>> values) throws IOException {
        ValueRange body = new ValueRange().setValues(values);
        sheetsService.spreadsheets().values().update(spreadsheetId, range, body)
                .setValueInputOption("RAW").execute();
        System.out.println("✅ Data successfully written to: " + range);
    }
    /**
     * Retrieves the first sheet ID in the spreadsheet.
     */
    public static Integer getFirstSheetId(Sheets sheetsService, String spreadsheetId) throws IOException {
        List<Sheet> sheets = sheetsService.spreadsheets().get(spreadsheetId).execute().getSheets();
        return sheets.isEmpty() ? null : sheets.get(0).getProperties().getSheetId();
    }
    /**
     * Creates a new sheet with the given name.
     */
    public static Integer createSheet(Sheets sheetsService, String spreadsheetId, String sheetName) throws IOException {
        List<Sheet> sheets = sheetsService.spreadsheets().get(spreadsheetId).execute().getSheets();

        // Check if the sheet already exists
        for (Sheet sheet : sheets) {
            if (sheet.getProperties().getTitle().equalsIgnoreCase(sheetName)) {
                System.out.println("❌ Sheet '" + sheetName + "' already exists.");
                return null;
            }
        }

        // Create new sheet
        SheetProperties newSheetProperties = new SheetProperties().setTitle(sheetName);
        AddSheetRequest addSheetRequest = new AddSheetRequest().setProperties(newSheetProperties);
        Request createSheetRequest = new Request().setAddSheet(addSheetRequest);

        BatchUpdateSpreadsheetRequest batchRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(Collections.singletonList(createSheetRequest));

        BatchUpdateSpreadsheetResponse response = sheetsService.spreadsheets().batchUpdate(spreadsheetId, batchRequest).execute();
        System.out.println("✅ Sheet '" + sheetName + "' created successfully.");

        return response.getReplies().get(0).getAddSheet().getProperties().getSheetId();
    }
    /**
     * Initializes the sheet structure by setting headers.
     */
    public static void initializeSheetStructure(Sheets sheetsService, String spreadsheetId, String sheetName) throws IOException {
        List<List<Object>> headers = List.of(List.of("#", "ID", "Name", "Department", "Salary"));
        String range = sheetName + "!A1:E1";

        // Write headers
        ValueRange body = new ValueRange().setValues(headers);
        sheetsService.spreadsheets().values().update(spreadsheetId, range, body)
                .setValueInputOption("RAW").execute();
        System.out.println("✅ Headers set in sheet: " + sheetName);

        // Apply formatting
        Integer sheetId = getSheetId(sheetsService, spreadsheetId, sheetName);
        if (sheetId != null) {
            applyHeaderFormatting(sheetsService, spreadsheetId, String.valueOf(sheetId));
        }
    }
    /**
     * Applies header formatting (bold, centered, background color).
     */
    public static void applyHeaderFormatting(Sheets sheetsService, String spreadsheetId, String sheetName) throws IOException {
        Integer sheetId = getSheetId(sheetsService, spreadsheetId, sheetName);
        if (sheetId == null) {
            System.err.println("❌ Error: Sheet '" + sheetName + "' not found.");
            return;
        }

        Request formatRequest = new Request().setRepeatCell(new RepeatCellRequest()
                .setRange(new GridRange()
                        .setSheetId(sheetId)
                        .setStartRowIndex(0)
                        .setEndRowIndex(1)
                        .setStartColumnIndex(0)
                        .setEndColumnIndex(5))
                .setCell(new CellData().setUserEnteredFormat(new CellFormat()
                        .setBackgroundColor(new Color().setRed(0.9f).setGreen(0.9f).setBlue(0.9f)) // Light gray
                        .setHorizontalAlignment("CENTER")
                        .setTextFormat(new TextFormat().setBold(true).setFontSize(12))))
                .setFields("userEnteredFormat(backgroundColor, textFormat, horizontalAlignment)"));

        BatchUpdateSpreadsheetRequest batchRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(Collections.singletonList(formatRequest));

        sheetsService.spreadsheets().batchUpdate(spreadsheetId, batchRequest).execute();
        System.out.println("✅ Header formatting applied to sheet: " + sheetName);
    }

    /**
     * Retrieves sheet ID by name.
     */
    public static Integer getSheetId(Sheets sheetsService, String spreadsheetId, String sheetName) throws IOException {
        List<Sheet> sheets = sheetsService.spreadsheets().get(spreadsheetId).execute().getSheets();
        for (Sheet sheet : sheets) {
            if (sheet.getProperties().getTitle().equalsIgnoreCase(sheetName)) {
                return sheet.getProperties().getSheetId();
            }
        }
        return null;
    }
    /**
     * Deletes a sheet by name.
     */
    public static void deleteSheet(Sheets sheetsService, String spreadsheetId, String sheetName) throws IOException {
        Integer sheetId = getSheetId(sheetsService, spreadsheetId, sheetName);
        if (sheetId == null) {
            System.out.println("❌ Sheet '" + sheetName + "' not found.");
            return;
        }

        Request deleteRequest = new Request().setDeleteSheet(new DeleteSheetRequest().setSheetId(sheetId));
        BatchUpdateSpreadsheetRequest batchRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(Collections.singletonList(deleteRequest));

        sheetsService.spreadsheets().batchUpdate(spreadsheetId, batchRequest).execute();
        System.out.println("✅ Sheet '" + sheetName + "' deleted successfully.");
    }

    public static List<String> listSheets(Sheets sheetsService, String spreadsheetId) throws IOException {
        List<String> sheetNames = new ArrayList<>();
        Spreadsheet spreadsheet = sheetsService.spreadsheets().get(spreadsheetId).execute();

        for (Sheet sheet : spreadsheet.getSheets()) {
            sheetNames.add(sheet.getProperties().getTitle());
        }

        return sheetNames;
    }

    public static void protectSheet(Sheets sheetsService, String spreadsheetId, String sheetName, String email) throws IOException {
        Integer sheetId = getSheetId(sheetsService, spreadsheetId, sheetName);
        if (sheetId == null) {
            System.out.println("❌ Sheet '" + sheetName + "' not found.");
            return;
        }

        Request request = new Request().setAddProtectedRange(new AddProtectedRangeRequest()
                .setProtectedRange(new ProtectedRange()
                        .setRange(new GridRange().setSheetId(sheetId))
                        .setEditors(new Editors().setUsers(Collections.singletonList(email)))
                        .setDescription("Only " + email + " can edit this sheet.")));

        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(Collections.singletonList(request));

        sheetsService.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
        System.out.println("✅ Sheet '" + sheetName + "' is now protected for " + email + ".");
    }
    public static void renameSheet(Sheets sheetsService, String spreadsheetId, String oldName, String newName) throws IOException {
        Integer sheetId = getSheetId(sheetsService, spreadsheetId, oldName);
        if (sheetId == null) {
            System.out.println("❌ Sheet '" + oldName + "' not found.");
            return;
        }

        Request renameRequest = new Request()
                .setUpdateSheetProperties(new UpdateSheetPropertiesRequest()
                        .setProperties(new SheetProperties().setSheetId(sheetId).setTitle(newName))
                        .setFields("title"));

        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(Collections.singletonList(renameRequest));

        sheetsService.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
        System.out.println("✅ Sheet renamed from '" + oldName + "' to '" + newName + "'.");
    }
    public static void copySheetFormatting(Sheets sheetsService, String spreadsheetId, Integer sourceSheetId, Integer targetSheetId) throws IOException {
        Request copyFormatRequest = new Request()
                .setCopyPaste(new CopyPasteRequest()
                        .setSource(new GridRange()
                                .setSheetId(sourceSheetId)
                                .setStartRowIndex(0)
                                .setStartColumnIndex(0))
                        .setDestination(new GridRange()
                                .setSheetId(targetSheetId)
                                .setStartRowIndex(0)
                                .setStartColumnIndex(0))
                        .setPasteType("PASTE_FORMAT"));

        BatchUpdateSpreadsheetRequest formatBatchRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(Collections.singletonList(copyFormatRequest));

        sheetsService.spreadsheets().batchUpdate(spreadsheetId, formatBatchRequest).execute();
        System.out.println("✅ Copied formatting from sheet ID " + sourceSheetId + " to sheet ID " + targetSheetId);
    }
    public static boolean validateSheetHeaders(Sheets sheetsService, String spreadsheetId) {
        try {
            // ✅ Get first sheet name dynamically
            String firstSheetName = getFirstSheetName(sheetsService, spreadsheetId);
            if (firstSheetName == null) {
                System.err.println("❌ Error: No sheets found in spreadsheet.");
                return false;
            }

            // ✅ Define expected headers
            List<Object> expectedHeaders = List.of("#", "ID", "Name", "Department", "Salary");
            String headerRange = firstSheetName + "!A1:E1"; // Apply to first row

            // ✅ Fetch headers from the sheet
            List<List<Object>> headers = readData(sheetsService, spreadsheetId, headerRange);
            if (headers == null || headers.isEmpty() || headers.get(0).size() < expectedHeaders.size()) {
                System.out.println("❌ Error: Invalid or missing headers. Fixing now...");
                fixHeaders(sheetsService, spreadsheetId, headerRange, expectedHeaders, firstSheetName);
                return true;
            }

            // ✅ Check if headers match expected format
            if (!headers.get(0).equals(expectedHeaders)) {
                System.out.println("⚠ Warning: Headers do not match expected format. Fixing...");
                fixHeaders(sheetsService, spreadsheetId, headerRange, expectedHeaders, firstSheetName);
                return true;
            }

            System.out.println("✅ Sheet headers validated successfully.");
            return true;
        } catch (IOException e) {
            System.err.println("❌ Error validating sheet headers: " + e.getMessage());
            return false;
        }
    }
    public static String getFirstSheetName(Sheets sheetsService, String spreadsheetId) throws IOException {
        List<Sheet> sheets = sheetsService.spreadsheets().get(spreadsheetId).execute().getSheets();
        return sheets.isEmpty() ? null : sheets.get(0).getProperties().getTitle();
    }
    // Helper method to fix headers and format them
    private static void fixHeaders(Sheets sheetsService, String spreadsheetId, String headerRange, List<Object> expectedHeaders, String sheetName) throws IOException {
        ValueRange body = new ValueRange().setValues(List.of(expectedHeaders));
        sheetsService.spreadsheets().values().update(spreadsheetId, headerRange, body)
                .setValueInputOption("RAW").execute();

        // ✅ Apply formatting to header row
        applyHeaderFormatting(sheetsService, spreadsheetId, sheetName);

        System.out.println("✅ Headers fixed and updated.");
    }








}
