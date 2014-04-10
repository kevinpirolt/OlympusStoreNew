package pkgPucher;

import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import pkgData.Database;
import pkgData.User;
import pkgPirolt.Cart;

@ManagedBean
@SessionScoped
/**
 * Bean for contentLeft.xhtml (in template folder)
 */
public class contentLeft implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{database}")
	private Database database = null;

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

	
	//Getter & Setter

	
	
	public String checkLogin() {
		
		boolean isCorrect = false;
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
		}
		
		return "index.jsf";
		
	}
	
	public String logOut() {
		
		this.setName("");
		this.setPassword("");
		this.setUser(null);
		this.setVisibilityform("");
		this.setVisibilityloggedin("display:none;");
		
		return "index.jsf";
		
	}
	
	public void incrementProductsInCart() {
		
		int currentCart = this.getProductsInCart();
		currentCart++;
		this.setProductsInCart(currentCart);
		
	}

	
}
