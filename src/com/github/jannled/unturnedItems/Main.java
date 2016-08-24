package com.github.jannled.unturnedItems;

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.github.jannled.lib.ArrayUtils;
import com.github.jannled.lib.Print;
import com.github.jannled.unturnedItems.components.Entry;
import com.github.jannled.unturnedItems.components.Selecter;
import com.github.jannled.unturnedItems.data.Item;
import com.github.jannled.unturnedItems.data.Vehicle;

public class Main
{
	WindowManager window;
	LoadIDs loadIDs;
	LoadoutManager manager;
	
	String unturnedPath = "C:\\Program Files (x86)\\Steam\\steamapps\\common\\Unturned";
	
	Vector<Item> items;
	Vector<Vehicle> vehicles;
	
	private Selecter selectLoadout = new Selecter(this);
	
	Main()
	{	
		window = new WindowManager(this);
		manager = new LoadoutManager(this);
		loadData(unturnedPath);
	}

	public void loadData(String pathToUnturned)
	{
		Print.m("Unturned location switched to " + pathToUnturned + ".");
		loadIDs = new LoadIDs(pathToUnturned);
		items = loadIDs.loadItemIDs(window.getFrame());
		vehicles = loadIDs.loadVehicleIDs(window.getFrame());
		window.getItemBrowser().setEntrys(items);
		window.getVehicleBrowser().setEntrys(vehicles);
		window.getLoadoutItemBrowser().setEntrys(items);
	}
	
	public static void main(String[] args)
	{
		Print.m("Running Unturned Item IDs with arguments " + ArrayUtils.arrayToString(args) + ".");
		
		try {
			Print.m("Setting look and feel to OS design.");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			Print.e("Class not found!");
			e.printStackTrace();
		} catch (InstantiationException e) {
			Print.e("Failed to Instantiate!");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Print.e("Illegal Acces!");
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			Print.e("Unsuported Look and feel!");
			e.printStackTrace();
		}
		
		new Main();
	}
	
	public void testWindow()
	{
		JFrame frame = new JFrame("Test");
		frame.getContentPane().add(new Entry(1000, "Gun", "Peng"));
		frame.pack();
		frame.setVisible(true);
	}
	
	public LoadIDs getIDs()
	{
		return loadIDs;
	}
	
	public Vector<Item> getItems()
	{
		return items;
	}
	
	public Vector<Vehicle> getVehicles()
	{
		return vehicles;
	}

	public LoadoutManager getLoadoutManager()
	{
		return manager;
	}
	
	
	public String getUnturnedPath()
	{
		return unturnedPath;
	}
	
	public WindowManager getWindowManager()
	{
		return window;
	}

	public Selecter getSelectLoadout()
	{
		return selectLoadout;
	}

	public void setUnturnedPath(String unturnedPath)
	{
		this.unturnedPath = unturnedPath;
	}
}
