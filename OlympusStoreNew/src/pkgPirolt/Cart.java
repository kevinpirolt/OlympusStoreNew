package pkgPirolt;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pkgData.Database;
import pkgOlympusRestClient.OlympusRestClient;
import pkgUtil.CartItem;
import pkgUtil.Product;

@ManagedBean(name = "cart")
@SessionScoped
public class Cart extends ParentOlympusBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{database}")
	private Database database;

	@ManagedProperty(value = "#{olympusRestClient}")
	private OlympusRestClient olympusRestClient;

	public String addItem(Product p) {
		String ret = null;
		CartItem toAdd = null;
		this.olympusRestClient.updateProductQuantity(new Product(p.getId(), 1));
		if (this.contentLeft.getUser() != null) {
			if ((toAdd = checkProductAlreadyInCart(p)) != null)
				toAdd.increaseQuantity();
			else
				this.contentLeft.getItems().add(new CartItem(p));
			ret = "checkout";
		} else {
			ret = "login";
			this.contentLeft.setItems(null);
		}
		return ret;
	}

	private CartItem checkProductAlreadyInCart(Product p) {
		initItems();
		System.out.println("items are: " + this.contentLeft.getItems());
		System.out.println("Product is: " + p);
		CartItem inCart = null;
		for (CartItem ci : contentLeft.getItems())
			if (ci.getItem().equals(p))
				inCart = ci;
		return inCart;
	}
	
	public String getPriceWith2Comma() {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(this.getFullPrice());
	}
	
	public String getDPriceWith2Comma() {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(this.getPriceWithDiscount());
	}

	public float getFullPrice() {
		float price = 0;
		for (CartItem ci : contentLeft.getItems())
			price += ci.calculateFullPrice();
		return price;
	}
	
	public float getPriceWithDiscount() {
		float price = this.getFullPrice();
		float discountPrice = price * this.contentLeft.getUser().getDiscountValue();
		return discountPrice;
	}

	public int getItemCount() {
		initItems();
		int count = 0;
		for (CartItem ci : contentLeft.getItems())
			count += ci.getQuantety();
		return count;
	}

	public void initCart() {
		System.out.println("In initCart");
		checkUserSignedIn();
		this.initItems();
	}

	public void initItems() {
		if (this.contentLeft.getItems() == null)
			this.contentLeft.setItems(new ArrayList<CartItem>());
	}

	private void checkUserSignedIn() {
		System.out.println("CheckUser");
		if (this.contentLeft.getUser() == null)
			try {
				this.contentLeft.setItems(null);
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("login.jsf");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public String checkOut() {
		try {
				this.database
						.insertCartAndCartItems(this.contentLeft.getItems(),
								this.contentLeft.getUser());
				this.contentLeft.setItems(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.contentLeft.setItems(null);
		return "index";
	}
	
	public String checkOutWithDiscount() {
		try {
			this.database.insertCartAndCartItems(this.contentLeft.getItems(), 
					this.contentLeft.getUser(), this.contentLeft.getUser().getDiscount());
			this.contentLeft.getUser().setDiscount(0);
			this.contentLeft.setItems(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
		this.contentLeft.setItems(items);
	}

	public ArrayList<CartItem> getItems() {
		initItems();
		return this.contentLeft.getItems();
	}

	public OlympusRestClient getOlympusRestClient() {
		return olympusRestClient;
	}

	public void setOlympusRestClient(OlympusRestClient olympusRestClient) {
		this.olympusRestClient = olympusRestClient;
	}

	@Override
	public void onLoad() {
		if (this.isAdmin())
			this.redirectToAdmin();
		else if (!this.isLoggedIn())
			this.reidrectToLogin();
		else
			this.initCart();
	}

	@Override
	public void onLoad(String type) {
	}

}
