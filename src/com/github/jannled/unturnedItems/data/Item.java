package com.github.jannled.unturnedItems.data;

import com.github.jannled.lib.Print;

public class Item extends Data
{
	String group;
	String type;
	int amount;
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param group
	 * @param type
	 * @param description
	 */
	public Item(int id, String name, String group, String type, String description)
	{
		super(id, name, description);
		this.id = id;
		this.name = name;
		this.group = group;
		this.type = type;
		this.description = description; 
		amount = 1;
	}

	public String getGroup()
	{
		return group;
	}

	public String getType()
	{
		return type;
	}
	
	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		Print.m("Setting amount to " + amount);
		this.amount = amount;
	}

	@Override
	public String toString()
	{
		return "Item ID: " + id + ", Name: " + name + ", Group: " + group + ", Type: " + type + ", Description: " + description;
	}
}
