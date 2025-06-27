package com.sist.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.sist.web.model.Goods;
import com.sist.web.model.KakaoPayApproveRequest;
import com.sist.web.model.KakaoPayApproveResponse;
import com.sist.web.model.KakaoPayReadyRequest;
import com.sist.web.model.KakaoPayReadyResponse;
import com.sist.web.model.Response;
import com.sist.web.service.GoodsService;
import com.sist.web.service.KakaoPayService;
import com.sist.web.util.CookieUtil;
import com.sist.web.util.HttpUtil;
import com.sist.web.util.SessionUtil;

@Controller("orderKakaoPayController")
public class OrderKakaoPayController 
{
	private static Logger logger = LoggerFactory.getLogger(OrderKakaoPayController.class);
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private KakaoPayService kakaoPayService;
	
	@Value("#{env['auth.cookie.name']}")
	private String AUTH_COOKIE_NAME;
	
	
	@Value("#{env['kakaopay.tid.session.name']}")
	private String KAKAOPAY_TID_SESSION_NAME;

	@Value("#{env['kakaopay.orderid.session.name']}")
	private String KAKAOPAY_ORDERID_SESSION_NAME;

	
	@RequestMapping(value = "/kakao/pay", method = RequestMethod.GET)
	public String pay(Model model, HttpServletRequest request, HttpServletResponse response)
	{	
		List<Goods> goodsList = goodsService.goodsList();
		
		model.addAttribute("goodsList", goodsList);
		
		return "/kakao/pay";
	}
	
	@RequestMapping(value = "/kakao/readyAjax", method = RequestMethod.POST)
	@ResponseBody
	public Response<JsonObject> readyAjax(HttpServletRequest request, HttpServletResponse repsonse)
	{
		Response<JsonObject> res = new Response<JsonObject>();
		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
		
		String goodsCode = HttpUtil.get(request, "goodsCode", ""); //상품코드
		Goods goods = goodsService.getGoods(goodsCode);
		
		if(goods == null)
		{
			res.setResponse(-1, "등록된 상품이 없습니다.");
			return res;
		}
		String userId = cookieUserId;
		String orderId = StringUtil.uniqueValue();	//추후 주문번호로 대체
		
		KakaoPayReadyRequest kakaoPayReadyRequest = new KakaoPayReadyRequest();
		
		kakaoPayReadyRequest.setPartner_order_id(orderId);			//(필수) 주문번호 최대 100자
		kakaoPayReadyRequest.setPartner_user_id(userId);			//(필수) 회원아이디 최대 100자
		kakaoPayReadyRequest.setItem_name(goods.getGoodsName());	//(필수) 상품명
		kakaoPayReadyRequest.setItem_code(goods.getGoodsCode());	//상품코드
		kakaoPayReadyRequest.setQuantity(1); 						//(필수) 상품수량
		kakaoPayReadyRequest.setTotal_amount(goods.getGoodsPrice());//(필수) 상품총액	
		kakaoPayReadyRequest.setTax_free_amount(0);					//(필수) 상품 비과세 금액
		
		//카카오페이 연동 시작
		KakaoPayReadyResponse kakaoPayReadyResponse = kakaoPayService.ready(kakaoPayReadyRequest);
		
		
		//response 세팅하고 리턴
		if(kakaoPayReadyResponse != null)
		{
			//카카오페이 트랜젝션 아이디 세션 저장
			HttpSession session = request.getSession(true);
			SessionUtil.setSession(session, KAKAOPAY_TID_SESSION_NAME, kakaoPayReadyResponse.getTid());
			SessionUtil.setSession(session, KAKAOPAY_ORDERID_SESSION_NAME, orderId);
			
			JsonObject json = new JsonObject();
			
			//요청한 클라이언트가 모바일 앱일 경우 카카오톡 결제 페이지  Redirect URL 
			json.addProperty("next_redirect_app_url", kakaoPayReadyResponse.getNext_redirect_app_url());
			
			//요청한 클라이언트가 모바일 웹일 경우 카카오톡 결제 페이지 Redirect URL
			json.addProperty("next_redirect_mobile_url", kakaoPayReadyResponse.getNext_redirect_mobile_url());

			//요청한 클라이언트가  pc 웹일 경우 카카오톡으로 결제 요청 메세지 (TMS)를 보내기 위한 사용자 정보 입력화면  Redirect URL
			json.addProperty("next_redirect_pc_url", kakaoPayReadyResponse.getNext_redirect_pc_url());

			
			//카카오페이 결제 화면으로 이동하는 Android 앱 스킴(Scheme) - 내부 서비스용
			json.addProperty("android_app_scheme", kakaoPayReadyResponse.getAndroid_app_scheme());

			//카카오페이 결제 화면으로 이동하는 iOS 앱 스킴 - 내부 서비스용
			json.addProperty("ios_app_scheme", kakaoPayReadyResponse.getIos_app_scheme());

			//결제 준비 요청 시간(날짜 포맷 yyyy-MM-dd 'T' HH:mm:ss)
			json.addProperty("created_at", kakaoPayReadyResponse.getCreated_at());
			
			res.setResponse(0, "success", json);
		}
		else
		{
			res.setResponse(-1, "카카오페이 결제 준비 중 오류가 발생하였습니다.");
		}
		
		return res;
		
	}
	
