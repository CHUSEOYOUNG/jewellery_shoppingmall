package com.sist.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sist.web.model.Cart;
import com.sist.web.service.CartService;
import com.sist.web.util.CookieUtil;
import com.sist.web.util.HttpUtil;

@Controller("cartController")
public class CartController {

    @Autowired 
    private CartService cartService;

    @Value("#{env['auth.cookie.name']}")  // ex: S_USER_ID
    private String AUTH_COOKIE_NAME;

    @GetMapping("/product/cart")
    public String cartList(Model model, HttpServletRequest request) {
        String userId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);

        if (userId == null || userId.isEmpty()) {
            return "redirect:/user/login";
        }

        List<Cart> cartList = cartService.getCartList(userId);
        model.addAttribute("cartList", cartList);

        return "/product/cart";
    }

    @PostMapping("/product/cartInsertProc")
    public String cartInsertProc(HttpServletRequest request) {
        String userId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
        String productId = HttpUtil.get(request, "productId", "");
        int quantity = HttpUtil.get(request, "quantity", 1);

        if (userId != null && !userId.isEmpty() && !productId.isEmpty()) {
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setQuantity(quantity);
            cartService.insertCart(cart);
        }

        return "redirect:/product/cart";
    }

    @PostMapping("/product/cartUpdate")
    public String cartUpdate(HttpServletRequest request) {
        long cartId = HttpUtil.get(request, "cartId", 0L);
        int quantity = HttpUtil.get(request, "quantity", 1);

        if (cartId > 0 && quantity > 0) {
            Cart cart = new Cart();
            cart.setCartId(cartId);
            cart.setQuantity(quantity);
            cartService.updateCart(cart);
        }

        return "redirect:/product/cart";
    }

    @PostMapping("/product/cartDelete")
    public String cartDelete(HttpServletRequest request) {
        long cartId = HttpUtil.get(request, "cartId", 0L);
        cartService.deleteCart(cartId);
        return "redirect:/product/cart";
    }

    @PostMapping("/product/cartPayReady")
    public String cartPayReady(HttpServletRequest request, Model model) {
        String[] ids = request.getParameterValues("cartIds");
        if (ids == null || ids.length == 0) {
            return "redirect:/product/cart";
        }

        List<Long> cartIds = new ArrayList<>();
        for (String id : ids) {
            try {
                cartIds.add(Long.parseLong(id));
            } catch (NumberFormatException e) {
                // 무시
            }
        }

        List<Cart> selectedItems = cartService.getSelectedCartItems(cartIds);
        model.addAttribute("selectedItems", selectedItems);
        return "/product/confirmPay";
    }
}
