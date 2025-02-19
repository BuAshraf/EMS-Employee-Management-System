package org.EMS.BackEnd.service;

import org.EMS.BackEnd.persistence.GoogleSheetsService;
import org.EMS.BackEnd.model.Employee;
import org.EMS.BackEnd.utils.GoogleSheetsUtils;

public class AddEmployeeWithValidation {
    private final EmployeeService employeeService;
    private final GoogleSheetsService googleSheetsService;
    private final String headerRange;

    public AddEmployeeWithValidation(EmployeeService employeeService, GoogleSheetsService googleSheetsService, String headerRange) {
        this.employeeService = employeeService;
        this.googleSheetsService = googleSheetsService;
        this.headerRange = headerRange;
    }

    public void addEmployee(Employee employee) {
        // ✅ Pass only Sheets instance & spreadsheetId (NO header range)
        if (!GoogleSheetsUtils.validateSheetHeaders(
                googleSheetsService.getSheetsInstance(),  // ✅ FIX: Get the Sheets instance
                googleSheetsService.getSpreadsheetId())) {  // ✅ FIX: Get the Spreadsheet ID
            System.out.println("❌ Error: Invalid sheet structure. Cannot add employee.");
            return;
        }

        employeeService.addEmployee(employee);
        System.out.println("✅ Employee added successfully: " + employee);
        employeeService.saveEmployees();
        System.out.println("✅ Employees synced to Google Sheets.");
    }


}
