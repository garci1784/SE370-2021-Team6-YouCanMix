

import SQL.CreateSQLTables;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.*;

public class YouCanMix extends JFrame {

	private int x = 1; //ingredient counter
	private String FullIngredient = "";//Holds all the ingredients/quantities

	//text fields to search/ enter info
	private JTextField findTextField = new JTextField(30);
	private JTextField textCocktailName = new JTextField(30);

	//TEXT FIELDS FOR 10 DIFFERENT INGREDIENTS/QUANTITIES
	private JTextField tIngredient = new JTextField(30);
	private JTextField tQuantity = new JTextField(30);


	// create a new buttons
	private JButton Yes = new JButton("Yes");
	private JButton No = new JButton("No");
	private JButton Menu = new JButton("Main Menu");
	private JButton Exit = new JButton("Exit");
	private JButton View = new JButton("View Drinks");
	private JButton Search = new JButton("Search For Drinks");
	private JButton Rate = new JButton("Rate Drinks");
	private JButton Create = new JButton("Create a Drink");
	private JButton findButton = new JButton("Search");

	//ADD DRINK BUTTONS
	private JButton Enter = new JButton("Enter");
	private JButton moreIngredients = new JButton("More Ingredients");


	public YouCanMix() {
		// displays YouCanMix title on every window
		super("YouCanMix");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Sets actions for each button
		Yes.addActionListener(new ActionListener() {//	WHEN YES BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				mainMenu();
			}
		});
		No.addActionListener(new ActionListener() {//	WHEN NO BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});

		//Creates drinks table in MySQL DB
		CreateSQLTables sqlTables = new CreateSQLTables();

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
		Search.addActionListener(new ActionListener() {//	WHEN SEARCH  BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				searchDrinks();
			}
		});

		View.addActionListener(new ActionListener() {//	WHEN VIEW DRINKS BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				drinkCatalogue();
			}
		});
		Search.addActionListener(new ActionListener() {//	WHEN SEARCH FOR DRINKS BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				searchDrinks();
			}
		});
		Rate.addActionListener(new ActionListener() {//	WHEN RATE DRINKS BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				rateDrinks();
			}
		});

		//CREATE DRINKS FUNCTION CALLS
		Create.addActionListener(new ActionListener() {//	WHEN CREATE DRINKS BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				createDrinks();
			}
		});
		moreIngredients.addActionListener(new ActionListener() {//WHEN MORE INGREDIENTS BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent event) {
				x++;
				FullIngredient += tIngredient.getText() + ", " + tQuantity.getText() + "; ";
				tIngredient.setText(null); // clears out ingredient and quantity text box
				tQuantity.setText(null);
				if (x < 10) createDrinks();
				else Error();
			}
		});
		Enter.addActionListener(new ActionListener() {//WHEN ENTER BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent event)
			{
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Drink drink = new Drink(textCocktailName.getText(), tIngredient.getText(), Integer.parseInt(tQuantity.getText()));
					drink.insertDrink();
				} catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
				// clears out ingredient, quantity and cocktail name text box
				tIngredient.setText(null);
				tQuantity.setText(null);
				textCocktailName.setText(null);
				x = 1;
				mainMenu();
			}
		});

		//Displays the verify screen to start
		Verify();

	}

	public void Verify() {

		JPanel verify = new JPanel(new GridBagLayout());    //panel to display menu

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);

		JLabel age = new JLabel("Are You 21 Year's of Age or Older?");

		constraints.gridx = 0;
		constraints.gridy = 0;
		verify.add(age, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 4;
		constraints.anchor = GridBagConstraints.WEST;
		verify.add(Yes, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 4;
		constraints.anchor = GridBagConstraints.EAST;
		verify.add(No, constraints);

		// set border for the panel
		verify.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Verify"));

		// add the panel to this frame
		add(verify);

		pack();
		setLocationRelativeTo(null);

	}

	//interface for the main menu
	public void mainMenu() {

		getContentPane().removeAll();//clears frame

		//Main menu

		JPanel menu = new JPanel(new GridBagLayout());    //panel to display menu

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

		/*
		// adds up to 9 ingredient/quantity pairs
		for (int k = 0; k <= 9; k++) {//CREATES TEXT FIELDS FOR UP TO 9 INGREDIENTS
			xIngredient[k] = new JTextField(30);
			xQuantity[k] = new JTextField(30);
		}
		 */

		JPanel creating = new JPanel(new GridBagLayout());//panel to prompt user entry		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);

		//Display prompt for cocktail name
		JLabel cocktailName = new JLabel("Cocktail Name:");

		//Display prompt for ingredients
		JLabel ingredient = new JLabel("Enter Ingredient " + x + ": ");
		JLabel quantity = new JLabel("Enter Ingredient " + x + "\'s Quantity: ");

		// initial components to the panel
		constraints.gridx = 0;
		constraints.gridy = 0;
		creating.add(cocktailName, constraints); //LABEL FOR NAME OF DRINK

		constraints.gridx = 1;
		creating.add(textCocktailName, constraints); //TEXT BOX FOR NAME OF DRINK


		constraints.gridx = 0;
		constraints.gridy = 1;
		creating.add(ingredient, constraints); //LABEL FOR INGREDIENT X

		constraints.gridx = 1;
		creating.add(tIngredient, constraints);//TEXT BOX FOR INGREDIENTS

		constraints.gridx = 0;
		constraints.gridy = 2;
		creating.add(quantity, constraints); //LABEL FOR QUANTITIES

		constraints.gridx = 1;
		creating.add(tQuantity, constraints); //TEXT BOX FOR QUANTITIES

		constraints.gridx = 2;
		creating.add(moreIngredients, constraints); //ALLOWS USER TO ADD MORE INGREDIENTS


		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.CENTER;
		creating.add(Enter, constraints);

		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 4;
		constraints.anchor = GridBagConstraints.WEST;
		creating.add(Menu, constraints);

		constraints.gridx = 0;
		constraints.gridy = 4;
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

	public void Error() {
		JFrame jFrame = new JFrame();
		JOptionPane.showMessageDialog(jFrame, "Can't Add Anymore Ingredients!", "ERROR", JOptionPane.ERROR_MESSAGE);
	}


	//starts the program
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new YouCanMix().setVisible(true);

			}
		});
	}

} // was forgotten in commit e298309





		