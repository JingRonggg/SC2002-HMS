package src.utils;

import java.util.Set;
import java.util.HashSet;

public class MedicalRecordIDGenerator {
    private static int medicalRecordID = 0;
    private static final Set<String> existingIDs = new HashSet<>();

    public static synchronized String nextMedicalRecordID() {
        String newID;
        do {
            newID = String.valueOf(medicalRecordID++);
        } while (existingIDs.contains(newID)); // Ensure unique ID
        existingIDs.add(newID); // Add to existing IDs
        return newID;
    }

    public static synchronized void initializeWithExistingIDs(Set<String> ids) {
        existingIDs.addAll(ids);
        if (!ids.isEmpty()) {
            // Set medicalRecordID to the next available number after the max existing ID
            medicalRecordID = ids.stream().mapToInt(Integer::parseInt).max().orElse(0) + 1;
        }
    }
}

