// ============================================
// EmployeeInformation.java
// ============================================

public class EmployeeInformation {

    private int employeeNumber;
    private String firstName;
    private String lastName;
    private String birthday;
    private double hourlyRate;

    public EmployeeInformation(
            int employeeNumber,
            String firstName,
            String lastName,
            String birthday,
            double hourlyRate) {

        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.hourlyRate = hourlyRate;
    }

    public int getEmployeeNumber() {

        return employeeNumber;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public String getBirthday() {

        return birthday;
    }

    public double getHourlyRate() {

        return hourlyRate;
    }

    public String getFullName() {

        return firstName + " " + lastName;
    }
}
