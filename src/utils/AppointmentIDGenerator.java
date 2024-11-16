package src.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for generating unique appointment IDs.
 * This class maintains a set of existing IDs and provides thread-safe ID generation.
 */
public class AppointmentIDGenerator {
    /** Counter for generating sequential appointment IDs */
    private static int appointmentID = 0;
    
    /** Set to track existing appointment IDs and ensure uniqueness */
    private static Set<String> existingIDs = new HashSet<>();

    /**
     * Generates the next unique appointment ID in a thread-safe manner.
     * The method ensures that the generated ID does not conflict with any existing IDs.
     *
     * @return A unique string ID for a new appointment
     */
    public static synchronized String nextAppointmentID() {
        String newID;
        do {
            newID = String.valueOf(appointmentID++);
        } while (existingIDs.contains(newID)); // Ensure the ID is unique
        existingIDs.add(newID); // Add the new ID to the set
        return newID;
    }

    /**
     * Initializes the ID generator with a set of existing IDs.
     * Updates the internal counter to ensure new IDs will be unique.
     *
     * @param ids Set of existing appointment IDs to initialize with
     */
    public static void initializeWithExistingIDs(Set<String> ids) {
        existingIDs.addAll(ids);
        if (!ids.isEmpty()) {
            // Set appointmentID to the next available number after the max existing ID
            appointmentID = ids.stream().mapToInt(Integer::parseInt).max().orElse(0) + 1;
        }
    }
}
