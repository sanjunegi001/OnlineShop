package com.auth.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.auth.bean.tokenDetails;

@Repository
@Transactional
public class TokenDetailsDAOImpl implements TokenDetailsDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public List<tokenDetails> getAllContact() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(tokenDetails.class);
		return criteria.list();
	}

	public List<tokenDetails> getListUrl(String name) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(tokenDetails.class);
		Criterion cn = Restrictions.eq("client_id", name);
		query.add(cn);
		return query.list();

	}

	public int save(tokenDetails naccess) {
		return (Integer) sessionFactory.getCurrentSession().save(naccess);
	}

	public void update(tokenDetails naccess) {
		sessionFactory.getCurrentSession().merge(naccess);

	}

	public void view(tokenDetails naccess) {
		sessionFactory.getCurrentSession().merge(naccess);

	}

	@Override
	public tokenDetails getOneById(String clientid) {
		// TODO Auto-generated method stub

		System.out.println("clientid Insider" + clientid);

		Criteria query = sessionFactory.getCurrentSession().createCriteria(tokenDetails.class);
		Criterion cn = Restrictions.eq("client_id", clientid);
		Criterion cn1 = Restrictions.eq("active_status", "1");
		query.add(cn);
		query.add(cn1);
		return (tokenDetails) query.uniqueResult();
	}

}
