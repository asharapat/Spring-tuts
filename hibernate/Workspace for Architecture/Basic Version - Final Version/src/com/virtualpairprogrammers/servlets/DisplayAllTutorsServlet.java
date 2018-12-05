package com.virtualpairprogrammers.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.virtualpairprogrammers.domain.Tutor;
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
		
		long totalSalaryBill = service.getTotalSalaryBill();
		
		request.setAttribute("allTutors", allTutors);
		request.setAttribute("totalSalaryBill", totalSalaryBill);
		
		// forward to the displayAllBooks.jsp page to display the results
		ServletContext context = getServletContext();
		RequestDispatcher dispatch = context.getRequestDispatcher("/displayAllTutors.jsp");
		dispatch.forward(request,response);
	}
}
