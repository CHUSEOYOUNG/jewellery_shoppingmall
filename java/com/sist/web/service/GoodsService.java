package com.sist.web.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sist.common.util.StringUtil;
import com.sist.web.model.Goods;


@Service("goodsService")
public class GoodsService 
{
	private static Logger logger = LoggerFactory.getLogger(GoodsService.class);
	
	private List<Goods> goodsList;
	
	//의존성 주입시 자동 호출되는 메서드: @PostConstruct
	@PostConstruct
	private void postConstruct()
	{
		goodsList = new ArrayList<Goods>();
		
		Goods goods1 = new Goods();
		goods1.setGoodsCode("202506090001");
		goods1.setGoodsName("iPhone 15 pro");
		goods1.setGoodsPrice(1500000);
		goodsList.add(goods1);
		
		Goods goods2 = new Goods();
		goods2.setGoodsCode("202506090002");
		goods2.setGoodsName("iPhone 15");
		goods2.setGoodsPrice(1300000);
		goodsList.add(goods2);
		
		Goods goods3 = new Goods();
		goods3.setGoodsCode("202506090003");
		goods3.setGoodsName("iPhone 15 mini");
		goods3.setGoodsPrice(1100000);
		goodsList.add(goods3);
	}
	
	//상품리스트
	public List<Goods> goodsList()
	{
		
		return goodsList;
	}
	
	//상품코드로 상품 조회
	public Goods getGoods(String goodsCode)
	{
		for(Goods goods : goodsList)
		{
			if(StringUtil.equals(goods.getGoodsCode(), goodsCode))
			{
				return goods;
			}
		}
		return null; 
	}
	
	
	
	
	
	
	
	
	
	
	
}
