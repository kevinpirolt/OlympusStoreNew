package pkgData;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import pkgUtil.CartItem;

@ManagedBean(name="database")
@SessionScoped

public class Database implements Serializable
{
	private static final long serialVersionUID = -2948438178157372271L;
	private DataSource ds=null;
	private java.sql.Connection conn=null;


	public Database()
	{
		Context ctx;
		try {
			ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/resStore");
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

	private void Connect() throws SQLException
	{
		conn=ds.getConnection();
	}

	private void CloseConnection() throws SQLException
	{
		conn.close();
	}

	public User getUser(String name)
	{
		User u=null;
		try
		{
			this.Connect();
			PreparedStatement stmt=null;
			String select ="select adress,url,to_Char(birthdate, 'YYYY/MM/DD') birthdate,email,passwort,discount, u_id from users where username=?";
			stmt = conn.prepareStatement(select);
			
			stmt.setString(1, name);
			
			ResultSet rs=stmt.executeQuery();
			
			
			if(rs.next())
			{
				System.out.println("<<<<<<<<<<<<<Jez nur getString: " + rs.getString("u_id"));
				u= new User(Integer.parseInt(rs.getString("u_id")), name, rs.getString("adress"),rs.getString("url"),
						rs.getString("birthdate"),rs.getString("email"),rs.getString("passwort"),rs.getInt("discount"));
			}
			
			System.out.println("-------->USER FROM DATABASE: " + u);
			this.CloseConnection();
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return u;
	}

	public boolean isPasswordCorrect(String name, String passwd) throws SQLException {
		this.Connect();
		String selectCount = "SELECT count(*) as cnt FROM users WHERE username=? AND passwort=? and deleted = 'false'";
		PreparedStatement stmt = conn.prepareStatement(selectCount,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, name);
		stmt.setString(2, passwd);
		ResultSet rs=stmt.executeQuery();
		rs.next();
		boolean ret=false;
		if(rs.getInt("cnt")>0)
			ret=true;
		this.CloseConnection();
		return ret;
	}

	public void createUser(String username, String adress, String url,String birthdate,String email,String pw ) throws SQLException
	{
		this.Connect();
		PreparedStatement stmt=conn.prepareStatement("insert into users values(seq_users.NEXTVAL,?,?,?,to_date(?,'DD.MM.YYYY'),?,?,?,?)");

		stmt.setString(1, username);
		stmt.setString(2, adress);
		stmt.setString(3, url);
		stmt.setString(4, birthdate);
		stmt.setString(5, email);
		stmt.setString(6, pw);
		stmt.setInt(7, 	0);
		stmt.setString(8, "false");


		stmt.executeUpdate();
		
		
		this.CloseConnection();
	}

	public void updateUser(User toUpdate) throws SQLException {
		this.Connect();
			String upd = "update users set username=?, adress=?, birthdate=to_date(?, 'YYYY/MM/DD'), "
					+ "email=?, passwort=? where u_id = ?";
			PreparedStatement prs = this.conn.prepareStatement(upd);
			prs.setString(1, toUpdate.getName());
			prs.setString(2, toUpdate.getAddress());
			prs.setString(3, toUpdate.getBirthdate());
			prs.setString(4, toUpdate.getEmail());
			prs.setString(5, toUpdate.getPassword());
			prs.setInt(6, toUpdate.getId());
			
			if(prs.executeUpdate() <= 0)
				throw new SQLException("User was not updated");
		this.CloseConnection();
	}
	
	public void insertCartAndCartItems(ArrayList<CartItem> items, User insertUser) throws SQLException {
		this.Connect();
		int newOrderId = this.getLatestOrderId();
		if(newOrderId < 0)
			throw new SQLException("OrderId error occured");
		this.insertOrder(newOrderId, insertUser.getId());
		this.insertOrderItems(newOrderId, items);
		this.CloseConnection();
	}
	
	private void insertOrderItems(int newOrderId, ArrayList<CartItem> items) throws SQLException {
		String insert = "insert into orderitem values(?,?,?,?,?)";
		PreparedStatement prs = this.conn.prepareStatement(insert);
		for(CartItem ci : items) {
			prs.setInt(1,ci.getItem().getId());
			prs.setInt(2, newOrderId);
			prs.setString(3, ci.getItem().getName());
			prs.setFloat(4, ci.getItem().getPrice());
			prs.setInt(5, ci.getQuantety());
		}
	}

	private void insertOrder(int o_id, int u_id) throws SQLException {
		String insert = "insert into orders(o_id,datum,u_id) values(?,to_date(?,'DDMMYYYY'),?)";
		String date = this.getCurrentDateAsString();
		PreparedStatement prs = this.conn.prepareStatement(insert);
		prs.setInt(1, o_id);
		prs.setString(2, date);
		prs.setInt(3, u_id);
		int inserted = prs.executeUpdate();
		if(inserted <= 0)
			throw new SQLException("Ordern could not be created");
	}
	
	private void insertOrder(int o_id, int u_id, int discount) throws SQLException {
		String insert = "insert into orders values(?,to_date(?,'DDMMYYYY'),?,?)";
		String date = this.getCurrentDateAsString();
		PreparedStatement prs = this.conn.prepareStatement(insert);
		prs.setInt(1, o_id);
		prs.setString(2, date);
		prs.setInt(3, u_id);
		prs.setInt(4, discount);
		int inserted = prs.executeUpdate();
		if(inserted <= 0)
			throw new SQLException("Ordern could not be created");
	}
	
	private String getCurrentDateAsString() {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		return sdf.format(new Date());
	}

	private int getLatestOrderId() throws SQLException {
		int nextId = -99;
		String selId = "select seq_orders.nextval from dual";
		PreparedStatement prs = this.conn.prepareStatement(selId);
		ResultSet rs = prs.executeQuery();
		if(rs.next())
			nextId = rs.getInt(1);
		return nextId;
	}

	public void deleteUser(User toDelete) throws SQLException {
		this.Connect();
		String delete = "update users set deleted = 'true' where u_id = ?";
		PreparedStatement prs = this.conn.prepareStatement(delete);
		prs.setInt(1, toDelete.getId());
		int deleted = prs.executeUpdate();
		if(deleted <= 0)
			throw new SQLException("User could not be deleted");
		this.CloseConnection();
	}

}
