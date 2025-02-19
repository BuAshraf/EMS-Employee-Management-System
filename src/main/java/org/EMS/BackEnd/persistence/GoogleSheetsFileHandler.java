package org.EMS.BackEnd.persistence;

import org.EMS.BackEnd.model.Employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleSheetsFileHandler implements EmployeeFileHandler {
    private final GoogleSheetsService googleSheetsService;
    private final String range;

    public GoogleSheetsFileHandler(GoogleSheetsService googleSheetsService, String range) {
        this.googleSheetsService = googleSheetsService;
        this.range = range;
    }

    @Override
    public List<Employee> loadEmployeesFromFile() {
        List<Employee> employees = new ArrayList<>();
        try {
            List<List<Object>> data = googleSheetsService.readData(range);
            if (data == null || data.isEmpty()) {
                System.out.println("No employee data found in Google Sheets.");
                return employees;
            }

            for (List<Object> row : data) {
                try {
                    if (row.size() >= 4) {
                        int id = Integer.parseInt(row.get(0).toString());  // ID (Column A)
                        String name = row.get(1).toString();  // Name (Column B)
                        String department = row.get(2).toString();  // Department (Column C)
                        double salary = Double.parseDouble(row.get(3).toString());  // Salary (Column D)

                        employees.add(new Employee(id, name, department, salary));
                    } else {
                        System.out.println("Skipping incomplete row: " + row);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing row " + row + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from Google Sheets: " + e.getMessage());
        }

        return employees;
    }

    @Override
    public void saveEmployeesToFile(List<Employee> employees) {
        List<List<Object>> data = new ArrayList<>();
        for (Employee employee : employees) {
            data.add(List.of(employee.getId(), employee.getName(), employee.getDepartment(), employee.getSalary()));
        }

        try {
            googleSheetsService.writeData(range, data);
            System.out.println("Employees saved successfully to Google Sheets.");
        } catch (IOException e) {
            System.err.println("Error writing to Google Sheets: " + e.getMessage());
        }
    }
}
