package ioc.app.bachhoa.model;

import java.sql.Date;
import java.util.List;




public class Employee {

	private int employeeID;

	private String employeeName;

	private Date age;

	private String address;

	private String pictureURL;

	private Date firstWork;

	private Store store;

	private Role role;
	private boolean activity;
	private String email;
	private String passwork;
	private Boolean status;

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Date getAge() {
		return age;
	}

	public void setAge(Date age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public Date getFirstWork() {
		return firstWork;
	}

	public void setFirstWork(Date firstWork) {
		this.firstWork = firstWork;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isActivity() {
		return activity;
	}

	public void setActivity(boolean activity) {
		this.activity = activity;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswork() {
		return passwork;
	}

	public void setPasswork(String passwork) {
		this.passwork = passwork;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	//    @OneToMany(mappedBy = "employee")
//    private List<Bill> bills;
//
//    @OneToMany(mappedBy = "employee")
//    private List<DetailWorkSchedule> detailWorkSchedules;
//
//    @OneToMany(mappedBy = "employee")
//    private List<InventoryHistory> inventoryHistories;
//
//    @OneToMany(mappedBy = "employee")
//    private List<ShipmentBatch> shipmentBatches;
//
//    @OneToMany(mappedBy = "employee")
//    private List<ShipmentBatchDetail> shipmentBatchDetails;

}
