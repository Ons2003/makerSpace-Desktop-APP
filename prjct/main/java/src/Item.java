package src;

public class Item {

    private String status ;
    private String name ;
    private String category ;
    private String reference ;
    private int quantity ;

    public Item(){
        this.status = "AVAILABLE" ;
        this.name = "";
        this.category ="";
        this.reference = "";
        this.quantity = 0 ;
    }

    public Item(String name,String category,String reference,int quantity,String status){
        this.status = status ;
        this.name = name ;
        this.category = category ;
        this.reference = reference ;
        this.quantity = quantity ;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getReference() {
        return reference;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
