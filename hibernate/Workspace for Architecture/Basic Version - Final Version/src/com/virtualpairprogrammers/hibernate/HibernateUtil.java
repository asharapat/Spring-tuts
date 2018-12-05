package com.virtualpairprogrammers.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

// From the Hibernate 3.3 reference manual at http://docs.jboss.org/hibernate/orm/3.3/reference/en/html/tutorial.html
public class HibernateUtil {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myDatabaseConfig");


    private static ThreadLocal<EntityManager> threadLocal = new ThreadLocal<EntityManager>();

    // The EntityManagerFactory is thread safe so it can be shared
    // by as many threads as we wish.
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
    
    public static EntityManager getEntityManager()
    {
    	return threadLocal.get();
    }
    
    public static void setEntityManager(EntityManager newEntityManager)
    {
    	threadLocal.set(newEntityManager);
    }

}
