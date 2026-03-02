package motorph;

public class PayrollCalculator {

    public static double calculateWeeklyHours(AttendanceRecord[] records, int employeeNumber) {
        double totalHours = 0;
        for (AttendanceRecord record : records) {
            if (record.getEmployeeNumber() == employeeNumber) {
                totalHours += record.calculateHoursWorked();
            }
        }
        return totalHours;
    }

    public static double calculateGrossWeeklySalary(Employee employee, double weeklyHours) {
        return employee.getHourlyRate() * weeklyHours;
    }

    public static double calculateSSS(double monthlySalary) {
        if (monthlySalary < 3250) return 135.00;

        double[][] sssTable = {
                {3250, 3749.99, 157.50},  {3750, 4249.99, 180.00},
                {4250, 4749.99, 202.50},  {4750, 5249.99, 225.00},
                {5250, 5749.99, 247.50},  {5750, 6249.99, 270.00},
                {6250, 6749.99, 292.50},  {6750, 7249.99, 315.00},
                {7250, 7749.99, 337.50},  {7750, 8249.99, 360.00},
                {8250, 8749.99, 382.50},  {8750, 9249.99, 405.00},
                {9250, 9749.99, 427.50},  {9750, 10249.99, 450.00},
                {10250, 10749.99, 472.50}, {10750, 11249.99, 495.00},
                {11250, 11749.99, 517.50}, {11750, 12249.99, 540.00},
                {12250, 12749.99, 562.50}, {12750, 13249.99, 585.00},
                {13250, 13749.99, 607.50}, {13750, 14249.99, 630.00},
                {14250, 14749.99, 652.50}, {14750, 15249.99, 675.00},
                {15250, 15749.99, 697.50}, {15750, 16249.99, 720.00},
                {16250, 16749.99, 742.50}, {16750, 17249.99, 765.00},
                {17250, 17749.99, 787.50}, {17750, 18249.99, 810.00},
                {18250, 18749.99, 832.50}, {18750, 19249.99, 855.00},
                {19250, 19749.99, 877.50}, {19750, 20249.99, 900.00},
                {20250, 20749.99, 922.50}, {20750, 21249.99, 945.00},
                {21250, 21749.99, 967.50}, {21750, 22249.99, 990.00},
                {22250, 22749.99, 1012.50},{22750, 23249.99, 1035.00},
                {23250, 23749.99, 1057.50},{23750, 24249.99, 1080.00},
                {24250, 24749.99, 1102.50}
        };

        for (double[] bracket : sssTable) {
            if (monthlySalary >= bracket[0] && monthlySalary <= bracket[1]) {
                return bracket[2];
            }
        }
        return 1125.00;
    }

    public static double calculatePhilHealth(double monthlySalary) {
        double premium;
        if (monthlySalary <= 10000) {
            premium = 300.00;
        } else if (monthlySalary >= 60000) {
            premium = 1800.00;
        } else {
            premium = monthlySalary * 0.03;
        }
        return premium / 2;
    }

    public static double calculatePagIbig(double monthlySalary) {
        double contribution;
        if (monthlySalary >= 1000 && monthlySalary <= 1500) {
            contribution = monthlySalary * 0.01;
        } else {
            contribution = monthlySalary * 0.02;
        }
        return Math.min(100.00, contribution);
    }

    public static double calculateWithholdingTax(double taxableMonthlyIncome) {
        if (taxableMonthlyIncome <= 20832) {
            return 0;
        } else if (taxableMonthlyIncome <= 33332) {
            return (taxableMonthlyIncome - 20833) * 0.20;
        } else if (taxableMonthlyIncome <= 66666) {
            return 2500 + (taxableMonthlyIncome - 33333) * 0.25;
        } else if (taxableMonthlyIncome <= 166666) {
            return 10833 + (taxableMonthlyIncome - 66667) * 0.30;
        } else if (taxableMonthlyIncome <= 666666) {
            return 40833.33 + (taxableMonthlyIncome - 166667) * 0.32;
        } else {
            return 200833.33 + (taxableMonthlyIncome - 666667) * 0.35;
        }
    }

    public static double calculateNetWeeklySalary(Employee employee, double weeklyGross) {
        double monthlySalary    = employee.getBasicSalary();
        double sss              = calculateSSS(monthlySalary);
        double philhealth       = calculatePhilHealth(monthlySalary);
        double pagibig          = calculatePagIbig(monthlySalary);
        double totalMandatory   = sss + philhealth + pagibig;
        double taxableIncome    = monthlySalary - totalMandatory;
        double withholdingTax   = calculateWithholdingTax(taxableIncome);
        double totalMonthly     = totalMandatory + withholdingTax;
        double weeklyDeductions = totalMonthly / 4.0;
        return weeklyGross - weeklyDeductions;
    }
}