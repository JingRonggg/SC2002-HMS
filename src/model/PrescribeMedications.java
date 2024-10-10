package src.model;

public class PrescribeMedications {
    private String medicinename;
    private int stock;
    private int lowstockalert;

    public PrescribeMedications(String medicinename, int stock, int lowstockalert){
        this.medicinename = medicinename;
        this.stock = stock;
        this.lowstockalert = lowstockalert;
    }

    public String getMedicineName(){
      return medicinename;
    }
    
    public void newMedicine(String medicinename){
        this.medicinename = medicinename;
    }

    public int getStock(){
        return stock;
    }

    public void setStock(int stock){
        this.stock = stock;
    }

    public int getStockAlert(){
        return lowstockalert;
    }

    public void setStockAlert(int lowstockalert){
        this.lowstockalert = lowstockalert;
    }
}
