package com.generation.lessons.model.dao;

import java.sql.Connection;

public class LessonDAOFactory
{
	public static LessonDAO make(Connection connection)
	{
		return new LessonDAOSQLite(connection);
	}
}
