package ioc.app.bachhoa.model;

import java.util.Date;


public class ShipmentBatch {

    private String shiBatID;

    private Store store;

    private Employee employee;

    private Date createTime;

    private Date finishTime;

    private boolean situation;

    public ShipmentBatch() {
    }

    public ShipmentBatch(String shiBatID, Store store, Employee employee, Date createTime, Date finishTime, boolean situation) {
        this.shiBatID = shiBatID;
        this.store = store;
        this.employee = employee;
        this.createTime = createTime;
        this.finishTime = finishTime;
        this.situation = situation;
    }

    public String getShiBatID() {
        return shiBatID;
    }

    public void setShiBatID(String shiBatID) {
        this.shiBatID = shiBatID;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public boolean isSituation() {
        return situation;
    }

    public void setSituation(boolean situation) {
        this.situation = situation;
    }

}
