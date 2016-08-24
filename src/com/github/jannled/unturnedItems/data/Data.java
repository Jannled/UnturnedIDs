package com.github.jannled.unturnedItems.data;

public abstract class Data
{
	int id;
	String name;
	String description;
	
	public Data(int id, String name, String description)
	{
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public int getID()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	@Override
	public String toString()
	{
		return "Item ID: " + id + ", Name: " + name + ", Description: " + description;
	}
}
