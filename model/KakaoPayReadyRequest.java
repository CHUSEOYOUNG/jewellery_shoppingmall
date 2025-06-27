package com.sist.web.model;

import java.io.Serializable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class KakaoPayReadyRequest implements Serializable{

	private static final long serialVersionUID = -7182225395555565992L;

	private String cid;						//(필수)	가맹점 코드, 10자
	private String cid_secret;				//(선택)	가맹점 코드 인증키, 24자, 숫자와 영문 소문자 조합
	private String partner_order_id;		//(필수)	가맹점 주문번호, 최대 100자
	private String partner_user_id;			//(필수)	가맹점 회원 id, 최대 100자(실명, ID와 같은 개인정보가 포함되지 않도록 유의)
	private String item_name;				//(필수)	상품명, 최대 100자
	private String item_code;				//(선택)	상품코드, 최대 100자
	private int quantity;					//(필수)	상품 수량
	private int total_amount;				//(필수)	상품 총액
	private int tax_free_amount;			//(필수)	상품 비과세 금액
	private int vat_amount;					//(선택)	상품 부가세 금액 값을 보내지 않을 경우 다음과 같이 VAT 자동 계산(상품총액 - 상품 비과세 금액)/11 : 소수점 이하 반올림
	private int green_deposit;				//(선택)	컵 보증금
	private String approval_url;			//(필수)	결제 성공 시 redirect url, 최대 255자
	private String cancel_url;				//(필수)	결제 취소 시 redirect url, 최대 255자
	private String fail_url;				//(필수)	결제 실패 시 redirect url, 최대 255자
	private JsonArray available_cards;		//(선택)	결제 수단으로써 사용 허가할 카드사를 지정해야 하는 경우 사용 카카오페이와 사전 협의 필요 사용 허가할 카드사 코드*의 배열 ex) ["HANA", "BC"](기본값: 모든 카드사 허용)
	private String payment_method_type;		//(선택)	사용 허가할 결제 수단, 지정하지 않으면 모든 결제 수단 허용 CARD 또는 MONEY 중 하나
	private int install_month;				//(선택)	카드 할부개월, 0~12
	private String use_share_installment;	//(선택)	분담무이자 설정 (Y/N), 사용 시 사전 협의 필요
	private JsonObject custom_json; 		/*(선택)	사전에 정의된 기능
												1. 결제 화면에 보여줄 사용자 정의 문구, 카카오페이와 사전 협의 필요
												2. iOS에서 사용자 인증 완료 후 가맹점 앱으로 자동 전환 기능(iOS만 처리가능, 안드로이드 동작불가)
												ex) return_custom_url과 함께 key 정보에 앱스킴을 넣어서 전송
												"return_custom_url":"kakaotalk://"*/
	public KakaoPayReadyRequest()
	{
		  cid = "";						
		  cid_secret = "";				
		  partner_order_id = "";		
		  partner_user_id = "";			
		  item_name = "";				
		  item_code = "";				
		  quantity = 0;				
		  total_amount = 0;				
		  tax_free_amount = 0;		
		  vat_amount = 0;					
		  green_deposit = 0;				
		  approval_url = "";		
		  cancel_url = "";				
		  fail_url = "";				
		  available_cards = null;		
		  payment_method_type = "";		
		  install_month = 0;				
		  use_share_installment = "";	
		  custom_json = null; 
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCid_secret() {
		return cid_secret;
	}
	public void setCid_secret(String cid_secret) {
		this.cid_secret = cid_secret;
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
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(int total_amount) {
		this.total_amount = total_amount;
	}
	public int getTax_free_amount() {
		return tax_free_amount;
	}
	public void setTax_free_amount(int tax_free_amount) {
		this.tax_free_amount = tax_free_amount;
	}
	public int getVat_amount() {
		return vat_amount;
	}
	public void setVat_amount(int vat_amount) {
		this.vat_amount = vat_amount;
	}
	public int getGreen_deposit() {
		return green_deposit;
	}
	public void setGreen_deposit(int green_deposit) {
		this.green_deposit = green_deposit;
	}
	public String getApproval_url() {
		return approval_url;
	}
	public void setApproval_url(String approval_url) {
		this.approval_url = approval_url;
	}
	public String getCancel_url() {
		return cancel_url;
	}
	public void setCancel_url(String cancel_url) {
		this.cancel_url = cancel_url;
	}
	public String getFail_url() {
		return fail_url;
	}
	public void setFail_url(String fail_url) {
		this.fail_url = fail_url;
	}
	public JsonArray getAvailable_cards() {
		return available_cards;
	}
	public void setAvailable_cards(JsonArray available_cards) {
		this.available_cards = available_cards;
	}
	public String getPayment_method_type() {
		return payment_method_type;
	}
	public void setPayment_method_type(String payment_method_type) {
		this.payment_method_type = payment_method_type;
	}
	public int getInstall_month() {
		return install_month;
	}
	public void setInstall_month(int install_month) {
		this.install_month = install_month;
	}
	public String getUse_share_installment() {
		return use_share_installment;
	}
	public void setUse_share_installment(String use_share_installment) {
		this.use_share_installment = use_share_installment;
	}
	public JsonObject getCustom_json() {
		return custom_json;
	}
	public void setCustom_json(JsonObject custom_json) {
		this.custom_json = custom_json;
	}
	
	
	
}
