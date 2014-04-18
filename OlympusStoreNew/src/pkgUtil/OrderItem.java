package pkgUtil;

public class OrderItem {
	private int itemId;
	private String itemName;
	private float price;
	private int quantity;
	
	public OrderItem(int itemId, String itemName, float price, int quantity) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.price = price;
		this.quantity = quantity;
	}

	public int getItemId() {
		return itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public float getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public float getFullPrice() {
		return (this.price * this.quantity);
	}
}
