package pkgPirolt;

import java.io.IOException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pkgData.Database;
import pkgPucher.contentLeft;
import pkgUtil.CartItem;
import pkgUtil.Product;

@ManagedBean(name = "cart")
@SessionScoped
public class Cart {

	private ArrayList<CartItem> items;

	@ManagedProperty(value = "#{database}")
	private Database database;

	@ManagedProperty(value = "#{contentLeft}")
	private contentLeft contentLeft;

	public String addItem(Product p) {
		String ret = null;
		CartItem toAdd = null;
		if (this.contentLeft.getUser() != null) {
			if ((toAdd = checkProductAlreadyInCart(p)) != null)
				toAdd.increaseQuantity();
			else
				this.items.add(new CartItem(p));
			ret = "checkout";
		}
		else
			ret = "login";
		return ret;
	}

	private CartItem checkProductAlreadyInCart(Product p) {
		initItems();
		System.out.println("items are: " + this.items);
		System.out.println("Product is: " + p);
		CartItem inCart = null;
		for (CartItem ci : items)
			if (ci.getItem().equals(p))
				inCart = ci;
		return inCart;
	}

	public float getFullPrice() {
		float price = 0;
		for (CartItem ci : items)
			price += ci.calculateFullPrice();
		return price;
	}

	public int getItemCount() {
		initItems();
		int count = 0;
		for (CartItem ci : items)
			count += ci.getQuantety();
		return count;
	}

	public void initCart() {
		System.out.println("In initCart");
		checkUserSignedIn();
		this.initItems();
	}

	public void initItems() {
		if (this.items == null)
			this.items = new ArrayList<CartItem>();
	}

	private void checkUserSignedIn() {
		System.out.println("CheckUser");
		if (this.contentLeft.getUser() == null)
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("login.jsf");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public String checkOut() {
		this.database.insertCartAndCartItems(this.items);
		this.items = null;
		return "index";
	}

	// ********************************************************************************
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

	public contentLeft getContentLeft() {
		return contentLeft;
	}

	public void setContentLeft(contentLeft contentLeft) {
		this.contentLeft = contentLeft;
	}

}
