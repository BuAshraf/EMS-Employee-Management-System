package org.EMS.BackEnd.persistence;

import org.EMS.BackEnd.model.Employee;

import java.util.List;

public interface EmployeeFileHandler {
    /**
     * Load employee data from a data source.
     *
     * @return a list of employees.
     */
    List<Employee> loadEmployeesFromFile();

    /**
     * Save employee data to a data source.
     *
     * @param employees the list of employees to save.
     */
    void saveEmployeesToFile(List<Employee> employees);
}
