package src.model;

import java.time.LocalDate;

public class PastDiagnosis {
    private String conditionName;
    private LocalDate diagnosisDate;
    private String status;  // e.g., "active", "resolved", etc.

    // Constructor
    public PastDiagnosis(String conditionName, LocalDate diagnosisDate, String status) {
        this.conditionName = conditionName;
        this.diagnosisDate = diagnosisDate;
        this.status = status;
    }

    // Getters and Setters
    public String getConditionName() { return conditionName; }
    public LocalDate getDiagnosisDate() { return diagnosisDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Condition: " + conditionName + ", Diagnosed on: " + diagnosisDate + ", Status: " + status;
    }
}

