package com.virtualpairprogrammers.testharness;

import com.virtualpairprogrammers.domain.Student;
import com.virtualpairprogrammers.domain.Tutor;
import com.virtualpairprogrammers.services.TutorManagement;

public class HibernateTestHarness 
{
	public static void main(String[] args)
	{		
		TutorManagement tutorManagement = new TutorManagement();
		
		Tutor newTutor = tutorManagement.createNewTutor("1789393", "Clara Jones", 10000);
		
		// client will now sit and wait.....
		
		newTutor.addStudentToSupervisionGroup(new Student("Andrew McCluskey", "1-MCC-1973"));
		newTutor.addStudentToSupervisionGroup(new Student("Martin Cooper", "2-COO-1974"));		
		
		tutorManagement.updateTutor(newTutor);
		
	}

}
