package com.github.jannled.unturnedItems;

import java.io.File;
import java.util.HashMap;
import java.util.Vector;

import com.github.jannled.lib.FileUtils;
import com.github.jannled.lib.Print;
import com.github.jannled.unturnedItems.data.Item;
import com.github.jannled.unturnedItems.data.Loadout;

public class LoadoutManager
{
	Main main;
	
	public File loadoutFile;
	
	public Vector<Loadout> loadouts = new Vector<Loadout>();
	public String[] loadoutText = new String[1];
	
	public LoadoutManager(Main main)
	{
		this.main = main;
	}
	
	public Vector<Item> analyseLoadout(String[] loadout)
	{
		HashMap<Integer, Item> iddb = main.getIDs().getItemIDs();
		Vector<Item> items = new Vector<Item>();
		
		for(int i=1; i<loadout.length; i++)
		{
			String s = loadout[i];
			int id = Integer.parseInt(s);
			Item item = iddb.get(id);
			items.add(item);
		}
		return items;
	}
	
	/**
	 * Load loadouts from file
	 * @param file
	 * @return All Loadouts
	 */
	public Vector<Loadout> loadLoadouts(File file)
	{
		Print.m("Loading Loadouts from file...");
		loadoutFile = file;
		
		String key = "loadout ";
		loadoutText = FileUtils.readTextFile(file);
		loadouts = new Vector<Loadout>();
		
		for(int i=0; i<loadoutText.length; i++)
		{
			String s = loadoutText[i];
			if(s.contains(key))
			{
				s = s.replace("loadout ", "");
				String[] seperate = s.split("/");
				Loadout loadout = new Loadout(i+1, Integer.parseInt(seperate[0]), analyseLoadout(seperate));
				loadouts.add(loadout);
				main.getSelectLoadout().addDecision(loadout);
			}
		}
		return loadouts;
	}
	
	public File getLoadoutFile()
	{
		return loadoutFile;
	}
	
	public Vector<Loadout> getLoadouts()
	{
		return loadouts;
	}
	
	public String[] getLoadoutText()
	{
		return loadoutText;
	}
	
	public void addLoadout(Loadout loadout)
	{
		loadouts.add(loadout);
	}
	
	public void setLoadoutFile(File file)
	{
		loadoutFile = file;
	}
}
