package com.sist.web.dao;

import java.util.List;

import com.sist.web.model.Cart;
import com.sist.web.model.OrderInfo;
import com.sist.web.model.OrderInfoDetail;

public interface OrderDao {
	public int insertOrderInfo(OrderInfo order);
    public int insertOrderDetail(OrderInfoDetail detail);
    
    public void processOrder(OrderInfo orderInfo, List<OrderInfoDetail> detailList);
    

    public List<OrderInfo> selectOrderList(String userId);
    public List<OrderInfoDetail> selectOrderDetails(String orderId);
    List<Cart> getSelectedCartItems(List<Long> cartIds);
    void deleteCartById(Long cartId);
}