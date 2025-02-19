//package org.EMS;
//
//import org.EMS.model.Employee;
//import org.EMS.persistence.GoogleSheetsService;
//import org.EMS.service.EmployeeService;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class EmployeeServiceTest {
//
//    @Test
//    public void testAddEmployee() throws GeneralSecurityException, IOException {
//        GoogleSheetsService sheetsService = new GoogleSheetsService("1jON4npL3MPJoahVn0rfjXpcqzdLNs1cAlsrCI6F60Ds");
//        EmployeeService employeeService = new EmployeeService(sheetsService);
//
//        Employee employee = new Employee(1, "John Doe", "IT", 5000.0);
//        employeeService.addEmployee(employee);
//
//        assertTrue(employeeService.getEmployees().contains(employee), "Employee should be added.");
//    }
//
//    @Test
//    public void testListEmployees() throws GeneralSecurityException, IOException {
//        GoogleSheetsService sheetsService = new GoogleSheetsService("1jON4npL3MPJoahVn0rfjXpcqzdLNs1cAlsrCI6F60Ds");
//        EmployeeService employeeService = new EmployeeService(sheetsService);
//
//        employeeService.listEmployees();
//        // Add assertions if testing specific output
//    }
//
//    @Test
//    public void testDeleteEmployee() throws GeneralSecurityException, IOException {
//        GoogleSheetsService sheetsService = new GoogleSheetsService("test-spreadsheet-id");
//        EmployeeService employeeService = new EmployeeService(sheetsService);
//
//        Employee employee = new Employee(1, "John Doe", "IT", 5000.0);
//        employeeService.addEmployee(employee);
//        employeeService.deleteEmployeeById(1);
//
//        assertFalse(employeeService.getEmployees().contains(employee), "Employee should be deleted.");
//    }
//}

package org.EMS;

import org.EMS.BackEnd.model.Employee;
import org.EMS.BackEnd.persistence.GoogleSheetsService;
import org.EMS.BackEnd.service.EmployeeService;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeServiceTest {

    private static EmployeeService employeeService;

    @BeforeAll
    public static void setUp() throws GeneralSecurityException, IOException {
        String testSpreadsheetId = "1jON4npL3MPJoahVn0rfjXpcqzdLNs1cAlsrCI6F60Ds"; // 🔹 Replace with actual test sheet ID
        GoogleSheetsService sheetsService = new GoogleSheetsService(testSpreadsheetId);
        employeeService = new EmployeeService(sheetsService);
    }

    @Test
    @Order(1)
    public void testAddEmployee() {
        Employee employee = new Employee(101, "John Doe", "IT", 5000.0);
        employeeService.addEmployee(employee);

        List<Employee> employees = employeeService.getEmployees();
        assertTrue(employees.contains(employee), "❌ Employee should be added in Google Sheets.");
        System.out.println("✅ testAddEmployee PASSED");
    }

    @Test
    @Order(2)
    public void testListEmployees() {
        List<Employee> employees = employeeService.getEmployees();
        assertNotNull(employees, "❌ Employees list should not be null.");
        assertFalse(employees.isEmpty(), "❌ Employees list should not be empty.");
        System.out.println("✅ testListEmployees PASSED");
    }

    @Test
    @Order(3)
    public void testUpdateEmployee() {
        Employee updatedEmployee = new Employee(101, "John Smith", "Engineering", 7000.0);
        employeeService.updateEmployee(101, "John Smith", "Engineering", 7000.0);

        List<Employee> employees = employeeService.getEmployees();
        assertTrue(employees.contains(updatedEmployee), "❌ Employee update failed in Google Sheets.");
        System.out.println("✅ testUpdateEmployee PASSED");
    }

    @Test
    @Order(4)
    public void testDeleteEmployee() {
        boolean isDeleted = employeeService.deleteEmployeeById(101);

        assertTrue(isDeleted, "❌ Employee deletion failed! Employee with ID 101 should exist before deletion.");

        List<Employee> employees = employeeService.getEmployees();
        assertFalse(
                employees.stream().anyMatch(emp -> emp.getId() == 101),
                "❌ Employee with ID 101 should not exist after deletion."
        );

        System.out.println("✅ testDeleteEmployee PASSED");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("✅ All tests completed!");
    }
}
