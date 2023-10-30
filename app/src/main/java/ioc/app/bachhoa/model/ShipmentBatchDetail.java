package ioc.app.bachhoa.model;

public class ShipmentBatchDetail {
    private String shiBatID;
    private String productID;
    private ShipmentBatch shipmentBatch;
    private Product product;
    private Employee employee;
    private boolean situation;
    private String pictureURL;
    private int quantity;
    private int storeID;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean isSituation() {
        return situation;
    }

    public void setSituation(boolean situation) {
        this.situation = situation;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getShiBatID() {
        return shiBatID;
    }

    public void setShiBatID(String shiBatID) {
        this.shiBatID = shiBatID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public ShipmentBatch getShipmentBatch() {
        return shipmentBatch;
    }

    public void setShipmentBatch(ShipmentBatch shipmentBatch) {
        this.shipmentBatch = shipmentBatch;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }
}
