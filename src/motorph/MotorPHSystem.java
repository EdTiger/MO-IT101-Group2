package motorph;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MotorPHSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // STAGE 1: Login Page
        System.out.println("===== MotorPH Login =====");
        System.out.print("Username: ");
        String user = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        // Validating credentials
        if (pass.equals("12345") && (user.equals("employee") || user.equals("payroll_staff"))) {
            System.out.println("\nLogin Successful! Welcome to MotorPH.");
            runSearch(sc);
        } else {
            System.out.println("\n[!] LOGIN FAILED");
            System.out.println("Incorrect username and/or password.");
        }
    }

    public static void runSearch(Scanner sc) {
        System.out.print("\nEnter Employee # to Search: ");
        String searchID = sc.next();
        
        // Calling the static method to read the CSV file
        findEmployeeInCSV(searchID);
    }

    public static void findEmployeeInCSV(String searchID) {
        String csvFile = "MotorPH_Data.csv"; // Ensure this matches your Notepad filename
        String line = "";
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // Splits the line by commas to access each data column
                String[] emp = line.split(","); 

                // emp[0] matches the Employee ID column in your Notepad
                if (emp[0].equals(searchID)) {
                    System.out.println("\n-------------------------------------------");
                    System.out.println(">>> FULL EMPLOYEE RECORD FOUND <<<");
                    System.out.println("-------------------------------------------");
                    System.out.println("Employee #:   " + emp[0]);
                    System.out.println("Last Name:    " + emp[1]);
                    System.out.println("First Name:   " + emp[2]);
                    System.out.println("Birthday:     " + emp[3]);
                    System.out.println("Address:      " + emp[4]);
                    System.out.println("Phone Number: " + emp[5]);
                    System.out.println("S-S-S Number: " + emp[6]);
                    System.out.println("PhilHealth:   " + emp[7]);
                    System.out.println("T-I-N Number: " + emp[8]);
                    System.out.println("Pag-IBIG:     " + emp[9]);
                    System.out.println("Status:       " + emp[10]);
                    System.out.println("Position:     " + emp[11]);
                    System.out.println("Supervisor:   " + emp[12]);
                    System.out.println("Basic Salary: " + emp[13]);
                    System.out.println("Hourly Rate:  " + emp[14]); // Found at the end of the line
                    System.out.println("-------------------------------------------");
                    found = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("\n[!] SYSTEM ERROR: Could not find " + csvFile + ".");
            System.out.println("Ensure the file is next to your 'src' folder.");
        }

        if (!found) {
            System.out.println("\n[!] Error: Employee #" + searchID + " not found.");
        }
    }
}
