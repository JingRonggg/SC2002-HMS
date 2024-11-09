package src.utils;

import java.util.regex.Pattern;

/**
 * Utility class for validating various input formats used in the hospital system.
 */
public class InputValidator {
    private static final String HOSPITAL_ID_PATTERN = "^P\\d{4}$"; // P followed by 4 digits
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_PATTERN = "^[89]\\d{7}$"; // Singapore phone number format

    /**
     * Validates if the given hospital ID matches the required format.
     * Valid format is 'P' followed by 4 digits.
     *
     * @param hospitalID The hospital ID to validate
     * @return true if the hospital ID is valid, false otherwise
     */
    public static boolean isValidHospitalID(String hospitalID) {
        return hospitalID != null && Pattern.matches(HOSPITAL_ID_PATTERN, hospitalID);
    }

    /**
     * Validates if the given email address matches the standard email format.
     *
     * @param email The email address to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return email != null && Pattern.matches(EMAIL_PATTERN, email);
    }

    /**
     * Validates if the given phone number matches the Singapore phone number format.
     * Valid format is 8 or 9 followed by 7 digits.
     *
     * @param phone The phone number to validate
     * @return true if the phone number is valid, false otherwise
     */
    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && Pattern.matches(PHONE_PATTERN, phone);
    }
    
    /**
     * Validates if the given patient ID is valid.
     * Uses the same format as hospital ID.
     *
     * @param patientID The patient ID to validate
     * @return true if the patient ID is valid, false otherwise
     */
    public static boolean isValidPatientID(String patientID) {
        return isValidHospitalID(patientID);
    }
    
    /**
     * Validates if the given date string matches the DD/MM/YYYY format.
     *
     * @param date The date string to validate
     * @return true if the date format is valid, false otherwise
     */
    public static boolean isValidDateFormat(String date) {
        String datePattern = "^\\d{2}/\\d{2}/\\d{4}$";
        return date != null && Pattern.matches(datePattern, date);
    }
    
    /**
     * Validates if the given blood type is one of the standard blood types.
     * Valid blood types are: A+, A-, B+, B-, O+, O-, AB+, AB-
     *
     * @param bloodType The blood type to validate
     * @return true if the blood type is valid, false otherwise
     */
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