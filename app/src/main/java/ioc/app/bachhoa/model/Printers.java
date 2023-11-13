package ioc.app.bachhoa.model;

public class Printers {
    private String ipAddress;
    private Store store;
    private String nameOfPrinter;
    private int pageSize;

    public Printers() {
    }

    public Printers(String ipAddress, Store store, String nameOfPrinter, int pageSize) {
        this.ipAddress = ipAddress;
        this.store = store;
        this.nameOfPrinter = nameOfPrinter;
        this.pageSize = pageSize;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAdress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getNameOfPrinter() {
        return nameOfPrinter;
    }

    public void setNameOfPrinter(String nameOfPrinter) {
        this.nameOfPrinter = nameOfPrinter;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
