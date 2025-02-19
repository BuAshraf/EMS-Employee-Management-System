package org.EMS.service;

import org.EMS.model.Employee;

public class ListEmployees {
    private final EmployeeService employeeService;


    public ListEmployees(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void displayEmployees(){
        if (employeeService.getEmployees().isEmpty()) {
            System.out.println(" No employees found!");
        }else {
            System.out.println("Employee List: ");
            for (Employee employee : employeeService.getEmployees()){
                System.out.println(employee);
            }
        }
    }
}
