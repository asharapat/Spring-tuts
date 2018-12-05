package com.virtualpairprogrammers.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	public void doGet (HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException,IOException
	{

		// now get the total salary and all tutors in a single transaction
		Map<String, Object> results = service.getTutorsAndSalaryBill();
		long totalSalary = (Long)results.get("salary");
		List tutors = (List)results.get("allTutors");
		request.setAttribute("totalSalary", totalSalary);
		request.setAttribute("allTutors", tutors);

		// forward to the displayAllBooks.jsp page to display the results
		ServletContext context = getServletContext();
		RequestDispatcher dispatch = context.getRequestDispatcher("/displayAllTutors.jsp");
		dispatch.forward(request,response);
	}
}
