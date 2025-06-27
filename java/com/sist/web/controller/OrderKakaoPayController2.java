package com.sist.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.sist.common.util.StringUtil;
import com.sist.web.model.Cart;
import com.sist.web.model.KakaoPayApproveRequest;
import com.sist.web.model.KakaoPayApproveResponse;
import com.sist.web.model.KakaoPayReadyRequest;
import com.sist.web.model.KakaoPayReadyResponse;
import com.sist.web.model.OrderInfo;
import com.sist.web.model.OrderInfoDetail;
import com.sist.web.model.Product;
import com.sist.web.model.Response;
import com.sist.web.service.KakaoPayService;
import com.sist.web.service.OrderService;
import com.sist.web.service.ProductService;
import com.sist.web.util.CookieUtil;
import com.sist.web.util.HttpUtil;
import com.sist.web.util.SessionUtil;

@Controller("orderKakaoPayController2")
public class OrderKakaoPayController2 {

    private static Logger logger = LoggerFactory.getLogger(OrderKakaoPayController2.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private KakaoPayService kakaoPayService;

    @Value("#{env['auth.cookie.name']}")
    private String AUTH_COOKIE_NAME;

    @Value("#{env['kakaopay.tid.session.name']}")
    private String KAKAOPAY_TID_SESSION_NAME;

    @Value("#{env['kakaopay.orderid.session.name']}")
    private String KAKAOPAY_ORDERID_SESSION_NAME;

    @RequestMapping(value = "/kakao/pay2", method = RequestMethod.GET)
    public String pay(Model model, HttpServletRequest request, HttpServletResponse response) 
    {
        List<Product> productList = productService.productList(new Product());
        model.addAttribute("productList", productList);
        return "/kakao/pay2";
    }

    @RequestMapping(value = "/kakao/readyAjax2", method = RequestMethod.POST)
    @ResponseBody
    public Response<JsonObject> readyAjax(HttpServletRequest request, HttpServletResponse response) 
    {
        Response<JsonObject> res = new Response<>();

        String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
        long productId = HttpUtil.get(request, "productId", 0L);
        Product product = productService.productSelect(productId);

        if (product == null) {
            res.setResponse(-1, "상품을 찾을 수 없습니다.");
            return res;
        }

        String orderId = StringUtil.uniqueValue();

        KakaoPayReadyRequest kakaoReq = new KakaoPayReadyRequest();
        kakaoReq.setPartner_order_id(orderId);
        kakaoReq.setPartner_user_id(cookieUserId);
        kakaoReq.setItem_name(product.getProductName());
        kakaoReq.setItem_code(String.valueOf(product.getProductId()));
        kakaoReq.setQuantity(1);
        kakaoReq.setTotal_amount(product.getProductPrice());
        kakaoReq.setTax_free_amount(0);

        KakaoPayReadyResponse kakaoRes = kakaoPayService.ready(kakaoReq);

        if (kakaoRes != null) 
        {
            HttpSession session = request.getSession(true);
            SessionUtil.setSession(session, KAKAOPAY_TID_SESSION_NAME, kakaoRes.getTid());
            SessionUtil.setSession(session, KAKAOPAY_ORDERID_SESSION_NAME, orderId);
            SessionUtil.setSession(session, "KAKAO_PRODUCT_ID", product.getProductId());

            JsonObject json = new JsonObject();
            json.addProperty("next_redirect_pc_url", kakaoRes.getNext_redirect_pc_url());
            json.addProperty("created_at", kakaoRes.getCreated_at());

            res.setResponse(0, "success", json);
        } else 
        {
            res.setResponse(-1, "카카오페이 결제 준비 중 오류가 발생하였습니다.");
        }

        return res;
    }

    @RequestMapping(value = "/order/kakaoPay/success2", method = RequestMethod.GET)
    public String success(Model model, HttpServletRequest request, HttpServletResponse response) {
        String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
        String pg_token = HttpUtil.get(request, "pg_token");

        HttpSession session = request.getSession(false);
        String tid = (String) SessionUtil.getSession(session, KAKAOPAY_TID_SESSION_NAME);
        String orderId = (String) SessionUtil.getSession(session, KAKAOPAY_ORDERID_SESSION_NAME);
        Long productId = (Long) SessionUtil.getSession(session, "KAKAO_PRODUCT_ID");

        @SuppressWarnings("unchecked")
        List<Long> cartIds = (List<Long>) SessionUtil.getSession(session, "KAKAO_CART_IDS");

        logger.info("pg_token : [" + pg_token + "]");
        logger.info("tid : [" + tid + "]");
        logger.info("orderId : [" + orderId + "]");

        if (!StringUtil.isEmpty(tid) && !StringUtil.isEmpty(pg_token)) {
            KakaoPayApproveRequest approveReq = new KakaoPayApproveRequest();
            approveReq.setTid(tid);
            approveReq.setPartner_order_id(orderId);
            approveReq.setPartner_user_id(cookieUserId);
            approveReq.setPg_token(pg_token);

            KakaoPayApproveResponse approveRes = kakaoPayService.approve(approveReq);

            if (approveRes != null) {
                logger.info("결제 승인 응답: {}", approveRes);

                if (approveRes.getError_code() == 0) {
                    try {
                        // === 단건 결제 처리 ===
                        if (cartIds == null || cartIds.isEmpty()) {
                            Product product = productService.productSelect(productId);

                            OrderInfo order = new OrderInfo();
                            order.setOrderId(orderId);
                            order.setUserId(cookieUserId);
                            order.setTotalPrice(product.getProductPrice());
                            order.setPaymentMethod("KAKAOPAY");

                            OrderInfoDetail detail = new OrderInfoDetail();
                            detail.setOrderId(orderId);
                            detail.setProductId(String.valueOf(product.getProductId()));
                            detail.setProductName(product.getProductName());
                            detail.setProductPrice(product.getProductPrice());
                            detail.setQuantity(1);
                            detail.setTotalPrice(product.getProductPrice());

                            orderService.insertOrderInfo(order);
                            orderService.insertOrderDetail(detail);
                        }
                        // === 장바구니 결제 처리 ===
                        else {
                            List<Cart> cartItems = orderService.getSelectedCartItems(cartIds);

                            int totalPrice = cartItems.stream()
                                .mapToInt(item -> item.getProductPrice() * item.getQuantity())
                                .sum();

                            OrderInfo order = new OrderInfo();
                            order.setOrderId(orderId);
                            order.setUserId(cookieUserId);
                            order.setTotalPrice(totalPrice);
                            order.setPaymentMethod("KAKAOPAY");

                            orderService.insertOrderInfo(order);

                            for (Cart item : cartItems) {
                                OrderInfoDetail detail = new OrderInfoDetail();
                                detail.setOrderId(orderId);
                                detail.setProductId(item.getProductId());
                                detail.setProductName(item.getProductName());
                                detail.setProductPrice(item.getProductPrice());
                                detail.setQuantity(item.getQuantity());
                                detail.setTotalPrice(item.getProductPrice() * item.getQuantity());
                                orderService.insertOrderDetail(detail);
                            }

                            // 장바구니 삭제 처리
                            for (Long cartId : cartIds) {
                                orderService.deleteCartById(cartId);
                            }
                        }

                        model.addAttribute("code", 0);
                        model.addAttribute("msg", "결제가 완료되었습니다.");
                    } catch (Exception e) {
                        logger.error("주문 저장 중 오류", e);
                        model.addAttribute("code", -2);
                        model.addAttribute("msg", "결제는 성공했지만 주문 저장 중 문제가 발생했습니다.");
                    }
                } else {
                    model.addAttribute("code", -1);
                    model.addAttribute("msg",
                        !StringUtil.isEmpty(approveRes.getError_message())
                            ? approveRes.getError_message()
                            : "카카오페이 결제 승인 중 오류가 발생했습니다.");
                }
            } else {
                model.addAttribute("code", -4);
                model.addAttribute("msg", "카카오페이 승인 응답이 null입니다.");
            }

            // 세션 정리
            SessionUtil.removeSession(session, KAKAOPAY_TID_SESSION_NAME);
            SessionUtil.removeSession(session, KAKAOPAY_ORDERID_SESSION_NAME);
            SessionUtil.removeSession(session, "KAKAO_PRODUCT_ID");
            SessionUtil.removeSession(session, "KAKAO_CART_IDS");

        } else {
            model.addAttribute("code", -3);
            model.addAttribute("msg", "세션 정보가 유효하지 않습니다.");
        }

        return "/kakao/result2";
    }

    @RequestMapping(value = "/order/orderList", method = RequestMethod.GET)
    public String orderList(Model model, HttpServletRequest request) {
        String userId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
        List<OrderInfo> orderList = orderService.getOrderListByUser(userId);
        model.addAttribute("orderList", orderList);
        return "/order/orderList";
    }

    //장바구니 결제 추가
    @RequestMapping(value = "/kakao/readyCartAjax", method = RequestMethod.POST)
    @ResponseBody
    public Response<JsonObject> readyCartAjax(HttpServletRequest request, HttpServletResponse response) {
        Response<JsonObject> res = new Response<>();
        String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);

        String[] cartIds = request.getParameterValues("cartIds");
        if (cartIds == null || cartIds.length == 0) {
            res.setResponse(-1, "선택된 장바구니 항목이 없습니다.");
            return res;
        }

        List<Long> cartIdList = new ArrayList<>();
        for (String id : cartIds) {
            cartIdList.add(Long.parseLong(id));
        }

        List<Cart> cartItems = orderService.getSelectedCartItems(cartIdList); // 장바구니 기반 상품 목록 조회

        if (cartItems == null || cartItems.isEmpty()) {
            res.setResponse(-2, "선택한 장바구니 항목이 유효하지 않습니다.");
            return res;
        }

        int totalAmount = cartItems.stream()
            .mapToInt(item -> item.getProductPrice() * item.getQuantity())
            .sum();

        String itemName = cartItems.size() > 1
            ? cartItems.get(0).getProductName() + " 외 " + (cartItems.size() - 1) + "건"
            : cartItems.get(0).getProductName();

        String orderId = StringUtil.uniqueValue();

        KakaoPayReadyRequest kakaoReq = new KakaoPayReadyRequest();
        kakaoReq.setPartner_order_id(orderId);
        kakaoReq.setPartner_user_id(cookieUserId);
        kakaoReq.setItem_name(itemName);
        kakaoReq.setQuantity(1);
        kakaoReq.setTotal_amount(totalAmount);
        kakaoReq.setTax_free_amount(0);

        KakaoPayReadyResponse kakaoRes = kakaoPayService.ready(kakaoReq);

        if (kakaoRes != null) {
            HttpSession session = request.getSession(true);
            SessionUtil.setSession(session, KAKAOPAY_TID_SESSION_NAME, kakaoRes.getTid());
            SessionUtil.setSession(session, KAKAOPAY_ORDERID_SESSION_NAME, orderId);
            SessionUtil.setSession(session, "KAKAO_CART_IDS", cartIdList);

            JsonObject json = new JsonObject();
            json.addProperty("next_redirect_pc_url", kakaoRes.getNext_redirect_pc_url());
            json.addProperty("created_at", kakaoRes.getCreated_at());
            res.setResponse(0, "success", json);
        } else {
            res.setResponse(-3, "카카오페이 결제 준비 중 오류");
        }

        return res;
    }

    
}
