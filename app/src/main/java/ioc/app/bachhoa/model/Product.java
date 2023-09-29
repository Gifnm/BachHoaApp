package ioc.app.bachhoa.model;

import java.sql.Date;

public class Product {

	private String productID;

	private Categories categories;

	private float price;

	private int vat;

	private Date nearestExpDate;

	private String productName;

	private Boolean status;
	private String image;
	private float importPrice;

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public Categories getCategories() {
		return categories;
	}

	public void setCategories(Categories categories) {
		this.categories = categories;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getVat() {
		return vat;
	}

	public void setVat(int vat) {
		this.vat = vat;
	}

	public Date getNearestExpDate() {
		return nearestExpDate;
	}

	public void setNearestExpDate(Date nearestExpDate) {
		this.nearestExpDate = nearestExpDate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public float getImportPrice() {
		return importPrice;
	}

	public void setImportPrice(float importPrice) {
		this.importPrice = importPrice;
	}
	//    @OneToMany(mappedBy = "product")
//    private List<Warehouse> warehouses;
//
//    @OneToMany(mappedBy = "product")
//    private List<BillDetail> billDetails;
//
//    @OneToMany(mappedBy = "product")
//    private List<ProductPositioning> productPositionings;
//
//    @OneToMany(mappedBy = "product")
//    private List<DiscountDetails> discountDetails;

}
