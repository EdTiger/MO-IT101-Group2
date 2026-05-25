// ============================================
// EmployeeFrame.java
// ============================================


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EmployeeFrame extends JFrame implements ActionListener {

    JLabel titleLabel;
    JLabel employeeNumberLabel;
    JLabel monthLabel;

    JLabel employeeNameLabel;
    JLabel birthdayLabel;
    JLabel hourlyRateLabel;

    JTextField employeeNumberField;

    JComboBox<String> monthComboBox;

    JButton searchButton;
    JButton clearButton;
    JButton exitButton;

    JTable attendanceTable;
    DefaultTableModel tableModel;

    JTextArea payslipTextArea;

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

    public EmployeeFrame() {

        employeeList =
                FileHandler.loadEmployees();

        attendanceList =
                FileHandler.loadAttendance();

        setTitle("Employee Portal");

        setSize(1100,720);

        setLayout(null);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(
                new Color(245,245,255));

        // TITLE
        titleLabel =
                new JLabel("Employee Portal");

        titleLabel.setFont(
                new Font("Arial",
                        Font.BOLD,
                        28));

        titleLabel.setForeground(Color.RED);

        titleLabel.setBounds(
                400,
                20,
                400,
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
                50,
                140,
                180,
                30);

        add(monthLabel);

        monthComboBox =
                new JComboBox<>(months);

        monthComboBox.setBounds(
                230,
                140,
                180,
                35);

        add(monthComboBox);

        // BUTTONS
        searchButton =
                new JButton("Search");

        searchButton.setBounds(
                450,
                140,
                120,
                35);

        searchButton.setBackground(
                new Color(65,105,225));

        searchButton.setForeground(Color.WHITE);

        searchButton.addActionListener(this);

        add(searchButton);

        clearButton =
                new JButton("Clear");

        clearButton.setBounds(
                590,
                140,
                120,
                35);

        clearButton.setBackground(
                new Color(255,140,0));

        clearButton.setForeground(Color.WHITE);

        clearButton.addActionListener(this);

        add(clearButton);

        exitButton =
                new JButton("Exit");

        exitButton.setBounds(
                730,
                140,
                120,
                35);

        exitButton.setBackground(
                new Color(220,20,60));

        exitButton.setForeground(Color.WHITE);

        exitButton.addActionListener(this);

        add(exitButton);

        // INFORMATION LABELS
        employeeNameLabel =
                new JLabel("Employee Name:");

        employeeNameLabel.setBounds(
                50,
                210,
                500,
                30);

        add(employeeNameLabel);

        birthdayLabel =
                new JLabel("Birthday:");

        birthdayLabel.setBounds(
                50,
                250,
                500,
                30);

        add(birthdayLabel);

        hourlyRateLabel =
                new JLabel("Hourly Rate:");

        hourlyRateLabel.setBounds(
                50,
                290,
                500,
                30);

        add(hourlyRateLabel);

        // TABLE
        String[] columns = {

                "Month",
                "Day",
                "Time In",
                "Time Out",
                "Hours Worked"
        };

        tableModel =
                new DefaultTableModel(columns,0);

        attendanceTable =
                new JTable(tableModel);

        JScrollPane tableScrollPane =
                new JScrollPane(attendanceTable);

        tableScrollPane.setBounds(
                50,
                350,
                950,
                200);

        add(tableScrollPane);

        // PAYSLIP
        payslipTextArea =
                new JTextArea();

        payslipTextArea.setFont(
                new Font("Monospaced",
                        Font.PLAIN,
                        14));

        JScrollPane payslipScrollPane =
                new JScrollPane(payslipTextArea);

        payslipScrollPane.setBounds(
                50,
                580,
                950,
                80);

        add(payslipScrollPane);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == searchButton) {

            searchEmployeeRecord();
        }

        if (e.getSource() == clearButton) {

            clearForm();
        }

        if (e.getSource() == exitButton) {

            dispose();
        }
    }

    public void searchEmployeeRecord() {

        tableModel.setRowCount(0);

        payslipTextArea.setText("");

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

                employeeNameLabel.setText(
                        "Employee Name: "
                                + employee.getFullName());

                birthdayLabel.setText(
                        "Birthday: "
                                + employee.getBirthday());

                hourlyRateLabel.setText(
                        "Hourly Rate: PHP "
                                + employee.getHourlyRate());

                loadAttendanceRecords(
                        employeeNumber,
                        selectedMonth);

                generateEmployeePayslip(
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

    public void loadAttendanceRecords(
            int employeeNumber,
            int selectedMonth) {

        for (String[] record
                : attendanceList) {

            int empNo =
                    Integer.parseInt(record[0]);

            int month =
                    Integer.parseInt(record[1]);

            if (empNo == employeeNumber
                    && month == selectedMonth) {

                double hours =
                        AttendanceComputation.calculateWorkedHours(
                                record[3],
                                record[4]);

                tableModel.addRow(new Object[] {

                        record[1],
                        record[2],
                        record[3],
                        record[4],
                        hours
                });
            }
        }
    }

    public void generateEmployeePayslip(
            EmployeeInformation employee,
            int selectedMonth) {

        double totalHours = 0;

        for (String[] record
                : attendanceList) {

            int empNo =
                    Integer.parseInt(record[0]);

            int month =
                    Integer.parseInt(record[1]);

            if (empNo ==
                    employee.getEmployeeNumber()
                    && month == selectedMonth) {

                totalHours +=
                        AttendanceComputation.calculateWorkedHours(
                                record[3],
                                record[4]);
            }
        }

        double grossSalary =
                PayrollProcessor.computeGrossSalary(
                        totalHours,
                        employee.getHourlyRate());

        double netSalary =
                PayrollProcessor.computeNetSalary(
                        grossSalary);

        payslipTextArea.setText(

                "============== EMPLOYEE PAYSLIP ==============\n"

                        + "Employee Number: "
                        + employee.getEmployeeNumber()

                        + "\nEmployee Name: "
                        + employee.getFullName()

                        + "\nMonth: "
                        + monthComboBox.getSelectedItem()

                        + "\nTotal Hours Worked: "
                        + totalHours

                        + "\nGross Salary: PHP "
                        + grossSalary

                        + "\nNet Salary: PHP "
                        + netSalary
        );
    }

    public void clearForm() {

        employeeNumberField.setText("");

        payslipTextArea.setText("");

        tableModel.setRowCount(0);

        employeeNameLabel.setText(
                "Employee Name:");

        birthdayLabel.setText(
                "Birthday:");

        hourlyRateLabel.setText(
                "Hourly Rate:");
    }
}
