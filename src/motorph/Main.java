package motorph;
/**
 * MotorPH Payroll System
 *
 * This program reads employee and attendance records from CSV files
 * and computes payroll automatically.
 *
 * Features:
 * - Login system (Employee or Payroll Staff)
 * - Employee information lookup
 * - Payroll processing
 * - Automatic computation of:
 *      - Gross Salary
 *      - SSS Contribution
 *      - PhilHealth Contribution
 *      - Pag-IBIG Contribution
 *      - Income Tax
 * - Payroll summary per cutoff
 *
 * Developed for MotorPH Phase 1 Payroll System
 */
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


public class Main {

    /**
     * Main entry point for the MotorPH Payroll System.
     * Handles login authentication and routes to the appropriate menu.
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Display system title
        System.out.println("=================================");
        System.out.println("                                 ");
        System.out.println("             MotorPH             ");
        System.out.println("         Payroll System          ");
        System.out.println("                                 ");
        System.out.println("=================================");

        // =============================
        // LOGIN SECTION
        // =============================

        // Ask user for login credentials
        System.out.print("Username: ");
        String username = scanner.nextLine(); // Read username

        System.out.print("Password: ");
        String password = scanner.nextLine(); // Read Password

        // Check if user is payroll staff
        boolean isPayrollStaff =
                username.equals("payroll_staff") && password.equals("12345");

        // Check if user is employee
        boolean isEmployee =
                username.equals("employee") && password.equals("12345");

        // If login credentials are incorrect
        if (!isPayrollStaff && !isEmployee) {

            System.out.println("------------------------------------");
            System.out.println("                                    ");
            System.out.println("             LOGIN FAILED           ");
            System.out.println("       _______________________      ");
            System.out.println("                                    ");
            System.out.println( "Incorrect username and/or password" );
            System.out.println("          Please try again.         ");
            System.out.println("                                    ");
            System.out.println("------------------------------------");

            return; // Stop program
        }

        // =============================
        // EMPLOYEE LOGIN MENU
        // =============================

        // Only show menu if the user is an employee
        if (isEmployee) {

            // Show menu options
            System.out.println("\n1 Enter employee number");
            System.out.println("2 Exit");

            // Read the user's input and converts it from text to an integer
            int choice = Integer.parseInt(scanner.nextLine());

            // If the user chose options 2 (Exit)
            if (choice == 2) {
                return;
            }

            // Prompt the user to enter an employee number and read the input
            System.out.print("\nEnter employee number: ");
            int empNumber = Integer.parseInt(scanner.nextLine());

            // Fetch and display the information for the given employee number
            employeeInfo(empNumber);
        }

        // =============================
        // PAYROLL STAFF MENU
        // =============================

        // Only show payroll options if the user is a payroll staff member
        if (isPayrollStaff) {

            // Show menu options
            System.out.println("\n1 Process Payroll");
            System.out.println("2 Exit");

            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 2) {
                return;
            }

            // Show the secondary menu for selecting payroll scope
            System.out.println("\n1 One Employee");
            System.out.println("2 All Employees");
            System.out.println("3 Exit");

            int empChoice = Integer.parseInt(scanner.nextLine());

            // If the user chose option 3 (Exit)
            if (empChoice == 3) {
                return;
            }

            // =============================
            // PROCESS ONE EMPLOYEE
            // =============================

            // If the user chose to process a single employee
            if (empChoice == 1) {

                // Prompt the user to enter an employee number and read the input
                System.out.print("\nEnter employee number: ");
                int empNumber = Integer.parseInt(scanner.nextLine());

                // Process payroll for the given employee number
                processEmployee(empNumber);
            }

            // =============================
            // PROCESS ALL EMPLOYEES
            // =============================

            if (empChoice == 2) {

                // Attempt to read the employee file, catch any errors that occur
                try {
                    // Point to the employees CSV file in the project folder
                    File empFile = new File("employees.csv");
                    BufferedReader empReader = new BufferedReader(new FileReader(empFile));

                    String line;

                    empReader.readLine(); // Skip the header row

                    // Loop through each remaining line until the end of the file
                    while ((line = empReader.readLine()) != null) {

                        // Split the line into an array of values using comma as the delimiter
                        String[] empData = line.split(",");

                        // Extract the employee number from the first column
                        int empNumber = Integer.parseInt(empData[0]);

                        processEmployee(empNumber);
                    }

                    empReader.close();

                } catch (Exception e) {
                    // Notify the user if the file could not be read
                    System.out.println("Error reading employees file.");
                }
            }
        }

        scanner.close();
    }

    // ============================================
    // DISPLAY EMPLOYEE INFORMATION
    // ============================================

    /**
     * Looks up and displays the details of a specific employee by number.
     */
    public static void employeeInfo(int empNumber) {

        try {
            // Point to the employees CSV file in the project folder
            File empFile = new File("employees.csv");
            BufferedReader empReader = new BufferedReader(new FileReader(empFile));

            String line;

            empReader.readLine(); // Skip the header row

            boolean found = false; // Check if the employee number is found

            while ((line = empReader.readLine()) != null) {

                String[] empData = line.split(",");

                int num = Integer.parseInt(empData[0]);

                if (num == empNumber) {

                    found = true;

                    System.out.println("-----------------------------------------------");
                    System.out.println("               Employee Details                ");
                    System.out.println("===============================================");
                    System.out.println("| Emp #  | Name                | Birthday     |");
                    System.out.println("-----------------------------------------------");

                    System.out.println("| " + num
                            + " | " + empData[2] + " " + empData[1]
                            + " | " + empData[3]);

                    break;
                }
            }

            // Notify the user if no matching employee number was found
            if (!found) {
                System.out.println("Employee number does not exist.");
            }

            empReader.close();

        } catch (Exception e) {
            // Notify the user if the file could not be read
            System.out.println("Error reading file.");
        }
    }

