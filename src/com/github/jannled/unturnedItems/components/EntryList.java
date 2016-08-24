package com.github.jannled.unturnedItems.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.github.jannled.lib.StringUtils;
import com.github.jannled.unturnedItems.data.Item;
import com.github.jannled.unturnedItems.data.Vehicle;

public class EntryList extends JPanel
{
	private static final long serialVersionUID = 8212038118286441343L;
	
	Entry[] entrys;
	JTextField searchField;
	JScrollPane scrollbar;
	JPanel entryContainer;
	
	public EntryList(String searchHint)
	{
		setMinimumSize(new Dimension(280, 300));
		loadComponents(searchHint);
	}
	
	public EntryList(String searchHint, Entry[] entrys)
	{
		loadComponents(searchHint);
		this.entrys = entrys;
	}
	
	public EntryList(String searchHint, LoadoutEntry[] entrys)
	{
		loadComponents(searchHint);
		this.entrys = entrys;
	}
	
	/**
	 * 
	 * @param searchHint
	 * @param data Can be an Entry, an Item or a Vehicle
	 */
	public EntryList(String searchHint, Vector<?> data)
	{
		loadComponents(searchHint);
		setEntrys(data);
	}
	
	public Entry[] setEntrys(Vector<?> data)
	{
		Entry[] output = new Entry[data.size()];
		if(data.get(0) instanceof Item)
		{
			for(int i=0; i<data.size(); i++)
			{
				Item item = (Item) data.get(i);
				Entry e = new Entry(item.getID(), item.getName(), item.getDescription());
				entryContainer.add(e);
				output[i] = e;
			}
		}
		else if(data.get(0) instanceof Vehicle)
		{
			for(int i=0; i<data.size(); i++)
			{
				Vehicle vehicle = (Vehicle) data.get(i);
				Entry e = new Entry(vehicle.getID(), vehicle.getName(), vehicle.getDescription());
				entryContainer.add(e);
				output[i] = e;
			}
		}
		else if(data.get(0) instanceof Entry)
		{
			for(int i=0; i<data.size(); i++)
			{
				Entry entry = (Entry) data.get(i);
				entryContainer.add(entry);
				output[i] = entry;
			}
		}
		this.entrys = output;
		return output;
	}
	
	public void loadComponents(String searchHint)
	{
		setLayout(new BorderLayout());
		
		loadTop(searchHint);
		
		entryContainer = new JPanel(new GridLayout(0, 1));
		add(entryContainer, BorderLayout.CENTER);
		
		scrollbar = new JScrollPane(entryContainer, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollbar.getVerticalScrollBar().setUnitIncrement(20);
		add(scrollbar);
	}
	
	public void loadTop(String searchHint)
	{
		searchField = new JTextField(searchHint);
		searchField.setToolTipText(searchHint);
		searchField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(searchField.getText().equals(searchHint))
				{
					searchField.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(searchField.getText().equals(""))
				{
					searchField.setText(searchHint);
				}
			}
		});
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				drawEntrys(searchField.getText());
			}
		});
		add(searchField, BorderLayout.NORTH);
	}
	
	public void addToEntrylist(Entry entry)
	{
		Entry[] buffer = entrys;
		entrys = new Entry[buffer.length+1];
		entrys[buffer.length] = entry;
	}
	
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
