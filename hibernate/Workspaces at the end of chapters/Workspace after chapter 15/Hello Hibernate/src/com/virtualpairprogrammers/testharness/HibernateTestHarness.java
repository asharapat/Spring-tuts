package com.virtualpairprogrammers.testharness;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.virtualpairprogrammers.domain.Student;
import com.virtualpairprogrammers.domain.Tutor;

public class HibernateTestHarness 
{
	public static void main(String[] args)
	{		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myDatabaseConfig");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Tutor t1 = new Tutor("ABC123", "David Banks", 2939393);
		em.persist(t1);
		
		// this only works because we are cascading from tutor to student
		t1.createStudentAndAddToSupervisionGroup("Marco Fortes", "1-FOR-2010");
		t1.createStudentAndAddToSupervisionGroup("Kath Grainer", "2-GRA-2009");
		
		tx.commit();
		em.close();
	}

	
}