    // ============================================
    // PROCESS PAYROLL COMPUTATION
    // ============================================

    /**
     * Processes and displays the payroll for a specific employee across all months.
     * Reads attendance records once, computes gross salary per cutoff, applies
     * government deductions (SSS, PhilHealth, Pag-IBIG, Tax) on the combined
     * monthly gross, and displays the payroll summary.
     */
    public static void processEmployee(int empNumber) {

        try {
            // ---- Read employee details from employees.csv ----
            File empFile = new File("employees.csv");
            BufferedReader empReader = new BufferedReader(new FileReader(empFile));

            String line;

            String firstName = "";
            String lastName = "";
            String birthday = "";
            double hourlyRate = 0;

            empReader.readLine(); // Skip header row

            boolean found = false;

            while ((line = empReader.readLine()) != null) {

                String[] empData = line.split(",");

                int num = Integer.parseInt(empData[0]); // Extract the employee number from the first column

                if (num == empNumber) {

                    found = true;

                    // Extract the employee's details from the CSV columns
                    lastName = empData[1];
                    firstName = empData[2];
                    birthday = empData[3];
                    hourlyRate = Double.parseDouble(empData[5]);

                    break;
                }
            }

            empReader.close();

            // If no matching employee was found, notify the user and stop
            if (!found) {
                System.out.println("Employee does not exist.");
                return;
            }

            // Display employee header
            System.out.println("-----------------------------------------------");
            System.out.println("               Employee Details                ");
            System.out.println("===============================================");
            System.out.println("| Emp #  | Name                | Birthday     |");
            System.out.println("-----------------------------------------------");
            System.out.println("| " + empNumber
                    + " | " + firstName + " " + lastName
                    + " | " + birthday);
            System.out.println("-----------------------------------------------");

            // ---- Read all attendance records once before the monthly loop ----
            File attRecord = new File("attendance.csv");
            BufferedReader attReader = new BufferedReader(new FileReader(attRecord));
            attReader.readLine(); // Skip header row

            // Create a list to store all attendance records in memory
            List<String[]> attendanceRecords = new ArrayList<>();
            String attLine;

            // Read each attendance line and store it in the list
            while ((attLine = attReader.readLine()) != null) {
                attendanceRecords.add(attLine.split(","));
            }

            attReader.close();

            // ---- Process payroll for each month (June to December) ----
            for (int month = 6; month <= 12; month++) {

                double firstCutoffHours = 0;
                double secondCutoffHours = 0;

                // Loop through all pre-loaded attendance records
                for (String[] record : attendanceRecords) {

                    // Extract the employee number, month, and day from the record
                    int num = Integer.parseInt(record[0]);
                    int recordMonth = Integer.parseInt(record[1]);
                    int day = Integer.parseInt(record[2]);

                    // Only process records that match the current employee and month
                    if (num == empNumber && recordMonth == month) {

                        // Compute the number of hours worked for this record
                        double hours = computeHours(record[3], record[4]);

                        // Add hours to the first cutoff (days 1-15) or second cutoff (days 16-end)
                        if (day <= 15) {
                            firstCutoffHours += hours;
                        } else {
                            secondCutoffHours += hours;
                        }
                    }
                }

                // Computes the gross salary
                double firstCutoffGross = firstCutoffHours * hourlyRate;
                double secondCutoffGross = secondCutoffHours * hourlyRate;

                // Combine both cutoffs to get the full monthly gross for deductions
                double monthlyGross = firstCutoffGross + secondCutoffGross;

                // Skip month if there is no attendance data
                if (monthlyGross <= 0) {
                    continue;
                }

                // Compute mandatory government deductions based on combined monthly gross
                double sss = computeSSS(monthlyGross);
                double philHealth = computePhilHealth(monthlyGross);
                double pagibig = computePagibig(monthlyGross);

                // Taxable income is the monthly gross minus the three government deductions
                double taxableIncome = monthlyGross - (sss + philHealth + pagibig);
                double tax = computeTax(taxableIncome);

                // Sum up all deductions for the month
                double totalDeductions = sss + philHealth + pagibig + tax;

                // =============================
                // FIRST CUTOFF (1st to 15th)
                // =============================

                // Display the first cutoff summary if the employee has hours in this period
                if (firstCutoffHours > 0) {

                    System.out.println("\nCutoff: " + getMonth(month) + " 1 to 15");
                    System.out.println("Total Hours Worked: " + firstCutoffHours);
                    System.out.println("Gross Salary: " + firstCutoffGross);
                    System.out.println("Net Salary: " + firstCutoffGross); // No deductions applied to the first cutoff — deductions are held for the second cutoff
                }

                // =============================
                // SECOND CUTOFF (16th to end)
                // =============================

                // Display the second cutoff summary if the employee has hours in this period
                if (secondCutoffHours > 0) {

                    // All monthly deductions are applied to the second cutoff
                    double netSalary = secondCutoffGross - totalDeductions;

                    System.out.println("\nCutoff: " + getMonth(month) + " 16 to end");
                    System.out.println("Total Hours Worked: " + secondCutoffHours);
                    System.out.println("Gross Salary: " + secondCutoffGross);

                    // Display the breakdown of each government deduction
                    System.out.println("Deductions (based on monthly gross of " + monthlyGross + "):");
                    System.out.println("  SSS: " + sss);
                    System.out.println("  PhilHealth: " + philHealth);
                    System.out.println("  Pag-IBIG: " + pagibig);
                    System.out.println("  Tax: " + tax);

                    System.out.println("Total Deductions: " + totalDeductions);
                    System.out.println("Net Salary: " + netSalary); // Display the final net salary after all deductions
                }
            }

        } catch (Exception e) {
            System.out.println("Error processing payroll.");
        }
    }

