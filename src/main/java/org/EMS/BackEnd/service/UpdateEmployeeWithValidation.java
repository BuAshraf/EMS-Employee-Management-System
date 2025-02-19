package org.EMS.BackEnd.service;

import org.EMS.BackEnd.persistence.GoogleSheetsService;
import org.EMS.BackEnd.utils.GoogleSheetsUtils;

public class UpdateEmployeeWithValidation {
    private final EmployeeService employeeService;
    private final GoogleSheetsService googleSheetsService;
    private final String headerRange;

    public UpdateEmployeeWithValidation(EmployeeService employeeService, GoogleSheetsService googleSheetsService, String headerRange) {
        this.employeeService = employeeService;
        this.googleSheetsService = googleSheetsService;
        this.headerRange = headerRange;
    }

    public void updateEmployee(int id, String name, String department, double salary) {
        if (!GoogleSheetsUtils.validateSheetHeaders(
                googleSheetsService.getSheetsInstance(),  // ✅ FIX: Get the Sheets instance
                googleSheetsService.getSpreadsheetId())) {  // ✅ FIX: Get the Spreadsheet ID
            System.out.println("❌ Error: Invalid sheet structure. Cannot update employee.");
            return;
        }

        employeeService.updateEmployee(id, name, department, salary);
        System.out.println("Employee with ID " + id + " updated successfully.");
        employeeService.saveEmployees();
        System.out.println("✅ Employees synced to Google Sheets.");
    }
}
