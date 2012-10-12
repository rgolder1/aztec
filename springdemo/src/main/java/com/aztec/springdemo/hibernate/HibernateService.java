package com.aztec.springdemo.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aztec.springdemo.dao.DemoResultDao;

@Service
public class HibernateService {
	
	@Autowired
	@Qualifier("hibernateDao")
	private DemoResultDao dao;

	@Transactional(readOnly = true)
	public String getResult(String type) {
		return dao.lookupResult(type);
	}

	/*
	 * @Transactional defaults:
	 * 
	 * - Propagation setting is PROPAGATION_REQUIRED.
	 * - Isolation level is ISOLATION_DEFAULT.
	 * - Transaction is read/write.
	 * - Transaction timeout defaults to the default timeout of the underlying transaction system, or to none if timeouts are not supported.
	 * - Any RuntimeException triggers rollback, and any checked Exception does not.
	 */
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void updateResult(Long id, String newResult) {
		// update
	}
}
