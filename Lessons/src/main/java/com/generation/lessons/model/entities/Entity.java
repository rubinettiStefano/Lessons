package com.generation.lessons.model.entities;


/**
 * Base per tutte le entities successive
 * @author rubin
 *
 */
public abstract class Entity
{
	//protected = visibile nel package e da tutti i DISCENDENTI
	protected String ID;

	//ACCESSORY METHOD, metodo che da accesso ad una proprietà
	public String getID()
	{
		return ID;
	}

	public void setID(String iD)
	{
		ID = iD;
	}
	
	
	
}
