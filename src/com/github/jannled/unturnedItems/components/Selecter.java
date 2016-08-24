package com.github.jannled.unturnedItems.components;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import com.github.jannled.lib.FileUtils;
import com.github.jannled.lib.Print;
import com.github.jannled.unturnedItems.Main;
import com.github.jannled.unturnedItems.data.Item;
import com.github.jannled.unturnedItems.data.Loadout;

public class Selecter extends JPanel implements ActionListener
{
	private static final long serialVersionUID = -8303132186579273158L;
	
	Main main;
	public static EntryLoadoutList activeLoadout;
	
	public static JPanel panel;
	
	Vector<EntryLoadoutList> loadoutEntrys = new Vector<EntryLoadoutList>();
	Vector<JRadioButton> buttons = new Vector<JRadioButton>();
	JRadioButton newLoadout;
	ButtonGroup bgroup = new ButtonGroup();
	
	public Selecter(Main main)
	{
		this.main = main;
		
		newLoadout = new JRadioButton("New Loadout");
		bgroup.add(newLoadout);
		newLoadout.addActionListener(this);
		add(newLoadout);
		
		setBorder(new TitledBorder(null, "Select Loadout", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMinimumSize(new Dimension(100, 300));
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(100, 300);
	}
	
	public void addDecision(Loadout loadout)
	{	
		LoadoutButton b;
		EntryLoadoutList el = new EntryLoadoutList(EntryLoadoutList.toEntry(loadout), loadout);
		Print.m("Adding Loadout line number " + loadout.getPositionInFile());
		b = new LoadoutButton(el);
		b.addActionListener(this);
		bgroup.add(b);
		add(b);
		buttons.add(b);
		loadoutEntrys.add(el);
		validate();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		LoadoutButton button = null;
		//Select Loadout
		if(e.getSource() instanceof LoadoutButton)
		{
			button = (LoadoutButton) e.getSource();
			activeLoadout = button.getLoadout();
			panel = button.drawEntrys("");
			main.getWindowManager().setLoadoutEntry(activeLoadout);
		}
		//New Loadout
		else if(e.getSource() instanceof JRadioButton)
		{
			if(main.getLoadoutManager().getLoadoutFile() == null)
				main.getLoadoutManager().setLoadoutFile(new File(""));
			int line = FileUtils.readTextFile(main.getLoadoutManager().getLoadoutFile()).length+1;
			for(EntryLoadoutList l : loadoutEntrys)
			{
				if(l.getLoadout().getPositionInFile() >= line)
					line++;
			}
			Loadout loadout = new Loadout(line, 0, new Item[] {});
			main.getLoadoutManager().addLoadout(loadout);
			addDecision(loadout);
		}
	}
	
	public EntryLoadoutList getActiveLoadout()
	{
		return activeLoadout;
	}
}
