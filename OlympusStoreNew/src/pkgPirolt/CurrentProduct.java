package pkgPirolt;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import pkgUtil.Product;

@ManagedBean(name="currentProduct")
@SessionScoped
public class CurrentProduct {
	private Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public String chooseCurrentProduct() {
		return "";
	}
}
