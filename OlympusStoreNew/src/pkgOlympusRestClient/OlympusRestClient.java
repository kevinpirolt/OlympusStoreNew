package pkgOlympusRestClient;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import pkgUtil.Product;
import pkgUtil.ProductList;
import pkgUtil.exception.ParameterNotFoundException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class OlympusRestClient implements Serializable{
	
	private String servUri;
	private ClientConfig config = null;
	private Client client = null;
	private WebResource service = null;

	public OlympusRestClient(ServletContext context) throws ParameterNotFoundException {
		if((this.servUri = getUriFromContext(context)) == null)
			throw new ParameterNotFoundException("The Parameter[olympusRestServer] was not found\n"
					+ "Check your context.xml");
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
		return products.getProducts();
	}
	//*********************************************************************************************
	private String getUriFromContext(ServletContext context) {
		//TODO Namen des Parameters eintragen.
		return context.getInitParameter("restServer");
	}

	private void initComponents() {
		config = new DefaultClientConfig();
		client = Client.create(config);
		service = client.resource(getBaseURI());
	}
	
	private URI getBaseURI() {
		return UriBuilder.fromUri(this.servUri).build();
	}
}
