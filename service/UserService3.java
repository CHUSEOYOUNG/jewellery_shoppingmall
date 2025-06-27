package com.sist.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.web.dao.UserDao3;
import com.sist.web.model.User3;

@Service("userService3")
public class UserService3 
{
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao3 userDao;
	
	public User3 userSelect(String userId)
	{
		User3 user = null;
		try
		{
			user = userDao.userSelect(userId);
		}
		catch(Exception e)
		{
			logger.error("[UserService3]userSelect Exception", e);
		}
		
		return user;
		
	}
	
	public int userInsert(User3 user)
	{
		int count = 0;
		try
		{
			count = userDao.userInsert(user);
		}
		catch(Exception e)
		{
			logger.error("[UserService3]userInsert Exception", e);
		}
		
		
		return count;
	}
	

	public int userUpdate(User3 user)
	{
		int count = 0;
		
		try
		{
			count = userDao.userUpdate(user);
		}
		catch(Exception e)
		{
			logger.error("[UserService3]userUpdate Exception", e);
		}
		return count;
	}
	
	public int userDelete(User3 user)
	{
		int count = 0;
		
		try
		{
			count = userDao.userDelete(user);
		}
		catch(Exception e)
		{
			logger.error("[UserService3]userDelete Exception", e);
		}
		return count;
	}
	
	
	
}
