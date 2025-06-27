package com.sist.web.dao;

import org.springframework.stereotype.Repository;

import com.sist.web.model.User3;

@Repository("userDao3")
public interface UserDao3 
{
	public User3 userSelect(String userId);
	
	public int userInsert(User3 user);
	
	public int userUpdate(User3 user);
	
	public int userDelete(User3 user);
}
