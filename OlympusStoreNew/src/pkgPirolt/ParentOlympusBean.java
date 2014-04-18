package pkgPirolt;

import java.io.IOException;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.apache.catalina.deploy.ErrorPage;

import pkgPucher.contentLeft;

public abstract class ParentOlympusBean {
	
	@ManagedProperty(value="#{contentLeft}")
	protected contentLeft contentLeft;
	
	public abstract void onLoad();
	public abstract void onLoad(String type);
	
	public boolean isAdmin() {
		boolean isAdmin = false;
		if (this.isLoggedIn() && this.contentLeft.getUser().getName().equals("admin"))
			isAdmin = true;
		return isAdmin;
	}
	
	public boolean isLoggedIn() {
		boolean isLoggedIn = false;
		if (this.contentLeft.getUser() != null)
			isLoggedIn = true;
		return isLoggedIn;
	}
	
	public void redirectToAdmin() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("admin.jsf");
		} catch (IOException e) {
			//TODO redirect to error side
		}
	}
	
	public void redirectToIndex() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("index.jsf");
		} catch (IOException e) {
			// TODO redirect to error side
		}
	}
	
	public void reidrectToLogin() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("login.jsf");
		} catch (IOException e) {
			// TODO redirect to error side
		}
	}
	
	public contentLeft getContentLeft() {
		return contentLeft;
	}
	public void setContentLeft(contentLeft contentLeft) {
		this.contentLeft = contentLeft;
	}
}
