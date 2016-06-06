


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Gui extends JFrame implements ActionListener
{
	//SETTING UP PANES SO THEY CAN BE SWAPPED GLOABALLY
	private JPanel pane;
	private JPanel banner;
	private JPanel nav;
	private JPanel pan;
	
	//SETTING GLOBAL I/O FIELDS
	
	//Content for the panel "pan" or "Pantry"
	private JTextField ingre;
	private JTextArea report;
	
	public Gui()	//This is the frame the main will call
	{
		//sets up title in the tab or top bar
		super("Strawberry Short Stack");
		
		//SETTING UP ALL THE DIFFERENT PANES
		
		//The main pane
		pane = (JPanel) getContentPane();
		pane.setLayout(new BorderLayout());
		
		//Title or top thingy
		banner = new JPanel();
		banner.setLayout(new FlowLayout());
		banner.add(new JLabel("Strawberry Short Stack! Good Cooking!"));
		
		//Navigation side pane
		nav = new JPanel();
		nav.setLayout(new GridLayout(5,1));
		nav.add(new JLabel("Navigation"));
		JButton pantry = new JButton("Pantry");
		JButton recipes = new JButton("Recipes");
		JButton shoppinglist = new JButton("Shopping List");
		JButton websearch = new JButton("Web Search");
		pantry.addActionListener(this);
		recipes.addActionListener(this);
		shoppinglist.addActionListener(this);
		shoppinglist.addActionListener(this);
		nav.add(pantry);
		nav.add(recipes);
		nav.add(shoppinglist);
		nav.add(websearch);
		
		//Content of the navigation option "Pantry"
		pan = new JPanel();
		pan.setLayout(new GridLayout(2,1));
		//sub panel for easy setup
		JPanel panopt = new JPanel();
		panopt.setLayout(new FlowLayout());
		ingre = new JTextField(50);
		panopt.add(ingre);
		JButton add = new JButton("Add Ingredient");
		add.addActionListener(this);
		panopt.add(add);
		pan.add(panopt);
		report = new JTextArea();
		pan.add(report);
		
		
		//ADDING ALL THE PANELS TO MAIN FRAME
		pane.add(banner, BorderLayout.NORTH);
		pane.add(nav, BorderLayout.WEST);
		
		pane.setSize(1000,1000);
		pane.setVisible(true);
		pack();
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) // All actions will be heard here
	{
		if(e.getActionCommand().equals("Pantry"))
		{
			pane.add(pan);
			setVisible(true);
		}
		if(e.getActionCommand().equals("Add"))
		{
			
		}
	}
}


