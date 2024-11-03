package src.utils;

public class PatientIDGenerator {
    private static int currentID = 1000;

    public static synchronized String generatePatientID() {
        currentID++;
        return "P" + currentID;
    }
}