package pkgPirolt;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.sun.jersey.api.core.HttpRequestContext;

import pkgUtil.Product;

@ManagedBean(name="productCd")
@SessionScoped
public class ProductCd {
	
	private Product currentProduct;
	
	@ManagedProperty(value="#{cd}")
	private Cd cd;

	public Product getCurrentProduct() {
		return currentProduct;
	}

	public Cd getCd() {
		return cd;
	}

	public void setCurrentProduct(Product currentProduct) {
		this.currentProduct = currentProduct;
	}

	public void setCd(Cd cd) {
		this.cd = cd;
	}
	
	public void selectProduct() {
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		int idp = Integer.parseInt(req.getParameter("id"));
		for(Product p : cd.getProducts()) {
			if(p.getId() == idp)
				this.currentProduct = p;
		}
	}
}
