package com.virtualpairprogrammers.servlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.virtualpairprogrammers.hibernate.HibernateUtil;

public class HibernateManagementRequestFilter implements Filter{
	public void doFilter(ServletRequest request,
						 ServletResponse response,
			             FilterChain chain)
					     throws IOException, ServletException
    {
		Transaction tx = null;
		Session session = null;
		try 
		{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.getTransaction();
			tx.begin();
			
			// now go to the servlet to start the main code
			chain.doFilter(request,  response);
			
			
			tx.commit();
		}
		catch (Exception e)
		{			
			if (tx != null)
				tx.rollback();

			System.out.println("Rolled Back transaction ok.");
			
			throw new ServletException(e);
		}
		finally
		{
			System.out.println("Attempting to clean up EntityManager");
			
			HibernateUtil.getSessionFactory().getCurrentSession().close();			
		}
	}

	public void destroy() 
	{

	}

	public void init(FilterConfig arg0) throws ServletException 
	{
		
	}
}
