package com.sist.web.dao;

import org.springframework.stereotype.Repository;
import com.sist.web.model.Admin;

@Repository("AdminDao")
public interface AdminDao {

    public Admin adminSelect(String adminId);

    public int adminInsert(Admin admin);
    

    public int adminUpdate(Admin admin);
    

    public int adminDelete(String adminId);
}
