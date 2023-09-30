package ioc.app.bachhoa.model;

public class BillDetail {

	private String billID;

	private Bill bill;

	private Product product;

	private int quantity;

	private float totalAmount;

	public BillDetail(String billID, Bill bill, Product product, int quantity, float totalAmount) {
		this.billID = billID;
		this.bill = bill;
		this.product = product;
		this.quantity = quantity;
		this.totalAmount = totalAmount;
	}

	public BillDetail() {
	}

	public String getId() {
		return billID;
	}

	public void setId(String id) {
		this.billID = id;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public float countTotalAmount(){
		return this.quantity * this.product.getPrice();

	}

}
