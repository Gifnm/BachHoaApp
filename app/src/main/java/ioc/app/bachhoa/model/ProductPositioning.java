package ioc.app.bachhoa.model;


public class ProductPositioning {
    private Integer id;
    private DisplayPlatter displayPlatter;
    private DisplayShelves displayShelves;
    private Product product;
    private int displayQuantity;
    private Store store;
    private int form;

    public ProductPositioning(Integer id, DisplayPlatter displayPlatter, DisplayShelves displayShelves, Product product, int displayQuantity, Store store) {
        this.id = id;
        this.displayPlatter = displayPlatter;
        this.displayShelves = displayShelves;
        this.product = product;
        this.displayQuantity = displayQuantity;
        this.store = store;
    }

    public ProductPositioning(Integer id, DisplayPlatter displayPlatter, DisplayShelves displayShelves, Product product, int displayQuantity, Store store, int form) {
        this.id = id;
        this.displayPlatter = displayPlatter;
        this.displayShelves = displayShelves;
        this.product = product;
        this.displayQuantity = displayQuantity;
        this.store = store;
        this.form = form;
    }

    public ProductPositioning() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DisplayPlatter getDisplayPlatter() {
        return displayPlatter;
    }

    public void setDisplayPlatter(DisplayPlatter displayPlatter) {
        this.displayPlatter = displayPlatter;
    }

    public DisplayShelves getDisplayShelves() {
        return displayShelves;
    }

    public void setDisplayShelves(DisplayShelves displayShelves) {
        this.displayShelves = displayShelves;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getDisplayQuantity() {
        return displayQuantity;
    }

    public void setDisplayQuantity(int displayQuantity) {
        this.displayQuantity = displayQuantity;
    }


    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public int getForm() {
        return form;
    }

    public void setForm(int form) {
        this.form = form;
    }
}
