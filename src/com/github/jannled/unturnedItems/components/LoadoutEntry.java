package com.github.jannled.unturnedItems.components;

import java.awt.Dimension;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.jannled.lib.Print;
import com.github.jannled.unturnedItems.data.Item;
import com.github.jannled.unturnedItems.data.Loadout;

public class LoadoutEntry extends Entry
{
	private static final long serialVersionUID = -3420098627284982286L;
	
	EntryLoadoutList el;
	Loadout loadout;
	Item item;

	public LoadoutEntry(Loadout loadout, Item item)
	{
		super(item.getID(), item.getName(), item.getDescription());
		this.item = item;
		this.loadout = loadout;
		
		JSpinner spinner = new JSpinner();
		spinner.setValue((int) item.getAmount());
		spinner.setBounds(260, 10, 35, 40);
		spinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e)
			{
				setAmout((int) spinner.getValue());
			}
		});
		add(spinner);
	}
	
	public void setAmout(int amount)
	{
		if(amount < 1)
		{
			Print.m("EL " + el);
			this.loadout = el.getLoadout();
			Print.m("Removing item " + item.getID() + " from Loadout " + loadout.getPositionInFile());
			el.removeItem(item);
		}
		else
		{
			item.setAmount(amount);
		}
	}
	
	public void setEL(EntryLoadoutList el)
	{
		this.el = el;
		this.loadout = el.getLoadout();
	}
	
	public Dimension getPreferredSize() 
	{
		return new Dimension(300, 60);
	}
}
