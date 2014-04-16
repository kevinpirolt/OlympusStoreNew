package pkgData;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
		String selectCount = "SELECT count(*) as cnt FROM users WHERE username=? AND passwort=?";
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
		PreparedStatement stmt=conn.prepareStatement("insert into users values(seq_users.NEXTVAL,?,?,?,to_date(?,'YYYY/MM/DD'),?,?,?)");

		stmt.setString(1, username);
		stmt.setString(2, adress);
		stmt.setString(3, url);
		stmt.setString(4, birthdate);
		stmt.setString(5, email);
		stmt.setString(6, pw);
		stmt.setInt(7, 	0);


		stmt.executeUpdate();
		
		
		this.CloseConnection();
	}

	public void updateUser(User toUpdate) throws SQLException {
		this.Connect();
			String upd = "update users set username=?, adress=?, birthdate=to_date(?, 'yyyy.dd.MM'), "
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
	
	public void insertCartAndCartItems(ArrayList<CartItem> items) {
		//TODO Stiege mach was, des san so zu sagen die Bestellungen
	}

}
