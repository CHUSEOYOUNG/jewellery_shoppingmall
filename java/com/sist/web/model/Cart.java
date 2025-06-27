package com.sist.web.model;

import java.io.Serializable;

public class Cart implements Serializable {
	 
    /**
	 * 
	 */
	private static final long serialVersionUID = 5414078327183875075L;
	private long cartId;
    private String userId;
    private String productId;
    private int quantity;
    private String regDate;

    // product join info
    private String productName;
    private String productImage;
    private int productPrice;

    
    public Cart()
    {
    	cartId = 0;
        userId = "";
        productId = "";
        quantity = 0;
        regDate = "";

        // product join info
        productName = "";
        productImage = "";
        productPrice = 0;
    }


	public long getCartId() {
		return cartId;
	}


	public void setCartId(long cartId) {
		this.cartId = cartId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getRegDate() {
		return regDate;
	}


	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getProductImage() {
		return productImage;
	}


	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}


	public int getProductPrice() {
		return productPrice;
	}


	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
    
    
}

