package ioc.app.bachhoa.model;

public class DisplayShelves {

    private int disSheID;

    private String shelfName;

    private Store store;

    public DisplayShelves(int disSheID, String shelfName, Store store) {
        this.disSheID = disSheID;
        this.shelfName = shelfName;
        this.store = store;
    }

    public DisplayShelves() {
    }

    public int getDisSheID() {
        return disSheID;
    }

    public void setDisSheID(int disSheID) {
        this.disSheID = disSheID;
    }

    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

}
