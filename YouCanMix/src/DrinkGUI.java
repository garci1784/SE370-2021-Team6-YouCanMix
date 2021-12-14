
//import JDBC.JDBCUtil;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
//import javax.swing.event.TableModelEvent;
//import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
//import java.io.*;
//import java.util.*;
//import java.sql.*;



public class DrinkGUI extends JFrame{
	
	private int num_Drinks = 100;
	private int currentSize = 0;
	
	private DrinkManager manager = new DrinkManager(); //DATABASE OBJECT ACCESSOR	
	private Drink currentDrink; //HOLDS THE CURRENT DRINK WE ARE WORKING WITH
	private Drink[] currentDrinks = new Drink[num_Drinks]; //HOLDS THE CURRENT DRINKS WE ARE WORKING WITH
	
	
	private JFrame catalogueFrame = new JFrame(); //VIEW DRINKS FRAME
	private JFrame displayDrinkFrame = new JFrame(); //CURRENT DRINK FRAME 
	private JFrame searchDrinkFrame = new JFrame(); //SEARCH DRINK FRAME
	private JFrame rateDrinkFrame = new JFrame(); //RATE DRINK FRAME
	
	protected JPanel RatingPanel; //ACTIVE RATING PANEL
	protected JPanel rate; //RATE PANEL
	protected JPanel search; //SEARCH PANEL

	private String Parameter = "*";
	private boolean success = true;//BOOLEAN FOR IF ADDING TO DATABASE WAS SUCCESSFULL
	private boolean bk = true;//TRUE GOES BACK TO CATALOGUE AND FALSE GOES BACK TO SEARCH
	private boolean sr = true;//TRUE GOES TO RATE FRAME AND FALSE GOES TO DRINK TABLE
	private boolean active = true;//TOGGLES LIST SELECTION LISTENER
	private int select;//HOLDS THE SELECTION FROM THE TABLE
	
	private String[] currentIngredient = new String[num_Drinks];
    private String[] currentQuantity = new String[num_Drinks];
	
    //CREATES A DEFAULT TABLE MODEL AND AN JTABLE
	private DefaultTableModel defaultTableModel = new DefaultTableModel();
    private JTable drinkTable = new JTable(defaultTableModel);    
	
	private int x = 1; //ingredient counter
	protected String FullIngredient = "";//Holds all the ingredients
	protected String FullQuantity = "";  //quantities
	
	//text fields to search/ enter info
    private JTextField findTextField = new JTextField(30);
    private JTextField textCocktailName = new JTextField(30);
    
    //TEXT FIELDS FOR DIFFERENT INGREDIENTS/QUANTITIES
    private JTextField tIngredient = new JTextField(30);
    private JTextField tQuantity = new JTextField(30);

    
    //CREATE BUTTONS
    private	JButton Yes = new JButton("Yes");
    private	JButton No = new JButton("No");
 	private	JButton Menu = new JButton("Main Menu");
 	private	JButton Exit = new JButton("Exit");
 	private	JButton View = new JButton("View Drinks");
 	private	JButton Search = new JButton("Search For Drinks");
 	private	JButton Rate = new JButton("Rate Drinks");
 	private	JButton Create = new JButton("Create a Drink");
 	private	JButton findButton = new JButton("Search");
 	private	JButton Back = new JButton("Back");
 	private JButton Next = new JButton("Next Drink");

 	
 	//ADD DRINK BUTTONS
 	private JButton Enter = new JButton("Enter");
    private JButton moreIngredients = new JButton("More Ingredients");
    
    //IMAGES ADDED TO GUI
	private Image icon = Toolkit.getDefaultToolkit().getImage("src/Icons/icon.jpg");    
	private Image star = Toolkit.getDefaultToolkit().getImage("src/Icons/Star.jpg");    
	private Image NoStar = Toolkit.getDefaultToolkit().getImage("src/Icons/NoStar.jpg");    
 	
