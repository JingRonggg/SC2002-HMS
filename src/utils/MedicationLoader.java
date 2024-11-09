package src.utils;

import src.controller.MedicineController;
import src.model.MedicationStorage;
import src.model.PrescribeMedications;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * A utility class for loading medication data from a CSV file into the system.
 */
public class MedicationLoader {
    /** The file path of the CSV file containing medication data */
    private String filePath;

    /**
     * Constructs a new MedicationLoader with the specified file path.
     *
     * @param filePath the path to the CSV file containing medication data
     */
    public MedicationLoader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads medication data from the CSV file and adds it to the system.
     * The CSV file should have the following format:
     * - First line: header row (skipped)
     * - Subsequent lines: medicineName,stock,lowStockAlert
     * 
     * Each line is parsed to create a new MedicationStorage object and
     * added to the system via MedicineController.
     * 
     * If an IOException occurs while reading the file, an error message
     * is printed to the console.
     */
    public void loadMedication() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip the header row
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");  // Assuming CSV is comma-separated
                String medicineName = data[0].trim();
                int stock = Integer.parseInt(data[1].trim());
                int lowStockAlert = Integer.parseInt(data[2].trim());

                // Create a Medication object without stock details
                MedicationStorage medicationStorage = new MedicationStorage(medicineName);

                // Add medication using MedicineController, passing stock and alert info separately
                MedicineController.addMedicine(medicationStorage, stock, lowStockAlert);
                
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}
