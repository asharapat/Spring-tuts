package com.virtualpairprogrammers.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.virtualpairprogrammers.domain.Tutor;

/*
 * Note: often EJBs have an interface, but if we are not remoting the EJB we can 
 * avoid using interfaces. As this EJB is only called from a Servlet, an interface
 * isn't needed. 
 */
@Stateless
public class TutorManagement
{
	// we can still use a DAO in an EJB, but many people prefer to use the EntityManager
	// directly from the EJB. Think of the EM as a DAO!
	
	@PersistenceContext
	private EntityManager em;

    public List<Tutor> getAllTutors()
    {
		return em.createNamedQuery("allTutors").getResultList();
    }
    
    public Tutor findTutorByIdWithSupervisionGroup(int id) throws NoResultsFoundException
    {
		Tutor tutor =  (Tutor)em.createNamedQuery("tutorByIdLazy").setParameter("id", id).getSingleResult();
		if (tutor == null)
		{
			throw new NoResultsFoundException();
		}
		return tutor;    
	}

	public Tutor createNewTutor(String name, int salary) 
	{
    	// generate a Staff Id. This is a very cheap way of doing it but it is a very
    	// long id!
    	String staffId = UUID.randomUUID().toString();
    	Tutor newTutor = new Tutor(staffId, name, salary);
    	newTutor.setStaffId(staffId);
		em.persist(newTutor);
    	return newTutor;
	}

	/*
	 * This feels a bit unnatural - consider moving the tranaction boundary upwards!
	 */
	public Map<String, Object> getTutorsAndSalaryBill()
	{
		// this will all be in a single transaction
		List<Tutor> tutors = this.getAllTutors();
		long salaryBill = (Long)em.createNamedQuery("totalSalary").getSingleResult();


		Map<String, Object> results = new HashMap<String, Object>();
		results.put("allTutors", tutors);		
		results.put("salary", salaryBill);
		
		return results;
	}
}
