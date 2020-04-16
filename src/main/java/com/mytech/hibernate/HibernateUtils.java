package com.mytech.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtils {
	
	private static final SessionFactory sessonFactory = buildSessionFactory();
	
	private static SessionFactory buildSessionFactory(){
		try{
		return new AnnotationConfiguration().configure().buildSessionFactory();
		}catch(Throwable thrExcp){
            System.err.println("Initial SessionFactory creation failed." + thrExcp);
            throw new ExceptionInInitializerError(thrExcp);
		}
	}
	
	public static SessionFactory getSessionFactory(){
		return sessonFactory;
	}

	public static void shutodown() {

		sessonFactory.close();
	}
}
