package org.EMS;

import org.EMS.model.Employee;
import org.EMS.service.*;
import org.EMS.persistence.LoadEmployeesFromFile;
import org.EMS.persistence.SaveEmployeesToFile;
import org.EMS.service.EmployeeService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String excelFilePath = "employees.txt";

        // Use  handlers  of text file handlers
        SaveEmployeesToFile saveHandler = new SaveEmployeesToFile(excelFilePath);
        LoadEmployeesFromFile loadHandler = new LoadEmployeesFromFile(excelFilePath);
        EmployeeService sharedService = new EmployeeService(saveHandler, loadHandler);

        AddEmployee addService = new AddEmployee(sharedService);
        ListEmployees listService = new ListEmployees(sharedService);
        UpdateEmployee updateService = new UpdateEmployee(sharedService);
        DeleteEmployee deleteService = new DeleteEmployee(sharedService);


        // Load existing employees from the  file on startup
        //sharedService.loadEmployees();// Not needed unless reloading is required

        // Save employees to  file on shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(sharedService::saveEmployees));

        while (true) {
            System.out.println("\n=== Welcome to EMS 🫡 ===");
            System.out.println("\n=== Employee Management System ===");
            System.out.println("1. Add Employee");
            System.out.println("2. List Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Department: ");
                    String department = scanner.nextLine();
                    System.out.print("Enter Salary: ");
                    double salary = scanner.nextDouble();
                    addService.addEmployee(new Employee(id, name, department, salary));
                }
                case 2 -> listService.displayEmployees();
                case 3 -> {
                    System.out.print("Enter ID of the employee to update: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter New Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter New Department: ");
                    String department = scanner.nextLine();
                    System.out.print("Enter New Salary: ");
                    double salary = scanner.nextDouble();
                    updateService.updateEmployee(id, name, department, salary);
                }
                case 4 -> {
                    System.out.print("Enter ID of the employee to delete: ");
                    int id = scanner.nextInt();
                    deleteService.deleteEmployee(id);
                }
                case 5 -> {
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
