package com.virtualpairprogrammers.domain;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Person 
{
	@Id
	private String id;
	private String name;
	
	
	public Person(String id, String name)
	{
		this.name = name;
		this.id = id;
	}
	
	public abstract void calculateReport();
	
	protected String getName()
	{
		return this.name;
	}
}
