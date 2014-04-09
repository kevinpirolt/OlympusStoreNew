package pkgPirolt;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import pkgUtil.Product;

@ManagedBean(name="currentProduct")
@SessionScoped
public class CurrentProduct implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
