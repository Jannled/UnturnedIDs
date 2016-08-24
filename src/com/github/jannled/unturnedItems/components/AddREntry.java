package com.github.jannled.unturnedItems.components;

import com.github.jannled.unturnedItems.data.Item;

public class AddREntry extends Entry
{
	private static final long serialVersionUID = -1939511292821559511L;
	
	Item item;
	
	public AddREntry(Item item)
	{
		super(item.getID(), item.getName(), item.getDescription());
		this.item = item;
		
		setupComponents();
	}
	
	@Override
	public void clicked()
	{
		Selecter.activeLoadout.getLoadout().addItem(item);
		Selecter.activeLoadout.reload();
	}
}
