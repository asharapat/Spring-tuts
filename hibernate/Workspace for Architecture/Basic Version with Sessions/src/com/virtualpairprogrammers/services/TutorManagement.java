package com.virtualpairprogrammers.services;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.hibernate.Session;

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
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    	
    	List<Tutor> allTutors = session.getNamedQuery("allTutors").list();
    	
    	return allTutors;
    }
    
    public long getTotalSalaryBill()
    {
    	// gets the salary bill for the whole college
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    	
    	Long result = (Long)session.getNamedQuery("totalSalary").uniqueResult();
    	 
    	if (result == null) result = 0L;
    	
    	return result;
    }
    
    public Tutor findTutorByIdWithSupervisionGroup(int id) throws NoResultsFoundException
    {
       	// gets the salary bill for the whole college
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();

    	Tutor tutor;
		try
    	{
        	tutor = (Tutor)session.getNamedQuery("tutorByIdWithEagerFetchOfStudents").setParameter("id", id).uniqueResult();
    	}
    	catch (javax.persistence.NoResultException e)
    	{
    		throw new NoResultsFoundException();
    	}

    	return tutor;
    }

	public String createNewTutor(String name, int salary) 
	{
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    	
    	// generate a Staff Id. This is a very cheap way of doing it but it is a very
    	// long id!
    	String staffId = UUID.randomUUID().toString();
    	
    	Tutor newTutor = new Tutor(staffId, name, salary);    	
    	session.save(newTutor);
    	
    	return staffId;
	}
}
