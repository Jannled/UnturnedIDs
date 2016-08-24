package com.github.jannled.unturnedItems.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;

import com.github.jannled.lib.Print;
import com.github.jannled.unturnedItems.data.Item;
import com.github.jannled.unturnedItems.data.Loadout;

public class EntryLoadoutList extends EntryList 
{
	private static final long serialVersionUID = -1666808106879526119L;
	
	Loadout loadout;
	LoadoutEntry[] entrys;
	
	JComboBox<?> comboBox;

	public EntryLoadoutList(LoadoutEntry[] le, Loadout loadout)
	{
		super("", le);
		setMinimumSize(new Dimension(320, 300));
		this.entrys = le;
		this.loadout = loadout;
		for(LoadoutEntry loudoutEntry : entrys)
			loudoutEntry.setEL(this);
		
		comboBox.setSelectedIndex(loadout.getSkillsetID());
		drawEntrys();
	}

	@Override
	public void loadTop(String s)
	{
		//The Skillset Selector
		comboBox = new JComboBox<Object>(Loadout.skillset);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == comboBox)
				{
					Print.m("Setting SkillsetID for " + loadout.getPositionInFile() + " to " + comboBox.getSelectedIndex());
					loadout.setSkillsetID(comboBox.getSelectedIndex());
				}
			}
		});
		add(comboBox, BorderLayout.NORTH);
	}
	
	@Override
	public Entry[] setEntrys(Vector<?> data)
	{
		LoadoutEntry[] output = new LoadoutEntry[data.size()];
		if(data.get(0) instanceof Item)
		{
			for(int i=0; i<data.size(); i++)
			{
				Item item = (Item) data.get(i);
				LoadoutEntry e = new LoadoutEntry(loadout, item);
				entryContainer.add(e);
				output[i] = e;
			}
		}
		if(data.get(0) instanceof LoadoutEntry)
		{
			for(int i=0; i<data.size(); i++)
			{
				LoadoutEntry entry = (LoadoutEntry) data.get(i);
				entryContainer.add(entry);
				output[i] = entry;
			}
		}
		this.entrys = output;
		return output;
	}
	
	/**
	 * Loads the loadout to entrys
	 */
	public void reload()
	{
		int len = loadout.getItems().length;
		entrys = new LoadoutEntry[len];
		for(int i=0; i<len; i++)
		{
			entrys[i] = new LoadoutEntry(loadout, loadout.getItems()[i]);
		}
		for(LoadoutEntry loudoutEntry : entrys)
			loudoutEntry.setEL(this);
		drawEntrys();
	}
	
	@Override
	public void addToEntrylist(Entry entry)
	{
		LoadoutEntry[] buffer = entrys;
		entrys = new LoadoutEntry[buffer.length+1];
		entrys[buffer.length] = (LoadoutEntry) entry;
	}
	
	public void removeItem(Item item)
	{
		Print.m("Before " + loadout.toString());
		loadout.removeItem(item);
		Print.m("After " + loadout.toString());
		reload();
		drawEntrys();
	}
	
	public void drawEntrys()
	{
		entryContainer.removeAll();

		for(LoadoutEntry entry : entrys)
		{
			entryContainer.add(entry);
		}
		validate();
	}
	
	public static LoadoutEntry[] toEntry(Loadout loadout)
	{
		Item[] items = loadout.getItems();
		LoadoutEntry[] entries = new LoadoutEntry[items.length];
		for(int i=0; i<items.length; i++)
		{
			Item item = items[i];
			entries[i] = new LoadoutEntry(loadout, item);
		}
		return entries;
	}
	
	public Loadout getLoadout()
	{
		return loadout;
	}
}
