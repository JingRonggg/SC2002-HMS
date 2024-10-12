package src.model;

import java.time.LocalDate;

public class Treatments {
    private String treatmentName;
    private LocalDate treatmentDate;
    private String treatmentDetails;

    public Treatments(String treatmentName, LocalDate treatmentDate, String treatmentDetails) {
        this.treatmentName = treatmentName;
        this.treatmentDate = treatmentDate;
        this.treatmentDetails = treatmentDetails;
    }

    public String getTreatmentName() {
        return treatmentName;
    }
    public LocalDate getTreatmentDate() {
        return treatmentDate;
    }
    public String getTreatmentDetails() {
        return treatmentDetails;
    }

    @Override
    public String toString() {
        return "Treatment: " + treatmentName + " on " + treatmentDate + " - " + treatmentDetails;
    }
}

