package com.sist.web.model;

import java.io.Serializable;
import java.util.List;

public class OrderInfo implements Serializable{
	

	private static final long serialVersionUID = -2534645903222983468L;
	private String orderId;
    private String userId;
    private int totalPrice;
    private String paymentMethod;
    private String orderDate;
    private List<OrderInfoDetail> detailList;

    public OrderInfo()
    {
    	orderId = "";
    	userId = "";
    	totalPrice = 0;
    	paymentMethod = "";
    	orderDate = "";
    	detailList = null;
    }
    public List<OrderInfoDetail> getDetailList() {
        return detailList;
    }
    public void setDetailList(List<OrderInfoDetail> detailList) {
        this.detailList = detailList;
    }

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
    
    
}
