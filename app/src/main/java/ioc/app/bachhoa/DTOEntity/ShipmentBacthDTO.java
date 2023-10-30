package ioc.app.bachhoa.DTOEntity;


import ioc.app.bachhoa.model.ProductPositioning;
import ioc.app.bachhoa.model.ShipmentBatchDetail;

public class ShipmentBacthDTO {
    private ShipmentBatchDetail shipmentBatchDetail;
    private ProductPositioning productPositioning;


    public ShipmentBacthDTO() {
    }

    public ShipmentBacthDTO(ShipmentBatchDetail shipmentBatchDetail, ProductPositioning productPositioning) {
        this.shipmentBatchDetail = shipmentBatchDetail;
        this.productPositioning = productPositioning;
    }

    public ShipmentBatchDetail getShipmentBatchDetail() {
        return shipmentBatchDetail;
    }

    public void setShipmentBatchDetail(ShipmentBatchDetail shipmentBatchDetail) {
        this.shipmentBatchDetail = shipmentBatchDetail;
    }

    public ProductPositioning getProductPositioning() {
        return productPositioning;
    }

    public void setProductPositioning(ProductPositioning productPositioning) {
        this.productPositioning = productPositioning;
    }
}