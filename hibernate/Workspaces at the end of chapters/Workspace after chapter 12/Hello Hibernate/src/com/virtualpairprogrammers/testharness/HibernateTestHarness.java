package com.virtualpairprogrammers.testharness;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.virtualpairprogrammers.domain.Student;
import com.virtualpairprogrammers.domain.Tutor;

public class HibernateTestHarness 
{
	private static SessionFactory sessionFactory = null;

	public static void main(String[] args)
	{		
		SessionFactory sf = getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		Tutor t1 = new Tutor("ABC123", "David Banks", 2939393);
		session.save(t1);
		
		Student s1 = new Student("Marco Fortes", "1-FOR-2010");
		t1.addStudentToSupervisionGroup(s1);
		
		Student s2 = new Student("Luke Adams", "2-ADA-2009");
		t1.addStudentToSupervisionGroup(s2);
		
		Student s3 = new Student("Angie Bainbridge", "3-BAI-2008");
		t1.addStudentToSupervisionGroup(s3);
		
		session.save(s1);
		session.save(s2);
		session.save(s3);
		
		Set<Student> allStudents = t1.getSupervisionGroup();
		
		System.out.println(allStudents.size());
				
		tx.commit();
		session.close();
	}

	public static SessionFactory getSessionFactory()
	{
		if (sessionFactory == null)
		{
			Configuration configuration = new Configuration();
			configuration.configure();
			
			ServiceRegistry serviceRegistry = new 
					ServiceRegistryBuilder().applySettings(configuration.getProperties())
					.buildServiceRegistry();        

			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		return sessionFactory;
	}			
}
