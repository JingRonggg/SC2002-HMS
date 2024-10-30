package src.utils;

import src.model.MedicationStorage;
import src.repository.MedicineRepository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class MedicineCsvExporter {
    protected static final String CSV_FILE_PATH = "./data/Medicine_List.csv";

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
