package com.sist.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.web.dao.MarkerDao;
import com.sist.web.model.TourSpot;

@Service("markerService")
public class MarkerService{
    @Autowired
    private MarkerDao markerDao;

    public List<TourSpot> getAllSpots() {
        return markerDao.getAllSpots();
    }

    public List<TourSpot> searchByKeyword(String keyword) {
        return markerDao.searchByKeyword(keyword);
    }
}

