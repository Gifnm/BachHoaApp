package ioc.app.bachhoa.DTOEntity;

import ioc.app.bachhoa.model.DiscountDetails;
import ioc.app.bachhoa.model.ProductPositioning;

public class PriceTag {
private DiscountDetails discountDetails;
private ProductPositioning productPositioning;

    public DiscountDetails getDiscountDetails() {
        return discountDetails;
    }

    public void setDiscountDetails(DiscountDetails discountDetails) {
        this.discountDetails = discountDetails;
    }

    public ProductPositioning getProductPositioning() {
        return productPositioning;
    }

    public void setProductPositioning(ProductPositioning productPositioning) {
        this.productPositioning = productPositioning;
    }
}
