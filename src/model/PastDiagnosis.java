package src.model;

import java.time.LocalDate;

public class PastDiagnosis {
    private String conditionName;
    private LocalDate diagnosisDate;


    // Constructor
    public PastDiagnosis(String conditionName, LocalDate diagnosisDate) {
        this.conditionName = conditionName;
        this.diagnosisDate = diagnosisDate;
    }

    // Getters and Setters
    public String getConditionName() { return conditionName; }
    public LocalDate getDiagnosisDate() { return diagnosisDate; }
    

    @Override
    public String toString() {
        return "Condition: " + conditionName + ", Diagnosed on: " + diagnosisDate;
    }
}

