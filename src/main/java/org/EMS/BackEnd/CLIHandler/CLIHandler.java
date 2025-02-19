package org.EMS.BackEnd.CLIHandler;

import org.EMS.BackEnd.model.Employee;
import org.EMS.BackEnd.service.EmployeeService;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CLIHandler {
    private final EmployeeService employeeService;
    private final Scanner scanner;

    public CLIHandler(EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n===  Employee Management System ===");
            System.out.println("1. Add Employee");
            System.out.println("2. List Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            try {
                // 🔴 FIX: Check if there's valid input before reading
                if (!scanner.hasNextInt()) {
                    System.err.println("Invalid input. Please enter a number.");
                    scanner.next(); // Clear invalid input
                    continue;
                }

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> addEmployeeFlow();
                    case 2 -> employeeService.listEmployees();
                    case 3 -> updateEmployeeFlow();
                    case 4 -> deleteEmployeeFlow();
                    case 5 -> {
                        System.out.println("Exiting... Goodbye!");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Error: Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    private void addEmployeeFlow() {
        try {
            System.out.print("Enter ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Department: ");
            String department = scanner.nextLine();
            System.out.print("Enter Salary: ");
            double salary = scanner.nextDouble();
            scanner.nextLine();

            employeeService.addEmployee(new Employee(id, name, department, salary));
        } catch (InputMismatchException e) {
            System.err.println("Invalid input. Please enter correct values.");
            scanner.nextLine(); // Clear input buffer
        }
    }

    private void updateEmployeeFlow() {
        try {
            System.out.print("Enter ID of the employee to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter New Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter New Department: ");
            String department = scanner.nextLine();
            System.out.print("Enter New Salary: ");
            double salary = scanner.nextDouble();
            scanner.nextLine();

            employeeService.updateEmployee(id, name, department, salary);
        } catch (InputMismatchException e) {
            System.err.println("Invalid input. Please enter correct values.");
            scanner.nextLine(); // Clear input buffer
        }
    }

    private void deleteEmployeeFlow() {
        try {
            System.out.print("Enter ID of the employee to delete: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            boolean removed = employeeService.deleteEmployeeById(id);
            if (removed) {
                System.out.println("Employee with ID " + id + " deleted successfully.");
            } else {
                System.out.println("Employee with ID " + id + " not found.");
            }
        } catch (InputMismatchException e) {
            System.err.println("Invalid input. Please enter a valid ID.");
            scanner.nextLine(); // Clear input buffer
        }
    }
}
