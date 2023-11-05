package ioc.app.bachhoa.model;

public class PurchaseHistory {
    private String id;
    private String productID;
    private DeliveryNote deliveryNote;
    private Store store;
    private Product product;
    private Employee employee;
    private int sysInventory;
    private int quantityReceived;
    private int confirmedQuantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public DeliveryNote getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(DeliveryNote deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getSysInventory() {
        return sysInventory;
    }

    public void setSysInventory(int sysInventory) {
        this.sysInventory = sysInventory;
    }

    public int getQuantityReceived() {
        return quantityReceived;
    }

    public void setQuantityReceived(int quantityReceived) {
        this.quantityReceived = quantityReceived;
    }

    public int getConfirmedQuantity() {
        return confirmedQuantity;
    }

    public void setConfirmedQuantity(int confirmedQuantity) {
        this.confirmedQuantity = confirmedQuantity;
    }
}
