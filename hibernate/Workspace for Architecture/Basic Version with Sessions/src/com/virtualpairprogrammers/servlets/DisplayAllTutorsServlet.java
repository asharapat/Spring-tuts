package com.virtualpairprogrammers.servlets;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.virtualpairprogrammers.domain.Tutor;
import com.virtualpairprogrammers.hibernate.HibernateUtil;
import com.virtualpairprogrammers.services.TutorManagement;

/**
 * A very basic servlet to display all the tutors in the college.
 * 
 * Uses basic MVC - see our Java Web Development course for details.
 */
public class DisplayAllTutorsServlet extends HttpServlet
{
	public void doGet (HttpServletRequest request, 
			HttpServletResponse response) 
			throws ServletException,IOException
	{    	
		// do some work
		TutorManagement service = TutorManagement.getService();	

		List<Tutor> allTutors = service.getAllTutors();
		request.setAttribute("allTutors", allTutors);
		
		// now get the total salary
		long totalSalary = service.getTotalSalaryBill();
		request.setAttribute("totalSalary", totalSalary);
				
		// forward to the displayAllBooks.jsp page to display the results
		ServletContext context = getServletContext();
		RequestDispatcher dispatch = context.getRequestDispatcher("/displayAllTutors.jsp");
		dispatch.forward(request,response);
	}
}
