package com.sist.web.dao;

import java.util.List;

import com.sist.web.model.TourSpot;


	public interface MarkerDao {
	    List<TourSpot> getAllSpots();
	    List<TourSpot> searchByKeyword(String keyword);
	}
