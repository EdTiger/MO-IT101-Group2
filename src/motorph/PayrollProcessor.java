// ============================================
// PayrollProcessor.java
// ============================================

public class PayrollProcessor {

    public static double computeGrossSalary(
            double hoursWorked,
            double hourlyRate) {

        return hoursWorked * hourlyRate;
    }

    public static double computeNetSalary(
            double grossSalary) {

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

        return grossSalary - totalDeductions;
    }
}
