package org.EMS.BackEnd.GUI;

import org.EMS.BackEnd.model.Employee;
import org.EMS.BackEnd.persistence.GoogleSheetsService;
import org.EMS.BackEnd.service.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class EmployeeManagementGUI {
    private final EmployeeService employeeService;
    private final AddEmployeeWithValidation addEmployeeService;
    private final DeleteEmployeeWithValidation deleteEmployeeService;
    private final ListEmployeesWithValidation listEmployeesService;
    private final UpdateEmployeeWithValidation updateEmployeeService;
    private final GoogleSheetsService googleSheetsService;
    private String activeSheet = "Sheet1";  // 🔹 Default active sheet

    private JFrame frame;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public EmployeeManagementGUI(EmployeeService employeeService,
                                 AddEmployeeWithValidation addEmployeeService,
                                 DeleteEmployeeWithValidation deleteEmployeeService,
                                 ListEmployeesWithValidation listEmployeesService,
                                 UpdateEmployeeWithValidation updateEmployeeService,
                                 GoogleSheetsService googleSheetsService) {
        this.employeeService = employeeService;
        this.addEmployeeService = addEmployeeService;
        this.deleteEmployeeService = deleteEmployeeService;
        this.listEmployeesService = listEmployeesService;
        this.updateEmployeeService = updateEmployeeService;
        this.googleSheetsService = googleSheetsService;

        createUI();
    }

    private void createUI() {
        frame = new JFrame("Employee Management System");

        // ✅ Set Logo (Make sure "logo.jpg" exists in `resources`)
        try {
            ImageIcon logo = new ImageIcon(getClass().getResource("/logo.jpg"));
            frame.setIconImage(logo.getImage());
        } catch (Exception e) {
            System.err.println("❌ Logo not found. Proceeding without it.");
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());

        // ✅ Create a Centered Title
        JLabel titleLabel = new JLabel("Employee Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Table to display employees
        String[] columns = {"ID", "Name", "Department", "Salary"};
        tableModel = new DefaultTableModel(columns, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(employeeTable);
        frame.add(tableScrollPane, BorderLayout.CENTER);

        // 🔍 Search Bar
        JPanel topPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchEmployee());
        topPanel.add(new JLabel("🔍 Search: "), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);
        frame.add(topPanel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4));
        addButton(buttonPanel, "➕ Add", this::addEmployee);
        addButton(buttonPanel, "✏️ Update", this::updateEmployee);
        addButton(buttonPanel, "🗑 Delete", this::deleteEmployee);
        addButton(buttonPanel, "🔄 Refresh", this::listEmployees);

        addButton(buttonPanel, "📄 New Sheet", this::createNewSheet);
        addButton(buttonPanel, "📋 List Sheets", this::listSheets);
        addButton(buttonPanel, "❌ Delete Sheet", this::deleteSheet);
        addButton(buttonPanel, "🔄 Choose Active Sheet", this::chooseActiveSheet);
        addButton(buttonPanel, "🚪 Exit", () -> System.exit(0));

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        listEmployees(); // Load employees initially
    }

    private void addButton(JPanel panel, String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(e -> action.run());
        panel.add(button);
    }




    private void searchEmployee() {
        String query = searchField.getText().trim().toLowerCase();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String name = tableModel.getValueAt(i, 1).toString().toLowerCase();
            if (name.contains(query)) {
                employeeTable.setRowSelectionInterval(i, i);
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "❌ Employee not found.");
    }

    public void addEmployee() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField departmentField = new JTextField();
        JTextField salaryField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Department:"));
        panel.add(departmentField);
        panel.add(new JLabel("Salary:"));
        panel.add(salaryField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "➕ Add Employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Employee employee = new Employee(
                        Integer.parseInt(idField.getText().trim()),
                        nameField.getText().trim(),
                        departmentField.getText().trim(),
                        Double.parseDouble(salaryField.getText().trim())
                );
                addEmployeeService.addEmployee(employee);
                listEmployees();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "❌ Invalid input. Please enter correct values.");
            }
        }
    }

    public void listEmployees() {
        tableModel.setRowCount(0);
        List<Employee> employees = employeeService.getEmployees();
        for (Employee emp : employees) {
            tableModel.addRow(new Object[]{emp.getId(), emp.getName(), emp.getDepartment(), emp.getSalary()});
        }
    }

    public void updateEmployee() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField departmentField = new JTextField();
        JTextField salaryField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("New Name:"));
        panel.add(nameField);
        panel.add(new JLabel("New Department:"));
        panel.add(departmentField);
        panel.add(new JLabel("New Salary:"));
        panel.add(salaryField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "✏️ Update Employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                updateEmployeeService.updateEmployee(
                        Integer.parseInt(idField.getText().trim()),
                        nameField.getText().trim(),
                        departmentField.getText().trim(),
                        Double.parseDouble(salaryField.getText().trim())
                );
                listEmployees();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "❌ Invalid input. Please enter correct values.");
            }
        }
    }

    public void deleteEmployee() {
        String idInput = JOptionPane.showInputDialog(frame, "🗑 Enter Employee ID to Delete:");
        if (idInput != null) {
            try {
                deleteEmployeeService.deleteEmployee(Integer.parseInt(idInput.trim()));
                listEmployees();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "❌ Invalid input. Please enter a valid ID.");
            }
        }
    }

    private void createNewSheet() {
        String newSheetName = JOptionPane.showInputDialog(frame, "📄 Enter new sheet name:");
        if (newSheetName != null) {
            try {
                googleSheetsService.createSheetWithFormat(newSheetName);
                JOptionPane.showMessageDialog(frame, "✅ New Sheet Created: " + newSheetName);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "❌ Error creating sheet: " + e.getMessage());
            }
        }
    }

    public void listSheets() {
        try {
            List<String> sheets = googleSheetsService.listSheets();
            JOptionPane.showMessageDialog(frame, "📋 Available Sheets:\n" + String.join("\n", sheets));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "❌ Error listing sheets: " + e.getMessage());
        }
    }
    public void deleteSheet() {
        String sheetName = JOptionPane.showInputDialog(frame, "❌ Enter sheet name to delete:");
        if (sheetName != null && !sheetName.trim().isEmpty()) {
            try {
                googleSheetsService.deleteSheet(sheetName);
                JOptionPane.showMessageDialog(frame, "✅ Sheet '" + sheetName + "' deleted successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "❌ Error deleting sheet: " + e.getMessage());
            }
        }
    }
    private void chooseActiveSheet() {
        try {
            List<String> sheets = googleSheetsService.listSheets();
            String chosenSheet = (String) JOptionPane.showInputDialog(
                    frame, "Select Sheet:", "Choose Active Sheet",
                    JOptionPane.QUESTION_MESSAGE, null,
                    sheets.toArray(), activeSheet);

            if (chosenSheet != null) {
                activeSheet = chosenSheet;
                JOptionPane.showMessageDialog(frame, "✅ Active Sheet set to: " + activeSheet);
                listEmployees();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "❌ Error selecting sheet: " + e.getMessage());
        }
    }

}
