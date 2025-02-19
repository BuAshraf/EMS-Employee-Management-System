package org.EMS.BackEnd.service;

import org.EMS.BackEnd.persistence.GoogleSheetsService;
import org.EMS.BackEnd.utils.GoogleSheetsUtils;

public class ListEmployeesWithValidation {
    private final EmployeeService employeeService;
    private final GoogleSheetsService googleSheetsService;
    private final String headerRange;

    public ListEmployeesWithValidation(EmployeeService employeeService, GoogleSheetsService googleSheetsService, String headerRange) {
        this.employeeService = employeeService;
        this.googleSheetsService = googleSheetsService;
        this.headerRange = headerRange;
    }

    public void listEmployees() {
        if (!GoogleSheetsUtils.validateSheetHeaders(
                googleSheetsService.getSheetsInstance(),  // ✅ FIX: Get the Sheets instance
                googleSheetsService.getSpreadsheetId())) {  // ✅ FIX: Get the Spreadsheet ID

            System.out.println("❌ Error : Invalid sheet structure. Cannot list employees.");
            return;
        }

        employeeService.listEmployees();
        employeeService.saveEmployees();
        System.out.println("✅ Employees synced to Google Sheets.");
    }
}
