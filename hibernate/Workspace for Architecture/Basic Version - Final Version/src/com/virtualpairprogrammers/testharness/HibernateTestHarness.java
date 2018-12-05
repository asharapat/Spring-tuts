package com.virtualpairprogrammers.testharness;

import java.util.Scanner;

import javax.persistence.OptimisticLockException;

import com.virtualpairprogrammers.domain.Tutor;
import com.virtualpairprogrammers.services.NoResultsFoundException;
import com.virtualpairprogrammers.services.TutorManagement;

public class HibernateTestHarness 
{
	public static void main(String[] args)
	{		
		TutorManagement tutorManagement = new TutorManagement();
		
		// STEP 1
		Tutor newTutor;
		try 
		{
			newTutor = tutorManagement.findTutorByIdWithSupervisionGroup(41);
			System.out.println("The name of the tutor " + newTutor.getName());			
			System.out.println("The tutor has " + newTutor.getSupervisionGroup().size() + " students.");
		} 
		catch (NoResultsFoundException e) 
		{
			System.out.println("No result found");
		}


	}

}
