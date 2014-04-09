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
	
	@ManagedProperty(value="#{listProductBean}")
	private ListProductBean listProductBean;

	public Product getCurrentProduct() {
		return currentProduct;
	}

	public ListProductBean getCd() {
		return listProductBean;
	}

	public void setCurrentProduct(Product currentProduct) {
		this.currentProduct = currentProduct;
	}

	public void setCd(ListProductBean cd) {
		this.listProductBean = cd;
	}
	
	public void selectProduct() {
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		int idp = Integer.parseInt(req.getParameter("id"));
		for(Product p : listProductBean.getProducts()) {
			if(p.getId() == idp)
				this.currentProduct = p;
		}
	}
}
