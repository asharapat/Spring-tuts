package com.virtualpairprogrammers.services;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Remove;

import com.virtualpairprogrammers.domain.Student;
import com.virtualpairprogrammers.domain.Tutor;

@Remote
public interface BuildATutorWizard {

	// Step 1: create the new tutor
	public abstract Tutor createNewTutor(String name, int salary);

	// step 2: show all students that don't have a tutor
	public abstract List<Student> getAllStudentsWithNoTutor();

	// step 3 (several times)
	public abstract void addStudentToTutorGroup(int studentId, int tutorId);

	// step 4. End the process
	@Remove
	public abstract void completeProcess();

}