package org.EMS;

import org.EMS.BackEnd.GUI.EmployeeManagementGUI;
import org.EMS.BackEnd.persistence.GoogleSheetsService;
import org.EMS.BackEnd.service.*;

import javax.swing.*;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // 🔹 Set Your Google Sheets ID Here
                String spreadsheetId = "1jON4npL3MPJoahVn0rfjXpcqzdLNs1cAlsrCI6F60Ds";

                // ✅ Initialize Google Sheets Service
                GoogleSheetsService googleSheetsService = new GoogleSheetsService(spreadsheetId);

                // ✅ Initialize Employee Services
                EmployeeService employeeService = new EmployeeService(googleSheetsService);
                AddEmployeeWithValidation addService = new AddEmployeeWithValidation(employeeService, googleSheetsService, "Sheet1!B1:E1");
                DeleteEmployeeWithValidation deleteService = new DeleteEmployeeWithValidation(employeeService, googleSheetsService, "Sheet1!B1:E1");
                ListEmployeesWithValidation listService = new ListEmployeesWithValidation(employeeService, googleSheetsService, "Sheet1!B1:E1");
                UpdateEmployeeWithValidation updateService = new UpdateEmployeeWithValidation(employeeService, googleSheetsService, "Sheet1!B1:E1");

                // ✅ Launch GUI
                new EmployeeManagementGUI(employeeService, addService, deleteService, listService, updateService, googleSheetsService);

            } catch (GeneralSecurityException | IOException e) {
                e.printStackTrace();
                System.err.println("❌ Error initializing application: " + e.getMessage());
            }
        });
    }
}
