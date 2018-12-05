package com.virtualpairprogrammers.services;

import java.util.List;
import java.util.UUID;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.virtualpairprogrammers.domain.Student;
import com.virtualpairprogrammers.domain.Tutor;

/**
 * An example of an Extended Entity Manager.
 * 
 * Here an entity manager will live for several method invocations, which will be called
 * in a "wizard-like" fashion from a client, potentially over a long period of time 
 * (ie a Conversation)
 * 
 * Key Points: 1: at the end of each method, the transaction is commited and database connections are released
 * 			   2: the EntityManager remains open (it is stored in session state), and NO detaching happens.
 * 			   2: only once the final method is called is the EntityManager closed.
 * 			   3: Lazy Initialization in the Servlet (or View) is not a problem during the intermediate steps
 * 			   4: If you want full isolation (in other words, you don't want changes in the intermediate steps
 *                to be applied to the database), you can defer the dirty check by switching transactions 
 *                off in all steps except the final one.
 *                	
 */

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class BuildATutorWizardImpl implements BuildATutorWizard 
{
	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager extendedEm;
		
	// Step 1: create the new tutor
	@Override
	public Tutor createNewTutor(String name, int salary)
	{
	   	// generate a Staff Id. This is a very cheap way of doing it but it is a very
    	// long id!
    	String staffId = UUID.randomUUID().toString();
    	Tutor newTutor = new Tutor(staffId, name, salary);
    	newTutor.setStaffId(staffId);
		extendedEm.persist(newTutor);
    	return newTutor;	
    }
	
	// step 2: show all students that don't have a tutor
	@Override
	public List<Student> getAllStudentsWithNoTutor()
	{
		return extendedEm.createNamedQuery("allStudentsNoTutor").getResultList();
	}
	
	// step 3 (several times)
	@Override
	public void addStudentToTutorGroup(int studentId, int tutorId)
	{
		// are you worried about these finds? 
		// Hint: think of the First Level Cache!
		Tutor requiredTutor = extendedEm.find(Tutor.class, tutorId);
		Student requiredStudent = extendedEm.find(Student.class, studentId);
		
		requiredTutor.addStudentToSupervisionGroup(requiredStudent);
		
		// this change will either result in an update now (if transactions are on), or at the end.
	}
	
	// step 4. End the process
	@Remove
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void completeProcess()
	{
		// again, nothing to do but the method is important. The removal of the EJB
		// forces the EntityManager to close. 
		// 
		// It is essential to have a transaction on this method, even if you didn't have
		// one in the previous steps.
		
		// tx.commit
		// DIRTY CHECK!!!
	}
	
	
}
