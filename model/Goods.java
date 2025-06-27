package com.sist.web.model;

import java.io.Serializable;

public class Goods implements Serializable
{


	private static final long serialVersionUID = -1993630158883183548L;

	private String goodsCode;	//상품코드
	private String goodsName;	//상품명
	private String goodsImage;	//상품명 이미지
	private int goodsPrice;		//상품 금액
	private String goodsContent;//상품 설명
	
	
	public Goods()
	{
		goodsCode = "";	//상품코드
		goodsName = "";	//상품명
		goodsImage = "";	//상품명 이미지
		goodsPrice = 0;		//상품 금액
		goodsContent = "";//상품 설명
	}


	public String getGoodsCode() {
		return goodsCode;
	}


	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}


	public String getGoodsName() {
		return goodsName;
	}


	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}


	public String getGoodsImage() {
		return goodsImage;
	}


	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}


	public int getGoodsPrice() {
		return goodsPrice;
	}


	public void setGoodsPrice(int goodsPrice) {
		this.goodsPrice = goodsPrice;
	}


	public String getGoodsContent() {
		return goodsContent;
	}


	public void setGoodsContent(String goodsContent) {
		this.goodsContent = goodsContent;
	}
	
	
	
}
