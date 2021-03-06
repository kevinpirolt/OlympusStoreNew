package pkgPirolt;

import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import pkgData.Database;

@ManagedBean(name="account")
@SessionScoped
public class Account extends ParentOlympusBean{

	@ManagedProperty(value="#{database}")
	private Database database;
	private String message;

	public String update() {
		
		System.out.println("------>new User: " + this.contentLeft.getUser());
		try {
			this.database.updateUser(this.contentLeft.getUser());
			this.setMessage("account edited!");
		} catch (SQLException e) {
			this.setMessage(e.getMessage());
		}
		return "account";
	}
	
	public String delete() {
		System.out.println();
		try {
			this.database.deleteUser(this.contentLeft.getUser());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.contentLeft.logOut();
	}
	
	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public void onLoad() {
		if(this.isAdmin())
			this.redirectToAdmin();
		if(!this.isLoggedIn())
			this.reidrectToLogin();
	}

	@Override
	public void onLoad(String type) {
		// TODO Auto-generated method stub
		
	}
	
}
