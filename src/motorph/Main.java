package motorph;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("         MotorPH Payroll         ");
        System.out.println("=================================");

        // LOGIN
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        boolean payrollStaff = username.equals("payroll_staff") && password.equals("12345");
        boolean employee = username.equals("employee") && password.equals("12345");

        if (!payrollStaff && !employee) {
            System.out.println("Login Failed.");
            return;
        }

        if (employee) {
            System.out.println("\n1 Enter employee number");
            System.out.println("2 Exit");

            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 2) return;

            System.out.print("Enter employee number: ");
            int empNumber = Integer.parseInt(scanner.nextLine());

            employeeInfo(empNumber);
        }

        if (payrollStaff) {
            System.out.println("\n1 Process Payroll");
            System.out.println("2 Exit");

            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 2) return;

            System.out.println("\n1 One Employee");
            System.out.println("2 All Employees");

            int empChoice = Integer.parseInt(scanner.nextLine());

            if (empChoice == 1) {
                System.out.print("Enter employee number: ");
                int empNumber = Integer.parseInt(scanner.nextLine());
                processEmployee(empNumber);
            }

            if (empChoice == 2) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("employees.csv"));
                    reader.readLine();

                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] empData = line.split(",");
                        int empNumber = Integer.parseInt(empData[0]);
                        processEmployee(empNumber);
                    }

                    reader.close();
                } catch (Exception e) {
                    System.out.println("Error reading employees file.");
                }
            }
        }

        scanner.close();
    }

    // DISPLAY EMPLOYEE INFO
    public static void employeeInfo(int empNumber) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("employees.csv"));
            reader.readLine();

            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] empData = line.split(",");
                int num = Integer.parseInt(empData[0]);

                if (num == empNumber) {
                    found = true;
                    System.out.println("\nEmployee Found:");
                    System.out.println(empData[2] + " " + empData[1]);
                    System.out.println("Birthday: " + empData[3]);
                    break;
                }
            }

            if (!found) System.out.println("Employee not found.");

            reader.close();
        } catch (Exception e) {
            System.out.println("Error reading file.");
        }
    }

    // PROCESS PAYROLL
    public static void processEmployee(int empNumber) {

        try {
            BufferedReader empReader = new BufferedReader(new FileReader("employees.csv"));
            empReader.readLine();

            String line;

            String firstName = "", lastName = "", birthday = "";
            double hourlyRate = 0;
            boolean found = false;

            while ((line = empReader.readLine()) != null) {
                String[] empData = line.split(",");
                int num = Integer.parseInt(empData[0]);

                if (num == empNumber) {
                    found = true;
                    lastName = empData[1];
                    firstName = empData[2];
                    birthday = empData[3];
                    hourlyRate = Double.parseDouble(empData[5]);
                    break;
                }
            }

            empReader.close();

            if (!found) {
                System.out.println("Employee not found.");
                return;
            }

            System.out.println("\n=================================");
            System.out.println("Employee: " + firstName + " " + lastName);
            System.out.println("=================================");

            // READ ATTENDANCE ONCE
            ArrayList<String[]> attendanceList = new ArrayList<>();

            BufferedReader attReader = new BufferedReader(new FileReader("attendance.csv"));
            attReader.readLine();

            String line2;
            while ((line2 = attReader.readLine()) != null) {
                attendanceList.add(line2.split(","));
            }
            attReader.close();

            // PROCESS PER MONTH
            for (int month = 6; month <= 12; month++) {

                double firstCutoff = 0;
                double secondCutoff = 0;

                for (String[] record : attendanceList) {
                    int num = Integer.parseInt(record[0]);
                    int m = Integer.parseInt(record[1]);
                    int day = Integer.parseInt(record[2]);

                    if (num == empNumber && m == month) {
                        double hours = computeHours(record[3], record[4]);

                        if (day <= 15) firstCutoff += hours;
                        else secondCutoff += hours;
                    }
                }

                double totalHours = firstCutoff + secondCutoff;

                if (totalHours == 0) continue;

                double totalGross = totalHours * hourlyRate;

                // DEDUCTIONS
                double sss = computeSSS(totalGross);
                double phil = computePhilHealth(totalGross);
                double pagibig = computePagibig(totalGross);

                double taxableIncome = totalGross - (sss + phil + pagibig);
                double tax = computeTax(taxableIncome);

                double totalDeduction = sss + phil + pagibig + tax;
                double net = totalGross - totalDeduction;

                System.out.println("\nMonth: " + getMonth(month));
                System.out.println("Total Hours: " + totalHours);
                System.out.println("Gross Salary: " + totalGross);

                System.out.println("Deductions:");
                System.out.println("SSS: " + sss);
                System.out.println("PhilHealth: " + phil);
                System.out.println("Pag-IBIG: " + pagibig);
                System.out.println("Tax: " + tax);

                System.out.println("Net Salary: " + net);
            }

        } catch (Exception e) {
            System.out.println("Error processing payroll.");
        }
    }

    // HOURS COMPUTATION
    public static double computeHours(String in, String out) {

        String[] inTime = in.split(":");
        String[] outTime = out.split(":");

        double timeIn = Integer.parseInt(inTime[0]) + Integer.parseInt(inTime[1]) / 60.0;
        double timeOut = Integer.parseInt(outTime[0]) + Integer.parseInt(outTime[1]) / 60.0;

        // Grace period (15 mins)
        if (timeIn <= 8.25) timeIn = 8.0;

        double hours = timeOut - timeIn;

        // Lunch deduction
        if (hours > 5) hours -= 1;

        // Max hours
        if (hours > 8) hours = 8;

        return hours;
    }

    // SSS
    public static double computeSSS(double gross) {
        if (gross <= 3250) return 135;
        else if (gross <= 24750) return 1102.5;
        else return 1125;
    }

    // PHILHEALTH
    public static double computePhilHealth(double gross) {
        double premium;
        if (gross <= 10000) premium = 300;
        else if (gross >= 60000) premium = 1800;
        else premium = gross * 0.03;

        return premium / 2;
    }

    // PAG-IBIG
    public static double computePagibig(double gross) {
        double contribution = (gross <= 1500) ? gross * 0.01 : gross * 0.02;
        return Math.min(contribution, 100);
    }

    // TAX
    public static double computeTax(double income) {
        if (income <= 20832) return 0;
        else if (income < 33333) return (income - 20833) * 0.20;
        else if (income < 66667) return 2500 + (income - 33333) * 0.25;
        else if (income < 166667) return 10833 + (income - 66667) * 0.30;
        else if (income < 666667) return 40833.33 + (income - 166667) * 0.32;
        else return 200833.33 + (income - 666667) * 0.35;
    }

    // MONTH NAME
    public static String getMonth(int m) {
        String[] months = {"", "", "", "", "", "", "June", "July", "August", "September", "October", "November", "December"};
        return months[m];
    }
}
