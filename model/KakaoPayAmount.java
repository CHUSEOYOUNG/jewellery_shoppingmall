package com.sist.web.model;

import java.io.Serializable;

public class KakaoPayAmount implements Serializable{
	private int total;			//전체 결제 금액
	private int tax_free;		//비과세 금액
	private int vat;			//부가세 금액
	private int point;			//사용한 포인트 금액
	private int discount;		//할인 금액
	private int green_deposit;	//컵 보증금
	
	public KakaoPayAmount()
	{
		total = 0;			
		tax_free = 0;		
		vat = 0;			
		point = 0;			
		discount = 0;		
		green_deposit = 0;	
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTax_free() {
		return tax_free;
	}

	public void setTax_free(int tax_free) {
		this.tax_free = tax_free;
	}

	public int getVat() {
		return vat;
	}

	public void setVat(int vat) {
		this.vat = vat;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getGreen_deposit() {
		return green_deposit;
	}

	public void setGreen_deposit(int green_deposit) {
		this.green_deposit = green_deposit;
	}
	
}
