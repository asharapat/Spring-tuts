package com.virtualpairprogrammers.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Represents a Student enrolled in the college management
 * system (CMS)
 */
@Entity
public class Student extends Person
{
	@Column(unique=true, nullable=false)
	private String enrollmentID;
    
    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="TUTOR_FK")
    private Tutor supervisor; 
    
    @Embedded
    private Address address;
               
    /*
     * Required by Hibernate
     */
    public Student()
    {
    	super(null, null);
    }
    
    /**
     * Initialises a student with no pre set tutor
     */
    public Student(String name, String enrollmentID, String street, 
    		       String city, String zipOrPostcode)
    {
    	super(enrollmentID, name);
    	this.enrollmentID = enrollmentID;
    	this.supervisor = null;
     	this.address = new Address(street, city, zipOrPostcode);
    }
    
    public Student(String name, String enrollmentId)
    {
    	super(enrollmentId, name);
    	this.enrollmentID = enrollmentId;
    	this.address = null;
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
        
    public void calculateReport()
    {
    	System.out.println("report for student " + this.getName());
    }
    
    public String getSupervisorName()
    {
    	return this.supervisor.getName();
    }
    
    public String toString()
    {
    	return this.getName() + " lives at: " + this.address;
    }
    
    public String getName()
    {
    	return super.getName();
    }

	public String getEnrollmentId()
	{
		return this.enrollmentID;
	}

	public Tutor getSupervisor() {
		return this.supervisor;
	}
	
	public void setAddress(Address newAddress)
	{
		this.address = newAddress;
	}
	
	public Address getAddress()
	{
		return this.address;
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
