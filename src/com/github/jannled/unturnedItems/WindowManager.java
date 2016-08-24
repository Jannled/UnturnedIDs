package com.github.jannled.unturnedItems;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.github.jannled.lib.FileUtils;
import com.github.jannled.lib.Print;
import com.github.jannled.unturnedItems.components.AddREntryList;
import com.github.jannled.unturnedItems.components.EntryList;
import com.github.jannled.unturnedItems.components.Selecter;
import com.github.jannled.unturnedItems.data.Loadout;

public class WindowManager
{	
	Main main;
	
	private JFrame frame;
	private Selecter selectLoadout;
	private JPanel loadoutEntrys;
	private JSplitPane splitPane;
	private JSplitPane splitPane_1;
	private JTextField loadoutPath;
	File loadoutFile;
	
	public EntryList itemBrowser;
	public EntryList vehicleBrowser;
	public AddREntryList loadoutItemBrowser;
	
	private int activeTab;
	private Dimension minSize = new Dimension(280, 300);

	/**
	 * Create the application.
	 */
	public WindowManager(Main main)
	{
		this.main = main;
		itemBrowser = new EntryList("Search for an Item");
		vehicleBrowser = new EntryList("Search for a Vehicle");
		loadoutItemBrowser = new AddREntryList("Search for an Item");
		initialize();
		frame.setMinimumSize(new Dimension(740, 440));
		frame.setTitle("Unturned Item-IDs");
		//frame.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("/assets/Icon.png")).getImage());
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		Print.m("Opening Window.");
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				activeTab = tabbedPane.getSelectedIndex();
				Print.m("Selected Tab " + activeTab + ".");
			}
		});
		frame.getContentPane().add(tabbedPane);
		
		JPanel greeter = new JPanel();
		tabbedPane.addTab("Welcome", null, greeter, null);
		greeter.setLayout(new BoxLayout(greeter, BoxLayout.X_AXIS));
		
		JTextArea textArea = new JTextArea("This is a free tool to look up items and more for the game Unturned by Nelson Sexton. Im not afiliated with the developer of this game in any way.");
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		greeter.add(textArea);
		
		JPanel createLoadout = new JPanel();
		tabbedPane.addTab("Create Loadout", null, createLoadout, null);
		createLoadout.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		createLoadout.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		loadoutPath = new JTextField("");
		loadoutPath.setEditable(false);
		panel.add(loadoutPath);
		loadoutPath.setColumns(10);
		
		JButton btnNewButton = new JButton("Load Commands.dat");
			btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				Print.m("Selecting Commands.dat");
				JFileChooser fileSelecter = new JFileChooser(main.getUnturnedPath());
				File file = null;
				fileSelecter.setSelectedFile(new File("Commands.dat"));
				int fileState = fileSelecter.showOpenDialog(frame);
				if(fileState == JFileChooser.APPROVE_OPTION)
				{
					file = fileSelecter.getSelectedFile();
					Print.m("Selected " + file.getAbsolutePath());
				}
				loadoutPath.setText(file.getPath());
				main.getLoadoutManager().loadLoadouts(file);
				loadoutFile = file;
			}
		});
		panel.add(btnNewButton);
		
		JButton btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Print.m("Hitted Save Button");
				//No Loadoutfile selected, create one
				if(loadoutFile == null)
				{
					Print.m("Create Commands.dat");
					JFileChooser fileSelecter = new JFileChooser(main.getUnturnedPath());
					fileSelecter.setSelectedFile(new File("Commands.dat"));
					int fileState = fileSelecter.showSaveDialog(frame);
					if(fileState == JFileChooser.APPROVE_OPTION)
					{
						loadoutFile = fileSelecter.getSelectedFile();
						Print.m("Selected " + loadoutFile.getAbsolutePath());
					}
					loadoutPath.setText(loadoutFile.getPath());
				}
				Print.m("Saving file " + loadoutFile.getPath());
				String[] loadouts = toFile();
				FileUtils.writeTextFile(loadoutFile, loadouts);
			}
		});
		panel.add(btnSave);
		
		JPanel panel_1 = new JPanel();
		createLoadout.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		splitPane = new JSplitPane();
		panel_1.add(splitPane);
		
		selectLoadout = main.getSelectLoadout();
		splitPane.setLeftComponent(selectLoadout);
		
		loadoutEntrys = new JPanel(new BorderLayout());
		splitPane.setRightComponent(loadoutEntrys);
		
		splitPane_1 = new JSplitPane();
		splitPane.setRightComponent(splitPane_1);
		
		JPanel browseItems = new JPanel();
		browseItems.setMinimumSize(minSize);
		tabbedPane.addTab("Browse Items", null, browseItems, null);
		browseItems.setLayout(new BoxLayout(browseItems, BoxLayout.X_AXIS));
		browseItems.add(itemBrowser);
		
		JPanel browseVehicles = new JPanel();
		browseVehicles.setMinimumSize(minSize);
		tabbedPane.addTab("Browse Vehicles", null, browseVehicles, null);
		browseVehicles.setLayout(new BoxLayout(browseVehicles, BoxLayout.X_AXIS));
		browseVehicles.add(vehicleBrowser);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmChangePathOf = new JMenuItem("Change Path of Unturned");
		mntmChangePathOf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				Print.m("Changing directory");
				JFileChooser fileSelecter = new JFileChooser();
				fileSelecter.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				File file = null;
				int fileState = fileSelecter.showOpenDialog(frame);
				if(fileState == JFileChooser.APPROVE_OPTION)
				{
					file = fileSelecter.getSelectedFile();
					Print.m("Selected " + file.getAbsolutePath());
				}
				main.setUnturnedPath(file.getAbsolutePath());
				main.loadData(file.getAbsolutePath());
			}
		});
		mnFile.add(mntmChangePathOf);
	}
	
	public String[] toFile()
	{
		String[] file = main.getLoadoutManager().getLoadoutText();
		Vector<Loadout> loadouts = main.getLoadoutManager().getLoadouts();
		for(Loadout l : loadouts)
		{
			int line = l.getPositionInFile();
			//Set the loadout in the text file
			if(file.length>line)
			{
				file[line] = l.toString();
			}
			//Textfile is to small, largen it
			else
			{
				String[] buffer = new String[line];
				for(int i=0; i<file.length; i++)
				{
					buffer[i] = file[i];
				}
				buffer[line-1] = l.toString();
				file = buffer;
			}
		}
		return file;
	}
	
	public JFrame getFrame()
	{
		return frame;
	}
	
	public Selecter getLoadoutSelecter()
	{
		return selectLoadout;
	}
	
	public EntryList getLoadoutItemBrowser()
	{
		return loadoutItemBrowser;
	}
	
	public EntryList getItemBrowser()
	{
		return itemBrowser;
	}
	
	public EntryList getVehicleBrowser()
	{
		return vehicleBrowser;
	}
	
	public File getLoadoutFile()
	{
		return loadoutFile;
	}
	
	public void setLoadoutFile(File loadoutFile)
	{
		this.loadoutFile = loadoutFile;
	}
	
	public void setLoadoutEntry(JPanel panel)
	{
		loadoutEntrys.removeAll();

		loadoutEntrys.add(panel, BorderLayout.CENTER);
		splitPane_1.setLeftComponent(loadoutEntrys);
		splitPane_1.setRightComponent(loadoutItemBrowser);
	}

	public void setItemBrowser(EntryList itemBrowser)
	{
		this.itemBrowser = itemBrowser;
	}

	public void setVehicleBrowser(EntryList vehicleBrowser)
	{
		this.vehicleBrowser = vehicleBrowser;
	}

	public void setLoadoutItemBrowser(AddREntryList loadoutItemBrowser)
	{
		this.loadoutItemBrowser = loadoutItemBrowser;
	}
}