    // ============================================
    // SSS CONTRIBUTION COMPUTATION
    // ============================================

    /**
     * Computes the SSS employee contribution based on the monthly gross salary.
     * Uses the SSS contribution table with salary brackets in 500-peso increments.
     */
    public static double computeSSS(double monthlyGross) {

        double[][] sssTable = {
                {3250, 135.00},   {3750, 157.50},   {4250, 180.00},
                {4750, 202.50},   {5250, 225.00},   {5750, 247.50},
                {6250, 270.00},   {6750, 292.50},   {7250, 315.00},
                {7750, 337.50},   {8250, 360.00},   {8750, 382.50},
                {9250, 405.00},   {9750, 427.50},   {10250, 450.00},
                {10750, 472.50},  {11250, 495.00},  {11750, 517.50},
                {12250, 540.00},  {12750, 562.50},  {13250, 585.00},
                {13750, 607.50},  {14250, 630.00},  {14750, 652.50},
                {15250, 675.00},  {15750, 697.50},  {16250, 720.00},
                {16750, 742.50},  {17250, 765.00},  {17750, 787.50},
                {18250, 810.00},  {18750, 832.50},  {19250, 855.00},
                {19750, 877.50},  {20250, 900.00},  {20750, 922.50},
                {21250, 945.00},  {21750, 967.50},  {22250, 990.00},
                {22750, 1012.50}, {23250, 1035.00}, {23750, 1057.50},
                {24250, 1080.00}, {24750, 1102.50}
        };

        for (double[] bracket : sssTable) {
            if (monthlyGross <= bracket[0]) {
                return bracket[1];
            }
        }

        // If the monthly gross exceeds all brackets, return the maximum SSS contribution
        return 1125.00;
    }

