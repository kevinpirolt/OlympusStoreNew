package pkgPirolt;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import pkgData.Database;
import pkgUtil.Order;

@ManagedBean(name="ordersPage")
@SessionScoped
public class OrdersPage extends ParentOlympusBean{
	
	@ManagedProperty(value="#{database}")
	private Database database;
	
	private ArrayList<Order> orders;

	@Override
	public void onLoad() {
		
		if(this.isAdmin())
			this.redirectToAdmin();
		else if(!this.isLoggedIn())
			this.reidrectToLogin();
		else
			this.loadOrders();
	}

	public Database getDatabase() {
		return database;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	private void loadOrders() {
		try {
			this.orders = this.database.getOrders(this.contentLeft.getUser());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onLoad(String type) {
		// TODO Auto-generated method stub
		
	}

}
