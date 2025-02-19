package org.EMS.persistence;


import org.EMS.model.Employee;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveEmployeesToFile implements EmployeeFileHandler {
    private final String filePath;

    public SaveEmployeesToFile(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void saveEmployeesToFile(List<Employee> employees) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Employee employee : employees) {
                writer.write(employee.getId() + "," +
                        employee.getName() + "," +
                        employee.getDepartment() + "," +
                        employee.getSalary());
                writer.newLine();
            }
            System.out.println("Employees saved successfully to file.");
        } catch (IOException e) {
            System.err.println("Error saving employees to file: " + e.getMessage());
        }
    }

    @Override
    public List<Employee> loadEmployeesFromFile() {
        throw new UnsupportedOperationException("Use LoadEmployeesFromFile for loading employees.");
    }
}
