package org.EMS.persistence;

import org.EMS.model.Employee;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoadEmployeesFromFile implements EmployeeFileHandler {
    private final String filePath;

    public LoadEmployeesFromFile(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Employee> loadEmployeesFromFile() {
        List<Employee> employees = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No existing employee data found.");
            return employees;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    try {
                        int id = Integer.parseInt(data[0].trim());
                        String name = data[1].trim();
                        String department = data[2].trim();
                        double salary = Double.parseDouble(data[3].trim());
                        employees.add(new Employee(id, name, department, salary));
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing employee data: " + line);
                    }
                } else {
                    System.err.println("Invalid employee data: " + line);
                }
            }
            System.out.println("Employees loaded successfully from file.");
        } catch (IOException e) {
            System.err.println("Error reading employee file: " + e.getMessage());
        }

        return employees;
    }

    @Override
    public void saveEmployeesToFile(List<Employee> employees) {
        throw new UnsupportedOperationException("Use SaveEmployeesToFile for saving employees.");
    }
}
