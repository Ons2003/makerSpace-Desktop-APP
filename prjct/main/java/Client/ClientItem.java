package Client;

import src.Item;

public class ClientItem extends Item {
    private int selectedQuantity = 0 ;

    public int getSelectedQuantity(){
        return selectedQuantity ;
    }
    public void setSelectedQuantity(int selectedQuantity){
        this.selectedQuantity = selectedQuantity ;
    }
}
