package ioc.app.bachhoa.model;

public class DisplayPlatter {

	private int disPlaID;

	private String rowName;

	private Store store;
	public int getDisPlaID() {
		return disPlaID;
	}

	public DisplayPlatter() {

	}

	public DisplayPlatter(int disPlaID) {
		this.disPlaID = disPlaID;
	}

	public DisplayPlatter(int disPlaID, String rowName, Store store) {

		this.disPlaID = disPlaID;
		this.rowName = rowName;
		this.store = store;
	}

	public void setDisPlaID(int disPlaID) {
		this.disPlaID = disPlaID;
	}

	public String getRowName() {
		return rowName;
	}

	public void setRowName(String rowName) {
		this.rowName = rowName;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}


}
