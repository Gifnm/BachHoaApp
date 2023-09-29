package ioc.app.bachhoa.model;


public class Warehouse {
	private int wareHouseID;

	private Store store;

	private Product product;

	private int quantity;

	public int getWareHouseID() {
		return wareHouseID;
	}

	public void setWareHouseID(int wareHouseID) {
		this.wareHouseID = wareHouseID;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
