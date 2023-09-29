package ioc.app.bachhoa.model;


public class ProductPositioning {
	private Integer id;

	private DisplayPlatter displayPlatter;
	private DisplayShelves displayShelves;

	private Product product;

	private int displayQuantity;

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

}
