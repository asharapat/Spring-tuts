package com.virtualpairprogrammers.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Subject 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String subjectName;
	private int numberOfSemesters;
	
	@ManyToMany
	private Set<Tutor> qualifiedTutors;
	
	public Subject() {
	}
	
	public Subject(String subjectName, int numberOfSemesters)
	{
		this.subjectName = subjectName;
		this.numberOfSemesters = numberOfSemesters;
		this.qualifiedTutors = new HashSet<Tutor>();
	}
	
	public void addTutorToSubject(Tutor tutor)
	{
		this.qualifiedTutors.add(tutor);
		tutor.getSubjects().add(this);
	}
	
	public Set<Tutor> getQualifiedTutors()
	{
		return this.qualifiedTutors;
	}
	
	public String toString()
	{
		return this.subjectName + " lasts for " + this.numberOfSemesters + " semesters";
	}
}
