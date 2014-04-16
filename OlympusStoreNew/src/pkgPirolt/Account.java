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

	
	public String update() {
		
		System.out.println("------>new User: " + this.contentLeft.getUser());
		try {
			this.database.updateUser(this.contentLeft.getUser());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "account";
	}
	
	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
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
