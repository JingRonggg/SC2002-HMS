package src.utils;

import java.util.regex.Pattern;

public class InputValidator {
    private static final String HOSPITAL_ID_PATTERN = "^P\\d{4}$"; // P followed by 4 digits
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_PATTERN = "^[89]\\d{7}$"; // Singapore phone number format

    public static boolean isValidHospitalID(String hospitalID) {
        return hospitalID != null && Pattern.matches(HOSPITAL_ID_PATTERN, hospitalID);
    }

    public static boolean isValidEmail(String email) {
        return email != null && Pattern.matches(EMAIL_PATTERN, email);
    }

    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && Pattern.matches(PHONE_PATTERN, phone);
    }
    
    public static boolean isValidPatientID(String patientID) {
        return isValidHospitalID(patientID);
    }
    
    public static boolean isValidDateFormat(String date) {
        String datePattern = "^\\d{2}/\\d{2}/\\d{4}$";
        return date != null && Pattern.matches(datePattern, date);
    }
    
    public static boolean isValidBloodType(String bloodType) {
        String[] validTypes = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        for (String type : validTypes) {
            if (type.equals(bloodType)) {
                return true;
            }
        }
        return false;
    }
}