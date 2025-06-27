package com.sist.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.web.dao.CalanderDao;
import com.sist.web.model.Calander;
import com.sist.web.model.CalanderList;

import org.springframework.transaction.annotation.Transactional;

@Service
public class CalanderService {

    @Autowired
    private CalanderDao calanderDao;

    public void saveList(CalanderList list) {
        calanderDao.insertCalanderList(list);
    }

    @Transactional
    public void saveDetail(Calander detail) {
        calanderDao.insertCalander(detail);
    }

    public List<Calander> getCalanders(String listId) {
        return calanderDao.getCalanderWithUnifiedSpotName(listId);
    }
}

