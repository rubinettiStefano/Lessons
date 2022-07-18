package com.generation.lessons.test.model.entities;

import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import com.generation.lessons.model.entities.Date;

class DateTest
{

	@Test
	void testToString()
	{
		Date d = new Date(5,2,1980);
		
		if(!d.toString().equals("05/02/1980"))
			fail("Excpected 05/02/1980, got "+ d.toString());
		
	}
	
	@Test
	void testEquals()
	{
		Date d1 = new Date(5,2,1980);
		Date d2 = new Date(5,2,1980);
		Date d3 = new Date(6,2,1980);
		
		assert(d1.equals(d2));
		assert(!d1.equals(d3));
	}

}
