package ioc.app.bachhoa.DTOEntity;

import ioc.app.bachhoa.model.ProductPositioning;

public class ProductOnShelf {
    private ProductPositioning productPositioning;
    private int numberOnShelf;
    private int status;

    public ProductOnShelf(ProductPositioning productPositioning, int numberOnShelf) {
        this.productPositioning = productPositioning;
        this.numberOnShelf = numberOnShelf;
    }

    public ProductPositioning getProductPositioning() {
        return productPositioning;
    }

    public void setProductPositioning(ProductPositioning productPositioning) {
        this.productPositioning = productPositioning;
    }

    public int getNumberOnShelf() {
        return numberOnShelf;
    }

    public void setNumberOnShelf(int numberOnShelf) {
        this.numberOnShelf = numberOnShelf;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
