package src.utils;

/**
 * Utility class for generating unique patient identification numbers.
 */
public class PatientIDGenerator {
    /** The current ID counter starting from 1000 */
    private static int currentID = 1000;

    /**
     * Generates a unique patient ID in the format "P" followed by a number.
     * This method is thread-safe.
     *
     * @return A unique patient ID string starting with "P" followed by a sequential number
     */
    public static synchronized String generatePatientID() {
        currentID++;
        return "P" + currentID;
    }
}