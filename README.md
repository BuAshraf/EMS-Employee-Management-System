# Employee Management System (EMS)

## Overview
The Employee Management System (EMS) is a Java-based application designed to manage employee records effectively. The application supports CRUD operations (Create, Read, Update, Delete) and includes file persistence for saving and loading employee data.

---

## Features
- **Add Employee**: Add new employees with unique IDs.
- **List Employees**: View a list of all employees.
- **Update Employee**: Modify details of an existing employee.
- **Delete Employee**: Remove an employee by ID.
- **File Persistence**: Save and load employee data to/from files using Apache POI (Excel).

---

## Project Structure
```
EMS/
├── src/
│   ├── com/
│   │   ├── model/
│   │   │   └── Employee.java
│   │   ├── persistence/
│   │   │   ├── EmployeeFileHandler.java
│   │   │   ├── LoadEmployeesFromFile.java
│   │   │   └── SaveEmployeesToFile.java
│   │   ├── service/
│   │   │   ├── AddEmployee.java
│   │   │   ├── DeleteEmployee.java
│   │   │   ├── EmployeeService.java
│   │   │   ├── ListEmployees.java
│   │   │   └── UpdateEmployee.java
│   └── Main.java
├── README.md
├── employees.xlsx
├── pom.xml
└── .gitignore
```

---

## Setup
### Prerequisites
- Java 22 (OpenJDK 22 or Oracle JDK 22)
- Maven
- IntelliJ IDEA or any Java IDE

### Dependencies
The project uses the following libraries:
- Apache POI (for working with Excel files):
  ```xml
  <dependency>
      <groupId>org.apache.poi</groupId>
      <artifactId>poi-ooxml</artifactId>
      <version>5.2.3</version>
  </dependency>
  <dependency>
      <groupId>org.apache.poi</groupId>
      <artifactId>poi-ooxml-schemas</artifactId>
      <version>4.1.2</version>
  </dependency>
  ```
- XMLBeans:
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
   git clone <repository_url>
   cd EMS
   ```

2. **Build the Project:**
   ```bash
   mvn clean install
   ```

3. **Run the Application:**
   - Use your IDE to run `Main.java` or execute:
     ```bash
     java -cp target/EMS-1.0-SNAPSHOT.jar com.Main
     ```

4. **Test the Application:**
   - Follow the menu prompts in the console to manage employees.

---

## File Persistence
- Employee data is saved to and loaded from `employees.xlsx`.
- The Excel file is automatically updated when the application exits.

---

## Future Enhancements
- Add advanced search filters for employees.
- Integrate with a database (e.g., MySQL, PostgreSQL).
- Implement a graphical user interface (GUI).
- Add role-based authentication.

---

## License
This project is licensed under the MIT License. See `LICENSE` for details.

---

## Contributors
- **Muhammed Ashraf Alkulaib** - Developer

Feel free to contribute to this project by submitting issues or pull requests!
