package com.virtualpairprogrammers.testharness;

import java.util.Scanner;

import javax.persistence.OptimisticLockException;

import com.virtualpairprogrammers.domain.Tutor;
import com.virtualpairprogrammers.services.TutorManagement;

public class HibernateTestHarness 
{
	public static void main(String[] args)
	{		
		TutorManagement tutorManagement = new TutorManagement();
		
		// STEP 1
		Tutor newTutor = tutorManagement.findTutorById(1);

		// client will now sit and wait.....
		newTutor.setName("PROCESS 2 NAME");
		
		System.out.println("Press enter to continue");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		
		// STEP 2 - because there's been a pause, check for a stale object
		try
		{
			tutorManagement.updateTutor(newTutor);
		}
		catch (OptimisticLockException e)
		{
			// deal with the problem.
			System.out.println("Sorry, that tutor was updated while you were thinking. Please try again!");
		}
		
	}

}
