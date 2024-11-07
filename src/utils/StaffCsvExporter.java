package src.utils;

import src.model.User;
import src.model.Staff;
import src.repository.UserRepository;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class StaffCsvExporter {
    protected static final String CSV_FILE_PATH = "./data/Staff_List.csv";
    private static final String[] HEADERS = {
        "Staff ID", "Name", "Role", "Gender", "Age", "Password"
    };

    public static void exportStaffToCsv(User user) {
        // Skip if this is a patient
        if ("Patient".equals(user.getRole())) {
            return;
        }

        List<String> lines = new ArrayList<>();
        boolean recordExists = false;

        try {
            // Check if file exists and read existing records
            if (Files.exists(Paths.get(CSV_FILE_PATH))) {
                try (BufferedReader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Split line into fields
                        String[] fields = line.split(",");
                        
                        // Add headers if present
                        if (fields[0].equals("Staff ID")) {
                            lines.add(line);
                            continue;
                        }

                        // Check if this Staff ID matches the current user's ID
                        if (fields[0].equals(user.getHospitalID())) {
                            // Update the existing record
                            lines.add(formatUserToCsv(user));
                            recordExists = true;
                            continue;
                        }
                        lines.add(line);
                    }
                }
            } else {
                // File doesn't exist, create headers
                lines.add(String.join(",", HEADERS));
            }

            // If user doesn't exist, add them
            if (!recordExists) {
                lines.add(formatUserToCsv(user));
            }

            // Write all lines back to CSV
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE_PATH))) {
                for (String record : lines) {
                    writer.write(record);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error processing staff CSV file: " + e.getMessage());
        }
    }

    // Handle bulk export while preserving existing records
    public static void exportAllStaffToCsv(UserRepository userRepository) {
        // Get all users from repository and convert to map for efficient lookup
        Collection<User> users = userRepository.getAllUsers();
        Map<String, User> userMap = new HashMap<>();
        for (User user : users) {
            // Only include non-patient users
            if (!"Patient".equals(user.getRole())) {
                userMap.put(user.getHospitalID(), user);
            }
        }

        List<String> lines = new ArrayList<>();
        Set<String> processedIds = new HashSet<>();

        try {
            // Check if file exists and process existing records
            if (Files.exists(Paths.get(CSV_FILE_PATH))) {
                try (BufferedReader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] fields = line.split(",");
                        
                        // Preserve headers
                        if (fields[0].equals("Staff ID")) {
                            lines.add(line);
                            continue;
                        }

                        String staffId = fields[0];
                        // If we have an updated version of this user, use it
                        if (userMap.containsKey(staffId)) {
                            lines.add(formatUserToCsv(userMap.get(staffId)));
                            processedIds.add(staffId);
                        } else {
                            // Keep existing record if no update available
                            lines.add(line);
                        }
                    }
                }
            } else {
                // New file, add headers
                lines.add(String.join(",", HEADERS));
            }

            // Add any new users that weren't in the original file
            for (User user : users) {
                if (!"Patient".equals(user.getRole()) && !processedIds.contains(user.getHospitalID())) {
                    lines.add(formatUserToCsv(user));
                }
            }

            // Write everything back to the file
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE_PATH))) {
                for (String record : lines) {
                    writer.write(record);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error processing staff CSV file: " + e.getMessage());
        }
    }

    private static String formatUserToCsv(User user) {
        String age = (user instanceof Staff) ? ((Staff) user).getAge() : "";
        return String.format("%s,%s,%s,%s,%s,%s",
            user.getHospitalID(),
            escapeSpecialCharacters(user.getName()),
            user.getRole(),
            user.getGender(),
            age,
            user.getPassword()
        );
    }

    private static String escapeSpecialCharacters(String field) {
        if (field == null) {
            return "";
        }
        // If field contains comma or newline, wrap in quotes and escape existing quotes
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }
}
