package src.utils;

import java.util.HashSet;
import java.util.Set;

public class AppointmentIDGenerator {
    private static int appointmentID = 0;
    private static Set<String> existingIDs = new HashSet<>();

    public static synchronized String nextAppointmentID() {
        String newID;
        do {
            newID = String.valueOf(appointmentID++);
        } while (existingIDs.contains(newID)); // Ensure the ID is unique
        existingIDs.add(newID); // Add the new ID to the set
        return newID;
    }

    public static void initializeWithExistingIDs(Set<String> ids) {
        existingIDs.addAll(ids);
        if (!ids.isEmpty()) {
            // Set appointmentID to the next available number after the max existing ID
            appointmentID = ids.stream().mapToInt(Integer::parseInt).max().orElse(0) + 1;
        }
    }
}
