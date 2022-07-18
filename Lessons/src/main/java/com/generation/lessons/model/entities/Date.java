package com.generation.lessons.model.entities;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class Date implements Validable
{
	private int d,m,y;

	
	/**
	 * Costruttore standard, ordine giorno mese anno
	 * @param d
	 * @param m
	 * @param y
	 */
	public Date(int d, int m, int y)
	{
		this.d = d;
		this.m = m;
		this.y = y;
	}
	
	/**
	 * costruirò una data a partire da una stringa
	 * @param s
	 */
	public Date(String s)
	{
		String[] parts = s.split("/");
		d = Integer.parseInt(parts[0]);
		m = Integer.parseInt(parts[1]);
		y = Integer.parseInt(parts[2]);
		
	}
	
	@Override
	public String toString()
	{
		String dayString   = d<10 ? "0" + d : d+"";
		String monthString = m<10 ? "0" + m : m+"";
		
		return dayString+"/"+monthString+"/"+y;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other==null)
			return false;
		if(this==other)
			return true;
		if(!this.getClass().equals(other.getClass()))
			return false;
		
		return this.toString().equals(other.toString());
	}
	
	@Override
	public int hashCode()
	{
		return y * 366 + m * 31 + d;
	}

	@Override
	public boolean isValid()
	{
		try
		{
			LocalDateTime.of(y, m, d, 0, 1);
			return true;
		}
		catch(DateTimeException e)
		{
			return false;
		}
	}
}
