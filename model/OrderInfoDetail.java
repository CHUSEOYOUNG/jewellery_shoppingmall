package com.sist.web.model;

import java.io.Serializable;

public class OrderInfoDetail  implements Serializable{
	
	
	private static final long serialVersionUID = -6604197942030648737L;
	private long orderDetailId;
    private String orderId;
    private String productId;
    private String productName;
    private int productPrice;
    private int quantity;
    private int totalPrice;
    private String productImage;

    
    public OrderInfoDetail()
    {
    	orderDetailId = 0;
    	orderId = "";
    	productId = "";
    	productName = "";
    	productPrice = 0;
    	quantity = 0;
    	totalPrice = 0;
    	productImage = "";
    }


    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
	public long getOrderDetailId() {
		return orderDetailId;
	}


	public void setOrderDetailId(long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public int getProductPrice() {
		return productPrice;
	}


	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public int getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
    
    
}
