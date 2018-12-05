package com.virtualpairprogrammers.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Represents a Student enrolled in the college management
 * system (CMS)
 */
@Entity
public class Student
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(unique=true, nullable=false)
	private String enrollmentID;
    private String name;
    
    @ManyToOne
    @JoinColumn(name="TUTOR_FK")
    private Tutor supervisor; 
    
    /*
     * Required by Hibernate
     */
    public Student()
    {
    	
    }
    
    /**
     * Initialises a student with a particular tutor
     */
    public Student(String name, Tutor supervisor)
    {
    	this.name = name;
    	this.supervisor = supervisor;
    }
    
    /**
     * Initialises a student with no pre set tutor
     */
    public Student(String name, String enrollmentID)
    {
    	this.name = name;
    	this.enrollmentID = enrollmentID;
    	this.supervisor = null;
    }
    
    public double calculateGradePointAverage()
    {
    	// some complex business logic!
    	// we won't need this method on the course, BUT it is import
    	// to remember that classes aren't just get/set pairs - we expect
    	// business logic in here as well.
    	return 0;
    }
    
    public void allocateSupervisor(Tutor newSupervisor)
    {
    	this.supervisor = newSupervisor;
    	newSupervisor.getModifiableSupervisionGroup().add(this);
    }
    
    public String getSupervisorName()
    {
    	return this.supervisor.getName();
    }
    
    public String toString()
    {
    	return this.name;
    }
    
    public int getId()
    {
    	return this.id;
    }

	public String getEnrollmentId()
	{
		return this.enrollmentID;
	}

	public Tutor getSupervisor() {
		return this.supervisor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((enrollmentID == null) ? 0 : enrollmentID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (enrollmentID == null) {
			if (other.enrollmentID != null)
				return false;
		} else if (!enrollmentID.equals(other.enrollmentID))
			return false;
		return true;
	}




}
