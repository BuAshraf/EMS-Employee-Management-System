package org.EMS.service;

import org.EMS.model.Employee;

public class UpdateEmployee {
    private final EmployeeService employeeService;

    public UpdateEmployee(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void updateEmployee(int id, String name, String department, double salary) {
        boolean updated = employeeService.updateEmployeeById(id, name, department, salary);

        if (updated) {
            System.out.println("Employee updated successfully.");
        } else {
            System.out.println("No employee found with ID " + id + ".");
        }
    }
}
