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
import com.virtualpairprogrammers.services.TutorManagement;

/**
 * A basic servlet, using MVC. See the Java Web Development course for details
 */
public class AddNewTutorServlet extends HttpServlet
{
	
	@EJB
	private TutorManagement service;
	
	public void doPost (HttpServletRequest request, 
					     HttpServletResponse response) 
			throws ServletException,IOException
	{
		String name = request.getParameter("NAME");
		int salary = new Integer(request.getParameter("SALARY"));
		
		Tutor newTutor = service.createNewTutor(name, salary);
		String generatedStaffId = newTutor.getStaffId();
		
		request.setAttribute("generatedStaffId", generatedStaffId);
		
		// Forward to a JSP page to inform the user all is well
		ServletContext context = getServletContext();
		RequestDispatcher dispatch = context.getRequestDispatcher("/tutorAdded.jsp");
		dispatch.forward(request,response);
	}
}