	public DrinkGUI() {
		// displays YouCanMix title on every window
		super("YouCanMix");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setIconImage(icon);
		
		//Sets actions for each button
		Yes.addActionListener(new ActionListener() {//	WHEN YES BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				BuildTable();
				mainMenu();
			}
		}); 
		No.addActionListener(new ActionListener() {//	WHEN NO BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});
		Menu.addActionListener(new ActionListener() {//	WHEN MENU BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				catalogueFrame.removeAll();
				catalogueFrame.setVisible(false);
				searchDrinkFrame.removeAll();
				searchDrinkFrame.setVisible(false);
				rateDrinkFrame.removeAll();
				rateDrinkFrame.setVisible(false);
				deleteAllRows();
				mainMenu();
			}
		}); 
		Exit.addActionListener(new ActionListener() {//	WHEN EXIT BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});
	
		Search.addActionListener(new ActionListener() {//	WHEN SEARCH FOR DRINKS BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				bk = false;
				sr = false;
				searchDrinks();
			}
		});	
		View.addActionListener(new ActionListener() {//	WHEN VIEW DRINKS BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
			    	currentDrinks = manager.getDrinks("SELECT * FROM Drinks");
			    	//System.out.println("TESTER: " + currentDrinks[0].getDrinkName());
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				fillTable();
				bk = true;
				drinkCatalogue();
			}
		});	
		ListSelectionModel click = drinkTable.getSelectionModel();
		click.addListSelectionListener(new ListSelectionListener() {// WHEN AN ROW IS SELECTED
			@Override
	        public void valueChanged(ListSelectionEvent le) {
				if(active) {
					catalogueFrame.removeAll();
					catalogueFrame.setVisible(false);
					searchDrinkFrame.removeAll();
					searchDrinkFrame.setVisible(false);

					select = click.getMinSelectionIndex();
					System.out.println("Selection: " + select);
					if(select >= 0 && select < 100)viewDrink(select);
				}
	        }
	    });
		Back.addActionListener(new ActionListener() {//	WHEN BACK BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				displayDrinkFrame.removeAll();
				displayDrinkFrame.setVisible(false);				
				//System.out.println("Selection: " + currentIngredient + "/" + currentQuantity);
				if(bk == true) drinkCatalogue();
				else {
					deleteAllRows();
					searchDrinks();
				}
				Parameter = "*";
				x = 1;
			}
		});
		
		
		
