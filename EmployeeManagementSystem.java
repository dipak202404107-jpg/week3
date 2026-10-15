
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Employee {
    int id;
    String name;
    String department;
    double salary;

    Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    void display() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Department: " + department);
        System.out.println("Salary: " + salary);
        System.out.println("-------------------------");
    }

    String toFileString() {
        return id + "|" + name + "|" + department + "|" + salary;
    }
}

public class EmployeeManagementSystem {
    static ArrayList<Employee> employees = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "employees.txt";

    static Employee findEmployee(int id) {
        for (Employee e : employees) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

    static void addEmployee() {
        try {
            System.out.print("Enter Employee ID: ");
            int id = Integer.parseInt(sc.nextLine().trim());

            if (id <= 0) {
                throw new IllegalArgumentException(
                    "ID must be positive."
                );
            }

            if (findEmployee(id) != null) {
                throw new IllegalArgumentException(
                    "Employee ID already exists."
                );
            }

            System.out.print("Enter Employee Name: ");
            String name = sc.nextLine().trim();

            System.out.print("Enter Department: ");
            String department = sc.nextLine().trim();

            System.out.print("Enter Salary: ");
            double salary = Double.parseDouble(sc.nextLine().trim());

            if (name.isEmpty() || department.isEmpty()
                    || name.contains("|") || department.contains("|")) {
                throw new IllegalArgumentException(
                    "Name and department must be valid."
                );
            }

            if (!Double.isFinite(salary) || salary < 0) {
                throw new IllegalArgumentException(
                    "Salary must be a valid non-negative number."
                );
            }

            Employee e = new Employee(id, name, department, salary);
            employees.add(e);

            System.out.println("Employee added successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Error: Enter valid numeric values.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.println("\n--- Employee Records ---");

        for (Employee e : employees) {
            e.display();
        }
    }

    static void searchEmployee() {
        try {
            System.out.print("Enter Employee ID to search: ");
            int id = Integer.parseInt(sc.nextLine().trim());

            Employee e = findEmployee(id);

            if (e != null) {
                System.out.println("Employee found:");
                e.display();
            } else {
                System.out.println("Employee not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Enter a valid ID.");
        }
    }

    static void deleteEmployee() {
        try {
            System.out.print("Enter Employee ID to delete: ");
            int id = Integer.parseInt(sc.nextLine().trim());

            Employee e = findEmployee(id);

            if (e != null) {
                employees.remove(e);
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("Employee not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Enter a valid ID.");
        }
    }

    static void saveEmployees() {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(FILE_NAME))) {

            for (Employee e : employees) {
                bw.write(e.toFileString());
                bw.newLine();
            }

            System.out.println("Employee data saved successfully!");

        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    static void loadEmployees() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No previous employee data found.");
            return;
        }

        ArrayList<Employee> loaded = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader(file))) {

            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;

                try {
                    String[] data = line.split("\\|", -1);

                    if (data.length != 4) {
                        throw new IllegalArgumentException(
                            "Invalid record format."
                        );
                    }

                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    String department = data[2];
                    double salary = Double.parseDouble(data[3]);

                    if (id <= 0 || name.trim().isEmpty()
                            || department.trim().isEmpty()
                            || !Double.isFinite(salary) || salary < 0) {
                        throw new IllegalArgumentException(
                            "Invalid employee details."
                        );
                    }

                    for (Employee existing : loaded) {
                        if (existing.id == id) {
                            throw new IllegalArgumentException(
                                "Duplicate employee ID."
                            );
                        }
                    }

                    loaded.add(new Employee(
                        id, name, department, salary
                    ));

                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping invalid record at line "
                            + lineNumber + ": " + e.getMessage());
                }
            }

            employees.clear();
            employees.addAll(loaded);
            System.out.println("Employee data loaded successfully!");

        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        loadEmployees();

        while (true) {
            System.out.println("\n===== Employee Management System =====");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Save Employees");
            System.out.println("6. Load Employees");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    addEmployee();
                    break;

                case "2":
                    viewEmployees();
                    break;

                case "3":
                    searchEmployee();
                    break;

                case "4":
                    deleteEmployee();
                    break;

                case "5":
                    saveEmployees();
                    break;

                case "6":
                    loadEmployees();
                    break;

                case "7":
                    saveEmployees();
                    System.out.println("Thank you! Program closed.");
                    sc.close();
                    return;

                default:
                    System.out.println(
                        "Invalid choice! Please enter 1 to 7."
                    );
            }
        }
    }
}
