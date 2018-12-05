package com.virtualpairprogrammers.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Tutor 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String staffId;
	private String name;
	private int salary;
	
	@OneToMany(mappedBy="supervisor")
	private Set<Student> supervisionGroup;
	
	@ManyToMany(mappedBy="qualifiedTutors")
	private Set<Subject> subjectsQualifiedToTeach;
	
	
	// required by hibernate
	public Tutor() {}

	// "business constructor"
	public Tutor(String staffId, String name, int salary) 
	{
		super();
		this.staffId = staffId;
		this.name = name;
		this.salary = salary;
		this.supervisionGroup = new HashSet<Student>();
		this.subjectsQualifiedToTeach = new HashSet<Subject>();
	}
	
	public void addSubjectToQualifications(Subject subject)
	{
		this.subjectsQualifiedToTeach.add(subject);
		subject.getQualifiedTutors().add(this);
	}
	
	public void addStudentToSupervisionGroup(Student studentToAdd)
	{
		this.supervisionGroup.add(studentToAdd);
		studentToAdd.allocateSupervisor(this);
	}
	
	public Set<Subject> getSubjects()
	{
		return this.subjectsQualifiedToTeach;
	}
	
	public Set<Student> getSupervisionGroup()
	{
		Set<Student> unmodifiable = Collections.unmodifiableSet(this.supervisionGroup);
		return unmodifiable;
	}
	
	public Set<Student> getModifiableSupervisionGroup()
	{
		return this.supervisionGroup;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String toString()
	{
		return "Tutor: " + this.name + " (" + this.staffId + ")";
	}
	
	
}
