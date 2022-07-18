package com.generation.lessons.controller.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.generation.lessons.model.dao.LessonDAO;
import com.generation.lessons.model.dao.MockLessonDAO;
import com.generation.lessons.model.entities.Lesson;

/**
 * Servlet implementation class WS
 */
@WebServlet("/*")
public class WS extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
	LessonDAO lessonDAO = new MockLessonDAO();
	
    public WS() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		//READ
		String URI = request.getRequestURI();
		if(URI.equals("/Lessons") || URI.equals("/Lessons/"))
		{
			response.addHeader("Content-type", "text/csv");
			//LA RICHIESTA È DI LEGGERE TUTTE LE LEZIONI
			for(Lesson lesson : lessonDAO.getAll())
				response.getWriter()
				.append(lesson.toCSV()+"\n");
			
			return;
		}
		
		if(URI.startsWith("/Lessons/"))
		{
			String ID = URI.replace("/Lessons/", "");
			Lesson lesson = lessonDAO.get(ID);
			if(lesson==null)
				response.setStatus(404);
			else
				response.getWriter().append(lesson.toCSV()+"\n");
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		//INSERT-CREATE
		String URI = request.getRequestURI();
		if(!URI.equals("/Lessons") && !URI.equals("/Lessons/"))
			response.setStatus(400);
		else
			try
			{
				//Prendo i parametri
				String day = request.getParameter("day");
				int hour = Integer.parseInt(request.getParameter("hour"));
				String student = request.getParameter("student");
	
				//Provo ad inserire la lezione
				Lesson lesson = new Lesson(student,day,hour);
				if(!lesson.isValid())
					throw new RuntimeException("Bad lesson data");
				lessonDAO.insert(lesson);
				
				//ora devo rispondere al Client
				//1 - devo dirgli in che formato gli rispondo
				response.addHeader("Content-type", "text/csv");
				//2 - metto nel BODY quello che voglio
				response.getWriter().append(lesson.toCSV()+"\n");
				
				response.setStatus(201); //201 = CREATED SUCCESSFULLY
			}
			catch(Exception e)
			{
				response.setStatus(400);
				response.getWriter().append(e.toString());
			}
		
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
		String URI = request.getRequestURI();
		if(URI.equals("/Lessons") || URI.equals("/Lessons/"))
		{
			response.setStatus(403);
			return;
		}

		if(URI.startsWith("/Lessons/"))
		{
			String[] parts = URI.replace("/Lessons/", "").split("/");
			String ID = parts[0];
			Lesson oldVersion = lessonDAO.get(ID);
		
			if(oldVersion==null)
			{
				response.setStatus(404);
				return;
			}
		
			try
			{
				
				
				String student = parts[1];
				String day = parts[2].replace("-", "/");
				int hour = Integer.parseInt(parts[3]);
	
				Lesson newVersion = new Lesson(student,day,hour);
				newVersion.setID(ID);
				lessonDAO.update(newVersion);
				
				response.addHeader("Content-type", "text/csv");
				response.getWriter().append(newVersion.toCSV()+"\n");
				
				response.setStatus(200); 
			}
			catch(Exception e)
			{
				response.setStatus(400);
				response.getWriter().append(e.toString());
			}
		}
			
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		//DELETE
		String URI = request.getRequestURI();
		if(URI.equals("/Lessons") || URI.equals("/Lessons/"))
		{
			response.setStatus(403); //FORBIDDENM
			return;
		}
		
		if(URI.startsWith("/Lessons/"))
		{
			String ID = URI.replace("/Lessons/", "");
			Lesson lesson = lessonDAO.get(ID);
			if(lesson==null)
				response.setStatus(404);
			else
				lessonDAO.delete(ID);
			return;
		}
		
		response.setStatus(400);
	}

}
