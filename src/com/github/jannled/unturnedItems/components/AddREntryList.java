package com.github.jannled.unturnedItems.components;

import java.util.Vector;

import com.github.jannled.lib.StringUtils;
import com.github.jannled.unturnedItems.data.Item;

public class AddREntryList extends EntryList
{
	private static final long serialVersionUID = 7486572563119558522L;

	AddREntry[] entrys;
	
	public AddREntryList(String searchHint)
	{
		super(searchHint);
	}
	
	@Override
	public Entry[] setEntrys(Vector<?> data)
	{
		AddREntry[] output = new AddREntry[data.size()];
		if(data.get(0) instanceof Item)
		{
			for(int i=0; i<data.size(); i++)
			{
				Item item = (Item) data.get(i);
				AddREntry e = new AddREntry(item);
				entryContainer.add(e);
				output[i] = e;
			}
		}
		else if(data.get(0) instanceof AddREntry)
		{
			for(int i=0; i<data.size(); i++)
			{
				AddREntry entry = (AddREntry) data.get(i);
				entryContainer.add(entry);
				output[i] = entry;
			}
		}
		this.entrys = output;
		return output;
	}
	
	public void addToEntrylist(AddREntry entry)
	{
		AddREntry[] buffer = entrys;
		entrys = new AddREntry[buffer.length+1];
		entrys[buffer.length] = entry;
	}
	
	@Override
	public void drawEntrys(String search)
	{
		entryContainer.removeAll();

		if(StringUtils.isNumber(search))
		{
			for(Entry entry : entrys)
			{
				if(entry.getItemID() == Integer.parseInt(search))
				{
					entryContainer.add(entry);
				}
			}
		}
		else
		{
			for(Entry entry : entrys)
			{
				if(entry.getItemName().toLowerCase().contains(search.toLowerCase()))
				{
					entryContainer.add(entry);
				}
			}
		}
		validate();
	}
}
