package com.auth.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.auth.bean.deviceDetails;

@Repository
@Transactional
public class BioDAOImpl implements BioDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public deviceDetails getByDevice_ID(int Device_ID) {

		return (deviceDetails) sessionFactory.getCurrentSession().get(deviceDetails.class, Device_ID);

	}

	public List<deviceDetails> getAllDevice() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(deviceDetails.class);
		return criteria.list();

	}

	public int save(deviceDetails device) {
		return (Integer) sessionFactory.getCurrentSession().save(device);
	}

	public void update(deviceDetails device) {
		sessionFactory.getCurrentSession().merge(device);

	}

	public void view(deviceDetails device) {
		sessionFactory.getCurrentSession().merge(device);

	}

	public void delete(int Device_ID) {
		deviceDetails d = getByDevice_ID(Device_ID);
		sessionFactory.getCurrentSession().delete(d);

	}

	public int isValidDevice(String udc) throws HibernateException, Exception {

		System.out.println("UDC CODE" + udc);
		int result = 0;
		Session sess = sessionFactory.openSession();
		Criteria criteria = sess.createCriteria(deviceDetails.class);
		criteria.add(Restrictions.eq("UDC", udc));
		List<deviceDetails> devicelist = criteria.list();

		if ((devicelist != null) && (devicelist.size() > 0)) {

			for (deviceDetails devicelists : devicelist) {
				if (devicelists.getUDC() != null) {

					result = 1;
					return result;
				} else {
					result = 0;
					return result;
				}

			}

		}

		sess.close();
		return result;
	}

}
