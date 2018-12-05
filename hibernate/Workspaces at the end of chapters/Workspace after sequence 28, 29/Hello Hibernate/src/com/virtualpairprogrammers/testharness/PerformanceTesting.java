package com.virtualpairprogrammers.testharness;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.stat.Statistics;

import com.virtualpairprogrammers.domain.Student;
import com.virtualpairprogrammers.domain.Subject;
import com.virtualpairprogrammers.domain.Tutor;

public class PerformanceTesting 
{
	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("myDatabaseConfig");

	public static void main(String[] args)
	{		
		setUpData();
		
		EntityManager em = emf.createEntityManager();
		
		Session session = (Session)em.getDelegate();
		Statistics stats = session.getSessionFactory().getStatistics();
		stats.setStatisticsEnabled(true);
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		
		// Cache not used
		Tutor  t = (Tutor)em.createQuery("select tutor from Tutor as tutor where tutor.name='David Banks'")
				.setHint("org.hibernate.cacheable", true)
				.getSingleResult();
		System.out.println(t);
		
		Set<Student> students = t.getSupervisionGroup();
		for (Student next : students)
		{
			System.out.println(next);
		}
		
		tx.commit();
		em.close();
		
		
		// later - possibly much later
		EntityManager em2 = emf.createEntityManager();
		EntityTransaction tx2 = em2.getTransaction();
		tx2.begin();
		
		
		System.out.println("\n\n\n\nSecond Entity Manager: \n");
		
		// Cache not used
		Tutor t2 = (Tutor)em2.createQuery("select tutor from Tutor as tutor where tutor.name='David Banks'")
				.setHint("org.hibernate.cacheable", true)
				.getSingleResult();
		System.out.println(t2);
		
		Set<Student> students2 = t2.getSupervisionGroup();
		for (Student next: students2)
		{
			System.out.println(next);
		}
		
		tx2.commit();
		em2.close();
		
		
		System.out.println("Tutor cache " + stats.getSecondLevelCacheStatistics("com.virtualpairprogrammers.domain.Tutor"));
		
		System.out.println("Student cache" + stats.getSecondLevelCacheStatistics("com.virtualpairprogrammers.domain.Student"));
	}
	
	public static void setUpData()
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		// Some subjects
		Subject mathematics = new Subject("Mathematics", 2);
		Subject science = new Subject("Science", 2);
		Subject history = new Subject("History", 3);
		em.persist(mathematics);
		em.persist(science);
		em.persist(history);

		// This tutor will be very busy, with lots of students
		Tutor t1 = new Tutor("ABC123", "David Banks", 2939393);
		t1.addSubjectToQualifications(mathematics);
		t1.addSubjectToQualifications(science);
		
		// This tutor is new and has no students
		// But he will be able to teach science and mathematics
		Tutor t2 = new Tutor("DEF456", "Alan Bridges", 0);
		t2.addSubjectToQualifications(mathematics);
		t2.addSubjectToQualifications(science);
		
		// This tutor is the only tutor who can teach History
		Tutor t3 = new Tutor("GHI678", "Linda Histroia", 0);
		t3.addSubjectToQualifications(history);
		
		// extras added for batch testing
		Tutor t4 = new Tutor("ABC456", "Claire Thatcher", 15000);
		Tutor t5 = new Tutor("ABC789", "Mark Wilson", 15000);
		Tutor t6 = new Tutor("DEF789", "Richard Snowden", 15000);
		Tutor t7 = new Tutor("GHI123", "Laura Gibson", 15000);
		Tutor t8 = new Tutor("ABCD1", "Mick Stenton", 15000);
		Tutor t9 = new Tutor("ABCD2", "Ian Winterburn", 15000);
		Tutor t10 = new Tutor("ABCD3", "Nicola Spinks", 15000);
		Tutor t11= new Tutor("ABCD4", "Gareth Topham", 15000);
		Tutor t12 = new Tutor("ABCD5", "Nina Carberry", 15000);
		Tutor t13 = new Tutor("ABCD6", "Carrie Ford", 15000);
		Tutor t14 = new Tutor("ABCD7", "Jaqui Oliver", 15000);
		
		em.persist(t1);
		em.persist(t2);
		em.persist(t3);	
		em.persist(t4);
		em.persist(t5);
		em.persist(t6);
		em.persist(t7);
		em.persist(t8);
		em.persist(t9);
		em.persist(t10);
		em.persist(t11);
		em.persist(t12);
		em.persist(t13);
		em.persist(t14);

		// this only works because we are cascading from tutor to student
		t1.createStudentAndAddToSupervisionGroup("Marco Fortes", "1-FOR-2010", "1 The Street", "Anytown", "484848");
		t1.createStudentAndAddToSupervisionGroup("Kath Grainer", "2-GRA-2009", "2 Kaths Street", "Georgia", "939393");
		t3.createStudentAndAddToSupervisionGroup("Sandra Perkins", "3-PER-2009", "4 The Avenue", "Georgia", "939393");
		t2.createStudentAndAddToSupervisionGroup("Mark Pitman", "4-PIT-2009", null, null, null);
		t4.createStudentAndAddToSupervisionGroup("Richard Dunwoody", "5-DUN-2009", null, null, null);
		t5.createStudentAndAddToSupervisionGroup("Claire Balding", "6-BAL-2009", null, null, null);
		t6.createStudentAndAddToSupervisionGroup("Tanya Stephenson", "7-STE-2009", null, null, null);
		t8.createStudentAndAddToSupervisionGroup("Lesley Graham", "8-GRA-2009", null, null, null);
		t9.createStudentAndAddToSupervisionGroup("Hywell Davies", "9-DAV-2009", null, null, null);
		t10.createStudentAndAddToSupervisionGroup("Ben De Haan", "10-DEH-2009", null, null, null);
		t11.createStudentAndAddToSupervisionGroup("Charlotte Brew", "11-BRE-2009", null, null, null);
		t12.createStudentAndAddToSupervisionGroup("Geraldine Rees", "12-REE-2009", null, null, null);
		t13.createStudentAndAddToSupervisionGroup("Steve Knight", "13-KNI-2009", null, null, null);
		t14.createStudentAndAddToSupervisionGroup("Marcus Armytage", "14-ARM-2009", null, null, null);

		tx.commit();
		em.close();
	}

	
}
