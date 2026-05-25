
// ============================================
// PayrollMenuFrame.java
// ============================================

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PayrollStaffFrame extends JFrame implements ActionListener {

    JLabel titleLabel;
    JLabel employeeNumberLabel;
    JLabel monthLabel;

    JTextField employeeNumberField;

    JComboBox<String> monthComboBox;

    JButton processButton;
    JButton processAllButton;
    JButton clearButton;
    JButton exitButton;

    JTable payrollTable;
    DefaultTableModel tableModel;

    JTextArea payrollTextArea;

    ArrayList<EmployeeInformation> employeeList;
    ArrayList<String[]> attendanceList;

    String[] months = {

            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };

    public PayrollStaffFrame() {

        employeeList =
                FileHandler.loadEmployees();

        attendanceList =
                FileHandler.loadAttendance();

        setTitle("Payroll Staff");

        setSize(1200,750);

        setLayout(null);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(
                new Color(230,240,250));

        // TITLE
        titleLabel =
                new JLabel("MotorPH Payroll System");

        titleLabel.setFont(
                new Font("Arial",
                        Font.BOLD,
                        28));

        titleLabel.setForeground(Color.RED);

        titleLabel.setBounds(
                350,
                20,
                500,
                40);

        add(titleLabel);

        // EMPLOYEE NUMBER
        employeeNumberLabel =
                new JLabel("Employee Number:");

        employeeNumberLabel.setBounds(
                50,
                90,
                180,
                30);

        add(employeeNumberLabel);

        employeeNumberField =
                new JTextField();

        employeeNumberField.setBounds(
                230,
                90,
                180,
                35);

        add(employeeNumberField);

        // MONTH
        monthLabel =
                new JLabel("Select Month:");

        monthLabel.setBounds(
                450,
                90,
                150,
                30);

        add(monthLabel);

        monthComboBox =
                new JComboBox<>(months);

        monthComboBox.setBounds(
                600,
                90,
                180,
                35);

        add(monthComboBox);

        // PROCESS BUTTON
        processButton =
                new JButton(
                        "Process One Employee");

        processButton.setBounds(
                50,
                140,
                220,
                40);

        processButton.setBackground(
                new Color(70,130,180));

        processButton.setForeground(Color.WHITE);

        processButton.addActionListener(this);

        add(processButton);

        // PROCESS ALL BUTTON
        processAllButton =
                new JButton(
                        "Process All Employees");

        processAllButton.setBounds(
                300,
                140,
                220,
                40);

        processAllButton.setBackground(
                new Color(60,179,113));

        processAllButton.setForeground(Color.WHITE);

        processAllButton.addActionListener(this);

        add(processAllButton);

        // CLEAR BUTTON
        clearButton =
                new JButton("Clear");

        clearButton.setBounds(
                550,
                140,
                120,
                40);

        clearButton.setBackground(
                new Color(255,140,0));

        clearButton.setForeground(Color.WHITE);

        clearButton.addActionListener(this);

        add(clearButton);

        // EXIT BUTTON
        exitButton =
                new JButton("Exit");

        exitButton.setBounds(
                700,
                140,
                120,
                40);

        exitButton.setBackground(
                new Color(220,20,60));

        exitButton.setForeground(Color.WHITE);

        exitButton.addActionListener(this);

        add(exitButton);

        // TABLE
        String[] columns = {

                "Employee No",
                "Employee Name",
                "1st Cutoff",
                "2nd Cutoff",
                "Gross Salary",
                "Deductions",
                "Net Salary"
        };

        tableModel =
                new DefaultTableModel(columns,0);

        payrollTable =
                new JTable(tableModel);

        JScrollPane tableScrollPane =
                new JScrollPane(payrollTable);

        tableScrollPane.setBounds(
                50,
                220,
                1080,
                200);

        add(tableScrollPane);

        // TEXT AREA
        payrollTextArea =
                new JTextArea();

        payrollTextArea.setFont(
                new Font("Monospaced",
                        Font.PLAIN,
                        14));

        JScrollPane payrollScrollPane =
                new JScrollPane(payrollTextArea);

        payrollScrollPane.setBounds(
                50,
                450,
                1080,
                220);

        add(payrollScrollPane);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == processButton) {

            processEmployeePayroll();
        }

        if (e.getSource() == processAllButton) {

            processAllPayrolls();
        }

        if (e.getSource() == clearButton) {

            clearForm();
        }

        if (e.getSource() == exitButton) {

            dispose();
        }
    }

    // PROCESS ONE EMPLOYEE
    public void processEmployeePayroll() {

        tableModel.setRowCount(0);

        payrollTextArea.setText("");

        String input =
                employeeNumberField.getText();

        if (!InputValidator.isNumber(input)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Employee Number");

            return;
        }

        int employeeNumber =
                Integer.parseInt(input);

        int selectedMonth =
                monthComboBox.getSelectedIndex() + 6;

        boolean found = false;

        for (EmployeeInformation employee
                : employeeList) {

            if (employee.getEmployeeNumber()
                    == employeeNumber) {

                found = true;

                displayPayroll(
                        employee,
                        selectedMonth);
            }
        }

        if (!found) {

            JOptionPane.showMessageDialog(
                    this,
                    "Employee Not Found");
        }
    }

    // PROCESS ALL
    public void processAllPayrolls() {

        tableModel.setRowCount(0);

        payrollTextArea.setText("");

        int selectedMonth =
                monthComboBox.getSelectedIndex() + 6;

        for (EmployeeInformation employee
                : employeeList) {

            displayPayroll(
                    employee,
                    selectedMonth);
        }
    }

    // DISPLAY PAYROLL
    public void displayPayroll(
            EmployeeInformation employee,
            int selectedMonth) {

        double firstCutoffHours = 0;
        double secondCutoffHours = 0;

        for (String[] record
                : attendanceList) {

            int empNo =
                    Integer.parseInt(record[0]);

            int month =
                    Integer.parseInt(record[1]);

            int day =
                    Integer.parseInt(record[2]);

            if (empNo ==
                    employee.getEmployeeNumber()
                    && month == selectedMonth) {

                double hours =
                        AttendanceComputation
                                .calculateWorkedHours(
                                        record[3],
                                        record[4]);

                if (day <= 15) {

                    firstCutoffHours += hours;
                }

                else {

                    secondCutoffHours += hours;
                }
            }
        }

        double totalHours =
                firstCutoffHours +
                        secondCutoffHours;

        double grossSalary =
                PayrollProcessor.computeGrossSalary(
                        totalHours,
                        employee.getHourlyRate());

        double sss =
                DeductionComputation.computeSSS(
                        grossSalary);

        double philHealth =
                DeductionComputation.computePhilHealth(
                        grossSalary);

        double pagibig =
                DeductionComputation.computePagibig(
                        grossSalary);

        double taxableIncome =
                grossSalary -
                        (sss + philHealth + pagibig);

        double tax =
                DeductionComputation.computeTax(
                        taxableIncome);

        double totalDeductions =
                sss + philHealth + pagibig + tax;

        double netSalary =
                grossSalary - totalDeductions;

        tableModel.addRow(new Object[] {

                employee.getEmployeeNumber(),

                employee.getFullName(),

                firstCutoffHours,

                secondCutoffHours,

                grossSalary,

                totalDeductions,

                netSalary
        });

        String monthName =
                monthComboBox
                        .getSelectedItem()
                        .toString();

        payrollTextArea.append(

                "=====================================\n"

                + "Employee Number: "
                + employee.getEmployeeNumber()

                + "\nEmployee Name: "
                + employee.getFullName()

                + "\n\nCutoff Date: "
                + monthName
                + " 1 to 15 2024"

                + "\nTotal Hours: "
                + firstCutoffHours
                + " hours"

                + "\nGross Salary: PHP "
                + (firstCutoffHours
                * employee.getHourlyRate())

                + "\nNet Salary: PHP "
                + (firstCutoffHours
                * employee.getHourlyRate())

                + "\n\nCutoff Date: "
                + monthName
                + " 16 to 30 2024"

                + "\nTotal Hours: "
                + secondCutoffHours
                + " hours"

                + "\nGross Salary: PHP "
                + grossSalary

                + "\nEach Deduction:"

                + "\n    SSS: PHP "
                + sss

                + "\n    PhilHealth: PHP "
                + philHealth

                + "\n    Pag-IBIG: PHP "
                + pagibig

                + "\n    Tax: PHP "
                + tax

                + "\nTotal Deductions: PHP "
                + totalDeductions

                + "\nNet Salary: PHP "
                + netSalary

                + "\n\n");
    }

    // CLEAR
    public void clearForm() {

        employeeNumberField.setText("");

        payrollTextArea.setText("");

        tableModel.setRowCount(0);
    }
}
