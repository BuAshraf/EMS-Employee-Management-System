# Employee Management System (EMS)

## Overview
The Employee Management System (EMS) is a Java-based application designed to streamline the management of employee records. It offers core CRUD functionality (Create, Read, Update, Delete) and uses file persistence to store employee data. EMS is a robust solution for small to medium-sized organizations.

---

## Features
- **Add Employee**: Register new employees with unique IDs.
- **List Employees**: Display a complete list of employees.
- **Update Employee**: Edit details of an existing employee.
- **Delete Employee**: Remove employees by their ID.
- **File Persistence**: Save and load employee data seamlessly using text files and Excel sheets.

---

## Project Structure
```
EMS/
├── src/
│   ├── org/
│   │   ├── EMS/
│   │   │   ├── model/
│   │   │   │   └── Employee.java
│   │   │   ├── persistence/
│   │   │   │   ├── EmployeeFileHandler.java
│   │   │   │   ├── LoadEmployeesFromFile.java
│   │   │   │   └── SaveEmployeesToFile.java
│   │   │   ├── service/
│   │   │   │   ├── AddEmployee.java
│   │   │   │   ├── DeleteEmployee.java
│   │   │   │   ├── EmployeeService.java
│   │   │   │   ├── ListEmployees.java
│   │   │   │   └── UpdateEmployee.java
│   │   │   └── Main.java
├── employees.txt
├── employees.xlsx
├── pom.xml
├── .gitignore
└── README.md
```

---

## Setup
### Prerequisites
- Java 22 or later (OpenJDK or Oracle JDK)
- Maven
- IntelliJ IDEA or any compatible Java IDE

### Dependencies
The project uses the following libraries:
- **Apache POI** (to handle Excel file operations):
  ```xml
  <dependency>
      <groupId>org.apache.poi</groupId>
      <artifactId>poi-ooxml</artifactId>
      <version>5.2.3</version>
  </dependency>
  ```
- **XMLBeans** (required for Apache POI):
  ```xml
  <dependency>
      <groupId>org.apache.xmlbeans</groupId>
      <artifactId>xmlbeans</artifactId>
      <version>5.2.2</version>
  </dependency>
  ```

---

## How to Run
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/BuAshraf/EMS-Employee-Management-System.git
   cd EMS
   ```

2. **Build the Project:**
   ```bash
   mvn clean install
   ```

3. **Run the Application:**
   - Use your IDE to run `Main.java`, or execute:
     ```bash
     java -cp target/EMS-1.0-SNAPSHOT.jar org.EMS.Main
     ```

4. **Test the Application:**
   - Interact with the console-based menu to add, update, delete, or list employees.

---

## File Persistence
- Employee data is stored in two formats:
  - **Text File:** `employees.txt`
  - **Excel File:** `employees.xlsx`
- Files are automatically updated during application execution.

---

## Future Enhancements
- Add advanced search filters for employees (e.g., by department or salary range).
- Replace file persistence with a database integration (e.g., MySQL, PostgreSQL).
- Develop a graphical user interface (GUI) for enhanced usability.
- Introduce role-based authentication and authorization.

---

## License
This project is licensed under the MIT License. See the `LICENSE` file for more details.

---

## Contributors
- **Muhammed Ashraf Alkulaib** - Developer

We welcome contributions! Feel free to open issues or submit pull requests to improve this project.

