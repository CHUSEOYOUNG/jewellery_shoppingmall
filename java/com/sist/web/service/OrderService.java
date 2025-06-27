package com.sist.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sist.web.dao.CartDao;
import com.sist.web.dao.OrderDao;
import com.sist.web.model.Cart;
import com.sist.web.model.OrderInfo;
import com.sist.web.model.OrderInfoDetail;
@Service("orderService")

public class OrderService {
	
	private static Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private CartDao cartDao;
	
	public int insertOrderInfo(OrderInfo orderInfo) throws Exception {
	    return orderDao.insertOrderInfo(orderInfo);
	}

	public int insertOrderDetail(OrderInfoDetail orderDetail) throws Exception {
	    return orderDao.insertOrderDetail(orderDetail);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void processOrder(OrderInfo orderInfo, List<OrderInfoDetail> detailList) throws Exception {
	    orderDao.insertOrderInfo(orderInfo);
	    for (OrderInfoDetail detail : detailList) {
	        orderDao.insertOrderDetail(detail);
	    }
	}
	
	public List<OrderInfo> getOrderListByUser(String userId) {
	    List<OrderInfo> orderList = orderDao.selectOrderList(userId);
	    for (OrderInfo order : orderList) {
	        List<OrderInfoDetail> details = orderDao.selectOrderDetails(order.getOrderId());
	        order.setDetailList(details);
	    }
	    return orderList;
	}
    
    public List<Cart> getSelectedCartItems(List<Long> cartIds) {
        return cartDao.getSelectedCartItems(cartIds);
    }

    
    public void deleteCartById(Long cartId) {
    	cartDao.deleteCart(cartId);
    }

}
