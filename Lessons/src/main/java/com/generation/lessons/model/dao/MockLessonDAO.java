package com.generation.lessons.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.generation.lessons.model.entities.Lesson;

public class MockLessonDAO implements LessonDAO
{
	private List<Lesson> lessons = new ArrayList<Lesson>();

	@Override
	public Lesson insert(Lesson newLesson)
	{
		//dobbiamo scrivere equals prima di contains
		if(lessons.contains(newLesson))
			throw new RuntimeException("Spot already taken for "+newLesson.getDay()+" "+newLesson.getHour());
		
		newLesson.setID("LESSON"+newLesson.hashCode());
		
		lessons.add(newLesson);
		
		return newLesson;
	}

	@Override
	public Lesson get(String ID)
	{
		for(Lesson l : lessons)
			if(l.getID().equals(ID))
				return l;
		return null;
	}

	@Override
	public List<Lesson> getAll()
	{
		return lessons;
	}

	@Override
	public Lesson update(Lesson lesson)
	{
	
		if(get(lesson.getID())==null)
			throw new RuntimeException ("Not found");
		delete(lesson.getID());
		return insert(lesson);
	}

	@Override
	public void delete(String ID)
	{
		Lesson toKill = get(ID);
		if(toKill!=null)
			lessons.remove(toKill);
		
	}
}
