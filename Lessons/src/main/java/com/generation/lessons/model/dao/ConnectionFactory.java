package com.generation.lessons.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory
{
	public static Connection make(String path) throws SQLException, ClassNotFoundException
	{
		Class.forName("org.sqlite.JDBC");
		return DriverManager.getConnection("jdbc:sqlite:"+path);
	}
}
