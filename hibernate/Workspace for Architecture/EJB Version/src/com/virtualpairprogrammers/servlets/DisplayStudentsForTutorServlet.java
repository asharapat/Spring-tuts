package com.virtualpairprogrammers.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.virtualpairprogrammers.domain.Tutor;
import com.virtualpairprogrammers.services.NoResultsFoundException;
import com.virtualpairprogrammers.services.TutorManagement;

public class DisplayStudentsForTutorServlet extends HttpServlet
{
	@EJB
	private TutorManagement service;
	
	public void doGet (HttpServletRequest request, 
			HttpServletResponse response) 
			throws ServletException,IOException
	{
		int id = new Integer(request.getParameter("id"));
		
		String resultsPage;
		try 
		{
			Tutor tutor = service.findTutorByIdWithSupervisionGroup(id);
			request.setAttribute("tutor", tutor);
			resultsPage = "/displayTutorDetail.jsp";
		} 
		catch (NoResultsFoundException e) 
		{
			resultsPage = "/noResultsFound.jsp";
		}
		
		// forward to the displayAllBooks.jsp page to display the results
		ServletContext context = getServletContext();
		RequestDispatcher dispatch = context.getRequestDispatcher(resultsPage);
		dispatch.forward(request,response);
	}
}
