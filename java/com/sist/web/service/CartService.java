package com.sist.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.web.dao.CartDao;
import com.sist.web.model.Cart;

@Service("cartService")
public class CartService {

    @Autowired
    private CartDao cartDao;

    public List<Cart> getCartList(String userId) {
        return cartDao.selectCartList(userId);
    }

    public void insertCart(Cart cart) {
        cartDao.insertCart(cart);
    }


    public int updateCart(Cart cart) {
        return cartDao.updateCart(cart);
    }

    public int removeCart(long cartId) {
        return cartDao.deleteCart(cartId);
    }
    public List<Cart> getSelectedCartItems(List<Long> cartIds) {
        return cartDao.getSelectedCartItems(cartIds);
    }
    public  int deleteCart(Long cartId)
    {
    	return cartDao.deleteCart(cartId);
    }
}

