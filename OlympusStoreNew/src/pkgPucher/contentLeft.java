package pkgPucher;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import pkgData.Database;
import pkgData.User;
import pkgPirolt.Cart;
import pkgUtil.CartItem;

@ManagedBean(name="contentLeft")
@SessionScoped
/**
 * Bean for contentLeft.xhtml (in template folder)
 */
public class contentLeft implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{database}")
	private Database database = null;
	
	private ArrayList<CartItem> items;

	private String name = "";
	private String password = "";
	private int productsInCart = 0;
	private String visibilityform = "";
	private String visibilityloggedin = "display:none;";
	private User user = null;

	public contentLeft() {
	}

	//Getter & Setter
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getProductsInCart() {
		return productsInCart;
	}

	public void setProductsInCart(int productsInCart) {
		this.productsInCart = productsInCart;
	}
	
	public String getVisibilityform() {
		return visibilityform;
	}

	public void setVisibilityform(String visibilityform) {
		this.visibilityform = visibilityform;
	}

	public String getVisibilityloggedin() {
		return visibilityloggedin;
	}

	public void setVisibilityloggedin(String visibilityloggedin) {
		this.visibilityloggedin = visibilityloggedin;
	}
	
	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<CartItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<CartItem> items) {
		this.items = items;
	}
	
	//Getter & Setter

	
	
	public String checkLogin() {
		
		boolean isCorrect = false;
		String redirect = "index.jsf";
		System.out.println(this.getName() + ";" + this.getPassword());
		try {
			
			isCorrect = this.database.isPasswordCorrect(this.getName(), this.getPassword());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(isCorrect) {
			this.user = this.database.getUser(this.name);
			System.out.println(user.toString());
			this.setVisibilityform("display:none;");
			this.setVisibilityloggedin("");
			if(this.user.getName().equals("admin"))
				redirect = "admin.jsf";
		}
		
		return redirect;
		
	}
	
	public String logOut() {
		
		this.setName("");
		this.setPassword("");
		this.setUser(null);
		this.setVisibilityform("");
		this.setVisibilityloggedin("display:none;");
		this.items = null;
		
		return "index?faces-redirect=true";
		
	}
	
	public void incrementProductsInCart() {
		
		int currentCart = this.getProductsInCart();
		currentCart++;
		this.setProductsInCart(currentCart);
		
	}

	
}
