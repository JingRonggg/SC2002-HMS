package src.repository;

import java.util.HashMap;

import src.model.PrescribeMedications;

public interface IMedicineRepository {
    
    void addMedicine(PrescribeMedications medication);

    PrescribeMedications getMedicine(String medicineName);

    void updateStock(String medicineName, int newStock);

    void checkLowStock();

    HashMap<String, PrescribeMedications> getAllMedicines();

    void updateStatus( String medicineName, String status);

    void checkReplenishReq();
}
