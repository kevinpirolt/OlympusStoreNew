package pkgOlympusRestClient;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import pkgUtil.Product;
import pkgUtil.ProductList;
import pkgUtil.exception.ParameterNotFoundException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

@ManagedBean(name="olympusRestClient")
@SessionScoped
public class OlympusRestClient implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private WebResource service = null;

	public OlympusRestClient() throws ParameterNotFoundException {
		initComponents();
	}
	//*********************************************************************************************
	public ArrayList<Product> getAllProducts() {
		ProductList products = null;
		products = service.path("olympus").path("olympusrest").path("getallbooks")
				.accept(MediaType.TEXT_XML).get(ProductList.class);
		return products.getProducts();
	}
	
	public ArrayList<Product> getProductsByName(String productName) {
		ProductList products = null;
		products = service.path("olympus").path("olympusrest").path("getproductsbyname/" + productName)
				.accept(MediaType.TEXT_XML).get(ProductList.class);
		return products.getProducts();
	}
	
	public ArrayList<Product> getLatestProducts(String productType) {
		ProductList products = null;
		products = service.path("olympus").path("olympusrest").path("getlatestproducts/" + productType)
				.accept(MediaType.TEXT_XML).get(ProductList.class);
		printProducts(products);
		return products.getProducts();
	}
	
	private void printProducts(ProductList products) {
		for(Product p : products.getProducts()) {
			System.out.println("New Product-----------------");
			System.out.println(p.getId());
			System.out.println(p.getName());
			System.out.println(p.getPrice());
			System.out.println(p.getQuantity());
			System.out.println(p.getInterpret());
			System.out.println(p.getGenre());
			System.out.println(p.getType());
			System.out.println(p.getDescription());
			System.out.println(p.getReleaseDate());
			System.out.println(p.getImage());
			System.out.println("------------------------------");
		}
	}
	//TODO Write Function to insertProduct
	//*********************************************************************************************
	private void initComponents() throws ParameterNotFoundException {
		ServletContext scon = (ServletContext) FacesContext
			    .getCurrentInstance().getExternalContext().getContext();
		String servUri = scon.getInitParameter("restServer");
		if(servUri == null)
			throw new ParameterNotFoundException("Could not find Parameter restServ.\n"
					+ "Check your context.xml");
		DefaultClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		service = client.resource(getBaseURI(servUri));
	}
	
	private URI getBaseURI(String servUri) {
		return UriBuilder.fromUri(servUri).build();
	}
	
	public WebResource getService() {
		return service;
	}
	
	public void setService(WebResource service) {
		this.service = service;
	}
}