    // ============================================
    // PHILHEALTH CONTRIBUTION COMPUTATION
    // ============================================

    /**
     * Computes the PhilHealth employee share based on the monthly gross salary.
     * Premium rate is 3% of salary (clamped between 10,000 and 60,000),
     * split equally between employer and employee.
     */
    public static double computePhilHealth(double monthlyGross) {

        double salary = monthlyGross;


        if (salary < 10000) salary = 10000; // Clamp the salary to the minimum covered amount of 10,000
        if (salary > 60000) salary = 60000; // Clamp the salary to the maximum covered amount of 60,000

        double monthlyPremium = salary * 0.03;  // Compute the total monthly premium at 3% of the clamped salary

        return monthlyPremium / 2; // Return only the employee's share, which is half of the total premium
    }

    // ============================================
    // PAG-IBIG CONTRIBUTION COMPUTATION
    // ============================================

    /**
     * Computes the Pag-IBIG employee contribution based on the monthly gross salary.
     * Rate is 1% for salaries 1,000-1,500, otherwise 2%, capped at 100.
     */
    public static double computePagibig(double monthlyGross) {

        double contribution;

        if (monthlyGross >= 1000 && monthlyGross <= 1500) {
            contribution = monthlyGross * 0.01; // Apply 1% rate for salaries between 1,000 and 1,500
        } else {
            contribution = monthlyGross * 0.02; // Apply 2% rate for all other salaries
        }

        // Cap the contribution at the maximum allowed amount of 100 pesos
        if (contribution > 100) {
            contribution = 100;
        }

        return contribution;
    }

    // ============================================
    // INCOME TAX COMPUTATION
    // ============================================

    /**
     * Computes the withholding tax based on the taxable income
     * (monthly gross minus SSS, PhilHealth, and Pag-IBIG contributions).
     */
    public static double computeTax(double taxableIncome) {

        // No tax for taxable income of 20,832 and below
        if (taxableIncome <= 20832)
            return 0;

        // 20% tax on the excess over 20,833 for incomes up to 33,332
        else if (taxableIncome < 33333)
            return (taxableIncome - 20833) * 0.20;

        // 2,500 fixed + 25% tax on the excess over 33,333 for incomes up to 66,666
        else if (taxableIncome < 66667)
            return 2500 + (taxableIncome - 33333) * 0.25;

        // 10,833 fixed + 30% tax on the excess over 66,667 for incomes up to 166,666
        else if (taxableIncome < 166667)
            return 10833 + (taxableIncome - 66667) * 0.30;

        // 40,833.33 fixed + 32% tax on the excess over 166,667 for incomes up to 666,666
        else if (taxableIncome < 666667)
            return 40833.33 + (taxableIncome - 166667) * 0.32;

        // 200,833.33 fixed + 35% tax on the excess over 666,667 for the highest bracket
        else
            return 200833.33 + (taxableIncome - 666667) * 0.35;
    }

