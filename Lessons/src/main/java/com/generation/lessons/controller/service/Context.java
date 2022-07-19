package com.generation.lessons.controller.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.generation.lessons.model.dao.ConnectionFactory;
import com.generation.lessons.model.dao.LessonDAOFactory;

public class Context
{
	static List<Object> dependencies = new ArrayList<Object>();
	
	static
	{
		String path = "C:\\Users\\rubin\\git\\Lessons\\Lessons\\src\\main\\webapp\\lessons.db";
		try
		{
			dependencies.add(LessonDAOFactory.make(ConnectionFactory.make(path)));
		}
		catch(SQLException | ClassNotFoundException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public static Object getDependency(Class type)
	{
		for(Object o:dependencies)
		{
			Class c = o.getClass();
			Class[] interfaces = c.getInterfaces();
			
			if(c.equals(type))
				return o;
			
			for(Class i : interfaces)
				if(i.equals(type))
					return o;
		}
		
		throw new RuntimeException("Object of Class "+type+" not found");
	}
}
