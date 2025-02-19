package org.EMS.BackEnd.persistence;

import org.EMS.BackEnd.model.Employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveEmployeesToGoogleSheet implements EmployeeFileHandler {
    private final GoogleSheetsService googleSheetsService;
    private final String range;

    public SaveEmployeesToGoogleSheet(GoogleSheetsService googleSheetsService, String range) {
        this.googleSheetsService = googleSheetsService;
        this.range = range;
    }

    @Override
    public void saveEmployeesToFile(List<Employee> employees) {
        List<List<Object>> data = new ArrayList<>();

        // Convert Employee objects to Google Sheets-compatible format
        for (Employee employee : employees) {
            data.add(List.of(employee.getId(), employee.getName(), employee.getDepartment(), employee.getSalary()));
        }

        try {
            // Write data to Google Sheets
            googleSheetsService.writeData(range, data);
            System.out.println("Employees saved successfully to Google Sheets.");
        } catch (IOException e) {
            System.err.println("Error saving employees to Google Sheets: " + e.getMessage());
        }
    }

    @Override
    public List<Employee> loadEmployeesFromFile() {
        throw new UnsupportedOperationException("Use LoadEmployeesFromGoogleSheet for loading employees.");
    }
}
