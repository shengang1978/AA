package com.divx.service;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class BaseDao {

	protected SessionFactory getSessionFactory()
	{
		return SessionFactoryHelper.getSessionFactory();
	}
}
