package com.sist.web.dao;

import java.util.List;

import com.sist.web.model.Cart;

public interface CartDao {
    public List<Cart> selectCartList(String userId);
    public int insertCart(Cart cart);
    public int updateCart(Cart cart);
    public int deleteCart(long cartId);
    List<Cart> getSelectedCartItems(List<Long> cartIds);
    int deleteCart(Long cartId);}