package org.EMS.BackEnd.service;

import org.EMS.BackEnd.model.Employee;
import org.EMS.BackEnd.persistence.DataPersistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private final List<Employee> employees;
    private final DataPersistence dataPersistence;

    public EmployeeService(DataPersistence dataPersistence) {
        this.dataPersistence = dataPersistence;
        this.employees = new ArrayList<>();
        loadEmployees();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

//    public boolean deleteEmployeeById(int id) {
//        Employee toRemove = employees.stream()
//                .filter(emp -> emp.getId() == id)
//                .findFirst()
//                .orElse(null);
//
//        if (toRemove != null) {
//            employees.remove(toRemove);
//            saveEmployees();
//            System.out.println("Employee with ID " + id + " deleted successfully.");
//            return true;
//        } else {
//            System.out.println("Employee with ID " + id + " not found.");
//            return false;
//        }
//    }

    public boolean deleteEmployeeById(int id) {
        boolean removed = employees.removeIf(emp -> emp.getId() == id);

        if (removed) {
            saveEmployees(); // 🔹 Persist the changes to Google Sheets
            System.out.println("✅ Employee with ID " + id + " deleted successfully.");
        } else {
            System.out.println("❌ Employee with ID " + id + " not found.");
        }

        return removed;
    }

    public void updateEmployee(int id, String name, String department, double salary) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                employee.setName(name);
                employee.setDepartment(department);
                employee.setSalary(salary);
                System.out.println("Employee updated successfully: " + employee);
                saveEmployees();
                return;
            }
        }
        System.out.println("Employee with ID " + id + " not found.");
    }

    public void listEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("Employee List:");
            employees.forEach(System.out::println);
        }
    }

    public void loadEmployees() {
        employees.clear(); // ✅ Clear existing list

        try {
            List<List<Object>> data = dataPersistence.readData("Sheet1!A2:E"); // ✅ Start from row 2 (skip headers)

            if (data == null || data.isEmpty()) {
                System.out.println("⚠ No employee data found. Starting with an empty list.");
                return;
            }

            for (List<Object> row : data) {
                if (row.size() < 5) {  // ✅ Ensure correct format (5 columns)
                    System.out.println("⚠ Invalid row format: " + row);
                    continue;  // Skip invalid rows
                }

                try {
                    int id = Integer.parseInt(row.get(1).toString().trim());  // ✅ Use Column B for Employee ID
                    String name = row.get(2).toString().trim();
                    String department = row.get(3).toString().trim();
                    double salary = Double.parseDouble(row.get(4).toString().trim());

                    employees.add(new Employee(id, name, department, salary));
                } catch (NumberFormatException e) {
                    System.out.println("⚠ Error parsing employee data: " + row + " - Skipping entry.");
                }
            }

            System.out.println("✅ Employees loaded successfully.");
        } catch (IOException e) {
            System.err.println("❌ Error loading employees: " + e.getMessage());
        }
    }


    public void saveEmployees() {
        List<List<Object>> values = new ArrayList<>();

        // ✅ Fix: First row is headers
        values.add(List.of("#", "ID", "Name", "Department", "Salary"));

        int rowNumber = 1; // ✅ Correctly set row numbers
        for (Employee employee : employees) {
            values.add(List.of(
                    rowNumber, // ✅ This is "#" column
                    employee.getId(), // ✅ This is "ID" column
                    employee.getName(),
                    employee.getDepartment(),
                    employee.getSalary()
            ));
            rowNumber++; // Increment row number
        }

        try {
            dataPersistence.writeData("Sheet1!A1:E" + values.size(), values);
            System.out.println("✅ Employees saved successfully to Google Sheets.");
        } catch (IOException e) {
            System.err.println("❌ Error saving employees: " + e.getMessage());
        }
    }


    public List<Employee> getEmployees() {
        return new ArrayList<>(employees); // Return a copy to avoid external modifications
    }
}
