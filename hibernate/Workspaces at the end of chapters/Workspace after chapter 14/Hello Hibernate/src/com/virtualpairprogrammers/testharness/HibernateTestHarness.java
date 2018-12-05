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

//		Tutor t1 = new Tutor("ABC123", "David Banks", 2939393);
//		em.persist(t1);
//		
//		Student s1 = new Student("Marco Fortes", "1-FOR-2010");
//		t1.addStudentToSupervisionGroup(s1);
//		
//		Student s2 = new Student("Luke Adams", "2-ADA-2009");
//		t1.addStudentToSupervisionGroup(s2);
//		
//		Student s3 = new Student("Angie Bainbridge", "3-BAI-2008");
//		t1.addStudentToSupervisionGroup(s3);
//		
//		em.persist(s1);
//		em.persist(s2);
//		em.persist(s3);
//
//		Set<Student> allStudents = t1.getSupervisionGroup();
//
//		System.out.println(allStudents.size());
		
		Tutor myTutor = em.find(Tutor.class, 1);
		
		System.out.println(myTutor);
		
		Set<Student> students = myTutor.getSupervisionGroup();
		for(Student next: students)
		{
			System.out.println(next);
		}
				
		Student myStudent = em.find(Student.class, 2);
		System.out.println(myStudent);
				
		em.remove(myStudent);
		
		tx.commit();
		em.close();
	}

	
}
