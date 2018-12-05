package com.virtualpairprogrammers.testharness;

import com.virtualpairprogrammers.services.TutorManagement;

public class LockingExample 
{
	public static void main(String[] args)
	{
		TutorManagement tm = new TutorManagement();
		
		tm.queryTutor();
		
		tm.generateReport();
	}
}
