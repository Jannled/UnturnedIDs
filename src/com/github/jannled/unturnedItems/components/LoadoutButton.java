package com.github.jannled.unturnedItems.components;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.github.jannled.lib.Print;
import com.github.jannled.unturnedItems.data.Item;
import com.github.jannled.unturnedItems.data.Loadout;

public class LoadoutButton extends JRadioButton
{
	private static final long serialVersionUID = -7604031998065257682L;
	
	Loadout loadout;
	JPanel loadoutEntrys = new JPanel(new GridLayout(0, 1));
	EntryLoadoutList entryLoadout;
	
	public LoadoutButton(EntryLoadoutList entryLoadout)
	{
		this.entryLoadout = entryLoadout;
		this.loadout = entryLoadout.getLoadout();
		setText("Loadout " + entryLoadout.getLoadout().getPositionInFile());
	}
	
	public JPanel drawEntrys(String search)
	{
		Print.m("Drawing " + getText());
		loadoutEntrys.removeAll();
		for(Item item : entryLoadout.getLoadout().getItems())
		{
			String id = "" + item.getID();
			if(id.contains(search) || (item.getName().indexOf(search) != -1))
			{
				loadoutEntrys.add(new LoadoutEntry(loadout, item));
			}
		}
		return loadoutEntrys;
	}
	
	public EntryLoadoutList getLoadout()
	{
		return entryLoadout;
	}
}
