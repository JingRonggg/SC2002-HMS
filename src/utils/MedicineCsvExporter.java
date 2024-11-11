package src.utils;

import src.model.MedicationStorage;
import src.repository.MedicineRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for exporting medicine data to CSV format.
 * Handles reading from and writing to a CSV file containing medicine information.
 */
public class MedicineCsvExporter {
    /** The file path where the CSV file will be stored */
    protected static final String CSV_FILE_PATH = "./data/Medicine_List.csv";

    /**
     * Exports all medicines from the repository to a CSV file.
     * If the file exists, it will be overwritten with updated data.
     * If the file doesn't exist, it will be created with appropriate headers.
     *
     * @param repository The medicine repository containing the data to be exported
     */
    public static void exportAllMedicinesToCsv(MedicineRepository repository) {
        Map<String, String> existingMedicines = new HashMap<>();

        if (Files.exists(Paths.get(CSV_FILE_PATH))) {
            try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
                String line;
                // Skip the header
                reader.readLine();
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        existingMedicines.put(parts[0], line);
                    }
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the CSV file: " + e.getMessage());
                return;
            }
        } else {
            writeHeaders();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, false))) {
            writer.write("Medicine Name,Initial Stock,Low Stock Level Alert");
            writer.newLine();

            for (String medicineName : repository.getAllMedicines().keySet()) {
                writeOrUpdateMedicineLine(writer, medicineName, repository);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while exporting medicines to CSV: " + e.getMessage());
        }
    }

    /**
     * Writes the CSV header row to a new file.
     * Creates the file if it doesn't exist.
     */
    private static void writeHeaders() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, false))) {
            String headers = String.join(",",
                    "Medicine Name",
                    "Initial Stock",
                    "Low Stock Level Alert");
            writer.write(headers);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("An error occurred while writing headers to CSV: " + e.getMessage());
        }
    }

    /**
     * Writes or updates a single medicine entry in the CSV file.
     *
     * @param writer The BufferedWriter to write the data
     * @param medicineName The name of the medicine to write
     * @param repository The repository containing the medicine data
     * @throws IOException If an I/O error occurs while writing
     */
    private static void writeOrUpdateMedicineLine(BufferedWriter writer, String medicineName, MedicineRepository repository) throws IOException {
        MedicationStorage medicationStorage = repository.getMedicine(medicineName);
        int initialStock = repository.getStock(medicineName);
        int lowStockAlert = repository.getStockAlert(medicineName);

        String line = String.join(",",
                medicationStorage.getMedicineName(),
                String.valueOf(initialStock),
                String.valueOf(lowStockAlert)
        );
        writer.write(line);
        writer.newLine();
    }
}
