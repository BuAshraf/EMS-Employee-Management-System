# Employee Management System (EMS) 

![1](./assets/EMS_logo.jpg?raw=true "EMS_logo")

## 📜 Overview  
The **Employee Management System (EMS)** is a Java-based application designed to streamline the management of employee data. With features for adding, viewing, updating, and deleting employee records, it provides efficient data handling. The project also supports file persistence using Excel for data storage.  

---

## 🚀 Features  
- Add new employees with unique IDs.  
- View a list of all employees.  
- Update employee details (name, department, salary).  
- Delete employee records by ID.  
- Save and load employee data using **Excel files**.  
- Structured and maintainable code for easy scalability.  

---

## 🔧 Technologies Used  
- **Java**: Core development language.  
- **Maven**: Build automation tool for dependency management.  
- **Apache POI**: Library for Excel file handling.  
- **Git & GitHub**: Version control and collaboration.  

---

## 💼 Installation & Setup  
### Prerequisites  
- **Java 22**: Ensure you have JDK 22 or later installed.  
- **Maven**: To handle project dependencies.  
- **IDE**: IntelliJ IDEA or Eclipse for Java development.  

### Steps  
1. Clone the repository:  
   ```bash
   git clone https://github.com/BuAshraf/EMS-Employee-Management-System.git
   cd EMS-Employee-Management-System
   ```
2. Build the project using Maven:  
   ```bash
   mvn clean install
   ```
3. Run the application:  
   Use your IDE to execute the `Main.java` file located in `src/main/java/org/EMS/Main.java`.  

---

## 🔍 Usage  
1. Start the application.  
2. Follow the menu prompts in the console to:  
   - Add an employee.  
   - View a list of all employees.  
   - Update or delete employee details.  
3. All data is saved automatically to `employees.xlsx` in the root directory.  

---

## 📂 Folder Structure  
```
EMS/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/
│   │   │       ├── EMS/
│   │   │       │   ├── Main.java
│   │   │       │   ├── model/
│   │   │       │   │   └── Employee.java
│   │   │       │   ├── persistence/
│   │   │       │   │   ├── EmployeeFileHandler.java
│   │   │       │   │   ├── LoadEmployeesFromFile.java
│   │   │       │   │   └── SaveEmployeesToFile.java
│   │   │       │   ├── service/
│   │   │       │   │   ├── AddEmployee.java
│   │   │       │   │   ├── DeleteEmployee.java
│   │   │       │   │   ├── EmployeeService.java
│   │   │       │   │   ├── ListEmployees.java
│   │   │       │   │   └── UpdateEmployee.java
├── employees.xlsx
├── pom.xml
└── README.md
```

---

## 🎥 Demo  
Provide a demo GIF or screenshots of the application in action (e.g., CRUD operations, file saving/loading).  


---

## 🌐 Acknowledgements  
- **Apache POI Team**: For the excellent Excel handling library.  
- **OpenJDK**: For the powerful Java platform.  
- **GitHub**: For project hosting and collaboration.  

---

## ✉ License  
This project is licensed under the MIT License. See `LICENSE` for details.

---


## 💬 Get to Know Me  😁 👨‍💻

- 💼 **LinkedIn:** [Connect with me](www.linkedin.com/in/muhammed-alkulaib-773492238)

- ✖ **Twitter:** [Follow me](https://twitter.com/bo_ashraf)

- 📬 **Email:** [Connect with me](muhammedalmugera21@gmail.com)

