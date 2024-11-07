package src.utils;

public class MedicalRecordIDGenerator {
    private static int medicalRecordID = 0;

    public static synchronized String nextMedicalRecordID() {
        return String.valueOf(medicalRecordID++);
    }
}
