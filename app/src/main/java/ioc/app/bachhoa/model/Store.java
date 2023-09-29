package ioc.app.bachhoa.model;

import java.util.List;




public class Store {

	private int storeID;


	private String address;


	private String size;

	public Store(int storeID, String address, String size) {
		this.storeID = storeID;
		this.address = address;
		this.size = size;
	}

	public Store() {
	}

	public int getStoreID() {
		return storeID;
	}

	public void setStoreID(int storeID) {
		this.storeID = storeID;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

//    @OneToMany(mappedBy = "store")
//    private List<Warehouse> warehouses;
//
//    @OneToMany(mappedBy = "store")
//    private List<Bill> bills;
//
//    @OneToMany(mappedBy = "store")
//    private List<InventoryHistory> inventoryHistories;
//
//    @OneToMany(mappedBy = "store")
//    private List<DisplayShelves> displayShelves;

}
