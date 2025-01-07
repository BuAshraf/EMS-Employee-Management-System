package org.EMS.persistence;

import org.EMS.model.Employee;

import java.util.List;

public interface EmployeeFileHandler {
    void saveEmployeesToFile(List<Employee> employees);
    List<Employee> loadEmployeesFromFile();
}
