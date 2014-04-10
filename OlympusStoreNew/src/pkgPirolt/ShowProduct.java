package pkgPirolt;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import pkgUtil.Product;

@ManagedBean(name="showProduct")
@SessionScoped
public class ShowProduct implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Product currentProduct;

	@ManagedProperty(value="#{listProductBean}")
	private ListProductBean listProductBean;
	
	@ManagedProperty(value="#{cart}")
	private Cart cart;

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getCurrentProduct() {
		return currentProduct;
	}

	public void setCurrentProduct(Product currentProduct) {
		this.currentProduct = currentProduct;
	}
	
	public ListProductBean getListProductBean() {
		return listProductBean;
	}

	public void setListProductBean(ListProductBean listProductBean) {
		this.listProductBean = listProductBean;
	}
	
	public void selectProduct() {
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		int idp = Integer.parseInt(req.getParameter("id"));
		for(Product p : listProductBean.getProducts()) {
			if(p.getId() == idp)
				this.currentProduct = p;
		}
	}
	
	public String addItem() {
		cart.initCart();
		System.out.println("------> After initCart() why");
		String ret = null;
		if(this.cart.getContentLeft() != null)
			ret = cart.addItem(this.currentProduct);
		else
			ret = "login";
		return ret;
	}
}
