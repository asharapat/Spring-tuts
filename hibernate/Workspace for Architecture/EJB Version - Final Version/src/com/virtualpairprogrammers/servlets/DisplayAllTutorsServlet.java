package com.virtualpairprogrammers.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import com.virtualpairprogrammers.services.TutorManagement;

/**
 * A very basic servlet to display all the tutors in the college.
 *
 * Uses basic MVC - see our Java Web Development course for details.
 */
public class DisplayAllTutorsServlet extends HttpServlet
{

	@EJB
	private TutorManagement service;
	
	@Resource
	private UserTransaction tx;

	public void doGet (HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException,IOException
	{
		try
		{
			tx.begin();
			List tutors = service.getAllTutors();

			long totalSalary = service.getTotalSalaryBill();
			
			tx.commit();
			
			request.setAttribute("totalSalary", totalSalary);
			request.setAttribute("allTutors", tutors);
		}
		catch (Exception e)
		{
			try {tx.rollback();} catch (Exception e2) { throw new ServletException(e); }
			throw new ServletException(e);
		}

		// forward to the displayAllBooks.jsp page to display the results
		ServletContext context = getServletContext();
		RequestDispatcher dispatch = context.getRequestDispatcher("/displayAllTutors.jsp");
		dispatch.forward(request,response);
	}
}
