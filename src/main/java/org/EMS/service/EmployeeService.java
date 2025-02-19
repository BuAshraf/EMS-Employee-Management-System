//package org.EMS.service;
//
//import org.EMS.model.Employee;
//import org.EMS.persistence.EmployeeFileHandler;
//
//import java.util.List;
//
//public class EmployeeService {
//    private final List<Employee> employees;
//    private  final EmployeeFileHandler saveHandler;
//    private  final EmployeeFileHandler loadHandler;
//
//
//    public EmployeeService(EmployeeFileHandler saveHandler, EmployeeFileHandler loadHandler) {
//        this.employees = loadHandler.loadEmployeesFromFile();
//        this.saveHandler = saveHandler;
//        this.loadHandler = loadHandler;
//    }
//    public void addEmployee(Employee employee){employees.add(employee);}
//
//    public  void deleteEmployee(int id){employees.removeIf(employees -> employees.getId() == id);}
//
//    public List<Employee> getEmployees(){ return employees;}
//
//    public void saveEmployees(){saveHandler.saveEmployeesToFile(employees);}
//
//    public void loadEmployees(){throw new UnsupportedOperationException("Not supported yet. ");}
//}


package org.EMS.service;

import org.EMS.model.Employee;
import org.EMS.persistence.EmployeeFileHandler;

import java.util.List;

public class EmployeeService {
    private final List<Employee> employees;
    private final EmployeeFileHandler saveHandler;
    private final EmployeeFileHandler loadHandler;

    public EmployeeService(EmployeeFileHandler saveHandler, EmployeeFileHandler loadHandler) {
        this.saveHandler = saveHandler;
        this.loadHandler = loadHandler;
        this.employees = loadHandler.loadEmployeesFromFile(); // Load employees from file on initialization
    }

    public boolean deleteEmployeeById(int id) {
        return employees.removeIf(employee -> employee.getId() == id);
    }

    public boolean updateEmployeeById(int id, String name, String department, double salary) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                employee.setName(name);
                employee.setDepartment(department);
                employee.setSalary(salary);
                return true; // Update successful
            }
        }
        return false; // Employee not found
    }

    // Add an employee to the list
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    // Delete an employee by ID
    public void deleteEmployee(int id) {
        employees.removeIf(employee -> employee.getId() == id);
    }

    // Get the list of employees
    public List<Employee> getEmployees() {
        return employees;
    }

    // Save employees to file
    public void saveEmployees() {
        saveHandler.saveEmployeesToFile(employees);
    }

    // Reload employees from file (optional)
    public void loadEmployees() {
        employees.clear(); // Clear the current list
        employees.addAll(loadHandler.loadEmployeesFromFile()); // Reload from file
    }
}
