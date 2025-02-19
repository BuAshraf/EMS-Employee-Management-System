package org.EMS.service;

import org.EMS.model.Employee;

public class DeleteEmployee {
    private final EmployeeService employeeService;

    public DeleteEmployee(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void deleteEmployee(int id) {
        boolean removed = employeeService.deleteEmployeeById(id);

        if (removed) {
            System.out.println("Employee with ID " + id + " was deleted successfully.");
        } else {
            System.out.println("No employee found with ID " + id + ".");
        }
    }
}
