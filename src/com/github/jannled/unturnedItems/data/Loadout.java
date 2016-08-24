package com.github.jannled.unturnedItems.data;

import java.util.HashMap;
import java.util.Vector;

import com.github.jannled.lib.ArrayUtils;
import com.github.jannled.lib.Print;

public class Loadout
{
	public static final String[] skillset = {"SkillsetID  0 : Civilian", "SkillsetID  1 : Fire Fighter", "SkillsetID  2 : Police Officer", "SkillsetID  3 : Spec Ops", "SkillsetID  4 : Farmer", "SkillsetID  5 : Fisher", "SkillsetID  6 : Lumberjack", "SkillsetID  7 : Worker", "SkillsetID  8 : Chef", "SkillsetID  9 : Thief", "SkillsetID 10: Doctor"};
	
	int positionInFile;
	int skillsetID;
	Item[] items;
	
	public Loadout(int positionInFile, int skillsetID, Item[] items)
	{
		this.positionInFile = positionInFile;
		this.skillsetID = skillsetID;
		this.items = new Item[items.length];
		
		HashMap<Integer, Item> ids = new HashMap<Integer, Item>();
		for(int i=0; i<items.length; i++)
		{
			Item item = items[i];
			Print.m("Item to be added " + item.toString());
			//Item existiert bereits
			if(ids.containsKey(item.getID()))
			{
				Item existingItem = ids.get(item.getID());
				Print.m("Updating count of item " + existingItem.getID() + " to " + (existingItem.getAmount()+1));
				existingItem.setAmount(existingItem.getAmount()+1);
			}
			//Füge neues Item dem Loadout hinzu
			else
			{
				Print.m("Actual Length = " + ArrayUtils.actualLength(this.items) + " Reserved Length = " + this.items.length);
				this.items[ArrayUtils.actualLength(this.items)] = item;
				ids.put(item.getID(), item);
			}
		}
		
		//Strip the array to the actual size
		Item[] buffer = this.items;
		this.items = new Item[ArrayUtils.actualLength(this.items)];
		for(int i=0; i<ArrayUtils.actualLength(this.items); i++)
		{
			this.items[i] = buffer[i];
		}
	}
	
	public Loadout(int positionInFile, int skillsetID, Vector<Item> items)
	{
		this.positionInFile = positionInFile;
		this.skillsetID = skillsetID;
		
		HashMap<Integer, Item> ids = new HashMap<Integer, Item>();
		Vector<Item> buffer = new Vector<Item>();
		for(Item item : items)
		{
			if(item == null)
				continue;
			//Contains this item, increase amount
			if(ids.containsKey(item.getID()))
			{
				Item exItem = ids.get(item.getID());
				exItem.setAmount(exItem.getAmount()+1);
			}
			//Add the item to the loadout
			else
			{
				ids.put(item.getID(), item);
				buffer.add(item);
			}
		}
		
		//Make array
		this.items = new Item[buffer.size()];
		for(int i=0; i<buffer.size(); i++)
		{
			this.items[i] = buffer.get(i);
		}
		
		Print.m("Items" + this.items[1]);
	}
	
	public int getPositionInFile()
	{
		return positionInFile;
	}

	public int getSkillsetID()
	{
		return skillsetID;
	}

	public Item[] getItems()
	{
		return items;
	}
	
	public void setSkillsetID(int skillsetID)
	{
		this.skillsetID = skillsetID;
		Print.m(toString());
	}
	
	public void addItem(Item item)
	{
		Item[] buffer = new Item[items.length+1];
		for(int i=0; i<items.length; i++)
		{
			//Check if item is in loadout
			if(items[i].getID() == item.getID())
			{
				increaseAmount(item);
				return;
			}
			buffer[i] = items[i];
		}
		buffer[buffer.length-1] = item;
		items = buffer;
	}
	
	public void increaseAmount(Item item)
	{
		item.setAmount(item.getAmount()+1);
	}
	
	public int removeItem(Item item)
	{
		Item[] buffer = items;
		Item[] out = new Item[buffer.length-1];
		int index = -1;
		int stride = 0;
		for(int i=0; i<buffer.length; i++)
		{
			if(buffer[i] != item)
			{
				out[i+stride] = buffer[i];
			}
			else
			{
				index = i;
				stride--;
			}
		}
		items = out;
		return index;
	}
	
	public static String[] getSkillsetIDs()
	{
		return skillset;
	}
	
	@Override
	public String toString()
	{
		String output = "";
		for(int i=0; i<items.length; i++)
		{
			for(int a=0; a<items[i].getAmount(); a++)
			{
				output = output + "/" + items[i].getID();
			}
		}
		return "loadout " + skillsetID + output;
	}
}
