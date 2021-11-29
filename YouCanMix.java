

import SQL.CreateDrinkTable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class YouCanMix extends JFrame {
    
	//text fields to search/ enter info
    private JTextField findTextField = new JTextField(30);
    private JTextField textCocktailName = new JTextField(30);
    private JTextField textIngredients = new JTextField(30);
    
    // create a new buttons
 	private	JButton Menu = new JButton("Main Menu");
 	private	JButton Exit = new JButton("Exit");
 	private	JButton View = new JButton("View Drinks");
 	private	JButton Search = new JButton("Search For Drinks");
 	private	JButton Rate = new JButton("Rate Drinks");
 	private	JButton Create = new JButton("Create a Drink");

	
	public YouCanMix() {
		// displays YouCanMix title on every window
		super("YouCanMix");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		//Sets actions for each button
		Menu.addActionListener(new ActionListener() {//	WHEN MENU BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				mainMenu();
			}
		}); 
		Exit.addActionListener(new ActionListener() {//	WHEN EXIT BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});       
		View.addActionListener(new ActionListener() {//	WHEN VIEW BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				drinkCatalogue();
			}
		}); 
		Search.addActionListener(new ActionListener() {//	WHEN SEARCH BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				searchDrinks();
			}
		}); 
		Rate.addActionListener(new ActionListener() {//	WHEN RATE BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				rateDrinks();
			}
		}); 
		Create.addActionListener(new ActionListener() {//	WHEN CREATE BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				createDrinks();
			}
		}); 
		
		//Displays the main menu to start
		mainMenu();
		
	}	
	
	//interface for the main menu
	public void mainMenu() {	
		
		getContentPane().removeAll();//clears frame
		
		//Main menu
		
		JPanel menu = new JPanel(new GridBagLayout());	//panel to display menu
			
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
         
        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;     
        menu.add(View, constraints);
 
        constraints.gridx = 1;
        menu.add(Search, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 1;     
        menu.add(Rate, constraints);
         
        constraints.gridx = 1;
        menu.add(Create, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        menu.add(Exit, constraints);
         
        // set border for the panel
        menu.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Main Menu"));
         
        // add the panel to this frame
        add(menu);
         
        pack();
        setLocationRelativeTo(null);
	}
	
	//interface to display all the drinks
	public void drinkCatalogue() { 
		
		getContentPane().removeAll();//clears frame
		
		//View Drinks
		
		JPanel catalogue = new JPanel(new GridBagLayout());//panel to display list of drinks
		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
		
        //temporary label
		JLabel drinks = new JLabel("***********List of Drinks goes here***********");
		
		//add menu and exit button to panel
        constraints.gridx = 0;
        constraints.gridy = 0;     
        catalogue.add(drinks, constraints); //add list of drinks here*********
 		
        
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.WEST;
        catalogue.add(Menu, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.EAST;
        catalogue.add(Exit, constraints);
         
        // set border for the panel
        catalogue.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Drink Catalogue"));
         
        // add the panel to this frame
        add(catalogue);
         
        pack();
        setLocationRelativeTo(null);
	}
	
	//interface to allow user to search for drinks
	public void searchDrinks() { 
		
		getContentPane().removeAll();//clears frame
		
		//Search for drinks
			
		JPanel search = new JPanel(new GridBagLayout());//panel to display list of drinks
		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
		
		searchBar(search);
		
		//add drinks related to search here*********
		
		//add menu and exit button to panel
		constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.WEST;
        search.add(Menu, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.EAST;
        search.add(Exit, constraints);
			
     // set border for the panel
        search.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Search For Drinks"));
         
        // add the panel to this frame
        add(search);
         
        pack();
        setLocationRelativeTo(null);
		
	}
	
	//interface for user to rate drinks
	public void rateDrinks() { 
	
		getContentPane().removeAll();//clears frame
		
		//Rate Drinks
		
		JPanel rating = new JPanel(new GridBagLayout());//panel to display list of drinks
		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
		
		searchBar(rating);// either returns value or the list of drinks **tbd
		
		//add list of drinks here*********
		//placement on panel to be updated
		
		//add menu and exit button to panel
		constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.WEST;
        rating.add(Menu, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.EAST;
        rating.add(Exit, constraints);
			
     // set border for the panel
        rating.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Rate Drinks"));
         
        // add the panel to this frame
        add(rating);
         
        pack();
        setLocationRelativeTo(null);
	}
		
	//interface for user to create a drink
	public void createDrinks() { 
		
		getContentPane().removeAll();//clears frame
		
		//Create Drinks
		
		JPanel creating = new JPanel(new GridBagLayout());//panel to prompt user entry		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
		
		//Display prompt for cocktail name
		JLabel cocktailName = new JLabel("Cocktail Name:");

	    //Display prompt for ingredients
	    JLabel ingredients = new JLabel("Enter Ingredients:");
	    
	    //might want to make multiple inputs for different ingredients/allow user to add more
	    //have to account for quantities of each ingredient/ make user input
	



	    //button to be triggered once cocktail is fully inputed
	    JButton Enter = new JButton("Enter");
	    Enter.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent event) {//needs to be updated to add cocktail to database***
	            String s = findTextField.getText().toUpperCase().trim();
	            if (!s.equals("")) {
	                findTextField.setText(s);
	            }
				Drink drink = new Drink(textCocktailName.getText(), textIngredients.getText());
				drink.insertDrink();
	        }
	    });
	    
	 // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;     
        creating.add(cocktailName, constraints);
 
        constraints.gridx = 1;
        creating.add(textCocktailName, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 1;     
        creating.add(ingredients, constraints);
         
        constraints.gridx = 1;
        creating.add(textIngredients, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        creating.add(Enter, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.WEST;
        creating.add(Menu, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.EAST;
        creating.add(Exit, constraints);
         
        // set border for the panel
        creating.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Create a Drink"));
         
        // add the panel to this frame
        add(creating);
         
        pack();
        setLocationRelativeTo(null);
	 
	}
	
	//function that displays search bar and searches for the drink
	public void searchBar(JPanel search) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);

		JButton findButton = new JButton("Search");
		JLabel findLabel = new JLabel("Search:");
	    
	    constraints.gridx = 0;
        constraints.gridy = 0;     
        search.add(findLabel, constraints);
 
        constraints.gridx = 1;
        search.add(findTextField, constraints);
        
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.EAST;
        search.add(findButton, constraints);
	
	    //Action once user clicks the findButton	    
	    findButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent event) {//needs to be updated to display relative drinks***
	            String s = findTextField.getText().toUpperCase().trim();
	            if (!s.equals("")) {
	                findTextField.setText(s);
	            }
	        }
	    });
	 
	}

    
	//starts the program
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
			      	new YouCanMix().setVisible(true);
			}
		});
		// Creates drinks table in MySQL DB
		CreateDrinkTable drinkTable = new CreateDrinkTable();
	}


















}
		