    // ============================================
    // COMPUTE WORK HOURS
    // ============================================

    /**
     * Computes the total working hours for a single day based on time-in and time-out.
     * Applies the following payroll rules:
     * - Grace period: clock-ins between 8:00 and 8:10 are treated as 8:00
     * - Work hours are counted from 8:00 AM to 5:00 PM only
     * - A 1-hour lunch break (12:00 PM to 1:00 PM) is deducted
     * - Maximum creditable hours per day is 8
     */
    public static double computeHours(String timeInStr, String timeOutStr) {

        // Parse time-in (HH:MM) into decimal hours
        String[] inParts = timeInStr.split(":");
        int inHour = Integer.parseInt(inParts[0]);
        int inMinute = Integer.parseInt(inParts[1]);
        double timeIn = inHour + (inMinute / 60.0); // Convert time-in to a decimal number

        // Parse time-out (HH:MM) into decimal hours
        String[] outParts = timeOutStr.split(":");
        int outHour = Integer.parseInt(outParts[0]);
        int outMinute = Integer.parseInt(outParts[1]);
        double timeOut = outHour + (outMinute / 60.0);  // Convert time-out to a decimal number

        // Grace period: if the employee clocked in between 8:00 and 8:10,
        // treat their time-in as exactly 8:00 so they are not penalized
        if (timeIn >= 8.0 && timeIn <= 8.0 + (10.0 / 60.0)) {
            timeIn = 8.0;
        }

        // If the employee clocked in before 8:00 AM, start counting from 8:00 AM only
        // since hours before 8:00 are not creditable
        if (timeIn < 8.0) {
            timeIn = 8.0;
        }

        // If the employee clocked out after 5:00 PM, cap time-out at 5:00 PM
        // since hours after 5:00 PM are not creditable
        if (timeOut > 17.0) {
            timeOut = 17.0;
        }

        // Compute the raw hours worked between the adjusted time-in and time-out
        double hoursWorked = timeOut - timeIn;

        // Deduct the 1-hour lunch break (12:00 PM to 1:00 PM) if the work period overlaps it
        // Math.min and Math.max are used to calculate exactly how much of the lunch break
        // falls within the employee's work period — handles partial overlaps correctly
        double lunchStart = 12.0;
        double lunchEnd = 13.0;
        double lunchOverlap = Math.max(0, Math.min(timeOut, lunchEnd) - Math.max(timeIn, lunchStart));
        hoursWorked -= lunchOverlap;

        // Ensure hours worked never goes below zero in case of invalid or edge case times
        if (hoursWorked < 0) {
            hoursWorked = 0;
        }

        // Cap the creditable hours at 8 hours per day (the standard 9-hour window minus 1-hour lunch)
        if (hoursWorked > 8.0) {
            hoursWorked = 8.0;
        }

        return hoursWorked;
    }

    // ============================================
    // GET MONTH NAME
    // ============================================

    /**
     * Converts a month number (1-12) to its corresponding month name.
     */
    public static String getMonth(int month) {

        // Array of month names indexed from January (0) to December (11)
        String[] monthNames = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };

        // If the month number is valid (1-12), return the corresponding month name
        // Subtract 1 from the month number since arrays are zero-indexed
        if (month >= 1 && month <= 12) {
            return monthNames[month - 1];
        }

        return "";
    }
}
