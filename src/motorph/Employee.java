package motorph;

public class Employee {
    private int employeeNumber;
    private String lastName;
    private String firstName;
    private String birthday;
    private double basicSalary;
    private double hourlyRate;
    private double riceSubsidy;
    private double phoneAllowance;
    private double clothingAllowance;

    public Employee(int employeeNumber, String lastName, String firstName,
                    String birthday, double basicSalary, double hourlyRate,
                    double riceSubsidy, double phoneAllowance, double clothingAllowance) {
        this.employeeNumber = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.basicSalary = basicSalary;
        this.hourlyRate = hourlyRate;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
    }

    public int getEmployeeNumber()      { return employeeNumber; }
    public String getLastName()         { return lastName; }
    public String getFirstName()        { return firstName; }
    public String getFullName()         { return firstName + " " + lastName; }
    public String getBirthday()         { return birthday; }
    public double getBasicSalary()      { return basicSalary; }
    public double getHourlyRate()       { return hourlyRate; }
    public double getRiceSubsidy()      { return riceSubsidy; }
    public double getPhoneAllowance()   { return phoneAllowance; }
    public double getClothingAllowance(){ return clothingAllowance; }
}