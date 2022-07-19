package com.generation.lessons.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.generation.lessons.model.entities.Lesson;

public class LessonDAOSQLite implements LessonDAO
{

	private static final String INSERTSQL = "insert into Lesson (id,student,day,hour) values('[id]','[student]','[day]','[hour]')";

	private static final String UPDATESQL = "UPDATE Lesson set student = '[student]', day='[day]',hour='[hour]' where id='[id]'";
	
	Connection connection;
	
	public LessonDAOSQLite(Connection connection)
	{
		this.connection = connection;
	}
	
	@Override
	public Lesson insert(Lesson newLesson)
	{
		newLesson.setID("LESSON"+newLesson.hashCode());
		if(get(newLesson.getID())!=null)
			throw new RuntimeException("Lesson "+newLesson.getID()+" already present");
		save(newLesson);
		return newLesson;
	}

	private Lesson _rowToLesson(ResultSet rows) throws SQLException
	{
		return new Lesson
				(
						rows.getString("id"),
						rows.getString("student"),
						rows.getString("day"),
						rows.getInt("hour")
				);
		
	}

	@Override
	public Lesson get(String ID)
	{
		try
		{
			Statement readCmd = connection.createStatement();
			String sql = "select * from Lesson where id = '" + ID + "'";
			ResultSet row = readCmd.executeQuery(sql);

			if (row.next())
			{

				Lesson res = _rowToLesson(row);
				readCmd.close();
				return res;
			} else
			{
				readCmd.close();
				return null;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();

			return null;
		}
	}

	@Override
	public List<Lesson> getAll()
	{
		List<Lesson> res = new ArrayList<>();
		try
		{
			Statement readCmd = connection.createStatement();
			String sql = "select * from Lesson";
			ResultSet rows = readCmd.executeQuery(sql);
			while (rows.next()) 
			{
				Lesson lesson = _rowToLesson (rows); 
				res.add(lesson); 
			}
			readCmd.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}

		return res;
	}

	@Override
	public Lesson update(Lesson newVersion)
	{
		if(get(newVersion.getID())==null)
			throw new RuntimeException("Lesson "+newVersion.getID()+" not present");
		save(newVersion);
		return newVersion;
	}

	@Override
	public void delete(String ID)
	{
		try
		{
			Statement writeCmd = connection.createStatement();
			// soluzione poco carina... ma va per ora.
			// COSTRUZIONE DI UNA QUERY DI INSERIMENTO
			String sql = "Delete from Lesson where id= '" + ID + "'";

			writeCmd.execute(sql);
			writeCmd.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}
	
	private String _replaceInQuery(String sql, Lesson lesson)
	{

		sql = sql.replace("[id]", lesson.getID());
		sql = sql.replace("[student]", lesson.getStudent());
		sql = sql.replace("[day]", lesson.getDay()+ "");
		sql = sql.replace("[hour]", lesson.getHour() + "");
		return sql;
	}

	private boolean save(Lesson lesson)
	{
		try
		{
			Statement writeCmd = connection.createStatement();
			writeCmd.execute(get(lesson.getID()) == null ? // isNew
					_replaceInQuery(INSERTSQL, lesson) : // prendo INSERTSQL, sostituisco ed eseguo
					_replaceInQuery(UPDATESQL, lesson) // prendo UPDATESQL
			);
			writeCmd.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
}
