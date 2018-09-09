package com.auth.dao;

import org.hibernate.HibernateException;

import com.auth.bean.subAua;

public interface SubAuaDAO {

	subAua getSubAUA(String clientid);

	public boolean isValidClient(String username) throws HibernateException, Exception;

}
