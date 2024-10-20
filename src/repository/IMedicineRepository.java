package src.repository;

import java.util.HashMap;

import src.model.PrescribeMedications;

public interface IMedicineRepository {
    
    public void addMedicine(PrescribeMedications medication, int stock, int lowStockAlert);

    public PrescribeMedications getMedicine(String medicineName);
    
    public HashMap<String, PrescribeMedications> getAllMedicines();

    public int getStock(String medicineName);

    public void setStock(String medicineName, int stock);

    public int getStockAlert(String medicineName);

    public void setStockAlert(String medicineName, int lowStockAlert);

    public void checkLowStock();

    public void updateStatus(String medicineName, String status);

    public void checkReplenishReq();

    public void deleteMedicine(String medicineName);

    public void updateMedicine(String medicineName, int stock, int lowStockAlert);

}