	//카카오페이 결제 승인요청
	@RequestMapping(value = "/order/kakaoPay/success", method = RequestMethod.GET)
	public String success(Model model, HttpServletRequest request, HttpServletResponse response)
	{
		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
		
		String pg_token = HttpUtil.get(request, "pg_token");
		String tid = null;
		String orderId = null;
		
		//false : 세션이 존재하지 않을 경우, 새로운 세션을 생성하지 않고 null 반환함.
		//기존 세션 존재시 그 세션 반환
		HttpSession session = request.getSession(false);
		if(session != null)
		{
			tid = (String)SessionUtil.getSession(session, KAKAOPAY_TID_SESSION_NAME);
			orderId = (String)SessionUtil.getSession(session, KAKAOPAY_ORDERID_SESSION_NAME);
		}
		
		logger.info("pg_token : [" + pg_token + "]");
		logger.info("tid : [" + tid + "]");
		logger.info("orderId : [" + orderId + "]");
		
		if(!StringUtil.isEmpty(pg_token) && !StringUtil.isEmpty(tid))
		{
			KakaoPayApproveRequest kakaoPayApproveRequest = new KakaoPayApproveRequest();
			
			String userId = cookieUserId;
			
			kakaoPayApproveRequest.setTid(tid);
			kakaoPayApproveRequest.setPartner_order_id(orderId);
			kakaoPayApproveRequest.setPartner_user_id(userId);
			kakaoPayApproveRequest.setPg_token(pg_token);
			
			//결제 승인요청
			KakaoPayApproveResponse kakaoPayApproveResponse = kakaoPayService.approve(kakaoPayApproveRequest);
			
			if(kakaoPayApproveResponse != null)
			{
				logger.info("[OrderKakaoPaycontroller]approve KakoPayApproveRequest :\n" + kakaoPayApproveResponse);
				
				if(kakaoPayApproveResponse.getError_code() == 0)
				{
					//성공(최종 결재완료 db 작업 필요)
					model.addAttribute("code",0);
					model.addAttribute("msg","카카오페이 결제가 완료되었습니다.");
				}
				else
				{
					model.addAttribute("code",-1);
					model.addAttribute("msg",(!StringUtil.isEmpty(kakaoPayApproveResponse.getError_message()) ? kakaoPayApproveResponse.getError_message():"카카오페이 결제처리 중 오류가 발생하였습니다.")
);
					
				}
			}
			else
			{
				//실패
				model.addAttribute("code", -4); 
				model.addAttribute("msg", "카카오페이 결제처리 중 오류가 발생하였습니다.");
			}
			
			if(!StringUtil.isEmpty(tid))
			{
				//tid session delete
				SessionUtil.removeSession(session, KAKAOPAY_TID_SESSION_NAME);
			}
		}

		
		return "/kakao/result";
	}
	
	
	
	
	
	
}
