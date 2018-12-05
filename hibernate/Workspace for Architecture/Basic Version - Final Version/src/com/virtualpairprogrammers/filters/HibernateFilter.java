package com.virtualpairprogrammers.filters;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.virtualpairprogrammers.hibernate.HibernateUtil;

public class HibernateFilter implements Filter
{

	@Override
	public void destroy() {
		HibernateUtil.getEntityManagerFactory().close();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException 
	{
		EntityTransaction tx = null;
		EntityManager em = null;
		try
		{
			System.out.println("Opening EntityManager");
	
			em = HibernateUtil.getEntityManagerFactory().createEntityManager();
			
			HibernateUtil.setEntityManager(em);
			
			tx = em.getTransaction();
			tx.begin();
			
			// proceed to the servlet
			chain.doFilter(request, response);
			
			tx.commit();
		}
		catch (Exception e)
		{
			System.out.println("Caught an exception!");
			if (tx != null) tx.rollback();
			throw new ServletException(e);
		}
		finally
		{
			System.out.println("Closing EntityManager");
			if (em != null) em.close();
			HibernateUtil.setEntityManager(null);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

}
