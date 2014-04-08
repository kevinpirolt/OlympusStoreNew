package pkgPirolt;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.sun.istack.internal.logging.Logger;

import pkgData.Database;
import pkgOlympusRestClient.OlympusRestClient;
import pkgUtil.Product;

@ManagedBean
@SessionScoped
public class Cd implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{database}")
	private Database database = null;
	
	@ManagedProperty(value="#{olympusRestClient}")
	private OlympusRestClient olympusRestClient = null;
	
	@ManagedProperty(value="#{currentProduct}")
	private CurrentProduct currentProduct;
	
	public CurrentProduct getCurrentProduct() {
		return currentProduct;
	}

	public void setCurrentProduct(CurrentProduct currentProduct) {
		this.currentProduct = currentProduct;
	}

	public OlympusRestClient getOlympusRestClient() {
		return olympusRestClient;
	}

	public void setOlympusRestClient(OlympusRestClient olympusRestClient) {
		this.olympusRestClient = olympusRestClient;
	}

	private ArrayList<Product> products;
	
	public Cd() {
		
	}
	
	public void fillProducts() {
		Logger.getLogger(Cd.class).info("In Fill Products");
		System.out.println("In Fill Products mit syso");
		this.products = this.olympusRestClient.getLatestProducts("CD");
		Logger.getLogger(Cd.class).info("products: " + this.products.size());
		System.out.println("products: " + this.products.size());
	}

	public Database getDatabase() {
		return database;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
}
