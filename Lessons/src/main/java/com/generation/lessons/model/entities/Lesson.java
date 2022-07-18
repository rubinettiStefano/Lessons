package com.generation.lessons.model.entities;

public class Lesson extends Entity
{
	private Date day;
	private int hour;
	private String student;
	
	
	
	public Lesson(String student,String day, int hour)
	{
		this.day = new Date(day);
		this.hour = hour;
		this.student = student;
	}

	public Date getDay()
	{
		return day;
	}

	public void setDay(Date day)
	{
		this.day = day;
	}

	public int getHour()
	{
		return hour;
	}

	public void setHour(int hour)
	{
		this.hour = hour;
	}

	public String getStudent()
	{
		return student;
	}

	public void setStudent(String student)
	{
		this.student = student;
	}


	@Override
	public String toString()
	{
		return day+" "+hour+"-"+(hour+1)+ " - "+student;
	}
	
	@Override
	public int hashCode()
	{
		return day.hashCode()*24 + hour;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other==null)
			return false;
		
		if(!(other instanceof Lesson))
			return false;
		
		return this.hashCode()==other.hashCode();
	}
	
	@Override
	public boolean isValid()
	{
		return 	student!=null && !student.isBlank() &&
				hour >=9  && hour<=22 && day.isValid();
	}
}
