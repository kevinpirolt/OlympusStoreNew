package pkgPucher;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;

import pkgData.Database;

@ManagedBean
@SessionScoped
/**
 * Bean for contentLeft.xhtml (in template folder)
 */
public class signin implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{database}")
	private Database database = null;
	
	private String name = "";
	private String address = "";
	private String picture = "";
	private String birthdate = "";
	private String email = "";
	private String password = "";

	public signin() {
	}

	//Getter & Setter
	
	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	//Getter & Setter

	
	
	public String createUser() {
		
		System.out.println("in createUser");
		
		boolean dateok = false;
		boolean emailok = false;
		
		emailok = this.getEmail().matches("[A-Z0-9._%+-][A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{3}");
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/DD");
		try {
			sdf.parse(this.getBirthdate());
			dateok = true;
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		if(emailok && dateok) {
			try {
				
				this.database.createUser(this.getName(), this.getAddress(), this.getPicture(), this.getBirthdate(), this.getEmail(), this.getPassword());
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}	
		
		return "signin.jsf";
		
	}

	
}