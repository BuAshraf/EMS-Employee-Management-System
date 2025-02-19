package org.EMS.BackEnd.GUI;

import org.EMS.BackEnd.model.Employee;
import org.EMS.BackEnd.persistence.GoogleSheetsService;
import org.EMS.BackEnd.service.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static java.awt.FlowLayout.CENTER;

public class EmployeeManagementGUI1 {
    private final EmployeeService employeeService;
    private final AddEmployeeWithValidation addEmployeeService;
    private final DeleteEmployeeWithValidation deleteEmployeeService;
    private final ListEmployeesWithValidation listEmployeesService;
    private final UpdateEmployeeWithValidation updateEmployeeService;
    private final GoogleSheetsService googleSheetsService;


    private JFrame frame;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public EmployeeManagementGUI1(EmployeeService employeeService,
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
        // ✅ Set Logo (Replace with your actual image path)
        ImageIcon logo = new ImageIcon(getClass().getResource("/logo.jpg")); // Ensure the logo is inside `resources`
        frame.setIconImage(logo.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // ✅ Create a Centered Title
        JLabel titleLabel = new JLabel("Employee Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));


        // Table to display employees
        String[] columns = {"ID", "Name", "Department", "Salary"};
        tableModel = new DefaultTableModel(columns, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(employeeTable);
        frame.add(tableScrollPane, BorderLayout.CENTER);

        // Top Panel with Search Bar
        JPanel topPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchEmployee());
        topPanel.add(new JLabel("Search: "), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);
        frame.add(topPanel, BorderLayout.NORTH);

        // Bottom Panel with Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
        addButton(buttonPanel, "Add", this::addEmployee);
        addButton(buttonPanel, "Update", this::updateEmployee);
        addButton(buttonPanel, "Delete", this::deleteEmployee);
        addButton(buttonPanel, "Refresh", this::listEmployees);




        addButton(buttonPanel, "Exit", () -> System.exit(0));
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
        JOptionPane.showMessageDialog(frame, "Employee not found.");
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

        int result = JOptionPane.showConfirmDialog(frame, panel, "Add Employee", JOptionPane.OK_CANCEL_OPTION);
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
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter correct values.");
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

        int result = JOptionPane.showConfirmDialog(frame, panel, "Update Employee", JOptionPane.OK_CANCEL_OPTION);
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
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter correct values.");
            }
        }
    }

    public void deleteEmployee() {
        String idInput = JOptionPane.showInputDialog(frame, "Enter Employee ID to Delete:");
        if (idInput != null) {
            try {
                deleteEmployeeService.deleteEmployee(Integer.parseInt(idInput.trim()));
                listEmployees();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid ID.");
            }
        }
    }
}
