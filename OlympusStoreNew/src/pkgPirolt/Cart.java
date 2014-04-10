package pkgPirolt;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import pkgData.Database;
import pkgUtil.CartItem;
import pkgUtil.Product;

@ManagedBean(name="cart")
@SessionScoped
public class Cart {

	private ArrayList<CartItem> items;
	
	@ManagedProperty(value="#{database}")
	private Database database;
	
	public String addItem(Product p) {
		CartItem toAdd = null;
		if((toAdd = checkProductAlreadyInCart(p)) != null)
			toAdd.increaseQuantity();
		else
			this.items.add(new CartItem(p));
		return "checkout";
	}

	private CartItem checkProductAlreadyInCart(Product p) {
		initItems();
		System.out.println("items are: " + this.items);
		System.out.println("Product is: " + p);
		CartItem inCart = null;
		for(CartItem ci : items)
			if(ci.getItem().equals(p))
				inCart = ci;
		return inCart;
	}

	public float getFullPrice() {
		float price = 0;
		for(CartItem ci : items)
			price += ci.calculateFullPrice();
		return price;
	}
	
	public int getItemCount() {
		initItems();
		int count = 0;
		for(CartItem ci : items)
			count += ci.getQuantety();
		return count;
	}
	
	public void initItems() {
		if(this.items == null)
			this.items = new ArrayList<CartItem>();
	}
	
	public String checkOut() {
		this.database.insertCartAndCartItems(this.items);
		this.items = null;
		return "index";
	}
	
	//********************************************************************************
	public void setDatabase(Database database) {
		this.database = database;
	}
	
	public Database getDatabase() {
		return this.database;
	}
	
	public void setItems(ArrayList<CartItem> items) {
		this.items = items;
	}
	
	public ArrayList<CartItem> getItems() {
		initItems();
		return items;
	}
}
