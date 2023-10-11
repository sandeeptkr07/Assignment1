import java.util.*;

public class EmployeeWorkHoursAnalyzer {

    public static void main(String[] args) {
        // Get the path to the employee work hours file.
        String filePath = "/path/to/employee_work_hours.csv";

        // Read the employee work hours file.
        List<EmployeeWorkHour> employeeWorkHours = readEmployeeWorkHoursFile(filePath);

        // Initialize variables to track the number of consecutive days worked, the time between shifts, and the total number of hours worked in a single shift.
        int consecutiveDaysWorked = 0;
        int timeBetweenShifts = 0;
        int totalHoursWorked = 0;

        // Iterate over the employee work hours and track the above variables.
        for (EmployeeWorkHour employeeWorkHour : employeeWorkHours) {
            // Calculate the time between shifts.
            if (employeeWorkHour.getStartTime() - timeBetweenShifts > 10) {
                // If the time between shifts is greater than 10 hours, reset the consecutive days worked variable.
                consecutiveDaysWorked = 0;
            }

            // Check if the employee has worked for 7 consecutive days.
            if (consecutiveDaysWorked == 7) {
                System.out.println(f"Employee {employeeWorkHour.getName()} has worked for 7 consecutive days.");
            }

            // Check if the employee has worked for more than 14 hours in a single shift.
            if (totalHoursWorked > 14) {
                System.out.println(f"Employee {employeeWorkHour.getName()} has worked for more than 14 hours in a single shift.");
            }

            // Update the variables.
            consecutiveDaysWorked += 1;
            timeBetweenShifts = employeeWorkHour.getStartTime();
            totalHoursWorked = employeeWorkHour.getEndTime() - employeeWorkHour.getStartTime();
        }
    }

    private static List<EmployeeWorkHour> readEmployeeWorkHoursFile(String filePath) {
        List<EmployeeWorkHour> employeeWorkHours = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            // Skip the header row.
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Split the line into columns.
                String[] columns = line.split(",");

                // Create an EmployeeWorkHour object from the columns.
                EmployeeWorkHour employeeWorkHour = new EmployeeWorkHour(
                        columns[0],
                        columns[1],
                        Long.parseLong(columns[2]),
                        Long.parseLong(columns[3]));

                employeeWorkHours.add(employeeWorkHour);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employeeWorkHours;
    }
}

class EmployeeWorkHour {
    private String name;
    private String position;
    private long startTime;
    private long endTime;

    public EmployeeWorkHour(String name, String position, long startTime, long endTime) {
        this.name = name;
        this.position = position;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
