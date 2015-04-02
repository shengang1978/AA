package com.divx.service;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryHelper
{
	private static SessionFactory	factory = new Configuration().configure().buildSessionFactory();
	
	public static SessionFactory getSessionFactory()
	{
//		if (factory == null || factory.isClosed())
//		{
//			try
//			{
//				factory = new Configuration().configure().buildSessionFactory();
//			}
//			catch(Throwable ex)
//			{
//				//Log exception.
//				//Throw error
//				throw new ExceptionInInitializerError(ex);
//			}
//		}
		
		return factory;
	}
}