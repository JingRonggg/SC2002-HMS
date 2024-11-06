package src.interfaces;

import src.model.MedicalRecord;
import src.model.PastDiagnosis;
import src.model.PrescribeMedications;
import src.model.Treatments;

import java.util.HashMap;
import java.util.List;

public interface IMedicalRecordRepository extends IMedicalRecordBasicOperations, IMedicalRecordDoctorOperations,IMedicalRecordMedicineOperations{
    void storeIntoCsv() ;
}
