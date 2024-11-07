package src.utils;

import src.model.Patient;
import src.model.User;
import src.repository.UserRepository;

import java.io.*;
import java.nio.file.*;
import java.util.*; 

public class PatientCsvExporter {
    protected static final String CSV_FILE_PATH = "./data/Patient_List.csv";
    private static final String[] HEADERS = {
        "Patient ID", "Name", "Date of Birth", "Gender", "Blood Type", "Contact Information", "Password", "Hashed Password"
    };

    // Single Export in case we want to implement immediate export
    public static void exportPatientToCsv(Patient patient) {
        // Skip if this is not a patient
        if (!"Patient".equals(patient.getRole())) {
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
                        if (fields[0].equals("Patient ID")) {
                            lines.add(line);
                            continue;
                        }

                        // Check if this Patient ID matches the current patient's ID
                        if (fields[0].equals(patient.getHospitalID())) {
                            // Update the existing record
                            lines.add(formatPatientToCsv(patient));
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

            // If patient doesn't exist, add them
            if (!recordExists) {
                lines.add(formatPatientToCsv(patient));
            }

            // Write all lines back to CSV
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE_PATH))) {
                for (String record : lines) {
                    writer.write(record);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error processing patient CSV file: " + e.getMessage());
        }
    }

    public static void exportAllPatientsToCsv(UserRepository userRepository) {
        // Get all users from repository and convert to map for efficient lookup
        Collection<User> users = userRepository.getAllUsers();
        Map<String, Patient> patientMap = new HashMap<>();
        for (User user : users) {
            // Only include actual patients
            if ("Patient".equals(user.getRole()) && user instanceof Patient) {
                patientMap.put(user.getHospitalID(), (Patient) user);
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
                        if (fields[0].equals("Patient ID")) {
                            lines.add(line);
                            continue;
                        }

                        String patientId = fields[0];
                        // If we have an updated version of this patient, use it
                        if (patientMap.containsKey(patientId)) {
                            lines.add(formatPatientToCsv(patientMap.get(patientId)));
                            processedIds.add(patientId);
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

            // Add any new patients that weren't in the original file
            for (User user : users) {
                if ("Patient".equals(user.getRole()) && user instanceof Patient && !processedIds.contains(user.getHospitalID())) {
                    lines.add(formatPatientToCsv((Patient) user));
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
            System.out.println("Error processing patient CSV file: " + e.getMessage());
        }
    }

    private static String formatPatientToCsv(Patient patient) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s",
            patient.getHospitalID(),
            escapeSpecialCharacters(patient.getName()),
            patient.getDateOfBirth(),
            patient.getGender(),
            patient.getBloodType(),
            escapeSpecialCharacters(patient.getEmailAddress()),
            escapeSpecialCharacters(patient.getPassword()),
            escapeSpecialCharacters(patient.getHashedPassword())
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
