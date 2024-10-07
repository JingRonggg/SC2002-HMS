package src.boundary;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class DoctorBoundary {

    public void displayDoctorMenu(Scanner scanner) {
        while (true) {
            System.out.println("Doctor Menu:");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept Appointment Requests");
            System.out.println("6. Decline Appointment Requests");
            System.out.println("7. View Upcoming Appointments");
            System.out.println("8. Record Appointment Outcome");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");
            int choice = parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("Insert View Patient Medical Record function");
                    break;
                case 2:
                    System.out.println("Insert Update Patient Medical Record function");
                    break;
                case 3:
                    System.out.println("Insert View Personal Schedule function");
                    break;
                case 4:
                    System.out.println("Insert set availability function");
                    break;
                case 5:
                    System.out.println("Insert Accept Appointment function");
                    break;
                case 6:
                    System.out.print("Insert decline Appointment function");
                    break;
                case 7:
                    System.out.print("Insert View Upcoming Appointments function");
                    break;
                case 8:
                    System.out.println("Insert Record Appointment Outcome function");
                    break;
                case 9:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
