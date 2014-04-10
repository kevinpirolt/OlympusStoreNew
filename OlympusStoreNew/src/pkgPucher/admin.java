package pkgPucher;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;

import pkgData.Database;
import pkgOlympusRestClient.OlympusRestClient;
import pkgUtil.Product;

@ManagedBean
@SessionScoped
/**
 * Bean for admin.xhtml
 */
public class admin implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{olympusRestClient}")
	private OlympusRestClient olympusRestClient = null;
	
	private String name = "";
	private String price = "";
	private String interpret = "";
	private String genre = "";
	private String releasedate = "";
	private String description = "";
	private String image = "";
	private String type = "";
	private String quantity = "";
	
	private String message = "...";

	public admin() {
	}

	//Getter & Setter

	public OlympusRestClient getOlympusRestClient() {
		return olympusRestClient;
	}

	public void setOlympusRestClient(OlympusRestClient olympusRestClient) {
		this.olympusRestClient = olympusRestClient;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getInterpret() {
		return interpret;
	}

	public void setInterpret(String interpret) {
		this.interpret = interpret;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getReleasedate() {
		return releasedate;
	}

	public void setReleasedate(String releasedate) {
		this.releasedate = releasedate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	//Getter & Setter

	
	
	public String createProduct() {
		
		boolean dateok = false;
		boolean priceok = false;
		boolean quantityok = false;
		Date relDate = null;
		float floatprice = 0;
		int intquantity = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/DD");
		try {
			relDate = sdf.parse(this.getReleasedate());
			dateok = true;
		} catch (Exception e1) {
			this.setMessage(e1.getMessage());
		}
		
		try {
			floatprice = Float.parseFloat(this.getPrice());
			priceok = true;
		} catch(Exception e2) {
			this.setMessage("price is not valid!");
		}
		
		try {
			intquantity = Integer.parseInt(this.getQuantity());
			quantityok = true;
		} catch(Exception e3) {
			this.setMessage("quantity is not valid!");
		}
		
		
		this.setMessage("releasedate: " + dateok);
		
		if(dateok && priceok && quantityok) {
			try {
				//public Product(float price,String name, int quantity, Date releaseDate, String interpret, String genre, String description, String image, String type) {
				Product p = new Product(floatprice,this.getName(),intquantity,relDate,this.getInterpret(),this.getGenre(),this.getDescription(),this.getImage(),this.getType());
				this.olympusRestClient.insertNewProduct(p);
				this.setMessage("Product " + this.getName() + " created!");
				
				//-private String name = "";
				//-private String price = "";
				//-private String interpret = "";
				//-private String genre = "";
				//-private String releasedate = "";
				//-private String description = "";
				//-private String image = "";
				//-private String type = "";
				//-private String quantity = "";
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				this.setMessage(e.getMessage());
			}
			
		}	
		
		return "signin.jsf";
		
	}

	

	
}