package pkgUtil;

public class CartItem {
	
	private Product item;
	private int quantety;
	
	public CartItem(Product item) {
		super();
		this.item = item;
		this.quantety = 1;
	}
	
	public CartItem(Product item, int quantety) {
		super();
		this.item = item;
		this.quantety = quantety;
	}
	
	public float calculateFullPrice() {
		return quantety*item.getPrice();
	}
	
	public void increaseQuantity() {
		this.quantety++;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean equal = false;
		if(obj instanceof CartItem)
			if(((CartItem)obj).getItem().getId() == this.item.getId())
				equal = true;
		return equal;
	}

	public Product getItem() {
		return item;
	}

	public int getQuantety() {
		return quantety;
	}

	public void setItem(Product item) {
		this.item = item;
	}

	public void setQuantety(int quantety) {
		this.quantety = quantety;
	}
}
