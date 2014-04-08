package pkgPirolt;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import pkgData.Database;
import pkgOlympusRestClient.OlympusRestClient;
import pkgUtil.Product;
import pkgUtil.exception.ParameterNotFoundException;

@ManagedBean
@SessionScoped
public class Cd {
	@ManagedProperty(value = "database")
	private Database database;
	
	private ArrayList<Product> products;
	
	public void fillProducts() {
		ServletContext context = (ServletContext) FacesContext
			    .getCurrentInstance().getExternalContext().getContext();
		try {
			OlympusRestClient orc = new OlympusRestClient(context);
			this.products = orc.getLatestProducts("CD");
		} catch (ParameterNotFoundException e) {
			Logger.getLogger(Cd.class.getName()).log(Level.SEVERE,"The OlympusRestClient could not find"
					+ " the restServer-parameter in your context.xml",e);
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
}
