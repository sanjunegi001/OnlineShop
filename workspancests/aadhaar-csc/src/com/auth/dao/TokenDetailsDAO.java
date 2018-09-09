package com.auth.dao;

import java.util.List;

import com.auth.bean.tokenDetails;;

public interface TokenDetailsDAO {

	tokenDetails getOneById(String clientid);

	List<tokenDetails> getAllContact();

	List<tokenDetails> getListUrl(String name);

	int save(tokenDetails contact);

	void update(tokenDetails contact);

	void view(tokenDetails contact);
}
