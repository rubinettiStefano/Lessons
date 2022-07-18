package com.generation.lessons.test.model.entities;

import org.junit.jupiter.api.Test;

import com.generation.lessons.model.entities.Lesson;

class StudentTest
{

	@Test
	void testToString()
	{
		Lesson l = new Lesson("Pippo","18/07/2022",10);
		assert(l.toString().equals("18/07/2022 10-11 - Pippo"));
	}

}
