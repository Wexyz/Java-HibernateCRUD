package com.rsvp.jdbc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.rsvp.hibernate.entity.Invite;

public class TestJdbc {

	public static void main(String[] args) {
		// Create Session Factoru
		SessionFactory f = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Invite.class).buildSessionFactory();
		
		// Create Session
		Session sess = f.getCurrentSession();
		
		try {
			Invite x = new Invite("water");
			sess.beginTransaction();
			
			sess.save(x);
			sess.getTransaction().commit();
			
		} catch (Exception e) {  
			e.getMessage();
		}	finally {
			f.close();
		}
	}

}
