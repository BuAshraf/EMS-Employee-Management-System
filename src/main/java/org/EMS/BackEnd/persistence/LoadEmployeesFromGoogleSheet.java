package org.EMS.BackEnd.persistence;

import org.EMS.BackEnd.model.Employee;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadEmployeesFromGoogleSheet implements EmployeeFileHandler {
    private final GoogleSheetsService googleSheetsService;
    private final String range;

    public LoadEmployeesFromGoogleSheet(GoogleSheetsService googleSheetsService, String range) {
        this.googleSheetsService = googleSheetsService;
        this.range = range;
    }

    @Override
    public List<Employee> loadEmployeesFromFile() {
        List<Employee> employees = new ArrayList<>();
        try {
            List<List<Object>> data = googleSheetsService.readData(range);
            if (data == null || data.isEmpty()) {
                System.err.println("No data found in Google Sheets or incorrect sheet name/range.");
                return employees; // Return empty list instead of null
            }

            for (List<Object> row : data) {
                try {
                    if (row.size() >= 4) {
                        int id = Integer.parseInt(row.get(0).toString());  // ID (Column B)
                        String name = row.get(1).toString().trim(); // Name (Column C) // Remove extra spaces
                        String department = row.get(2).toString().trim(); // Department (Column D)
                        double salary = Double.parseDouble(row.get(3).toString());  // Salary (Column E)

                        employees.add(new Employee(id, name, department, salary));
                    } else {
                        System.out.println("Skipping incomplete row: " + row);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing row: " + row + " - " + e.getMessage());
                }
            }
            System.out.println("Employees loaded successfully from Google Sheets.");
        } catch (IOException e) {
            System.err.println("Error loading employees from Google Sheets: " + e.getMessage());
        }
        return employees;
    }

    @Override
    public void saveEmployeesToFile(List<Employee> employees) {
        throw new UnsupportedOperationException("Use SaveEmployeesToGoogleSheet for saving employees.");
    }
}
