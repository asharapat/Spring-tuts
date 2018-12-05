package com.virtualpairprogrammers.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.virtualpairprogrammers.domain.Tutor;

/*
 * This is not production standard! 
 * WE will be improving it, but for now it must not 
 * be used on real code.
 */
public class TutorManagement 
{
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("myDatabaseConfig");
	
	public Tutor createNewTutor(String staffId, String name, int salary)
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Tutor newTutor = new Tutor(staffId, name, salary);
		
		em.persist(newTutor);
		
		// commit release any database connections
		tx.commit();
		
		// close will detach the tutor
		em.close();
		return newTutor;
	}
	
	public Tutor updateTutor(Tutor tutorToUpdate)
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		// make the object be dirty checked again
		tutorToUpdate = em.merge(tutorToUpdate);	
		
		// dirty check
		tx.commit();
		em.close();
		return tutorToUpdate;
	}
	
	
}
