package pkgPucher;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="errorpage")
@SessionScoped
/**
 * Bean for errorpage.xhtml
 */
public class ErrorPage implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String errormessage = "";

	public ErrorPage() {
	}

	//Getter & Setter
	
	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}
	
	//Getter & Setter
	
}
