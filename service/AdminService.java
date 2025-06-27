package com.sist.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.web.dao.AdminDao;
import com.sist.web.model.Admin;

@Service("adminService")
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    public Admin adminSelect(String adminId) {
        return adminDao.adminSelect(adminId);
    }

    public int adminInsert(Admin admin) {
        return adminDao.adminInsert(admin);
    }

    public int adminUpdate(Admin admin) {
        return adminDao.adminUpdate(admin);
    }

    public int adminDelete(String adminId) {
        return adminDao.adminDelete(adminId);
    }
}