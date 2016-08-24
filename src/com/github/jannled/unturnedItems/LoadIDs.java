package com.github.jannled.unturnedItems;

import java.io.File;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.github.jannled.lib.FileUtils;
import com.github.jannled.lib.Print;
import com.github.jannled.unturnedItems.data.Item;
import com.github.jannled.unturnedItems.data.Vehicle;

public class LoadIDs
{
	String unturnedPath;
	String fs = File.separator;
	
	HashMap<Integer, Item> itemIDs = new HashMap<Integer, Item>();
	HashMap<Integer, Vehicle> vehicleIDs = new HashMap<Integer, Vehicle>();
	
	public LoadIDs(String unturnedPath)
	{
		this.unturnedPath = unturnedPath;
	}
	
	public Vector<Item> loadItemIDs(JFrame frame)
	{
		Vector<Item> items = new Vector<Item>();
		File directory = new File(unturnedPath + fs + "Bundles" + fs + "Items");
		
		if(!directory.exists())
		{
			String n = System.lineSeparator();
			String e = "Could not find the Unturned specific folders. Please make sure that you have selected the correct Workingdirectory under " + n +"\"File\" -> \"Change Path of Unturned\". If you have done that Nelson Sexton did major changes in Unturned and this program isn't working anymore. " + n + "Please check if a new version is avaiable or contact the developer Jannled.";
			Print.e(e);
			JOptionPane.showMessageDialog(frame, e);
		}
		
		//Für jede Itemgruppe
		for(File group : directory.listFiles())
		{
			//Für jeden Itemordner
			for(File folder : group.listFiles())
			{
				int itemID = -1;
				String itemName = "";
				String itemGroup = folder.getName();
				String itemType = "";
				String itemDescription = "";
				
				//Für jedes Item (Es gibt 2 Dateien, ITEM.dat und English.dat)
				for(File item : folder.listFiles())
				{
					String[] text = FileUtils.readTextFile(item);
					
					//ITEM.dat
					if(item.getName().equals("English.dat"))
					{
						itemName = find("Name", text);
						itemDescription = find("Description", text);
					}
					
					//English.dat
					else
					{
						itemType = find("Type", text);
						
						try {
							itemID = Integer.parseInt(find("ID", text));
						} catch(NumberFormatException e) {
							Print.e("The ItemID of " + item.getName() + " is not a number.");
						}
					}
				}
				
				Item item = new Item(itemID, itemName, itemGroup, itemType, itemDescription);
				if(!itemName.equals("<?>"))
				{
					itemIDs.put(item.getID(), item);
					items.add(item);
				}
			}
		}
		return items;
	}
	
	public Vector<Vehicle> loadVehicleIDs(JFrame frame)
	{
		Vector<Vehicle> vehicles = new Vector<Vehicle>();
		File directory = new File(unturnedPath + fs + "Bundles" + fs + "Vehicles");
		
		if(!directory.exists())
		{
			String n = System.lineSeparator();
			String e = "Could not find the Unturned specific folders. Please make sure that you have selected the correct Workingdirectory under " + n +"\"File\" -> \"Change Path of Unturned\". If you have done that Nelson Sexton did major changes in Unturned and this program isn't working anymore. " + n + "Please check if a new version is avaiable or contact the developer Jannled.";
			Print.e(e);
			JOptionPane.showMessageDialog(frame, e);
		}
	
		//Für jeden Itemordner
		for(File folder : directory.listFiles())
		{
			int itemID = -1;
			String itemName = "";
			
			//Für jedes Item (Es gibt 2 Dateien, ITEM.dat und English.dat)
			for(File item : folder.listFiles())
			{
				String[] text = FileUtils.readTextFile(item);
				
				//ITEM.dat
				if(item.getName().equals("English.dat"))
				{
					itemName = find("Name", text);
				}
				
				//English.dat
				else
				{	
					try {
						itemID = Integer.parseInt(find("ID", text));
					} catch(NumberFormatException e) {
						Print.e("The ItemID of " + item.getName() + " is not a number.");
					}
				}
			}
			
			Vehicle vehicle = new Vehicle(itemID, itemName);
			if(!itemName.equals("<?>"))
			{
				vehicleIDs.put(vehicle.getID(), vehicle);
				vehicles.add(vehicle);
			}
		}
		return vehicles;
	}
	
	/**
	 * 
	 * @param key The key to find in the text.
	 * @param text The text file.
	 * @return Rerturns the line where the key has been found without the key.
	 */
	public String find(String key, String[] text)
	{
		key = key + " ";
		String output;
		
		for(String s : text)
		{
			if(s.contains(key))
			{
				output = s.replace(key, "");
				return output;
			}
		}
		return "<?>";
	}
	
	public HashMap<Integer, Item> getItemIDs()
	{
		return itemIDs;
	}
	
	public HashMap<Integer, Vehicle> getVehicleIDs()
	{
		return vehicleIDs;
	}
}
