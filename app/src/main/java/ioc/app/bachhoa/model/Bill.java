package ioc.app.bachhoa.model;

import java.sql.Date;
import java.sql.Timestamp;


public class Bill {

    private String billID;
    private Store store;
    private Employee employee;
    private float totalAmount;
    private Timestamp timeCreate;
    private float cash;
    private float reduced;

    public Bill(String billID) {
        this.billID = billID;
    }

    public Bill() {
    }

    public Bill(String billID, Store store, Employee employee, float totalAmount, Timestamp timeCreate) {
        this.billID = billID;
        this.store = store;
        this.employee = employee;
        this.totalAmount = totalAmount;
        this.timeCreate = timeCreate;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Timestamp getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(Timestamp timeCreate) {
        this.timeCreate = timeCreate;
    }

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }

    public float getReduced() {
        return reduced;
    }

    public void setReduced(float reduced) {
        this.reduced = reduced;
    }
}
