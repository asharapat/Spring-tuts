package com.virtualpairprogrammers.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.virtualpairprogrammers.domain.Tutor;
import com.virtualpairprogrammers.services.NoResultsFoundException;

@Repository
@Transactional
public class TutorDao 
{
	// threads managed automatically
	@PersistenceContext
	private EntityManager em;
	
	public List<Tutor> getAllTutors() 
	{
		return em.createNamedQuery("allTutors").getResultList();
	}

	public Tutor getTutorById(int id) throws NoResultsFoundException 
	{
		Tutor tutor =  (Tutor)em.createNamedQuery("tutorByIdLazy").setParameter("id", id).getSingleResult();
		
		if (tutor == null)
		{
			throw new NoResultsFoundException();
		}
		return tutor;
	}

	public void save(Tutor newTutor) 
	{
		em.persist(newTutor);
	}

	public long getSalaryBill() 
	{
		return (Long)em.createNamedQuery("totalSalary").getSingleResult();
	}
}
