package pkgPirolt;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import pkgUtil.Product;

@ManagedBean(name = "showProduct")
@SessionScoped
public class ShowProduct extends ParentOlympusBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Product currentProduct;
	private boolean disabled;

	@ManagedProperty(value = "#{listProductBean}")
	private ListProductBean listProductBean;

	@ManagedProperty(value = "#{cart}")
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

	public boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public void selectProduct() {
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		int idp = Integer.parseInt(req.getParameter("id"));
		for (Product p : listProductBean.getProducts()) {
			if (p.getId() == idp)
				this.currentProduct = p;
		}
	}

	public String addItem() {
		cart.initCart();
		String ret = null;
		if (this.cart.getContentLeft().getUser() != null)
			ret = cart.addItem(this.currentProduct);
		else
			ret = "login";
		return ret;
	}

	private void checkQuantity() {
		if (this.currentProduct.getQuantity() <= 0) {
			this.setDisabled(true);
		} else {
			this.setDisabled(false);
		}
	}

	@Override
	public void onLoad() {
		if (!this.isAdmin()) {
				this.selectProduct();
				this.checkQuantity();
		} 
		else
			this.redirectToAdmin();
	}

	@Override
	public void onLoad(String type) {
	}
}
