package src.model;

import java.time.LocalDate;

/**
 * Represents a past medical diagnosis with condition name and diagnosis date.
 */
public class PastDiagnosis {
    /** The name of the diagnosed medical condition */
    private String conditionName;
    /** The date when the diagnosis was made */
    private LocalDate diagnosisDate;

    /**
     * Constructs a new PastDiagnosis with the specified condition and date.
     * 
     * @param conditionName The name of the diagnosed condition
     * @param diagnosisDate The date when the diagnosis was made
     */
    public PastDiagnosis(String conditionName, LocalDate diagnosisDate) {
        this.conditionName = conditionName;
        this.diagnosisDate = diagnosisDate;
    }

    /**
     * Gets the name of the diagnosed condition.
     * @return The condition name
     */
    public String getConditionName() { return conditionName; }

    /**
     * Gets the date when the diagnosis was made.
     * @return The diagnosis date
     */
    public LocalDate getDiagnosisDate() { return diagnosisDate; }

    /**
     * Returns a string representation of the past diagnosis.
     * @return A string containing the condition name and diagnosis date
     */
    @Override
    public String toString() {
        return "Condition: " + conditionName + ", Diagnosed on: " + diagnosisDate;
    }
}
