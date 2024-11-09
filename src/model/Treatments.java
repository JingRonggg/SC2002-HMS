package src.model;

import java.time.LocalDate;

/**
 * Represents a medical treatment with a name, date, and details.
 */
public class Treatments {
    private String treatmentName;
    private LocalDate treatmentDate; 
    private String treatmentDetails;

    /**
     * Constructs a new Treatment with the specified name, date and details.
     *
     * @param treatmentName    The name of the treatment
     * @param treatmentDate    The date when the treatment was administered
     * @param treatmentDetails Additional details about the treatment
     */
    public Treatments(String treatmentName, LocalDate treatmentDate, String treatmentDetails) {
        this.treatmentName = treatmentName;
        this.treatmentDate = treatmentDate;
        this.treatmentDetails = treatmentDetails;
    }

    /**
     * Gets the name of the treatment.
     *
     * @return The treatment name
     */
    public String getTreatmentName() {
        return treatmentName;
    }

    /**
     * Gets the date of the treatment.
     *
     * @return The treatment date
     */
    public LocalDate getTreatmentDate() {
        return treatmentDate;
    }

    /**
     * Gets the details of the treatment.
     *
     * @return The treatment details
     */
    public String getTreatmentDetails() {
        return treatmentDetails;
    }

    /**
     * Returns a string representation of the treatment.
     *
     * @return A string containing the treatment name, date, and details
     */
    @Override
    public String toString() {
        return "Treatment: " + treatmentName + " on " + treatmentDate + " - " + treatmentDetails;
    }
}