		findButton.addActionListener(new ActionListener() {//	WHEN SEARCH BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				deleteAllRows();
				searchDrinkFrame.removeAll();
				searchDrinkFrame.setVisible(false);
				rateDrinkFrame.removeAll();
				rateDrinkFrame.setVisible(false);
				
				Parameter = findTextField.getText();
				try {
					currentDrinks = manager.getDrinks("SELECT * FROM Drinks WHERE Ingredients LIKE \"%" +
							Parameter +"%\" or Drink_Name LIKE \"%" + Parameter +"%\"");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				findTextField.setText(null);
				if(sr == false) {
					fillTable();
					searchDrinks();
					Parameter = "*";
				}
				else {
					System.out.println("Rating selection");
					ratingSelection();
					rateDrinks();
				}
				
			}
		}); 
		Rate.addActionListener(new ActionListener() {//	WHEN RATE DRINKS BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				sr = true;
				rateDrinks();
			}
		});
		
		//GOES TO THE NEXT DRINK RATING
		Next.addActionListener(new ActionListener() {//	WHEN CREATE DRINKS BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				rateDrinkFrame.removeAll();
				rateDrinkFrame.setVisible(false);
				x++;
				if (x <= manager.getCurrentSize()) {
					ratingSelection();
					rateDrinks();
				}
				else {
					noDrinkError();
					x = 1;
					rateDrinks();
				}
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
	    		FullIngredient += tIngredient.getText() + ", ";
	    		FullQuantity += tQuantity.getText() + ", ";
	    		tIngredient.setText(null);//clears out ingredient and quantity text box
		        tQuantity.setText(null);
	    		if(x < 10)createDrinks();//LIMITS THE AMOUNT OF INGREDIENTS THAT CAN BE ADDED
	    		else Error();
	        }
	    });
		Enter.addActionListener(new ActionListener() {//WHEN ENTER BUTTON IS PRESSED
	        @Override
	        public void actionPerformed(ActionEvent event) {
	        	
	        	//PUTS ALL INGREDIENTS/QUANTITIES EACH INTO ONE STRING
	        	FullIngredient += tIngredient.getText(); 
	        	FullQuantity += tQuantity.getText();
		        try {
		        	//MAKES NEW DRINK AND PUTS IT INTO CURRENT DRINK
					currentDrink = new Drink(textCocktailName.getText(), FullIngredient, 
							FullQuantity, 5 );
					
					//CONNECTS TO THE DATABASE AND INSERTS NEW DRINK INTO IT
					//RETURNS IF IT WAS ADDED OR NOT
					success = manager.insertDrink(currentDrink);
					
				} catch (ClassNotFoundException e) {//CATCHES ERROR
					e.printStackTrace();
				}
		        
		        if(success) {//IF DRINK WAS SUCCESSFULLY ADDED
		        	drinkAdded();
		        }
		        else drinkNotAdded();// IF DRINK WAS NOT ADDED
		        
		        //clears out ingredient, quantity and cocktail name text box
		        tIngredient.setText(null);
		        tQuantity.setText(null);
		        textCocktailName.setText(null);
		        
		        //clear out FullIngredient, FullQuantity and resets x counter
		        FullIngredient = "";
		        FullQuantity = "";
				x = 1;
				mainMenu();
	        }
	    });
		
		//Displays the verify screen to start
		Verify();
		
	}

	//INTERFACE TO VERIFY USERS AGE
	public void Verify() {
		
		JPanel verify = new JPanel(new GridBagLayout());	//panel to display menu
		
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
	
	//INTERFACE FOR THE MAIN MENU
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
        
		setVisible(true);
        pack();
        setLocationRelativeTo(null);
	}
	
	//INTERFACE THAT DISPLAYS THE CATALOGUE OF DRINKS
	public void drinkCatalogue(){ 
		
		getContentPane().removeAll();//clears frame
		setVisible(false);

		//View Drinks
		catalogueFrame = new JFrame();//NEW FRAME TO DISPLAY DRINKS
		catalogueFrame.setLayout(new FlowLayout());
		catalogueFrame.setIconImage(icon);
		catalogueFrame.setTitle("Drink Catalogue");


		JPanel catalogue = new JPanel(new GridBagLayout());//panel to display list of drinks
		
		catalogue.add(new JScrollPane(drinkTable));//ADDS THE DRINK TABLE TO THE PANEL
		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
       
        //ADDS MENU AND EXIT BUTTONS TO THE BOTTOM OF THE PANEL 
        constraints.gridx = 0;
        constraints.gridy = 2; 
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.WEST;
        catalogue.add(Menu, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 2; 
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.EAST;
        catalogue.add(Exit, constraints);
        
        catalogueFrame.setSize(800, (currentSize*75));//SETS THE SIZE OF THE FRAME
        
        //ADDS THE PANEL TO CATALOGUE FRAME
        catalogueFrame.add(catalogue);
         
        //SETS FRAME POSTITION TO THE MIDDLE OF THE SCREEN AND VISIBLE
        catalogueFrame.pack();
        catalogueFrame.setLocationRelativeTo(null);
        catalogueFrame.setVisible(true);
        catalogueFrame.validate();
	}
	
	//INTERFACE THAT DISPLAYS SEARCH BAR AND SEARCH RESULTS
	public void searchDrinks() { 
		
		getContentPane().removeAll();//clears frame
		setVisible(false);

		//Search for drinks
		searchDrinkFrame = new JFrame();//NEW FRAME TO DISPLAY SEARCH FOR DRINKS
		searchDrinkFrame.setLayout(new FlowLayout());
		searchDrinkFrame.setIconImage(icon);
		searchDrinkFrame.setTitle("YouCanMix");
			
		JPanel searching = new JPanel(new GridBagLayout());//panel to display list of drinks
		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
		
		searchBar();//GETS PANEL THAT DISPLAYS SEARCH BAR
		
		constraints.gridx = 0;
        constraints.gridy = 0;
        searching.add(search, constraints);//ADDS SEARCH BAR TO SEARCHING PANEL
        
        constraints.gridx = 0;
        constraints.gridy = 1;
		if(Parameter != "*") {//IF SOMETHING HAS BEEN SEARCHED FOR
			searching.add(new JScrollPane(drinkTable), constraints);//ADD TABLE OF RELEVANT DRINKS
		}
		else {//SETS BORDER TO THE SEARCHING PANEL
			searching.setBorder(BorderFactory.createTitledBorder(
	                BorderFactory.createEtchedBorder(), "Search For Drinks"));
		}
		
		//ADD MENU AND EXIT BUTTON TO PANEL
		constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.WEST;
        searching.add(Menu, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.EAST;
        searching.add(Exit, constraints);
			
        searchDrinkFrame.setSize(800, (currentSize*75));//SETS THE SIZE OF THE FRAME
        
        //add the panel to this frame
        searchDrinkFrame.add(searching);
         
        //SETS FRAME POSTITION TO THE MIDDLE OF THE SCREEN AND VISIBLE
        searchDrinkFrame.pack();
        searchDrinkFrame.setLocationRelativeTo(null);
        searchDrinkFrame.setVisible(true);
        searchDrinkFrame.validate();
		
	}
	
	//interface for user to rate drinks
	public void rateDrinks() { 
	
		getContentPane().removeAll();//clears frame
		setVisible(false);
		
		//Rate Drinks
		rateDrinkFrame = new JFrame();//NEW FRAME TO DISPLAY RATE DRINKS
		rateDrinkFrame.setLayout(new FlowLayout());
		rateDrinkFrame.setIconImage(icon);
		rateDrinkFrame.setTitle("YouCanMix");
		
		JPanel rating = new JPanel(new GridBagLayout());//panel to display list of drinks
		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
		
		searchBar();//GETS PANEL THAT DISPLAYS SEARCH BAR
		
		constraints.gridx = 0;
        constraints.gridy = 0;
        rating.add(search, constraints);//ADDS SEARCH BAR TO SEARCHING PANEL
        
        constraints.gridx = 0;
        constraints.gridy = 1;
        if(Parameter != "*") {//IF SOMETHING HAS BEEN SEARCHED FOR
        	rating.add(RatingPanel, constraints);//ADD TABLE OF RELEVANT DRINKS
		}
		else {//SETS BORDER TO THE SEARCHING PANEL
			rating.setBorder(BorderFactory.createTitledBorder(
	                BorderFactory.createEtchedBorder(), "Rating Drinks"));
		}   
		
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
			
        rateDrinkFrame.setSize(600, (x*85));//SETS THE SIZE OF THE FRAME
        
        // add the panel to this frame
        rateDrinkFrame.add(rating);
         
      //SETS FRAME POSTITION TO THE MIDDLE OF THE SCREEN AND VISIBLE
        rateDrinkFrame.pack();
        rateDrinkFrame.setLocationRelativeTo(null);
        rateDrinkFrame.setVisible(true);
        rateDrinkFrame.validate();
	}
	
	public void ratingSelection(){
		
		RatingPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
		
        JLabel title = new JLabel("Cocktail Name:  " + 
        		currentDrinks[x-1].getDrinkName() + "    Rate: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        RatingPanel.add(title, constraints);
        
        
        JButton one = new JButton();
        one.setIcon(new ImageIcon(NoStar));
					
        constraints.gridx = 1;
        RatingPanel.add(one);
        one.addActionListener(new ActionListener() {//WHEN ONE STAR IS PRESSED
        	@Override
        	public void actionPerformed(ActionEvent event) {
        		try {
					success = manager.insertRate(currentDrinks[x-1], 1);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
        		if(success) {//IF RATING WAS SUCCESSFULLY ADDED
		        	rateAdded();
		        }
		        else rateNotAdded();// IF RATING WAS NOT ADDED
        	}
				
        });
        
        
        JButton two = new JButton();
        two.setIcon(new ImageIcon(NoStar));
					
        constraints.gridx = 2;
        RatingPanel.add(two);
        two.addActionListener(new ActionListener() {//WHEN TWO STAR IS PRESSED
        	@Override
        	public void actionPerformed(ActionEvent event) {
        		try {
        			success = manager.insertRate(currentDrinks[x-1], 2);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
        		if(success) {//IF RATING WAS SUCCESSFULLY ADDED
		        	rateAdded();
		        }
		        else rateNotAdded();// IF RATING WAS NOT ADDED
        	}
        	
        });
        
        
        JButton three = new JButton();
        three.setIcon(new ImageIcon(NoStar));
					
        constraints.gridx = 3;
        RatingPanel.add(three);
        three.addActionListener(new ActionListener() {//WHEN THREE STAR IS PRESSED
        	@Override
        	public void actionPerformed(ActionEvent event) {
        		try {
        			success = manager.insertRate(currentDrinks[x-1], 3);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
        		if(success) {//IF RATING WAS SUCCESSFULLY ADDED
		        	rateAdded();
		        }
		        else rateNotAdded();// IF RATING WAS NOT ADDED
        	}
				
        });
        
        
        JButton four = new JButton();
        four.setIcon(new ImageIcon(NoStar));
					
        constraints.gridx = 4;
        RatingPanel.add(four);
        four.addActionListener(new ActionListener() {//WHEN FOUR STAR IS PRESSED
        	@Override
        	public void actionPerformed(ActionEvent event) {
        		try {
        			success = manager.insertRate(currentDrinks[x-1], 4);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
        		if(success) {//IF RATING WAS SUCCESSFULLY ADDED
		        	rateAdded();
		        }
		        else rateNotAdded();// IF RATING WAS NOT ADDED
        	}
				
        });
        
        
        JButton five = new JButton();
        five.setIcon(new ImageIcon(NoStar));
					
        constraints.gridx = 5;
        RatingPanel.add(five);
        five.addActionListener(new ActionListener() {//WHEN FIVE STAR IS PRESSED
        	@Override
        	public void actionPerformed(ActionEvent event) {
        		try {
        			success = manager.insertRate(currentDrinks[x-1], 5);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}  
        		if(success) {//IF RATING WAS SUCCESSFULLY ADDED
		        	rateAdded();
		        }
		        else rateNotAdded();// IF RATING WAS NOT ADDED
        	}
				
        });
                
        constraints.gridx = 6;
        constraints.gridy = 6;
        constraints.gridwidth = 5;
        constraints.anchor = GridBagConstraints.CENTER;
        RatingPanel.add(Next);
		
	}    
	
	//FILLS THE JTABLE THAT HAS A COLUMN FOR DRINK NAME AND INGREDIENTS 
	public void fillTable() {
		
    	int num = 0;
    	currentSize = manager.getCurrentSize();//NUM OF DRINKS BEING PUT INTO TABLE 
    	while(num < currentSize ) {// WHILE THERE ARE DRINKS IN THE ARRAY
		 defaultTableModel.addRow(new Object[]{currentDrinks[num].getDrinkName(), 
				 currentDrinks[num].getIngredients()});
         num++;
    	}
		if(currentSize == 0)noDrinkError();//NO DRINKS MATCHED SEARCH	
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
	    JLabel ingredient = new JLabel("Enter Ingredient " + x + ": ");
	    JLabel quantity = new JLabel("Enter Ingredient " + x + "\'s Quantity: ");
	    
	    //initial components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;     
        creating.add(cocktailName, constraints); //LABEL FOR NAME OF DRINK
 
        constraints.gridx = 1;
        creating.add(textCocktailName, constraints); //TEXT BOX FOR NAME OF DRINK
         
        constraints.gridx = 0;
        constraints.gridy = 1;     
        creating.add(ingredient, constraints); //LABEL FOR INGREDIENT X
         
        constraints.gridx = 1;
        creating.add(tIngredient, constraints);//TEXT BOX FOR INGREDIENT X
        
        constraints.gridx = 0;
        constraints.gridy = 2;     
        creating.add(quantity, constraints); //LABEL FOR QUANTITIY X
        
        constraints.gridx = 1;
        creating.add(tQuantity, constraints); //TEXT BOX FOR QUANTITIY X
        
        constraints.gridx = 2;
        creating.add(moreIngredients, constraints); //BUTTON TO ADD MORE INGREDIENTS
        
        //PUTS THE ENTER BUTTON IN THE MIDDLE OF THE PANEL
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        creating.add(Enter, constraints);
        
        //ADDS MENU AND EXIT BUTTON TO THE BOTTOM OF THE PANEL
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
        
        //SETS THE LOCATION OF THE FRAME
        pack();
        setLocationRelativeTo(null);
	 
	}
	
	//CREATES A PANEL THAT HOLDS THE SEARCH BAR
	public void searchBar() {
		
		search = new JPanel(new GridBagLayout());//PANEL TO DISPLAY THE SEARCH BAR
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);

		JLabel findLabel = new JLabel("Search:");//LABEL TO DISPLAY SEARCH
	    
	    constraints.gridx = 0;
        constraints.gridy = 0;     
        search.add(findLabel, constraints);//ADD LABEL TO PANEL

        constraints.gridx = 1;
        search.add(findTextField, constraints);//ADD TEXT FIELD TO PANEL
        
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.EAST;
        search.add(findButton, constraints);//ADDS BUTTON TO SEARCH FOR DRINKS TO PANEL
	 
	}
	
	//INTERFACE THAT DISPLAYS CURRENT DRINK
	public void viewDrink(int s) {
		
		getContentPane().removeAll();//clears frame
		
		displayDrinkFrame = new JFrame();//NEW FRAME TO DISPLAY CURRENT DRINK
		displayDrinkFrame.setLayout(new FlowLayout());
		displayDrinkFrame.setIconImage(icon);
		displayDrinkFrame.setTitle("Drink Recipe");

		
		JPanel displayCocktail = new JPanel(new GridBagLayout());//panel to display list of drinks
		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 2, 10, 2);       
        
        //ADDS INGREDIENTS AND QUANTITIES TO RESPECTIVE LISTS 
        currentIngredient = currentDrinks[s].getIngredients().split(",");
        currentQuantity = currentDrinks[s].getQuantities().split(",");    

        //LABEL THAT DISPLAYS THE CURRENT COCKTAIL NAME
        JLabel cocktailName = new JLabel("Cocktail Name: " + currentDrinks[s].getDrinkName());
        
        constraints.gridx = 0;
        constraints.gridy = 0;
        displayCocktail.add(cocktailName, constraints); //ADDS LABEL TO PANEL
        
        while( x <= currentIngredient.length ) {//WHILE THERE ARE STILL INGREDIENTS TO DISPLAY
        	
        	//Display ingredients and quantities
        	JLabel ingredient = new JLabel("Ingredient " + x + ": " + currentIngredient[x-1]);
        	JLabel quantity = new JLabel("Quantity: " + currentQuantity[x-1]);        	

        	constraints.gridx = 0;
            constraints.gridy = x;
            displayCocktail.add(ingredient, constraints); //ADDS LABEL FOR INGREDIENT X
                 
            constraints.gridx = 1;
            constraints.gridy = x;
            displayCocktail.add(quantity, constraints); //ADDS LABEL FOR QUANTITIY X
            
      	    x++;
        }
        
        ratingGUI(s);//GETS THE RATING OF THE DRINK 
        
        
        constraints.gridx = 0;
        constraints.gridy = x;
        constraints.gridwidth = 10;
        displayCocktail.add(rate, constraints);

        
        //add menu and exit button to panel
        constraints.gridx = 0;
        constraints.gridy = x + 1;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.WEST;
        displayCocktail.add(Back, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = x + 1 ;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.EAST;
        displayCocktail.add(Exit, constraints);
      			
        
        displayDrinkFrame.setSize(600, (x*85));//SETS THE SIZE OF THE FRAME
                
        // add the panel to this frame
        displayDrinkFrame.add(displayCocktail);
        
        //SETS LOCATION AND VISIBILITY OF FRAME
        displayDrinkFrame.pack();
        displayDrinkFrame.setLocationRelativeTo(null);
        displayDrinkFrame.setVisible(true);
        displayDrinkFrame.validate();
      		
		
	}
	
	//CREATES A PANEL THAT HOLD THE DRINK rate
	public void ratingGUI(int s) {
		
		rate = new JPanel(new GridBagLayout());//PANEL TO DISPLAY THE RATE OF A DRINK
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
		
        //CREATES AND ADD rate LABEL TO PANEL
		JLabel title = new JLabel("Rating: ");
        constraints.gridx = 0;
        rate.add(title);
        
        //CREATES AND ADDS APPROPRIATE RATING ICON LABEL TO PANEL
        for(int x = 1; x <= 5; x++) {
        	JLabel Rate = new JLabel();
        	if(x <= currentDrinks[s].getRating()) {
        		Rate.setIcon(new ImageIcon(star));
           		constraints.gridx = x;
           		rate.add(Rate);
        	}
        	else {
        		Rate.setIcon(new ImageIcon(NoStar));
           		constraints.gridx = x;
           		rate.add(Rate);
        	}
        	
        }
		
	}

	//MAKE AN JTABLE THAT HAS A COLUMN FOR DRINK NAME AND INGREDIENTS
	public void BuildTable() {
		
		//makes Table to Display all the drinks
	       drinkTable.setPreferredScrollableViewportSize(new Dimension(600, 200));
	       drinkTable.setFillsViewportHeight(true);
	       defaultTableModel.addColumn("Drink Name");
	       defaultTableModel.addColumn("Ingredients");    
	       
	       //sets the width of each column
	       TableColumnModel colModel = drinkTable.getColumnModel();
	       TableColumn col = colModel.getColumn(0);
	       col.setPreferredWidth(200);
	       col = colModel.getColumn(1);
	       col.setPreferredWidth(200);
	}
	
	//CLEARS THE JTABLE AND ASSOCIATED VALUES
	public void deleteAllRows() {
		active = false;
		for(int g = currentIngredient.length-1; g >= 0; g--) {
			currentIngredient[g] = "";
			currentQuantity[g] = "";
		}
		while(defaultTableModel.getRowCount() > 0) {
			defaultTableModel.removeRow(0);
		}
		currentSize = 0;
		active = true;
		Parameter = "*";
		x = 1;
	}

	//ERROR MESSAGE WHEN USER TRIES TO ADD TOO MANY INGREDIENTS
	public void Error() {
		JFrame eFrame = new JFrame();
        JOptionPane.showMessageDialog(eFrame, "Can't Add Anymore Ingredients!", "ERROR", JOptionPane.ERROR_MESSAGE);	
	}
	
	//ERROR MESSAGE WHEN NO MATCHING DRINKS ARE FOUND 
	public void  noDrinkError() {
		JFrame nDEFrame = new JFrame();
        JOptionPane.showMessageDialog(nDEFrame, "No matching drinks found", "ERROR", JOptionPane.ERROR_MESSAGE);
        Parameter = "*";
	}
	
	//ERROR MESSAGE IF DRINK WAS NOT ADDED TO DATABASE
	public void drinkNotAdded() {
		JFrame dNAFrame = new JFrame();
        JOptionPane.showMessageDialog(dNAFrame, "Drink Not Added!", "ERROR", JOptionPane.ERROR_MESSAGE);
	}
	
	//SUCCESS MESSAGE IF DRINK WAS ADDED TO DATABASE
	public void drinkAdded() {
		JFrame dAFrame = new JFrame();
        JOptionPane.showMessageDialog(dAFrame, "Drink Succesfully Added to Database!", "SUCCESS", JOptionPane.PLAIN_MESSAGE);
	}
	
	//ERROR MESSAGE IF RATING WAS NOT ADDED TO DATABASE
	public void rateNotAdded() {
		JFrame rNAFrame = new JFrame();
        JOptionPane.showMessageDialog(rNAFrame, "Rating Not Added!", "ERROR", JOptionPane.ERROR_MESSAGE);
	}
	
	//SUCCESS MESSAGE IF RATING WAS ADDED TO DATABASE
	public void rateAdded() {
		JFrame rAFrame = new JFrame();
        JOptionPane.showMessageDialog(rAFrame, "Rating Succesfully Added to Database!", "SUCCESS", JOptionPane.PLAIN_MESSAGE);
	}
	
	
}
