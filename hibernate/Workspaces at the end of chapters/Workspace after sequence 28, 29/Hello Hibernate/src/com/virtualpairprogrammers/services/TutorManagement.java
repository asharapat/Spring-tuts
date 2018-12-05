package com.virtualpairprogrammers.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import com.virtualpairprogrammers.domain.Student;
import com.virtualpairprogrammers.domain.Tutor;
import com.virtualpairprogrammers.domain.Tutor_;

/*
 * This is not production standard! 
 * WE will be improving it, but for now it must not 
 * be used on real code.
 */
public class TutorManagement 
{
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("myDatabaseConfig");
	
	// STEP 1
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
	
	// Note: this method will only return Tutor data. Please don't
	// try to access the students (or subjects)
	public Tutor findTutorById(int id)
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Tutor foundTutor = em.find(Tutor.class, id);
		
		tx.commit();
		em.close();
		return foundTutor;
	}
	
	// returns a tutor AND their associated supervision group
	public Tutor findTutorByIdWithSupervisionGroup(int id)
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Tutor foundTutor = (Tutor)em.createQuery("select tutor from Tutor as tutor left join fetch tutor.supervisionGroup where tutor.id=:id")
					.setParameter("id", id)
					.getSingleResult();
		
		tx.commit();
		em.close();
		return foundTutor;
		
	}
	
	// STEP 2
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

	// this method will give the name and salarys of every tutor in the college, with their salary
	// a summary at the end with the total salary bill for the whole college.
	public void generateReport() 
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
				
		List<Object[]> namesAndSalarys = em.createQuery("select tutor.name, tutor.salary from Tutor as tutor")
										   .setLockMode(LockModeType.PESSIMISTIC_READ)
										   .getResultList();
		
		for (Object[] next : namesAndSalarys)
		{
			System.out.println(next[0] + " : " + next[1]);
		}
		
		long totalSalary = (Long)em.createQuery("select sum(tutor.salary) from Tutor as tutor").getSingleResult();
		System.out.println("The total salary for the college is " + totalSalary);
				
		tx.commit();
		em.close();
	}
	
	public void queryTutor()
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Tutor tutor = em.find(Tutor.class, 1);
		
		System.out.println("the tutor's name is " + tutor.getName());
		
		System.out.println("the tutor's salary is " + tutor.getSalary());
		
		tx.commit();
		em.close();
	}
	
	
}
