package src.enums;

/**
 * Enum representing the different possible statuses of an appointment in the hospital system.
 */
public enum AppointmentStatus {
    /** Indicates that the appointment has been completed */
    COMPLETED,
    /** Indicates that the appointment is pending confirmation */
    PENDING, 
    /** Indicates that the appointment has been confirmed */
    CONFIRMED,
    /** Indicates that the appointment has been cancelled */
    CANCELLED,
    /** Indicates that the appointment slot is not available */
    NOT_AVAILABLE,
    /** Indicates that the appointment slot is available for booking */
    AVAILABLE
}
