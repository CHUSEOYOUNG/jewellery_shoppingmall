package com.sist.web.model;

import java.io.Serializable;

public class KakaoPayApproveRequest implements Serializable{


	private static final long serialVersionUID = -7418560448899733079L;
	
	private String id;					//(필수)	가맹점 코드, 10자
	private String cid_secret;			//		가맹점 코드 인증키, 24자, 숫자+영문 소문자 조합
	private String tid;					//(필수)	결제 고유번호, 결제 준비 API 응답에 포함
	private String partner_order_id;	//(필수)	가맹점 주문번호, 결제 준비 API 요청과 일치해야 함
	private String partner_user_id;		//(필수)	가맹점 회원 id, 결제 준비 API 요청과 일치해야 함
	private String pg_token;			//(필수)	결제승인 요청을 인증하는 토큰 사용자 결제 수단 선택 완료 시, approval_url로 redirection 해줄 때 pg_token을 query string으로 전달
	private String payload;				//		결제 승인 요청에 대해 저장하고 싶은 값, 최대 200자
	private int total_amount;			//		상품 총액, 결제 준비 API 요청과 일치해야 함
	
	
	public KakaoPayApproveRequest()
	{
		  id = "";					
		  cid_secret = "";			
		  tid = "";					
		  partner_order_id = "";	
		  partner_user_id = "";		
		  pg_token = "";			
		  payload = "";				
		  total_amount = 0;			
		
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getCid_secret() {
		return cid_secret;
	}


	public void setCid_secret(String cid_secret) {
		this.cid_secret = cid_secret;
	}


	public String getTid() {
		return tid;
	}


	public void setTid(String tid) {
		this.tid = tid;
	}


	public String getPartner_order_id() {
		return partner_order_id;
	}


	public void setPartner_order_id(String partner_order_id) {
		this.partner_order_id = partner_order_id;
	}


	public String getPartner_user_id() {
		return partner_user_id;
	}


	public void setPartner_user_id(String partner_user_id) {
		this.partner_user_id = partner_user_id;
	}


	public String getPg_token() {
		return pg_token;
	}


	public void setPg_token(String pg_token) {
		this.pg_token = pg_token;
	}


	public String getPayload() {
		return payload;
	}


	public void setPayload(String payload) {
		this.payload = payload;
	}


	public int getTotal_amount() {
		return total_amount;
	}


	public void setTotal_amount(int total_amount) {
		this.total_amount = total_amount;
	}
	
}
