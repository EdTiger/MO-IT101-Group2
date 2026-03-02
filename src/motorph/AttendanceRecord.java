package motorph;

import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class AttendanceRecord {
    private int employeeNumber;
    private String date;
    private LocalTime timeIn;
    private LocalTime timeOut;

    private static final LocalTime OFFICIAL_START  = LocalTime.of(8, 0);
    private static final LocalTime GRACE_PERIOD_END = LocalTime.of(8, 10);
    private static final LocalTime LUNCH_START      = LocalTime.of(12, 0);
    private static final LocalTime LUNCH_END        = LocalTime.of(13, 0);

    public AttendanceRecord(int employeeNumber, String date, String timeIn, String timeOut) {
        this.employeeNumber = employeeNumber;
        this.date = date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        this.timeIn  = LocalTime.parse(timeIn, formatter);
        this.timeOut = LocalTime.parse(timeOut, formatter);
    }

    public double calculateHoursWorked() {
        LocalTime effectiveTimeIn;

        // 8:11 AM rule: if within grace period, work starts at 8:00 AM
        if (!timeIn.isAfter(GRACE_PERIOD_END)) {
            effectiveTimeIn = OFFICIAL_START;
        } else {
            effectiveTimeIn = timeIn; // late: hours counted from actual login
        }

        long totalMinutes = Duration.between(effectiveTimeIn, timeOut).toMinutes();

        // Deduct 1-hour lunch break if work period covers lunch
        if (!effectiveTimeIn.isAfter(LUNCH_START) && timeOut.isAfter(LUNCH_END)) {
            totalMinutes -= 60;
        }

        return Math.max(0, totalMinutes / 60.0);
    }

    public boolean isLate() {
        return timeIn.isAfter(GRACE_PERIOD_END);
    }

    public int getEmployeeNumber() { return employeeNumber; }
    public String getDate()        { return date; }
    public LocalTime getTimeIn()   { return timeIn; }
    public LocalTime getTimeOut()  { return timeOut; }
}