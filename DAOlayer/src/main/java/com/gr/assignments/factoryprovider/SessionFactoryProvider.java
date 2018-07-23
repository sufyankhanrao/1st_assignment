package com.gr.assignments.factoryprovider;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryProvider {
	private static SessionFactoryProvider sessionFactoryProvider=new SessionFactoryProvider();
	private SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();;
	
	private SessionFactoryProvider() {
		
	}
	
	public static SessionFactoryProvider getInstance() {
		return sessionFactoryProvider;
	} 
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
