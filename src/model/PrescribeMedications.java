package src.model;

public class PrescribeMedications {
    private String medicineName;
    private int stock;
    private int lowStockAlert;
    private String status;

    public PrescribeMedications(String medicineName, int stock, int lowStockAlert){
        this.medicineName = medicineName;
        this.stock = stock;
        this.lowStockAlert = lowStockAlert;
        this.status = "disperse";
    }

    public String getMedicineName(){
      return medicineName;
    }
    
    public void newMedicine(String medicineName){
        this.medicineName = medicineName;
    }

    public int getStock(){
        return stock;
    }

    public void setStock(int stock){
        this.stock = stock;
    }

    public int getStockAlert(){
        return lowStockAlert;
    }

    public void setStockAlert(int lowStockAlert){
        this.lowStockAlert = lowStockAlert;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
