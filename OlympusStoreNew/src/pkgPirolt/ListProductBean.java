package pkgPirolt;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import pkgData.Database;
import pkgOlympusRestClient.OlympusRestClient;
import pkgPucher.contentLeft;
import pkgUtil.Product;

@ManagedBean(name = "listProductBean")
@SessionScoped
public class ListProductBean extends ParentOlympusBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{database}")
	private Database database = null;

	@ManagedProperty(value = "#{olympusRestClient}")
	private OlympusRestClient olympusRestClient = null;

	@ManagedProperty(value = "#{currentProduct}")
	private CurrentProduct currentProduct;

	@ManagedProperty(value = "#{search}")
	private Search search;

	private String productShowAdress;

	public String getProductShowAdress() {
		return productShowAdress;
	}

	public void setProductShowAdress(String productShowAdress) {
		this.productShowAdress = productShowAdress;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

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

	public ListProductBean() {

	}

	public void fillProducts(String pType) {
		if (!isAdmin()) {
			this.products = this.olympusRestClient.getLatestProducts(pType);
		} else {
			this.redirectToAdmin();
		}
	}

	public void fillProductsSearch() {
		if (!isAdmin()) {
			this.products = this.olympusRestClient
					.getProductsByName(this.search.getSearchString());
		} else {
			this.redirectToAdmin();
		}
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

	@Override
	public void onLoad() {
		this.fillProductsSearch();
	}

	@Override
	public void onLoad(String type) {
		this.fillProducts(type);
	}
}
