package com.virtualpairprogrammers.services;

import java.rmi.server.UID;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.virtualpairprogrammers.domain.Tutor;
import com.virtualpairprogrammers.hibernate.HibernateUtil;

/*
 * This is a starting point : it is far from perfect and will be improved
 * on the course videos. See the final code folder for the finished version.
 */
public class TutorManagement
{
	private static TutorManagement reference;

    /**
     * Use this method if you want to share the service across many servlets.
     *
     * This is simulating what a true container such as Spring would provide
     */
    public static TutorManagement getService()
    {
    	if (reference == null)
    		reference = new TutorManagement();
    	return reference;
    }

    public List<Tutor> getAllTutors()
    {
    	EntityManager em  = HibernateUtil.getEntityManagerFactory().createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	tx.begin();

    	List<Tutor> allTutors = em.createNamedQuery("allTutors").getResultList();

    	tx.commit();
    	em.close();
    	return allTutors;
    }

    public long getTotalSalaryBill()
    {
    	// gets the salary bill for the whole college
    	EntityManager em  = HibernateUtil.getEntityManagerFactory().createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	tx.begin();
    	
    	Long result = (Long)em.createNamedQuery("totalSalary").getSingleResult();

    	if (result == null) result = 0L;
    	
    	tx.commit();
    	em.close();

    	return result;
    }

    public Tutor findTutorByIdWithSupervisionGroup(int id) throws NoResultsFoundException
    {
       	// gets the salary bill for the whole college
    	EntityManager em  = HibernateUtil.getEntityManagerFactory().createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	tx.begin();

    	Tutor tutor;
		try
    	{
        	tutor = (Tutor)em.createNamedQuery("tutorByIdWithEagerFetchOfStudents").setParameter("id", id).getSingleResult();
    	}
    	catch (javax.persistence.NoResultException e)
    	{
    		throw new NoResultsFoundException();
    	}

    	tx.commit();
    	em.close();
    	return tutor;
    }

	public String createNewTutor(String name, int salary)
	{
    	EntityManager em  = HibernateUtil.getEntityManagerFactory().createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	tx.begin();

    	// generate a Staff Id. This is a very cheap way of doing it but it is a very
    	// long id!
    	String staffId = UUID.randomUUID().toString();

    	Tutor newTutor = new Tutor(staffId, name, salary);
    	em.persist(newTutor);

    	tx.commit();
    	em.close();

    	return staffId;
	}
}
