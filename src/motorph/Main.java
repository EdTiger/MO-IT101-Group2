package motorph;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // ===== EMPLOYEE DATABASE =====
        Employee[] employees = {
                new Employee(10001, "Garcia",     "Manuel III",  "10/11/1983", 90000.00, 535.71, 1500.00, 2000.00, 1000.00),
                new Employee(10002, "Lim",        "Antonio",     "06/19/1988", 60000.00, 357.14, 1500.00, 2000.00, 1000.00),
                new Employee(10003, "Aquino",     "Bianca Sofia","08/04/1989", 60000.00, 357.14, 1500.00, 2000.00, 1000.00),
                new Employee(10004, "Reyes",      "Isabella",    "06/16/1994", 60000.00, 357.14, 1500.00, 2000.00, 1000.00),
                new Employee(10005, "Hernandez",  "Eduard",      "09/23/1989", 52670.00, 313.51, 1500.00, 1000.00, 1000.00),
                new Employee(10006, "Villanueva", "Andrea Mae",  "02/14/1988", 52670.00, 313.51, 1500.00, 1000.00, 1000.00),
        };

        // ===== ATTENDANCE RECORDS (Sample Week: June 3-7, 2024) =====
        AttendanceRecord[] attendance = {
                // Employee 10001 - Garcia, Manuel III
                new AttendanceRecord(10001, "06/03/2024", "7:55",  "17:00"),
                new AttendanceRecord(10001, "06/04/2024", "8:05",  "17:00"),
                new AttendanceRecord(10001, "06/05/2024", "8:15",  "17:00"),  // late
                new AttendanceRecord(10001, "06/06/2024", "7:59",  "17:00"),
                new AttendanceRecord(10001, "06/07/2024", "8:30",  "17:00"),  // late
                // Employee 10002 - Lim, Antonio
                new AttendanceRecord(10002, "06/03/2024", "8:01",  "17:00"),
                new AttendanceRecord(10002, "06/04/2024", "8:00",  "17:00"),
                new AttendanceRecord(10002, "06/05/2024", "8:00",  "17:00"),
                new AttendanceRecord(10002, "06/06/2024", "8:05",  "17:00"),
                new AttendanceRecord(10002, "06/07/2024", "8:00",  "17:00"),
                // Employee 10003 - Aquino, Bianca Sofia
                new AttendanceRecord(10003, "06/03/2024", "8:00",  "17:00"),
                new AttendanceRecord(10003, "06/04/2024", "8:20",  "17:00"),  // late
                new AttendanceRecord(10003, "06/05/2024", "8:00",  "17:00"),
                new AttendanceRecord(10003, "06/06/2024", "8:00",  "17:00"),
                new AttendanceRecord(10003, "06/07/2024", "8:00",  "17:00"),
        };

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n========================================");
            System.out.println("       MOTORPH PAYROLL SYSTEM");
            System.out.println("========================================");
            System.out.println("1. View All Employees");
            System.out.println("2. View Employee Details");
            System.out.println("3. Calculate Weekly Payroll");
            System.out.println("4. Exit");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayAllEmployees(employees);
                    break;
                case 2:
                    System.out.print("Enter Employee Number: ");
                    displayEmployeeDetails(employees, scanner.nextInt());
                    break;
                case 3:
                    System.out.print("Enter Employee Number: ");
                    calculateAndDisplayPayroll(employees, attendance, scanner.nextInt());
                    break;
                case 4:
                    System.out.println("Exiting MotorPH Payroll System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        scanner.close();
    }

    private static void displayAllEmployees(Employee[] employees) {
        System.out.println("\n========================================");
        System.out.println("           EMPLOYEE LIST");
        System.out.println("========================================");
        System.out.printf("%-12s %-30s %-15s%n", "Emp. No.", "Full Name", "Birthday");
        System.out.println("----------------------------------------");
        for (Employee emp : employees) {
            System.out.printf("%-12d %-30s %-15s%n",
                    emp.getEmployeeNumber(), emp.getFullName(), emp.getBirthday());
        }
    }

    private static void displayEmployeeDetails(Employee[] employees, int employeeNumber) {
        Employee emp = findEmployee(employees, employeeNumber);
        if (emp == null) { System.out.println("Employee not found."); return; }

        System.out.println("\n========================================");
        System.out.println("         EMPLOYEE DETAILS");
        System.out.println("========================================");
        System.out.printf("Employee Number  : %d%n",        emp.getEmployeeNumber());
        System.out.printf("Full Name        : %s%n",        emp.getFullName());
        System.out.printf("Birthday         : %s%n",        emp.getBirthday());
        System.out.printf("Basic Salary     : PHP %,.2f%n", emp.getBasicSalary());
        System.out.printf("Hourly Rate      : PHP %,.2f%n", emp.getHourlyRate());
        System.out.printf("Rice Subsidy     : PHP %,.2f%n", emp.getRiceSubsidy());
        System.out.printf("Phone Allowance  : PHP %,.2f%n", emp.getPhoneAllowance());
        System.out.printf("Clothing Allow.  : PHP %,.2f%n", emp.getClothingAllowance());
    }

    private static void calculateAndDisplayPayroll(Employee[] employees,
                                                   AttendanceRecord[] attendance,
                                                   int employeeNumber) {
        Employee emp = findEmployee(employees, employeeNumber);
        if (emp == null) { System.out.println("Employee not found."); return; }

        double weeklyHours       = PayrollCalculator.calculateWeeklyHours(attendance, employeeNumber);
        double grossWeeklySalary = PayrollCalculator.calculateGrossWeeklySalary(emp, weeklyHours);
        double monthlySalary     = emp.getBasicSalary();
        double sss               = PayrollCalculator.calculateSSS(monthlySalary);
        double philhealth        = PayrollCalculator.calculatePhilHealth(monthlySalary);
        double pagibig           = PayrollCalculator.calculatePagIbig(monthlySalary);
        double totalMandatory    = sss + philhealth + pagibig;
        double taxableIncome     = monthlySalary - totalMandatory;
        double withholdingTax    = PayrollCalculator.calculateWithholdingTax(taxableIncome);
        double totalMonthly      = totalMandatory + withholdingTax;
        double weeklyDeductions  = totalMonthly / 4.0;
        double netWeeklySalary   = grossWeeklySalary - weeklyDeductions;

        // Attendance breakdown
        System.out.println("\n========================================");
        System.out.println("      WEEKLY ATTENDANCE SUMMARY");
        System.out.println("========================================");
        System.out.printf("Employee: %s (#%d)%n", emp.getFullName(), emp.getEmployeeNumber());
        System.out.println("----------------------------------------");
        System.out.printf("%-12s %-10s %-10s %-8s %-8s%n", "Date", "Time In", "Time Out", "Hours", "Status");
        System.out.println("----------------------------------------");
        for (AttendanceRecord record : attendance) {
            if (record.getEmployeeNumber() == employeeNumber) {
                System.out.printf("%-12s %-10s %-10s %-8.2f %-8s%n",
                        record.getDate(), record.getTimeIn(), record.getTimeOut(),
                        record.calculateHoursWorked(),
                        record.isLate() ? "LATE" : "ON TIME");
            }
        }

        // Payroll summary
        System.out.println("\n========================================");
        System.out.println("        WEEKLY PAYROLL SUMMARY");
        System.out.println("========================================");
        System.out.printf("Employee         : %s%n",        emp.getFullName());
        System.out.printf("Employee No.     : %d%n",        emp.getEmployeeNumber());
        System.out.println("----------------------------------------");
        System.out.printf("Total Hours      : %.2f hrs%n",  weeklyHours);
        System.out.printf("Hourly Rate      : PHP %,.2f%n", emp.getHourlyRate());
        System.out.printf("GROSS WEEKLY PAY : PHP %,.2f%n", grossWeeklySalary);
        System.out.println("----------------------------------------");
        System.out.println("DEDUCTIONS (weekly equivalent):");
        System.out.printf("  SSS            : PHP %,.2f%n", sss / 4);
        System.out.printf("  PhilHealth     : PHP %,.2f%n", philhealth / 4);
        System.out.printf("  Pag-IBIG       : PHP %,.2f%n", pagibig / 4);
        System.out.printf("  Withholding Tax: PHP %,.2f%n", withholdingTax / 4);
        System.out.printf("  Total          : PHP %,.2f%n", weeklyDeductions);
        System.out.println("----------------------------------------");
        System.out.printf("NET WEEKLY PAY   : PHP %,.2f%n", netWeeklySalary);
        System.out.println("========================================");
    }

    private static Employee findEmployee(Employee[] employees, int employeeNumber) {
        for (Employee emp : employees) {
            if (emp.getEmployeeNumber() == employeeNumber) return emp;
        }
        return null;
    }
}