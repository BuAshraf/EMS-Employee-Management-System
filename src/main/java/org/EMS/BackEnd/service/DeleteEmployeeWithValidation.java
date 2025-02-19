package org.EMS.BackEnd.service;

import org.EMS.BackEnd.persistence.GoogleSheetsService;
import org.EMS.BackEnd.utils.GoogleSheetsUtils;

public class DeleteEmployeeWithValidation {
    private final EmployeeService employeeService;
    private final GoogleSheetsService googleSheetsService;
    private final String headerRange;

    public DeleteEmployeeWithValidation(EmployeeService employeeService, GoogleSheetsService googleSheetsService, String headerRange) {
        this.employeeService = employeeService;
        this.googleSheetsService = googleSheetsService;
        this.headerRange = headerRange;
    }

    public void deleteEmployee(int id) {
        // ✅ Use getSheetsInstance() and getSpreadsheetId() to pass correct arguments
        if (!GoogleSheetsUtils.validateSheetHeaders(
                googleSheetsService.getSheetsInstance(),  // ✅ FIX: Get the Sheets instance
                googleSheetsService.getSpreadsheetId())) {  // ✅ FIX: Get the Spreadsheet ID
            System.out.println("❌ Error: Invalid sheet structure. Cannot delete employee.");
            return;
        }

        boolean removed = employeeService.deleteEmployeeById(id);

        if (removed) {
            System.out.println("✅ Employee with ID " + id + " was deleted successfully.");
        } else {
            System.out.println("❌ No employee found with ID " + id + ".");
        }
        employeeService.saveEmployees();
        System.out.println("✅ Employees synced to Google Sheets.");
    }

}
