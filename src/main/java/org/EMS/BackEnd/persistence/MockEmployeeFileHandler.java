package org.EMS.BackEnd.persistence;

import org.EMS.BackEnd.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class MockEmployeeFileHandler implements EmployeeFileHandler {
    private final List<Employee> mockData;

    public MockEmployeeFileHandler() {
        this.mockData = new ArrayList<>();
    }

    @Override
    public List<Employee> loadEmployeesFromFile() {
        // Return a copy to prevent modification of the internal data
        return new ArrayList<>(mockData);
    }

    @Override
    public void saveEmployeesToFile(List<Employee> employees) {
        // Clear existing data and add the new list
        mockData.clear();
        mockData.addAll(employees);
        System.out.println("Mock: Employees saved to mock storage.");
    }

    // Additional methods for testing
    public int getEmployeeCount() {
        return mockData.size();
    }

    public void clearMockData() {
        mockData.clear();
    }
}
