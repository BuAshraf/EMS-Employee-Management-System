package org.EMS.service;

import org.EMS.model.Employee;

public class AddEmployee {
    private final EmployeeService employeeService;

    public AddEmployee(EmployeeService employeeService){
        this.employeeService =employeeService;
    }


    public void addEmployee(Employee employee){
        employeeService.getEmployees().add(employee);
        System.out.println("Employee added successfully:  "+employee);
    }

}
