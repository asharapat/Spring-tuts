package com.virtualpairprogrammers.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory()
    {
       if (sessionFactory == null)
       {
          Configuration configuration = new Configuration();
     
          ServiceRegistry serviceRegistry = new
             ServiceRegistryBuilder().applySettings
                      (configuration.configure().getProperties()).buildServiceRegistry();
     
          sessionFactory = configuration.buildSessionFactory(serviceRegistry);
       }
       return sessionFactory;
    }
}
