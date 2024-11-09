package src.utils;

import java.util.Set;
import java.util.HashSet;

/**
 * Utility class for generating unique medical record IDs.
 * This class maintains a set of existing IDs and provides thread-safe methods
 * for generating new unique IDs.
 */
public class MedicalRecordIDGenerator {
    /** Counter for generating sequential medical record IDs */
    private static int medicalRecordID = 0;
    
    /** Set containing all existing medical record IDs to ensure uniqueness */
    private static final Set<String> existingIDs = new HashSet<>();

    /**
     * Generates the next available unique medical record ID.
     * This method is thread-safe and ensures that no duplicate IDs are generated.
     *
     * @return A unique String representing the next available medical record ID
     */
    public static synchronized String nextMedicalRecordID() {
        String newID;
        do {
            newID = String.valueOf(medicalRecordID++);
        } while (existingIDs.contains(newID)); // Ensure unique ID
        existingIDs.add(newID); // Add to existing IDs
        return newID;
    }

    /**
     * Initializes the generator with a set of existing medical record IDs.
     * Updates the internal counter to ensure new IDs will be unique.
     *
     * @param ids Set of existing medical record IDs to initialize with
     */
    public static synchronized void initializeWithExistingIDs(Set<String> ids) {
        existingIDs.addAll(ids);
        if (!ids.isEmpty()) {
            // Set medicalRecordID to the next available number after the max existing ID
            medicalRecordID = ids.stream().mapToInt(Integer::parseInt).max().orElse(0) + 1;
        }
    }
}
